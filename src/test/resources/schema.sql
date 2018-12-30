create table user(
  id INT auto_increment,
  username VARCHAR(100),
  password VARCHAR(20),
  email VARCHAR(100),
  name VARCHAR(100),
  surname VARCHAR(100),
  language VARCHAR(10)
);

create table location(
	id int auto_increment ,
	country VARCHAR(100) ,
	city VARCHAR(100),
	address VARCHAR(100),
	longitude DOUBLE ,
	latitude DOUBLE 
);

create table garden(
	id int auto_increment ,
	name VARCHAR(100),
	working_days VARCHAR(40),
	working_time_window VARCHAR(256) ,
	password VARCHAR(20),
	user_id int,
	location_id int,
	foreign key (user_id) references user(id),
	foreign key (location_id) references location(id)
);

create table patch(
  id int auto_increment,
  patch_code VARCHAR(80),
  humidity_threshold float,
  humidity_critical float,
  humidity float,
  type VARCHAR(20),
  watering_time NUMBER(10),
  status VARCHAR(20),
  garden_id int,
  foreign key (garden_id) references garden(id),
);


