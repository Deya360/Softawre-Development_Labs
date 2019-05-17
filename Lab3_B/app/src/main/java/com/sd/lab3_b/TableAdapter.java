package com.sd.lab3_b;

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
    private boolean isUpgraded;

    public TableAdapter(List<Student> students, boolean isUpgraded) {
        this.students = students;
        this.isUpgraded = isUpgraded;
    }

    class TableHolder extends RecyclerView.ViewHolder {
        private View rootView;
        private TextView idTv;
        private TextView fullNameTv;
        private TextView lastNameTv;
        private TextView firstNameTv;
        private TextView middleNameTv;
        private TextView dateAddedTv;

        TableHolder(View itemView) {
            super(itemView);
            this.idTv = itemView.findViewById(R.id.idTv);
            this.fullNameTv = itemView.findViewById(R.id.full_nameTv);
            this.lastNameTv = itemView.findViewById(R.id.last_nameTv);
            this.firstNameTv = itemView.findViewById(R.id.first_nameTv);
            this.middleNameTv = itemView.findViewById(R.id.middle_nameTv);
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

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int pos) {
        Student currentStudent = students.get(pos);

        //Populate data
        holder.idTv.setText(String.valueOf(currentStudent.getId()));

        if(isUpgraded) {
            holder.lastNameTv.setText(currentStudent.getLastName());
            holder.firstNameTv.setText(currentStudent.getFirstName());
            holder.middleNameTv.setText(currentStudent.getMiddleName());

        } else {
            holder.fullNameTv.setText(currentStudent.getFullName());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3")); /* TimeZone.getDefault() wasn't working on my machine, had to hardcode it :( */
        holder.dateAddedTv.setText(sdf.format(currentStudent.getDateAdded()));

        // Configure Visibility
        if(isUpgraded) {
            holder.lastNameTv.setVisibility(View.VISIBLE);
            holder.firstNameTv.setVisibility(View.VISIBLE);
            holder.middleNameTv.setVisibility(View.VISIBLE);
            holder.fullNameTv.setVisibility(View.GONE);

        } else {
            holder.lastNameTv.setVisibility(View.GONE);
            holder.firstNameTv.setVisibility(View.GONE);
            holder.middleNameTv.setVisibility(View.GONE);
            holder.fullNameTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
