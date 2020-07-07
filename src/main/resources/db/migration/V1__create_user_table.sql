-- Table: user

-- DROP TABLE user;

CREATE TABLE "user"
(
    id character varying(60) COLLATE pg_catalog."default" NOT NULL,
    email character varying(100) COLLATE pg_catalog."default" NOT NULL,
    password character varying(250) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    phone character varying(40) COLLATE pg_catalog."default" NOT NULL,
    last_login timestamp without time zone,
    created_datetime timestamp without time zone,
    updated_datetime timestamp without time zone,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

