package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.RegistroPonto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por gerenciar registros de ponto dos funcionários.
 * <p>
 * Este gerenciador realiza operações de controle de ponto, como registrar
 * entrada e saída, consultar pontos por data e persistir informações no
 * arquivo JSON. A estrutura utiliza um repositório genérico para salvamento.
 * </p>
 *
 * <p>Principais funcionalidades:</p>
 * <ul>
 *     <li>Registrar entrada e saída de funcionários</li>
 *     <li>Buscar registro de ponto por data</li>
 *     <li>Carregar registros salvos</li>
 *     <li>Limpar todos os registros de ponto</li>
 * </ul>
 */
public class GerenciadorPonto {

    /** Lista de registros de ponto mantidos em memória. */
    private List<RegistroPonto> pontos = new ArrayList<>();

    /** Repositório responsável por persistir registros no arquivo JSON. */
    private final RepositorioJson<RegistroPonto> repo =
            new RepositorioJson<>(RegistroPonto.class, "pontos.json");

    /**
     * Construtor que inicializa o gerenciador carregando registros já existentes.
     */
    public GerenciadorPonto() {
        this.carregar();
    }

    /**
     * Carrega todos os registros de ponto do repositório JSON para a memória.
     * Caso o arquivo esteja vazio, uma lista nova é criada.
     */
    public void carregar() {
        pontos = repo.buscarTodos();
        if (pontos == null) {
            pontos = new ArrayList<>();
        }
    }

    /**
     * Registra o ponto (entrada ou saída) de um funcionário com base na data atual.
     * <p>
     * Regras:
     * <ul>
     *     <li>Se não existir registro para o funcionário na data atual → registra ENTRADA</li>
     *     <li>Se existir entrada mas não houver saída → registra SAÍDA</li>
     *     <li>Se já houver entrada e saída → uma mensagem é exibida informando que o ponto já foi finalizado</li>
     * </ul>
     * </p>
     *
     * @param funcionario Funcionário que está registrando o ponto
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

        // Se for o primeiro registro do dia → ENTRADA
        if (pontoHoje == null) {
            RegistroPonto novoRegistro = new RegistroPonto();
            novoRegistro.setIdFuncionario(funcionario);
            novoRegistro.setData(dataAtual);
            novoRegistro.setHoraEntrada(LocalTime.now().withNano(0).toString());

            registros.add(novoRegistro);

            System.out.println("Ponto registrado com sucesso! " +
                    funcionario.getNome() + " no horário: " + novoRegistro.getHoraEntrada());
        }
        // Se já existe entrada mas ainda não existe saída → SAÍDA
        else if (pontoHoje.getHoraSaida() == null) {
            pontoHoje.setHoraSaida(LocalTime.now().withNano(0).toString());

            System.out.println("Registro de saída salvo com sucesso! " +
                    funcionario.getNome() + " no horário: " + pontoHoje.getHoraSaida());
        }
        // Se já registrou entrada e saída
        else {
            System.out.println("O funcionário " + funcionario.getNome() +
                    " já registrou entrada e saída hoje.");
        }

        repo.salvarTodos(registros);
        pontos = registros;
    }

    /**
     * Busca o registro de ponto de um funcionário para uma data específica.
     *
     * @param funcionario Funcionário cujo ponto será procurado
     * @param data Data desejada no formato ISO (yyyy-MM-dd)
     * @return o registro encontrado, ou {@code null} caso não exista
     */
    public RegistroPonto buscarPorData(Funcionario funcionario, String data) {
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
     * Remove todos os registros de ponto do sistema, tanto da memória quanto do arquivo JSON.
     */
    public void limpar() {
        pontos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
