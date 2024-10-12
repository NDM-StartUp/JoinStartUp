insert into application_status (name) values ('Pending');
insert into application_status (name) values ('Accepted');
insert into application_status (name) values ('Rejected');

insert into degree_type (name) values ('Bachelor');
insert into degree_type (name) values ('Master');
insert into degree_type (name) values ('PhD');

insert into major (name) values ('Computer Science');
insert into major (name) values ('Electrical Engineering');
insert into major (name) values ('Mechanical Engineering');

insert into notification_type (name) values ('Email');
insert into notification_type (name) values ('SMS');
insert into notification_type (name) values ('Push Notification');

insert into notification (notification_type, name, description) values (1, 'Application Update', 'Your application status has been updated.');
insert into notification (notification_type, name, description) values (2, 'New Message', 'You have received a new message.');
insert into notification (notification_type, name, description) values (3, 'Job Offer', 'You have received a job offer.');

insert into position (name) values ('Software Engineer');
insert into position (name) values ('Data Scientist');
insert into position (name) values ('Product Manager');

insert into progress_status (name) values ('In Progress');
insert into progress_status (name) values ('Completed');
insert into progress_status (name) values ('Failed');

insert into site (name, domain_name) values ('LinkedIn', 'linkedin.com');
insert into site (name, domain_name) values ('GitHub', 'github.com');
insert into site (name, domain_name) values ('Stack Overflow', 'stackoverflow.com');

insert into skill (name) values ('Java');
insert into skill (name) values ('Python');
insert into skill (name) values ('C++');

insert into start_up (is_paid, company_name, name, location, requirements, description) values (true, 'TechCo', 'Developer', 'New York', 'Experience with Java', 'Exciting startup looking for a developer.');
insert into start_up (is_paid, company_name, name, location, requirements, description) values (false, 'InnovateX', 'Data Scientist', 'San Francisco', 'Knowledge of Machine Learning', 'Innovative startup seeking a data scientist.');
insert into start_up (is_paid, company_name, name, location, requirements, description) values (true, 'BuildIT', 'Product Manager', 'Boston', 'Experience in product management', 'Growing startup needs a product manager.');

insert into required_skill (skill_id, start_up_id) values (1, 1);
insert into required_skill (skill_id, start_up_id) values (2, 2);
insert into required_skill (skill_id, start_up_id) values (3, 3);

insert into start_up_status (date, progress_status_id, start_up_id) values ('2023-10-01', 1, 1);
insert into start_up_status (date, progress_status_id, start_up_id) values ('2023-10-02', 2, 2);
insert into start_up_status (date, progress_status_id, start_up_id) values ('2023-10-03', 3, 3);

insert into status (name) values ('Active');
insert into status (name) values ('Inactive');

insert into ticket_status (name) values ('Open');
insert into ticket_status (name) values ('Closed');
insert into ticket_status (name) values ('In Progress');

insert into university (name, location) values ('MIT', 'Cambridge, MA');
insert into university (name, location) values ('Stanford', 'Stanford, CA');
insert into university (name, location) values ('Harvard', 'Cambridge, MA');

insert into users (registration_date, phone, first_name, last_name, description, email) values ('2023-09-01', '1234567890', 'John', 'Doe', 'Software Developer', 'john.doe@example.com');
insert into users (registration_date, phone, first_name, last_name, description, email) values ('2023-09-02', '0987654321', 'Jane', 'Smith', 'Data Scientist', 'jane.smith@example.com');

insert into app_user_details (user_id, email, password) values (1, 'john.doe@example.com', 'password123');
insert into app_user_details (user_id, email, password) values (2, 'jane.smith@example.com', 'password456');

insert into employee (user_id) values (1);
insert into employee (user_id) values (2);

insert into employee_cv (employee_id, link) values (1, 'https://example.com/cv/john_doe');
insert into employee_cv (employee_id, link) values (2, 'https://example.com/cv/jane_smith');

insert into application_cv (application_status_id, date, employee_cv_id, start_up_id) values (1, '2022-09-15', 1, 1);
insert into application_cv (application_status_id, date, employee_cv_id, start_up_id) values (2, '2023-09-16', 2, 2);

insert into employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id) values ('2019-09-01', '2023-06-01', 1, 1, 1, 1);
insert into employee_education (beginning_date, finishing_date, degree_type_id, employee_user_id, major_id, university_id) values ('2018-09-01', '2022-06-01', 2, 2, 2, 2);

insert into employee_skill (employee_id, skill_id) values (1, 1);
insert into employee_skill (employee_id, skill_id) values (2, 2);

insert into employer (user_id) values (1);
insert into employer (user_id) values (2);

insert into employer_start_up (employer_id, start_up_id) values (1, 1);
insert into employer_start_up (employer_id, start_up_id) values (2, 2);

insert into link (site_id, user_id, link) values (1, 1, 'https://linkedin.com/johndoe');
insert into link (site_id, user_id, link) values (2, 2, 'https://github.com/janesmith');

insert into login_history (date, user_id) values ('2023-09-01 08:00:00', 1);
insert into login_history (date, user_id) values ('2023-09-02 09:00:00', 2);

insert into startup_employee (starting_date, employee_id, position_id, start_up_id) values ('2023-09-01', 1, 1, 1);
insert into startup_employee (starting_date, employee_id, position_id, start_up_id) values ('2023-09-01', 2, 2, 2);

insert into support_ticket (ticket_status_id, user_id, name, description) values (1, 1, 'Issue 1', 'Problem with application');
insert into support_ticket (ticket_status_id, user_id, name, description) values (2, 2, 'Issue 2', 'Problem with account');

insert into user_notification (date, notification_id, user_id) values ('2023-09-01 10:00:00', 1, 1);
insert into user_notification (date, notification_id, user_id) values ('2023-09-02 11:00:00', 2, 2);

insert into user_roles (user_id, role) values (1, 'ROLE_USER');
insert into user_roles (user_id, role) values (2, 'ROLE_ADMIN');

insert into user_status (status_id, user_id) values (1, 1);
insert into user_status (status_id, user_id) values (2, 2);

insert into work_employee (starting_date, employee_id, position_id, company_name) values ('2022-09-01', 1, 1, 'TechCorp');
insert into work_employee (starting_date, employee_id, position_id, company_name) values ('2022-09-01', 2, 2, 'DataSolutions');
