package xela.chris.barbearia.models;

public class Cliente extends Pessoa {

    private int id;

    public Cliente(String nome, String cpf, String telefone) {
        super(nome, cpf, telefone);
        this.id = 0;
    }

    @Override
    public String toString() {
        return "\n ===============" +
                "\n id=" + id +
                "\n Nome:" +getNome()+
                "\n Cpf: " +getCpf()+
                "\n Telefone: " +getTelefone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
