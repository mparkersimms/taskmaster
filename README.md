# Task Manager


## Daily Change Log

- 4/26/21 :
    - Set up home page, add tasks page and all tasks page. Added navigation between the pages, functionality to the "Add Task" button.
    "Submitted!" is displayed when the button is pushed.

- 4/27/21
    - Set up the settings page using shared preferences to save the username and apply it to the settings page and the home page.
    - Set up the Task Detail page, and created three buttons on the home page all of which take the user to the taskItem detail page.
        - Each of the buttons displays a different taskItem title, and displays that title on the taskItem detail page when clicked.

-4/28/21
    - Implemented a RecyclerView on the home page that displays the hardcoded tasks, and takes the user to the taskItem detail page when clicked on.

-4/29/21
    - Implemented Room database usage in the app. Store Task instances in the database, populating the RecyclerView on the home page with taskItem titles from
    database, and then displaying the taskItem title, description, and state on the Task Detail page.

-5/7/21
    - Started the process to get the app on the google play store. App is in internal testing phase right now.

- 5/10/21
    - implemented Cognito into the app. There is now the functionality to sign up, and login/logout in the app using cognito through AWS/Amplify

-5/11/21
    - implemented S3 into the app today. Setup the ability to upload a file from the phone of the user into the Add a Task page. That
    image then saves to S3 when the button is pushed. The image is then displayed on the detail page for that task if the image exists in the
    database.

- 5/13/21
    - added anayltics implementations

    -5/17/21
    - added the functionality to upload and share a picture from another app to this app.

-5/18/21
    - implemented location services in the app
## Screenshots

- 4/26/21
![homepage](screenshots/Day1/HomePage.png)
![AddTaskPage](screenshots/Day1/AddTaskPage.png)
![SubmittedTextView](screenshots/Day1/SubmittedTextView.png)
![AllTasksPage](screenshots/Day1/AllTasksPage.png)

- 4/27/21
![homepage](screenshots/Day2/HomePage.png)
![SettingsPage](screenshots/Day2/SettingsPage.png)
![TaskDetailPage1](screenshots/Day2/TaskDetailPage1.png)
![TaskDetailPage2](screenshots/Day2/TaskDetailPage2.png)
![TaskDetailPage3](screenshots/Day2/TaskDetailPage3.png)

- 4/28/21
![homepage](screenshots/Day3/homepage.png)

- 5/7/21
![homepage](screenshots/Day8/homepage.png)
![SettingsPage](screenshots/Day8/settings.png)
![AddTaskPage](screenshots/Day8/addtaskpage.png)
![TaskDetailPage](screenshots/Day8/taskdetailpage.png)

- 5/13/21

![analytics](screenshots/analytics.png)
![notifications](screenshots/notificationsonapp.png)



