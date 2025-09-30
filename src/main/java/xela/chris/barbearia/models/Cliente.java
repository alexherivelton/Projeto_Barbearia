package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.negocio.GeradoraDeIds;

import java.util.UUID;

public class Cliente extends Pessoa {

    private StatusAtendimentoCliente statusAtendimentoCliente;
    private int id;

    public Cliente(){

    }

    public Cliente(String nome, String cpf, String telefone, StatusAtendimentoCliente statusAtendimentoCliente) {
        super(nome, cpf, telefone);
        this.statusAtendimentoCliente = statusAtendimentoCliente;
        this.id = GeradoraDeIds.nextId();
    }

    @Override
    public String toString() {
        return "\n ===============" +
                "\n id=" + getId() +
                "\n Nome:" +getNome()+
                "\n Cpf: " + cpfPseudoAnonimizado()+
                "\n Telefone: " +telefoneCorreto() +
                "\n Status:" + statusAtendimentoCliente +
                "\n";
    }

    public StatusAtendimentoCliente getStatusAtendimentoCliente() {
        return statusAtendimentoCliente;
    }

    public void setStatusAtendimentoCliente(StatusAtendimentoCliente statusAtendimentoCliente) {
        this.statusAtendimentoCliente = statusAtendimentoCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
