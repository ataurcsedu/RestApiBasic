# RestApiBasic
This is a very basic operation with spring oauth2 implementation.
Here I want to show how to access a specific restricted resource and secured with oauth2 spring security.
To runnable this project,

1) Download the project.
2) Create database "springrestsampledb" and create table given in sql file in mysql database.
3) Start mysql database.
4) This is maven project. So clean and build the project. In my case I used Netbeans editor and import the porject.
5) Attach Tomcat server in the project from project properties (in Netbeans).
6) Run the project.

To test the project follow the steps,
1) Open postman (nothing but a requester tool) and put the url http://localhost:8080/TestSpring/oauth/token?grant_type=password&username=munna&password=munna
2) Select method type POST.
3) Select Authorization Tab and from the drop down select Basic Auth
4) put username "myRestClient" and password "P@ssw0rd".
5) Hit the send button and if everything is ok, then you found a access_token along with refresh_token, scope and expires_in.
6) We want to get all user list from db. so we send a request to server with that access token.
7) put the url (http://localhost:8080/TestSpring/api/users?access_token=) in postman with access_token you found in just steps 5.
8) Select Request method to GET and hit the send button. If everything is ok, then you found a list of user.

N.B: Its not a complete project, but for first step how spring security oauth2 works can be seen here.
