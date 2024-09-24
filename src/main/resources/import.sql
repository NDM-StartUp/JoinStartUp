-- INSERTS for application_status
INSERT INTO public.application_status (name) VALUES ('Pending');
INSERT INTO public.application_status (name) VALUES ('Approved');
INSERT INTO public.application_status (name) VALUES ('Rejected');

-- INSERTS for degree_type
INSERT INTO public.degree_type (name) VALUES ('Bachelor of Science');
INSERT INTO public.degree_type (name) VALUES ('Master of Science');
INSERT INTO public.degree_type (name) VALUES ('Doctorate');

-- INSERTS for major
INSERT INTO public.major (name) VALUES ('Computer Science');
INSERT INTO public.major (name) VALUES ('Electrical Engineering');
INSERT INTO public.major (name) VALUES ('Business Administration');
INSERT INTO public.major (name) VALUES ('Mechanical Engineering');

-- INSERTS for notification_type
INSERT INTO public.notification_type (name) VALUES ('Email');
INSERT INTO public.notification_type (name) VALUES ('SMS');
INSERT INTO public.notification_type (name) VALUES ('Push Notification');

-- INSERTS for notification
INSERT INTO public.notification (notification_type, name, description) VALUES (1, 'Welcome Email', 'Welcome email for new users');
INSERT INTO public.notification (notification_type, name, description) VALUES (2, 'Password Reset', 'Password reset instructions');
INSERT INTO public.notification (notification_type, name, description) VALUES (3, 'App Update', 'Notification for new application update');
INSERT INTO public.notification (notification_type, name, description) VALUES (1, 'Application Received', 'Your application has been received.');
INSERT INTO public.notification (notification_type, name, description) VALUES (2, 'Interview Scheduled', 'Your interview has been scheduled for next week.');
INSERT INTO public.notification (notification_type, name, description) VALUES (3, 'Application Status Update', 'Your application status has been updated.');

-- INSERTS for position
INSERT INTO public.position (name) VALUES ('Software Engineer');
INSERT INTO public.position (name) VALUES ('Data Analyst');
INSERT INTO public.position (name) VALUES ('System Administrator');
INSERT INTO public.position (name) VALUES ('Software Developer');
INSERT INTO public.position (name) VALUES ('Data Scientist');
INSERT INTO public.position (name) VALUES ('Project Manager');

-- INSERTS for progress_status
INSERT INTO public.progress_status (name) VALUES ('In Progress');
INSERT INTO public.progress_status (name) VALUES ('Completed');
INSERT INTO public.progress_status (name) VALUES ('On Hold');

-- INSERTS for site
INSERT INTO public.site (name, domain_name) VALUES ('Google', 'google.com');
INSERT INTO public.site (name, domain_name) VALUES ('Facebook', 'facebook.com');
INSERT INTO public.site (name, domain_name) VALUES ('Amazon', 'amazon.com');
INSERT INTO public.site (name, domain_name) VALUES ('Pinterest', 'pinterest.com');
INSERT INTO public.site (name, domain_name) VALUES ('LinkedIn', 'linkedin.com');
INSERT INTO public.site (name, domain_name) VALUES ('GitHub', 'github.com');

-- INSERTS for skill
INSERT INTO public.skill (name) VALUES ('Java');
INSERT INTO public.skill (name) VALUES ('C#');
INSERT INTO public.skill (name) VALUES ('Python');

-- INSERTS for start_up
INSERT INTO public.start_up (is_paid, company_name, name, location, requirements, description)
VALUES (TRUE, 'Tech Innovators', 'Startup A', 'San Francisco', 'Python, Java', 'AI-based startup');
INSERT INTO public.start_up (is_paid, company_name, name, location, requirements, description)
VALUES (FALSE, 'Data Explorers', 'Startup B', 'New York', 'C#, SQL', 'Data-driven startup');
INSERT INTO public.start_up (is_paid, company_name, name, location, requirements, description)
VALUES (TRUE, 'App Developers', 'Startup C', 'Los Angeles', 'React, Node.js', 'App development startup');
INSERT INTO public.start_up (is_paid, company_name, name, location, requirements, description)
VALUES (TRUE, 'AI Development', 'Tech Innovators', 'San Francisco', 'Expert in AI', 'Developing AI solutions.');
INSERT INTO public.start_up (is_paid, company_name, name, location, requirements, description)
VALUES (FALSE, 'Green Energy', 'Sustainable Tech', 'Austin', 'Knowledge of renewable energy', 'Working on sustainable technologies.');
INSERT INTO public.start_up (is_paid, company_name, name, location, requirements, description)
VALUES (TRUE, 'Health Solutions', 'Medical Software', 'New York', 'Experience in healthcare software', 'Creating health management software.');

