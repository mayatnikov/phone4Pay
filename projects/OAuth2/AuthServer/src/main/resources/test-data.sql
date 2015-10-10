insert into users values ('user_01','5f4dcc3b5aa765d61d8327deb882cf99',1) ;
insert into users values ('user_02','5f4dcc3b5aa765d61d8327deb882cf99',1) ;

insert into authorities values ('user_01','ROLE_USER') ;
insert into authorities values ('user_01','ROLE_CLIENT') ;
insert into authorities values ('user_02','ROLE_USER') ;
insert into authorities values ('user_02','ROLE_CLIENT') ;
insert into authorities values ('user_02','ROLE_ADMIN') ;
insert into authorities values ('user_02','ROLE_TRUSTED_CLIENT') ;

insert into oauth_client_details values ('client1','secret',null,'read,write,delete','password,authorization_code,refresh_token,implicit',null,'ROLE_CLIENT, ROLE_TRUSTED_CLIENT',null,null,null) ;





