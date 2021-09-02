# Movies App

A basic movies app made with kotlin with the implementation of https://developers.themoviedb.org/3/

## Features
- Get the most popular movies from themoviedb
- Get the movies in a page-based fashion
- Cached http requests
- Cached image load requests
- Sort the movies by liked or disliked
- Dark mode
- EN - ES support
- Tablet support

## Considerations
- For pagination a NumberPicker was used instead of generating a grid/row of the available numbers by clusters so the user can spin quickly between pages
- The local storage was implemented using shared preferences with a json based approach
- The user can like or dislike a movie from wherever it comes (Popular, favorites, non favorites) in favor of UX
- The app DOES MAKE an http request if there's no internet connection (but will show a toast indicating that there's none), this is in response of the http cache implementation, if the request finds a cached list of movies will show that instead if needed this can be easily patched with a simple return
- The user can view the full poster image from the detail view
- Only 1 call to the API is used, this is to reduce the data consumption (the /popular endpoint retrieves the necessary data for the app)
- Libraries where used (we don't have to re-make the wheel smh)
- Picasso was used for image loading
- Retrofit was used for http requests
- Gson was used as a json parser

