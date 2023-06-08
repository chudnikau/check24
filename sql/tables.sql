create table film_rating (
   id bigint not null primary key,
   user_id bigint not null,
   film_id bigint not null
);

alter table film_rating add constraint fk_users foreign key (user_id) references users (id);
alter table film_rating add constraint fk_films foreign key (film_id) references films (id);