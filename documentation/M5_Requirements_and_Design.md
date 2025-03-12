# M4 - Requirements and Design

## 1. Change History

| Date      | Modification Description                                                                                                                        | Rationale                                                                                                                                                                 |
| --------- | ----------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 2025.3.1  | 4.0 Sequence Diagrams                                                                                                                           | Update and improve sequence diagrams based on the modification in design                                                                                                  |
| 2025.3.1  | 4.0 Dependency Diagram: Correct the arrows to firebase and google auth. fix loop between location and notification manager                      | During implementation, the relationships between ocmponents became very clear. Updated diagram to reflect this.                                                           |
| 2025.3.1  | 4.0 main components: Added REST api routes instead of java funtion signatures                                                                   | REST api routes are more clear in terms of URI, req/res/body which better helps explain the interactions between client and server.                                       |
| 2025.3.1  | 3.3 Functional Requirements: removed FRs we dont need. Added NFRs such as polling average latency which directly describe our app's performance | Some of our FRs were redundant and this was realized during implementation. Since out app uses polling, an NFR to test average polling latency is an effective indicator. |
| 2025.3.1  | 3.1 Use case diagram: Modified Use cases                                                                                                        | Our use cases did not matc FRs.                                                                                                                                           |
| 2025.3.12 | Non-Functional Requirements: Modified NFR1 and NFR2                                                                                             | Change Non-Functional Requirements to something measureable and more specific                                                                                             |

---

## 2. Project Description

**Project Name:** CMIYC - Cache Me If You Can!

**Target Audience:** Individuals who enjoy spontaneous meetups with friends but struggle with coordination or discovering who is nearby and available. Target users include young adults, college students, and professionals who value real-time social connections.

**Problem Statement:** Group chats and manual check-ins are inefficient and frustrating, often leading to missed opportunities for meetups.

**Solution:** "Cache Me If You Can!" allows users to share their real-time location with selected friends, enabling seamless coordination for spontaneous hangouts. The app promotes privacy, spontaneity, and immediate engagement.

**Key Features:** Location-sharing with privacy controls, notifications to update friends, log of status messages from friends.

---

## 3. Requirements Specification

### 3.1. Use-Case Diagram

<img src="./images/M4/Use-Case.png" alt="My Image" width="100%"/>

### 3.2. Actors Description

1. **User**: A person who uses the app to share their real-time location and update their status and view their friend’s location.
2. **Admin**: A person who can have access to block users

### 3.3. Functional Requirements

#### **FR1: Login with Google Authentication**

- **Overview**:

  1. Google Login
  2. Log out

- **Detailed Flow for Each Independent Scenarios**:

  - **FR1_1**: Google Login

    - **Description**: When no session is active, new and existing users use Google Authentication service to start a session.
    - **Primary Actors**: User, admin
    - **Main success scenario(s)**:
      1. New and existing users click on the Google login button on the App’s login page.
      2. User is redirected to a page view where the user enters their Google email and password.
      3. Google Authentication succeeds.
      4. User is redirected out of the login page and into the app’s main page: The map page.
    - **Failure scenario(s)**:
      - 2a. Network error while contacting Google Auth services
        - 2a1. Display the following: “Login Error: Network Error during Registration
        - 2a2. When the OK button is clicked, User is redirected back to the login page.
      - 3a. Google authentication fails.
        - 3a1. Users can retry entering their Google email and password.

  - **FR1_2: Logout**
    - **Primary actor(s)**: User, admin
    - **Main success scenario(s)**:
      1. From a profile page, the user clicks logout.
      2. User logs out successfully.
      3. User is redirected to the login page.
    - **Failure scenario(s)**:
      - 1a. Logout failed; user stays logged in due to network error.
        - 1a1. Display error to logout message and show retry button.

#### **FR2: Manage Friends**

- **Overview**:

  1. Add Friend
  2. Remove Friend
  3. Respond to Friend Requests

