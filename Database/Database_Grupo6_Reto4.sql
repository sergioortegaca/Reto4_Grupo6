create database IF NOT EXISTS reto4_grupo6;
use reto4_grupo6;

CREATE TABLE IF NOT EXISTS Podcaster (
	IDPodcaster VARCHAR(5) PRIMARY KEY NOT NULL,
	NombreArtistico VARCHAR(30) UNIQUE NOT NULL,
    Imagen VARCHAR(99)
);

CREATE TABLE IF NOT EXISTS Musico (
    IDMusico VARCHAR(5) PRIMARY KEY NOT NULL,
    NombreArtistico VARCHAR(30) UNIQUE NOT NULL,
    Imagen VARCHAR(99),
    Caracteristica ENUM('Solista', 'Grupo')
);

CREATE TABLE IF NOT EXISTS Album (
	IDAlbum VARCHAR(30) PRIMARY KEY NOT NULL,
    Titulo VARCHAR(30) NOT NULL, 
    Ano DATE NOT NULL, 
    Genero VARCHAR(30) NOT NULL,
    Imagen VARCHAR(99),
    IDMusico VARCHAR(5) NOT NULL,
    FOREIGN KEY (IDMusico)	
		REFERENCES Musico(IDMusico)
);

CREATE TABLE IF NOT EXISTS Audio (
	IDAudio VARCHAR(30) PRIMARY KEY UNIQUE NOT NULL,
    Nombre VARCHAR(30) NOT NULL,
    Duracion TIME NOT NULL, 
    Imagen VARCHAR(99), 
    Tipo ENUM('Podcast','Cancion')	
);

CREATE TABLE IF NOT EXISTS Podcast (
	IDAudio VARCHAR(30) PRIMARY KEY NOT NULL,
	Colaboradores VARCHAR(99) NULL,
    IDPodcaster VARCHAR(5) NOT NULL,
	FOREIGN KEY (IDAudio)	
		REFERENCES Audio(IDAudio),
	FOREIGN KEY (IDPodcaster)	
		REFERENCES Podcaster(IDPodcaster)	
);

CREATE TABLE IF NOT EXISTS Cancion (
	IDAudio VARCHAR(30) PRIMARY KEY NOT NULL,
	IDAlbum VARCHAR(30) NOT NULL,
    ArtistasInvitados VARCHAR(99) NULL,
	FOREIGN KEY (IDAudio)	
		REFERENCES Audio(IDAudio),
	FOREIGN KEY (IDAlbum)	
		REFERENCES Album(IDAlbum)
);

CREATE TABLE IF NOT EXISTS Cliente (
	IDCliente VARCHAR(5) PRIMARY KEY NOT NULL,
    Nombre VARCHAR(30) NOT NULL,
    Apellido VARCHAR(30) NOT NULL,
    Usuario VARCHAR(30) UNIQUE NOT NULL, 
    Contrasena VARCHAR(30) NOT NULL,
    FechaNacimiento DATE NOT NULL,
    FechaRegistro DATE NOT NULL,
    Tipo ENUM('Free','Premium') 
    );

CREATE TABLE IF NOT EXISTS Premium (
	IDCliente VARCHAR(5) PRIMARY KEY NOT NULL,
	FechaCaducidad DATE NOT NULL,
	FOREIGN KEY (IDCliente)
		REFERENCES Cliente(IDCliente)
);

CREATE TABLE IF NOT EXISTS Playlist (
	IDList VARCHAR(30) PRIMARY KEY NOT NULL,
    Titulo VARCHAR(30) NOT NULL,
    FechaCreacion DATE NOT NULL,
    IDCliente VARCHAR(5) NOT NULL,
    FOREIGN KEY (IDCliente)
		REFERENCES Cliente(IDCliente)
);

