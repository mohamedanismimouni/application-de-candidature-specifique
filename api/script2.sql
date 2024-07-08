-- Ajout des liens utiles
INSERT INTO public.useful_link (id, created_at, label, link, updated_at)
VALUES (nextval('seq_useful_link_entity'), now(), 'www.talan.tn', 'https://tn.talan.com/', now());

INSERT INTO public.useful_link (id, created_at, label, link, updated_at)
VALUES (nextval('seq_useful_link_entity'), now(), 'Portail Assurance', 'https://portail.magassur.com.tn/sante/login.jsp', now());

INSERT INTO public.useful_link (id, created_at, label, link, updated_at)
VALUES (nextval('seq_useful_link_entity'), now(), 'Campus Talan', 'https://campus2.talan.com/connect', now());

INSERT INTO public.useful_link (id, created_at, label, link, updated_at)
VALUES (nextval('seq_useful_link_entity'), now(), 'Byblos', 'https://byblos.talan.com/login.jsf', now());

INSERT INTO public.useful_link (id, created_at, label, link, updated_at)
VALUES (nextval('seq_useful_link_entity'), now(), 'Page Facebook TTC', 'https://www.facebook.com/TalanTunisia/?fref=ts', now());

INSERT INTO public.useful_link (id, created_at, label, link, updated_at)
VALUES (nextval('seq_useful_link_entity'), now(), 'TTC Service Desk', 'https://servicedesk.talan.tn/servicedesk/customer/portal/1/user/login?destination=portal%2F1', now());


-- Ajout du lien utile avec échappement des caractères spéciaux
INSERT INTO public.useful_link (
    id, created_at, label, link, updated_at
) VALUES (
    nextval('seq_useful_link_entity'), now(), 'Wiki Sécurité',
    'http://securite.talan.tn/xwiki/wiki/securite.talan.tn/login/XWiki/XWikiLogin;jsessionid=57C415D2CE81368181435B3E5C838378?srid=SpR7dyQK&amp;xredirect=%2Fxwiki%2Fbin%2Fview%2FMain%2F%3Fsrid%3DSpR7dyQK',
    now()
);

-- Configuration du score pour tous les collaborateurs
UPDATE public.collaborator SET score = 0;

-- Insertion des configurations de score
INSERT INTO public.config_score (
    id_component, component_label, component_score
) VALUES (
    nextval('seq_config_score_entity'), 'How do you feel today', 50
);
INSERT INTO public.config_score (
    id_component, component_label, component_score
) VALUES (
    nextval('seq_config_score_entity'), 'quiz', 100
);
INSERT INTO public.config_score (
    id_component, component_label, component_score
) VALUES (
    nextval('seq_config_score_entity'), 'event', 100
);
INSERT INTO public.config_score (
    id_component, component_label, component_score
) VALUES (
    nextval('seq_config_score_entity'), 'proverb', 150
);

-- Ajout du document utile
INSERT INTO public.useful_document (
    id, created_at, idedm, label, updated_at, upload_date
) VALUES (
    nextval('seq_useful_document_entity'), now(), null, 'Livret d''accueil - Tunisie', now(), null
);



-- Suppression des données existantes
DELETE FROM public.answered_by;
DELETE FROM public.quiz_entity;

-- Insertions des questions de quiz
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Qu’est-ce qui peut faire le tour du monde en restant dans son coin ?', 'un timbre', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Quel est l’ami qu’on ne peut pas supporter ?', 'la migraine', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'J’ai un chapeau, mais pas de tête. J’ai un pied mais pas de chaussures. Qui suis-je?', 'un champignon', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Combien de temps peut vivre une souris ?', 'cela dépend des chats', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Qu’est-ce qui n’a ni pattes, ni ailes, ni yeux, ni mains, qui ne rampe pas, qui ne nage pas, mais qui s’enfuit dès qu’on le touche ?', 'le savon', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Qu’est ce qui est vert qui monte et qui descend ?', 'un haricot vert dans un ascenseur', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Que dit un citron policier à un voleur ?', 'plus un zeste', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Qu’est ce qui est jaune et qui court très vite ?', 'un citron pressé', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Un pou et une fourmi font la course. Lequel va arriver le premier ?', 'le pou car il est toujours en tête', now()
);
INSERT INTO public.quiz_entity (
    id, created_at, question, response, updated_at
) VALUES (
    nextval('seq_quiz_entity'), now(), 'Qu’est ce que l’on doit casser avant de l’utiliser?', 'un œuf', now()
);


-- Insérer un collaborateur
INSERT INTO public.collaborator (
    id, account_status, created_at, email,
    end_contract_date, entry_date, first_name,
    id_byblos, last_name, matricule, passed_onboarding_process,
    recruited_at, score, secret_word, updated_at, id_civilite,
    id_function, member_of, id_qualification
) VALUES (
    nextval('seq_collab_entity'), 'ACTIVE', now(), 'supp.appli.rh@hotmail.com',
    null, null, 'System', null, 'Admin', '0', true,
    null, 0, null, now(),
    (SELECT id FROM civility WHERE civility.l_civilite = 'Monsieur' LIMIT 1),
    null, null, null
);

-- Insérer un événement
INSERT INTO public.event (
    id, canceled_motif, created_at, date, event_price, idedmimage, image_extension,
    location, number_max_participation, origin_link, status, title, type, creator
) VALUES (
    nextval('seq_event_entity'), null, now(), '2021-12-30', 0, null, null, 'En Ligne', 5,
    'https://campus2.talan.com/courses/252',
    'ACTIVE', 'CAMPUS TALAN - Formation: Travailler à distance', 'Gratuit',
    (SELECT id FROM public.collaborator WHERE first_name LIKE 'System' LIMIT 1)
);
