package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um funcionário da barbearia, herdando informações básicas da classe {@link Pessoa}.
 *
 * <p>Cada funcionário possui um ID único gerado automaticamente, além de
 * atributos como cargo, usuário, senha e lista de permissões definidas de acordo
 * com o cargo ocupado.</p>
 *
 * <p>As permissões controlam as ações que o funcionário pode realizar no sistema,
 * como cadastrar clientes, visualizar dados ou gerar relatórios.</p>
 */
public class Funcionario extends Pessoa {

    private String cargo;
    private static final AtomicInteger contador = new AtomicInteger(0);
    private String usuario;
    private int id;
    private String senha;
    private List<PermissoesEnum> permissoes;

    /**
     * Construtor padrão.
     *
     * <p>Inicializa a lista de permissões como vazia. É utilizado principalmente
     * por frameworks de serialização/desserialização.</p>
     */
    public Funcionario() {
        this.permissoes = new ArrayList<>();
    }

    /**
     * Construtor completo que cria um funcionário com seus dados pessoais, cargo,
     * credenciais e permissões associadas automaticamente.
     *
     * @param nome     nome completo do funcionário
     * @param cpf      CPF do funcionário
     * @param telefone telefone de contato
     * @param cargo    cargo ocupado (ex.: "barbeiro", "atendente", "administrador")
     * @param usuario  nome de usuário para login
     * @param senha    senha de acesso ao sistema
     */
    public Funcionario(String nome, String cpf, String telefone,
                       String cargo, String usuario, String senha) {

        super(nome, cpf, telefone);
        this.id = contador.incrementAndGet();
        this.cargo = cargo;
        this.permissoes = definirPermissoesPorCargo(cargo);
        this.usuario = usuario;
        this.senha = senha;
    }

    /**
     * Define automaticamente as permissões com base no cargo do funcionário.
     *
     * @param cargo cargo informado
     * @return lista de permissões correspondente ao cargo
     */
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
                lista.addAll(List.of(PermissoesEnum.values()));
                break;

            default:
                break;
        }

        return lista;
    }

    /**
     * Retorna o cargo do funcionário.
     *
     * @return cargo atual
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Define o cargo do funcionário.
     *
     * @param cargo novo cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Retorna o nome de usuário utilizado pelo funcionário para login.
     *
     * @return nome de usuário
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Define o nome de usuário para login.
     *
     * @param usuario novo nome de usuário
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna a senha do funcionário.
     *
     * @return senha atual
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do funcionário.
     *
     * @param senha nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna uma nova lista contendo as permissões do funcionário.
     *
     * <p>A lista retornada é uma cópia para evitar modificações externas.</p>
     *
     * @return lista de permissões
     */
    public List<PermissoesEnum> getPermissoes() {
        return new ArrayList<>(permissoes);
    }

    /**
     * Verifica se o funcionário possui determinada permissão.
     *
     * @param permissao permissão a verificar
     * @return {@code true} se o funcionário possui a permissão, {@code false} caso contrário
     */
    public boolean temPermissao(PermissoesEnum permissao) {
        return permissoes.contains(permissao);
    }

    /**
     * Retorna o identificador único do funcionário.
     *
     * @return ID do funcionário
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente o ID do funcionário.
     *
     * @param id novo ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atualiza o contador estático de IDs, garantindo continuidade de numeração
     * quando dados são carregados do armazenamento.
     *
     * @param ultimoId maior ID encontrado nos registros
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Retorna uma representação textual do funcionário, contendo informações básicas
     * e permissões atribuídas.
     *
     * @return string formatada com dados do funcionário
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
