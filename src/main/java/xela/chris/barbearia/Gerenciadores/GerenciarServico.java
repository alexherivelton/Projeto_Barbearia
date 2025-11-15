package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Servico;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gerencia o ciclo de vida (CRUD) dos serviços oferecidos pela barbearia.
 *
 * Esta classe centraliza as operações de cadastro, atualização, busca e
 * remoção de serviços, mantendo uma lista em memória para acesso rápido
 * e coordenando a persistência com um {@link RepositorioJson} (no arquivo
 * "servicos.json").
 *
 * A persistência (salvamento) não é totalmente automática. Métodos como
 * {@code adicionar} e {@code removerPorId} modificam apenas a lista em memória,
 * exigindo uma chamada posterior a {@link #salvarTodosServicos()}.
 * A exceção é o método {@link #atualizar(int, String, double, String)},
 * que persiste suas alterações imediatamente.
 */
public class GerenciarServico {

    /** Lista de serviços mantida em memória, carregada do JSON. */
    private List<Servico> servicos = new ArrayList<>();
    /** Repositório para persistência em JSON ("servicos.json"). */
    private final RepositorioJson<Servico> repo = new RepositorioJson<>(Servico.class, "servicos.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama {@link #carregar()} para popular
     * a lista de serviços a partir do arquivo JSON.
     */
    public GerenciarServico() {
        this.carregar();
    }

    /**
     * Carrega (ou recarrega) todos os serviços do arquivo JSON para a lista
     * em memória ({@code this.servicos}).
     *
     * Se a lista carregada não estiver vazia, este método encontra o
     * ID mais alto e atualiza o contador estático na classe {@link Servico}
     * (via {@link Servico#atualizarContador(int)}) para evitar IDs
     * duplicados em novos cadastros.
     */
    public void carregar() {
        servicos = repo.buscarTodos();
        if (!servicos.isEmpty()) {
            int maiorId = servicos.stream()
                    .mapToInt(Servico::getId)
                    .max()
                    .orElse(0);
            Servico.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona um novo serviço à lista em memória.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodosServicos()} para gravar a alteração.
     *
     * @param servico O objeto {@link Servico} a ser adicionado.
     */
    public void adicionar(Servico servico) {
        servicos.add(servico);
    }

    /**
     * Remove um serviço da lista em memória pelo ID.
     *
     * Atenção: Esta operação *não* persiste os dados automaticamente.
     * Chame {@link #salvarTodosServicos()} para gravar a alteração.
     *
     * @param id O identificador do serviço a ser removido.
     * @return {@code true} se o serviço foi encontrado e removido da
     * lista em memória, {@code false} caso contrário.
     */
    public boolean removerPorId(int id) {
        boolean removido = servicos.removeIf(s -> s.getId() == id);
        if (removido) {
            System.out.println("Removido com sucesso!");
        }
        return removido;
    }

    /**
     * Busca um serviço na lista em memória pelo seu ID.
     *
     * @param id O identificador do serviço.
     * @return O objeto {@link Servico} encontrado, ou {@code null} se não
     * existir na lista em memória.
     */
    public Servico buscarPorId(int id) {
        Iterator<Servico> iterator = servicos.iterator();
        while(iterator.hasNext()){
            Servico servico = iterator.next();
            if(servico.getId() == id){
                return servico;
            }
        }
        System.out.println("Diabo nao encontrado!");
        return null;
    }

    /**
     * Atualiza as informações de um serviço existente na lista em memória
     * e persiste imediatamente a alteração no arquivo JSON.
     *
     * A atualização é feita "in-place" no objeto encontrado (via
     * {@link #buscarPorId(int)}).
     *
     * @param id O ID do serviço que será atualizado.
     * @param novoNome O novo nome para o serviço.
     * @param novoPreco O novo preço para o serviço.
     * @param novaDescricao A nova descrição para o serviço.
     * @return {@code true} se o serviço foi encontrado, atualizado e
     * salvo com sucesso, {@code false} se o serviço não foi encontrado.
     */
    public boolean atualizar(int id, String novoNome, double novoPreco, String novaDescricao) {
        Servico s = buscarPorId(id);
        if (s != null) {
            s.setNome(novoNome);
            s.setPreco(novoPreco);
            s.setDescricao(novaDescricao);
            repo.salvarTodos(servicos); // Persistência imediata
            return true;
        }
        return false;
    }

    /**
     * Imprime todos os serviços da lista em memória no console.
     *
     * Nota: Este método retorna uma lista vazia imutável (via {@code List.of()}),
     * independentemente dos serviços em memória. Sua principal função
     * é a exibição no console.
     *
     * @return Uma {@link List} vazia.
     */
    public List<Servico> listar() {
        System.out.println("➡ Serviços carregados: " + servicos.size());
        for (Servico s : servicos) {
            System.out.println(s);
        }
        return List.of();
    }

    /**
     * Salva a lista de serviços atualmente em memória ({@code this.servicos})
     * no arquivo JSON, sobrescrevendo o conteúdo anterior do arquivo.
     */
    public void salvarTodosServicos(){
        repo.salvarTodos(servicos);
    }

    /**
     * Remove todos os serviços do sistema (memória e persistência).
     *
     * Este método limpa a lista em memória ({@code this.servicos}) e,
     * em seguida, salva uma lista vazia no arquivo JSON
     * (via {@code repo.salvarTodos}), efetivamente limpando todos os
     * dados persistidos.
     */
    public void limpar() {
        servicos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}