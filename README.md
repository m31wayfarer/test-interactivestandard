<h2>Test Assignment for Android Developer</h2>
<br/>
You are required to develop a mobile application for Android using Kotlin. The application
should request a certain number of coordinate points (x, y) from a server, and then display the
received response in the form of a table and a graph.
<br/>
The main screen should have a block of informational text, a field for entering the number of
points, and a button labeled "Let's Go".
<br/>
Upon pressing the button, a request is made to the server's API (GET /api/test/points), with the
parameter specifying the number of points to request (count).
<br/>
The server responds with an array of points in JSON format, for example: {"
points":[{"x":1.23, "y":2.44},{"x":2.17, "y":3.66}]}
<br/>
API Specification:
https://hr-challenge.interactivestandard.com/swagger-ui.html?urls.primaryName=mobile
<br/>
If an incorrect number of points is requested, the server returns an error. Additionally, the server
might experience occasional outages.
<br/>
If a response is received from the server, a new screen should display a table with the received
coordinate points. Below the table, a graph should be displayed with points connected by straight
lines. The points on the graph should be arranged in ascending order of the x-coordinate.
<br/>
The following additional graph functionalities should be implemented:
<br/>
<ul>
<li>User-scalable functionality</li>
<li>Support for both portrait and landscape screen orientations</li>
<li>Capability to share the graph image</li>
</ul>
<br/>
<b>Architecture</b>: Clean architecture
<br/>
<b>Presentation Layer Architecture</b>: Jetpack MVVM
<br/>
<b>Dependency Injection (DI)</b>: Hilt
<br/>
<b>Navigation</b>: Jetpack Navigation
<br/>
<b>Asynchronicity</b>: Kotlin Flow & Coroutines
<br/>
<b>Networking</b>: Retrofit
<br/>
<b>Logging</b>: Timber
<br/>
<b>Graphs</b>: MPAndroidChart
<br/>
<b>Other</b>: Jetpack Startup, Splash Screen, Single Activity, Fragments
<br/>
<br/>
The project (view models and mappers) is covered by unit tests using Mockk and 
kotlinx-coroutines-test.