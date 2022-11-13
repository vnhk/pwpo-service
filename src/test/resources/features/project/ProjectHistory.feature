Feature: Project History tests

  Scenario: the client wants to check history of the project
    When the client wants to check history of the project with id = 4
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 1        | 1           | 1            |
    And the client sees history
      | id | editor | expired             |
      | 1  | joedoe | 2022-11-12T14:21:51 |

  Scenario: the client wants to check history details of the project
    When the client wants to check history details with id = 1 of the project with id = 4
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 1        | 1           | 1            |
    And the client sees project history details
      | id | name       | shortForm | summary            | status | description | owner  | editor |
      | 1  | Monitoring | MONPR     | Project monitoring | New    | description | joedoe | joedoe |
