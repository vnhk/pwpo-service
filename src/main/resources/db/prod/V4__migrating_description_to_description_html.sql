UPDATE PROJECT p SET p.desciprion_html = CAST(p.description AS text);
UPDATE TASK t SET t.desciprion_html = CAST(t.description AS text);
UPDATE PROJECT_HISTORY ph SET ph.desciprion_html = CAST(ph.description AS text);
UPDATE TASK_HISTORY th SET th.desciprion_html = CAST(th.description AS text);

UPDATE PROJECT p SET p.description = null;
UPDATE TASK t SET t.description = null;
UPDATE PROJECT_HISTORY ph SET ph.description = null;
UPDATE TASK_HISTORY th SET th.description = null;
