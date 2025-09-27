package xela.chris.barbearia.negocio;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Servico;

import java.util.Date;
import java.util.List;

public class Agendamento {
    private Date dataHora;
    Cliente cliente;
    Funcionario funcionario;
    private List<Servico> servicos;
    private StatusAtendimentoCliente statusCliente;

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
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
}
