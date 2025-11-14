package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa uma nota fiscal emitida para um agendamento.
 * Contém informações sobre a data de emissão, origem (agendamento)
 * e os produtos/itens adquiridos.
 */
public class NotaFiscal {

    private static final AtomicInteger contador = new AtomicInteger(0);


    private int id;
    private Date dataEmissao;
    private Agendamento agendamento;
    private List<Servico> servicos = new ArrayList<>();
    private List<Produto> itens;
    private double valorTotal;
    /**
     * Construtor da classe NotaFiscal.
     *
     * @param agendamento o agendamento cujos serviços compõem a nota fiscal
     */
    public NotaFiscal(Agendamento agendamento) {
        this.id = contador.incrementAndGet();
        this.dataEmissao = new Date();
        this.agendamento = agendamento;
        if (agendamento != null) {
            this.servicos = new ArrayList<>(agendamento.getServicos());
            this.valorTotal = this.servicos.stream()
                    .mapToDouble(Servico::getPreco)
                    .sum();
        }
    }

    /**
     * Retorna o identificador da nota fiscal.
     *
     * @return id da nota fiscal.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador da nota fiscal.
     *
     * @param id Novo identificador da nota fiscal.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data de emissão da nota fiscal.
     *
     * @return data de emissão.
     */
    public Date getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define a data de emissão da nota fiscal.
     *
     * @param dataEmissao Nova data de emissão.
     */
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    /**
     * Retorna o agendamento de origem da nota fiscal.
     *
     * @return agendamento relacionado.
     */
    public Agendamento getAgendamento() {
        return agendamento;
    }

    /**
     * Define o agendamento de origem da nota fiscal.
     *
     * @param agendamento Novo agendamento relacionado.
     */
    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        if (agendamento != null) {
            this.servicos = new ArrayList<>(agendamento.getServicos());
            this.valorTotal = this.servicos.stream()
                    .mapToDouble(Servico::getPreco)
                    .sum();
        }
    }


    /**
     * Obtém a lista de serviços desta nota fiscal.
     *
     * @return lista de serviços
     */
    public List<Servico> getServicos() {
        return servicos;
    }

    /**
     * Define a lista de serviços e atualiza o valor total conforme os preços.
     *
     * @param servicos nova lista de serviços
     */
    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
        this.valorTotal = servicos.stream().mapToDouble(Servico::getPreco).sum();
    }

    /**
     * Retorna a lista de produtos/itens da nota fiscal.
     *
     * @return lista de produtos.
     */
    public List<Produto> getItens() {
        return itens;
    }

    /**
     * Define a lista de produtos/itens da nota fiscal.
     *
     * @param itens Nova lista de produtos.
     */
    public void setItens(List<Produto> itens) {
        this.itens = itens;
    }

    /**
     * Obtém o valor total da nota fiscal.
     *
     * @return soma dos preços dos serviços
     */
    public double getValorTotal() {
        return valorTotal;
    }


    /**
     * Atualiza o contador estático para sincronização com IDs persistidos.
     *
     * @param ultimoId maior ID encontrado ao carregar dados
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }


    /**
     * Representação em string da nota fiscal, detalhando cada serviço
     * e o valor total. Também formata a data de emissão para melhor
     * legibilidade.
     *
     * @return string com detalhes da nota fiscal
     */
    @Override
    public String toString() {

        String clienteNome = (agendamento != null && agendamento.getCliente() != null)
                ? agendamento.getCliente().getNome()
                : "N/A";

        String funcionarioNome = (agendamento != null && agendamento.getFuncionario() != null)
                ? agendamento.getFuncionario().getNome()
                : "N/A";

        String listaServicos = "";
        for (Servico s : servicos) {
            listaServicos += "  • " + s.getNome() + " - R$ " + String.format("%.2f", s.getPreco()) + "\n";
        }

        return "\n===============" +
                "\n ID Nota: " + id +
                "\n Data de Emissão: " + dataEmissao +
                "\n Cliente: " + clienteNome +
                "\n Funcionário: " + funcionarioNome +
                "\n Serviços:\n" + listaServicos +
                " Valor Total: R$ " + String.format("%.2f", valorTotal) +
                "\n===============";
    }
}
