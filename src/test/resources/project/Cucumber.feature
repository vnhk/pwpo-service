Feature: Project controller endpoint tests

  Scenario: the client makes GET call to get all projects
    When the client wants to receive all the projects
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 5        | 1           | 5            |

  Scenario: the client makes GET call to get project with given id
    When the client wants to receive project by id = 5
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 1        | 1           | 1            |

  Scenario: the client wants to create new project, but project with given name already exists
    When the client wants to create project with following data
      | name              | description         | shortForm | summary         | owner |
      | Project for tests | example description | C0001     | example summary | 1     |
    Then the client receives 400 status
    And the client receives validation details
      | field | code             | message                                     |
      | name  | FIELD_VALIDATION | Project with the given name already exists! |

  Scenario: the client wants to create new project
    When the client wants to create project with following data
      | name        | description         | shortForm | summary         | owner |
      | New Project | example description | C0002     | example summary | 1     |
    Then the client receives 200 status
    And the client receives newly created Project
      | name              | shortForm | summary         | owner | status |
      | Project for tests | C0001     | example summary | 1     | New    |