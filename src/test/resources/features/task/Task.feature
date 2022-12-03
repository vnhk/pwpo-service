Feature: Task controller endpoint tests

  Scenario: the client wants to get all Task for project
    Given the client is on the project with id = 1
    When the client wants to receive all tasks in project
    Then the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 3        | 1           | 3            |

  Scenario: the client wants to get task by id in project
    Given the client is on the project with id = 1
    When the client wants to receive task with id = 2
    Then the client receives 200 status
    And the client receives APIResponse
      | allFound | currentPage | currentFound |
      | 1        | 1           | 1            |

  Scenario: the client wants to get task by id, but task does not exist
    When the client wants to receive task with id = 0
    Then the client receives 400 status
    And the client receives bad request details
      | code      | message              |
      | NOT_FOUND | Could not find task! |

  Scenario: the client wants to create new task in project
    Given the client is on the project with id = 5
    When the client wants to create task with following data
      | type    | summary              | assignee | owner | dueDate    | priority | description     | estimationInHours | estimationInMinutes |
      | Feature | task created in test | 1        | 2     | 2020-10-10 | Critical | description val | 10                | 25                  |
    Then the client receives 200 status
    And the client receives newly created Task
      | type    | summary              | assignee | owner | dueDate    | priority | status | project |
      | Feature | task created in test | 1        | 2     | 2020-10-10 | Critical | New    | 5       |

  Scenario: the client wants to edit existing task but form is invalid
    Given the client wants to edit task with id = 1
    When the client sets following task values
      | type | summary              | assignee | owner | dueDate    | priority | description     | estimationInHours | estimationInMinutes | status |
      |      | task created in test |          |       | 2020-10-10 |          | description val | 10                | 250                 |        |
    Then the client receives 400 status
    And the client receives bad request details
      | field               | code             | message                          |
      | type                | FIELD_VALIDATION | must not be null                 |
      | status              | FIELD_VALIDATION | must not be null                 |
      | owner               | FIELD_VALIDATION | must not be null                 |
      | priority            | FIELD_VALIDATION | must not be null                 |
