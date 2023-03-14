Feature: Project controller endpoint tests

  Scenario: the client wants to get all projects
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to receive all the projects
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 5        | 1           | 5            |

  Scenario: the client wants to get project with given id
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to receive project by id = 5
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 1        | 1           | 1            |

  Scenario: the client wants to create new project, but project with given name already exists
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to create project with following data
      | name              | description         | shortForm | summary         | owner |
      | Project for tests | example description | C0001     | example summary | 1     |
    Then the client receives 400 status
    And the client receives bad request details
      | field | code             | message                                     |
      | name  | FIELD_VALIDATION | Project with the given name already exists! |

  Scenario: the client wants to create new project, but short name is incorrect and name is missing
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to create project with following data
      | name | description         | shortForm                           | summary         | owner |
      |      | example description | C0033455121321312312312332131231201 | example summary | 1     |
    Then the client receives 400 status
    And the client receives bad request details
      | field     | code             | message                       |
      | shortForm | FIELD_VALIDATION | size must be between 1 and 15 |
      | name      | FIELD_VALIDATION | must not be null              |

  Scenario: the client wants to create new project
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to create project with following data
      | name        | description         | shortForm | summary         | owner |
      | New Project | example description | C0002     | example summary | 1     |
    Then the client receives 200 status
    And the client receives newly created Project
      | name        | shortForm | summary         | owner | status |
      | New Project | C0002     | example summary | 1     | New    |

  Scenario: the client wants to edit existing project
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    Given the client wants to edit project with id = 1
    When the client sets following project values
      | name           | description        | shortForm | summary        | owner | status |
      | Edited Project | edited description | EDITED    | edited summary | 2     | New    |
    Then the client receives 200 status
    And the client receives edited Project
      | id | name           | shortForm | summary        | owner | status |
      | 1  | Edited Project | EDITED    | edited summary | 2     | New    |

  Scenario: the client wants to edit project, but project does not exist
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    Given the client wants to edit project with id = 1500
    When the client sets following project values
      | name           | description        | shortForm | summary        | owner | status |
      | Edited Project | edited description | EDITED    | edited summary | 2     | New    |
    Then the client receives 400 status
    And the client receives bad request details
      | code               | message                     |
      | GENERAL_VALIDATION | The project does not exist! |

  Scenario: the client wants to edit project, but project with given name already exists
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    Given the client wants to edit project with id = 3
    When the client sets following project values
      | name              | description        | shortForm | summary        | owner | status |
      | Project for tests | edited description | C0005     | edited summary | 2     | New    |
    Then the client receives 400 status
    And the client receives bad request details
      | field | code             | message                                     |
      | name  | FIELD_VALIDATION | Project with the given name already exists! |

  Scenario: the client wants to edit project, but project with given short form already exists
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    Given the client wants to edit project with id = 3
    When the client sets following project values
      | name        | description        | shortForm | summary        | owner | status |
      | edited name | edited description | BUIPR     | edited summary | 2     | New    |
    Then the client receives 400 status
    And the client receives bad request details
      | field     | code             | message                                           |
      | shortForm | FIELD_VALIDATION | Project with the given short form already exists! |

  Scenario: the client wants to create new project, but project with given name and short form already exists
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to create project with following data
      | name              | description         | shortForm | summary         | owner |
      | Project for tests | example description | BUIPR     | example summary | 1     |
    Then the client receives 400 status
    And the client receives bad request details
      | field     | code             | message                                           |
      | name      | FIELD_VALIDATION | Project with the given name already exists!       |
      | shortForm | FIELD_VALIDATION | Project with the given short form already exists! |
