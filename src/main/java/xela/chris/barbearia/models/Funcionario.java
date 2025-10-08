package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um funcionário da barbearia, estendendo a classe {@link Pessoa}.
 * <p>
 * Cada funcionário possui um identificador único gerado automaticamente,
 * além de informações como cargo, usuário, senha e permissões de acesso.
 * </p>
 */
public class Funcionario extends Pessoa {

    private String cargo;
    private static final AtomicInteger contador = new AtomicInteger(0);
    private String usuario;
    private int id;
    private String senha;
    private List<PermissoesEnum> permissoes;

    /**
     * Construtor padrão da classe {@code Funcionario}.
     * <p>
     * Utilizado principalmente por frameworks de serialização e desserialização JSON.
     * </p>
     */
    public Funcionario() {
    }

    /**
     * Construtor completo da classe {@code Funcionario}.
     *
     * @param nome        o nome do funcionário
     * @param cpf         o CPF do funcionário
     * @param telefone    o telefone do funcionário
     * @param cargo       o cargo ocupado (ex: barbeiro, gerente)
     * @param permissoes  a lista de permissões associadas ao funcionário
     */
    public Funcionario(String nome, String cpf, String telefone, String cargo, List<PermissoesEnum> permissoes) {
        super(nome, cpf, telefone);
        this.cargo = cargo;
        this.permissoes = permissoes;
        this.usuario = "";
        this.senha = "";
        this.id = contador.incrementAndGet();
    }

    /**
     * Retorna o cargo do funcionário.
     *
     * @return o cargo atual
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Define o cargo do funcionário.
     *
     * @param cargo o novo cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Retorna o nome de usuário do funcionário.
     *
     * @return o nome de usuário
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Define o nome de usuário do funcionário.
     *
     * @param usuario o nome de usuário
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha do funcionário.
     *
     * @return a senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do funcionário.
     *
     * @param senha a nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna a lista de permissões associadas ao funcionário.
     *
     * @return lista de permissões
     */
    public List<PermissoesEnum> getPermissoes() {
        return permissoes;
    }

    /**
     * Define as permissões do funcionário.
     *
     * @param permissoes lista de permissões
     */
    public void setPermissoes(List<PermissoesEnum> permissoes) {
        this.permissoes = permissoes;
    }

    /**
     * Retorna o identificador único do funcionário.
     *
     * @return o ID do funcionário
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente o ID do funcionário.
     *
     * @param id o novo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna uma representação textual do funcionário.
     * <p>
     * Exibe os dados formatados, com CPF parcialmente anonimizado
     * e telefone no formato correto.
     * </p>
     *
     * @return string representando o funcionário
     */
    @Override
    public String toString() {
        return "\n ===============" +
                "\n Id: " + getId() +
                "\n Nome: " + getNome() +
                "\n Cpf: " + cpfPseudoAnonimizado() +
                "\n Telefone: " + telefoneCorreto() +
                "\n Cargo: " + getCargo() +
                "\n";
    }
}
