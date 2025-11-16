package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gerencia o ciclo de vida (CRUD) dos objetos {@link Funcionario}.
 *
 * Esta classe coordena as operações entre uma lista de funcionários mantida
 * em memória e um mecanismo de persistência {@link RepositorioJson} (que
 * salva os dados em "funcionarios.json").
 *
 * É responsável por carregar os dados na inicialização, sincronizar o
 * contador de IDs estático da classe {@link Funcionario} e fornecer
 * métodos para adicionar, buscar, listar, atualizar e remover funcionários.
 *
 * Nota: A persistência não é automática. Métodos como `adicionarFuncionario`
 * modificam apenas a lista em memória. É necessário chamar
 * {@link #salvarTodosFuncionarios()} para persistir as alterações no arquivo.
 */
public class GerenciadorFuncionario {

    /** Lista em memória de funcionários, carregada do JSON. */
    private List<Funcionario> funcionarios = new ArrayList<>();

    /** Repositório para persistência em JSON ("funcionarios.json"). */
    private final RepositorioJson<Funcionario> repo =
            new RepositorioJson<>(Funcionario.class, "funcionarios.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama imediatamente o método {@link #carregar()}
     * para popular a lista de funcionários a partir do arquivo JSON.
     */
    public GerenciadorFuncionario() {
        this.carregar();
    }

    /**
     * Carrega (ou recarrega) a lista de funcionários do arquivo JSON
     * para a lista em memória.
     *
     * Após carregar, este método varre a lista para encontrar o ID mais alto
     * e atualiza o contador estático em {@link Funcionario#atualizarContador(int)}
     * para evitar IDs duplicados em novos cadastros.
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
     * Adiciona um novo funcionário à lista em memória.
     *
     * Esta operação é sincronizada e afeta apenas a lista em memória.
     * Para salvar o novo funcionário no arquivo JSON,
     * chame {@link #salvarTodosFuncionarios()} posteriormente.
     *
     * @param funcionario O objeto {@link Funcionario} a ser adicionado.
     */
    public synchronized void adicionarFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    /**
     * Retorna a lista de funcionários atualmente mantida em memória.
     *
     * @return Uma {@link List} de {@link Funcionario}.
     */
    public List<Funcionario> listarFuncionarios() {
        return funcionarios;
    }

    /**
     * Remove um funcionário com base no ID informado.
     *
     * Atenção: Esta implementação carrega uma nova lista do repositório
     * (usando {@code repo.listar()}), remove o item dessa lista local
     * e, em seguida, descarta a lista.
     *
     * Esta operação NÃO afeta a lista em memória ({@code this.funcionarios})
     * e NÃO persiste a remoção no arquivo JSON.
     *
     * @param id O identificador do funcionário a ser removido.
     */
    public void removerFuncionario(int id) {
        List<Funcionario> funcionarios = repo.listar();
        funcionarios.removeIf(f -> f.getId() == id);
    }

    /**
     * Busca e retorna um funcionário da lista em memória pelo seu ID.
     *
     * @param id O identificador do funcionário.
     * @return O objeto {@link Funcionario} encontrado, ou {@code null}
     * se nenhum funcionário com esse ID existir na lista em memória.
     */
    public Funcionario buscarFuncionario(int id) {
        Iterator<Funcionario> iterator = funcionarios.iterator();
        while (iterator.hasNext()) {
            Funcionario funcionario = iterator.next();
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }
        System.out.println("Funcionario nao encontrado!");
        return null;
    }

    /**
     * Atualiza os dados de um funcionário existente na lista em memória.
     *
     * A atualização é feita "in-place" no objeto encontrado na lista
     * (via {@link #buscarFuncionario(int)}).
     *
     * Apenas os parâmetros fornecidos como não nulos serão atualizados;
     * valores nulos indicam que o campo deve manter seu valor atual.
     *
     * Se o cargo ({@code novoCargo}) for alterado (mesmo que para o mesmo
     * valor), as permissões do funcionário são redefinidas
     * (via {@code definirPermissoesPorCargo}).
     *
     * Nota: Esta operação afeta apenas a lista em memória. Chame
     * {@link #salvarTodosFuncionarios()} para persistir as alterações.
     *
     * @param id ID do funcionário a ser atualizado.
     * @param novoNome Novo nome, ou {@code null} para manter o atual.
     * @param novoCpf Novo CPF, ou {@code null} para manter o atual.
     * @param novoTelefone Novo telefone, ou {@code null} para manter o atual.
     * @param novoCargo Novo cargo, ou {@code null} para manter o atual.
     * @param novoUsuario Novo nome de usuário, ou {@code null} para manter o atual.
     * @param novaSenha Nova senha, ou {@code null} para manter a atual.
     *
     * @return {@code true} se o funcionário foi encontrado e a atualização
     * aplicada (na memória), {@code false} se o funcionário não foi encontrado.
     */
    public boolean atualizarFuncionario(int id, String novoNome, String novoCpf,
                                        String novoTelefone, String novoCargo,
                                        String novoUsuario, String novaSenha) {

        List<Funcionario> funcionarios = repo.listar(); // Esta linha parece não ter efeito
        Funcionario funcionario = buscarFuncionario(id); // Busca na lista de memória

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
     * Retorna uma lista de funcionários lida diretamente do arquivo JSON.
     *
     * Este método ignora a lista mantida em memória ({@code this.funcionarios})
     * e realiza uma nova leitura do repositório (via {@code repo.buscarTodos()}).
     *
     * @return Uma nova lista de funcionários lida do arquivo.
     */
    public List<Funcionario> listar() {
        return repo.buscarTodos();
    }

    /**
     * Salva a lista de funcionários atualmente em memória
     * ({@code this.funcionarios}) no arquivo JSON,
     * sobrescrevendo o conteúdo anterior do arquivo.
     */
    public void salvarTodosFuncionarios(){
        repo.salvarTodos(funcionarios);
    }

    /**
     * Limpa o arquivo JSON de funcionários, salvando uma lista vazia nele.
     *
     * Atenção: Esta operação NÃO limpa a lista mantida em memória
     * ({@code this.funcionarios}).
     */
    public void limpar() {
        repo.salvarTodos(new ArrayList<>());
    }
}