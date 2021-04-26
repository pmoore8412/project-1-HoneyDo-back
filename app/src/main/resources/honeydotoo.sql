CREATE TABLE users (user_email text primary key,
first_name text not null,
last_name text not null,
user_password text not null,
user_class text not null);

CREATE TABLE tasks (task_id bigint primary key,
task_name text not null,
task_due_date text not null,
task_discription text not null,
asigned_to text not null);

CREATE TABLE tasksComplete (task_id bigint primary key,
task_name text,
completed_on text,
completed_by text);