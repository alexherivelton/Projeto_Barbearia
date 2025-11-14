package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.enums.TipoCadeira;
import xela.chris.barbearia.models.Cadeira;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsável por gerenciar as cadeiras da barbearia.
 *
 * Esta classe lida com:
 * - Carga e persistência das cadeiras em JSON;
 * - Criação automática das cadeiras padrão caso o arquivo esteja vazio;
 * - Busca por ID ou por tipo de cadeira;
 * - Listagem geral.
 *
 * As cadeiras representam as posições de atendimento da barbearia,
 * como cadeiras de lavagem ou cadeiras de serviços corriqueiros.
 */
public class GerenciarCadeira {

    private List<Cadeira> cadeiras = new ArrayList<>();
    private RepositorioJson<Cadeira> repo = new RepositorioJson<>(Cadeira.class, "cadeiras.json");

    /**
     * Construtor que já carrega as cadeiras do JSON.
     * Caso o arquivo esteja vazio, cria automaticamente
     * as cadeiras padrão da barbearia e salva no arquivo.
     */
    public GerenciarCadeira() {
        carregar();
        if (cadeiras.isEmpty()) {
            inicializarCadeiras();
            salvar();
        }
    }

    /**
     * Inicializa as cadeiras padrão da barbearia.
     *
     * Padrão usado:
     * - 1 cadeira para lavar e secar;
     * - 2 cadeiras para serviços comuns de barbearia.
     */
    private void inicializarCadeiras() {
        cadeiras.add(new Cadeira("Cadeira Lavagem 1", TipoCadeira.LAVAR_SECAR));
        cadeiras.add(new Cadeira("Cadeira Serviço 1", TipoCadeira.SERVICO_CORRIQUEIRO));
        cadeiras.add(new Cadeira("Cadeira Serviço 2", TipoCadeira.SERVICO_CORRIQUEIRO));
    }

    /**
     * Carrega as cadeiras salvas no arquivo JSON.
     * Também sincroniza o contador de IDs da classe {@link Cadeira}
     * garantindo que novos objetos recebam IDs corretos.
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
     * Salva todas as cadeiras atuais no arquivo JSON.
     */
    public void salvar() {
        repo.salvarTodos(cadeiras);
    }

    /**
     * Retorna uma lista com todas as cadeiras cadastradas.
     *
     * @return Lista de cadeiras existentes.
     */
    public List<Cadeira> listarCadeiras() {
        return cadeiras;
    }

    /**
     * Busca uma cadeira pelo seu ID.
     *
     * @param id Identificador da cadeira.
     * @return A cadeira encontrada ou {@code null} caso não exista.
     */
    public Cadeira buscarPorId(int id) {
        return cadeiras.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Retorna todas as cadeiras de um determinado tipo,
     * como cadeiras de lavagem ou cadeiras de serviço.
     *
     * @param tipo Tipo de cadeira (definido no enum {@link TipoCadeira}).
     * @return Lista de cadeiras que correspondem ao tipo informado.
     */
    public List<Cadeira> buscarPorTipo(TipoCadeira tipo) {
        return cadeiras.stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
