INSERT INTO public.teams(id, created_at, updated_at, name, team_evaluation_date, managed_by_id)
VALUES (
  'a5fdf781-0266-4e2f-9784-0e00900bc8g7',
  now(),
  now(),
  'Digitalisation',
  null,
  (SELECT id FROM public.collaborator WHERE collaborator.first_name = 'Yassine' AND collaborator.last_name = 'MEZRANI')
);

UPDATE public.collaborator
SET member_of = (SELECT id FROM teams WHERE teams.name LIKE '%Digitalisation%')
WHERE collaborator.first_name LIKE '%Eya%' AND collaborator.last_name LIKE '%BENAMOR MEDDEB%';

UPDATE public.collaborator
SET member_of = (SELECT id FROM teams WHERE teams.name LIKE '%Digitalisation%')
WHERE collaborator.first_name LIKE 'rh' AND collaborator.last_name LIKE 'rh';

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c100', now(), now(), 'Consultant Junior');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c101', now(), now(), 'Consultant Senior');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c102', now(), now(), 'Manager');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c103', now(), now(), 'Expert');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1000', now(), now(), 'Développeur Junior');


INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1001', now(), now(), 'Développeur Senior');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1002', now(), now(), 'Consultant AMOA Junior');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1003', now(), now(), 'Consultant AMOA Senior');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1004', now(), now(), 'Product Owner');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1005', now(), now(), 'Consultant QA Junior');

INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1006', now(), now(), 'Consultant QA Senior');


-- Inserting a new career step
INSERT INTO public.career_steps(
id, created_at, updated_at, label)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1007', now(), now(), 'Consultant QA Manager');

-- Defining career paths
INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1000', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c100', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c101');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1001', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c101', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c102');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1002', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c101', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c103');


INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1003', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1000', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1001');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1004', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1001', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c102');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1005', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1001', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c103');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1006', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1002', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1003');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1007', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1003', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1004');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1008', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1005', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1006');

INSERT INTO public.career_paths(
id, created_at, updated_at, years_of_experience, from_career_step, to_career_step)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c1009', now(), now(), 2, 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1006', 'f54e1eeff1-97bd-4069-8b00-fbd40506d48c1007');


INSERT INTO public.profiles(id, created_at, updated_at, label, career_step, team)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c105', now(), now(), 'Consultant Junior',
(SELECT id FROM public.career_steps WHERE career_steps.label LIKE 'Consultant Junior'),
(SELECT id FROM public.teams WHERE teams.name='Digitalisation'));

INSERT INTO public.profiles(id, created_at, updated_at, label, career_step, team)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c106', now(), now(), 'Consultant Senior',
(SELECT id FROM public.career_steps WHERE career_steps.label LIKE 'Consultant Senior'),
(SELECT id FROM public.teams WHERE teams.name='Digitalisation'));

INSERT INTO public.profiles(id, created_at, updated_at, label, career_step, team)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c107', now(), now(), 'Manager',
(SELECT id FROM public.career_steps WHERE career_steps.label LIKE 'Manager'),
(SELECT id FROM public.teams WHERE teams.name='Digitalisation'));

INSERT INTO public.profiles(id, created_at, updated_at, label, career_step, team)
VALUES ('f54e1eeff1-97bd-4069-8b00-fbd40506d48c108', now(), now(), 'Expert',
(SELECT id FROM public.career_steps WHERE career_steps.label LIKE 'Expert'),
(SELECT id FROM public.teams WHERE teams.name='Digitalisation'));


INSERT INTO public.career_positions(
    id, created_at, started_at, status, updated_at, profile, collaborator_id, supervisor_id
)
VALUES (
    nextval('seq_career_position_entity'), 
    now(), 
    (SELECT recruited_at FROM public.collaborator WHERE collaborator.first_name LIKE '%Eya%' AND collaborator.last_name LIKE '%BENAMOR MEDDEB%'), 
    'CURRENT', 
    now(), 
    (SELECT id FROM profiles WHERE team = (SELECT member_of FROM collaborator WHERE collaborator.first_name LIKE '%Eya%' AND collaborator.last_name LIKE '%BENAMOR MEDDEB%') AND label LIKE 'Consultant Junior'), 
    (SELECT id FROM public.collaborator WHERE collaborator.first_name LIKE '%Eya%' AND collaborator.last_name LIKE '%BENAMOR MEDDEB%'), 
    (SELECT id FROM public.collaborator WHERE collaborator.first_name LIKE '%Yassine%' AND collaborator.last_name LIKE '%MEZRANI%')
);


INSERT INTO public.career_positions(
    id, created_at, started_at, status, updated_at, profile, collaborator_id, supervisor_id
)
VALUES (
    nextval('seq_career_position_entity'), 
    now(), 
    (SELECT recruited_at FROM public.collaborator WHERE collaborator.first_name LIKE 'rh' AND collaborator.last_name LIKE 'rh'), 
    'CURRENT', 
    now(), 
    (SELECT id FROM profiles WHERE team = (SELECT member_of FROM collaborator WHERE collaborator.first_name LIKE 'rh' AND collaborator.last_name LIKE 'rh') AND label LIKE 'Consultant Junior'), 
    (SELECT id FROM public.collaborator WHERE collaborator.first_name LIKE '%rh%' AND collaborator.last_name LIKE '%rh%'), 
    (SELECT id FROM public.collaborator WHERE collaborator.first_name LIKE '%Yassine%' AND collaborator.last_name LIKE '%MEZRANI%')
);
