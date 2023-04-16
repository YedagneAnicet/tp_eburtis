ALTER TABLE personne
ADD CONSTRAINT fk_departement_code
FOREIGN KEY (departement_code) REFERENCES departement(code);