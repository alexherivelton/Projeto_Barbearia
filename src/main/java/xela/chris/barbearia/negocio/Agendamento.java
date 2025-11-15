package xela.chris.barbearia.negocio;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Servico;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um agendamento realizado na barbearia, contendo informações sobre
 * data e hora, cliente, funcionário responsável, serviços selecionados,
 * status do atendimento e a cadeira onde o cliente será atendido.
 *
 * Cada agendamento recebe um ID único gerado automaticamente.
 */
public class Agendamento {

    private String dataHoraAgendamento;
    /** Cliente associado ao agendamento. */
    Cliente cliente;
    /** Funcionário responsável pelo atendimento. */
    Funcionario funcionario;
    private List<Servico> servicos;
    private StatusAtendimentoCliente statusCliente;
    private int idCadeira;

    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;

    /**
     * Construtor padrão para uso geral.
     */
    public Agendamento() {}

    /**
     * Construtor que inicializa um novo agendamento com todas as informações necessárias.
     *
     * @param dataHoraAgendamento data e hora do agendamento
     * @param cliente cliente associado ao agendamento
     * @param funcionario funcionário responsável pelo atendimento
     * @param servicos lista de serviços selecionados
     * @param statusCliente status atual do atendimento do cliente
     * @param idCadeira número da cadeira onde o cliente será atendido
     */
    public Agendamento(String dataHoraAgendamento,
                       Cliente cliente,
                       Funcionario funcionario,
                       List<Servico> servicos,
                       StatusAtendimentoCliente statusCliente,
                       int idCadeira) {

        this.id = contador.incrementAndGet();
        this.dataHoraAgendamento = dataHoraAgendamento;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.servicos = servicos;
        this.statusCliente = statusCliente;
        this.idCadeira = idCadeira;
    }

    /**
     * Retorna o ID único do agendamento.
     *
     * @return ID do agendamento
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente um ID para o agendamento.
     *
     * @param id novo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data e hora do agendamento.
     *
     * @return data e hora
     */
    public String getDataHora() {
        return dataHoraAgendamento;
    }

    /**
     * Define a data e hora do agendamento.
     *
     * @param dataHoraAgendamento nova data e hora
     */
    public void setDataHora(String dataHoraAgendamento) {
        this.dataHoraAgendamento = dataHoraAgendamento;
    }

    /**
     * Retorna o cliente vinculado ao agendamento.
     *
     * @return cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Retorna o funcionário responsável pelo atendimento.
     *
     * @return funcionário
     */
    public Funcionario getFuncionario() {
        return funcionario;
    }

    /**
     * Retorna a lista de serviços escolhidos para este agendamento.
     *
     * @return lista de serviços
     */
    public List<Servico> getServicos() {
        return servicos;
    }

    /**
     * Retorna o número da cadeira onde o cliente será atendido.
     *
     * @return número da cadeira
     */
    public int getIdCadeira() {
        return idCadeira;
    }

    /**
     * Define qual cadeira será utilizada no atendimento.
     *
     * @param idCadeira número da cadeira
     */
    public void setIdCadeira(int idCadeira) {
        this.idCadeira = idCadeira;
    }

    /**
     * Retorna o status atual do atendimento do cliente.
     *
     * @return status
     */
    public StatusAtendimentoCliente getStatusCliente() {
        return statusCliente;
    }

    /**
     * Define o status do atendimento do cliente.
     *
     * Este método permite atualizar o status do atendimento do cliente,
     *
     *
     * @param statusCliente novo status a ser atribuído
     */

    public void setStatusCliente(StatusAtendimentoCliente statusCliente) {
        this.statusCliente = statusCliente;
    }

    /**
     * Atualiza o contador de IDs para manter a continuidade na numeração
     * quando os dados são carregados de arquivos ou outros repositórios.
     *
     * @param ultimoId maior ID encontrado
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Retorna uma representação textual do agendamento,
     * contendo os principais detalhes.
     *
     * @return string formatada com informações do agendamento
     */
    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + getId() +
                "\n Data/Hora: " + getDataHora() +
                "\n Cliente: " + (cliente != null ? cliente.getNome() : "N/A") +
                "\n Funcionário: " + (funcionario != null ? funcionario.getNome() : "N/A") +
                "\n Servicos:  " + servicos +
                "\n Status: " + (statusCliente != null ? statusCliente : "N/A") +
                "\n Cadeira: " + idCadeira +
                "\n";
    }
}
