package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;

public class Funcionario extends Pessoa {

    private String cargo;
    private String usuario;
    private String senha;

    public Funcionario(String nome, String cpf, String telefone,String cargo, String usuario, String senha) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.usuario = usuario;
        this.senha = senha;
    }
}
