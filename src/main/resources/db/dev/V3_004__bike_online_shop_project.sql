INSERT INTO PROJECT (ID, CREATED, NAME, DESCRIPTION, SHORT_FORM, STATUS, SUMMARY, CREATED_BY_ID, OWNER_ID, UPDATED)
VALUES (4, '2022-01-01', 'Cheap Bike', 'A web shop that sells bikes and accessories', 'CB', 'NEW',
        'Develop an e-commerce platform for bike enthusiasts', 6, 2, '2022-01-01');


INSERT INTO USER_PROJECT (ID, PROJECT_ID, USER_ID, ROLE, CREATED, UPDATED)
VALUES (200, 4, 2, 'PRODUCT_OWNER', '2022-01-01', '2022-01-01');

--TASKS
INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER, PRIORITY, STATUS, SUMMARY, TYPE,
                  ASSIGNEE_ID, CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (200, '2022-01-01', 'Research e-commerce platforms and determine which is most suitable for Cheap Bike',
        '2022-01-15', 120, 'CB0001', 'LOW', 'NEW', 'Research e-commerce platforms', 'STORY', 6, 6, 2, 4, '2022-01-01');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER, PRIORITY, STATUS, SUMMARY, TYPE,
                  ASSIGNEE_ID, CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (201, '2022-01-15', 'Design the user interface for the e-commerce platform', '2022-02-15', 130, 'CB0002', 'HIGH',
        'NEW', 'Design user interface', 'STORY', 6, 6, 2, 4, '2022-01-15');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER, PRIORITY, STATUS, SUMMARY, TYPE,
                  ASSIGNEE_ID, CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (202, '2022-02-15', 'Develop a backend system for the e-commerce platform', '2022-04-01', 600, 'CB0003', 'HIGH',
        'NEW', 'Develop backend system', 'STORY', 2, 6, 2, 4, '2022-02-15');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER, PRIORITY, STATUS, SUMMARY, TYPE,
                  ASSIGNEE_ID, CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (203, '2022-03-01', 'Create a database schema for the e-commerce platform', '2022-03-15', 200, 'CB0004',
        'MEDIUM',
        'NEW', 'Create database schema', 'STORY', NULL, 6, 2, 4, '2022-03-01');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER, PRIORITY, STATUS, SUMMARY, TYPE,
                  ASSIGNEE_ID, CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (204, '2022-03-15', 'Integrate payment gateway into the e-commerce platform', '2022-04-15', 400, 'CB0005',
        'HIGH',
        'NEW', 'Integrate payment gateway', 'STORY', 6, 6, 2, 4, '2022-03-15');

