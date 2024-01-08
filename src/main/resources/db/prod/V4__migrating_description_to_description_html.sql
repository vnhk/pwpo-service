UPDATE PROJECT SET description_html = CAST(description AS text);
UPDATE TASK SET description_html = CAST(description AS text);
UPDATE PROJECT_HISTORY SET description_html = CAST(description AS text);
UPDATE TASK_HISTORY SET description_html = CAST(description AS text);

UPDATE PROJECT SET description = null;
UPDATE TASK SET description = null;
UPDATE PROJECT_HISTORY SET description = null;
UPDATE TASK_HISTORY SET description = null;
