package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelo gerenciamento de funcionários da barbearia.
 * <p>
 * Permite realizar operações básicas como carregar, adicionar, remover,
 * listar e limpar funcionários, utilizando um repositório baseado em JSON
 * para persistência de dados.
 * </p>
 */
public class GerenciadorFuncionario {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private final RepositorioJson<Funcionario> repo = new RepositorioJson<>(Funcionario.class, "funcionarios.json");

    /**
     * Carrega os funcionários do arquivo JSON para a lista em memória.
     * <p>
     * Caso o arquivo não exista ou esteja vazio, a lista permanecerá vazia.
     * </p>
     */
    public void carregar() {
        funcionarios = repo.buscarTodos();
    }

    /**
     * Adiciona um novo funcionário à lista e salva as alterações no arquivo JSON.
     *
     * @param funcionario o funcionário a ser adicionado
     */
    public void adicionar(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        repo.salvarTodos(funcionarios);
    }

    /**
     * Remove um funcionário da lista com base no CPF informado.
     * <p>
     * Caso o funcionário seja removido, o arquivo JSON é atualizado automaticamente.
     * </p>
     *
     * @param cpf o CPF do funcionário a ser removido
     * @return {@code true} se o funcionário foi removido com sucesso, {@code false} caso contrário
     */
    public boolean removerPorCpf(String cpf) {
        boolean removido = this.funcionarios.removeIf(f -> cpf.equals(f.getCpf()));
        if (removido) {
            repo.salvarTodos(funcionarios);
        }
        return removido;
    }

    /**
     * Retorna a lista de todos os funcionários atualmente carregados na memória.
     *
     * @return uma lista de objetos {@link Funcionario}
     */
    public List<Funcionario> listar() {
        return funcionarios;
    }

    /**
     * Remove todos os funcionários da lista e limpa o conteúdo do arquivo JSON.
     */
    public void limpar() {
        funcionarios = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
