package dadospessoais;
import clinica.Consulta;
import java.util.ArrayList;
import java.util.List;

public class Medico extends Pessoa{
    private final String crm; 
    private final String especialidade;

    public Medico(String nome, String telefone, String email, String crm, String especialidade){
        super(nome, telefone, email); // a chamada super() precisa de 3 argumentos
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCrm(){
        return crm;
    }

    public String getEspecialidade(){
        return especialidade;
    }

    @Override
    public void exibirDetalhesEspecificos(){
        super.exibirDetalhesComuns();
        System.out.println("CRM: " + crm + ", Especialidade: " + especialidade);
    }

}
