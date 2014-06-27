# SimpleTwitterClient

* Part 2

Completed required user stories:

* [x] Includes all required user stories from Week 3 Twitter Client
* [x] User can switch between Timeline and Mention views using tabs.
..* [x] User can view their home timeline tweets.
..* [x] User can view the recent mentions of their username.
..* [] User can scroll to bottom of either of these lists and new tweets will load ("infinite scroll")
..* [] Optional: Implement tabs in a gingerbread-compatible approach
* [x] User can navigate to view their own profile
..* [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
..* [] User can click on the profile image in any tweet to see another user's profile.
..* [] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
..* [x] Profile view should include that user's timeline
..* [] Optional: User can view following / followers list through the profile

The following advanced user stories are optional:

* [] Robust error handling, check if internet is available, handle error cases, network failures
* [] Advanced: When a network request is sent, user sees an indeterminate progress indicator
* [] Advanced: User can "reply" to any tweet on their home timeline
..* [] The user that wrote the original tweet is automatically "@" replied in compose
* [] Advanced: User can click on a tweet to be taken to a "detail view" of that tweet
..* [] Advanced: User can take favorite (and unfavorite) or reweet actions on a tweet
* [] Advanced: Improve the user interface and theme the app to feel twitter branded
* [] Advanced: User can search for tweets matching a particular query and see results
* [] Bonus: User can view their direct messages (or send new ones)




* Part 1

This is the third assignment for the Android CodePath class.

Time spent: 17h

* Video walkthrough plus debugging:
..* 5 hours
..* Wednesday, 18th
..* 22:30 - 3:20
* Starting on other required
..* 3 hours
..* Thursday, 19
..* 23:00 - 2? although slowed towards the end


Completed required user stories:

* [x] User can sign in to Twitter using OAuth login
* [x] User can view the tweets from their home timeline
..* [x] User should be able to see the username, name, body and timestamp for each tweet
..* [x] User should be displayed the relative timestamp for a tweet "8m", "7h"
..* [x] User can view more tweets as they scroll with infinite pagination
..* [x] Optional: Links in tweets are clickable and will launch the web browser (see autolink)
* [x] User can compose a new tweet
..* [x] User can click a “Compose” icon in the Action Bar on the top right
..* [x] User can then enter a new tweet and post this to twitter
..* [x] User is taken back to home timeline with new tweet visible in timeline
..* [x] Optional: User can see a counter with total number of characters left for tweet
..* [x] Extra: Character count color changes to yellow with <10
characters left to use, to red with no chars left to use
..* [x] Extra: Tweet button is disabled on too many or few charaters.


Walkthrough of all user stories:

![Video Walkthrough of App](anim_simple_twitter.gif)
![Video Walkthrough of Compose Screen](anim_simple_twitter_compose.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).
