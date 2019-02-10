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
        ON UPDATE CASCADE,
check(6 < (select count(1) from joke group by userID, date(createdDate) order by 1 desc limit 1))
);

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
check (tag not like '% %')
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
score varchar(10) not null check(score in ('excellent', 'good', 'fair', 'poor')),
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
check(6 < (select count(1) from joke_review group by username, date(createdDate) order by 1 desc limit 1))
);
 
-- CREATE VIEW vw_joke_review(
--   username,
--   jokeID,
--   score,
--   remark
-- )
-- AS
-- select 
--   username,
--   jokeID,
--   score,
--   remark
-- from joke_review 
-- where
--   (username = substring_index(user(), '@', 1));

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
('vzhang', 'victor1234', 'vzhang', 'man', 'vzhang@hotmail.com')
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

insert into sampledb.vw_joke_review(
username,
jokeid,
score,
remark
)
values
('john2', 3, 'pooor', 'XXX')
;

-- sp_check_posts_perDay_joke
-- drop procedure sp_check_posts_perDay_joke;
DELIMITER $
CREATE PROCEDURE sp_check_posts_perDay_joke()
BEGIN
    IF (select count(1) from sampledb.joke group by userID, date(current_timestamp()) order by 1 desc limit 1) = 5 THEN
        SIGNAL SQLSTATE '45002'
           SET MESSAGE_TEXT = 'check constraint on joke posts per day failed';
    END IF;
END$
DELIMITER ;

-- joke_before_insert
-- drop trigger joke_before_insert;
DELIMITER $
CREATE TRIGGER `joke_before_insert` BEFORE INSERT ON `joke`
FOR EACH ROW
BEGIN
    CALL sp_check_posts_perDay_joke;
END$   
DELIMITER ; 

insert into joke (
userID, title, description
)
values
(3, 'joke11', 'this is a fancy joke')
,(3, 'joke12', 'this is a very intereasting joke i heard')
-- ,(3, 'joke13', 'this is a boring joke i heard')
-- ,(3, 'joke14', 'this is a very intereasting joke i heard')
-- ,(3, 'joke15', 'this is a boring joke i heard')
;

-- sp_check_single_word_joke_tag
-- drop procedure sp_check_single_word_joke_tag;
DELIMITER $
CREATE PROCEDURE sp_check_single_word_joke_tag(IN tag varchar(50))
BEGIN
    IF tag like '% %' THEN
        SIGNAL SQLSTATE '45010'
           SET MESSAGE_TEXT = 'check constraint on joke_tag.tag failed on single word';
    END IF;
END$
DELIMITER ;

-- joke_tag_before_insert
-- joke_tag_before_update
-- drop trigger joke_tag_before_insert;
DELIMITER $
CREATE TRIGGER `joke_tag_before_insert` BEFORE INSERT ON `joke_tag`
FOR EACH ROW
BEGIN
    CALL sp_check_single_word_joke_tag(new.tag);
END$   
DELIMITER ; 

DELIMITER $
CREATE TRIGGER `joke_tag_before_update` BEFORE UPDATE ON `joke_tag`
FOR EACH ROW
BEGIN
    CALL sp_check_single_word_joke_tag(new.tag);
END$   
DELIMITER ;

insert into joke_tag(
jokeID,
tag
)
values
(1, 'XY')
;

select *from joke_tag;
-- update joke_tag set tag='Y' where jokeid = 1 and tag = 'X Y';

-- sp_check_type_user_favorite;
DELIMITER $
CREATE PROCEDURE `sp_check_type_user_favorite`(IN type varchar(20))
BEGIN
    IF type not in ('friend', 'joke') THEN
        SIGNAL SQLSTATE '45020'
           SET MESSAGE_TEXT = 'check constraint on user_favorite.type failed';
    END IF;
END$
DELIMITER ;

-- user_favorite_before_insert
-- user_favorite_before_update
-- drop trigger user_favorite_before_insert;
-- drop trigger `user_favorite_before_update`;
DELIMITER $
CREATE TRIGGER `user_favorite_before_insert` BEFORE INSERT ON `user_favorite`
FOR EACH ROW
BEGIN
    CALL sp_check_type_user_favorite(new.type);
END$   
DELIMITER ; 
-- before update
DELIMITER $
CREATE TRIGGER `user_favorite_before_update` BEFORE UPDATE ON `user_favorite`
FOR EACH ROW
BEGIN
    CALL sp_check_type_user_favorite(new.type);
END$   
DELIMITER ;

-- show triggers;

-- sp_check_score_joke_review
-- drop procedure sp_check_score_joke_review;
DELIMITER $
CREATE PROCEDURE `sp_check_score_joke_review`(IN score varchar(10), IN username varchar(20))
BEGIN
    IF score not in ('excellent', 'good', 'fair', 'poor') THEN
        SIGNAL SQLSTATE '45000'
           SET MESSAGE_TEXT = 'check constraint on joke_review.score failed';
    END IF;
    
    IF username <>  substring_index(user(), '@', 1) THEN
		SIGNAL SQLSTATE '45001'
			SET MESSAGE_TEXT = 'check constraint on joke_review.username failed';
    END IF;
--     
--     IF price < cost THEN
--  SIGNAL SQLSTATE '45002'
--            SET MESSAGE_TEXT = 'check constraint on parts.price & parts.cost failed';
--     END IF;
END$
DELIMITER ;

-- drop trigger joke_review_before_insert;
-- drop trigger `joke_review_before_update`;
DELIMITER $
CREATE TRIGGER `joke_review_before_insert` BEFORE INSERT ON `joke_review`
FOR EACH ROW
BEGIN
    CALL sp_check_score_joke_review(new.score, new.username);
END$   
DELIMITER ; 
-- before update
DELIMITER $
CREATE TRIGGER `joke_review_before_update` BEFORE UPDATE ON `joke_review`
FOR EACH ROW
BEGIN
    CALL sp_check_score_joke_review(new.score, new.username);
END$   
DELIMITER ;

-- sp_check_posts_perDay_review
DELIMITER $
CREATE PROCEDURE sp_check_posts_perDay_review()
BEGIN
    IF (select count(1) from sampledb.joke_review group by username, date(current_timestamp()) order by 1 desc limit 1) = 5 THEN
        SIGNAL SQLSTATE '45030'
           SET MESSAGE_TEXT = 'check constraint on review posts per day failed';
    END IF;
END$
DELIMITER ;

-- joke_review_posts_before_insert
-- drop trigger joke_before_insert;
DELIMITER $
CREATE TRIGGER `joke_review_posts_before_insert` BEFORE INSERT ON `joke_review`
FOR EACH ROW
BEGIN
    CALL sp_check_posts_perDay_review;
END$   
DELIMITER ; 


insert into sampledb.joke_review(
username,
jokeid,
score,
remark
)
values
('vzhang', 3, 'good', 'XXX')
,('vzhang', 4, 'good', 'XXX')
,('vzhang', 5, 'good', 'XXX')
;

select * from joke_review order by createdDate;

-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'john'@'%';
--  
-- GRANT SELECT, INSERT, UPDATE, DELETE, 
--   ON TABLE user_books
-- TO 'mary'@'%';



