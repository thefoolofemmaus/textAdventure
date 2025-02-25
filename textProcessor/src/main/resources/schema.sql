drop table if exists ROOMS;
create table ROOMS(
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    north INTEGER NOT NULL DEFAULT 0,
    east INTEGER NOT NULL DEFAULT 0,
    south INTEGER NOT NULL DEFAULT 0,
    west INTEGER NOT NULL DEFAULT 0
);
