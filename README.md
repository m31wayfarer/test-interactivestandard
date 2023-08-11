# test-interactivestandard

<h2>Тестовое задание для Android разработчика</h2>
<br/>

Требуется написать на Kotlin или Java мобильное приложение для Android, которое запрашивает у
сервера определённое количество координат точек (x, y), а затем отображает полученный ответ в виде
таблицы и графика.
На главном экране имеется блок информационного текста, поле для ввода числа точек и одна кнопка
«Поехали».
По нажатию на кнопку осуществляется запрос к API сервера (GET /api/test/points), с параметром
количества запрашиваемых точек (count).
Сервер выдаёт в ответ массив точек в JSON формате, пример: {"
points":[{"x":1.23, "y":2.44},{"x":2.17, "y":3.66}]}
<br/>
Спецификация API:
https://hr-challenge.interactivestandard.com/swagger-ui.html?urls.primaryName=mobile
<br/>
При неверном количестве запрошенных точек сервер возвращает ошибку. Кроме того он может просто
ломаться сам-по-себе.
Если ответ от сервера получен, то на новом экране должна отобразиться таблица с полученными
координатами точек. Ниже должен быть отображен график с точками, соединёнными прямыми линиями. Точки
на графике должны следовать по возрастанию координаты x.
<br/>
Дополнительно осуществлены следующие возможности работы с графиком:
<br/>
изменения масштаба пользователем
<br/>
работа в портретной и ландшафтной ориентации экрана
<br/>
функциональность поделиться картинкой графика
<br/>

<b>Архитектура</b>: Clean architecture
<br/>
<b>Архитектура слоя presentation</b>: Jetpack MVVM
<br/>
<b>DI</b>: Hilt
<br/>
<b>Навигация</b>: Jetpack Navigation
<br/>
<b>Асинхронность</b>: Kotlin Flow & Coroutines
<br/>
<b>Сеть</b>: Retrofit
<br/>
<b>Логирование</b>: Timber
<br/>
<b>Графики</b>: MPAndroidChart
<br/>
<b>Прочее</b>: Jetpack Startup, Splash, Single Activity, Fragments
<br/>

Проект (вьюмодели и мапперы) покрыт юнит-тестами с помощью Mockk и kotlinx-coroutines-test