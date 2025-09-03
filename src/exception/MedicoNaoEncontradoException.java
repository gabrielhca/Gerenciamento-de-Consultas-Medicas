package exception;
import java.lang.Exception;

public class MedicoNaoEncontradoException extends Exception {
    public MedicoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
