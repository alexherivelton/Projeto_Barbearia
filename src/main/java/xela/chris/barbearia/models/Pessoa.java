package xela.chris.barbearia.models;

/**
 * Classe abstrata que representa uma pessoa no sistema da barbearia.
 * <p>
 * É utilizada como base para outras classes, como {@link Funcionario} e {@code Cliente},
 * armazenando informações comuns como nome, CPF e telefone.
 * </p>
 */
public abstract class Pessoa {

    /** Nome completo da pessoa. */
    private String nome;

    /** CPF da pessoa (somente números). */
    private String cpf;

    /** Telefone da pessoa, armazenado apenas com dígitos numéricos. */
    private String telefone;

    /**
     * Construtor padrão da classe {@code Pessoa}.
     * <p>
     * Necessário para frameworks de serialização e para criação de subclasses sem parâmetros.
     * </p>
     */
    public Pessoa() {
    }

    /**
     * Construtor completo da classe {@code Pessoa}.
     *
     * @param nome     o nome completo da pessoa
     * @param cpf      o CPF da pessoa (somente números)
     * @param telefone o telefone da pessoa (com ou sem formatação)
     */
    public Pessoa(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    /**
     * Retorna o nome da pessoa.
     *
     * @return o nome completo
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da pessoa.
     *
     * @param nome o nome completo
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o CPF da pessoa.
     *
     * @return o CPF em formato numérico (sem pontuação)
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Define o CPF da pessoa.
     *
     * @param cpf o CPF em formato numérico
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Retorna o telefone da pessoa.
     *
     * @return o telefone em formato numérico
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Define o telefone da pessoa, removendo automaticamente quaisquer
     * caracteres não numéricos (como parênteses, espaços ou traços).
     *
     * @param telefone o telefone a ser definido
     */
    public void setTelefone(String telefone) {
        if (telefone != null) {
            this.telefone = telefone.replaceAll("[^0-9]", "");
        } else {
            this.telefone = null;
        }
    }

    /**
     * Retorna o CPF parcialmente anonimizado para exibição.
     * <p>
     * Exemplo: {@code 123.***.***-45}
     * </p>
     *
     * @return o CPF anonimizado ou "CPF inválido" caso o valor seja nulo ou incorreto
     */
    public String cpfPseudoAnonimizado() {
        if (cpf == null || cpf.length() != 11) {
            return "CPF inválido";
        }
        return cpf.substring(0, 3) + ".***.***-" + cpf.substring(9, 11);
    }

    /**
     * Retorna o telefone formatado no padrão brasileiro.
     * <p>
     * Exemplo: {@code (31) 91234-5678}
     * </p>
     *
     * @return o telefone formatado ou mensagem de erro caso o formato seja inválido
     */
    public String telefoneCorreto() {
        if (telefone == null || telefone.length() != 11) {
            return "Telefone Desconhecido. Formato esperado: (31 9 99999999)";
        }
        return "(" + telefone.substring(0, 2) + ") "
                + telefone.substring(2, 7) + "-"
                + telefone.substring(7);
    }
}
