package centralcomando;
import java.util.*;

import clinica.Consulta;
import dadospessoais.*;
import notificacao.*;
import exception.*;

public class Aplicacao {
    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();
    private final List<Medico> medicos = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public void executar(){
        int opcao = 0; // inicia com zero para entrar no loop

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

            try {
                System.out.print("Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine());

                switch(opcao){
                    case 1 -> adicionarPaciente();
                    case 2 -> adicionarMedico();
                    case 3 -> agendarConsulta();
                    case 4 -> cancelarConsulta();
                    case 5 -> listarPacientes();
                    case 6 -> listarMedicos();
                    case 7 -> {
                        try {
                            listarConsultas();
                        } catch (PacienteNaoEncontradoException | MedicoNaoEncontradoException e) {
                            System.out.println("Erro ao listar consultas: " + e.getMessage());
                        }
                    }
                    case 8 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção invalida! Por favor, escolha um numero de 1 a 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, digite um número.");
            }
        }while(opcao != 8);
    }

    private void adicionarPaciente(){
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        Paciente paciente = new Paciente(nome, telefone, email, cpf);
        this.pacientes.add(paciente);
        System.out.println("Paciente registrado com sucesso!");
    }

    private void adicionarMedico(){
        System.out.print("Nome do Medico: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("CRM: ");
        String crm = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        Medico medico = new Medico(nome, telefone, email, crm, especialidade);
        this.medicos.add(medico);
        System.out.println("Medico registrado com sucesso!");
    }

    private void agendarConsulta(){
        try {
            Paciente paciente = selecionarPaciente();
            Medico medico = selecionarMedico();

            System.out.println("Data (dd/mm/aa) - Horario (hh:mm)");
            String dataHora = scanner.nextLine();

            Consulta novaConsulta = new Consulta(paciente, medico, dataHora);
            consultas.add(novaConsulta);

            paciente.adicionarConsulta(novaConsulta);
            medico.adicionarConsulta(novaConsulta);

            System.out.println("Consulta marcada!");

            Notificavel notificacao = new NotificacaoSMS();
            String mensagem = "Sua consulta com Dr(a): " + medico.getNome()
                    + " foi agendada para " + dataHora + ".";

        } catch (PacienteNaoEncontradoException | MedicoNaoEncontradoException e) {
            System.out.println("Erro ao agendar Consulta!" + e.getMessage());
        }
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

        int index;

        try {
            System.out.print("Escolha o numero da consulta para cancelar: ");
            index = Integer.parseInt(scanner.nextLine()) - 1;

            if(index >= 0 && index < consultas.size()){
                Consulta consultaParaCancelar = consultas.get(index);
                if(!consultaParaCancelar.getCancelada()) {
                    Notificavel notificacao = new NotificacaoEmail();
                    String mensagem = "Sua consulta com Dr(a) " + consultaParaCancelar.getMedico().getNome()
                            + " em " + consultaParaCancelar.getDataHora() + " foi cancelada.";
                    notificacao.enviarNotificacao(mensagem);
                } else {
                    System.out.println("Essa consulta ja foi cancelada.");
                }
            } else {
                System.out.println("Indice invalido!");
            }
            // NumberFormatException é um erro quando o metodo tenta converter uma String em um tipo numerico, mas a String não contem um caracter valido para conversão
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida. Por favor, digite um numero.");
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

    private void listarConsultas() throws PacienteNaoEncontradoException, MedicoNaoEncontradoException {
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

                List<Consulta> consultasDoMedico = medico.getConsultas();

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

                List<Consulta> consultasDoPaciente = paciente.getConsultas();

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

    private Paciente selecionarPaciente() throws PacienteNaoEncontradoException {
        listarPacientes();
        if(pacientes.isEmpty()){
            throw new PacienteNaoEncontradoException("Nenhum paciente cadastrado!");
        }

        System.out.print("Escolha um paciente: ");

        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < pacientes.size()){
                return pacientes.get(index);
            } else {
                throw new PacienteNaoEncontradoException("Indice de paciente invalido!");
            }
        } catch (NumberFormatException e) {
            throw new PacienteNaoEncontradoException("Entrada invalida! Por favor, digite um numero.");
        }
    }

    private Medico selecionarMedico() throws MedicoNaoEncontradoException {
        listarMedicos();
        if (medicos.isEmpty()) {
            throw new MedicoNaoEncontradoException("Nenhum medico cadastrado!");
        }

        System.out.print("Escolha um medico: ");

        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;
            if (index >= 0 && index < medicos.size()) {
                return medicos.get(index);
            } else {
                throw new MedicoNaoEncontradoException("Indice de medico invalido!");
            }
        } catch (NumberFormatException e) {
            throw new MedicoNaoEncontradoException("Entrada invalida! Por favor, digite um numero.");
        }
    }
}
