--Attention pas testé, c'est chatGPT qui m'a fait la conversion.

-- Connexion à la base de données existante
\c auctionflow;

-- Création de la table UTILISATEURS
CREATE TABLE UTILISATEURS (
    no_utilisateur SERIAL PRIMARY KEY,
    pseudo VARCHAR(30) NOT NULL UNIQUE,
    nom VARCHAR(30) NOT NULL,
    prenom VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    telephone VARCHAR(15),
    rue VARCHAR(30) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    ville VARCHAR(50) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL, -- Hash de mot de passe recommandé
    credit INTEGER NOT NULL DEFAULT 0,
    administrateur BOOLEAN NOT NULL DEFAULT FALSE
);

-- Création de la table CATEGORIES
CREATE TABLE CATEGORIES (
    no_categorie SERIAL PRIMARY KEY,
    libelle VARCHAR(30) NOT NULL UNIQUE
);

-- Création de la table ARTICLES_VENDUS
CREATE TABLE ARTICLES_VENDUS (
    no_article SERIAL PRIMARY KEY,
    nom_article VARCHAR(30) NOT NULL,
    description TEXT NOT NULL,
    date_debut_encheres DATE NOT NULL,
    date_fin_encheres DATE NOT NULL,
    prix_initial INTEGER CHECK (prix_initial >= 0),
    prix_vente INTEGER CHECK (prix_vente >= 0),
    no_utilisateur INTEGER NOT NULL,
    no_categorie INTEGER NOT NULL,
    FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS(no_utilisateur) ON DELETE CASCADE,
    FOREIGN KEY (no_categorie) REFERENCES CATEGORIES(no_categorie) ON DELETE SET NULL
);

-- Création de la table ENCHERES
CREATE TABLE ENCHERES (
    no_enchere SERIAL PRIMARY KEY,
    date_enchere TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    montant_enchere INTEGER NOT NULL CHECK (montant_enchere > 0),
    no_article INTEGER NOT NULL,
    no_utilisateur INTEGER NOT NULL,
    FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS(no_article) ON DELETE CASCADE,
    FOREIGN KEY (no_utilisateur) REFERENCES UTILISATEURS(no_utilisateur) ON DELETE CASCADE
);

-- Création de la table RETRAITS
CREATE TABLE RETRAITS (
    no_article INTEGER PRIMARY KEY,
    rue VARCHAR(30) NOT NULL,
    code_postal VARCHAR(10) NOT NULL,
    ville VARCHAR(30) NOT NULL,
    FOREIGN KEY (no_article) REFERENCES ARTICLES_VENDUS(no_article) ON DELETE CASCADE
);
