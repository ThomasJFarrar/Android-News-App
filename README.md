# Android News App

## Introduction
My news app updates you on the latest happenings in your chosen country. You can easily access news articles from top news outlets and local publications. Browse through articles with ease, save your favorite ones for later, and access them anytime. You will need to generate an API key, so there are instructions in ```strings.xml```.

## Design Rationale
### **There are multiple screens, and users can navigate between screens**
I created four activities: ```MainActivity``` shows news articles in a card-based layout, ```DetailsActivity``` provides more information about an article, ```SavedActivity``` displays saved news articles, and ```SettingsActivity``` lets users change app settings. Users can navigate between ```MainActivity``` and ```SavedActivity``` to ```DetailsActivity``` by clicking on an article. The menu in the top right of the screen provides access to ```SavedActivity``` and ```SettingsActivity```, which contains a ```SettingsFragment``` which uses a PreferenceScreen to display the settings. To return to ```MainActivity```, users can click the back arrow in the top left.

### **Use both explicit and implicit intents**
The app uses explicit intent to start specific activities within the app, such as clicking the menu option to start ```SavedActivity``` or ```SettingsActivity```. An example of where the app uses implicit intent is when the device's web browser is opened when they click the url link in ```DetailsActivity```.

### **Has Menus**
I added an Options Menu to enable users to navigate between the news feed, saved articles, and settings screens. The menu is located in the top right corner of the screen and contains an item for each page. I handled click events for the two options to direct users to the respective screen. Clicking on an item directs the user to the respective screen, providing easy access without obstructing the news articles on the rest of the screen.

### **Use RecyclerView**
I utilised a RecyclerView to display a dynamic list of news articles in both the main news feed and saved news articles. I chose to use a RecyclerView since it is ideal when users scroll down the list of news articles, as the view does not get destroyed, but instead gets reused, making memory usage more efficient, and improving the app's performance.

### **Use Data Storage**
The app stores user preferences and saved news articles efficiently. Saved articles are written to a JSON string in a text file called ```saved.txt``` and retrieved when the saved articles screen appears. Users can remove articles, and the file is updated accordingly. I used internal storage as it is faster and always available. For settings, I used SharedPreferences to store key-value data, allowing users to change news source and toggle notifications. SharedPreferences was the perfect choice as it is lightweight and easy to implement. Both storage methods persist after closing and reopening the app.

### **Use Internet**
The app fetches news articles from an API called [NewsData.io](https://newsdata.io/), using the HTTP client [Retrofit](https://square.github.io/retrofit/) to make the API calls and automatically parse the JSON responses. NewsData.io provides vast amounts of content, including information about the latest news articles from a range of countries, so I didn't have to gather this information myself.

### **The app opens and the screens display properly when the device has no connection to the Internet**
Before making an API call, the app uses ConnectivityManager to make a query about the state of network connectivity. If the user is not connected to the internet, a toast appears informing the user, and no articles are displayed in the news feed. However, the user can still access all their saved articles, settings, and modify them without any issues.

## Novel Features
### **Notifications**
The notifications feature is designed to automatically send out a message to the user 12 hours after closing the app to read the latest news and stay up to date, in order to increase user retention. This is done with NotificationManager and AlarmManager to schedule the notification to appear after the duration. Notifications can be toggled on and off in the setting screen.

### **Swipe to Refresh**
The user can swipe down on the main news feed and saved articles screen to refresh. If on the news feed, the app will make another API call to fetch the latest articles. If on the saved articles screen, refreshing will update any changes made, such as unsaving a saved article or if a new saved article was added.

## Challenges and Future Improvements
### **Challenges**
One of the first challenges encountered was displaying an image into the app from a URL. Each article from the API response included a URL to an image related to the content of the article. Luckily, I was able to use a caching library for Android called [Picasso](https://square.github.io/picasso/) to download the image from the URL and display it within the app easily. My second major challenge was finding the best way to store news articles. I first thought of using SharedPreferences but it only works for key-value data. Although a database was an option, internal storage was more practical for what I wanted to achieve. Eventually, I realized I could save the articles in JSON format to internal storage, allowing me to reuse code from the news feed to saved articles.

### **Future Improvements**
One potential improvement for the news app would be to upgrade the API or gather news articles automatically. Currently, because the app uses NewsData.io for free, the news feed is limited to 10 articles per API request, and users cannot see news from the past. Additionally, not every news article has an image with it, so some articles on the news feed just have a placeholder image. The app would look better if these were filtered out, or if relevant images could be found automatically. Another improvement would be to have a better data storage system for the user's saved articles. A text file was convenient and easy to implement; it works well on a small scale however, as the amount of data grows, reading and writing to a large text file becomes slow and inefficient. Text files are also vulnerable to unauthorised access and manipulation, which is a security risk. A solution could be to implement a database, as it would be more robust, scalable, and secure. 