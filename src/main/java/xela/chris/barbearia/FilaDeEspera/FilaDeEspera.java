package xela.chris.barbearia.FilaDeEspera;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Servico;


public class FilaDeEspera {

    private String horarioData;
    private Cliente cliente;
    private Servico servicoDesejado;
    private StatusAtendimentoCliente status;

    public FilaDeEspera(Servico servicoDesejado, Cliente cliente, String horarioData, StatusAtendimentoCliente status) {
        this.servicoDesejado = servicoDesejado;
        this.cliente = cliente;
        this.horarioData = horarioData;
        this.status = status;
    }

    public FilaDeEspera() {}

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
