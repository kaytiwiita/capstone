drop database if exists plantbase;
create database plantbase;
use plantbase;

create table plantbase_role (
	role_id int primary key auto_increment,
    role_name varchar(10) not null
);

create table garden (
	garden_id int primary key auto_increment
);

create table user_profile (
	user_id int primary key auto_increment,
    role_id int not null,
    first_name varchar(25) not null,
    last_name varchar(25) not null,
    email varchar(50) not null,
	constraint fk_user_profile_role_id
        foreign key (role_id)
        references plantbase_role(role_id)
);

create table my_garden (
	my_garden_id int primary key auto_increment,
    user_id int not null,
    bio varchar(100) default "",
    photo varchar(1000) default "",
    constraint fk_my_garden_user_id
		foreign key (user_id)
        references user_profile(user_id)
);

create table post (
	post_id int primary key auto_increment,
    user_id int not null,
    garden_id int not null,
    caption varchar(250) not null,
    photo varchar(1000) default "",
    datetime_posted datetime not null,
    like_count int not null,
    constraint fk_post_user_id
		foreign key (user_id)
        references user_profile(user_id),
	constraint fk_post_garden_id
		foreign key (garden_id)
        references garden(garden_id)
);

create table reply (
	reply_id int primary key auto_increment,
    user_id int not null,
    post_id int not null,
    reply varchar(250),
    datetime_posted datetime not null,
    like_count int not null,
    constraint fk_reply_user_id
		foreign key (user_id)
        references user_profile(user_id),
	constraint fk_reply_post_id
		foreign key (post_id)
        references post(post_id)
);

create table plant (
	plant_id int primary key auto_increment,
    post_id int not null,
    my_garden_id int not null,
    plant_description varchar(250) not null,
    photo varchar(1000) default "",
    plant_name varchar(50),
    plant_type varchar(50),
    gotcha_date date null,
    constraint fk_plant_post_id
		foreign key (post_id)
        references post(post_id),
	constraint fk_plant_my_garden_id
		foreign key (my_garden_id)
        references my_garden(my_garden_id)
);

delimiter //
create procedure set_known_good_state()
begin
	delete from plantbase_role;
    alter table plantbase_role auto_increment = 1;
    delete from user_profile;
    alter table user_profile auto_increment = 1;
    delete from garden;
    alter table garden auto_increment = 1;
    delete from my_garden;
    alter table my_garden auto_increment = 1;
    delete from post;
    alter table post auto_increment = 1;
    delete from reply;
    alter table reply auto_increment = 1;
    delete from plant;
    alter table plant auto_increment = 1;

	insert into plantbase_role (role_id, role_name)
		values
		(1, 'ADMIN'),
		(2, 'USER');

	insert into user_profile (user_id, role_id, first_name, last_name, email)
		values 
		(1, 1, 'John', 'Smith', 'john@smith.com'),
		(2, 2, 'Kayti', 'Wiita', 'asdf@asdf.com'),
		(3, 2, 'Rachel', 'Cuccia', '1234@asdf.com'),
		(4, 2, 'Ashley', 'Edmunds', 'lkjf@asdf.com');
		
	insert into my_garden (my_garden_id, user_id, bio, photo)
		values
		(1, 1, 'test bio', 'fkdk.jpeg');

	insert into garden (garden_id)
		values (1);
		
	insert into post (post_id, user_id, garden_id, caption, photo, datetime_posted, like_count)
		values
		(1, 1, 1, 'test post', 'test_post.png', '2021-05-18 10:43:18', 0);
			
	insert into reply (reply_id, user_id, post_id, reply, datetime_posted, like_count)
		values
		(1, 1, 1, 'test reply', '2021-05-18 10:43:18', 0);

	insert into plant (plant_id, post_id, my_garden_id, plant_description, photo, plant_name, plant_type, gotcha_date)
		values
		(1, 1, 1, 'pink', 'test.png', 'katy', 'double flower flaming katy', '2021-05-13');
        
end //
delimiter ;

    