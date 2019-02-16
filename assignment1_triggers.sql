CREATE PROCEDURE sp_check_posts_perDay_joke()
	BEGIN
    IF (select count(1) from sampledb.joke where date(createddate) =  date(current_timestamp()) group by userID order by 1 desc limit 1) = 5 THEN
        SIGNAL SQLSTATE '45002'
           SET MESSAGE_TEXT = 'check constraint on joke posts per day failed';
    END IF;
END;

-- sp_check_single_word_joke_tag
-- drop procedure sp_check_single_word_joke_tag;
CREATE PROCEDURE sp_check_single_word_joke_tag(IN tag varchar(50))
	BEGIN
    IF tag like '% %' THEN
        SIGNAL SQLSTATE '45010'
           SET MESSAGE_TEXT = 'check constraint on joke_tag.tag failed on single word. Tag should be single word.';
    END IF;
END;

-- sp_check_type_user_favorite;
CREATE PROCEDURE `sp_check_type_user_favorite`(IN type varchar(20))
	BEGIN
    IF type not in ('friend', 'joke') THEN
        SIGNAL SQLSTATE '45020'
           SET MESSAGE_TEXT = 'check constraint on user_favorite.type failed. Type should be either (friend, joke) ';
    END IF;
END;

-- CALL sp_before_insert_joke_review(new.score, new.jokeID);
-- sp_before_insert_joke_review
-- drop procedure sp_before_insert_joke_review;
CREATE PROCEDURE `sp_before_insert_joke_review`(IN score varchar(10), IN jokeID int)
	BEGIN
    IF score not in ('excellent', 'good', 'fair', 'poor') THEN
        SIGNAL SQLSTATE '45000'
           SET MESSAGE_TEXT = 'check constraint on joke_review.score failed. Score should be either (excellent, good, fair or poor).';
    END IF;
    
    IF (select user.username from joke join user on user.userID = joke.userID where joke.jokeID = jokeID) =  substring_index(user(), '@', 1) THEN
		SIGNAL SQLSTATE '45001'
			SET MESSAGE_TEXT = 'check constraint on joke_review.username failed. Cannot post review on your own joke';
    END IF;
    
    IF (select count(1) from sampledb.joke_review where date(createdDate) = date(current_timestamp()) and reviewUsername =  substring_index(user(), '@', 1) group by reviewUsername) = 5 THEN
        SIGNAL SQLSTATE '45030'
           SET MESSAGE_TEXT = 'check constraint on review posts per day failed';
    END IF;
END;

-- select user.username from joke join user on user.userID = joke.userID where joke.jokeID = 1;
-- select * from joke_review;
-- select *from joke;

-- select count(1) from sampledb.joke_review jr join user on user.userID = jr where date(createdDate) = date(current_timestamp()) and reviewerID =  group by reviewerID order by 1 desc limit 1
        
-- select distinct user.username from user join joke on user.userID = joke.userID and joke.jokeID = 6;
-- select substring_index(user(), '@', 1);

-- drop procedure sp_before_update_joke_review;
CREATE PROCEDURE `sp_before_update_joke_review`(IN score varchar(10), IN reviewUsername varchar(20))
	BEGIN
    IF score not in ('excellent', 'good', 'fair', 'poor') THEN
        SIGNAL SQLSTATE '45000'
           SET MESSAGE_TEXT = 'check constraint on joke_review.score failed. Score should be either (excellent, good, fair or poor).';
    END IF;
    
    IF reviewUsername <>  substring_index(user(), '@', 1) THEN
		SIGNAL SQLSTATE '45001'
			SET MESSAGE_TEXT = 'check constraint on joke_review.username failed. Cannot modify other user review';
    END IF;
END;

--
--
-- joke_before_insert
-- drop trigger joke_before_insert;
--CREATE TRIGGER `joke_before_insert` BEFORE INSERT ON `joke`
--	FOR EACH ROW
--	BEGIN
--    CALL sp_check_posts_perDay_joke 
--	END;

	CREATE TRIGGER `joke_before_insert` BEFORE INSERT ON `joke`
	FOR EACH ROW
	BEGIN
    IF (select count(1) from sampledb.joke where date(createddate) =  date(current_timestamp()) group by userID order by 1 desc limit 1) = 5 THEN
        SIGNAL SQLSTATE '45002'
           SET MESSAGE_TEXT = 'check constraint on joke posts per day failed';
    END IF;
	END;
	
-- joke_tag_before_insert
-- joke_tag_before_update
-- drop trigger joke_tag_before_insert;
CREATE TRIGGER `joke_tag_before_insert` BEFORE INSERT ON `joke_tag`
	FOR EACH ROW
	BEGIN
    CALL sp_check_single_word_joke_tag(new.tag) 
END; 

CREATE TRIGGER `joke_tag_before_update` BEFORE UPDATE ON `joke_tag`
	FOR EACH ROW
	BEGIN
    CALL sp_check_single_word_joke_tag(new.tag) 
END; 

-- user_favorite_before_insert
-- user_favorite_before_update
-- drop trigger user_favorite_before_insert;
-- drop trigger `user_favorite_before_update`;
CREATE TRIGGER `user_favorite_before_insert` BEFORE INSERT ON `user_favorite`
	FOR EACH ROW
	BEGIN
    CALL sp_check_type_user_favorite(new.type) 
END;   
-- before update
CREATE TRIGGER `user_favorite_before_update` BEFORE UPDATE ON `user_favorite`
	FOR EACH ROW
	BEGIN
    CALL sp_check_type_user_favorite(new.type) 
END;   


-- drop trigger joke_review_before_insert;
-- drop trigger `joke_review_before_update`;
CREATE TRIGGER joke_review_before_insert BEFORE INSERT ON joke_review
	FOR EACH ROW
	BEGIN
    CALL sp_before_insert_joke_review(new.score, new.jokeID) 
END;  

-- before update
CREATE TRIGGER joke_review_before_update BEFORE UPDATE ON joke_review
	FOR EACH ROW
	BEGIN
    CALL sp_before_update_joke_review(new.score, new.reviewUsername) 
END; 

-- show triggers;
-- use sampledb;
-- drop trigger joke_before_insert;

-- DELIMITER $ 
-- CREATE TRIGGER joke_before_insert BEFORE INSERT ON joke 
-- BEGIN
-- FOR EACH ROW 
-- CALL test.sp_check_posts_perDay_joke $ 
-- END;
-- DELIMITER ;

-- DELIMITER $ CREATE TRIGGER joke_before_insert BEFORE INSERT ON joke FOR EACH ROW CALL test.sp_check_posts_perDay_joke $ DELIMITER ;
