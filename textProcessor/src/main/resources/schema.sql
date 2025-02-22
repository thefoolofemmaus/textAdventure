drop table if exists ROOMS;
create table ROOMS(
    ID int not null AUTO_INCREMENT,
    title varchar(100),
    description varchar(100),
    north int,
    east int,
    south int,
    west int
);
