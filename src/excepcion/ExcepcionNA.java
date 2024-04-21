package excepcion;

public class ExcepcionNA extends RuntimeException{

    private String mensaje;

    public ExcepcionNA(String mensaje)
    {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage(){
        return this.mensaje;
    }
}
