# Java Library Management - eclipse MacOS

<h3 align="center">
  <img src="https://cdn.dribbble.com/users/1010637/screenshots/11280628/media/221251d3281dd7770a2a78abc8bdcc78.png?compress=1&resize=300x225">
</h3>

# Feature?
+ Book: view books in lib, add, search, delete.
+ Reader: view readers registed, add, search, delete.
+ Management borrowed and returned books: create a borrowed and returned form, view detail of form, comfirm form, delete form, search form in DB.

> UI of project is vietnamese.

# **Purpose** of this project?
> + Learn how connect to DB.
> + Create basic query to DB.
> + Build a basic UI. (get used to using JDialog, JTable, JPanelScoll, JButton, JLabel, JTextField)
> + Learn how pass data to other class. 

# **Prerequire** to run successfully this project:
+ A mysql installed (search Microsoft website instruction install mysql on MacOS).
+ A eclipse installed.
+ A MySQLWorkBench installed. (you still be able to run this project to take a look at the UI with no need MySQLWorkBench).
+ A mysql Connector file was locate in main repo, in eclipse, you must config path from eclipse to mysql Connector file to have the ability to connect to DB

# How to **run this project**?
1. Clone and open project in eclipse, click run with Frame1.java as entry file of app.

# This project contain some trash file not relate to Java but this all file you should **concern** to:
+ Frame1.java (entry file)
+ InsertReader.java (reader management).
+ DetailForm.java (view detail of a borrow book form).
+ InsertBookForm.java (manage the book in library and can insert book in to library).
+ RentManagement.java (manage, create borrow book form).
+ StaffManagement.java (this file was name wrong, it manage borrow form).

# How to **connect database**?
### Follow step by step:
1. Create a database in MySQLWorkBench, remember your username and password.
2. Check all file in app which create model of Database, you will find the config to make query to DB, depend on the (insert into Table ..) query you can create a correct table in MySQLWorkBench.
3. Make sure your DB server in run, check it, if not, access System Reference, click mysql icon at the bottom (appear when your Mac installed MySQL).
4. If you did create and run a correctly DB, fake some row of data and run the app to check results. If not, the console may be help you to fix your DB.

# How to **use** this app in UI?
Find it yourself.

# Cannot find **how to use this app** properly?
if you cannot know how to use a feature in the UI after atleast 100 times try, **contact me**: [vudat81299@gmail.com]. I will help you.
