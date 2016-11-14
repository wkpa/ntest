-- Список задач

create table todo (
    id integer not null default nextval('todo_seq'),
    name varchar(50),
    description varchar(255)
    );

comment on table todo is 'Список задач';
comment on column todo.id is 'Идентификатор (PK)';
comment on column todo.name is 'Наименование задачи';
comment on column todo.description is 'Описание задачи';

alter table todo add constraint todo_pk primary key (id);
alter table todo add constraint todo_description_nn check (description is not null);

