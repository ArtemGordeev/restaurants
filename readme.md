## Task

-----------------------------

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and **README.md with API documentation and curl commands to get data for voting and vote.**

-----------------------------
P.S.: Make sure everything works with latest version that is on github :)

P.P.S.: Asume that your API will be used by a frontend developer to build frontend on top of that.

-----------------------------

#### Configurations:

- URL: http://localhost:8080/graduation/
- Aplication context: /graduation

-----------------------------

## Dish
#### Get by id:
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/100002/menus/100003/dishes/100004
- Data parameters: none
- Success Response: 200 OK, Content: {"id":100004,"description":"Pizza","price":150,"menu":null}
- curl -s http://localhost:8080/graduation/rest/restaurants/100002/menus/100003/dishes/100004 --user admin@gmail.com:admin

#### Get all:
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/100002/menus/100003/dishes
- Data parameters: none
- Success Response: 200 OK, Content: [{"id": 100005,"description": "Tea","price": 50,"menu": null}, …]
- curl -s http://localhost:8080/graduation/rest/restaurants/100002/menus/100003/dishes --user admin@gmail.com:admin

#### Delete by id (admin only):
- Method: DELETE	
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003/dishes/100004
- Data parameters: none
- Success Response: 204 No Content
- curl -s -X DELETE http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003/dishes/100004 --user admin@gmail.com:admin

#### Update (admin only): 
- Method: PUT 	
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003/dishes/100004
- Data parameters: {"id": 100004, "description": "NewPizza", "price": 150}
- Success Response: 204 No Content
- curl -s -X PUT -d "{\"id\": 100004, \"description\": \"NewPizza\", \"price\": 150}" -H "Content-Type: application/json" http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003/dishes/100004 --user admin@gmail.com:admin

#### Create (admin only):
- Method: POST 	
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003/dishes
- Data parameters: {"id": null, "description": "NewPizza", "price": 150}
- Success Response: 201 Created, Content: {"id": 100006,"description": "NewPizza","price": 150,"menu": null}
- curl -s -X POST -d "{\"id\": null, \"description\": \"NewPizza\", \"price\": 150}" –H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003/dishes --user admin@gmail.com:admin

-----------------------------

## Menu
#### Get by id: 
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/100002/menus/100003
- Data parameters: none
- Success Response: 200 OK, Content: {"id": 100003,"title": "Monday","dishes": null,"restaurant": null,"date": "2020-04-01"}
- curl -s http://localhost:8080/graduation/rest/restaurants/100002/menus/100003 --user admin@gmail.com:admin

#### Get all:
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/100002/menus
- Data parameters: none
- Success Response: 200 OK, Content: [{"id": 100003,"title": "Monday","dishes": null,"restaurant": null,"date": "2020-04-01"}, …]
- curl -s http://localhost:8080/graduation/rest/restaurants/100002/menus --user admin@gmail.com:admin

#### Get today’s menu with dishes
- Method: GET 	
- URL: http://localhost:8080/graduation/rest/restaurants/100002/menus/today
- Data parameters: none
- Success Response: 200 OK, Content: {"id": 100003,"title": "Monday","dishes": [{"id": 100004,"description": "Pizza","price": 150},{"id": 100005,"description": "Tea","price": 50}],
"restaurant": null,"date": "2020-05-16"}
- curl -s http://localhost:8080/graduation/rest/restaurants/100002/menus/today --user admin@gmail.com:admin

#### Delete by id (admin only):
- Method: DELETE		
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003
- Data parameters: none
- Success Response: 204 No Content
- curl -s -X DELETE http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003 --user admin@gmail.com:admin

#### Update (admin only): 
- Method: PUT 	
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003
- Data parameters: {"id": 100003, "title": "Sunday", "dishes": null, "restaurant": null, "date":"2020-04-01"}
- Success Response: 204 No Content
- curl -s -X PUT -d "{\"id\": 100003, \"title\": \"Sunday\", \"dishes\": null, \"restaurant\": null, \"date\":\"2020-04-01\"}" -H "Content-Type: application/json" http://localhost:8080/graduation/rest/admin/restaurants/100002/menus/100003 --user admin@gmail.com:admin

