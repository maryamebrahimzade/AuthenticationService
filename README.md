# Simple Authentication Service Java Test
This application performs the process of user authentication and authorization and receiving the integer and responding to the corresponding Fibonacci sentence 
and receiving epoch time and responding the next N intervals.

### List of used packages
1. The infrastructure of this app is from Spring Security based on jwt token

2. The jjwt and spring-security packages has been used.

3. Redis is used to cache ID tokens and Fibonacci data

### Requirements needed to devate the project
1. To run this application,simply copy the path of the exceptions_fa_IR.properties file and paste it in the RestExceptionHandler class.
2. To run this application,first you need to specify your username and password for connecting to the database in the application.yml file.

### API List:
|         API name         |         Input Parameters         |                  description                   |
|:------------------------:|:--------------------------------:|:----------------------------------------------:|
|      register User       |             UserDto              |             /api/v1/auth/register              |
|        login User        |             LoginDto             |               /api/v1/auth/login               |
|       logout User        |          request header          |              /api/v1/user/logout               |   
|      token validity      |          request header          |             /api/v1/token/is-valid             |
|     fibonacci series     | request header and request param |           /api/v1/user/calc?number=            |
| next intervals of a time |             TimeDto              |             /api/v1/user/intervals             |
 
 

