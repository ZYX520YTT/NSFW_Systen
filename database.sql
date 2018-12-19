CREATE TABLE complain
(
  comp_id      VARCHAR(32)  NOT NULL
    PRIMARY KEY,
  comp_company VARCHAR(100) NULL,
  comp_name    VARCHAR(20)  NULL,
  comp_mobile  VARCHAR(20)  NULL,
  is_NM        TINYINT(1)   NULL,
  comp_time    DATETIME     NULL,
  comp_title   VARCHAR(200) NOT NULL,
  to_comp_name VARCHAR(20)  NULL,
  to_comp_dept VARCHAR(100) NULL,
  comp_content TEXT         NULL,
  state        VARCHAR(1)   NULL
)
  ENGINE = InnoDB;

CREATE TABLE complain_reply
(
  reply_id      VARCHAR(32)  NOT NULL
    PRIMARY KEY,
  comp_id       VARCHAR(32)  NOT NULL,
  replyer       VARCHAR(20)  NULL,
  reply_dept    VARCHAR(100) NULL,
  reply_time    DATETIME     NULL,
  reply_content VARCHAR(300) NULL,
  CONSTRAINT FK_comp_reply
  FOREIGN KEY (comp_id) REFERENCES complain (comp_id)
)
  ENGINE = InnoDB;

CREATE INDEX FK_comp_reply
  ON complain_reply (comp_id);

CREATE TABLE info
(
  info_id     VARCHAR(32)  NOT NULL
    PRIMARY KEY,
  type        VARCHAR(10)  NULL,
  source      VARCHAR(50)  NULL,
  title       VARCHAR(100) NOT NULL,
  content     LONGTEXT     NULL,
  memo        VARCHAR(200) NULL,
  creator     VARCHAR(10)  NULL,
  create_time DATETIME     NULL,
  state       VARCHAR(1)   NULL
)
  ENGINE = InnoDB;

CREATE TABLE role
(
  roleId VARCHAR(32) NOT NULL
    PRIMARY KEY,
  state  VARCHAR(1)  NULL,
  name   VARCHAR(20) NOT NULL
)
  ENGINE = InnoDB;

CREATE TABLE role_privilege
(
  role_id VARCHAR(32)  NOT NULL,
  code    VARCHAR(255) NOT NULL,
  PRIMARY KEY (role_id, code),
  CONSTRAINT FK45FBD628F05C38CB
  FOREIGN KEY (role_id) REFERENCES role (roleId)
)
  ENGINE = InnoDB;

CREATE INDEX FK45FBD628F05C38CB
  ON role_privilege (role_id);

CREATE TABLE t_month
(
  imonth INT NULL
)
  ENGINE = InnoDB;

CREATE TABLE user
(
  id       VARCHAR(32)  NOT NULL
    PRIMARY KEY,
  name     VARCHAR(20)  NOT NULL,
  dept     VARCHAR(20)  NOT NULL,
  account  VARCHAR(50)  NOT NULL,
  password VARCHAR(50)  NOT NULL,
  headImg  VARCHAR(100) NULL,
  gender   BIT          NULL,
  email    VARCHAR(50)  NULL,
  mobile   VARCHAR(20)  NULL,
  birthday DATETIME     NULL,
  state    VARCHAR(1)   NULL,
  memo     VARCHAR(200) NULL
)
  ENGINE = InnoDB;

CREATE TABLE user_role
(
  role_id VARCHAR(32) NOT NULL,
  user_id VARCHAR(32) NOT NULL,
  PRIMARY KEY (role_id, user_id),
  CONSTRAINT FK143BF46AF05C38CB
  FOREIGN KEY (role_id) REFERENCES role (roleId)
)
  ENGINE = InnoDB;

CREATE INDEX FK143BF46AF05C38CB
  ON user_role (role_id);

