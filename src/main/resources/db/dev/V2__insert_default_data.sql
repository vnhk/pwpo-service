INSERT INTO USER_ACCOUNT (ID, CREATED, FULL_NAME, NICK, UPDATED)
VALUES (1, '2022-10-10', 'Joe Doe', 'joedoe', '2022-10-10');

INSERT INTO USER_ACCOUNT (ID, CREATED, FULL_NAME, NICK, UPDATED)
VALUES (2, '2022-10-10', 'Max Musserman', 'maxmusserman', '2022-10-10');

INSERT INTO USER_ACCOUNT (ID, CREATED, FULL_NAME, NICK, UPDATED, PASSWORD)
VALUES (3, '2022-10-10', 'Tom Smith', 'tomsmith', '2022-10-10', '$2a$10$msMkxMCSbrxrmc67J7gk8.B98Gw66yfrr.Y/TtR8SBcpnnCoLepcW');


INSERT INTO PROJECT (ID, CREATED, DESCRIPTION, NAME, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (1, '2022-10-10',
        'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        'Monitoring Project', 'MONPR', 'NEW', 'Project about monitoring', 1, 2, '2022-10-10');

INSERT INTO PROJECT (ID, CREATED, DESCRIPTION, NAME, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (2, '2022-10-11',
        'Aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        'Building Project', 'BUIPR', 'NEW', 'Project about building', 1, 2, '2022-10-10');

INSERT INTO PROJECT (ID, CREATED, DESCRIPTION, NAME, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (3, '2022-10-12',
        'Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        'Watching Project', 'WATPR', 'IN_PROGRESS', 'Project about watching', 2, 1, '2022-10-10');

INSERT INTO PROJECT (ID, CREATED, DESCRIPTION, NAME, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (4, '2022-10-12',
        'Project without users added', 'Where users?', 'NOUSRPR', 'IN_PROGRESS', 'Project without users added', 1, 1,
        '2022-10-10');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (1, 1, 1, 'DEVELOPER', '2022-10-12', '2022-10-10');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (2, 1, 2, 'DEVELOPER', '2022-10-12', '2022-10-10');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (3, 2, 1, 'DEVELOPER', '2022-10-12', '2022-10-10');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (4, 2, 2, 'DEVELOPER', '2022-10-12', '2022-10-10');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (5, 3, 1, 'TESTER', '2022-10-12', '2022-10-10');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (6, 3, 2, 'MANAGER', '2022-10-12', '2022-10-10');


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