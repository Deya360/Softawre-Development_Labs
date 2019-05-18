# Labartory Task 3

Just yet another android application, developed for education purposes.


### Cool Features
- The first part of the application acts as a ContentProvider for the second part, essentially, allowing both parts to operate the same database and exchange data seemlessly.
- The database provides a fallback option, in the event the database needs to be downgraded back to the previous version.

#
### Preview


*general application showcase*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab3_A/screencapture1.gif"/>

*seemless data interchange between both parts*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab3_A/screencapture2.gif"/>

*database onUpgrade/onDowngrade function*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab3_A/screencapture3.gif"/>

#
### Libraries Used

| Library | Link | Usage |
| ------ | ------ | ------ |
| Faker | https://github.com/blocoio/faker | Generates fake data for testing or populating a development database. | 

#
### Original Task Requirements

>Создать приложение, взаимодействующее с базой данных. Первое активити должно содержать три кнопки. При нажатии на первую кнопку должно открываться новое активити, выводящее информацию из таблицы Студенты в удобном для восприятия формате.

>При запуске приложения необходимо:
>1. Создать БД, если ее не существует.
>2. Создать таблицу Студенты, содержащую поля:
>   + ID
>   + ФИО
>   + Время добавления записи
>3. Удалять все записи из БД, а затем вносить 5 записей об одногруппниках со случайными данными

>При нажатии на вторую кнопку необходимо внести еще одну запись в таблицу.
>При нажатии на третью кнопку необходимо заменить ФИО в последней внесенной записи на Иванов Иван Иванович.


>Создать также новое отдельное приложение на основе приложения, созданного в части 1. Переопределить функцию onUpgrade. При изменении изменить таблицу Студенты следующим образом:
> - ID
> - Фамилия
> - Имя
> - Отчество
> - Время добавления записи
>Данные из поля ФИО необходимо по проблема разделить на три поля: Фамилия, Имя, Отчество. Также, изменить версию базы данных.
