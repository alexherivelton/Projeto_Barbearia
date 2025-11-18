package xela.chris.barbearia.FilaDeEspera;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Servico;


/**
 * Representa uma entrada na fila de espera da barbearia.
 *
 * Esta classe armazena as informações de um cliente que está aguardando
 * atendimento, incluindo o cliente em si, o serviço que ele deseja,
 * o horário/data da solicitação e o status atual do seu atendimento.
 */
public class FilaDeEspera {

    private String horarioData;
    private Cliente cliente;
    private Servico servicoDesejado;
    private StatusAtendimentoCliente status;

    /**
     * Construtor completo para criar uma nova entrada na fila de espera.
     *
     * @param servicoDesejado O serviço que o cliente deseja realizar.
     * @param cliente O cliente que está aguardando.
     * @param horarioData A data e hora da solicitação ou entrada na fila.
     * @param status O status inicial do cliente (ex: AGUARDANDO).
     */
    public FilaDeEspera(Servico servicoDesejado, Cliente cliente, String horarioData, StatusAtendimentoCliente status) {
        this.servicoDesejado = servicoDesejado;
        this.cliente = cliente;
        this.horarioData = horarioData;
        this.status = status;
    }

    /**
     * Construtor padrão (sem argumentos).
     * Cria uma instância de FilaDeEspera com todos os campos nulos (ou padrão).
     */
    public FilaDeEspera() {}

    /**
     * Obtém o cliente associado a esta entrada da fila.
     *
     * @return O objeto {@link Cliente}.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente para esta entrada da fila.
     *
     * @param cliente O objeto {@link Cliente}.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtém o serviço desejado pelo cliente nesta entrada da fila.
     *
     * @return O objeto {@link Servico}.
     */
    public Servico getServicoDesejado() {
        return servicoDesejado;
    }

    /**
     * Define o serviço desejado para esta entrada da fila.
     *
     * @param servicoDesejado O objeto {@link Servico}.
     */
    public void setServicoDesejado(Servico servicoDesejado) {
        this.servicoDesejado = servicoDesejado;
    }

    /**
     * Obtém a string de data e hora desta entrada na fila.
     *
     * @return A data e hora (formato String).
     */
    public String getHorarioData() {
        return horarioData;
    }

    /**
     * Define a string de data e hora para esta entrada na fila.
     *
     * @param horarioData A data e hora (formato String).
     */
    public void setHorarioData(String horarioData) {
        this.horarioData = horarioData;
    }

    /**
     * Obtém o status atual do atendimento desta entrada da fila.
     *
     * @return O enum {@link StatusAtendimentoCliente}.
     */
    public StatusAtendimentoCliente getStatus() {
        return status;
    }

    /**
     * Define o status do atendimento para esta entrada da fila.
     *
     * @param status O enum {@link StatusAtendimentoCliente}.
     */
    public void setStatus(StatusAtendimentoCliente status) {
        this.status = status;
    }

    /**
     * Retorna uma representação em String desta entrada da FilaDeEspera.
     * Inclui informações do cliente, ID do serviço, horário e status.
     *
     * @return Uma String formatada com os detalhes do objeto.
     */
    // ...
    @Override
    public String toString() {
        return "FilaDeEspera{" +
                "\ncliente=" + (cliente != null ? cliente : "null") +
                ",\n servico=" + servicoDesejado.getId() +
                ",\n horario=" + horarioData +
                ",\n status=" + status +
                '}';
    }
}