# Labartory Task 2

Just a android application, developed for education purposes.


### Cool Features
- The item list supports the Swipe Down to Refresh feature. Thus, the application no longer needs to be restarted to Load or Update the item list. 
- The application saves the raw input JSON-file to phone storage. Thus, repeated downloading of the file from the Internet is no longer necessary, which saves network-traffic, allows the application to run in offline-mode and allows further processing to be done on the file.


#
### Preview


*general application showcase*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab2/screencapture1.gif"/>

*swipe to refresh showcase*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab2/screencapture2.gif"/>

*content provider showcase*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab2/screencapture3.gif"/>

*JSON-file saving showcase*
<br /><img src="https://github.com/Deya360/Softawre-Development_Labs/blob/main/Lab2/screencapture4.gif"/>

#
### Libraries Used

| Library | Link | Usage |
| ------ | ------ | ------ |
| Glide | https://github.com/bumptech/glide | Loading images from URL into ImageView | 

#
### Original Task Requirements

>Реализовать приложение, состоящее из двух активити:

>Первое активити - сплеш скрин из первой лабораторной, только показывается не больше чем время загрузки.

>Второе активити представляет собой список и ViewPager сформированный из [json-файла][link1].
>Этот JSON файл — массив «технологий» в игре Цивилизация. В каждой технологии вас должны интересовать поля:
> - graphic - картинка (относительно [ссылки][link2])
> - name - название
> - helptext - дополнительная информация (может не быть)
>Второе активити должно начинаться с фрагмента со списком, в элементе списка маленькая картинка 64dp и название.

>Список кликабельный. При клике на технологии открывается ViewPager c фрагментом, в котором соответствующая картинка большого размера (не больше ширины экрана-20dp) и описанием технологии. При свайпе вправо описание должно меняться на описание предмета выше в списке, соответственно влево - на описание ниже в списке.

>Опционально приложение может предоставлять Content Provider с даннымитехнологий.

>Загрузка JSON должна происходить в момент показа сплеш скрина. Загрузка картинок, в процессе показа списка (естественно не в UI потоке), имена технологий не должны прыгать в процессе загрузки. Необходимо минимизировать использование трафика.


[//]: #
[link1]: https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json
[link2]: https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/advanced_flight.jpg
