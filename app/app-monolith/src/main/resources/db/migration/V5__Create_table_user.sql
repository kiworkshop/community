create table user
(
    id                      bigint      not null auto_increment,
    created_at              datetime(6),
    updated_at              datetime(6),
    account_non_expired     bit         not null,
    account_non_locked      bit         not null,
    credentials_non_expired bit         not null,
    enabled                 bit         not null,
    social_provider         varchar(20),
    social_id               varchar(256),
    username                varchar(36) not null,
    primary key (id)
);

alter table user
    add constraint uk_user_social_id unique (social_id);

alter table user
    add constraint uk_user_username unique (username);