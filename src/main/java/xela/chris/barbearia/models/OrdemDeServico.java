package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.models.Venda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa uma Ordem de Serviço (OS) na barbearia, agora focada
 * em registrar os detalhes de um serviço concluído usando IDs/CPFs
 * em vez de objetos complexos (Cliente/Funcionario/Agendamento).
 * <p>
 * Esta classe armazena os detalhes da transação para fins de histórico e relatório.
 * O contador estático {@code contador} armazena o total de instâncias criadas.
 * </p>
 */
public class OrdemDeServico {
    /** Contador estático para rastrear o número total de OS criadas. */
    private static final AtomicInteger contador = new AtomicInteger(0);

    /** Identificador único e final desta Ordem de Serviço. */
    private int id;

    /** A chave estrangeira (ID) do Serviço prestado (ou tipo de serviço). */
    private int servicoId;

    /** O CPF do Cliente que recebeu o serviço. */
    private String clienteCpf;

    /** O CPF do Funcionario que realizou o serviço. */
    private String funcionarioCpf;

    /** O valor total consolidado dos serviços/produtos. */
    private double valorTotal;

    /** Notas ou diagnóstico do profissional sobre o serviço realizado (usado como descrição). */
    private String descricaoServico;

    /** A data em que o serviço foi realizado (armazenada como String, ex: "dd/MM/yyyy"). */
    private String dataDoServico;


    /**
     * Construtor padrão (sem argumentos).
     * <p>
     * Necessário para a desserialização de JSON por frameworks como o Jackson.
     * </p>
     */
    public OrdemDeServico() {
    }

    /**
     * Construtor modificado para criar uma nova Ordem de Serviço,
     * baseada em dados simplificados (IDs/CPFs e valor).
     *
     * @param servicoId ID do Serviço.
     * @param clienteCpf CPF do Cliente.
     * @param funcionarioCpf CPF do Funcionário.
     * @param valorTotal Valor total gasto no serviço (apenas valor do serviço).
     * @param descricaoServico Descrição ou notas do profissional.
     * @param dataDoServico Data do serviço no formato "dd/MM/yyyy".
     */
    public OrdemDeServico(int servicoId, String clienteCpf, String funcionarioCpf, double valorTotal, String descricaoServico, String dataDoServico) {

        this.id = contador.incrementAndGet();
        this.servicoId = servicoId;
        this.clienteCpf = clienteCpf;
        this.funcionarioCpf = funcionarioCpf;
        this.valorTotal = valorTotal;
        this.descricaoServico = descricaoServico;
        this.dataDoServico = dataDoServico;
    }


    /**
     * Atualiza o contador estático para o último ID conhecido.
     * <p>
     * Usado principalmente ao carregar dados persistidos para garantir
     * que novos registros continuem a numeração.
     * </p>
     *
     * @param ultimoId O maior ID encontrado na persistência.
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Retorna o número total de Ordens de Serviço criadas (o valor
     * do contador estático).
     *
     * @return O total (int) de instâncias de OS criadas.
     */
    public static int getTotalOS() {
        return contador.get();
    }

    /**
     * Retorna o identificador único desta Ordem de Serviço.
     *
     * @return O ID da OS.
     */
    public int getId() { return id; }

    /**
     * Define o identificador único desta Ordem de Serviço.
     * <p>
     * Este método é primariamente usado por frameworks de desserialização (JSON).
     * </p>
     *
     * @param id O novo ID da OS.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Retorna o ID do Serviço prestado.
     *
     * @return O ID do serviço.
     */
    public int getServicoId() { return servicoId; }

    /**
     * Define o ID do Serviço prestado.
     *
     * @param servicoId O novo ID do serviço.
     */
    public void setServicoId(int servicoId) { this.servicoId = servicoId; }

    /**
     * Retorna o CPF do Cliente que recebeu o serviço.
     *
     * @return O CPF do cliente.
     */
    public String getClienteCpf() { return clienteCpf; }

    /**
     * Define o CPF do Cliente.
     *
     * @param clienteCpf O novo CPF do cliente.
     */
    public void setClienteCpf(String clienteCpf) { this.clienteCpf = clienteCpf; }

    /**
     * Retorna o CPF do Funcionário que realizou o serviço.
     *
     * @return O CPF do funcionário.
     */
    public String getFuncionarioCpf() { return funcionarioCpf; }

    /**
     * Define o CPF do Funcionário.
     *
     * @param funcionarioCpf O novo CPF do funcionário.
     */
    public void setFuncionarioCpf(String funcionarioCpf) { this.funcionarioCpf = funcionarioCpf; }

    /**
     * Retorna o valor total consolidado da Ordem de Serviço (serviços + produtos).
     *
     * @return O valor total.
     */
    public double getValorTotal() { return valorTotal; }

    /**
     * Define o valor total consolidado da Ordem de Serviço.
     *
     * @param valorTotal O novo valor total.
     */
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    /**
     * Retorna a descrição ou notas do serviço realizado.
     *
     * @return O texto da descrição.
     */
    public String getDiagnosticoServico() { return descricaoServico; }

    /**
     * Define a descrição ou notas do serviço realizado.
     *
     * @param descricaoServico O novo texto da descrição.
     */
    public void setDiagnosticoServico(String descricaoServico) { this.descricaoServico = descricaoServico; }

    /**
     * Retorna a data em que o serviço foi realizado (formato "dd/MM/yyyy").
     *
     * @return A data do serviço.
     */
    public String getDataDoServico() { return dataDoServico; }

    /**
     * Define a data em que o serviço foi realizado.
     *
     * @param dataDoServico A nova data do serviço.
     */
    public void setDataDoServico(String dataDoServico) { this.dataDoServico = dataDoServico; }

    /**
     * Retorna uma representação textual completa da Ordem de Serviço.
     *
     * @return Uma String formatada com todos os dados da OS.
     */
    @Override
    public String toString() {
        return "\n===============" +
                "\n Ordem de Serviço #" + id +
                "\n Data: " + dataDoServico +
                "\n Serviço ID: " + servicoId +
                "\n Cliente CPF: " + clienteCpf +
                "\n Funcionário CPF: " + funcionarioCpf +
                "\n Valor Total: R$ " + String.format("%.2f", valorTotal) +
                "\n Descrição/Diagnóstico: " + (descricaoServico != null ? descricaoServico : "N/A");
    }
}