#### Create (admin only):
- Method: POST 	
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002/menus
- Data parameters: {"id": null, "title": "Sunday", "dishes": null, "restaurant": null, "date": "2020-05-01"}
- Success Response: 201 Created, Content: {"id": 100007,"title": "Sunday","restaurant": null,"date": "2020-05-01"}
- curl -s -X POST -d "{\"id\": null, \"title\": \"Sunday\", \"dishes\": null, \"restaurant\": null, \"date\": \"2020-05-01\"}" –H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/graduation/rest/admin/restaurants/100002/menus --user admin@gmail.com:admin

-----------------------------

## Restaurant
#### Get by id: 
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/100002
- Data parameters: none
- Success Response: 200 OK, Content: {"id": 100002,"title": "KFC","menus": null,"votes": null}
- curl -s http://localhost:8080/graduation/rest/restaurants/100002 --user admin@gmail.com:admin

#### Get all:
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/
- Data parameters: none
- Success Response: 200 OK, Content: [{"id": 100002,"title": "KFC","votes": 0}, …]
- curl -s http://localhost:8080/graduation/rest/restaurants --user admin@gmail.com:admin

#### Get all with today menu:
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/today
- Data parameters: none
- Success Response: 200 OK, Content: [{"id": 100002,"title": "KFC","menus": [{"id": 100003,"title": "Monday","dishes": [{"id": 100004,"description": "Pizza","price": 150},{"id": 100005,
"description": "Tea","price": 50}],"date": "2020-05-16"}],"votes": null}]
- curl -s http://localhost:8080/graduation/rest/restaurants/today --user admin@gmail.com:admin

#### Get winner:
- Method: GET	
- URL: http://localhost:8080/graduation/rest/restaurants/winner
- Data parameters: none
- Success Response: 200 OK, Content: {"id": 100002,"title": "KFC","votes": 1}
- curl -s http://localhost:8080/graduation/rest/restaurants/winner --user admin@gmail.com:admin

#### Delete by id (admin only):
- Method: DELETE		
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002
- Data parameters: none
- Success Response: 204 No Content
- curl -s -X DELETE http://localhost:8080/graduation/rest/admin/restaurants/100002 --user admin@gmail.com:admin

#### Update (admin only): 
- Method: PUT
- URL: http://localhost:8080/graduation/rest/admin/restaurants/100002
- Data parameters: {"id": 100002, "title": "Burger King", "menus": null, "votes": null}
- Success Response: 204 No Content
- curl -s -X PUT -d "{\"id\": 100002, \"title\": \"Burger King\", \"menus\": null, \"votes\": null}" -H "Content-Type: application/json" http://localhost:8080/graduation/rest/admin/restaurants/100002 --user admin@gmail.com:admin

#### Create (admin only):
- Method: POST 	
- URL: http://localhost:8080/graduation/rest/admin/restaurants/
- Data parameters: {"id": null, "title": "Burger King", "menus": null, "votes": null}
- Success Response: 201 Created, Content: {"id": 100006,"title": "Burger King"}
- curl -s -X POST –d "{\"id\": null, \"title\": \"Burger King\", \"menus\": null, \"votes\": null}" –H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/graduation/rest/admin/restaurants/ --user admin@gmail.com:admin

-----------------------------

## Vote
#### Get all user’s votes:
- Method: GET 
- URL: http://localhost:8080/graduation/rest/users/100000/votes
- Data parameters: none
- Success Response: 200 OK, Content: [{"id": 100007,"date": "2020-05-03","time": "15:22:59","restaurant": "KFC","user": null}]
- curl -s http://localhost:8080/graduation/rest/users/100000/votes --user admin@gmail.com:admin

#### Vote for restaurant:
- Method: POST	
- URL: http://localhost:8080/graduation/rest/vote/100002
- Data parameters: none
- Success Response: 204 No Content
- curl -s -X POST http://localhost:8080/graduation/rest/vote/100002 --user admin@gmail.com:admin

-----------------------------

## User
#### get All Users
- curl -s http://localhost:8080/graduation/rest/admin/users --user admin@gmail.com:admin

#### get Users 100001
- curl -s http://localhost:8080/graduation/rest/admin/users/100001 --user admin@gmail.com:admin

#### register Users
- curl -s -i -X POST -d "{\"name\":\"New User\",\"email\":\"test@mail.ru\",\"password\":\"test-password\"}" -H "Content-Type:application/json;charset=UTF-8" http://localhost:8080/graduation/rest/profile/register

#### get Profile
- curl -s http://localhost:8080/graduation/rest/profile --user test@mail.ru:test-password



