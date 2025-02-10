# beefirstonvideo
Technical test for BeeFirst. This app should play a dailymotion's video by id

TECH AND ARCHI

This project is developed in full Kotlin and Jetpack Compose (navigation included). 
The project uses Koin for dependency injection. 
The clean architecture separates the different layers: views (= app) / domain (= use cases) / data (= repositories). 
The App layer contains all the Android-specific parts: activities, views, view models, etc... It only uses the different UseCases to know what to display and when. 
The UseCases layer corresponds to the business need. For example, the UserUseCase allows all the manipulations on the Video that we will need for the proper functioning of the app (get/play/pause). 
The Repositories layer corresponds as a whole to the management of data. In our case, it is the a remote repository with HTTP that allows us to get all video infos needed.
Thanks to this architecture, each layer can be implemented independently of the others and tested more easily. 
We are on a prototype, so there is no 100% code coverage but there is still some to show the approach that should be followed for each module.

HOW IT WORKS

You can build the project and launch it on a device by pressing the play button in android studio. (build "app" with "debug" build variant by default)
When arriving on the HomeScreen we can enter, in a text field, an existing Dailymotion video ID. The app will retrieve information about this video, if it exists, and display the video in a player.
Portrait or landscape mode is managed. Several error messages can guide the user if they do not have a network connection or if the ID does not exist, among many others.

Enjoy your dailymotion video without advertising !
