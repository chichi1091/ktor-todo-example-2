create table accounts (
    id VARCHAR(50) NOT NULL,
    password TEXT,
    google_id TEXT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
)