- **Detailed Flow for Each Independent Scenarios**:

  - **FR2_1: Add Friend**

    - **Description**: Users can add friends by sending friend requests.
    - **Primary actor(s)**: User, admin
    - **Success scenario(s)**:
      1. User clicks the friends button to access the friend page
      2. User clicks the add friend button
      3. User enters friend’s email address
      4. User submit the friend request
    - **Failure scenario(s)**:
      - 4a. Sending friend request fails due to a network error.
        - 4a1. Display the error message: “Error: Network error while sending friend request”
        - 4a2. User clicks the only option “ok”
        - 4a3. User redirected back to friend request dialog box
      - 4b. Friend has never registered in the app
        - 4b1. Display “Failed to send friend request: 404”
        - 4b2. User clicks the only option “ok”
        - 4b3. User redirected back to friend request dialog box

  - **FR2_2: Remove Friend**

    - **Description**: Users can remove friends from their friend list at any time.
    - **Primary actor(s)**: User, admin
    - **Success scenario(s)**:
      1. User navigates to friends page
      2. User clicks on the button beside the friend to remove them.
      3. The friend is removed from the user's friend list.
      4. The user is also removed from the user's friend list.
    - **Failure scenario(s)**:
      - 2a. Network error when removing friends.
        - 2a1. Display the error message “Network error: failed to remove friend”.
        - 2a2. User clicks the only option “ok”.
        - 2a3. User is redirected back to friends page.

  - **FR2_3: Respond to Friend Requests**
    - **Description**: The user can accept or deny friend requests.
    - **Primary actor(s)**: User, admin
    - **Success scenario(s)**:
      1. User receives a friend request.
      2. User navigates to friends page
      3. User clicks the friend requests icon
      4. Friend requests to the User are displayed
      5. If the user accepts, that friend is added to their friend list and the friend’s list adds the user.
      6. If the user denies, the friend request is cleared.
    - **Failure scenario(s)**:
      - 5a. Network error when accepting or declining friend requests.
        - 5a1. Display the error message: “Network error while accepting/declining friend request”.
        - 5a2. User clicks the only option “ok”.
        - 5a3. User is redirected back to friend requests dialog box

#### **FR3: View Friends’ Location**

- **Overview**:

  1. Show Friends’ Locations

- **Detailed Flow for Each Independent Scenarios**:

  - **FR3.1 Show Friends’ Locations**
    - **Description**: Users can view their friend’s location.
    - **Primary actor(s)**: User, admin
    - **Main success scenario**:
      1. The user opens the app.
      2. The user can drag and navigate the map.
      3. The user sees their friends' locations markers.
      4. Google profile image is displayed as their markers.
    - **Failure scenario(s)**:
      - 3a. Home screen fails to get friend location updates in 20 retries due to network error.
        - 3a1. Display the error message: “Connection issue: The app will continue trying to update in background”
        - 3a2. Continue retrying every 5 seconds until the user signs out of, leaves the home screen or closes the app.

#### **FR4: Broadcast Activity**

- **Overview**:

  1. Set activity message & send a real-time push notification to all nearby friends
  2. Post a new log to all friends' log list

