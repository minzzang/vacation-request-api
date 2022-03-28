create table member (
                        id bigint generated by default as identity,
                        created_at timestamp,
                        updated_at timestamp,
                        email varchar(255),
                        password varchar(255),
                        primary key (id)
);

create table member_vacation (
                                 member_id bigint not null,
                                 created_at timestamp,
                                 updated_at timestamp,
                                 total_vacation_days integer not null,
                                 use integer not null,
                                 primary key (member_id)
);

create table member_vacation_detail (
                                        id bigint generated by default as identity,
                                        created_at timestamp,
                                        updated_at timestamp,
                                        comments varchar(255),
                                        end_date date,
                                        start_date date,
                                        use integer not null,
                                        vacation_request_status varchar(255),
                                        member_id bigint,
                                        primary key (id)
);

alter table member
    add constraint UK_M
        unique (email);

alter table member_vacation
    add constraint FK_MV
        foreign key (member_id)
            references member;

alter table member_vacation_detail
    add constraint FK_MVD
        foreign key (member_id)
            references member_vacation;



INSERT INTO MEMBER (EMAIL, PASSWORD, CREATED_AT, UPDATED_AT)
VALUES ('minzzan@gmail.com', '1234', NOW(), NOW());

INSERT INTO MEMBER_VACATION (MEMBER_ID, TOTAL_VACATION_DAYS, USE, CREATED_AT, UPDATED_AT)
VALUES (SELECT ID FROM MEMBER WHERE EMAIL = 'minzzan@gmail.com', 15, 0, NOW(), NOW());
