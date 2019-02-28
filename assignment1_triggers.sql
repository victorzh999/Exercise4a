-- joke_before_insert
-- drop trigger joke_before_insert;

 	CREATE TRIGGER `joke_before_insert` BEFORE INSERT ON `joke`
 	FOR EACH ROW
 	BEGIN
     IF (select count(1) from joke where date(createddate) =  date(current_timestamp()) 
     and userID = new.userID) = 5 THEN
         SIGNAL SQLSTATE '45002'
            SET MESSAGE_TEXT = 'check constraint on joke posts per day failed';
     END IF;
 	END;
-- 	
-- -- joke_tag_before_insert
-- -- joke_tag_before_update
-- -- drop trigger joke_tag_before_insert;
 CREATE TRIGGER `joke_tag_before_insert` BEFORE INSERT ON `joke_tag`
 	FOR EACH ROW
 		BEGIN
    IF new.tag like '% %' THEN
        SIGNAL SQLSTATE '45010'
           SET MESSAGE_TEXT = 'check constraint on joke_tag.tag failed on single word. Tag should be single word.';
    END IF;
    
	IF binary new.tag <> binary lower(new.tag) THEN
        SIGNAL SQLSTATE '45011'
           SET MESSAGE_TEXT = 'check constraint on joke_tag.tag failed on lower case. Tag should be lower cases.';
    END IF;
END;



 CREATE TRIGGER `joke_tag_before_update` BEFORE UPDATE ON `joke_tag`
 	FOR EACH ROW
 	 		BEGIN
    IF new.tag like '% %' THEN
        SIGNAL SQLSTATE '45010'
           SET MESSAGE_TEXT = 'check constraint on joke_tag.tag failed on single word. Tag should be single word.';
    END IF;
    
	IF binary new.tag <> binary lower(new.tag) THEN
        SIGNAL SQLSTATE '45011'
           SET MESSAGE_TEXT = 'check constraint on joke_tag.tag failed on lower case. Tag should be lower cases.';
    END IF;
END;
 

    
-- -- user_favorite_before_insert
-- -- user_favorite_before_update
-- -- drop trigger user_favorite_before_insert;
-- -- drop trigger `user_favorite_before_update`;
-- CREATE TRIGGER `user_favorite_before_insert` BEFORE INSERT ON `user_favorite`
-- 	FOR EACH ROW
-- 	BEGIN
--	 	    IF new.type not in ('friend', 'joke') THEN
--        SIGNAL SQLSTATE '45020'
--           SET MESSAGE_TEXT = 'check constraint on user_favorite.type failed. Type should be either (friend, joke) ';
--    END IF;
----     CALL sp_check_type_user_favorite(new.type) 
-- END;   
-- -- before update
-- CREATE TRIGGER `user_favorite_before_update` BEFORE UPDATE ON `user_favorite`
-- 	FOR EACH ROW
-- 	BEGIN
--	 	    IF new.type not in ('friend', 'joke') THEN
--        SIGNAL SQLSTATE '45020'
--           SET MESSAGE_TEXT = 'check constraint on user_favorite.type failed. Type should be either (friend, joke) ';
--    END IF;
----     CALL sp_check_type_user_favorite(new.type) 
-- END;   


-- -- drop trigger joke_review_before_insert;
-- -- drop trigger `joke_review_before_update`;
 CREATE TRIGGER joke_review_before_insert BEFORE INSERT ON joke_review
 	FOR EACH ROW
 	BEGIN
	 	    IF new.score not in ('excellent', 'good', 'fair', 'poor') THEN
        SIGNAL SQLSTATE '45000'
           SET MESSAGE_TEXT = 'check constraint on joke_review.score failed. Score should be either (excellent, good, fair or poor).';
    END IF;
    
    IF (select userID from joke where jokeID = new.jokeID) =  new.reviewerID THEN
		SIGNAL SQLSTATE '45001'
			SET MESSAGE_TEXT = 'check constraint on joke_review.userID failed. Cannot post review on your own joke';
    END IF;
    
    IF (select count(1) from sampledb.joke_review where date(createdDate) = date(current_timestamp()) and reviewerID =  new.reviewerID ) = 5 THEN
        SIGNAL SQLSTATE '45030'
           SET MESSAGE_TEXT = 'check constraint on review posts per day failed';
    END IF;
--     CALL sp_before_insert_joke_review(new.score, new.jokeID) 
 END;  

-- -- before update
 CREATE TRIGGER joke_review_before_update BEFORE UPDATE ON joke_review
 	FOR EACH ROW
 	BEGIN
	 	    IF new.score not in ('excellent', 'good', 'fair', 'poor') THEN
        SIGNAL SQLSTATE '45000'
           SET MESSAGE_TEXT = 'check constraint on joke_review.score failed. Score should be either (excellent, good, fair or poor).';
    END IF;
    
    IF (old.jokeID = new.jokeID) and (old.reviewerID <>  new.reviewerID) THEN
		SIGNAL SQLSTATE '45001'
			SET MESSAGE_TEXT = 'check constraint on joke_review.userID failed. Cannot modify other user review';
    END IF;
--     CALL sp_before_update_joke_review(new.score, new.reviewUsername) 
 END; 

-- show triggers;

