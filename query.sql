use mydb;

#insert a student
# insert  into student values(4,'小黑','男',19931209,1);
# select * from student;

#search by id
select * from student where student_id=15030132;

#search by name
select * from student where student.name='小军';

#search by profession
select * from student where student.class_class_id in 
(select class_id from class where class.profession_profession_id in 
(select profession_id from profession where profession.name='compute'));

#insert a transcript
#insert into transcript values(70,-1,1,1);
#select * from transcript;

##search course,compulsory,semester,credit,result of a student
select course.name,course.credit,transcript.result,plan.compulsory,plan.semester from course,transcript,plan
where course.course_id  in (select course_id from transcript where student_student_id=15030131) and 
transcript.student_student_id=15030131 and transcript.course_course_id in (select course_id from transcript where student_student_id=15030131) and
plan.course_course_id in (select course_id from transcript where student_student_id=15030131) and plan.profession_profession_id in (select class.profession_profession_id from class where exists (select * from student where student_id=15030131));

#calculate average result including compolsory and uncompolsory of a student
select avg(transcript.result) from transcript where transcript.student_student_id=15030131;
select avg(transcript.result) from transcript where transcript.student_student_id=15030131 and transcript.course_course_id in 
(select course_course_id from plan where compulsory=1 and (profession_profession_id) in (select class.profession_profession_id from class where exists (select * from student where student_id=15030131)));

#search teacher name who teacher a student.
select teacher.name from teacher where teacher.teacher_id in
(select teacher_teacher_id from arrangement where arrangement.class_class_id in
(select class_class_id from student where student.name='小军')); 


#search a student already fail credit compulsory = 1.
select sum(course.credit) from course 
where course_id in (select transcript.course_course_id from transcript where transcript.result<60 and transcript.second_result<60 and transcript.student_student_id=15030131)
and course_id in (select plan.course_course_id from plan where plan.compulsory=1 and plan.profession_profession_id in (select class.profession_profession_id from class where exists (select * from student where student_id=15030131)));

#search a student already fail credit compulsory = 0.
