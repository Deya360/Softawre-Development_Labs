package com.sd.lab3_a;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder> {
    private List<Student> students;

    public TableAdapter(List<Student> students) {
        this.students = students;
    }

    class TableHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView idTv;
        private TextView fullNameTv;
        private TextView dateAddedTv;

        TableHolder(View itemView) {
            super(itemView);
            this.idTv = itemView.findViewById(R.id.idTv);
            this.fullNameTv = itemView.findViewById(R.id.full_nameTv);
            this.dateAddedTv = itemView.findViewById(R.id.date_addedTv);
        }
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        return new TableHolder(itemView);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int pos) {
        Student currentStudent = students.get(pos);

        //Populate data
        holder.idTv.setText(String.valueOf(currentStudent.getId()));
        holder.fullNameTv.setText(currentStudent.getFullName());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3")); /* TimeZone.getDefault() wasn't working on my machine, had to hardcode it :( */
        holder.dateAddedTv.setText(sdf.format(currentStudent.getDateAdded()));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