- **Detailed Flow for Each Independent Scenarios**:

  - **FR4.1 Set activity message & send a real-time push notification to all nearby friends**

    - **Description**: From the map page, users can enter their activity message and click on broadcast to let all their friends know.
    - **Primary actor(s)**: User, admin
    - **Main success scenarios**:
      1. The user is on the map page.
      2. User clicks “Broadcast.”
      3. In the dialog box, User enters current activity
      4. User clicks update
      5. The user’s current activity is sent via push notifications to the user’s friends within 1 km radius.
      6. Nearby friend receives push notification.
      7. Friend clicks on the notification and is redirected to log screen
    - **Failure scenarios**:
      - 4a. Broadcasting fails due to network error.
        - 4a1. 4a1. Display network error message: “Your broadcast may not have been sent due to a network error. Your friends might not receive this update.”
        - 4a2. User clicks ok and is redirected back to broadcast dialog box

  - **FR4.2 Post a new log to all friends’ log list**
    - **Description**: When a user broadcasts activity, a new log entry will be added to all friends’ activity logs.
    - **Primary actor(s)**: User, admin
    - **Pre-Condition**: User has successfully broadcasted activity to nearby friends
    - **Main success scenarios**:
      1. Nearby friend opens activity log page
      2. A new entry contains the broadcaster’s name, activity message, broadcaster’s location when they broadcasted, time and date of broadcast.
    - **Failure scenarios**:
      - 2a. Refreshing log page fails due to network error.
        - 2a1. App retries getting the updated log list every 30 seconds for 5 retries.
        - 2a2. If retries exceed, show the error message: “Sync problem, the app will continue to retry in the background” until the user leaves log screen or closes the app

#### **FR5: View Activity Logs**

- **Overview**:

  1. User views the activity logs.

- **Detailed Flow for Each Independent Scenarios**:

  - **FR5.1 View Notifications**
    - **Description**: The user can view a list of all activity logs received from their friends.
    - **Primary actor(s)**: User, admin
    - **Main success scenario**:
      1. The user switches to the “Activity Log” page
      2. The user can scroll up and down the log to see all received activity logs.
      3. The logs are sorted from most recent to least recent
    - **Failure scenario(s)**:
      - 1a. Network error while fetching activity log
        - 1a1. Display the “Sync Problem” Alert to ask user to check internet connection
        - 1a2. When the OK button is clicked, App will keep trying to fetch the activity log

#### **FR6: Manage Users**

- **Overview**:

  1. Ban User
  2. View All Users

- **Detailed Flow for Each Independent Scenarios**:

  - **FR6.1 Ban User**

    - **Description**: Admins can permanently ban users who engage in harmful behavior, ensuring a safe environment. The criteria for banning users is up to the admin. It could be because someone is using the app in a harmful manner as if they are stalking their friends.
    - **Primary actor(s)**: Admin
    - **Pre-condition(s)**: User is an admin. For testing purposes, any user can become an admin by clicking on the “Test admin” checkbox in the login page.
    - **Main success scenarios**:
      1. Admin can see admin page icon in home/map page
      2. Admin clicks on admin page icon
      3. Admin view all users
      4. Admin clicks on ban user icon beside a user
      5. The user is banned. Future log in attempts display “You are banned, contact support
      6. User can only click on exit to exit the app.
    - **Failure scenarios**:

      - 3a. Network error while retrieving all users.
        - 3a1. Display error “Network error while retrieving users”
        - 3a2. 3a2. Admin click ok, and the app will continue retry
      - 4a. Network error while banning user
        - 4a1: Display error “Network error while banning users”
        - 4a2: Admin clicks ok, and is redirected back to view all users.

  - **FR6.2 View All Users**
    - **Description**: The admin can view a list of all users currently registered to the app.
    - **Primary actor(s)**: Admin
    - **Pre-condition(s)**: User is an admin. For testing purposes, any user can become an admin by clicking on the “Test admin” checkbox in the login page.
    - **Main success scenarios**:
      1. Admin can see admin page icon in home/map page.
      2. Admin clicks on admin page
      3. Admin views all users
    - **Failure scenarios**:
      - 3a. Network error while retrieving all users.
        - 3a1. Display error “Network error while retrieving users”
        - 3a2. 3a2. Admin click ok, and the app will continue retry

### 3.5. Non-Functional Requirements

1. **NFR1: App Startup Time**

   - **Description**: The app follows the Android vitals startup suggestions:

     - Cold startup takes 5 seconds or less.
     - Warm startup takes 2 seconds or less.
     - Hot startup takes 1.5 seconds or less.

   - **Justification**: Quick startup ensures a seamless user experience, reducing wait times and improving engagement.

