create table accounts (
    id SERIAL NOT NULL,
    password TEXT,
    name TEXT NOT NULL,
    email varchar(60) NOT NULL,
    PRIMARY KEY (id)
);
CREATE UNIQUE INDEX unq_accounts_email ON accounts (email);