# comment가 sql 예약에서라서 comments로 변경
create table comments (
    id         bigint  not null auto_increment,
    active     bit     not null,
    board_type varchar(255),
    content    varchar(255),
    created_at datetime,
    ordinal    integer not null,
    parent_id  bigint,
    post_id    bigint,
    username   varchar(255),
    primary key (id)
);