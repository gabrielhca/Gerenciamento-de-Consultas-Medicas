package dadospessoais;

import java.util.ArrayList;
import java.util.List;
import clinica.Consulta;

public abstract class Pessoa {
    private final String nome;
    private final String telefone;
    private final String email;
    protected final List<Consulta> consultas;

    public Pessoa(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.consultas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void adicionarConsulta(Consulta consulta){
        this.consultas.add(consulta);
    }

    public List<Consulta> getConsultas(){ return consultas; }

    // é um metodo concreto
    public void exibirDetalhesComuns(){
        System.out.println("Nome: " + nome);
        System.out.println("Telefone: " + telefone);
        System.out.println("E-mail: " + email);
    }

    // é o metodo abstrato que cada subclasse deve implementar
    public abstract void exibirDetalhesEspecificos();
}