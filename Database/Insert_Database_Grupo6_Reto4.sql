use reto4_grupo6;

INSERT IGNORE INTO Podcaster(NombreArtistico, Imagen)
VALUES
('Jordi Wild', 'C:\Users\in1dm3\Pictures\example.jpg');
INSERT IGNORE INTO Musico(NombreArtistico, Imagen, Caracteristica)
VALUES
('Cruz Cafune', 'C:\Users\in1dm3\Pictures\example.jpg', 'Solista');
INSERT IGNORE INTO Album(Titulo, Ano, Genero, Imagen, IDMusico)
VALUES
('Moonlight922', '2020-01-10', 'Trap/RnB', 'C:\Users\in1dm3\Pictures\example.jpg', 1);
INSERT IGNORE INTO Audio(Nombre, Duracion, Imagen, Tipo)
VALUES
('En Bajo Perfil', '00:04:13', 'C:\Users\in1dm3\Pictures\example.jpg', 'Cancion'),
('Los Dineros', '01:14:13', 'C:\Users\in1dm3\Pictures\example.jpg', 'Podcast');
INSERT IGNORE INTO Podcast(Colaboradores, IDPodcaster)
VALUES
('Papa Giorgio', 1);
INSERT IGNORE INTO Cancion(IDAudio, IDAlbum)
VALUES
(1, 1);
INSERT IGNORE INTO Cliente(Nombre, Apellido, Usuario, Contrasena, FechaNacimiento, FechaRegistro, Tipo)
VALUES
('Ibai', 'Zaballa', 'Furaik', '123456789abc', '2003-01-01', '2024-04-18', 'Premium'),
('admin', 'admin', 'admin', 'admin', '2000-01-01', '2000-01-01', 'Premium');
INSERT IGNORE INTO Premium(FechaCaducidad)
VALUES
('2025-05-18');
INSERT IGNORE INTO Playlist(Titulo, FechaCreacion, IDCliente)
VALUES
('Favoritos', '2024-04-18', 1);
INSERT IGNORE INTO PlaylistCanciones (IDAudio, IDList, FechaPlaylistCancion)
VALUES
(1, 1, '2024-04-18');
INSERT IGNORE INTO Reproducciones(IDCliente, IDAudio, FechaReproduccion)
VALUES
(1, 1, '2024-04-18');