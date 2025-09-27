package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;

import java.util.List;

public class Funcionario extends Pessoa {

    private String cargo;
    private String usuario;
    private String senha;
    private List<PermissoesEnum> permissoes;

    public Funcionario() {
    }

    public Funcionario(String nome, String cpf, String telefone, String cargo, List<PermissoesEnum> permissoes) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.permissoes = permissoes;
        this.usuario = "";
        this.senha = "";
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<PermissoesEnum> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissoesEnum> permissoes) {
        this.permissoes = permissoes;
    }

    @Override
    public String toString() {
        return "\n ===============" +
                "\n Nome:" +getNome() +
                "\n Cpf: " +getCpf() +
                "\n Telefone: " +getTelefone() +
                "\n Cargo: " + getCargo() +
                "\n";
    }
}
