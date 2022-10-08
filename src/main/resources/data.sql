INSERT INTO USER_DETAILS
VALUES (1, 'Joe Doe', 'joedoe');
INSERT INTO USER_DETAILS
VALUES (2, 'Max Musserman', 'maxmusserman');


/*
  ID
 CREATED
 DESCRIPTION
 IS_DELETED
 MODIFIED
 NAME
 SHORT FORM
 STATUS
 SUMMARY
 CREATED_BY_ID
 OWNER_ID
 */

INSERT INTO PROJECT
VALUES (1, '2022-10-10',
        'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        false, '2022-10-10', 'Monitoring Project', 'MONPR', 'NEW', 'Project about monitoring', 1, 2);
INSERT INTO PROJECT
VALUES (2, '2022-10-11',
        'Aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        false, '2022-10-11', 'Building Project', 'BUIPR', 'NEW', 'Project about building', 1, 2);
INSERT INTO PROJECT
VALUES (3, '2022-10-12',
        'Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        false, '2022-10-12', 'Watching Project', 'WATPR', 'IN_PROGRESS', 'Project about watching', 2, 1);

INSERT INTO PROJECT
VALUES (4, '2022-10-12',
        'Project without users added',
        false, '2022-10-12', 'Where users?', 'NOUSRPR', 'IN_PROGRESS', 'Project without users added', 1,
        1);

-- ID  	PROJECT_ID  	USER_ID   ROLE

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE)
VALUES (1, 1, 1, 'DEVELOPER');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE)
VALUES (2, 1, 2, 'DEVELOPER');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE)
VALUES (3, 2, 1, 'DEVELOPER');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE)
VALUES (4, 2, 2, 'DEVELOPER');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE)
VALUES (5, 3, 1, 'TESTER');

INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE)
VALUES (6, 3, 2, 'MANAGER');
/*
 ID,CREATED,DESCRIPTION,
 DUE_DATE,ESTIMATION,IS_DELETED,MODIFIED,NUMBER,
 PRIORITY,STATUS,SUMMARY,TYPE,ASSIGNEE_ID,
 CREATED_BY_ID,OWNER_ID,PROJECT_ID
 */

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, IS_DELETED, MODIFIED, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID)
VALUES (1, '2022-10-10',
        'Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-11-23', '25', false, '2022-10-10', 'NO0001',
        'LOW', 'NEW', 'Task about monitoring', 'OBJECTIVE', 1,
        2, 2, 1);

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, IS_DELETED, MODIFIED, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID)
VALUES (2, '2022-10-11', 'Illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-12-28', '25', false, '2022-10-10', 'NO0002',
        'CRITICAL', 'DONE', 'Task about playing', 'STORY', 2,
        2, 2, 1);

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, IS_DELETED, MODIFIED, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID)
VALUES (3, '2022-10-12', 'Dolorem eum fugiat quo voluptas nulla pariatur?',
        '2020-12-30', '25', false, '2022-10-10', 'NO0003',
        'HIGH', 'IN_PROGRESS', 'Task about watching', 'FEATURE', 2,
        2, 2, 1);