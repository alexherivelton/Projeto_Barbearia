package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.RegistroPonto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por gerenciar os registros de ponto (entrada e saída)
 * dos funcionários.
 *
 * Este gerenciador controla a persistência dos registros em um arquivo JSON
 * ("pontos.json") e mantém uma lista em memória. Ele fornece a lógica
 * principal para "bater o ponto", que registra a entrada ou a saída
 * com base no estado atual do funcionário no dia corrente.
 */
public class GerenciadorPonto {

    /** Lista de registros de ponto mantidos em memória. */
    private List<RegistroPonto> pontos = new ArrayList<>();

    /** Repositório responsável por persistir registros no arquivo "pontos.json". */
    private final RepositorioJson<RegistroPonto> repo =
            new RepositorioJson<>(RegistroPonto.class, "pontos.json");

    /**
     * Construtor padrão.
     * Inicializa o gerenciador e chama imediatamente o método {@link #carregar()}
     * para popular a lista de pontos a partir do arquivo JSON.
     */
    public GerenciadorPonto() {
        this.carregar();
    }

    /**
     * Carrega (ou recarrega) todos os registros de ponto do repositório JSON
     * para a lista em memória ({@code pontos}).
     * Se o arquivo não puder ser lido ou estiver vazio, inicializa
     * {@code pontos} como uma nova ArrayList vazia.
     */
    public void carregar() {
        pontos = repo.buscarTodos();
        if (pontos == null) {
            pontos = new ArrayList<>();
        }
    }

    /**
     * Registra o ponto (entrada ou saída) de um funcionário para a data atual.
     *
     * Este método é sincronizado e segue a seguinte lógica:
     * 1. Busca no repositório (`repo.listar()`) se já existe um registro
     * para o funcionário na data atual (`LocalDate.now()`).
     * 2. Se não existir registro: Cria um novo {@link RegistroPonto},
     * define a {@code horaEntrada} e o adiciona à lista.
     * 3. Se existir registro e a {@code horaSaida} for nula: Define a
     * {@code horaSaida} no registro existente.
     * 4. Se existir registro e a {@code horaSaida} já estiver definida:
     * Informa que o ponto já foi totalmente registrado no dia.
     *
     * Ao final, a lista em memória ({@code this.pontos}) é atualizada
     * com o resultado das operações.
     *
     * @param funcionario O funcionário que está registrando o ponto.
     */
    public synchronized void baterPonto(Funcionario funcionario) {
        String dataAtual = LocalDate.now().toString();
        List<RegistroPonto> registros = repo.listar();

        RegistroPonto pontoHoje = null;

        for (RegistroPonto p : registros) {
            if (p.getIdFuncionario() != null
                    && p.getIdFuncionario().getId() == funcionario.getId()
                    && dataAtual.equals(p.getData())) {
                pontoHoje = p;
                break;
            }
        }

        if (pontoHoje == null) {
            RegistroPonto novoRegistro = new RegistroPonto();
            novoRegistro.setIdFuncionario(funcionario);
            novoRegistro.setData(dataAtual);
            novoRegistro.setHoraEntrada(LocalTime.now().withNano(0).toString());

            registros.add(novoRegistro);

            System.out.println("Ponto registrado com sucesso! " +
                    funcionario.getNome() + " no horário: " + novoRegistro.getHoraEntrada());
        }
        else if (pontoHoje.getHoraSaida() == null) {
            pontoHoje.setHoraSaida(LocalTime.now().withNano(0).toString());

            System.out.println("Registro de saída salvo com sucesso! " +
                    funcionario.getNome() + " no horário: " + pontoHoje.getHoraSaida());
        }
        else {
            System.out.println("O funcionário " + funcionario.getNome() +
                    " já registrou entrada e saída hoje.");
        }

        pontos = registros;
    }

    /**
     * Busca o registro de ponto de um funcionário para uma data específica.
     *
     * Este método *recarrega* a lista de pontos do repositório
     * ({@code repo.buscarTodos()}) antes de realizar a busca,
     * ignorando o estado atual da lista em memória.
     *
     * @param funcionario O funcionário cujo ponto será procurado.
     * @param data A data desejada no formato ISO (ex: "2025-11-15").
     * @return O objeto {@link RegistroPonto} encontrado, ou {@code null}
     * se não for encontrado.
     */
    public RegistroPonto buscarPorData(Funcionario funcionario, String data) {
        // Recarrega do arquivo antes de buscar
        pontos = repo.buscarTodos();
        if (pontos == null) {
            System.out.println("Nenhum ponto foi encontrado no arquivo!");
            return null;
        }

        for (RegistroPonto r : pontos) {
            if (r.getIdFuncionario() != null &&
                    r.getIdFuncionario().getId() == funcionario.getId() &&
                    data.equals(r.getData())) {

                System.out.println("Ponto encontrado: " +
                        funcionario.getNome() +
                        " em " + data +
                        " - Entrada: " + r.getHoraEntrada() +
                        ", Saída: " + r.getHoraSaida());

                return r;
            }
        }

        System.out.println("Nenhum ponto encontrado para " +
                funcionario.getNome() + " em " + data);

        return null;
    }

    /**
     * Salva a lista de registros de ponto atualmente em memória
     * ({@code this.pontos}) no arquivo JSON.
     *
     * Esta ação sobrescreve o conteúdo anterior do arquivo.
     */
    public void salvarPonto(){
        repo.salvarTodos(pontos);
    }

    /**
     * Remove todos os registros de ponto do sistema.
     *
     * Esta operação limpa a lista em memória ({@code this.pontos})
     * e, em seguida, salva uma lista vazia no arquivo JSON,
     * efetivamente limpando todos os dados persistidos.
     */
    public void limpar() {
        pontos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}