package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;

public class Administrador extends Funcionario {
    public Administrador(String nome, String cpf, String telefone, String cargo, String usuario, String senha) {
        super(nome, cpf, telefone, cargo, usuario, senha);
    }
}
