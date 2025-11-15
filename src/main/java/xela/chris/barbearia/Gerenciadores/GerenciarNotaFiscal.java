package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.NotaFiscal;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.models.Venda;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia o ciclo de vida e a persistência das Notas Fiscais ({@link NotaFiscal}).
 *
 * Esta classe é responsável por carregar, salvar, adicionar, gerar e
 * listar todas as notas fiscais emitidas pelo sistema, utilizando
 * um {@link RepositorioJson} para armazenamento no arquivo "notasFiscais.json".
 *
 * A maioria das operações de modificação (como adicionar, gerar nota e limpar)
 * persiste as alterações imediatamente no arquivo JSON.
 */
public class GerenciarNotaFiscal {

    /** Lista de notas fiscais mantida em memória, carregada do JSON. */
    private List<NotaFiscal> notas = new ArrayList<>();
    /** Repositório para persistência em JSON ("notasFiscais.json"). */
    private final RepositorioJson<NotaFiscal> repo = new RepositorioJson<>(NotaFiscal.class, "notasFiscais.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama imediatamente {@link #carregar()}
     * para popular a lista de notas a partir do arquivo JSON.
     */
    public GerenciarNotaFiscal() {
        carregar();
    }

    /**
     * Carrega (ou recarrega) todas as notas fiscais do arquivo JSON
     * para a lista em memória ({@code this.notas}).
     *
     * Se a lista carregada não estiver vazia, este método encontra o
     * ID mais alto e atualiza o contador estático na classe {@link NotaFiscal}
     * (via {@link NotaFiscal#atualizarContador(int)}) para evitar IDs
     * duplicados em novos cadastros.
     */
    public void carregar() {
        notas = repo.buscarTodos();
        if (!notas.isEmpty()) {
            int maiorId = notas.stream()
                    .mapToInt(NotaFiscal::getId)
                    .max()
                    .orElse(0);
            NotaFiscal.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona uma nova nota fiscal à lista em memória e persiste
     * imediatamente a lista atualizada no arquivo JSON.
     *
     * @param nota A {@link NotaFiscal} a ser adicionada e salva.
     */
    public void adicionar(NotaFiscal nota) {
        notas.add(nota);
        repo.salvarTodos(notas);
    }

    /**
     * Gera uma nota fiscal baseada apenas em um agendamento (sem vendas de produtos).
     *
     * Este é um método de conveniência que chama
     * {@link #gerarNotaFiscal(Agendamento, List)} com uma lista vazia de vendas.
     * A nota gerada é persistida automaticamente.
     *
     * @param agendamento O agendamento base para a nota.
     * @return A {@link NotaFiscal} gerada e persistida.
     */
    public NotaFiscal gerarNotaFiscal(Agendamento agendamento) {
        return gerarNotaFiscal(agendamento, new ArrayList<>());
    }

    /**
     * Cria, adiciona e persiste uma nova nota fiscal baseada em um agendamento
     * e uma lista de vendas de produtos associadas.
     *
     * Se o agendamento for nulo, a operação é abortada.
     * A nota fiscal criada é imediatamente salva no arquivo JSON
     * através do método {@link #adicionar(NotaFiscal)}.
     *
     * @param agendamento O agendamento (serviços) a ser incluído na nota.
     * @param vendasProdutos A lista de vendas de produtos a ser incluída.
     * @return A {@link NotaFiscal} gerada e persistida, ou {@code null}
     * se o agendamento fornecido for nulo.
     */
    public NotaFiscal gerarNotaFiscal(Agendamento agendamento, List<Venda> vendasProdutos) {
        if (agendamento == null) {
            return null;
        }
        NotaFiscal nota = new NotaFiscal(agendamento, vendasProdutos);
        adicionar(nota); // O método adicionar já salva
        return nota;
    }

    /**
     * Retorna a lista de notas fiscais atualmente mantida em memória.
     *
     * @return Uma {@link List} de {@link NotaFiscal}.
     */
    public List<NotaFiscal> listar() {
        return notas;
    }

    /**
     * Remove todas as notas fiscais do sistema (memória e persistência).
     *
     * Este método limpa a lista em memória ({@code this.notas}) e,
     * em seguida, salva uma lista vazia no arquivo JSON
     * (via {@code repo.salvarTodos}), efetivamente limpando todos os
     * dados persistidos.
     */
    public void limpar() {
        notas = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}