-- INSERTS for required_skill
INSERT INTO public.required_skill (skill_id, start_up_id) VALUES (1, 1);
INSERT INTO public.required_skill (skill_id, start_up_id) VALUES (2, 2);
INSERT INTO public.required_skill (skill_id, start_up_id) VALUES (3, 3);
INSERT INTO public.required_skill (skill_id, start_up_id) VALUES (1, 4);
INSERT INTO public.required_skill (skill_id, start_up_id) VALUES (2, 4);
INSERT INTO public.required_skill (skill_id, start_up_id) VALUES (3, 5);

-- INSERTS for start_up_status
INSERT INTO public.start_up_status (date, progress_status_id, start_up_id) VALUES ('2024-09-01', 1, 1);
INSERT INTO public.start_up_status (date, progress_status_id, start_up_id) VALUES ('2024-09-02', 2, 2);
INSERT INTO public.start_up_status (date, progress_status_id, start_up_id) VALUES ('2024-09-03', 3, 3);
INSERT INTO public.start_up_status (date, progress_status_id, start_up_id) VALUES ('2024-09-15', 1, 4);
INSERT INTO public.start_up_status (date, progress_status_id, start_up_id) VALUES ('2024-09-20', 2, 5);
INSERT INTO public.start_up_status (date, progress_status_id, start_up_id) VALUES ('2024-09-22', 3, 6);

-- INSERTS for status
INSERT INTO public.status (name) VALUES ('Active');
INSERT INTO public.status (name) VALUES ('Inactive');
INSERT INTO public.status (name) VALUES ('Suspended');

-- INSERTS for ticket_status
INSERT INTO public.ticket_status (name) VALUES ('Open');
INSERT INTO public.ticket_status (name) VALUES ('In Progress');
INSERT INTO public.ticket_status (name) VALUES ('Closed');

-- INSERTS for university
INSERT INTO public.university (name, location) VALUES ('MIT', 'Cambridge, MA');
INSERT INTO public.university (name, location) VALUES ('Stanford', 'Stanford, CA');
INSERT INTO public.university (name, location) VALUES ('Harvard', 'Cambridge, MA');

-- INSERTS for users
INSERT INTO public.users (registration_date, phone, first_name, last_name, description, email)
VALUES ('2024-09-01', '1234567890', 'John', 'Doe', 'Software Engineer', 'john.doe@example.com');
INSERT INTO public.users (registration_date, phone, first_name, last_name, description, email)
VALUES ('2024-09-05', '0987654321', 'Jane', 'Smith', 'Data Scientist', 'jane.smith@example.com');
INSERT INTO public.users (registration_date, phone, first_name, last_name, description, email)
VALUES ('2024-09-10', '5551234567', 'Alice', 'Johnson', 'Project Manager', 'alice.johnson@example.com');

-- INSERTS for employee
INSERT INTO public.employee (user_id) VALUES (1);
INSERT INTO public.employee (user_id) VALUES (2);
INSERT INTO public.employee (user_id) VALUES (3);

-- INSERTS for employee_cv
INSERT INTO public.employee_cv (employee_id, link) VALUES (1, 'http://example.com/cv1');
INSERT INTO public.employee_cv (employee_id, link) VALUES (2, 'http://example.com/cv2');
INSERT INTO public.employee_cv (employee_id, link) VALUES (3, 'http://example.com/cv3');

-- INSERTS for application_cv
INSERT INTO public.application_cv (application_status_id, date, employee_cv_id, start_up_id)
VALUES (1, '2024-09-15 10:00:00', 1, 1);
INSERT INTO public.application_cv (application_status_id, date, employee_cv_id, start_up_id)
VALUES (2, '2024-09-16 11:00:00', 2, 2);
INSERT INTO public.application_cv (application_status_id, date, employee_cv_id, start_up_id)
VALUES (3, '2024-09-17 12:00:00', 3, 3);

