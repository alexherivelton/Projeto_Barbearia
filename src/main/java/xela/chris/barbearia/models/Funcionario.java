package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Funcionario extends Pessoa {

    private String cargo;
    private static final AtomicInteger contador = new AtomicInteger(0);
    private String usuario;
    private int id;
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
        this.id = contador.incrementAndGet();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\n ===============" +
                "\n Id:" +getId() +
                "\n Nome:" +getNome() +
                "\n Cpf: " +cpfPseudoAnonimizado() +
                "\n Telefone: " +telefoneCorreto() +
                "\n Cargo: " + getCargo() +
                "\n";
    }
}
