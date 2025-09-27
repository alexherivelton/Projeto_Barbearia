package xela.chris.barbearia.models;

public class Funcionario extends Pessoa {

    private String cargo;
    private String usuario;
    private String senha;

    public Funcionario() {
    }

    public Funcionario(String nome, String cpf, String telefone, String cargo, String usuario, String senha) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.usuario = usuario;
        this.senha = senha;
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
}
