drop database sampledb;

create database sampledb;
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
createdDate datetime default current_timestamp(),
-- createdBy varchar(100) default user(),
updatedDate datetime default current_timestamp(),
-- updatedBy varchar(100) user(),
primary key (userID),
unique user_userName_unique (userName),
unique user_email_unique (email)
);

create table joke (
jokeID int not null auto_increment,
userID int,
title varchar(100) not null,
description text,
createdDate datetime default current_timestamp(),
-- createdBy varchar(100) ,
updatedDate datetime default current_timestamp(),
-- updatedBy varchar(100),
primary key (jokeID),
FOREIGN KEY FK_joke_userID (userID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE
-- check(6 < (select count(1) from joke group by userID, date(createdDate) order by 1 desc limit 1))
);

-- create table tag(
-- tagID int not null auto_increment,
-- tag varchar(50) not null,
-- createdDate datetime default current_timestamp(),
-- PRIMARY KEY (tagID),
-- unique tag_tag_unique (tag),
-- check ()
-- )

create table joke_tag(
jokeID int not null,
tag varchar(50),
createdDate datetime default current_timestamp(),
-- createdBy varchar(100) ,
updatedDate datetime default current_timestamp(),
-- updatedBy varchar(100) ,
primary key (jokeID, tag),
FOREIGN KEY (jokeID) REFERENCES joke(jokeID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
check (tag not like '%[,; ]%')
);

create table user_favorite(
userID int not null,
type varchar(20),
favorite varchar(50),
createdDate datetime default current_timestamp(),
-- createdBy varchar(100)  ,
updatedDate datetime default current_timestamp(),
-- updatedBy varchar(100) ,
primary key (userID, type, favorite),
FOREIGN KEY (userID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
check (type in ('friend', 'joke'))
);

create table joke_review(
username varchar(20) not null,
jokeID int not null,
score varchar(10) not null,
remark varchar(1000),
createdDate datetime default current_timestamp(),
-- createdBy varchar(100)  ,
updatedDate datetime default current_timestamp(),
-- updatedBy varchar(100) ,
primary key (username, jokeID),
FOREIGN KEY (username) REFERENCES user(username) 
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
FOREIGN KEY (jokeID) REFERENCES joke(jokeID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
check(score in ('excellent', 'good', 'fair', 'poor')),
check(6 < (select count(1) from joke_review group by username, date(createdDate) order by 1 desc limit 1))
);
 
CREATE VIEW vw_joke_review(
  username,
  jokeID,
  score,
  remark
)
AS
select 
  username,
  jokeID,
  score,
  remark
from joke_review 
where
  (username = substring_index(user(), '@', 1));

  

-- drop table blacklist;
create table blacklist(
userID varchar(20) not null,
createdDate datetime default current_timestamp(),
PRIMARY key (userID)
);

insert into user(
userName, password, firstName, lastName, email
)
values
('root', 'pass1234', 'john', 'man', 'root@hotmail.com')
,('john', 'pass1234', 'root', 'man2', 'man@hotmail.com')
,('root1', 'pass1234', 'john1', 'man', 'root1@hotmail.com')
,('john1', 'pass1234', 'root1', 'man2', 'man1@hotmail.com')
,('root2', 'pass1234', 'john2', 'man', 'root2@hotmail.com')
,('john2', 'pass1234', 'root2', 'man2', 'man2@hotmail.com')
,('root3', 'pass1234', 'john3', 'man3', 'root3@hotmail.com')
,('john3', 'pass1234', 'root3', 'man3', 'man3@hotmail.com')
,('root4', 'pass1234', 'john4', 'man', 'root4@hotmail.com')
,('john4', 'pass1234', 'root4', 'man2', 'man4@hotmail.com')
;

insert into joke (
userID, title, description
)
values
(3, 'joke1', 'this is a fancy joke')
,(3, 'joke2', 'this is a very intereasting joke i heard')
,(4, 'joke3', 'this is a boring joke i heard')
,(3, 'joke4', 'this is a very intereasting joke i heard')
,(4, 'joke5', 'this is a boring joke i heard')
,(2, 'joke6', 'this is a very intereasting joke i heard')
,(2, 'joke7', 'this is a boring joke i heard')
,(2, 'joke8', 'this is a very intereasting joke i heard')
,(5, 'joke9', 'this is a boring joke i heard')
,(6, 'joke210', 'this is a very intereasting joke i heard')
;

-- use sampledb;
-- select * from joke where description like '% %'

-- insert into joke (
-- userID, title, description
-- )
-- values
-- ('root1', 'joke210', 'this is a very intereasting joke i heard')
-- ;


-- select userID, count(1) from joke group by userID order by 1 desc limit 1;
-- select * from joke order by 2;
-- -- delete from joke where jokeid in (10, 11);
-- desc joke;



-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'john'@'%';
--  
-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'mary'@'%';


