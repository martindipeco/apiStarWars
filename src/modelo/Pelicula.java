package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public Pelicula(){}

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


    public String buscaPeliculaPorTitulo(String busqueda)
    {
        URI direccion = URI.create("https://swapi.dev/api/films/?search="+busqueda);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
        HttpResponse<String> response = null;
        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }

    public Pelicula buscaPeliculaPorIndice(int indice)
    {
        URI direccion = URI.create("https://swapi.dev/api/films/"+indice); //transforma int a string por operador "+"
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(direccion).build();
        HttpResponse<String> response = null;
        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        String json = response.body();
        Gson gson = new GsonBuilder().create();
        PeliculaSwapi miPeliculaSwapi = gson.fromJson(json, PeliculaSwapi.class);
        Pelicula miPelicula = new Pelicula(miPeliculaSwapi);
        return miPelicula;
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
