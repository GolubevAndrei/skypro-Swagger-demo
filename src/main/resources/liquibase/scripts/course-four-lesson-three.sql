liquibase formatted sql

changeset GolubevAA:1
CREATE INDEX student_name_idx ON student (name);


changeset GolubevAA:1
CREATE INDEX color_name_idx ON faculty (color, name);