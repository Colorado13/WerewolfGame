CREATE database java3final;
USE java3final;
CREATE TABLE users
( username VARCHAR(30)
, password VARCHAR(30)
, privilege VARCHAR(6)
, nummatches DECIMAL(4,0)
, numwins DECIMAL(4,0)
, CONSTRAINT pkusers PRIMARY KEY (username)
, CONSTRAINT ckprivilege CHECK (privilege IN ('ADMIN','MEMBER'))
);
CREATE TABLE games
( gameid TINYINT NOT NULL AUTO_INCREMENT
, numofplayers DECIMAL(2,0)
, gameround TINYINT DEFAULT 1;
, CONSTRAINT pkgames PRIMARY KEY (gameid)
);

CREATE TABLE mainchat
( username VARCHAR(30)
, message VARCHAR(150)
, thetime DATETIME
, CONSTRAINT pkmainchat PRIMARY KEY (username, message, thetime)
);

CREATE TABLE gameid (
gameid TINYINT,
playerid VARCHAR(30),
role VARCHAR(10),
status VARCHAR(5) DEFAULT "ALIVE",
CONSTRAINT gamefk FOREIGN KEY (gameid) REFERENCES games (gameid),
CONSTRAINT playerfk FOREIGN KEY (playerid) REFERENCES users (username)
);

CREATE TABLE gamechat (
gameid TINYINT,
playerid VARCHAR(30),
message VARCHAR(150),
thetime DATETIME,
CONSTRAINT gamechatfk FOREIGN KEY (gameid) REFERENCES games (gameid),
CONSTRAINT playergamechatfk FOREIGN KEY (playerid) REFERENCES users (username)
);

CREATE TABLE werewolfchat (
gameid TINYINT,
playerid VARCHAR(30),
message VARCHAR(150),
thetime DATETIME,
CONSTRAINT wwchatfk FOREIGN KEY (gameid) REFERENCES games (gameid),
CONSTRAINT wwgamechatfk FOREIGN KEY (playerid) REFERENCES users (username)
);

CREATE TABLE deadchat (
gameid TINYINT,
playerid VARCHAR(30),
message VARCHAR(150),
thetime DATETIME,
CONSTRAINT deadchatfk FOREIGN KEY (gameid) REFERENCES games (gameid),
CONSTRAINT deadgamechatfk FOREIGN KEY (playerid) REFERENCES users (username)
);

CREATE TABLE killorder (
gameid TINYINT,
wwid VARCHAR(30),
victimid VARCHAR(30),
gameround TINYINT NOT NULL DEFAULT 1,
orderindex SMALLINT NOT NULL DEFAULT 0,
CONSTRAINT ordergameidfk FOREIGN KEY (gameid) REFERENCES games (gameid),
CONSTRAINT wwplayerfk FOREIGN KEY (wwid) REFERENCES users (username),
CONSTRAINT victimplayerfk FOREIGN KEY (victimid) REFERENCES users (username)
);

CREATE TABLE votes (
gameid TINYINT,
votingid VARCHAR(30),
votedid VARCHAR(30),
gameround TINYINT NOT NULL DEFAULT 1,
voteindex SMALLINT NOT NULL DEFAULT 0,
CONSTRAINT votegameidfk FOREIGN KEY (gameid) REFERENCES games (gameid),
CONSTRAINT votingplayerfk FOREIGN KEY (votingid) REFERENCES users (username),
CONSTRAINT votedplayerfk FOREIGN KEY (votedid) REFERENCES users (username)
);

INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Joao', 'root', 'ADMIN', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Jamie', 'root', 'ADMIN', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player1', 'Player1', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player2', 'Player2', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player3', 'Player3', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player4', 'Player4', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player5', 'Player5', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player6', 'Player6', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player7', 'Player7', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player8', 'Player8', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player9', 'Player9', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player10', 'Player10', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player11', 'Player11', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player12', 'Player12', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player13', 'Player13', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player14', 'Player14', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player15', 'Player15', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player16', 'Player16', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player17', 'Player17', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player18', 'Player18', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player19', 'Player19', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player20', 'Player20', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player21', 'Player21', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player22', 'Player22', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player23', 'Player23', 'MEMBER', 0, 0)
;
INSERT INTO users(username, password, privilege, nummatches, numwins)
  VALUES ('Player24', 'Player24', 'MEMBER', 0, 0)
;