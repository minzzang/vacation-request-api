drop table if exists member CASCADE;

drop table if exists member_vacation CASCADE;

drop table if exists member_vacation_info CASCADE;

create table member (
                        id bigint generated by default as identity,
                        created_at timestamp,
                        updated_at timestamp,
                        email varchar(255),
                        password varchar(255),
                        primary key (id)
);

create table member_vacation_info (
                                      member_id bigint not null,
                                      created_at timestamp,
                                      updated_at timestamp,
                                      remains float not null,
                                      total_vacation_days float not null,
                                      primary key (member_id)
);

create table member_vacation (
                                 id bigint generated by default as identity,
                                 created_at timestamp,
                                 updated_at timestamp,
                                 comments varchar(255),
                                 end_date date,
                                 start_date date,
                                 use float not null,
                                 vacation_type varchar(255),
                                 member_id bigint,
                                 primary key (id)
);

alter table member
    add constraint UK_M
        unique (email);

alter table member_vacation
    add constraint FK_MV
        foreign key (member_id)
            references member_vacation_info;

INSERT INTO MEMBER (EMAIL, PASSWORD, CREATED_AT, UPDATED_AT)
VALUES ('minzzang@gmail.com', '$2a$10$IAMZSFoTwvLMZddyX1mwQeBBRWEpYpuIrUcjdz4oE0L4RyRQquUcm', NOW(), NOW());

INSERT INTO member_vacation_info (MEMBER_ID, TOTAL_VACATION_DAYS, REMAINS, CREATED_AT, UPDATED_AT)
VALUES (SELECT ID FROM MEMBER WHERE EMAIL = 'minzzang@gmail.com', 15, 15, NOW(), NOW());
