alter table todos add column status varchar(10) not null;
alter table todos add column person_id SERIAL not null;
alter table todos add FOREIGN KEY (person_id) REFERENCES accounts (id);
