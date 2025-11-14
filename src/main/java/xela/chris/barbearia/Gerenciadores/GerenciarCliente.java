package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Responsável por gerenciar os clientes da barbearia.
 *
 * Esta classe permite:
 * - Cadastrar novos clientes;
 * - Remover clientes;
 * - Atualizar dados de um cliente existente;
 * - Buscar por ID;
 * - Listar todos os clientes armazenados;
 * - Persistir os dados em arquivo JSON por meio do {@link RepositorioJson}.
 *
 * É usada para manter o controle dos clientes que realizam serviços
 * ou agendamentos na barbearia.
 */
public class GerenciarCliente {

    private List<Cliente> clientes = new ArrayList<>();
    private RepositorioJson<Cliente> repo = new RepositorioJson<>(Cliente.class, "clientes.json");

    /**
     * Construtor que automaticamente carrega os clientes gravados no JSON.
     */
    public GerenciarCliente() {
        this.carregar();
    }

    /**
     * Carrega todos os clientes armazenados no arquivo JSON.
     * Também sincroniza o contador de IDs da classe {@link Cliente}
     * garantindo que novos clientes recebam IDs corretos.
     */
    public void carregar() {
        clientes = repo.buscarTodos();
        if (!clientes.isEmpty()) {
            int maiorId = clientes.stream()
                    .mapToInt(Cliente::getId)
                    .max()
                    .orElse(0);
            Cliente.atualizarContador(maiorId);
        }
    }

    /**
     * Busca um cliente pelo seu ID.
     *
     * @param id Identificador único do cliente.
     * @return O cliente encontrado ou {@code null} caso não exista.
     */
    public Cliente buscarCliente(int id) {
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        System.out.println("Cliente não encontrado!");
        return null;
    }

    /**
     * Adiciona um novo cliente ao sistema e salva no arquivo JSON.
     *
     * @param cliente Cliente a ser adicionado.
     */
    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Remove um cliente da lista a partir do seu ID.
     *
     * @param id Identificador do cliente a ser removido.
     * @return true se o cliente foi removido; false caso contrário.
     */
    public boolean removerPorId(int id) {
        boolean removido = clientes.removeIf(c -> id == c.getId());
        if (removido) {
            System.out.println("Cliente removido!");
        }
        return removido;
    }

    /**
     * Atualiza os dados de um cliente existente.
     * Qualquer campo que receber {@code null} permanece inalterado.
     *
     * @param id           ID do cliente que será atualizado.
     * @param novoNome     Novo nome ou {@code null} para manter o atual.
     * @param novoCpf      Novo CPF ou {@code null} para manter o atual.
     * @param novoTelefone Novo telefone ou {@code null} para manter o atual.
     * @param status       Novo status de atendimento ou {@code null}.
     * @return true caso o cliente seja encontrado e atualizado; false caso contrário.
     */
    public boolean atualizarCliente(int id, String novoNome, String novoCpf, String novoTelefone, StatusAtendimentoCliente status) {
        List<Cliente> clientes = repo.listar();
        Cliente cliente = buscarCliente(id);

        if (cliente == null) {
            System.out.println("Cliente com ID {" + id + "} não foi encontrado!");
            return false;
        }

        if (novoNome != null) cliente.setNome(novoNome);
        if (novoCpf != null) cliente.setCpf(novoCpf);
        if (novoTelefone != null) cliente.setTelefone(novoTelefone);
        if (status != null) cliente.setStatusAtendimentoCliente(status);

        repo.salvarTodos(clientes);

        System.out.println("Sucesso em atualizar!");
        return true;
    }

    /**
     * Lista todos os clientes cadastrados, exibindo no console.
     *
     * @return Uma lista vazia (o método apenas exibe no console).
     */
    public List<Cliente> listar() {
        System.out.println("➡ Clientes carregados: " + clientes.size());
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        return List.of();
    }


    public void salvarTodosClientes(){
        repo.salvarTodos(clientes);
    }

    /**
     * Remove todos os clientes tanto da memória quanto do arquivo JSON.
     */
    public void limpar() {
        clientes = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
