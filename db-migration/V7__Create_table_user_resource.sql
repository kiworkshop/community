create table user_resource (
  id bigint not null auto_increment,
  created_at datetime(6),
  updated_at datetime(6),
  contact_email varchar(255),
  nickname varchar(255) not null,
  user_id bigint not null,
  username varchar(255) not null,
  primary key (id)
);

alter table user_resource
    add constraint uk_user_resource_nickname unique (nickname);

alter table user_resource
    add constraint uk_user_resource_user_id unique (user_id);

alter table user_resource
    add constraint uk_user_resource_username unique (username);