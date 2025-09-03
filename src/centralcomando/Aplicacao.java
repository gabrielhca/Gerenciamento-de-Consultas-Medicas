package centralcomando;
import java.util.*;
import dadosclinica.*;

public class Aplicacao {
    private final Vector<Paciente> pacientes = new Vector<>();
    private final Vector<Consulta> consultas = new Vector<>();
    private final Vector<Medico> medicos = new Vector<>();
    private final Scanner scanner = new Scanner(System.in);

    public void executar(){
        int opcao;

        do {
            System.out.println("\nMenu");
            System.out.println("1. Adicionar Paciente");
            System.out.println("2. Adicionar Medico");
            System.out.println("3. Agendar Consulta");
            System.out.println("4. Cancelar Consulta");
            System.out.println("5. Listar Pacientes");
            System.out.println("6. Listar Medicos");
            System.out.println("7. Listar Consultas");
            System.out.println("8. Sair");
            opcao = Integer.parseInt(scanner.nextLine());

            switch(opcao){
                case 1 -> adicionarPaciente();
                case 2 -> adicionarMedico();
                case 3 -> agendarConsulta();
                case 4 -> cancelarConsulta();
                case 5 -> listarPacientes();
                case 6 -> listarMedicos();
                case 7 -> listarConsultas();
            }
        }while(opcao != 8);
    }

    private void adicionarPaciente(){
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Paciente paciente = new Paciente(nome, cpf, telefone);
        this.pacientes.add(paciente);
        System.out.println("Paciente registrado com sucesso!");
    }

    private void adicionarMedico(){
        System.out.print("Nome do Medico: ");
        String nome = scanner.nextLine();

        System.out.print("CRM: ");
        String crm = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        Medico medico = new Medico(nome, crm, especialidade);
        this.medicos.add(medico);
        System.out.println("Medico registrado com sucesso!");
    }

    private void agendarConsulta(){
        Paciente paciente = selecionarPaciente();
        if (paciente == null) return;

        Medico medico = selecionarMedico();
        if (medico == null) return;

        System.out.println("Data (dd/mm/aa) - Horario (hh:mm)");
        String dataHora = scanner.nextLine();

        Consulta novaConsulta = new Consulta(paciente, medico, dataHora);
        consultas.add(novaConsulta);

        paciente.adicionarConsulta(novaConsulta);
        medico.adicionarConsulta(novaConsulta);

        System.out.println("Consulta marcada!");

    }

    private void cancelarConsulta(){
        if(consultas.isEmpty()){
            System.out.println("Nenhuma consulta cadastrada!");
            return;
        }
        for(int i = 0; i < consultas.size(); i++){
            System.out.println((i+1) + ". ");
            consultas.get(i).exibirDetalhes();
        }

        System.out.print("Escolha o numero da consulta para cancelar: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if(index >= 0 && index <consultas.size()){
            Consulta consultaParaCancelar = consultas.get(index);
            if(!consultaParaCancelar.getCancelada()) {
                consultaParaCancelar.cancelarConsulta();
            } else {
                System.out.println("Essa consulta ja foi cancelada.");
            }
        } else {
            System.out.println("Indice invalido!");
        }

    }

    private void listarPacientes(){
        if (pacientes.isEmpty()){
            System.out.println("Nenhum paciente cadastrado!");
        } else {
            System.out.println("Pacientes cadastrados: ");
            for(int i = 0; i < pacientes.size(); i++) {
                System.out.println((i+1) + ". " + pacientes.get(i).getNome());
            }
        }
    }

    private void listarMedicos(){
        if(medicos.isEmpty()){
            System.out.println("Nenhum medico cadastrado!");
        } else {
            System.out.println("Medicos cadastrados: ");
            for(int i = 0; i < medicos.size(); i++){
                System.out.println((i+1) + ". " + medicos.get(i).getNome() + ", Especialidade: " + medicos.get(i).getEspecialidade());
            }
        }
    }

    private void listarConsultas(){
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada!");
            return;
        }

        System.out.println("Escolha listar consultas de:");
        System.out.println("1. Medico");
        System.out.println("2. Paciente");
        int index = Integer.parseInt(scanner.nextLine());

        switch (index) {
            case 1 -> {
                Medico medico = selecionarMedico();
                if(medico == null){
                    return;
                }

                Vector<Consulta> consultasDoMedico = medico.getConsultas();

                if(consultasDoMedico.isEmpty()){
                    System.out.println("Nunhuma consulta agendada!");
                } else {
                    System.out.println("Consultas do Dr(a). " + medico.getNome() + ":");
                    for(int i = 0; i < consultasDoMedico.size(); i ++) {
                        System.out.print((i+1) + ". ");
                        consultasDoMedico.get(i).exibirDetalhes();
                    }
                }
            }
            case 2 -> {
                Paciente paciente = selecionarPaciente();
                if(paciente == null){
                    return;
                }

                Vector<Consulta> consultasDoPaciente = paciente.getConsultas();

                if(consultasDoPaciente.isEmpty()){
                    System.out.println("Nunhuma consulta agendada!");
                } else {
                    System.out.println("Consultas do paciente " + paciente.getNome() + ":");
                    for(int i = 0; i < consultasDoPaciente.size(); i ++) {
                        System.out.print((i+1) + ". ");
                        consultasDoPaciente.get(i).exibirDetalhes();
                    }
                }
            }
            default -> System.out.println("Opcao invalida!");
        }
    }

    private Paciente selecionarPaciente(){
        listarPacientes();
        System.out.print("Escolha um paciente: ");
        int index = Integer.parseInt(scanner.next()) - 1;

        // Valida e retorna o paciente selecionado
        if (index >= 0 && index < pacientes.size()){
            return pacientes.get(index);
        } else {
            System.out.println("Indice invalido!");
            return null;
        }

    }

    private Medico selecionarMedico(){
        listarMedicos();
        System.out.print("Escolha um medico: ");
        int index = Integer.parseInt(scanner.next()) - 1;

        // Validade e retorna o medico selecionado
        if(index > 0 && index < medicos.size()){
            return medicos.get(index);
        } else {
            System.out.println("Indice invalido!");
            return null;
        }

    }
}
