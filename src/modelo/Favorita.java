package modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Favorita {

    private List<Pelicula> listaFavoritas = new ArrayList<>();

    public List<Pelicula> getListaFavoritas() {
        return listaFavoritas;
    }

    public void mostrarLista()
    {
        if(!listaFavoritas.isEmpty())
        {
            for(Pelicula item : listaFavoritas)
            {
                System.out.println(item);
            }
        }
        else
        {
            System.out.println("Aún no hay items en la lista");
        }
    }

    public void agregarAFavortitas(Pelicula peli)
    {
        //chequeo si ya existe en lista
        for (Pelicula item : listaFavoritas)
        {
            if(peli.getEpisodio()== item.getEpisodio())
            {
                System.out.println(peli.getTitulo() + " ya está en la lista");
                return;
            }
        }
        listaFavoritas.add(peli);
        System.out.println(peli.getTitulo() + " fue agregada a favoritas");
    }

    public void ordenaPorNombre()
    {
        listaFavoritas.sort(Comparator.comparing(Pelicula::getTitulo));
    }

    public void ordenaPorNombreDescendente()
    {
        listaFavoritas.sort(Comparator.comparing(Pelicula::getTitulo).reversed());
    }

    public void ordenaPorEpisodio()
    {
        listaFavoritas.sort(Comparator.comparing(Pelicula::getEpisodio));
    }

    public void ordenaPorEpisodioDescendente()
    {
        listaFavoritas.sort(Comparator.comparing(Pelicula::getEpisodio).reversed());
    }

    public void ordenaPorFecha()
    {
        listaFavoritas.sort(Comparator.comparing(Pelicula::getFechaEstreno));
    }

    public void ordenaPorFechaDescendente()
    {
        listaFavoritas.sort(Comparator.comparing(Pelicula::getFechaEstreno).reversed());
    }
}
