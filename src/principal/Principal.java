package principal;

import com.google.gson.*;
import modelo.Favorita;
import modelo.GeneradorDeArchivo;
import modelo.Pelicula;
import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static void main (String[] args) throws InterruptedException, IOException
    {
        Scanner scanner = new Scanner(System.in);
        Favorita fav = new Favorita();
        Pelicula peli = new Pelicula();
        GeneradorDeArchivo generador = new GeneradorDeArchivo();
        System.out.println("Bienvenido a las consultas de películas de Star Wars :)");
        System.out.println("Ingrese la opción deseada");
        String opcion;
        do
        {
            System.out.println("\na: consultar info de películas por título");
            System.out.println("b: consultar info de películas por orden de estreno");
            System.out.println("c: ver favoritas");
            System.out.println("x: salir");

            opcion = scanner.nextLine().toLowerCase();

            switch (opcion)
            {
                case "a":
                    //búsqueda por título
                    //TODO: validar que se ingrese al menos un caracter valido
                    System.out.println("Ingrese título o parte del título");
                    String busquedaTitulo = scanner.nextLine();

                    String jsonString = peli.buscaPeliculaPorTitulo(busquedaTitulo);

                    //to access values from the JSON -> parse String to Json
                    JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                    JsonArray resultsArray = jsonObject.getAsJsonArray("results");
                    int numeroDeResultados = jsonObject.get("count").getAsInt();

                    if (numeroDeResultados == 0) {
                        System.out.println("No se obtuvieron resultados con ese título");
                        break;
                    } else if (numeroDeResultados == 1) {
                        System.out.println("Se encontró un resultado");
                    } else {
                        System.out.println("Se obtuvieron " + numeroDeResultados + " resultados:");
                    }
                    for (JsonElement element : resultsArray) {
                        int episodio = element.getAsJsonObject().get("episode_id").getAsInt();
                        String titulo = element.getAsJsonObject().get("title").getAsString();
                        String fecha = element.getAsJsonObject().get("release_date").getAsString().substring(0, 4);
                        System.out.println("Episodio: " + episodio + ": " + titulo + ", de " + fecha);
                    }
                    System.out.println("Ingrese el numero de episodio para confirmar la búsqueda");
                    String eleccion = scanner.nextLine();
                    //validar que sea un numero
                    Integer numeroParaBusqueda;
                    int numero = Integer.parseInt(eleccion);
                    //peliculas están ordenadas por fecha de estreno, no por número de episodio
                    //la número 1 es el episodio 4
                    if (numero > 3) {
                        numeroParaBusqueda = numero - 3;
                    } else {
                        numeroParaBusqueda = numero + 3;
                    }
                    //Pelicula miPelicula
                    peli = peli.buscaPeliculaPorIndice(numeroParaBusqueda);

                    System.out.println(peli);
                    System.out.println("¿Desea guardarla en Favoritas?(s/n)");
                    String porSioPorNo = scanner.nextLine();
                    if (porSioPorNo.equals("s")) {
                        fav.agregarAFavortitas(peli);
                        generador.guardarTxt(peli);
                    }
                    //catch (ErrorNAException e)
                    //{
                    //    System.out.println(e.getMessage());
                    //}
                    break;
                case "b":
                    String seleccion;
                    do {
                        //el num de peli está organizada cronológicamente en la base de datos por fecha de lanzamiento, no por episode_id
                        System.out.println("Películas disponibles");
                        System.out.println("Ingrese el número correspondiente para confirmar búsqueda");

                        System.out.println("\n1: A New Hope - 1977");
                        System.out.println("2: The Empire Strikes Back - 1980");
                        System.out.println("3: Return of the Jedi - 1983");
                        System.out.println("4: The Phantom Menace - 1999");
                        System.out.println("5: Attack of the Clones - 2002");
                        System.out.println("6: Revenge of the Sith - 2005");
                        System.out.println("x: Volver atrás");

                        //TODO: chequear que la selección sea un número entre 1 a 6
                        seleccion = scanner.nextLine();
                        if (seleccion.equals("x"))
                        {
                            break;
                        }

                        int numeroBusqueda = Integer.parseInt(seleccion);
                        //miPeliculaBloqueB
                        peli = peli.buscaPeliculaPorIndice(numeroBusqueda);

                        //la llave "count" tiene valor la cantidad de episodios disponibles
                        //ordenados por fecha ascendente. x ej; https://swapi.dev/api/films/1 es la primer pelicula de 1977, episodio 4

                        System.out.println(peli);
                        System.out.println("¿Desea guardarla en Favoritas?(s/n)");
                        String porSoN = scanner.nextLine();
                        if(porSoN.equals("s"))
                        {
                            fav.agregarAFavortitas(peli);
                            generador.guardarTxt(peli);
                            System.out.println("Guardada en Favoritas");
                        }
                    }
                    while (!"x".equals(seleccion));
                    break;

                case "c":
                    String opcionOrden;
                    do {
                        fav.mostrarLista();

                        System.out.println("\na: Ordena por Título A-Z");
                        System.out.println("b: Ordena por Título Z-A");
                        System.out.println("c: Ordena por Episodio ascendente");
                        System.out.println("d: Ordena por Episodio descendente");
                        System.out.println("e: Ordena por fecha más lejana");
                        System.out.println("f: Ordena por fecha más próxima");
                        System.out.println("x: Volver atrás");

                        opcionOrden = scanner.nextLine().toLowerCase();

                        switch (opcionOrden)
                        {
                            case "a":
                                System.out.println("ordenada por nombre");
                                fav.ordenaPorNombre();
                                break;
                            case "b":
                                System.out.println("ordenada por nombre descendente");
                                fav.ordenaPorNombreDescendente();
                                break;
                            case "c":
                                fav.ordenaPorEpisodio();
                                System.out.println("ordenada por Episodio");
                                break;
                            case "d":
                                fav.ordenaPorEpisodioDescendente();
                                System.out.println("ordenada por Episodio descendente");
                                break;
                            case "e":
                                fav.ordenaPorFecha();
                                System.out.println("ordenada por fecha");
                                break;
                            case "f":
                                fav.ordenaPorFechaDescendente();
                                System.out.println("ordenada por fecha descendente");
                                break;
                            case "x":
                                System.out.println("Volviendo atrás\n");
                                break;
                            default:
                                System.out.println("Opción no válida, por favor ingrese de nuevo\n");
                                break;
                        }
                    }
                    while (!"x".equals(opcionOrden));
                    break;
                case "x":
                    System.out.println("Gracias por utilizar el sistema\n");
                    break;
                default:
                    System.out.println("Opción no válida, por favor ingrese de nuevo\n");
                    break;
            }
        }
        while (!"x".equals(opcion));
        scanner.close();
    }
}
