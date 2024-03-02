# To the 'Beat'  

## A music festival companion app

#### To the Beat is aiming to be your companion app for when you are at a festival and don't know what to do. It will help you find the best music based on your listening habits on Spotify

## Scope: 

* create an application for the purpose of learning JavaFX, API calls and how to manage a database using JDBC and SQLITE

## Table of contents:

    1. Technologies used
    2. The need
    3. Features
    4. How it works
    5. Usage

### 1. Technologies Used:
* JavaFX
* Spark Framework
* Spotify API
* Rapid API
* Maven
* org.json
* SQLite
* JDBC

### 2. The need:
* The need came from a discussion with a friend that had trouble finding bands to go to at Electric Festival. He was overwhelmed by the amount of bands and didn't know where to start. That sparked the idea of creating an app that would help him find the best bands to go to based on his listening habits on Spotify.

### 3. Features:
* User can log in with their Spotify account
* User can see their top artists,tracks and genres
* User can find all the concerts/festivals in his area
* Based on the user's top genres, the app will recommend concerts/festivals

### 4. How it works:
* The app uses the Spotify API in order to get the user's top artists and tracks. Based on this information it will generate a top genres list.
* Authorization code flow:
  
  ![auth-code-flow](https://github.com/RazvanMacovei15/SpotifyFestival/assets/95320896/cda242a3-6cd3-4b7f-a5ec-c7c1af649101)

* The app uses the Rapid API Concerts - Artists Events Tracker in order to get the concerts/festivals in the user's area.
* SQLite is used to store the user's information and all the information regarding the festivals
* In the feature "THIS IS THE WAY" the user will have the option to view all the concerts/festivals in his area and based on his top genres, the app will recommend at which of them he can go to
* All this is viewed in a JavaFX GUI using a Tree-like structure with the root being the User's Current Location
* The following images will be used to illustrate the app's functionality for both the normal user and the admin user

### 5. Usage:
1. Normal User:

![one_240302_131312_1](https://github.com/RazvanMacovei15/SpotifyFestival/assets/95320896/1fc6725c-a530-4863-845e-38c9f3061611)

![two_240302_133720_1 copy](https://github.com/RazvanMacovei15/SpotifyFestival/assets/95320896/ac29d108-13b5-4ccb-ae8e-5f189f4903f0)

2. Admin User:

![Three_1 copy](https://github.com/RazvanMacovei15/SpotifyFestival/assets/95320896/630f1162-0144-48ae-b382-70680a152595)

