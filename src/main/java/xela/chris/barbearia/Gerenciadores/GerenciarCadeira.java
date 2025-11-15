package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.enums.TipoCadeira;
import xela.chris.barbearia.models.Cadeira;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsável por gerenciar as cadeiras físicas disponíveis na barbearia.
 *
 * Esta classe controla o inventário de cadeiras, que representam as
 * posições de atendimento (ex: cadeiras de lavagem, cadeiras de corte).
 *
 * Ela lida com:
 * - Carga e persistência das cadeiras no arquivo "cadeiras.json"
 * (via {@link RepositorioJson}).
 * - Criação automática das cadeiras padrão caso o arquivo esteja vazio
 * na inicialização.
 * - Sincronização do contador de IDs estático da classe {@link Cadeira}.
 * - Métodos de busca para encontrar cadeiras por ID ou por tipo.
 * - Listagem geral de todas as cadeiras.
 */
public class GerenciarCadeira {

    private List<Cadeira> cadeiras = new ArrayList<>();
    private RepositorioJson<Cadeira> repo = new RepositorioJson<>(Cadeira.class, "cadeiras.json");

    /**
     * Construtor padrão.
     * Chama {@link #carregar()} para ler os dados do arquivo "cadeiras.json".
     *
     * Se a lista em memória permanecer vazia após o carregamento (indicando
     * um primeiro uso ou arquivo vazio), o método {@link #inicializarCadeiras()}
     * é chamado para criar as cadeiras padrão, e {@link #salvar()} é
     * chamado para persistir essas cadeiras padrão.
     */
    public GerenciarCadeira() {
        carregar();
        if (cadeiras.isEmpty()) {
            inicializarCadeiras();
            salvar();
        }
    }

    /**
     * Inicializa a lista de cadeiras com um conjunto padrão.
     * Este método é privado e chamado apenas pelo construtor se
     * nenhuma cadeira for carregada.
     *
     * Padrão usado:
     * - 1 Cadeira de Lavagem.
     * - 2 Cadeiras de Serviço Corriqueiro (corte, barba, etc.).
     */
    private void inicializarCadeiras() {
        cadeiras.add(new Cadeira("Cadeira Lavagem 1", TipoCadeira.LAVAR_SECAR));
        cadeiras.add(new Cadeira("Cadeira Serviço 1", TipoCadeira.SERVICO_CORRIQUEIRO));
        cadeiras.add(new Cadeira("Cadeira Serviço 2", TipoCadeira.SERVICO_CORRIQUEIRO));
    }

    /**
     * Carrega (ou recarrega) a lista de cadeiras do arquivo JSON
     * para a lista em memória ({@code this.cadeiras}).
     *
     * Após carregar, este método varre a lista para encontrar o ID mais alto
     * e atualiza o contador estático na classe {@link Cadeira}
     * (via {@link Cadeira#atualizarContador(int)}) para evitar IDs
     * duplicados em novos cadastros.
     */
    public void carregar() {
        cadeiras = repo.buscarTodos();
        if (!cadeiras.isEmpty()) {
            int maiorId = cadeiras.stream()
                    .mapToInt(Cadeira::getId)
                    .max()
                    .orElse(0);
            Cadeira.atualizarContador(maiorId);
        }
    }

    /**
     * Salva a lista de cadeiras atualmente em memória ({@code this.cadeiras})
     * no arquivo JSON, sobrescrevendo o conteúdo anterior do arquivo.
     */
    public void salvar() {
        repo.salvarTodos(cadeiras);
    }

    /**
     * Retorna a lista de cadeiras atualmente mantida em memória.
     *
     * @return Uma {@link List} de {@link Cadeira}.
     */
    public List<Cadeira> listarCadeiras() {
        return cadeiras;
    }

    /**
     * Busca uma cadeira na lista em memória pelo seu ID.
     *
     * @param id O identificador numérico da cadeira.
     * @return A instância de {@link Cadeira} encontrada, ou {@code null}
     * se nenhuma cadeira com esse ID existir na lista.
     */
    public Cadeira buscarPorId(int id) {
        return cadeiras.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna uma nova lista contendo apenas as cadeiras que
     * correspondem ao tipo especificado.
     *
     * @param tipo O {@link TipoCadeira} desejado (ex: LAVAR_SECAR ou
     * SERVICO_CORRIQUEIRO).
     * @return Uma {@link List} de {@link Cadeira} filtrada pelo tipo.
     * Pode retornar uma lista vazia se nenhum resultado for encontrado.
     */
    public List<Cadeira> buscarPorTipo(TipoCadeira tipo) {
        return cadeiras.stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}