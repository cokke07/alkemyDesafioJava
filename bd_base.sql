SELECT * FROM generos;
select * from personajes;
select * from peliculas_series;
select * from peliculas_series_personajes;

insert into personajes (nombre,image,edad,peso,historia) values
("Sydney Jordan","http://zoom.us/en-us?q=4",38,99,"vulputate ullamcorper magna. Sed eu eros."),
("Brent Ford","https://cnn.com/en-ca?q=4",49,94,"et, euismod et,"),
("Peter Webster","http://ebay.com/site?q=11",73,71,"dictum eleifend, nunc risus varius orci,"),
("Idola Clemons","https://wikipedia.org/user/110?ab=441&aad=2",63,71,"vel nisl."),
("Alfonso Rhodes","https://nytimes.com/site?search=1&q=de",49,83,"Proin vel nisl."),
("Leilani Lane","https://wikipedia.org/user/110?q=test",68,70,"Donec egestas. Aliquam nec enim. Nunc ut erat. Sed"),
("Lucas Keith","https://zoom.us/user/110?q=0",72,64,"Fusce fermentum fermentum arcu. Vestibulum ante"),
("Ulric Reyes","https://yahoo.com/fr?p=8",45,56,"velit in"),
("Nomlanga Williamson","http://yahoo.com/settings?search=1&q=de",53,100,"eget magna. Suspendisse tristique neque venenatis lacus. Etiam bibendum"),
("Wanda Newton","https://naver.com/fr?search=1&q=de",44,55,"vulputate, risus");
insert into generos (nombre, image) value ("Accion","aca va link de ref"),("Comedia","aca va link de ref"),("Anime","aca va link de ref");
insert into peliculas_series (titulo,fecha_creacion,calificacion,id_genero) values ("La casa del Terror",NOW(),4,1),
("La novia de mi amigo",NOW(),4,2),("La casa del lago",NOW(),4,3),("Dia lluvioso",NOW(),4,3);
insert into peliculas_series_personajes (personajes_id_personaje,pelicula_serie_id_pelicula_serie) values (1,1),(2,1),(3,2),(4,2),(5,2),(6,3),(1,4),(2,4);