Feature: Project Search

  Scenario: the client wants to get all projects with name Monitoring Project or Building Project
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to search for "project" with criteria
      | id | type    | attr | operator | value              |
      | C1 | project | name | equals   | Monitoring Project |
      | C2 | project | name | equals   | Building Project   |
    And criteria are combined into groups
      | id | operator | queries |
      | G1 | OR       | C1,C2   |
    Then the client performs search
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 2        | 1           | 2            |

  Scenario: the client wants to get all projects with name Monitoring Project or in In Progress status
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to search for "project" with criteria
      | id | type    | attr   | operator | value              |
      | C1 | project | name   | equals   | Monitoring Project |
      | C2 | project | status | equals   | IN_PROGRESS        |
    And criteria are combined into groups
      | id | operator | queries |
      | G1 | OR       | C1,C2   |
    Then the client performs search
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 4        | 1           | 4            |

  Scenario: the client wants to get all projects with description/summary/name containing "test"
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to search for "project" with criteria
      | id | type    | attr        | operator | value |
      | C1 | project | name        | contains | test  |
      | C2 | project | description | contains | test  |
      | C3 | project | summary     | contains | test  |
    And criteria are combined into groups
      | id | operator | queries  |
      | G1 | OR       | C1,C2,C3 |
    Then the client performs search
    And the client receives api response with projects
      | id | name               | shortForm | summary                  | owner | status      |
      | 1  | Monitoring Project | MONPR     | Project about monitoring | 2     | New         |
      | 3  | Watching Project   | WATPR     | Project about watching   | 1     | In Progress |
      | 5  | Project for tests  | TEST      | Project                  | 1     | In Progress |
