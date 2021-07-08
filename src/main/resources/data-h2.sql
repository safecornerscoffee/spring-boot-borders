insert into users (username, password)
values ('mocha', 'mocha');
insert into users (username, password)
values ('greentea', 'greentea');

insert into authorities (username, authority)
values ('mocha', 'ROLE_USER');
insert into authorities (username, authority)
values ('greentea', 'ROLE_USER');

commit;