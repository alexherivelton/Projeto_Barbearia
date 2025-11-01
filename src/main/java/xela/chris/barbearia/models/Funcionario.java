package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;

import java.util.ArrayList;
import java.util.Arrays;
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
     * Inicializa a lista de permissões do funcionário com um conjunto padrão de ações,
     * garantindo que todo funcionário criado com este construtor possa:
     * </p>
     * <ul>
     *   <li>Gerar notas fiscais ({@link PermissoesEnum#GERAR_NOTA})</li>
     *   <li>Cadastrar clientes ({@link PermissoesEnum#CADASTRAR_CLIENTE})</li>
     *   <li>Verificar clientes ({@link PermissoesEnum#VERIFICAR_CLIENTE})</li>
     *   <li>Criar agendamentos ({@link PermissoesEnum#CRIAR_AGENDAMENTO})</li>
     * </ul>
     * <p>
     * Este construtor é especialmente útil para frameworks de serialização e desserialização JSON.
     * </p>
     */
    public Funcionario() {
        this.permissoes = new ArrayList<>();
    }

    /**
     * Construtor completo da classe {@code Funcionario}.
     *
     * @param nome        o nome do funcionário
     * @param cpf         o CPF do funcionário
     * @param telefone    o telefone do funcionário
     * @param cargo       o cargo ocupado (ex: barbeiro, gerente)
     */
    public Funcionario(String nome, String cpf, String telefone, String cargo, String usuario , String senha) {
        super(nome, cpf, telefone);
        this.id = contador.incrementAndGet();
        this.cargo = cargo;
        this.permissoes = definirPermissoesPorCargo(cargo);
        this.usuario = usuario;
        this.senha = senha;
    }

    public List<PermissoesEnum> definirPermissoesPorCargo(String cargo) {
        List<PermissoesEnum> lista = new ArrayList<>();

        switch (cargo.toLowerCase()) {
            case "atendente":
                lista.add(PermissoesEnum.CADASTRAR_CLIENTE);
                lista.add(PermissoesEnum.VERIFICAR_CLIENTE);
                lista.add(PermissoesEnum.CRIAR_AGENDAMENTO);
                lista.add(PermissoesEnum.GERAR_NOTA);
                lista.add(PermissoesEnum.GERAR_BALANCO_MENSAL);
                break;

            case "barbeiro":
                lista.add(PermissoesEnum.VERIFICAR_CLIENTE);
                lista.add(PermissoesEnum.CRIAR_AGENDAMENTO);
                break;

            case "administrador":
                lista.addAll(List.of(
                        PermissoesEnum.values()
                ));
                break;
        }
        return lista;
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
     * Retorna uma cópia da lista de permissões associadas ao funcionário.
     * <p>
     * A lista retornada é uma nova instância, garantindo que alterações feitas fora
     * da classe não afetem a lista interna de permissões do funcionário.
     * </p>
     *
     * @return uma {@link List} contendo as permissões do funcionário
     */
    public List<PermissoesEnum> getPermissoes() {
        return new ArrayList<>(permissoes);
    }

    /**
     * Verifica se o funcionário possui uma permissão específica.
     *
     * @param permissao a permissão a ser verificada
     * @return {@code true} se o funcionário possui a permissão; {@code false} caso contrário
     */
    public boolean temPermissao(PermissoesEnum permissao) {
        return permissoes.contains(permissao);
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
                "\n Permissoes: " + definirPermissoesPorCargo(getCargo()) +
                "\n";
    }
}
