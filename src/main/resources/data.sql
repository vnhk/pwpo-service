INSERT INTO USER_DETAILS
VALUES (1, 'Joe Doe', 'joedoe');
INSERT INTO USER_DETAILS
VALUES (2, 'Max Musserman', 'maxmusserman');
INSERT INTO PROJECT
VALUES (1, '2022-10-10',
        'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        false, '2022-10-10', 'Monitoring Project', 'MONPR', 'OPEN', 'Project about monitoring', 1, 2);
INSERT INTO PROJECT
VALUES (2, '2022-10-11',
        'Aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        false, '2022-10-11', 'Building Project', 'BUIPR', 'OPEN', 'Project about building', 1, 2);
INSERT INTO PROJECT
VALUES (3, '2022-10-12',
        'Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        false, '2022-10-12', 'Watching Project', 'WATPR', 'IN_PROGRESS', 'Project about watching', 2, 1);

/*
 ID,CREATED,DESCRIPTION,
 DUE_DATE,ESTIMATION,IS_DELETED,MODIFIED,NUMBER,
 PRIORITY,STATUS,SUMMARY,TYPE,ASSIGNEE_ID,
 CREATED_BY_ID,OWNER_ID,PROJECT_ID
 */

INSERT INTO TASK
VALUES (1, '2022-10-10','Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-12-12', '25h', false, '2022-10-10', 'NO0001',
        1, 2, 'Task about monitoring', 1, 1,
        2, 2, 1);

INSERT INTO TASK
VALUES (2, '2022-10-11','Illum qui dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-12-12', '25h', false, '2022-10-10', 'NO0002',
        1, 3, 'Task about playing', 1, 2,
        2, 2, 1);

INSERT INTO TASK
VALUES (3, '2022-10-12','Dolorem eum fugiat quo voluptas nulla pariatur?',
        '2023-12-12', '25h', false, '2022-10-10', 'NO0003',
        3, 1, 'Task about watching', 1, 2,
        2, 2, 1);