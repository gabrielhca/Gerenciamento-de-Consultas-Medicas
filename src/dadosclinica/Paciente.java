package dadosclinica;

import java.util.Vector;

public class Paciente {
    private final String nome;
    private final String cpf;
    private final String telefone;
    private final Vector<Consulta> consultas = new Vector<>();

    public Paciente(String nome, String cpf, String telefone){
       this.nome = nome;
       this.cpf = cpf;
       this.telefone = telefone;
    }

    public String getNome(){
        return nome;
    }

    public String getCpf(){
        return cpf;
    }

    public String getTelefone(){
        return telefone;
    }

    public void adicionarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    public Vector<Consulta> getConsultas(){
        return consultas;
    }
}


