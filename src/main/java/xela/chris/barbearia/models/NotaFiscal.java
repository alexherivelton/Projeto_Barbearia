package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.Date;

public class NotaFiscal {
    private int id;
    private Date dataEmissao;
    private Agendamento origem;
    private List<NotaItem> itens;

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}
