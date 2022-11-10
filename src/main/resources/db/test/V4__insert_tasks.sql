INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (1, '2022-10-10',
        'Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-11-23', '25', 'NO0001',
        'LOW', 'NEW', 'Task about monitoring', 'OBJECTIVE', 1,
        2, 2, 1, '2022-10-10');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (2, '2022-10-11', 'Illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-12-28', '25', 'NO0002',
        'CRITICAL', 'DONE', 'Task about playing', 'STORY', 2,
        2, 2, 1, '2022-10-10');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (3, '2022-10-12', 'Dolorem eum fugiat quo voluptas nulla pariatur?',
        '2020-12-30', '25', 'NO0003',
        'HIGH', 'IN_PROGRESS', 'Task about watching', 'FEATURE', 2,
        2, 2, 1, '2022-10-10');