insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'IN_WAITING', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'VALIDATED', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'INVALIDATED', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'CANCELED', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'RECEIVED', now());

insert into signatories values (nextval('seq_signataire_entity'), now(), 'Behjet Mounir', 'BOUSSOFARA', now());

--insert user guide in template
INSERT INTO public.request_type(
    id, created_at, idedm, label, updated_at, upload_date, visibility, id_signatory, is_template_of_request
) VALUES (
    nextval('seq_request_type_entity'), now(), null, 'USER_GUIDE', now(), null, true,
    (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), false
);

--insert WORK_CERTIFICATE
INSERT INTO public.request_type(
    id, created_at, idedm, label, updated_at, upload_date, visibility, id_signatory, is_template_of_request
) VALUES (
    nextval('seq_request_type_entity'), now(), null, 'WORK_CERTIFICATE', now(), null, true,
    (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), true
);

--insert Salary_CERTIFICATE
INSERT INTO public.request_type(
    id, created_at, idedm, label, updated_at, upload_date, visibility, id_signatory, is_template_of_request
) VALUES (
    nextval('seq_request_type_entity'), now(), null, 'SALARY_CERTIFICATE', now(), null, true,
    (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), true
);

INSERT INTO public.request_type(
    id, created_at, idedm, label, updated_at, upload_date, visibility, id_signatory, is_template_of_request
) VALUES (
    nextval('seq_request_type_entity'), now(), null, 'PAYROLL', now(), null, false,
    (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), false
);


-- Insert request type 'ETIQUETTE_TYPE'
INSERT INTO public.request_type(
    id, created_at, idedm, label, updated_at, upload_date, visibility, id_signatory, is_template_of_request
) VALUES (
    nextval('seq_request_type_entity'), now(), null, 'ETIQUETTE_TYPE', now(), null, true,
    (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), false
);

-- Add reminder request type
INSERT INTO request_type (id, created_at, label, updated_at, id_signatory, visibility)
VALUES (
    nextval('seq_request_type_entity'), now(), 'REMINDER', now(), null, false
);

-- Update null reminder
UPDATE public.salary_histories
SET reminder = 0
WHERE reminder IS NULL;

-- Script initialisation civility
INSERT INTO civility VALUES (nextval('seq_civility_entity'), 'Mme', now(), 'Madame', now());
INSERT INTO civility VALUES (nextval('seq_civility_entity'), 'Mr', now(), 'Monsieur', now());


-- Insertions dans la table qualification
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Junior', 'J1', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Junior', 'J2', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Junior', 'J3', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Senior', 'S1', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Senior', 'S2', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Senior', 'S3', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Manager', 'M1', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Manager', 'M2', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Manager', 'M3', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Expert', 'E1', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Expert', 'E2', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Expert', 'E3', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Comptable', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'RH', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'DAF', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Directeur', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Mandataire Social', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'RRH', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Stagiaire', '', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Consultant', 'Junior', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Consultat BSCS', 'Junior', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Consultat BSCS', 'Confirme', now());
INSERT INTO qualification VALUES (nextval('seq_qualification_entity'), now(), 'Consultat BSCS', 'Senior', now());


-- Paramétrage de l'application
INSERT INTO public.parametrage_appli (
    id, created_at, parametre, type_param, updated_at, valeur_param
) VALUES (
    nextval('seq_parametrage_appli_entity'), now(), 'DureeAutoValidDemandeAdmin', 'NBjour', now(), '2'
);


   