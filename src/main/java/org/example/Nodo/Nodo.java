package org.example.Nodo;

public class Nodo {
    private String nombreCancion;
    private String artista;
    private String genero;
    private int duracion; // en segundos

    // Constructor
    public Nodo(String nombreCancion, String artista, String genero, int duracion) {
        this.nombreCancion = nombreCancion;
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
    }

    // Métodos getters
    public String getNombreCancion() {
        return nombreCancion;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public int getDuracion() {
        return duracion;
    }

    // Métodos para obtener el nombre del artista y género en español
    public String getNombreArtistaEnEspanol() {
        // Implementar lógica de traducción aquí si es necesario
        return artista;
    }

    public String getNombreGeneroEnEspanol() {
        // Implementar lógica de traducción aquí si es necesario
        return genero;
    }
}

//public class Nodo {
//    private String nombreCancion;
//    private String artista;
//    private String genero;
//    private int duracion; // en segundos
//
//    // Constructor
//    public Nodo(String nombreCancion, String artista, String genero, int duracion) {
//        this.nombreCancion = nombreCancion;
//        this.artista = artista;
//        this.genero = genero;
//        this.duracion = duracion;
//    }
//
//    // Métodos getters
//    public String getNombreCancion() {
//        return nombreCancion;
//    }
//
//    public String getArtista() {
//        return artista;
//    }
//
//    public String getGenero() {
//        return genero;
//    }
//
//    public int getDuracion() {
//        return duracion;
//    }
//
//    // Métodos para obtener el nombre del artista y género en español
//    public String getNombreArtistaEnEspanol() {
//        // Implementar lógica de traducción aquí si es necesario
//        return artista;
//    }
//
//    public String getNombreGeneroEnEspanol() {
//        // Implementar lógica de traducción aquí si es necesario
//        return genero;
//    }
//}
//public class Nodo {
//    private String nombreCancion;
//    private String artista;
//    private String genero;
//    private int duracion; // en segundos
//
//    // Constructor
//    public Nodo(String nombreCancion, String artista, String genero, int duracion) {
//        this.nombreCancion = nombreCancion;
//        this.artista = artista;
//        this.genero = genero;
//        this.duracion = duracion;
//    }
//
//    // Métodos getters
//    public String getNombreCancion() {
//        return nombreCancion;
//    }
//
//    public String getArtista() {
//        return artista;
//    }
//
//    public String getGenero() {
//        return genero;
//    }
//
//    public int getDuracion() {
//        return duracion;
//    }
//}
