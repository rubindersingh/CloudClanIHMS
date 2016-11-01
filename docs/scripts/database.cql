CREATE KEYSPACE IF NOT EXISTS cloudclan WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3} AND durable_writes = 'true';
USE cloudclan;

CREATE TABLE IF NOT EXISTS user(
email_id text PRIMARY KEY,
password text,
first_name text,
last_name text
);

CREATE TABLE IF NOT EXISTS container(
id text PRIMARY KEY,
name text,
type text
);

CREATE TABLE IF NOT EXISTS user_container(
email_id text,
container_id text,
r_container_id text,
access_type text,
PRIMARY KEY(email_id, container_id)
);

CREATE INDEX IF NOT EXISTS user_container_r_container_id_idx ON user_container (r_container_id);

CREATE TABLE IF NOT EXISTS image(
container_id text,
url text,
metadata map<text,text>,
PRIMARY KEY(container_id, url)
);

CREATE TABLE IF NOT EXISTS image_service_use(
container_id text,
url text,
ts uuid,
trans int,
upload_size int,
download_size int,
PRIMARY KEY(container_id,url,ts)
);

CREATE TABLE IF NOT EXISTS image_storage_use(
container_id text,
url text,
transformation text,
size int,
ts timeuuid,
PRIMARY KEY(container_id,url,transformation)
);

CREATE OR REPLACE FUNCTION cost_function( totaltime double, size int, ts timestamp)
CALLED ON NULL INPUT
RETURNS double
LANGUAGE java AS '
if (totaltime == null) totaltime = (double) (new Date().getTime()-ts.getTime())*size; else totaltime = totaltime + (new Date().getTime()-ts.getTime())*size; return totaltime; ' ;


CREATE OR REPLACE AGGREGATE totalcost(int, timestamp)
SFUNC cost_function
STYPE double;