package notificacao;

// essa classe implementa a interface
// o que significa que ela é obrigada
// a fornecer o codigo para o método enviarNotificacao();

public class NotificacaoSMS implements Notificavel {
    @Override
    public void enviarNotificacao(String mensagem) {
        System.out.println("Enviado SMS: " + mensagem);
    }
}