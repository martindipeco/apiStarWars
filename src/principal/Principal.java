package principal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.Favorita;
import modelo.Pelicula;
import modelo.PeliculaSwapi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {

    public static void main (String[] args) throws InterruptedException, IOException
    {
        System.out.println("Zona de obras: Api Star Wars");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido a SWAPI :)");
        System.out.println("Ingrese la opción deseada");
        String opcion;
        do
        {
            System.out.println("\na: consultar info de películas por título");
            System.out.println("b: consultar info de películas por orden de estreno");
            System.out.println("c: ver favoritas");
            System.out.println("d: guardar información");
            System.out.println("x: salir");

            //convierto a minusculas por las dudas
            opcion = scanner.nextLine().toLowerCase();

            //para consultar la info. 2 opciones
            //opcion 1: hacer la consulta general, y después ir seccionando la información. Ventaja: si agregan películas, podré accederlas. Desventaja: hago una búsqueda muy grande posiblemente innecesaria
            //opcion 2: hardcodear (??!!) la cantidad de films disponibles - solo 6 - y luego ir haciendo consultas específicas. Ventaja: voy buscando solo la información necesaria. Desventaja: si se agregan películas, tendré que agregar manualmente las opciones
            //a los fines prácticos, se optan por las 2 opciones conociendo sus ventajas/ desventajas y la condición de "mala práctica" de hardcodear opciones que pueden cambiar con el tiempo

            switch (opcion)
            {
                case "a":
                    //búsqueda por título
                    System.out.println("buscando por título");
                    //TODO: validar que se ingrese al menos un caracter valido
                    System.out.println("Ingrese título en inglés");
                    String busquedaTitulo = scanner.nextLine();
                    String direccionTitulo = "https://swapi.dev/api/films/?search="+busquedaTitulo;
                    try
                    {
                        //TODO: crear clases separadas para client, request, response
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(direccionTitulo))
                                .build();
                        HttpResponse<String> response = client
                                .send(request, HttpResponse.BodyHandlers.ofString());

                        String json = response.body();
                        System.out.println(json);

                        //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

                        Gson gson = new GsonBuilder().create();

                        //Convertir via record class
                        //TODO: se crea un objeto con todos los campos null
                        PeliculaSwapi miPeliculaSwapi = gson.fromJson(json, PeliculaSwapi.class);
                        System.out.println(miPeliculaSwapi);

                        Pelicula miPelicula = new Pelicula(miPeliculaSwapi);
                        System.out.println(miPelicula);
                        System.out.println("¿Desea guardarla en Favoritas?(s/n)");
                        String porSioPorNo = scanner.nextLine();
                        if(porSioPorNo.equals("s"))
                        {
                            Favorita fav = new Favorita();
                            fav.agregarAFavortitas(miPelicula);
                            System.out.println("Guardada en Favoritas");
                        }

                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Ocurrió error con números");
                        System.out.println(e.getMessage());
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.out.println("Ocurrió un error de argumentos");
                        System.out.println(e.getMessage());
                    }
                    //catch (ErrorNAException e)
                    //{
                    //    System.out.println(e.getMessage());
                    //}
                    break;
                case "b":
                    String seleccion;
                    do {
                        //TODO: cambiar la forma de consultar?
                        //¿o AGREGAR la búsqueda por titulo?

                        //el num de peli está organizada cronológicamente en la base de datos por fecha de lanzamiento, no por episode_id
                        System.out.println("Películas disponibles");

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

                        String direccion = "https://swapi.dev/api/films/"+seleccion;

                        //la llave "count" tiene valor la cantidad de episodios disponibles
                        //ordenados por fecha ascendente. x ej; https://swapi.dev/api/films/1 es la primer pelicula de 1977, episodio 4
                        //TODO: opcion de hacer Loop por los 6 episodios disponibles
                        try
                        {
                            //TODO: crear clases separadas para client, request, response
                            HttpClient client = HttpClient.newHttpClient();
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(direccion))
                                    .build();
                            HttpResponse<String> response = client
                                    .send(request, HttpResponse.BodyHandlers.ofString());

                            String json = response.body();
                            System.out.println(json);

                            //Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

                            Gson gson = new GsonBuilder().create();

                            //Convertir via record class
                            PeliculaSwapi miPeliculaSwapi = gson.fromJson(json, PeliculaSwapi.class);
                            System.out.println(miPeliculaSwapi);

                            Pelicula miPelicula = new Pelicula(miPeliculaSwapi);
                            System.out.println(miPelicula);
                            System.out.println("¿Desea guardarla en Favoritas?(s/n)");
                            String porSioPorNo = scanner.nextLine();
                            if(porSioPorNo.equals("s"))
                            {
                                Favorita fav = new Favorita();
                                fav.agregarAFavortitas(miPelicula);
                                System.out.println("Guardada en Favoritas");
                            }

                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Ocurrió error con números");
                            System.out.println(e.getMessage());
                        }
                        catch (IllegalArgumentException e)
                        {
                            System.out.println("Ocurrió un error de argumentos");
                            System.out.println(e.getMessage());
                        }
                        //catch (ErrorNAException e)
                        //{
                        //    System.out.println(e.getMessage());
                        //}
                    }
                    while (!"x".equals(seleccion));
                    break;

                case "c":
                    String opcionOrden;
                    //historial de compras();
                    do {
                        //cuenta.mostrarHistorial();
                        System.out.println("acá se muestra el historial");
                        Favorita fav = new Favorita();
                        fav.mostrarLista();

                        System.out.println("\na: Ordena por nombre de producto A-Z");
                        System.out.println("b: Ordena por nombre de producto Z-A");
                        System.out.println("c: Ordena por precio menor a mayor");
                        System.out.println("d: Ordena por precio mayor a menor");
                        System.out.println("e: Ordena por fecha más lejana");
                        System.out.println("f: Ordena por fecha más próxima");
                        System.out.println("x: Volver atrás");

                        //convierto a minusculas por las dudas
                        opcionOrden = scanner.nextLine().toLowerCase();

                        switch (opcionOrden)
                        {
                            case "a":
                                System.out.println("ordenada por nombre");
                                //cuenta.ordenaPorNombre();
                                break;
                            case "b":
                                System.out.println("ordenada por nombre descendente");
                                //cuenta.ordenaPorNombreDescendente();
                                break;
                            case "c":
                                //cuenta.ordenaPorMonto();
                                System.out.println("ordenada por monto");
                                break;
                            case "d":
                                //cuenta.ordenaPorMontoDescendente();
                                System.out.println("ordenada por monto descendente");
                                break;
                            case "e":
                                //cuenta.ordenaPorFecha();
                                System.out.println("ordenada por fecha");
                                break;
                            case "f":
                                //cuenta.ordenaPorFechaDescendente();
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
                case "d":
                    System.out.println("Guardando información\n");
                    //FileWriter escritura = new FileWriter("peliculas.txt");
                    //escritura.write(miTitulo.toString());
                    //escritura.close();
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
