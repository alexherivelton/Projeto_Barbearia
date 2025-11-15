package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.NotaFiscal;
import xela.chris.barbearia.negocio.Agendamento;
import xela.chris.barbearia.models.Venda;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador responsável por manter e persistir notas fiscais de serviços.
 *
 * <p>
 * Esta classe fornece métodos para carregar notas fiscais de um arquivo JSON,
 * adicionar novas notas, listar notas existentes e limpar o repositório.
 * A persistência é realizada através da classe {@link RepositorioJson}.
 * </p>
 */
public class GerenciarNotaFiscal {

    /** Lista de notas fiscais mantida em memória. */
    private List<NotaFiscal> notas = new ArrayList<>();
    /** Repositório JSON para armazenamento das notas fiscais. */
    private final RepositorioJson<NotaFiscal> repo = new RepositorioJson<>(NotaFiscal.class, "notasFiscais.json");

    /**
     * Construtor que inicia o carregamento das notas salvas em disco.
     */
    public GerenciarNotaFiscal() {
        carregar();
    }

    /**
     * Carrega todas as notas fiscais existentes do arquivo JSON para a lista interna.
     * Se não houver notas ou o arquivo estiver vazio, a lista permanecerá vazia.
     * Também sincroniza o contador de IDs com o maior ID encontrado.
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
     * Persiste e adiciona uma nova nota fiscal à lista de notas.
     *
     * @param nota a nova nota fiscal a ser adicionada
     */
    public void adicionar(NotaFiscal nota) {
        notas.add(nota);
        repo.salvarTodos(notas);
    }

    public NotaFiscal gerarNotaFiscal(Agendamento agendamento) {
        return gerarNotaFiscal(agendamento, new ArrayList<>());
    }

    /**
     * Gera e persiste uma nova nota fiscal com serviços e vendas de produtos.
     *
     * @param agendamento agendamento que originou a nota fiscal
     * @param vendasProdutos vendas de produtos associadas ao cliente
     * @return instância da nota fiscal gerada ou {@code null} caso o agendamento seja inválido
     */
    public NotaFiscal gerarNotaFiscal(Agendamento agendamento, List<Venda> vendasProdutos) {
        if (agendamento == null) {
            return null;
        }
        NotaFiscal nota = new NotaFiscal(agendamento, vendasProdutos);
        adicionar(nota);
        return nota;
    }

    /**
     * Retorna todas as notas fiscais atualmente carregadas.
     *
     * @return lista de notas fiscais
     */
    public List<NotaFiscal> listar() {
        return notas;
    }

    /**
     * Remove todas as notas fiscais e limpa o arquivo JSON correspondente.
     */
    public void limpar() {
        notas = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}