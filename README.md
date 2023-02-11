# Video Database

Developed a simplified platform for movie & series information with personalized recommendations. Utilized OOP concepts such as inheritance, aggregation, polymorphism, and abstraction. Practiced Java programming and emphasized code quality for design and structure. Used checkstyle for code style compliance. <br>

<h1> Implementation </h1>

The implemented platform simulates actions that users can perform on a movie viewing platform: ratings, movie viewing, searches, recommendations, etc. <br>

The entities that were modeled are: <br>

<h3>Video</h3>
- Of two types: movies and shows (TV series). The difference between them is that TV series have seasons. <br>
- All videos have in common a title, release year, one or more genres (e.g. comedy, thriller) <br>
- The seasons of a TV series are associated with a number, the duration of the entire season, and a rating. <br>
- Movies have duration and rating. <br>

<h3>User</h3>
- Has two categories: standard and premium. <br>
- Has favorite videos and viewed videos. <br>
- Data for these entities is loaded from JSON files provided as input in tests. They are kept in a Repository. <br>

Users can perform the following three types of actions: commands, queries, and recommendations. <br>

<h1> Execution of the project </h1>

1. The data read from the test JSON file is loaded into objects.<br>
2. Actions (commands, queries or recommendations) are received sequentially and executed as they are received, their result having an effect on the Repository. <br>
3. After executing an action, the result of the action is displayed in the output JSON file. <br>
4. Upon completion of all actions, the program execution is also completed. <br>

<h3> Commands </h3>
These represent a user's ability to perform direct actions and are of 3 different types.<br>
- Favorite - adds a video to the user's list of favorite videos if the user has already viewed it. <br>
- View - views a video by marking it as seen. If the user has already seen it, the number of views of that video by the user is incremented. When viewing a TV series, all seasons are viewed. <br>
- Rating - provides a rating for a video that has already been seen (for TV series, it is applied per season, unlike viewing, where it is done for the entire series). <br>
The rating varies depending on the type - for TV series it is per season.<br>
The rating can only be given once by a user. For TV series, it can only be given once per season.<br>

<h3>Queries</h3>
These represent global searches performed by users for actors, videos, and users. The results of these queries are displayed as output of the test.<br>

A query contains the type of information being searched for: actor/video/user, the sorting criteria, and various parameters.Queries include the sorting order as a parameter, whether sorting is to be done in ascending or descending order. The secondary sorting criterion is alphabetical order in descending/ascending order depending on the order from the first criterion.<br>

<h3>Recommendations</h3>
These represent suggestions made to users based on their viewing history, favorites, and ratings. Recommendations are made based on different criteria, such as genre, actor, etc. The recommendations made are displayed as output of the test.<br>
