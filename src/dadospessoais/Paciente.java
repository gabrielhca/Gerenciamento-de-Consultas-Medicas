package dadospessoais;
import clinica.Consulta;
import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa{
    private final String cpf;


    public Paciente(String nome, String telefone, String email, String cpf){
        super(nome, telefone, email); // a chamada super() precisa de 3 argumentos
        this.cpf = cpf;
    }
    public String getCpf(){
        return cpf;
    }

    @Override
    public void exibirDetalhesEspecificos(){
        super.exibirDetalhesComuns();
        System.out.println("CPF: " + cpf);
    }
}