2. **NFR2: Real-Time Performance**

   - **Description**: The app must be designed to handle errors and unexpected conditions gracefully. This includes:

     - Friend Location Refresh: The average latency for refreshing friend locations on the home screen shall be approximately 500 ms (with an acceptable error margin).
     - User Location Update: The average latency to update the user's own location shall be approximately 500 ms (each individual call has a round-trip time of about 100 ms, aggregating to the target value).

   - **Justification**: Near-real-time location updates ensure smooth and coordinated interactions, enhancing the overall user experience.

---

## 4. Design Specification

### 4.1. Main Components

#### **Location Manager**

- **Purpose**: Stores every User’s location and Handles real-time location updates and sharing.
- **Interfaces**:
  - **updateUserLocation(currentLocation: LocationObject): boolean**
    - REST API (client <-> server)
      - URI: PUT /location/{userID}
      - Body: { "currentLocation": {"latitude": Number, "longitude": Number, "timestamp": Number} }
      - Response: 200/OK (location updated successfully)
    - **Purpose**: Updates the user's current location in the database

#### **Notification Manager**

- **Purpose**: Manages sending notifications to friends.
- **Interfaces**:
  - **setFCMToken(fcmToken: String): boolean**
    - REST API (client <-> server)
      - URI: PUT /fcm/{userID}
      - Body: { "fcmToken": String }
      - Response: 200/OK (token set successfully)
    - **Purpose**: Updates the Firebase Cloud Messaging token for a user to enable push notifications
  - **sendEventNotification(eventName: String): boolean**
    - REST API (client <-> server)
      - URI: POST /send-event/{userID}
      - Body: { "eventName": String }
      - Response: 200/OK (notification sent successfully)
    - **Purpose**: Sends notifications to nearby friends (within 1km) about an event the user is starting
  - **getNotifications(): List\<Log\>**
    - REST API (client <-> server)
      - URI: GET /notifications/{userID}
      - Response: 200/OK with array of notification log objects
    - **Purpose**: Retrieves the user's notification history (log list)

#### **User Manager**

- **Purpose**: Manages user-related operations such as creating the user profile.
- **Interfaces**:
  - **createUserProfile(): boolean**
    - REST API (client <-> server)
      - URI: POST /user
      - Body: { “userID”: String, “displayName”: String, “email”: String, “photoURL”: String, “fcmToken”: String, “currentLocation”: {“latitude”, “longitude”, “timestamp”} }
      - Response: {“isAdmin”: boolean, “isBanned”: boolean}
    - **Purpose**: To create a new user profile. Existing profile requests are updated in the DB with any changed information
  - **getUsers(): List\<User\>**
    - REST API (client <-> server)
      - URI: GET /user/:adminUserID
      - Response: 200/OK (successfully created)
    - **Purpose**: To get all users for the admin page. adminUserID is passed for verification on the server side.
  - **banUserProfile(): boolean**
    - REST API (client <-> server)
      - URI: POST /user/ban/:adminUserID
      - Body: { “userID”: String}
      - Response: 200/OK (successfully created)
    - **Purpose**: To ban a user for the admin page. adminUserID is used for verification. userID is the user admin wants to ban

#### **Friend Manager**

