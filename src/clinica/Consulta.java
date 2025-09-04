package clinica;

import dadospessoais.Medico;
import dadospessoais.Paciente;

public class Consulta {
    private final Paciente paciente;
    private final Medico medico;
    private final String dataHora;
    private boolean cancelada = false;

    public Consulta(Paciente paciente, Medico medico, String dataHora){
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;

    }

    public Paciente getPaciente(){
        return paciente;
    }
    public Medico getMedico(){
        return medico;
    }
    public String getDataHora(){
        return dataHora;
    }

    public boolean getCancelada(){
        return cancelada;
    }

    public void cancelarConsulta(){
        this.cancelada = true;
        System.out.println("Consulta em " + dataHora + " Cancelada para " + paciente.getNome() + " com Dr(a) " + medico.getNome() + ".");
    }

    public void exibirDetalhes(){
        String status = cancelada ? " (CANCELADA)" : "";
        System.out.println("Paciente " + paciente.getNome() +
                ", Medico: Dr(a) " + medico.getNome() + " (" + medico.getEspecialidade() + ")" +
                ", Data/Hora: " + dataHora + status);
    }
}
