package modelo;

import java.time.LocalDate;

public class Pelicula {

    private String titulo;
    private int episodio;
    private String director;
    private String productor;
    private LocalDate fechaEstreno;

    public Pelicula(String titulo, int episodio, String director, String productor, LocalDate fechaEstreno) {
        this.titulo = titulo;
        this.episodio = episodio;
        this.director = director;
        this.productor = productor;
        this.fechaEstreno = fechaEstreno;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getEpisodio() {
        return episodio;
    }

    public String getDirector() {
        return director;
    }

    public String getProductor() {
        return productor;
    }

    public LocalDate getFechaEstreno() {
        return fechaEstreno;
    }

    @Override
    public String toString() {
        return "{ Pelicula: " +
                "Título=" + titulo + '\'' +
                ", Episodio=" + episodio +
                ", Director='" + director + '\'' +
                ", Productor='" + productor + '\'' +
                ", Fecha de Estreno=" + fechaEstreno +
                '}';
    }
}
