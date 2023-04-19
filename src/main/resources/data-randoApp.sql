INSERT INTO role(nom) VALUES ("ADMINISTRATEUR"),("UTILISATEUR");


-- mot de passe : '1234' (hashé)
INSERT INTO utilisateur (nom, prenom, email, password, role_id) VALUES
                ("Doe", "John", "Joohn@doe.fr", "$2a$10$b7UK.5rgL48N4ZvV6GRNm.eVSiJCzDqDBwluR0bKPmRIYns033qBG",1),
                ("Doe", "Jane", "jane@doe.fr", "$2a$10$b7UK.5rgL48N4ZvV6GRNm.eVSiJCzDqDBwluR0bKPmRIYns033qBG",2 );