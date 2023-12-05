package com.example.pt7m08.Model;

public class Artista {

    String nombreArtista;
    String nombreAlbum;
    String imagen;

    public Artista(String nombreArtista, String nombreAlbum, String imagen) {
        this.nombreArtista = nombreArtista;
        this.nombreAlbum = nombreAlbum;
        this.imagen = imagen;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
