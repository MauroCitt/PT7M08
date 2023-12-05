package com.example.pt7m08.Model;

public class Pelicula {
    String nombre;
    String actores;
    String poster;
    String sinopsis;

    public Pelicula(String nombre, String actores, String poster, String sinopsis) {
        this.nombre = nombre;
        this.actores = actores;
        this.poster = poster;
        this.sinopsis = sinopsis;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
}
