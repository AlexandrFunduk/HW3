create table IF NOT EXISTS TB_TINDER_USER
(
    ID          INTEGER auto_increment
        primary key,
    CHAT_ID     CHARACTER VARYING(255)           not null
        constraint chat_id_unique
            unique,
    DESCRIPTION CHARACTER VARYING(255),
    HEADER      CHARACTER VARYING(255),
    NAME        CHARACTER VARYING(255),
    PREFERENCE  CHARACTER VARYING(255),
    REGISTERED  TIMESTAMP default LOCALTIMESTAMP not null,
    SEX         CHARACTER VARYING(255),
    UPDATED     TIMESTAMP,
    LAST_VIEWED_ID INTEGER
        references TB_TINDER_USER,
    LAST_FOUND_ID INTEGER
            references TB_TINDER_USER
);

create index idx_tb_tinder_user_chatid on TB_TINDER_USER (CHAT_ID);


create table IF NOT EXISTS TB_LIKE
(
    USER_ID  INTEGER not null
        constraint fk_tb_like_user_id
            references TB_TINDER_USER,
    LIKE_ID INTEGER not null
        constraint fk_tb_like_like_id
            references TB_TINDER_USER,
    constraint tb_like_pk
        primary key (USER_ID, LIKE_ID)
);
