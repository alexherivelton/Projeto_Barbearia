package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por gerenciar todas as operações relacionadas aos funcionários do sistema.
 * <p>
 * Este gerenciador realiza operações de CRUD sobre objetos {@link Funcionario}, utilizando
 * um repositório baseado em JSON como mecanismo de persistência. Ele também mantém uma lista
 * em memória para facilitar buscas e manipulação dos dados.
 * </p>
 *
 * <p>Principais funcionalidades:</p>
 * <ul>
 *     <li>Carregamento de funcionários salvos no arquivo JSON</li>
 *     <li>Cadastro de novos funcionários</li>
 *     <li>Listagem de funcionários</li>
 *     <li>Atualização de informações de um funcionário</li>
 *     <li>Remoção de funcionários pelo ID</li>
 *     <li>Busca de funcionário específico</li>
 * </ul>
 */
public class GerenciadorFuncionario {

    /** Lista de funcionários carregada em memória. */
    private List<Funcionario> funcionarios = new ArrayList<>();

    /** Repositório JSON responsável pela persistência dos funcionários. */
    private final RepositorioJson<Funcionario> repo =
            new RepositorioJson<>(Funcionario.class, "funcionarios.json");

    /**
     * Construtor padrão que inicializa o gerenciador carregando os funcionários do repositório.
     */
    public GerenciadorFuncionario() {
        this.carregar();
    }

    /**
     * Carrega todos os funcionários do repositório JSON para a memória.
     * <p>
     * Após o carregamento, o método identifica o maior ID existente e atualiza o
     * contador estático da classe {@link Funcionario} para garantir que novos
     * funcionários recebam IDs sequenciais adequados.
     * </p>
     */
    public void carregar() {
        funcionarios = repo.buscarTodos();
        if (!funcionarios.isEmpty()) {
            int maiorId = funcionarios.stream()
                    .mapToInt(Funcionario::getId)
                    .max()
                    .orElse(0);
            Funcionario.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona um novo funcionário ao sistema.
     * <p>
     * O método carrega a lista atual do repositório, adiciona o novo funcionário
     * e salva novamente todos os registros.
     * </p>
     *
     * @param funcionario funcionário que será cadastrado
     */
    public synchronized void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    /**
     * Retorna a lista de funcionários mantida em memória.
     *
     * @return lista de funcionários carregada
     */
    public List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }

    /**
     * Remove um funcionário com base no ID informado.
     * <p>
     * Se o ID não existir, nenhuma remoção é realizada.
     * </p>
     *
     * @param id identificador do funcionário a ser removido
     */
    public void removerFuncionario(int id) {
        List<Funcionario> funcionarios = repo.listar();
        funcionarios.removeIf(f -> f.getId() == id);
    }

    /**
     * Busca e retorna um funcionário pelo seu ID.
     *
     * @param id identificador do funcionário
     * @return o funcionário encontrado, ou {@code null} caso não exista
     */
    public Funcionario buscarFuncionario(int id) {
        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario funcionario = iterator.next();
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }
        System.out.println("Diabo nao encontrado!");
        return null;
    }

    /**
     * Atualiza os dados de um funcionário existente.
     * <p>
     * Apenas os parâmetros não nulos serão atualizados; valores nulos indicam que o
     * campo deve permanecer o mesmo. Caso o cargo seja alterado, as permissões são
     * redefinidas automaticamente com base no novo cargo.
     * </p>
     *
     * @param id ID do funcionário a ser atualizado
     * @param novoNome novo nome, ou {@code null} para manter o atual
     * @param novoCpf novo CPF, ou {@code null} para manter o atual
     * @param novoTelefone novo telefone, ou {@code null} para manter o atual
     * @param novoCargo novo cargo, ou {@code null} para manter o atual
     * @param novoUsuario novo nome de usuário, ou {@code null} para manter o atual
     * @param novaSenha nova senha, ou {@code null} para manter a atual
     *
     * @return {@code true} se a atualização for bem-sucedida, {@code false} se o funcionário não existir
     */
    public boolean atualizarFuncionario(int id, String novoNome, String novoCpf,
                                        String novoTelefone, String novoCargo,
                                        String novoUsuario, String novaSenha) {

        List<Funcionario> funcionarios = repo.listar();
        Funcionario funcionario = buscarFuncionario(id);

        if (funcionario == null) {
            System.out.println("Funcionario com o id{" + id + "} nao foi encontrado!");
            return false;
        }

        String nomeAtual = funcionario.getNome();
        String cpfAtual = funcionario.getCpf();
        String telefoneAtual = funcionario.getTelefone();
        String cargoAtual = funcionario.getCargo();
        String usuarioAtual = funcionario.getUsuario();
        String senhaAtual = funcionario.getSenha();

        funcionario.setNome(novoNome != null ? novoNome : nomeAtual);
        funcionario.setCpf(novoCpf != null ? novoCpf : cpfAtual);
        funcionario.setTelefone(novoTelefone != null ? novoTelefone : telefoneAtual);

        if (novoCargo != null) {
            funcionario.setCargo(novoCargo);
            funcionario.definirPermissoesPorCargo(novoCargo);
        } else {
            funcionario.setCargo(cargoAtual);
            funcionario.definirPermissoesPorCargo(cargoAtual);
        }

        funcionario.setUsuario(novoUsuario != null ? novoUsuario : usuarioAtual);
        funcionario.setSenha(novaSenha != null ? novaSenha : senhaAtual);


        System.out.println("Sucesso em atualizar!");
        return true;
    }

    /**
     * Lista todos os funcionários diretamente do arquivo JSON,
     * ignorando a lista mantida em memória.
     *
     * @return lista atualizada de funcionários
     */
    public List<Funcionario> listar() {
        return repo.buscarTodos();
    }

    public void salvarTodosFuncionarios(){
        repo.salvarTodos(funcionarios);
    }

    /**
     * Remove todos os funcionários do repositório, sobrescrevendo o arquivo
     * com uma lista vazia.
     */
    public void limpar() {
        repo.salvarTodos(new ArrayList<>());
    }
}
