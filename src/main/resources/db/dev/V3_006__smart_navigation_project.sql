INSERT INTO PROJECT (ID, CREATED, NAME, DESCRIPTION, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID, UPDATED)
VALUES (6, '2023-05-01', 'Smart Navigation', 'A mobile app car navigation system', 'SN', 'IN_PROGRESS',
        'Developing a navigation app for drivers to easily navigate while on the road', 3, 2, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (101, '2023-05-14', 'Design user interface for the navigation app', '2023-06-01', 20, 'SN-0001',
        'HIGH', 'IN_PROGRESS', 'Design UI', 'STORY', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (102, '2023-05-14', 'Develop functionality for finding directions and routes', '2023-06-15', 40, 'SN-0002',
        'HIGH', 'NEW', 'Develop route finding', 'STORY', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (103, '2023-05-14', 'Implement map view and geolocation functionality', '2023-06-15', 30, 'SN-0003',
        'HIGH', 'NEW', 'Add map view', 'STORY', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (104, '2023-05-14', 'Add voice directions feature', '2023-06-30', 20, 'SN-0004',
        'MEDIUM', 'NEW', 'Add voice directions', 'STORY', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (105, '2023-05-14', 'Create database schema for storing maps and routes', '2023-05-31', 10, 'SN-0005',
        'HIGH', 'IN_PROGRESS', 'Design database schema', 'STORY', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (106, '2023-05-14', 'Optimize route finding algorithm', '2023-07-01', 40, 'SN-0006',
        'MEDIUM', 'NEW', 'Optimize route finding', 'STORY', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (107, '2023-05-14', 'Add support for multiple languages', '2023-07-15', 30, 'SN-0007',
        'LOW', 'NEW', 'Add multi-language support', 'TASK', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (108, '2023-05-14', 'Create onboarding flow for new users', '2023-06-30', 20, 'SN-0008',
        'LOW', 'NEW', 'Design onboarding flow', 'TASK', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (109, '2023-05-14', 'Test app on various mobile devices', '2023-08-01', 30, 'SN-0009',
        'MEDIUM', 'NEW', 'Test on multiple devices', 'OBJECTIVE', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (110, '2023-05-14', 'Create in-app purchase system for premium features', '2023-08-15', 40, 'SN-0010',
        'LOW', 'NEW', 'Add in-app purchases', 'OBJECTIVE', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (111, '2023-05-14', 'Create settings screen for app preferences', '2023-07-15', 20, 'SN-0011',
        'LOW', 'NEW', 'Design settings screen', 'OBJECTIVE', NULL, 2, 2, 6, '2023-05-14');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (112, '2023-05-14', 'Add support for real-time traffic data', '2023-07-31', 30, 'SN-0012',
        'MEDIUM', 'NEW', 'Add real-time traffic data', 'TASK', NULL, 2, 2, 6, '2023-05-14');

-- RISKS
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (50, 'Delay in delivery of required API from a third-party provider', 4, 'RISK', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (51, 'Technical issues with GPS tracking functionality', 3, 'RISK', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (52, 'User data privacy and security concerns', 5, 'RISK', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (53, 'Unexpected changes in mapping data', 2, 'RISK', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (54, 'Insufficient testing leading to user dissatisfaction', 4, 'RISK', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (55, 'Difficulty in obtaining required licenses for mapping data', 3, 'RISK', 6);


-- GOALS
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (56, 'Develop a user-friendly mobile app for car navigation', 5, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (57, 'Incorporate GPS tracking functionality into the app', 4, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (58, 'Integrate the app with third-party APIs for real-time traffic updates', 3, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (59, 'Ensure the app is compatible with multiple mobile operating systems', 4, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (60, 'Obtain necessary licenses for mapping data', 2, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (61, 'Perform rigorous testing to ensure app reliability and performance', 5, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (62, 'Implement robust security measures to protect user data', 4, 'GOAL', 6);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (63, 'Provide users with up-to-date and accurate navigation information', 5, 'GOAL', 6);
