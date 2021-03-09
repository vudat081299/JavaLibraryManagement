# JavaLibraryManagement on MacOS
Run with mysqlworkbench, eclipse on macos.
I define a constant config for database in every source files which has function interact with database, so if you want to run this project it maybe a little bit confuse but do not worry, follow step below:
Open workspace to take a look.
Everything you need to focus is 
* Make sure you install mysql on your macOS -> search google, in microsoft website has tutorial for install this in macOS, just 5'
1. In this repo has a sql connector file, you should set link from eclipse to this connector file -> search on google to connect in eclipse, just 2'.
2. Open mysqlworkbench create database, remember name and password.
3. You did have a database but not table, this step is easy but you need to read code, find every table i created and it is easy to find the field of table, base on what you found, create table in mysqlworkbench -> you should make should db server it's running to allowd create. if you cannot, open setting on your macos and you will see MySQL icon, open it and run database server.
4. After create full table, save it and run this project at main file
Require database connnector file and should place and set right link file.

