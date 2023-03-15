-- liquibase formatted sql
-- changeSet GolubevAA:1
CREATE INDEX student_name_idx ON student (name);


-- changeSet GolubevAA:2
CREATE INDEX color_name_idx ON faculty (color, name);