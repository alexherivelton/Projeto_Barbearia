package xela.chris.barbearia.models;

public class Pessoa {

    private String nome;
    private String cpf;
    private String telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone != null) {
            this.telefone = telefone.replaceAll("[^0-9]", "");
        } else {
            this.telefone = null;
        }
    }


    public String cpfPseudoAnonimizado() {
        if (cpf == null || cpf.length() != 11) {
            return "CPF inválido";
        }
        return cpf.substring(0, 3) + ".***.***-" + cpf.substring(9, 11);
    }


    public String telefoneCorreto() {
        if (telefone == null || telefone.length() != 11) {
            return "Telefone Desconhecido. Formato esperado: (31 9 99999999)";
        }
        return "(" + telefone.substring(0, 2) + ") "
                + telefone.substring(2, 7) + "-"
                + telefone.substring(7);
    }
}
