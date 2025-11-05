package xela.chris.barbearia.negocio;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Servico;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Agendamento {
    private String dataHoraAgendamento;
    Cliente cliente;
    Funcionario funcionario;
    private List<Servico> servicos;
    private StatusAtendimentoCliente statusCliente;

    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;

    public Agendamento(){

    }

    public Agendamento(String dataHoraAgendamento, Cliente cliente, Funcionario funcionario, List<Servico> servicos, StatusAtendimentoCliente statusCliente) {
        this.id = contador.incrementAndGet();
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.servicos = servicos;
        this.statusCliente = statusCliente;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataHora() {
        return dataHoraAgendamento;
    }

    public void setDataHora(String dataHoraAgendamento) {
        this.dataHoraAgendamento = dataHoraAgendamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public StatusAtendimentoCliente getStatusCliente() {
        return statusCliente;
    }


    /**
     * Atualiza o contador de IDs para garantir que os próximos registros
     * continuem a sequência a partir do último ID existente.
     *
     * @param ultimoId maior ID encontrado ao carregar os dados
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + getId() +
                "\n Data/Hora: " + getDataHora() +
                "\n Cliente: " + (cliente != null ? cliente.getNome() : "N/A") +
                "\n Funcionário: " + (funcionario != null ? funcionario.getNome() : "N/A") +
                "\n Quantidade de Serviços: " + servicos +
                "\n Status: " + (statusCliente != null ? statusCliente : "N/A") +
                "\n";
    }


}
