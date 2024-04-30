create database IF NOT EXISTS reto4_grupo6;
use reto4_grupo6;

CREATE TABLE IF NOT EXISTS Podcaster (
    IDPodcaster INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NombreArtistico VARCHAR(30) UNIQUE NOT NULL,
    Imagen VARCHAR(99)
);

CREATE TABLE IF NOT EXISTS Musico (
    IDMusico INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    NombreArtistico VARCHAR(30) UNIQUE NOT NULL,
    Imagen VARCHAR(99),
    Caracteristica ENUM('Solista', 'Grupo')
);


CREATE TABLE IF NOT EXISTS Album (
    IDAlbum INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Titulo VARCHAR(30) NOT NULL,
    Ano DATE NOT NULL,
    Genero VARCHAR(30) NOT NULL,
    Imagen VARCHAR(99),
    IDMusico INT NOT NULL,
    FOREIGN KEY (IDMusico)
        REFERENCES Musico (IDMusico)
);

CREATE TABLE IF NOT EXISTS Audio (
    IDAudio INT UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(30) NOT NULL,
    Duracion TIME NOT NULL,
    Imagen VARCHAR(99),
    Tipo ENUM('Podcast', 'Cancion')
);

CREATE TABLE IF NOT EXISTS Podcast (
    IDAudio INT NOT NULL PRIMARY KEY,
    Colaboradores VARCHAR(99) NULL,
    IDPodcaster INT NOT NULL,
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio),
    FOREIGN KEY (IDPodcaster)
        REFERENCES Podcaster (IDPodcaster)
);

CREATE TABLE IF NOT EXISTS Cancion (
    IDAudio INT NOT NULL PRIMARY KEY,
    IDAlbum INT NOT NULL,
    ArtistasInvitados VARCHAR(99) NULL,
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio),
    FOREIGN KEY (IDAlbum)
        REFERENCES Album (IDAlbum)
);

CREATE TABLE IF NOT EXISTS Cliente (
    IDCliente INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(30) NOT NULL,
    Apellido VARCHAR(30) NOT NULL,
    Usuario VARCHAR(30) UNIQUE NOT NULL,
    Contrasena VARCHAR(30) NOT NULL,
    FechaNacimiento DATE NOT NULL,
    FechaRegistro DATE NOT NULL,
    Tipo ENUM('Free', 'Premium')
);

CREATE TABLE IF NOT EXISTS Premium (
    IDCliente INT NOT NULL PRIMARY KEY,
    FechaCaducidad DATE NOT NULL,
    FOREIGN KEY (IDCliente)
        REFERENCES Cliente (IDCliente)
);

CREATE TABLE IF NOT EXISTS Playlist (
    IDList INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Titulo VARCHAR(30) NOT NULL,
    FechaCreacion DATE NOT NULL,
    IDCliente INT NOT NULL,
    FOREIGN KEY (IDCliente)
        REFERENCES Cliente (IDCliente)
);

CREATE TABLE IF NOT EXISTS PlaylistCanciones (
    IDAudio INT NOT NULL,
    IDList INT NOT NULL,
    FechaPlaylistCancion DATE NOT NULL,
    PRIMARY KEY (IDAudio , IDList),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio),
    FOREIGN KEY (IDList)
        REFERENCES Playlist (IDList)
);

CREATE TABLE IF NOT EXISTS Gustos (
    IDCliente INT NOT NULL ,
    IDAudio INT NOT NULL ,
    PRIMARY KEY (IDCliente , IDAudio),
    FOREIGN KEY (IDCliente)
        REFERENCES Cliente (IDCliente),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio)
);

CREATE TABLE IF NOT EXISTS Reproducciones (
    IDCliente INT NOT NULL,
    IDAudio INT NOT NULL,
    FechaReproduccion DATE NOT NULL,
    PRIMARY KEY (IDCliente , IDAudio),
    FOREIGN KEY (IDCliente)
        REFERENCES Cliente (IDCliente),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio)
);

CREATE TABLE IF NOT EXISTS Estadisticas (
    IDAudio INT NOT NULL PRIMARY KEY,
    KeyFigure1 VARCHAR(30),
    KeyFigure2 VARCHAR(30),
    KeyFigure3 VARCHAR(30),
    KeyFigure4 VARCHAR(30),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio)
);
