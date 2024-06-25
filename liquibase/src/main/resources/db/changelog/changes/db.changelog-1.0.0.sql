--liquibase formatted sql
--changeset ebamor :000-00-001 aplitStatements :false dbms :postgresql endDelimiter="\n/"
--comment : script d'initialisation
-- Function : Remplir le table users
INSERT INTO public.users(
dc, id, created_at, updated_at, account_status, email, first_name,
last_name, password, profile_type, passed_onboarding_process,
recruited_at)
VALUES ('HR_RESPONSIBLE', nextVal('seq_user_entity'), '2019-05-06 00:00:01', '2019-05-06 00:00:01','ACTIVE', 'john.doe@email.com', 'John',
'Doe', '$2a$10$IrYvRdaDAQLJrDOPB43NX.gDqJIpvXFM5t2JCgpco4qfV4dJ0BwHW', 'HR_RESPONSIBLE', FALSE, '2019-05-06 00:00:01');

--changeset ggharbi:000-00-002 aplitStatements :false dbms :postgresql endDelimiter="\n/"
--comment : script d'initialisation requests_status
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'IN_WAITING', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'VALIDATED', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'INVALIDATED', now());
insert into REQUEST_STATUS values (nextval('seq_request_status_entity'), now(), 'CANCELED', now());

--script d'initialitsation signataires
insert into signatories values (nextval('seq_signataire_entity'), now(), 'BEHJET', 'BOUSSOFARA', now());

-- script initialisation requests_type
insert into request_type (id, created_at, label, updated_at, id_signataire, visibility) values(nextVal('seq_request_type_entity'), now(), 'WORK_CERTIFICATE', now(), (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), true);
insert into request_type (id, created_at, label, updated_at, id_signataire, visibility) values(nextVal('seq_request_type_entity'), now(), 'MISSION_CERTIFICATE', now(), (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), true);
insert into request_type (id, created_at, label, updated_at, id_signataire, visibility) values(nextVal('seq_request_type_entity'), now(), 'SALARY_CERTIFICATE', now(), (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), true);
insert into request_type (id, created_at, label, updated_at, id_signataire, visibility) values(nextVal('seq_request_type_entity'), now(), 'PAYROLL', now(), (select id from signatories where lower(first_name)='behjet' and lower(last_name)='boussofara'), false);

-- script initialisation civility
insert into civility values(nextval('seq_civility_entity'), 'Mme', now(), 'Madame', now());
insert into civility values(nextval('seq_civility_entity'), 'Mr', now(), 'Monsieur', now());

-- script initialisation qualification
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Junior J1', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Junior J2', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Junior J3', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Senior S1', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Senior S2', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Senior S3', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Manager M1', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Manager M2', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Manager M3', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Expert E1', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Expert E2', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Expert E3', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Comptable', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'RH', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'DAF', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Directeur', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Mandataire Social', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'RRH', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Stagiaire', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Consultant Junior', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Consultat BSCS Junior', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Consultat BSCS Confirme', now());
insert into qualification values (nextval('seq_qualification_entity'), now(), 'Consultat BSCS Senior', now());




-- script initialisation Collaborators
INSERT INTO public.collaborator(id, bonus, created_at, entry_date, first_name, id_byblos, last_name, matricule, updated_at, id_civilite, id_qualification)
    VALUES (nextval('seq_collab_entity'), 0, '2021-04-06 09:50:37.370752', '2014-12-01', 'Alice', 11777578, 'Alice',
            '2222', '2021-04-06 09:50:37.370752', 1, 1);

INSERT INTO public.collaborator(
            id, bonus, created_at, entry_date, first_name, id_byblos, last_name,
            matricule, updated_at, id_civilite, id_qualification)
    VALUES (nextval('seq_collab_entity'), 0, '2021-04-06 09:50:37.370752', '2014-12-01', 'Emma', 11777577, 'Emma',
            '3333', '2021-04-06 09:50:37.370752', 1, 1);


--script initialisation Users



INSERT INTO public.users(
            dc, id, created_at, updated_at, account_status, email, first_name,
            last_name, password, profile_type, passed_onboarding_process,
            recruited_at,collab_id)
    VALUES ('COLLABORATOR', nextval('seq_user_entity') , '2019-05-06 00:00:01', '2019-05-06 00:00:01','ACTIVE', 'Emma.Emma@test.tst', 'Emma',
            'Emma', '$2a$10$IrYvRdaDAQLJrDOPB43NX.gDqJIpvXFM5t2JCgpco4qfV4dJ0BwHW', 'COLLABORATOR', TRUE, '2019-05-06 00:00:01',
            (SELECT id
	     FROM public.collaborator
	     WHERE (public.collaborator.first_name LIKE 'Emma') AND (public.collaborator.last_name LIKE 'Emma')  )
            );


INSERT INTO public.users(
            dc, id, created_at, updated_at, account_status, email, first_name,
            last_name, password, profile_type, passed_onboarding_process,
            recruited_at,collab_id)
    VALUES ('COLLABORATOR', nextval('seq_user_entity') , '2019-05-06 00:00:01', '2019-05-06 00:00:01','ACTIVE', 'Alice.Alice@test.tst', 'Alice',
            'Alice', '$2a$10$IrYvRdaDAQLJrDOPB43NX.gDqJIpvXFM5t2JCgpco4qfV4dJ0BwHW', 'COLLABORATOR', TRUE, '2019-05-06 00:00:01',
            (SELECT id
	     FROM public.collaborator
	     WHERE (public.collaborator.first_name LIKE 'Alice') AND (public.collaborator.last_name LIKE 'Alice')  )
            );

ALTER TABLE collaborator DROP COLUMN IF EXISTS bonus;

