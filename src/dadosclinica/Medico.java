package dadosclinica;

import java.util.Vector;

public class Medico {
    private final String nome;
    private final String crm;
    private final String especialidade;
    private final Vector<Consulta> consultas = new Vector<>();

    public Medico(String nome, String crm, String especialidade){
        this.nome = nome;
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getNome(){
        return nome;
    }

    public String getCrm(){
        return crm;
    }

    public String getEspecialidade(){
        return especialidade;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public Vector<Consulta> getConsultas(){
        return consultas;
    }
}
