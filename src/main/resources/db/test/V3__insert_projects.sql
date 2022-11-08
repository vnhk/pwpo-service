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

INSERT INTO PROJECT (ID, CREATED, DESCRIPTION, NAME, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID,
                     UPDATED)
VALUES (5, '2022-10-12',
        'Project that is used for testing application', 'Project for tests', 'TEST', 'IN_PROGRESS', '', 1, 1,
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

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (7, 5, 1, 'TESTER', '2022-10-12', '2022-10-10');