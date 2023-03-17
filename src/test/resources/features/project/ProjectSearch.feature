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

  Scenario: the client wants to get all project with name Monitoring Project or in In Progress status
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