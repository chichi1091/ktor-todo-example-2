create table accounts (
    id UUID NOT NULL DEFAULT gen_random_uuid(),
    password TEXT,
    google_id TEXT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
)