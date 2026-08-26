INSERT INTO users (username, password)
VALUES ('test', '$2a$12$X4u4Ig5F4jRa/7Ms0CpdounYb0VhshpcT8rrwPi/Z.dFex0tUqANW');

INSERT INTO patient (name)
VALUES ('Michel DUBOIS'), ('Patrique DUPONT'), ('Louis TOLIER'), ('Pierre CHARENZON'), ('Paul FORT');

INSERT INTO doctor (name)
VALUES ('Jean-Pierre MARTIN'), ('Claire DUBOIS'), ('Philippe BERNARD'), ('Sophie LEFEVRE'), ('Laurent MOREAU');

INSERT INTO consultation (date, patient_id, doctor_id)
VALUES
    ('2024-06-05', 1, 1),
    ('2025-02-04', 2, 1),
    ('2026-01-18', 1, 2),
    ('2027-04-16', 3, 2),
    ('2024-12-01', 2, 3),
    ('2025-12-07', 3, 3),
    ('2028-05-28', 1, 3),
    ('2023-08-22', 4, 5),
    ('2023-09-23', 2, 5),
    ('2022-09-05', 1, 1),
    ('2025-05-04', 1, 2),
    ('2024-11-18', 2, 5),
    ('2026-08-18', 3, 5),
    ('2026-04-26', 3, 3),
    ('2027-02-14', 2, 3);

INSERT INTO medication (label)
VALUES
    ('Doliprane'),
    ('Efferalgan'),
    ('Paracétamol'),
    ('Ibuprofène'),
    ('Amoxicilline'),
    ('Augmentin');

INSERT INTO prescription (medication_id, consultation_id, quantity)
VALUES
    (1, 10, 2),
    (1, 10, 5),
    (2, 10, 2),
    (3, 5, 4),
    (4, 4, 5),
    (5, 2, 1);