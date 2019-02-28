drop database if exists sampledb;

create database if not exists sampledb;
use sampledb;

create table user (
userID varchar(20) not null,
password varchar(50) not null,
firstName varchar(50) not null,
lastName varchar(50) not null,
email varchar(100) not null,
gender varchar(20),
age int,
isRoot bool default 0,
isBlacklist bool default 0,
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (userID),
unique user_email_unique (email)
);

create table joke (
jokeID int not null auto_increment,
userID varchar(20) not null,
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

create table user_favorite_friend(
userID varchar(20) not null,
friendID varchar(20) not null,
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (userID, friendID),
FOREIGN KEY (userID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
FOREIGN KEY (friendID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE
);

create table user_favorite_joke(
userID varchar(20) not null,
jokeID int not null,
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (userID, jokeID),
FOREIGN KEY (userID) REFERENCES user(userID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
FOREIGN KEY (jokeID) REFERENCES joke(jokeID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE
);

create table joke_review(
reviewerID varchar(20) not null,
jokeID int not null,
score varchar(10) not null check(score in ('excellent', 'good', 'fair', 'poor')),
remark varchar(1000),
createdDate datetime default current_timestamp(),
updatedDate datetime default current_timestamp(),
primary key (reviewerID, jokeID),
FOREIGN KEY (reviewerID) REFERENCES user(userID) 
		ON DELETE NO ACTION  
        ON UPDATE CASCADE,
FOREIGN KEY (jokeID) REFERENCES joke(jokeID)
		ON DELETE NO ACTION  
        ON UPDATE CASCADE
-- ,check(5 >= (select count(1) from joke_review where date(createdDate) = date(current_timestamp()) and reviewUsername =  substring_index(user(), '@', 1) group by reviewUsername) = 5)
);

-- create table blacklist(
-- userID varchar(20),
-- createdDate datetime default current_timestamp(),
-- Primary key (userID)
-- -- FOREIGN KEY (userID) REFERENCES user(userID) 
-- -- 		ON DELETE NO ACTION  
-- --         ON UPDATE CASCADE
-- );


insert into user(
userID, password, firstName, lastName, email, isRoot, gender, age, isBlacklist
)
values
('root', 'pass1234', 'vzhang', 'man', 'root@hotmail.com', 1, 'F', 30, 0)
,('john', 'pass1234', 'root', 'man2', 'man@hotmail.com', 0, 'M', 20, 0)
,('root1', 'pass1234', 'john1', 'man', 'root1@hotmail.com', 0, 'F', 35, 1)
,('john1', 'pass1234', 'root1', 'man2', 'man1@hotmail.com', 0, 'M', 30, 1)
,('root2', 'pass1234', 'john2', 'man', 'root2@hotmail.com', 0, 'F', 30, 0)
,('john2', 'pass1234', 'root2', 'man2', 'man2@hotmail.com', 0, 'M', 30, 0)
,('root3', 'pass1234', 'john3', 'man3', 'root3@hotmail.com', 0, 'F', 30, 0)
,('john3', 'pass1234', 'root3', 'man3', 'man3@hotmail.com', 0, 'M', 30, 0)
,('root4', 'pass1234', 'john4', 'man', 'root4@hotmail.com', 0, 'F', 30, 0)
,('john4', 'pass1234', 'root4', 'man2', 'man4@hotmail.com', 0, 'M', 30, 0)
,('vzhang', 'victor1234', 'vzhang', 'man', 'vzhang@hotmail.com', 0, 'M', 40, 0)
;


insert into joke (
userID, title, description
)
values
('vzhang', 'joke_vzhang', 'this is a normal joke')
,('john', 'joke1', 'this is a fancy joke')
,('john', 'joke2', 'this is a very intereasting joke i heard')
,('root2', 'joke3', 'this is a boring joke i heard')
,('john1', 'joke4', 'this is a very intereasting joke i heard')
,('root2', 'joke5', 'this is a boring joke i heard')
,('root1', 'joke6', 'this is a very intereasting joke i heard')
,('root1', 'joke7', 'this is a boring joke i heard')
,('root1', 'joke8', 'this is a very intereasting joke i heard')
,('john2', 'joke9', 'this is a boring joke i heard')
,('john2', 'joke210', 'this is a very intereasting joke i heard')
,('john1', 'joke11', 'this is a fancy joke')
,('john1', 'joke12', 'this is a very intereasting joke i heard')
;

insert into joke_tag(
jokeID,
tag
)
values
(1, 'xyy'),
(1, 'x'),
(1, 'y'),
(2, 'x'),
(2, 'y'),
(3, 'x'),
(3, 'y'),
(3, 'xyyy'),
(3, 'yxy'),
(4, 'story'),
(4, 'kids')
;


insert into sampledb.joke_review(
reviewerID,
jokeid,
score,
remark
)
values
('john', 7, 'good', 'XXX')
,('john', 3, 'good', 'XXX')
,('john', 4, 'good', 'XXX')
,('john', 5, 'good', 'XXX')
,('vzhang', 2, 'good', 'XXX')
,('vzhang', 3, 'good', 'XXX')
,('vzhang', 4, 'good', 'XXX')
,('vzhang', 5, 'good', 'XXX')
,('vzhang', 6, 'good', 'XXX')
,('john', 2, 'good', 'XXX')
;


INSERT INTO user_favorite_joke
(userID,
jokeID)
VALUES
('vzhang', 4)
,('vzhang', 5)
,('vzhang', 6)
,('vzhang', 7)
,('vzhang', 8)
,('john', 4)
,('john', 5)
,('john', 6)
,('john', 7)
,('john', 8)
;

-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('vzhang', 'john');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('vzhang', 'john1');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('vzhang', 'john2');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('vzhang', 'root2');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('vzhang', 'root3');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('john', 'root2');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('john', 'john2');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('john', 'john3');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('john', 'root3');
-- INSERT INTO user_favorite_friend(userID, friendID) VALUES('john', 'vzhang');

INSERT INTO user_favorite_friend(userID, friendID) VALUES
('vzhang', 'john')
,('vzhang', 'john1')
,('vzhang', 'john2')
,('vzhang', 'root2')
,('vzhang', 'root3')
,('john', 'root2')
,('john', 'john2')
,('john', 'john3')
,('john', 'root3')
,('john', 'vzhang');

-- INSERT INTO blacklist
-- (userID)
-- VALUES
-- ('john2')
-- ,('john3')
-- ;

-- update sampledb.joke_review set score = 'fair' where reviewerID = 2 and jokeID = 7;


-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'john'@'%';
--  
-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'mary'@'%';



