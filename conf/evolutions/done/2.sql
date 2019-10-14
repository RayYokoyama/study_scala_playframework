# --- !Ups
CREATE TABLE posts (
  id varchar(36) NOT NULL,
  user_id varchar(36) NOT NULL,
  text varchar(100) NOT NULL,
  comment_count int DEFAULT 0,
  posted_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
);

# --- !Downs
drop table  posts;