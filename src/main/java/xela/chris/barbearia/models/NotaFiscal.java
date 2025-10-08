package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.Date;
import java.util.List;

/**
 * Representa uma nota fiscal emitida para um agendamento.
 * Contém informações sobre a data de emissão, origem (agendamento)
 * e os produtos/itens adquiridos.
 */
public class NotaFiscal {
    private int id;
    private Date dataEmissao;
    private Agendamento origem;
    private List<Produto> itens;

    /**
     * Construtor da classe NotaFiscal.
     *
     * @param id         Identificador único da nota fiscal.
     * @param dataEmissao Data de emissão da nota fiscal.
     * @param origem     Agendamento relacionado à nota fiscal.
     * @param itens      Lista de produtos ou serviços da nota fiscal.
     */
    public NotaFiscal(int id, Date dataEmissao, Agendamento origem, List<Produto> itens) {
        this.id = id;
        this.dataEmissao = dataEmissao;
        this.origem = origem;
        this.itens = itens;
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
    public Agendamento getOrigem() {
        return origem;
    }

    /**
     * Define o agendamento de origem da nota fiscal.
     *
     * @param origem Novo agendamento relacionado.
     */
    public void setOrigem(Agendamento origem) {
        this.origem = origem;
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
}
