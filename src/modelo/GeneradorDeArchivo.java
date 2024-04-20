package modelo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class GeneradorDeArchivo {

    public void guardarJson(Pelicula pelicula)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter escritura = new FileWriter(pelicula.getTitulo()+".json");
            escritura.write(gson.toJson(pelicula));
            escritura.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void guardarTxt(Pelicula pelicula)
    {
        try {
            FileWriter escritura = new FileWriter(pelicula.getTitulo()+".txt");
            escritura.write(pelicula.toString());
            escritura.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
