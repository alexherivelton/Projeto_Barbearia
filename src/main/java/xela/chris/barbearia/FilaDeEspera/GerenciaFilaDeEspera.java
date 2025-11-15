package xela.chris.barbearia.FilaDeEspera;

import xela.chris.barbearia.Gerenciadores.RepositorioJson;
import xela.chris.barbearia.models.Cliente;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia a fila de espera da barbearia, implementando a persistência
 * dos dados em JSON e a lógica básica de manipulação da fila (FIFO).
 *
 * Esta classe é responsável por:
 *
 * - Carregar a fila de espera salva em "filaDeEspera.json" ao ser instanciada.
 * - Salvar o estado atual da fila de espera no arquivo JSON.
 * - Adicionar novos clientes (entradas {@link FilaDeEspera}) à fila.
 * - Remover clientes da fila seguindo a lógica FIFO (First-In, First-Out).
 */
public class GerenciaFilaDeEspera {
    private RepositorioJson<FilaDeEspera> repo = new RepositorioJson<>(FilaDeEspera.class, "filaDeEspera.json");
    private List<FilaDeEspera> listaDeEspera = new ArrayList<>();

    /**
     * Construtor do Gerenciador da Fila de Espera.
     * Inicializa a lista de espera interna e chama o método {@link #carregar()}
     * para popular a lista com dados persistidos do arquivo JSON.
     */
    public GerenciaFilaDeEspera(){
        this.listaDeEspera = new ArrayList<FilaDeEspera>();
        this.carregar();
    }

    /**
     * Carrega a lista de espera a partir do arquivo "filaDeEspera.json".
     * Utiliza o {@link RepositorioJson} para buscar todos os registros
     * e substitui a lista em memória pela lista carregada.
     */
    public void carregar() {
        listaDeEspera = repo.buscarTodos();
    }

    /**
     * Salva o estado atual da {@code listaDeEspera} (em memória) no arquivo
     * "filaDeEspera.json", utilizando o {@link RepositorioJson}.
     */
    public void salvarFilaDeEspera(){
        repo.salvarTodos(listaDeEspera);
    }

    /**
     * Adiciona uma nova entrada {@link FilaDeEspera} ao final da fila.
     *
     * @param fila O objeto {@link FilaDeEspera} (representando o cliente
     * e seu serviço) a ser adicionado.
     */
    public void adicionarClienteNaFila(FilaDeEspera fila){
        listaDeEspera.add(fila);
    }


    /**
     * Remove e retorna o primeiro item da fila de espera (FIFO - First-In, First-Out).
     *
     * @return O objeto {@link FilaDeEspera} que estava no início da fila.
     * Retorna {@code null} se a fila estiver vazia.
     */
    public FilaDeEspera buscarComFIFONaLista(){
        if (listaDeEspera.isEmpty()) {
            System.out.println("A fila está vazia!");
            return null;
        }

        return listaDeEspera.remove(0);
    }

    /**
     * Retorna a lista completa de espera atual.
     *
     * @return Uma {@link List} contendo todas as entradas {@link FilaDeEspera}.
     */
    public List<FilaDeEspera> getFilaDeEspera() {
        return listaDeEspera;
    }

}