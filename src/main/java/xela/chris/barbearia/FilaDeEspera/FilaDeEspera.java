package xela.chris.barbearia.FilaDeEspera;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Servico;

public class FilaDeEspera {
    Cliente nomeCliente;
    Servico servicoDesejado;
    String horarioData;
    StatusAtendimentoCliente status;

    public FilaDeEspera(Servico servicoDesejado, Cliente nomeCliente, String horarioData, StatusAtendimentoCliente status) {
        this.servicoDesejado = servicoDesejado;
        this.nomeCliente = nomeCliente;
        this.horarioData = horarioData;
        this.status = status;
    }

    public Cliente getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(Cliente nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Servico getServicoDesejado() {
        return servicoDesejado;
    }

    public void setServicoDesejado(Servico servicoDesejado) {
        this.servicoDesejado = servicoDesejado;
    }

    public String getHorarioData() {
        return horarioData;
    }

    public void setHorarioData(String horarioData) {
        this.horarioData = horarioData;
    }

    public StatusAtendimentoCliente getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimentoCliente status) {
        this.status = status;
    }
}
