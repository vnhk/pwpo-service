UPDATE PROJECT p SET p.desciprion_html = p.description;
UPDATE TASK t SET t.desciprion_html = t.description;
UPDATE PROJECT_HISTORY ph SET ph.desciprion_html = ph.description;
UPDATE TASK_HISTORY th SET th.desciprion_html = th.description;

UPDATE PROJECT p SET p.description = null;
UPDATE TASK t SET t.description = null;
UPDATE PROJECT_HISTORY ph SET ph.description = null;
UPDATE TASK_HISTORY th SET th.description = null;
