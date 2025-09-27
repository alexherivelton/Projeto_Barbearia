package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.Date;
import java.util.List;

public class NotaFiscal {
    private int id;
    private Date dataEmissao;
    private Agendamento origem;
    private List<Produto> itens;

    public NotaFiscal(int id, Date dataEmissao, Agendamento origem, List<Produto> itens) {
        this.id = id;
        this.dataEmissao = dataEmissao;
        this.origem = origem;
        this.itens = itens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Agendamento getOrigem() {
        return origem;
    }

    public void setOrigem(Agendamento origem) {
        this.origem = origem;
    }

    public List<Produto> getItens() {
        return itens;
    }

    public void setItens(List<Produto> itens) {
        this.itens = itens;
    }
}
