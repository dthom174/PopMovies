# PopMovies 
Most of us can relate to kicking back on the couch and enjoying a movie with friends and family. In this project, you’ll build an app to allow users to discover the most popular movies playing.

## Stage 1
- Presents the user with a grid arrangement of movie posters upon launch.
- allow users to change sort order via a setting:(The sort order can be by most popular or by highest-rated)
- allow the user to tap on a movie poster and transition to a details screen with additional information such as:
  * original title
  * movie poster image thumbnail
  * A plot synopsis (called overview in the api)
  * user rating (called vote_average in the api)
  * release date
## Stage 2
- allow users to view and play trailers (either in the youtube app or a web browser).
- allow users to read reviews of a selected movie.
- also allow users to mark a movie as a favorite in the details view by tapping a button (star).
- made use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle) to create a robust an efficient application.
- created a database using Room to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).
- modified the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