- **Purpose**: Manages friend-related operations such as sending and accepting friend requests.
- **Interfaces**:
  - **getFriends(): List\<Friend\>**
    - REST API (client <-> server)
      - URI: GET /friends/{userID}
      - Response: 200/OK with array of friend objects
    - **Purpose**: Retrieves all friends for a specific user
  - **sendFriendRequest(friendEmail: String): boolean**
    - REST API (client <-> server)
      - URI: URI: POST /friends/{userID}/sendRequest/{friendEmail}
      - Response: 200/OK (request sent successfully)
    - **Purpose**: Sends a friend request to another user by email
  - **getFriendRequests(): List\<FriendRequest\>**
    - REST API (client <-> server)
      - URI: GET /friends/{userID}/friendRequests
      - Response: 200/OK with array of friend request objects
    - **Purpose**: Retrieves all pending friend requests for a user
  - **acceptFriendRequest(friendID: String): boolean**
    - REST API (client <-> server)
      - URI: POST /friends/{userID}/acceptRequest/{friendID}
      - Response: 200/OK (request accepted successfully)
    - **Purpose**: Accepts a pending friend request
  - **declineFriendRequest(friendID: String): boolean**
    - REST API (client <-> server)
      - URI: POST /friends/{userID}/declineRequest/{friendID}
      - Response: 200/OK (request declined successfully)
    - **Purpose**: Declines a pending friend request
  - **deleteFriend(friendID: String): boolean**
    - REST API (client <-> server)
      - URI: PUT /friends/{userID}/deleteFriend/{friendID}
      - Response: 200/OK (friend removed successfully)
    - **Purpose**: Removes a friend from the user's friend list

### 4.2. Databases

1. **MongoDB**
   - **User Table**
     - **Purpose**: Stores `userID`, `Name`, `List of friends`, `userPhoto`, and `Last known location`.
     - **Reason**: We need a persistent store of User information and their friends. This data should not be lost when the server is restarted. We chose MongoDB for our project due to its flexibility, scalability, and ability to handle data efficiently. Its schema-less structure allows us to store dynamic user data without rigid migrations, and its JSON-like document structure integrates seamlessly with our Node.js backend, enabling fast development.
   - **Activity Logs Table**
     - **Purpose**: We need a persistent store for every user, the event logs they receive
     - **Reason**: Activity logs are ground truth and should persist between server restarts.

---

### 4.3. External Modules

1. **MapBox Mobile Maps SDKs**

   - **Purpose**: Provides map visualizations for the map page of the app.
   - **Reason**: Users need to see the roads/buildings of their friends’ location pins.

2. **Google Sign In**

   - **Purpose**: Allows the user to log in/sign up.
   - **Reason**: It saves the user time by offering an easier and faster solution to join the app.

3. **Firebase Push Notifications**
   - **Purpose**: Allows the user to receive push notifications from the server.
   - **Reason**: It is used to notify the user of new activities from their friends, or friend requests, etc.

---

### 4.4. Frameworks

1. **MongoDB**

   - **Purpose**: To store user information such as name, profile photo, friends list, etc.
   - **Reason**: The app needs access to the above information to display the user account. It also needs the friends list to display certain friends.

2. **Amazon Web Services EC2**
   - **Purpose**: Hosts the MongoDB and Location Processing services. Additionally, provides flexibility in choosing necessary compute resources.
   - **Reason**: Instead of doing the work and algorithm on the app, the backend services process data in the cloud.

---

### 4.5. Dependencies Diagram

<img src="./images/M4/Dependency-Diagram.png" alt="Dependency Diagram" width="100%"/>

---

### 4.6. Functional Requirements Sequence Diagram

#### **FR1: Authentication using Google**

<img src="./images/M4/FR1.png" alt="FR1 Diagram" width="100%"/>

#### **FR2: Manage Friends**

<img src="./images/M4/FR2.png" alt="FR2 Diagram" width="100%"/>

#### **FR3: View Friends' Location**

<img src="./images/M4/FR3.png" alt="FR3 Diagram" width="100%"/>

#### **FR4: Broadcast Activity**

<img src="./images/M4/FR4.png" alt="FR4 Diagram" width="100%"/>

#### **FR5: Manage User**

<img src="./images/M4/FR5.png" alt="FR5 Diagram" width="100%"/>

#### **FR5: Manage User Profile**

<img src="./images/M4/FR6.png" alt="FR6 Diagram" width="100%"/>

---

### 4.7. Non-Functional Requirements Design

1. **App Startup Time**

   - **Validation**: Measure startup times under various conditions to ensure compliance with the following benchmarks:
   - Cold startup: ≤5 seconds
   - Warm startup: ≤2 seconds
   - Hot startup: ≤1.5 seconds

