CREATE KEYSPACE IF NOT EXISTS cloudclan WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3} AND durable_writes = 'true';
USE cloudclan;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS cloud_container;

CREATE TABLE IF NOT EXISTS user(
email_id text PRIMARY KEY,
password text,
first_name text,
last_name text
);

CREATE TABLE container(
id text PRIMARY KEY,
type text
);

CREATE TABLE container_user(
container_id text,
email_id text,
r_email_id text,
access_type text,
PRIMARY KEY(container_id, email_id)
);
CREATE INDEX container_user_r_email_id_idx ON container_user (r_email_id);


CREATE TYPE image_metadata(
object_id text,
width int,
height int,
size int,
transformation text,
type text
);


CREATE TABLE image(
url text PRIMARY KEY,
container_id text,
total_size int,
total_trans int,
metadata map<text,frozen<image_metadata>>
);

CREATE INDEX image_container_id_idx ON image (container_id);
