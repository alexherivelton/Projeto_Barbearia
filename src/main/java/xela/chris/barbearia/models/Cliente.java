package xela.chris.barbearia.models;



public class Cliente extends Pessoa {

    private Long id;

    public Cliente(String nome, String cpf, String telefone, String senha) {
        super(nome, cpf, telefone);
        this.id = id;
    }
}
