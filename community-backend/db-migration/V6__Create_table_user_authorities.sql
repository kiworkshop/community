create table user_authorities
(
    user_id        bigint not null,
    authorities_id bigint not null,
    primary key (user_id, authorities_id)
);

alter table user_authorities
    add constraint fk_user_authorities_authorities_id_authority_id
        foreign key (authorities_id)
            references authority (id);

alter table user_authorities
    add constraint fk_user_authorities_user_id_user_id
        foreign key (user_id)
            references user (id);