package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;

import xela.chris.barbearia.Comparators.ClienteCpfComparators;
import xela.chris.barbearia.Comparators.ClienteNomeComparators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Responsável por gerenciar o ciclo de vida (CRUD) dos clientes da barbearia.
 *
 * Esta classe centraliza as operações de cadastro, atualização, busca e
 * remoção de clientes, mantendo uma lista em memória para acesso rápido
 * e coordenando a persistência com um {@link RepositorioJson} (no arquivo
 * "clientes.json").
 *
 * Funções principais:
 * - Carregar clientes do JSON na inicialização.
 * - Sincronizar o contador de ID estático da classe {@link Cliente}.
 * - Adicionar, atualizar e remover clientes (na lista em memória).
 * - Salvar a lista em memória para o arquivo JSON (operação manual).
 * - Métodos de busca (por ID, e buscas binárias especializadas).
 *
 * Atenção: A maioria das operações (adicionar, remover, atualizar)
 * modifica apenas a lista em memória. É necessário chamar
 * {@link #salvarTodosClientes()} para persistir as alterações no arquivo.
 * A exceção é o método {@link #limpar()}, que persiste imediatamente.
 */
public class GerenciarCliente {

    private List<Cliente> clientes = new ArrayList<>();
    private RepositorioJson<Cliente> repo = new RepositorioJson<>(Cliente.class, "clientes.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama {@link #carregar()} para popular
     * a lista de clientes a partir do arquivo JSON.
     */
    public GerenciarCliente() {
        this.carregar();
    }

    /**
     * Carrega (ou recarrega) todos os clientes do arquivo JSON para a lista
     * em memória ({@code this.clientes}).
     *
     * Se a lista carregada não estiver vazia, este método encontra o
     * ID mais alto e atualiza o contador estático na classe {@link Cliente}
     * (via {@link Cliente#atualizarContador(int)}) para evitar IDs
     * duplicados em novos cadastros.
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
     * Busca um cliente na lista em memória pelo seu ID.
     *
     * @param id O identificador único do cliente.
     * @return O objeto {@link Cliente} encontrado, ou {@code null} se não
     * existir na lista em memória.
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
     * Adiciona um novo cliente à lista em memória.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodosClientes()} para gravar a alteração.
     *
     * @param cliente O objeto {@link Cliente} a ser adicionado.
     */
    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Remove um cliente da lista em memória a partir do seu ID.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodosClientes()} para gravar a alteração.
     *
     * @param id O identificador do cliente a ser removido.
     * @return {@code true} se o cliente foi encontrado e removido da
     * lista em memória, {@code false} caso contrário.
     */
    public boolean removerPorId(int id) {
        boolean removido = clientes.removeIf(c -> id == c.getId());
        if (removido) {
            System.out.println("Cliente removido!");
        }
        return removido;
    }

    /**
     * Atualiza os dados de um cliente existente na lista em memória.
     *
     * A atualização é feita "in-place" no objeto encontrado (via
     * {@link #buscarCliente(int)}). Apenas os campos fornecidos como
     * não nulos serão atualizados.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodosClientes()} para gravar a alteração.
     *
     * @param id           ID do cliente que será atualizado.
     * @param novoNome     Novo nome, ou {@code null} para manter o atual.
     * @param novoCpf      Novo CPF, ou {@code null} para manter o atual.
     * @param novoTelefone Novo telefone, ou {@code null} para manter o atual.
     * @param status       Novo {@link StatusAtendimentoCliente}, ou {@code null}.
     * @return {@code true} caso o cliente seja encontrado e atualizado
     * (na memória), {@code false} se o cliente não foi encontrado.
     */
    public boolean atualizarCliente(int id, String novoNome, String novoCpf, String novoTelefone, StatusAtendimentoCliente status) {
        Cliente cliente = buscarCliente(id);

        if (cliente == null) {
            System.out.println("Cliente com ID {" + id + "} não foi encontrado!");
            return false;
        }

        if (novoNome != null) cliente.setNome(novoNome);
        if (novoCpf != null) cliente.setCpf(novoCpf);
        if (novoTelefone != null) cliente.setTelefone(novoTelefone);
        if (status != null) cliente.setStatusAtendimentoCliente(status);

        System.out.println("Sucesso em atualizar!");
        return true;
    }

    /**
     * Imprime todos os clientes da lista em memória no console.
     *
     * Nota: Este método retorna uma lista vazia imutável (via {@code List.of()}),
     * independentemente dos clientes em memória. Sua principal função
     * é a exibição no console.
     *
     * @return Uma {@link List} vazia.
     */
    public List<Cliente> listar() {
        System.out.println("➡ Clientes carregados: " + clientes.size());
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        return List.of();
    }


    /**
     * Salva a lista de clientes atualmente em memória ({@code this.clientes})
     * no arquivo JSON, sobrescrevendo o conteúdo anterior do arquivo.
     */
    public void salvarTodosClientes(){
        repo.salvarTodos(clientes);
    }

    /**
     * Remove todos os clientes do sistema (memória e persistência).
     *
     * Este método limpa a lista em memória ({@code this.clientes}) e,
     * em seguida, salva uma lista vazia no arquivo JSON
     * (via {@code repo.salvarTodos}), efetivamente limpando todos os
     * dados persistidos.
     */
    public void limpar() {
        clientes = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }

    /**
     * (Questão 17) Busca um cliente usando iteração linear após ordenar uma
     * cópia da lista.
     *
     * Este método cria uma cópia da lista de clientes, ordena-a usando o
     * {@link Comparator} fornecido, e então itera sobre ela, usando
     * {@code comparator.compare()} para encontrar uma correspondência exata (retorno 0).
     *
     * @param clienteProcurado O objeto {@link Cliente} a ser encontrado
     * (usado para a comparação).
     * @param comparator O {@link Comparator} usado para ordenar e comparar.
     * @return O objeto {@link Cliente} encontrado, ou {@code null} se não
     * for encontrado.
     */
    public Cliente findCliente(Cliente clienteProcurado, Comparator<Cliente> comparator) {
        // Primeiro, ordena a lista usando o comparator
        List<Cliente> listaOrdenada = new ArrayList<>(clientes);
        listaOrdenada.sort(comparator);

        // Usa iterator para percorrer a lista ordenada
        Iterator<Cliente> iterator = listaOrdenada.iterator();
        while (iterator.hasNext()) {
            Cliente cliente = iterator.next();
            // Compara usando o comparator (retorna 0 se forem iguais)
            if (comparator.compare(cliente, clienteProcurado) == 0) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * (Questão 17) Busca um cliente usando {@link Collections#binarySearch}
     * com o comparador de Nomes ({@link ClienteNomeComparators}).
     *
     * Este método cria uma cópia da lista de clientes, ordena-a
     * especificamente por nome, e então realiza uma busca binária.
     *
     * @param clienteProcurado O objeto {@link Cliente} a ser encontrado
     * (deve ter o nome preenchido para a comparação).
     * @return O objeto {@link Cliente} encontrado, ou {@code null} se o
     * índice da busca for negativo.
     */
    public Cliente buscarClientePorNomeComBinarySearch(Cliente clienteProcurado) {
        List<Cliente> listaOrdenada = new ArrayList<>(clientes);
        Collections.sort(listaOrdenada, new ClienteNomeComparators());

        int indice = Collections.binarySearch(listaOrdenada, clienteProcurado, new ClienteNomeComparators());
        if (indice >= 0) {
            return listaOrdenada.get(indice);
        }
        return null;
    }

    /**
     * (Questão 17) Busca um cliente usando {@link Collections#binarySearch}
     * com um {@link Comparator} genérico.
     *
     * Este método cria uma cópia da lista de clientes, ordena-a
     * usando o {@code comparator} fornecido, e então realiza uma
     * busca binária.
     *
     * @param clienteProcurado O objeto {@link Cliente} a ser encontrado.
     * @param comparator O {@link Comparator} usado para ordenar e
     * realizar a busca.
     * @return O objeto {@link Cliente} encontrado, ou {@code null} se o
     * índice da busca for negativo.
     */
    public Cliente buscarClienteComBinarySearch(Cliente clienteProcurado, Comparator<Cliente> comparator) {
        List<Cliente> listaOrdenada = new ArrayList<>(clientes);
        Collections.sort(listaOrdenada, comparator);

        int indice = Collections.binarySearch(listaOrdenada, clienteProcurado, comparator);
        if (indice >= 0) {
            return listaOrdenada.get(indice);
        }
        return null;
    }

    /**
     * (Questão 17) Retorna uma nova lista contendo os clientes, ordenada
     * pelo {@link Comparator} fornecido.
     *
     * @param comparator O {@link Comparator} usado para a ordenação.
     * @return Uma nova {@link List} de {@link Cliente} ordenada.
     */
    public List<Cliente> getClientesOrdenados(Comparator<Cliente> comparator) {
        List<Cliente> listaOrdenada = new ArrayList<>(clientes);
        Collections.sort(listaOrdenada, comparator);
        return listaOrdenada;
    }

    /**
     * Retorna uma cópia da lista de clientes atualmente em memória.
     *
     * @return Uma nova {@link ArrayList} contendo todos os clientes.
     */
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }
}