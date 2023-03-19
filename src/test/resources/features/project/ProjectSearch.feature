Feature: Project Search

  Scenario: the client wants to get all projects with name Search project 1 or Search project 2
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to search for "project" with criteria
      | id | type    | attr      | operator | value            |
      | C1 | project | name      | equals   | Search project 1 |
      | C2 | project | name      | equals   | Search project 2 |
      | C3 | project | shortForm | contains | SEARCH           |
    And criteria are combined into groups
      | id | operator | queries |
      | G1 | OR       | C1,C2   |
      | G2 | AND      | G1,C3   |
    Then the client performs search
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 2        | 1           | 2            |

  Scenario: the client wants to search projects with criteria
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to search for "project" with criteria
      | id | type    | attr      | operator | value  |
      | C1 | project | status    | equals   | NEW    |
      | C2 | project | shortForm | contains | SEARCH |
    And criteria are combined into groups
      | id | operator | queries |
      | G1 | AND      | C1,C2   |
    Then the client performs search
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 3        | 1           | 3            |

  Scenario: the client wants to search projects with criteria
    Given the "joedoe" user with roles: "ROLE_MANAGER" is logged
    When the client wants to search for "project" with criteria
      | id | type    | attr        | operator | value  |
      | C1 | project | name        | contains | test   |
      | C2 | project | description | contains | test   |
      | C3 | project | summary     | contains | test   |
      | C4 | project | shortForm   | contains | SEARCH |
    And criteria are combined into groups
      | id | operator | queries  |
      | G1 | OR       | C1,C2,C3 |
      | G2 | AND      | G1,C4    |
    Then the client performs search
    And the client receives api response with projects
      | id | name                  | shortForm | summary     | owner | status      |
      | 7  | Search project 2      | SEARCH2   | Search      | 1     | In Progress |
      | 8  | Search project 3      | SEARCH3   | Search test | 2     | New         |
      | 9  | Search test project 4 | SEARCH4   | Search      | 2     | New         |
