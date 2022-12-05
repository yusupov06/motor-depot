# motor-depot


<p align="center">
  <img alt="Survey" src="src/main/webapp/static/images/home.svg">
</p>



<h1>The platform supports only English language.</h1>

# Description
<div>
Motor depot. The Dispatcher distributes Requests for Flights between Drivers, each of whom has his own Car assigned to them.
A Car that is in good condition and whose characteristics correspond to the Application can be assigned to the Flight.
The driver makes a note about the performance of the Race and the condition of the Car.
</div>

# Project stack

<h3> Java EE / PostgreSQL / HTML5 / CSS3 / Bootstrap 5 / JavaScript / Command design pattern </h3>

# Functional roles
<p>
The platform supports the following roles and their corresponded functionality.

|  Permission        | MANAGER | DISPATCHER  | DRIVER |
|  SIGN_IN           |    +    |      +      |    +   |
|  LOG_OUT           |    +    |      +      |    +   |
|  ADD_REQUEST       |    +    |      +      |        |
|  EDIT_REQUEST      |    +    |      +      |        |
|  DELETE_REQUEST    |    +    |      +      |        |
|  SHOW_REQUESTS     |    +    |      +      |        |
|  ADD_CRUISE        |    +    |             |        |
|  EDIT_CRUISE       |    +    |             |        |
|  DELETE_CRUISE     |    +    |      +      |        |
|  SHOW_CRUISES      |    +    |             |    +   |
|  EDIT_CRUISE_STATUS|    +    |             |    +   |
|  ADD_DRIVER        |    +    |             |        |
|  DELETE_DRIVER     |    +    |             |        |
|  EDIT_DRIVER       |    +    |             |        |
|  SHOW_DRIVERS      |    +    |             |        |
|  ADD_CAR           |    +    |             |        |
|  DELETE_CAR        |    +    |             |        |
|  EDIT_CAR          |    +    |             |        |
|  SHOW_CARS         |    +    |             |        |
|  ADD_ROLE          |    +    |             |        |
|  EDIT_ROLE         |    +    |             |        |
|  DELETE_ROLE       |    +    |             |        |
|  SHOW_ROLES        |    +    |             |        |
</p>

# Database schema

PostgreSQL database is used to store data.

<p align="center">
  <kbd> <img alt="Database" src="src/main/webapp/static/images/databaseER.png" style="border-radius:10px"\></kbd> 
</p>
<p align="center">Database schema</p>

## Installation

1. Clone the project.
2. Create a new MySQL database using database.sql from the data folder.
3. Change the app.properties file, located in the resources/properties/ folder, based on your database configurations.
4. Fill up any valid email service's user and password in the mail.properties file, located in the resources/properties/
   folder, for password change functionality.
5. Build the project using maven.
6. Add new Tomcat 9.0.62 configuration to the project.
7. Run Tomcat and open http://localhost:8080/ on the browser.
8. Log in as admin, change password and create new users. Admin default account is admin@admin.com, password - 12345678.

## Usage

<p align="center">
  <kbd> <img alt="Edit survey" src="https://user-images.githubusercontent.com/64004682/180663466-59c3809d-6870-4e19-9d1c-a131d13dc482.gif" width="100%" style="border-radius:10px"\></kbd> 
</p>
<p align="center">Sign in and edit survey</p>
<br>

<p align="center">
  <kbd> <img alt="Survey attempt" src="https://user-images.githubusercontent.com/64004682/180663470-5ce92f47-aba5-45bd-a19c-eb308b620a42.gif" width="100^" style="border-radius:10px"\></kbd> 
</p>
<p align="center">Survey attempt</p>
<br>

<p align="center">
  <kbd> <img alt="View survey result and theme CRUD" src="https://user-images.githubusercontent.com/64004682/180663476-4ab570e0-40ae-4e96-8131-12013bb17fd5.gif" width="100%" style="border-radius:10px"\></kbd> 
</p>
<p align="center">View survey result and work with themes</p>
