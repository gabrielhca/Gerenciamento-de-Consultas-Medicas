package exception;
import java.lang.Exception;

public class PacienteNaoEncontradoException extends Exception {
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
