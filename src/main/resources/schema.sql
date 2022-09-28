CREATE TABLE public.book (
	book_id int8 NOT NULL,
	author_name varchar(255) NULL,
	"name" varchar(255) NULL,
	price float8 NOT NULL,
	stock int4 NOT NULL,
	version int8 NULL,
	CONSTRAINT book_pkey PRIMARY KEY (book_id)
);

CREATE TABLE public.customer (
	customer_id int8 NOT NULL,
	email varchar(255) NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	surname varchar(255) NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (customer_id)
);


CREATE TABLE public.order_table (
	id int8 NOT NULL,
	create_date timestamp NULL,
	status varchar(255) NULL,
	customer_id int8 NOT NULL,
	CONSTRAINT order_table_pkey PRIMARY KEY (id)
);

ALTER TABLE public.order_table ADD CONSTRAINT fkdit09e676nqbguvt1k1w8mlj2 FOREIGN KEY (customer_id) REFERENCES public.customer(customer_id);

CREATE TABLE public.order_detail (
	id int8 NOT NULL,
	amount int4 NOT NULL,
	price float8 NULL,
	book_id int8 NOT NULL,
	order_id int8 NOT NULL,
	CONSTRAINT order_detail_amount_check CHECK ((amount >= 1)),
	CONSTRAINT order_detail_pkey PRIMARY KEY (id)
);

ALTER TABLE public.order_detail ADD CONSTRAINT fk15634ut62dpm7cmpeltva15k FOREIGN KEY (order_id) REFERENCES public.order_table(id);
ALTER TABLE public.order_detail ADD CONSTRAINT fk3aceepmpjwpo8pdn7gmjdfckq FOREIGN KEY (book_id) REFERENCES public.book(book_id);



CREATE TABLE public.roles_table (
	id serial NOT NULL,
	"name" varchar(20) NULL,
	CONSTRAINT roles_table_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users_table (
	id bigserial NOT NULL,
	email varchar(50) NULL,
	"name" varchar(255) NULL,
	"password" varchar(120) NULL,
	surname varchar(255) NULL,
	username varchar(20) NULL,
	CONSTRAINT ukk9gbvc9xvagimmj1y060txmha UNIQUE (email),
	CONSTRAINT ukn9aj79eaiuhioi3qxwiwqp46y UNIQUE (username),
	CONSTRAINT users_table_pkey PRIMARY KEY (id)
);

CREATE TABLE public.user_roles (
	user_id int8 NOT NULL,
	role_id int4 NOT NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id)
);

ALTER TABLE public.user_roles ADD CONSTRAINT fk1u8x5ktm8kuwd6ef3e43cilib FOREIGN KEY (user_id) REFERENCES public.users_table(id);
ALTER TABLE public.user_roles ADD CONSTRAINT fkdoith43643c311rbssb7y5opv FOREIGN KEY (role_id) REFERENCES public.roles_table(id);

