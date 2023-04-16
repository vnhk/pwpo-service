INSERT INTO PROJECT (ID, CREATED, NAME, DESCRIPTION, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (5, '2023-02-02',
        'TaskMgmt web application', 'TaskMgmt is a web-based task management application designed to help individuals and teams stay organized and productive. The application is built using modern web technologies, including HTML5, CSS3, and JavaScript, and is optimized for performance and scalability.

The application features a responsive user interface that adapts to different screen sizes and devices, providing a seamless user experience across desktop and mobile platforms. The frontend of the application is built using the popular React framework, which allows for fast and efficient rendering of dynamic data and components.

The backend of the application is built using Node.js, a powerful runtime environment for JavaScript that allows for server-side programming. The application uses a RESTful API architecture, allowing for easy integration with other systems and services. Data is stored in a scalable NoSQL database such as MongoDB, which ensures that the application can handle large volumes of data with ease.

The application includes a range of features for task management, including the ability to create and manage tasks, set deadlines and reminders, assign tasks to team members, and track progress. The application also includes collaboration features, such as real-time updates, comments, and file attachments, allowing team members to work together efficiently.

TaskMgmt also includes powerful reporting and analytics features, including metrics such as task completion rates, time spent on tasks, and team member productivity. These metrics are displayed using data visualizations such as charts and graphs, allowing for easy interpretation and analysis of data.

In conclusion, TaskMgmt is a modern, scalable, and feature-rich task management application that leverages the latest web technologies to provide a powerful and user-friendly experience.', 'T-MGMT', 'NEW', 'Technical description about TaskMgmt application.', 1, 1,
        '2023-02-02');


INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (5, '2023-02-02', 'Develop a RESTful API that can handle CRUD operations for tasks, including the ability to create, read, update, and delete tasks',
        '2023-12-28', '1660', 'TMG001',
        'CRITICAL', 'NEW', 'RESTful API', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (6, '2023-02-02', 'Implement real-time updates using WebSockets, allowing team members to collaborate on tasks and receive instant notifications of changes.',
        '2023-12-28', '1660', 'TMG002',
        'MEDIUM', 'NEW', 'Real-time updates', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (7, '2023-02-02', 'Design and develop a scalable NoSQL database schema that can handle large volumes of data and support efficient querying for reporting and analytics features.',
        '2023-12-28', '1300', 'TMG003',
        'CRITICAL', 'NEW', 'DB Schema', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (8, '2023-02-02', 'Implement a responsive user interface using React, with components for creating and managing tasks, setting deadlines and reminders, and assigning tasks to team members',
        '2023-12-28', '1960', 'TMG004',
        'CRITICAL', 'NEW', 'Responsive UI', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (9, '2023-02-02', 'Design and implement authentication and authorization mechanisms to secure the application, including user registration, login, and role-based access control.',
        '2023-12-28', '1260', 'TMG005',
        'HIGH', 'NEW', 'Authentication and authorization', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (10, '2023-02-02', 'Develop reporting and analytics features using data visualization libraries such as D3.js, allowing users to view and analyze task completion rates, time spent on tasks, and team member productivity.',
        '2023-12-28', '1160', 'TMG006',
        'LOW', 'NEW', 'Analytics features', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (11, '2023-02-02', 'Integrate with third-party services such as Google Calendar and Slack, allowing users to synchronize tasks and receive notifications and reminders within those platforms.',
        '2023-12-28', '860', 'TMG007',
        'HIGH', 'NEW', '3rd Party apps integration', 'STORY', 2,
        2, 2, 5, '2023-02-02');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (12, '2023-02-02', 'Implement a search functionality using a search engine such as Elasticsearch, allowing users to search for tasks based on various criteria such as title, description, and assigned team member.',
        '2023-12-28', '1660', 'TMG008',
        'HIGH', 'NEW', 'Smart Search', 'STORY', 2,
        2, 2, 5, '2023-02-02');