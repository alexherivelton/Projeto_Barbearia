package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma Ordem de Serviço (OS) na barbearia.
 *
 * Esta classe agrupa todos os serviços (via {@link Agendamento})
 * e vendas de produtos (via {@link Venda}) associados a um
 * {@link Cliente} específico em uma determinada {@code data}.
 *
 * Ela é responsável por calcular o valor total consolidado desses
 * itens e mantém um contador estático ({@code contador}) para
 * rastrear o número total de OS criadas.
 *
 * (Nota: Esta classe depende de {@link Venda} e {@link Servico},
 * que não estão explicitamente importadas neste arquivo).
 */
public class OrdemDeServico {
    /** Contador estático para rastrear o número total de OS criadas. */
    private static int contador = 0; // conta quantas OS foram criadas
    /** Identificador único e final desta Ordem de Serviço. */
    private final int id;

    /** O cliente associado a esta Ordem de Serviço. */
    private Cliente cliente;
    /** A data (em formato String) em que esta OS foi criada. */
    private String data;
    /** Lista de agendamentos (serviços) incluídos nesta OS. */
    private List<Agendamento> agendamentos;
    /** Lista de vendas de produtos incluídas nesta OS. */
    private List<Venda> vendas;

    /**
     * Constrói uma nova Ordem de Serviço.
     *
     * Ao ser criada, a OS inicializa as listas de agendamentos e vendas
     * como vazias, incrementa o contador estático ({@code contador})
     * e atribui um ID único a esta instância.
     *
     * @param cliente O cliente para o qual a OS está sendo aberta.
     * @param data A data (String) de abertura da OS.
     */
    public OrdemDeServico(Cliente cliente, String data) {
        this.cliente = cliente;
        this.data = data;
        this.agendamentos = new ArrayList<>();
        this.vendas = new ArrayList<>();

        contador++;
        this.id = contador;
    }

    /**
     * Adiciona um agendamento (serviço) à lista desta Ordem de Serviço.
     *
     * @param a O {@link Agendamento} a ser incluído.
     */
    public void adicionarAgendamento(Agendamento a) {
        agendamentos.add(a);
    }

    /**
     * Adiciona uma venda de produto à lista desta Ordem de Serviço.
     *
     * @param v A {@link Venda} a ser incluída.
     */
    public void adicionarVenda(Venda v) {
        vendas.add(v);
    }

    /**
     * Calcula o valor total desta Ordem de Serviço.
     *
     * O cálculo soma o preço de todos os {@link Servico} contidos em
     * todos os {@link Agendamento} da lista, e adiciona o
     * {@code valorTotal} de todas as {@link Venda} da lista.
     *
     * @return O valor total (double) consolidado.
     */
    public double calcularTotal() {
        double total = 0;

        for (Agendamento ag : agendamentos) {
            for (Servico servico : ag.getServicos()) {
                total += servico.getPreco();
            }
        }

        for (Venda v : vendas) {
            total += v.getValorTotal();
        }

        return total;
    }

    /**
     * Retorna o número total de Ordens de Serviço criadas (o valor
     * do contador estático).
     *
     * @return O total (int) de instâncias de OS criadas.
     */
    public static int getTotalOS() {
        return contador;
    }

    /**
     * Retorna o identificador único desta Ordem de Serviço.
     *
     * @return O ID (int) da OS.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna uma representação em String da Ordem de Serviço.
     *
     * Inclui o ID, nome do cliente, data, contagem de agendamentos
     * e vendas, e o valor total calculado.
     *
     * @return Uma String formatada com o resumo da OS.
     */
    @Override
    public String toString() {
        return "\n===============" +
                "Ordem de Serviço #" + id +
                "\nCliente: " + cliente.getNome() +
                "\nData: " + data +
                "\nAgendamentos: " + agendamentos.size() +
                "\nVendas: " + vendas.size() +
                "\nTotal: R$ " + calcularTotal();
    }
}