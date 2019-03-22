# Labartory Task 1

Just a simple android application, developed for education purposes.

### Cool Features
- Listview item images are populated with randomly generated avatar images from [Divebear's Online API][link1].
- Smart image loading: if not internet connection is available, Listview item images are populated randomly from local album gallery.
- Any item in the Listview can be viewed individually in a detailed activity, with enlarged image and text size.

#
### Preview

![screencapture1](https://imgur.com/ei9CJ8F.gif)
<img src="https://imgur.com/ei9CJ8F.gif" hight="400px">
*general application overview*
<br />
<br />

![screencapture2](https://imgur.com/B9o8Yln.gif)

*vertical screen oriantation compatibility*

#
### Libraries Used

| Library | Link | Usage |
| ------ | ------ | ------ |
| Glide | https://github.com/bumptech/glide | Loading images from URL into ImageView | 
| AndroidSVG | https://github.com/BigBadaboom/androidsvg | Loading .SVG images into ImageView |

#
### Original Task Requirements

>Реализовать приложение, состоящее из двух активити:

>Первое активити - это splash screen. Полноэкранное без ActionBar, c одной картинкой (или анимацией по желанию) по центру. Активити показывается 2 секунды, потом запускает второе и первое «умирает». Ожидание необходимо реализовать через Thread.sleep.

>Второе активити представляет из себя список из 1000000 (один миллион) элементов, где четные элементы имеют серый фон (#CCCCCC), а нечетные — белый (#FFFFFF). Каждый элемент содержит картинку и текст. Картинка может быть любая, и она не меняется для элементов (выбирайте маленькую). Текст каждого элемента — это его индекс текстом (пример «cто двенадцать»). Отсчет начинается с единицы. Шрифт большой.

>Приложение должно нормально обрабатывать поворот экрана. При нажатии на клавишу back во втором активити приложение должно закрываться.


[//]: #
[link1]: https://avatars.dicebear.com/