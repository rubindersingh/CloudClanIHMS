CREATE KEYSPACE IF NOT EXISTS cloudclan WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3} AND durable_writes = 'true';
USE cloudclan;

CREATE TABLE IF NOT EXISTS user(
email_id text PRIMARY KEY,
password text,
first_name text,
last_name text
);

CREATE TABLE container(
id text PRIMARY KEY,
name text,
type text
);

CREATE TABLE user_container(
email_id text,
container_id text,
r_container_id text,
access_type text,
PRIMARY KEY(email_id, container_id)
);
CREATE INDEX user_container_r_container_id_idx ON user_container (r_container_id);

CREATE TYPE image_metadata(
object_id text,
width int,
height int,
size int,
transformation text,
type text
);

CREATE TYPE image_metadata(
object_id text,
size int,
transformation text,
type text
);

CREATE TABLE image(
container_id text,
url text,
metadata map<text,frozen<image_metadata>>,
PRIMARY KEY(container_id, url)
);

CREATE TABLE image_stats(
container_id text,
url text,
ts uuid,
size int,
trans int,
upload_size int,
download_size int,
PRIMARY KEY(container_id,url,ts)
);

CREATE INDEX image_stats_container_id_idx ON image_stats (container_id);
CREATE INDEX image_stats_url_idx ON image_stats (url);

Insert into image_stats(container_id,url,ts,size,trans,upload_size) values ('vhhkkjhj','/home/pic.jpg',now(), 1000, 1, 10000);
Insert into image_stats(container_id,url,ts,size,trans,upload_size) values ('vhhkkjhj','/home/pic1.jpg',now(), 1000, 1, 10000);
Insert into image_stats(container_id,url,ts,size,trans,upload_size) values ('vhhkkjhj','/home/pic2.jpg',now(), 1000, 1, 10000);
Insert into image_stats(container_id,url,ts,size,trans,upload_size) values ('vhhkkjhj','/home/pic2.jpg',now(), 100000, 1, 1000);
Insert into image_stats(container_id,url,total_size,total_trans) values ('vhhkkjhj','/home/pic.jpg', 1000, 1);
Insert into image_stats(container_id,url,total_size,total_trans) values ('vhhkkjhj','/home/pic.jpg', 1000, 1);
Insert into image_stats(container_id,url,total_size,total_trans) values ('vhhkkjhj','/home/pic.jpg', 1000, 1);

select group_and_total(url,size),group_and_total(url,trans),group_and_total(url,upload_size) from image_stats where container_id = 'vhhkkjhj' AND url IN ('/home/pic.jpg', '/home/pic1.jpg','/home/pic2.jpg');

CREATE FUNCTION state_group_and_total( state map<text, int>, type text, amount int )
CALLED ON NULL INPUT
RETURNS map<text, int>
LANGUAGE java AS '
Integer count = (Integer) state.get(type);  if (count == null) count = amount; else count = count + amount; state.put(type, count); return state; ' ;


CREATE OR REPLACE AGGREGATE group_and_total(text, int)
SFUNC state_group_and_total
STYPE map<text, int>
INITCOND {};