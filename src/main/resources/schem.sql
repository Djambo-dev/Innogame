drop table shot;
drop table game;
drop table player;
create table player
(
    id              bigserial primary key,
    player_name     varchar(100),
    ip              varchar(100),
    points          integer,
    wins_count  integer,
    loses_count integer
);
create table game
(
    id                        bigserial primary key,
    dateTime                  varchar(100),
    player_first              bigint,
    player_second             bigint,
    player_first_shots_count  integer,
    player_second_shots_count integer,
    game_time_duration        bigint,
    foreign key (player_first) references player (id),
    foreign key (player_second) references player (id)
);
create table shot
(
    id             bigserial primary key,
    shotTime       varchar(100),
    game_id        bigint,
    player_shooter bigint,
    player_target  bigint,
    foreign key (game_id) references game (id),
    foreign key (player_shooter) references player (id),
    foreign key (player_target) references player (id)
);

