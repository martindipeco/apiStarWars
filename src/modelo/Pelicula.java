package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public Pelicula(PeliculaSwapi peliculaSwapi)
    {
        if (peliculaSwapi.title().contains("N/A"))
        {
            //TODO crear excepcion propia - ver cuentas ordenadas
            //throw new ErrorNAException("No se pudo convertir por que apareció N/A en Título");
            System.out.println("Hubo un error");
        }
        this.titulo = peliculaSwapi.title();
        this.episodio = Integer.valueOf(peliculaSwapi.episode_id());
        this.director = peliculaSwapi.director();
        this.productor = peliculaSwapi.producer();
        //swapi documentation: date string is like "2024-04-18" -- ISO 8601 date string
        //DateTimeFormatter.ISO_LOCAL_DATE is a predefined formatter for parsing ISO 8601 date strings
        this.fechaEstreno = LocalDate.parse(peliculaSwapi.release_date(), DateTimeFormatter.ISO_LOCAL_DATE);
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
