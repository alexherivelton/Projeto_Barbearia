package xela.chris.barbearia.Tests;

import xela.chris.barbearia.FilaDeEspera.FilaDeEspera;
import xela.chris.barbearia.FilaDeEspera.GerenciaFilaDeEspera;
import xela.chris.barbearia.Gerenciadores.GerenciarCliente;
import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Servico;

import java.time.LocalDateTime;

public class TestFilaDeEspera {
    public static void main(String[] args) {
        GerenciaFilaDeEspera fila = new GerenciaFilaDeEspera();
        GerenciarCliente cliente = new GerenciarCliente();
        GerenciarServico servico = new GerenciarServico();

//         Criando objetos apenas para teste
        Cliente c1 = new Cliente("Roberto Souza", "12345678900", "33998487867", StatusAtendimentoCliente.AGENDADO );
        Cliente c2 = new Cliente("Gustavo Nascimento", "09876543211", "31998769756", StatusAtendimentoCliente.AGENDADO);
        Cliente c3 = new Cliente("Hugo Loures", "09876543222", "21998377645", StatusAtendimentoCliente.AGENDADO);
        cliente.salvarTodosClientes();

//         Adicionando 3 clientes na fila
        fila.adicionarClienteNaFila(new FilaDeEspera(servico.buscarPorId(1), c1, "14/11/25 - 14:30", StatusAtendimentoCliente.EM_ESPERA));
        fila.adicionarClienteNaFila(new FilaDeEspera(servico.buscarPorId(1), c2, "14/11/25 - 15:30", StatusAtendimentoCliente.EM_ESPERA));
        fila.adicionarClienteNaFila(new FilaDeEspera(servico.buscarPorId(1), c3, "14/11/25 - 16:30", StatusAtendimentoCliente.EM_ESPERA));

        fila.adicionarClienteNaFila(new FilaDeEspera(servico.buscarPorId(1), cliente.buscarCliente(1), "14/11/25 - 14:30", StatusAtendimentoCliente.EM_ESPERA ));
        fila.adicionarClienteNaFila(new FilaDeEspera(servico.buscarPorId(1), cliente.buscarCliente(9), "14/11/25 - 14:30", StatusAtendimentoCliente.EM_ESPERA ));
        fila.adicionarClienteNaFila(new FilaDeEspera(servico.buscarPorId(1), cliente.buscarCliente(10), "14/11/25 - 14:30", StatusAtendimentoCliente.EM_ESPERA ));

        fila.salvarFilaDeEspera();

        System.out.println("Chamando o próximo da fila (1)...");
        FilaDeEspera p1 = fila.buscarComFIFONaLista();
        System.out.println("Chamado: " + p1 + "\n");

        System.out.println("Fila após chamada 1:");
        fila.getFilaDeEspera().forEach(System.out::println);

        // ============================
        // SEGUNDA CHAMADA FIFO
        // ============================

        System.out.println("\nChamando o próximo da fila (2)...");
        FilaDeEspera p2 = fila.buscarComFIFONaLista();
        System.out.println("Chamado: " + p2 + "\n");

        System.out.println("Fila após chamada 2:");
        fila.getFilaDeEspera().forEach(System.out::println);

    }
}
