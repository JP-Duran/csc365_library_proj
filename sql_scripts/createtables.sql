create table Users (
cardNum VARCHAR(16) primary key,
uname VARCHAR(25),
upassword VARCHAR(25) );

create table Libraries (
lid integer primary key auto_increment,
llocation VARCHAR(25) );

create table Employees (
empid VARCHAR(25) primary key,
lid integer,
emppassword VARCHAR(25),
foreign key(lid) references Libraries(lid) );

create table Books (
isbn VARCHAR(10) primary key,
bname VARCHAR(25),
genre VARCHAR(25),
lid integer,
cardnum VARCHAR(16),
available boolean,
foreign key(lid) references Libraries(lid),
foreign key (cardnum) references Users(cardnum) );

#create table Holds (
#hid integer primary key auto_increment,
#isbn VARCHAR(10),
#cardnum VARCHAR(16),
#hdate DATE,
#foreign key(isbn) references Books(isbn),
#foreign key(cardnum) references Users(cardnum) );

#drop table Holds;