2. **Real-Time Performance**

   - **Validation**: Conduct comprehensive tests (both unit and integration tests) to ensure that the app delivers a seamless, fluid user experience. Location updates must complete within 500 ms on average and within 2 seconds at worst.

3. **Compatibility**
   - **Validation**: The app should be compatible with Android devices running Android 12 (API 31) and above.

---

### 4.8. Main Project Complexity Design

#### **Component: Location Manager**

- **Description**: Store location data of each user and detect nearby friends efficiently using a quadtree proximity search algorithm. We plan on doing this every single time a broadcast event is called as storing the tree and updating it whenever a location is updated doesn’t make sense, as there will need to be a tree for each set of friends then

- **Why complex?**:

  - The unoptimized version of the location manager involves storing the key-value pair `{UserID : {latitude, longitude}}` for each `UserID`. On location updates, we would update the corresponding value. However, computing if another friend is nearby requires searching all friends’ locations. This linear time computation does not scale well when we have more users with more friends.
  - A quadtree is a tree-like data structure. Each node represents a rectangle on the Earth's surface. Each node has 4 children, each representing a quadrant of the parent. Since we know the broadcaster’s location, we only need to search for friends in one of the nodes above. This greatly improves efficiency as we reduce to a logarithmic time complexity.

  - <img src="./images/M4/quadtree.jpeg" alt="QuadTree Example" width="100%"/>

- **Design**:

  - **Use case**: Find all nearby friends who we should send push notifications to when the user broadcasts activity.
  - **Input**:
    - `userId` of the user broadcasting activity.
    - `Latitude`, `Longitude` of the user broadcasting activity.
  - **Output**:
    - `userId` of User’s friends who are within a 500m radius.
  - **Main computational logic**:
    1. **Define the Quadtree**:
       - The quadtree will store the latitude and longitude of all users.
       - Each node in the quadtree represents a region of the Earth's surface and can split into four sub-quadrants.
    2. **Insert Users into the Quadtree**:
       - For each user, insert their latitude and longitude into the quadtree.
    3. **Query for Nearby Friends**:
       - Given the broadcasting user's latitude and longitude, traverse the quadtree to find all points within a 500-meter radius.

- **Pseudo-code**:

```plaintext
1. Define Quadtree Node:
   - Properties: min_lat, max_lat, min_lon, max_lon, points[], children[]

2. Insert Point into Quadtree:
   - If point not in bounds, return False
   - If node has capacity, add point
   - Else:
     - Split node into 4 quadrants (NW, NE, SW, SE)
     - Reinsert points into children

3. Query Nearby Friends:
   - If node does not intersect query circle, return
   - For each point in node:
     - If distance(point, query) ≤ radius, add to results
   - Recursively query children

4. Distance Calculation (Haversine):
   - Convert lat/lon to radians
   - Compute distance using Haversine formula
   - Return distance in meters

5. Main Logic:
   - Initialize Quadtree with Earth bounds (-90 to 90 lat, -180 to 180 lon)
   - Insert all users into Quadtree
   - Query Quadtree for friends within 500m of broadcasting user
   - Return results
```

---

## 5. Contributions

- **Peter Scholtens**:

  - Design Specification 4.1-4.4
  - Sequence diagram R1, R6
  - Functional Requirements FR2
  - Reviewed All Sections

- **Antonio Qiao**:

  - Functional requirements
  - Non-functional requirements
  - Sequence diagram FR4 and FR5
  - Markdown creation
  - Reflection

- **Deepan Chakravarthy**:

  - Functional Requirements: FR1, FR4
  - Dependency Diagram
  - Main project Complexity Design
  - Review sections

- **James Lee**:
  - Use case diagram
  - Main actors and descriptions
  - Functional Requirements: FR3, FR6
  - Sequence diagram FR2, FR3
  - Reviewed all sections
