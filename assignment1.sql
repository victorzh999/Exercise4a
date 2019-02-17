drop database if exists sampledb;

create database if not exists sampledb;
use sampledb;

create table user(
userID int not null auto_increment,
userName varchar(20) not null,
password varchar(50) not null,
firstName varchar(50) not null,
lastName varchar(50) not null,
email varchar(100) not null,
gender varchar(20),
age int,
isRoot int default 0,
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (userID),
unique user_userName_unique (userName),
unique user_email_unique (email)
) auto_increment = 1;

create table joke (
jokeID int not null auto_increment,
userID int,
title varchar(100) not null,
description text,
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (jokeID),
FOREIGN KEY FK_joke_userID (userID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE
-- , check( 5 >= (select count(1) from joke where date(createddate) =  date(current_timestamp()) group by userID order by 1 desc limit 1))
) auto_increment = 1;



create table joke_tag(
jokeID int not null,
tag varchar(50),
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (jokeID, tag),
FOREIGN KEY (jokeID) REFERENCES joke(jokeID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
check (tag not like '% %'),
check (binary tag = binary lower(tag))
);

create table user_favorite(
userID int not null,
type varchar(20),
favorite int,
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (userID, type, favorite),
FOREIGN KEY (userID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
check (type in ('friend', 'joke'))
);

create table joke_review(
reviewerID int not null,
jokeID int not null,
reviewUsername varchar(20) not null,
score varchar(10) not null check(score in ('excellent', 'good', 'fair', 'poor')),
remark varchar(1000),
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (reviewerID, jokeID),
FOREIGN KEY (reviewUsername) REFERENCES user(username) 
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
FOREIGN KEY (reviewerID) REFERENCES user(userID) 
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
FOREIGN KEY (jokeID) REFERENCES joke(jokeID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE
-- ,check(5 >= (select count(1) from joke_review where date(createdDate) = date(current_timestamp()) and reviewUsername =  substring_index(user(), '@', 1) group by reviewUsername) = 5)
);

create table blacklist(
userID varchar(20) not null,
createdDate datetime default current_timestamp(),
PRIMARY key (userID)
);

insert into user(
userName, password, firstName, lastName, email, isRoot
)
values
('root', 'pass1234', 'vzhang', 'man', 'root@hotmail.com', 1)
,('john', 'pass1234', 'root', 'man2', 'man@hotmail.com', 0)
,('root1', 'pass1234', 'john1', 'man', 'root1@hotmail.com', 0)
,('john1', 'pass1234', 'root1', 'man2', 'man1@hotmail.com', 0)
,('root2', 'pass1234', 'john2', 'man', 'root2@hotmail.com', 0)
,('john2', 'pass1234', 'root2', 'man2', 'man2@hotmail.com', 0)
,('root3', 'pass1234', 'john3', 'man3', 'root3@hotmail.com', 0)
,('john3', 'pass1234', 'root3', 'man3', 'man3@hotmail.com', 0)
,('root4', 'pass1234', 'john4', 'man', 'root4@hotmail.com', 0)
,('john4', 'pass1234', 'root4', 'man2', 'man4@hotmail.com', 0)
,('vzhang', 'victor1234', 'vzhang', 'man', 'vzhang@hotmail.com', 0)
;

insert into joke (
userID, title, description
)
values
(11, 'joke_vzhang', 'this is a normal joke')
,(3, 'joke1', 'this is a fancy joke')
,(3, 'joke2', 'this is a very intereasting joke i heard')
,(4, 'joke3', 'this is a boring joke i heard')
,(3, 'joke4', 'this is a very intereasting joke i heard')
,(4, 'joke5', 'this is a boring joke i heard')
,(2, 'joke6', 'this is a very intereasting joke i heard')
,(2, 'joke7', 'this is a boring joke i heard')
,(2, 'joke8', 'this is a very intereasting joke i heard')
,(5, 'joke9', 'this is a boring joke i heard')
,(6, 'joke210', 'this is a very intereasting joke i heard')
,(3, 'joke11', 'this is a fancy joke')
,(3, 'joke12', 'this is a very intereasting joke i heard')
;

insert into joke_tag(
jokeID,
tag
)
values
(1, 'XYY'),
(1, 'X'),
(1, 'Y'),
(2, 'X'),
(2, 'Y'),
(3, 'X'),
(3, 'Y'),
(3, 'XYYY'),
(3, 'YXY'),
(4, 'story'),
(4, 'kids')
;

insert into sampledb.joke_review(
reviewerID,
reviewUsername,
jokeid,
score,
remark
)
values
(2, 'john', 7, 'good', 'XXX')
,(2, 'john', 3, 'good', 'XXX')
,(2, 'john', 4, 'good', 'XXX')
,(2, 'john', 5, 'good', 'XXX')
,(11, 'vzhang', 2, 'good', 'XXX')
,(11, 'vzhang', 3, 'good', 'XXX')
,(11, 'vzhang', 4, 'good', 'XXX')
,(11, 'vzhang', 5, 'good', 'XXX')
,(11, 'vzhang', 6, 'good', 'XXX')
,(2, 'john', 2, 'good', 'XXX')
;

INSERT INTO `sampledb`.`user_favorite`
(`userID`,
`type`,
`favorite`)
VALUES
(11, 'friend', 2)
,(11, 'friend', 3)
,(11, 'friend', 4)
,(11, 'friend', 5)
,(11, 'friend', 6)
,(11, 'joke', 4)
,(11, 'joke', 5)
,(11, 'joke', 6)
,(11, 'joke', 7)
,(11, 'joke', 8)
;

INSERT INTO `sampledb`.`blacklist`
(`userID`)
VALUES
(3)
,(4)
;

-- update sampledb.joke_review set score = 'fair' where reviewerID = 2 and jokeID = 7;


-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'john'@'%';
--  
-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'mary'@'%';



