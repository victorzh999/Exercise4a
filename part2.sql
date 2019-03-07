use sampledb;

-- 2.1

INSERT INTO sampledb.joke
(userID, title, description)
VALUES
("userID",
"a joke about a pope",
"The pope arrived at a church with only a few elders there,");


INSERT INTO sampledb.joke_tag
(jokeID,
tag)
VALUES
(10, "blockchain")
, (10, "bitcoin")
, (10, "decentralized");

-- 2.2
select jk.userID, jk.title, jk.createdDate from sampledb.joke jk join sampledb.joke_tag jt on jk.jokeID = jt.jokeID where jt.tag = "x";

-- 2.3
INSERT INTO sampledb.joke_review
(reviewerID, jokeID, score, remark)
VALUES ("reviewerID", 10, "excellent", "This is a nice joke. I like the words spoken by the pope.");

-- 2.4
INSERT INTO sampledb.user_favorite_friend
(userID, friendID)
VALUES ("userID", "friendID");

delete from sampledb.user_favorite_friend where userID = "userID" and friendID = "friendID";

select concat(user.firstName, " ", user.lastName) as UserName, joke.title as postedJoke, joke.createdDate from sampledb.user join sampledb.joke on user.userID = joke.userID where user.userID = "john";

-- 2.5
INSERT INTO sampledb.user_favorite_joke
(userID,
jokeID)
VALUES ("john", 10);


DELETE FROM sampledb.user_favorite_joke WHERE userID = "userID" and jokeID = 10;

SELECT joke.jokeID,
    joke.userID,
    joke.title,
    joke.description,
    joke.createdDate
FROM sampledb.joke where jokeID = 10;

