INSERT INTO PROJECT (ID, CREATED, NAME, DESCRIPTION, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (5, '2023-02-02',
        'TaskMgmt web application', 'TaskMgmt is a web-based task management application designed to help individuals and teams stay organized and productive. The application is built using modern web technologies, including HTML5, CSS3, and JavaScript, and is optimized for performance and scalability.

The application features a responsive user interface that adapts to different screen sizes and devices, providing a seamless user experience across desktop and mobile platforms. The frontend of the application is built using the popular React framework, which allows for fast and efficient rendering of dynamic data and components.

The backend of the application is built using Node.js, a powerful runtime environment for JavaScript that allows for server-side programming. The application uses a RESTful API architecture, allowing for easy integration with other systems and services. Data is stored in a scalable NoSQL database such as MongoDB, which ensures that the application can handle large volumes of data with ease.

The application includes a range of features for task management, including the ability to create and manage tasks, set deadlines and reminders, assign tasks to team members, and track progress. The application also includes collaboration features, such as real-time updates, comments, and file attachments, allowing team members to work together efficiently.

TaskMgmt also includes powerful reporting and analytics features, including metrics such as task completion rates, time spent on tasks, and team member productivity. These metrics are displayed using data visualizations such as charts and graphs, allowing for easy interpretation and analysis of data.

In conclusion, TaskMgmt is a modern, scalable, and feature-rich task management application that leverages the latest web technologies to provide a powerful and user-friendly experience.',
        'T-MGMT', 'NEW', 'Technical description about TaskMgmt application.', 1, 1,
        '2023-02-02');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (55, 5, 1, 'PRODUCT_OWNER', '2023-02-02', '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (55, '2023-02-02',
        'Develop a RESTful API that can handle CRUD operations for tasks, including the ability to create, read, update, and delete tasks',
        '2023-12-28', '1660', 'TMG001',
        'CRITICAL', 'NEW', 'RESTful API', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (56, '2023-02-02',
        'Implement real-time updates using WebSockets, allowing team members to collaborate on tasks and receive instant notifications of changes.',
        '2023-12-28', '1660', 'TMG002',
        'MEDIUM', 'NEW', 'Real-time updates', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (57, '2023-02-02',
        'Design and develop a scalable NoSQL database schema that can handle large volumes of data and support efficient querying for reporting and analytics features.',
        '2023-12-28', '1300', 'TMG003',
        'CRITICAL', 'NEW', 'DB Schema', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (58, '2023-02-02',
        'Implement a responsive user interface using React, with components for creating and managing tasks, setting deadlines and reminders, and assigning tasks to team members',
        '2023-12-28', '1960', 'TMG004',
        'CRITICAL', 'NEW', 'Responsive UI', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (59, '2023-02-02',
        'Design and implement authentication and authorization mechanisms to secure the application, including user registration, login, and role-based access control.',
        '2023-12-28', '1260', 'TMG005',
        'HIGH', 'NEW', 'Authentication and authorization', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (60, '2023-02-02',
        'Develop reporting and analytics features using data visualization libraries such as D3.js, allowing users to view and analyze task completion rates, time spent on tasks, and team member productivity.',
        '2023-12-28', '1160', 'TMG006',
        'LOW', 'NEW', 'Analytics features', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (61, '2023-02-02',
        'Integrate with third-party services such as Google Calendar and Slack, allowing users to synchronize tasks and receive notifications and reminders within those platforms.',
        '2023-12-28', '860', 'TMG007',
        'HIGH', 'NEW', '3rd Party apps integration', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (62, '2023-02-02',
        'Implement a search functionality using a search engine such as Elasticsearch, allowing users to search for tasks based on various criteria such as title, description, and assigned team member.',
        '2023-12-28', '1660', 'TMG008',
        'HIGH', 'NEW', 'Smart Search', 'STORY', 2,
        2, 2, 5, '2023-02-02');

-- RISKS
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 1
       , 'Technical Complexity: As the application is built using modern web technologies, it is possible that the development team may face technical challenges that could delay the project or affect its quality.'
       , 4, 'RISK',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 2
       , 'Security Risks: TaskMgmt stores sensitive data such as user login credentials and task details, making it important to ensure that the application is secure and protected against potential threats such as hacking attempts and data breaches.'
       , 1, 'RISK',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 3
       , 'Integration Challenges: Integrating TaskMgmt with other systems and services may pose challenges, particularly if those systems use different APIs or protocols.'
       , 4, 'RISK',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 4
       , 'Performance Issues: As the application is designed to handle large volumes of data, it is essential that it is optimized for performance to ensure that it can deliver fast and responsive performance even under heavy loads.'
       , 4, 'RISK',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 5
       , 'User Adoption: User adoption is a critical factor for the success of any software application, and it is possible that TaskMgmt may face challenges in gaining user adoption due to the competitive nature of the task management market or due to a lack of user awareness or training.'
       , 4, 'RISK',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 6
       , 'Budget and Resource Constraints: Developing and maintaining a complex application such as TaskMgmt requires significant financial and human resources, and it is possible that budget and resource constraints may affect the project''s timeline and quality.'
       , 5, 'RISK',5);

-- GOALS
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 7
       , 'Develop a high-quality and scalable application that meets the needs of individual and team task management.'
       , 3, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 8
       , 'Ensure that TaskMgmt is secure and protected against potential threats, with appropriate measures such as data encryption, authentication, and access controls.'
       , 3, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 9
       , 'Provide seamless integration with other systems and services, using open and standard APIs and protocols.'
       , 5, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 10
       , 'Optimize TaskMgmt for performance and scalability, with efficient algorithms, caching, and load balancing.'
       , 3, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 11
       , 'Increase user adoption by providing a user-friendly and intuitive interface, with onboarding tutorials and user documentation.'
       , 4, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 12
       , 'Provide regular updates and maintenance to ensure that TaskMgmt remains up-to-date with the latest web technologies and security patches.'
       , 5, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 13
       , 'Foster collaboration and communication among team members, using real-time updates, comments, and file attachments.'
       , 4, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 14
       , 'Provide insightful reporting and analytics features, with customizable metrics and data visualizations.'
       , 5, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 15
       , 'Improve team productivity by providing features such as task assignment and tracking, deadline management, and progress monitoring.'
       , 2, 'GOAL',5);
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES ( 16
       , 'Manage project resources effectively, by ensuring that the development team has adequate budget and resources, and by tracking project milestones and risks.'
       , 5, 'GOAL',5);