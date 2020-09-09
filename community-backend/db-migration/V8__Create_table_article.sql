create table article (
  id bigint not null auto_increment,
  created_at datetime(6),
  updated_at datetime(6),
  title varchar(255) not null,
  content LONGTEXT not null,
  username varchar(255) not null,
  active bit,
  primary key (id)
);