INSERT INTO TASK (ID, CREATED, DESCRIPTION,
                  DUE_DATE, ESTIMATION, NUMBER,
                  PRIORITY, STATUS, SUMMARY, TYPE, ASSIGNEE_ID,
                  CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (205, '2022-06-10',
        'As part of the CheapBike e-commerce website, we need to create a dedicated page for our bike products. The page should showcase all available bike models, their features, specifications, and pricing. It should also include high-quality product images and videos, as well as customer reviews and ratings. The page should be optimized for SEO to ensure it ranks well on search engines and drives traffic to the website.',
        '2022-07-01', 180, 'CB0006', 'MEDIUM', 'IN_PROGRESS', 'Product page for bikes', 'FEATURE', NULL, 6, 2, 4,
        '2022-06-15'),
       (206, '2022-06-10',
        'Create product page for accessories: In addition to bikes, CheapBike also sells a wide range of bike accessories such as helmets, locks, lights, and racks. We need a dedicated page for these products that showcases their features, specifications, and pricing. The page should also include high-quality images and videos, as well as customer reviews and ratings. The page should be optimized for SEO to ensure it ranks well on search engines and drives traffic to the website.',
        '2022-07-01', 180, 'CB0007', 'MEDIUM', 'IN_PROGRESS',
        'Product page for accessories', 'FEATURE', NULL, 6, 2, 4, '2022-06-15'),
       (207, '2022-06-15',
        'As part of the CheapBike e-commerce website redesign, we need to create a modern and user-friendly layout that enhances the user experience and improves the overall look and feel of the website. The layout should be responsive and work seamlessly on all devices. It should also be optimized for SEO to ensure it ranks well on search engines and drives traffic to the website',
        '2022-07-10', 190, 'CB0008', 'MEDIUM', 'IN_PROGRESS',
        'Website layout design', 'OBJECTIVE', 2, 6, 2, 4, '2022-06-15'),
       (208, '2022-06-20',
        'CheapBike customers need a secure and user-friendly login page to access their account information, view order history, and manage their preferences. The login page should be easy to use and work seamlessly on all devices. It should also be optimized for security to ensure customers'' personal and financial information is protected',
        '2022-07-05', 290, 'CB0009', 'MEDIUM', 'IN_PROGRESS', 'Login page',
        'FEATURE', 6, 6, 2, 4, '2022-06-20'),
       (209, '2022-06-20',
        'CheapBike customers need a simple and easy-to-use registration page to create a new account on the website. The registration page should capture all necessary customer information, including name, email, and password. It should also be optimized for security to ensure customers personal and financial information is protected',
        '2022-07-05', 120, 'CB0010', 'MEDIUM', 'IN_PROGRESS',
        'User registration page', 'FEATURE', 6, 6, 2, 4, '2022-06-20'),
       (210, '2022-06-25',
        'CheapBike customers need a simple and easy-to-use shopping cart that allows them to add items to their cart, view cart contents, and checkout securely. The shopping cart should be optimized for usability and speed, and it should work seamlessly on all devices.',
        '2022-07-20', 200, 'CB0011', 'MEDIUM', 'IN_PROGRESS',
        'Shopping cart functionality', 'FEATURE', 2, 6, 2, 4, '2022-06-25'),
       (211, '2022-06-30',
        'As part of the CheapBike e-commerce website redesign, we need to create a modern and user-friendly product catalog that showcases all available products in an easy-to-use and organized manner. The catalog should be optimized for usability and speed, and it should work seamlessly on all devices.',
        '2022-07-15', 160, 'CB0012', 'MEDIUM', 'IN_PROGRESS',
        'Product catalog design', 'OBJECTIVE', 6, 6, 2, 4, '2022-06-30'),
       (212, '2022-07-05',
        'In addition to the main product catalog, we need a dedicated product catalog page for each product category (bikes, accessories, etc.) that showcases all available products in that category. The page should be optimized for usability and speed, and it should work seamlessly on all devices',
        '2022-07-25', 160, 'CB0013', 'MEDIUM', 'IN_PROGRESS',
        'Product catalog page', 'FEATURE', 6, 6, 2, 4, '2022-07-05'),
       (213, '2022-07-10',
        'CheapBike customers need a secure and user-friendly checkout page that allows them to enter their shipping and billing information, select a payment method, and complete their order. The checkout page should be optimized for usability and speed, and it should work seamlessly on all devices.',
        '2022-07-30', 160, 'CB0014', 'MEDIUM', 'IN_PROGRESS',
        'Checkout page design', 'OBJECTIVE', 2, 6, 2, 4, '2022-07-10'),
       (214, '2022-07-15',
        'CheapBike customers need a secure and reliable payment functionality that allows them to pay for their orders using a variety of payment methods, including credit cards, PayPal, and more. The payment functionality should be optimized for security and speed, and it should work seamlessly on all devices.',
        '2022-08-05', 200, 'CB0015', 'MEDIUM', 'IN_PROGRESS',
        'Payment functionality', 'FEATURE', 2, 6, 2, 4, '2022-07-15');

INSERT INTO TASK (ID, CREATED, DESCRIPTION, DUE_DATE, ESTIMATION, NUMBER, PRIORITY, STATUS, SUMMARY, TYPE,
                  ASSIGNEE_ID, CREATED_BY_ID, OWNER_ID, PROJECT_ID, UPDATED)
VALUES (215, '2022-04-01', 'Implement search functionality for the e-commerce platform', '2022-05-01', 330, 'CB0016',
        'HIGH', 'NEW', 'Implement search functionality', 'STORY', 2, 6, 2, 4, '2022-04-01');

-- RISKS
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (100, 'Supplier may delay delivery of bike parts', 3, 'RISK', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (101, 'Website may experience technical issues during high traffic periods', 4, 'RISK', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (102, 'Unforeseen increase in cost of materials', 2, 'RISK', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (103, 'Legal issues with product licensing', 5, 'RISK', 4);

-- GOALS
INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (104, 'Develop a user-friendly website', 4, 'GOAL', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (105, 'Establish partnerships with bike manufacturers', 3, 'GOAL', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (106, 'Expand product line to include bike accessories', 2, 'GOAL', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (107, 'Increase customer base through targeted marketing campaigns', 5, 'GOAL', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (108, 'Optimize supply chain to reduce costs', 4, 'GOAL', 4);

INSERT INTO GOAL_RISK (ID, CONTENT, PRIORITY, TYPE, PROJECT_ID)
VALUES (109, 'Provide exceptional customer service to increase customer satisfaction and loyalty', 5, 'GOAL', 4);