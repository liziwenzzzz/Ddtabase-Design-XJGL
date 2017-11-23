use mydb;

load data local infile "D:/MySQL/course.txt" into table course
fields terminated by ',' lines terminated by'\r\n';
load data local infile "D:/MySQL/profession.txt" into table profession
fields terminated by ',' lines terminated by '\r\n';
load data local infile "D:\\MySQL\\class.txt" into table class
fields terminated by ',' lines terminated by '\r\n';
load data local infile "D:\\MySQL\\plan.txt" into table plan
fields terminated by ',' lines terminated by '\r\n';
load data local infile "D:\\MySQL\\student.txt" into table student
fields terminated by ',' lines terminated by '\r\n';
load data local infile "D:\\MySQL\\teacher.txt" into table teacher
fields terminated by ',' lines terminated by '\r\n';
load data local infile "D:\\MySQL\\transcript.txt" into table transcript
fields terminated by ',' lines terminated by '\r\n';
load data local infile "D:\\MySQL\\arrangement.txt" into table arrangement
fields terminated by ',' lines terminated by '\r\n';

select * from course;
select * from profession;
select * from class;
select * from arrangement;
select * from plan;
select * from student;
select * from teacher;
select * from transcript;