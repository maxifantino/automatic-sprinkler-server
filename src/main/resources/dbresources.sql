create table user(
	MEDIUMINT AUTOINCREMENT id,
	VARCHAR(100) username,
	VARCHAR(20) String password,
	VARCHAR(100) email,
	VARCHAR(100) name,
	VARCHAR(100) surname,
	VARCHAR(10) language
)

create table garden(
	MEDIUMINT AUTOINCREMENT id,
	VARCHAR(100) name,
	VARCHAR(40) working_days,
	VARCHAR(256) working_time_window,
	VARCHAR(20) String password,
	foreign key (user_id) references user(id),
	foreign key (location_id) references location(id)
)

create table location(
	MEDIUMINT AUTOINCREMENT id,
	VARCHAR(100) country,
	VARCHAR(100) city,
	VARCHAR(100) address,
	DOUBLE longitude,
	DOUBLE latitude
);
