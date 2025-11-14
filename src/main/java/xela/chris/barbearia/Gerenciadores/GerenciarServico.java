package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Servico;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gerencia os serviços da barbearia, permitindo adicionar, remover,
 * atualizar, listar e carregar serviços, persistindo os dados em um arquivo JSON.
 */
public class GerenciarServico {

    private List<Servico> servicos = new ArrayList<>();
    private final RepositorioJson<Servico> repo = new RepositorioJson<>(Servico.class, "servicos.json");

    public GerenciarServico() {
        this.carregar();
    }

    /**
     * Carrega todos os serviços do arquivo JSON para a lista interna.
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
     * Adiciona um novo serviço à lista e salva no arquivo JSON.
     *
     * @param servico Serviço a ser adicionado.
     */
    public void adicionar(Servico servico) {
        servicos.add(servico);
    }

    /**
     * Remove um serviço pelo ID e atualiza o arquivo JSON.
     *
     * @param id Identificador do serviço a ser removido.
     * @return true se foi removido com sucesso, false caso contrário.
     */
    public boolean removerPorId(int id) {
        boolean removido = servicos.removeIf(s -> s.getId() == id);
        if (removido) {
            System.out.println("Removido com sucesso!");
        }
        return removido;
    }

    /**
     * Busca um serviço pelo ID.
     *
     * @param id Identificador do serviço.
     * @return O serviço encontrado ou null se não existir.
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
     * Atualiza as informações de um serviço existente.
     *
     * @param id ID do serviço que será atualizado.
     * @param novoNome Novo nome do serviço.
     * @param novoPreco Novo preço.
     * @param novaDescricao Nova descrição.
     * @return true se o serviço foi atualizado, false se não encontrado.
     */
    public boolean atualizar(int id, String novoNome, double novoPreco, String novaDescricao) {
        Servico s = buscarPorId(id);
        if (s != null) {
            s.setNome(novoNome);
            s.setPreco(novoPreco);
            s.setDescricao(novaDescricao);
            repo.salvarTodos(servicos);
            return true;
        }
        return false;
    }

    /**
     * Retorna todos os serviços atualmente carregados.
     *
     * @return Lista de serviços.
     */
    public List<Servico> listar() {
        System.out.println("➡ Serviços carregados: " + servicos.size());
        for (Servico s : servicos) {
            System.out.println(s);
        }
        return List.of();
    }

    public void salvarTodosProdutos(){
        repo.salvarTodos(servicos);
    }

    /**
     * Limpa todos os serviços da lista e do arquivo JSON.
     */
    public void limpar() {
        servicos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
