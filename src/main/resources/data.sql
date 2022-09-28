INSERT INTO public.roles_table(id,"name") VALUES(1,'ROLE_CUSTOMER');
INSERT INTO public.roles_table(id,"name") VALUES(2,'ROLE_ADMIN');
INSERT INTO users_table (id,email,"name","password",surname,username) VALUES (1,'anillcan7@gmail.com','Anil','$2a$10$grdkPI9v1idfpT257mJEEOtDA2XKdFSGdZjp1ILxYhgy/4efTSKFq','Can','getiruser');
INSERT INTO user_roles (user_id,role_id) VALUES (1,2);
INSERT INTO book (book_id,author_name,"name",price,stock,version) VALUES (1,'William Gibson','Count Zero',75.35,5,0);