-- INSERTS for employee_education
INSERT INTO public.employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id)
VALUES ('2020-09-01', '2024-06-01', 1, 1, 1, 1);
INSERT INTO public.employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id)
VALUES ('2018-09-01', '2022-06-01', 2, 2, 2, 2);
INSERT INTO public.employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id)
VALUES ('2019-09-01', '2023-06-01', 3, 3, 3, 3);



-- INSERTS for start_up_status


-- INSERTS for status
INSERT INTO public.status (name) VALUES ('Pending Approval');
INSERT INTO public.status (name) VALUES ('Approved');
INSERT INTO public.status (name) VALUES ('Rejected');

-- INSERTS for ticket_status
INSERT INTO public.ticket_status (name) VALUES ('Pending');
INSERT INTO public.ticket_status (name) VALUES ('Resolved');
INSERT INTO public.ticket_status (name) VALUES ('Escalated');

-- INSERTS for university
INSERT INTO public.university (name, location) VALUES ('California Institute of Technology', 'Pasadena, CA');
INSERT INTO public.university (name, location) VALUES ('University of California, Berkeley', 'Berkeley, CA');
INSERT INTO public.university (name, location) VALUES ('University of Washington', 'Seattle, WA');

-- INSERTS for users
INSERT INTO public.users (registration_date, phone, first_name, last_name, description, email)
VALUES ('2024-09-20', '1231231234', 'Michael', 'Brown', 'Software Architect', 'michael.brown@example.com');
INSERT INTO public.users (registration_date, phone, first_name, last_name, description, email)
VALUES ('2024-09-21', '3213214321', 'Emily', 'Davis', 'UI/UX Designer', 'emily.davis@example.com');
INSERT INTO public.users (registration_date, phone, first_name, last_name, description, email)
VALUES ('2024-09-22', '4564564567', 'David', 'Miller', 'Frontend Developer', 'david.miller@example.com');

-- INSERTS for employee
INSERT INTO public.employee (user_id) VALUES (4);
INSERT INTO public.employee (user_id) VALUES (5);
INSERT INTO public.employee (user_id) VALUES (6);

-- INSERTS for employee_cv
INSERT INTO public.employee_cv (employee_id, link) VALUES (4, 'http://example.com/cv4');
INSERT INTO public.employee_cv (employee_id, link) VALUES (5, 'http://example.com/cv5');
INSERT INTO public.employee_cv (employee_id, link) VALUES (6, 'http://example.com/cv6');

-- INSERTS for application_cv


-- INSERTS for employee_education
INSERT INTO public.employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id)
VALUES ('2021-09-01', '2025-06-01', 1, 4, 1, 4);
INSERT INTO public.employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id)
VALUES ('2020-09-01', '2024-06-01', 2, 5, 2, 5);
INSERT INTO public.employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id)
VALUES ('2022-09-01', '2026-06-01', 3, 6, 3, 6);


INSERT INTO public.application_status (name) VALUES ('Under Review');
INSERT INTO public.application_status (name) VALUES ('Finalized');

-- INSERTS for notifications
INSERT INTO public.notification (notification_type, name, description) VALUES (1, 'Application Accepted', 'Congratulations! Your application has been accepted.');
INSERT INTO public.notification (notification_type, name, description) VALUES (2, 'Application Rejected', 'We regret to inform you that your application has been rejected.');
INSERT INTO public.notification (notification_type, name, description) VALUES (3, 'Interview Feedback', 'You have received feedback on your interview.');

-- INSERTS for positions
INSERT INTO public.position (name) VALUES ('Business Analyst');
INSERT INTO public.position (name) VALUES ('Network Engineer');

-- INSERTS for skills
INSERT INTO public.skill (name) VALUES ('SQL');
INSERT INTO public.skill (name) VALUES ('React');
INSERT INTO public.skill (name) VALUES ('Node.js');


INSERT INTO public.work_employee (ending_date, starting_date, employee_id, position_id, company_name)
VALUES ('2024-12-01', '2024-01-01', 1, 1, 'Tech Innovators');
INSERT INTO public.work_employee (ending_date, starting_date, employee_id, position_id, company_name)
VALUES ('2024-12-01', '2024-01-01', 2, 2, 'Green Energy');
INSERT INTO public.work_employee (ending_date, starting_date, employee_id, position_id, company_name)
VALUES (NULL, '2024-01-01', 3, 3, 'Health Solutions');