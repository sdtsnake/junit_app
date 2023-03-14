package work.oscarramos.juintapp.ejemplos.exeption;

public class DineroInsuficienteException extends RuntimeException {
    public DineroInsuficienteException(String message) {
        super(message);
    }
}