CREATE TABLE IF NOT EXISTS PlaylistCanciones (
    IDAudio VARCHAR(30) NOT NULL,
    IDList VARCHAR(30) NOT NULL,
    FechaPlaylistCancion DATE NOT NULL,
    PRIMARY KEY (IDAudio , IDList),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio),
    FOREIGN KEY (IDList)
        REFERENCES Playlist (IDList)
);

CREATE TABLE IF NOT EXISTS Gustos (
    IDCliente VARCHAR(5) NOT NULL,
    IDAudio VARCHAR(30) NOT NULL,
    PRIMARY KEY (IDCliente , IDAudio),
    FOREIGN KEY (IDCliente)
        REFERENCES Cliente (IDCliente),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio)
);

CREATE TABLE IF NOT EXISTS Reproducciones (
    IDCliente VARCHAR(5) NOT NULL,
    IDAudio VARCHAR(30) NOT NULL,
    FechaReproduccion DATE NOT NULL,
    PRIMARY KEY (IDCliente , IDAudio),
    FOREIGN KEY (IDCliente)
        REFERENCES Cliente (IDCliente),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio)
);

CREATE TABLE IF NOT EXISTS Estadisticas (
    IDAudio VARCHAR(30) PRIMARY KEY NOT NULL,
    KeyFigure1 VARCHAR(30),
    KeyFigure2 VARCHAR(30),
    KeyFigure3 VARCHAR(30),
    KeyFigure4 VARCHAR(30),
    FOREIGN KEY (IDAudio)
        REFERENCES Audio (IDAudio)
);

INSERT IGNORE INTO Podcaster(IDPodcaster, NombreArtistico, Imagen)
VALUES
('PAAA1', 'Jordi Wild', 'C:\Users\in1dm3\Pictures\example.jpg');
INSERT IGNORE INTO Musico(IDMusico, NombreArtistico, Imagen, Caracteristica)
VALUES
('MAAA1', 'Cruz Cafune', 'C:\Users\in1dm3\Pictures\example.jpg', 'Solista');
INSERT IGNORE INTO Album(IDAlbum, Titulo, Ano, Genero, Imagen, IDMusico)
VALUES
('AAAA1', 'Moonlight922', '2020-01-10', 'Trap/RnB', 'C:\Users\in1dm3\Pictures\example.jpg', 'MAAA1');
INSERT IGNORE INTO Audio(IDAudio, Nombre, Duracion, Imagen, Tipo)
VALUES
('AUAA1', 'En Bajo Perfil', '00:04:13', 'C:\Users\in1dm3\Pictures\example.jpg', 'Cancion'),
('AUAA2', 'Los Dineros', '01:14:13', 'C:\Users\in1dm3\Pictures\example.jpg', 'Podcast');
INSERT IGNORE INTO Podcast(IDAudio, Colaboradores, IDPodcaster)
VALUES
('AUAA2', 'Papa Giorgio', 'PAAA1');
INSERT IGNORE INTO Cancion(IDAudio, IDAlbum, ArtistasInvitados)
VALUES
('AUAA1', 'AAAA1');
INSERT IGNORE INTO Cliente(IDCliente, Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo)
VALUES
('CAAA1', 'Ibai', 'Zaballa', 'Furaik', '123456789abc', '2003-01-01', '2024-04-18', 'Premium');
INSERT IGNORE INTO Premium(IDCliente, FechaCaducidad)
VALUES
('CAAA1', '2025-05-18');
INSERT IGNORE INTO Playlist(IDList, Titulo, FechaCreacion, IDCliente)
VALUES
('PLAA1', 'Favoritos', '2024-04-18', 'CAAA1');
INSERT IGNORE INTO PlaylistCanciones (IDAudio, IDList, FechaPlaylistCancion)
VALUES
('AUAA1', 'PLAA1', '2024-04-18');
INSERT IGNORE INTO Reproducciones(IDCliente, IDAudio, FechaReproduccion)
VALUES
('CAAA1', 'AUAA1', '2024-04-18');
