package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;

import java.util.UUID;

public class Cliente extends Pessoa {

    private StatusAtendimentoCliente statusAtendimentoCliente;
    private String id;

    public Cliente(){
        this.id = UUID.randomUUID().toString();
    }

    public Cliente(String nome, String cpf, String telefone, StatusAtendimentoCliente statusAtendimentoCliente) {
        super(nome, cpf, telefone);
        this.statusAtendimentoCliente = statusAtendimentoCliente;
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "\n ===============" +
                "\n id=" + id +
                "\n Nome:" +getNome()+
                "\n Cpf: " +getCpf()+
                "\n Telefone: " +getTelefone() +
                "\n Status:" +statusAtendimentoCliente +
                "\n";
    }

    public StatusAtendimentoCliente getStatusAtendimentoCliente() {
        return statusAtendimentoCliente;
    }

    public void setStatusAtendimentoCliente(StatusAtendimentoCliente statusAtendimentoCliente) {
        this.statusAtendimentoCliente = statusAtendimentoCliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
