package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa uma nota fiscal emitida na barbearia, consolidando serviços
 * de um {@link Agendamento} e, opcionalmente, vendas de {@link Produto}.
 *
 * A classe é responsável por:
 * - Atribuir um ID único (via contador estático).
 * - Registrar a data de emissão.
 * - Armazenar os serviços do agendamento.
 * - Armazenar as vendas de produtos associadas.
 * - Calcular e atualizar automaticamente o {@code valorTotal}
 * com base nos serviços e produtos.
 */
public class NotaFiscal {

    /** Contador estático para gerar IDs únicos para novas notas fiscais. */
    private static final AtomicInteger contador = new AtomicInteger(0);


    private int id;
    private Date dataEmissao;
    private Agendamento agendamento;
    private List<Servico> servicos = new ArrayList<>();
    private List<Venda> vendasProdutos = new ArrayList<>();
    /** Lista de Produtos (itens) extraída das vendasProdutos. */
    private List<Produto> itens = new ArrayList<>();
    /** Valor total (Serviços + Produtos). */
    private double valorTotal;

    /**
     * Construtor padrão (sem argumentos).
     * Necessário para a desserialização (ex: do JSON).
     */
    public NotaFiscal() {
    }


    /**
     * Construtor que cria uma nota fiscal baseada apenas em um agendamento
     * (sem vendas de produtos).
     *
     * @param agendamento O agendamento que originou a nota (fonte dos serviços).
     */
    public NotaFiscal(Agendamento agendamento) {
        this(agendamento, new ArrayList<>());
    }

    /**
     * (NOVO) Construtor que cria uma nota fiscal baseada apenas em uma lista
     * de vendas de produtos (sem agendamento de serviços).
     *
     * @param vendasProdutos A lista de {@link Venda} (produtos) a ser incluída.
     */
    public NotaFiscal(List<Venda> vendasProdutos) {
        this(null, vendasProdutos);
    }

    /**
     * Construtor completo que cria uma nota fiscal baseada em um agendamento
     * (serviços) e uma lista de vendas de produtos.
     *
     * Ao ser chamado, atribui um novo ID, define a data de emissão atual,
     * extrai os serviços do agendamento, processa as vendas de produtos
     * e calcula o {@code valorTotal}.
     *
     * @param agendamento O agendamento que originou a nota (fonte dos serviços).
     * @param vendasProdutos A lista de {@link Venda} (produtos) a ser incluída.
     */
    public NotaFiscal(Agendamento agendamento, List<Venda> vendasProdutos) {
        this.id = contador.incrementAndGet();
        this.dataEmissao = new Date();
        this.agendamento = agendamento;
        if (agendamento != null) {
            List<Servico> servicosAgendamento = agendamento.getServicos();
            this.servicos = servicosAgendamento != null ? new ArrayList<>(servicosAgendamento) : new ArrayList<>();
        } else {
            this.servicos = new ArrayList<>();
        }
        setVendasProdutos(vendasProdutos);
        atualizarValorTotal();
    }

    /**
     * Obtém o ID único desta nota fiscal.
     *
     * @return O identificador (int).
     */
    public int getId() {
        return id;
    }

    /**
     * Define o ID da nota fiscal.
     * (Usado principalmente pela desserialização).
     *
     * @param id Novo identificador da nota fiscal.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a data e hora em que a nota fiscal foi emitida.
     *
     * @return O objeto {@link Date} da emissão.
     */
    public Date getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define a data de emissão da nota.
     *
     * @param dataEmissao Nova data de emissão.
     */
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    /**
     * Obtém o agendamento que originou esta nota fiscal (fonte dos serviços).
     *
     * @return O objeto {@link Agendamento} associado.
     */
    public Agendamento getAgendamento() {
        return agendamento;
    }

    /**
     * Define o agendamento associado a esta nota.
     * Ao definir, a lista de serviços ({@code servicos}) é automaticamente
     * extraída do agendamento e o {@code valorTotal} é recalculado.
     *
     * @param agendamento Novo agendamento relacionado.
     */
    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
        if (agendamento != null) {
            List<Servico> servicosAgendamento = agendamento.getServicos();
            this.servicos = servicosAgendamento != null ? new ArrayList<>(servicosAgendamento) : new ArrayList<>();
        } else {
            this.servicos = new ArrayList<>();
        }
        atualizarValorTotal();
    }


    /**
     * Obtém a lista de serviços incluídos nesta nota (geralmente vinda do agendamento).
     *
     * @return Uma {@link List} de {@link Servico}.
     */
    public List<Servico> getServicos() {
        return servicos;
    }

    /**
     * Define a lista de serviços da nota.
     * O {@code valorTotal} é recalculado automaticamente.
     *
     * @param servicos nova lista de serviços.
     */
    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos != null ? servicos : new ArrayList<>();
        atualizarValorTotal();
    }

    /**
     * Retorna a lista de produtos/itens da nota fiscal.
     * (Esta lista é derivada da {@code vendasProdutos}).
     *
     * @return Uma {@link List} de {@link Produto}.
     */
    public List<Produto> getItens() {
        return itens;
    }

    /**
     * Obtém o valor total calculado da nota (Serviços + Produtos).
     *
     * @return O valor total (double).
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna a lista de vendas de produtos associadas a esta nota.
     *
     * @return Uma {@link List} de {@link Venda}.
     */
    public List<Venda> getVendasProdutos() {
        return vendasProdutos;
    }

    /**
     * Define a lista de vendas de produtos.
     * Ao definir, a lista {@code itens} é atualizada e o {@code valorTotal}
     * é recalculado automaticamente.
     *
     * @param vendasProdutos vendas de produtos a serem associadas.
     */
    public void setVendasProdutos(List<Venda> vendasProdutos) {
        if (vendasProdutos == null) {
            this.vendasProdutos = new ArrayList<>();
        } else {
            this.vendasProdutos = new ArrayList<>(vendasProdutos);
        }
        atualizarItensProdutos();
        atualizarValorTotal();
    }

    /**
     * Adiciona uma única venda de produto à nota.
     * A lista {@code itens} e o {@code valorTotal} são recalculados
     * automaticamente.
     *
     * @param venda A {@link Venda} a ser adicionada.
     */
    public void adicionarVendaProduto(Venda venda) {
        if (venda != null) {
            if (this.vendasProdutos == null) {
                this.vendasProdutos = new ArrayList<>();
            }
            this.vendasProdutos.add(venda);
            atualizarItensProdutos();
            atualizarValorTotal();
        }
    }

    /**
     * Calcula a soma dos preços de todos os serviços na lista {@code servicos}.
     *
     * @return O total (double) dos serviços.
     */
    private double calcularTotalServicos() {
        return servicos == null ? 0.0 : servicos.stream().mapToDouble(Servico::getPreco).sum();
    }

    /**
     * Calcula a soma dos valores de todas as vendas na lista {@code vendasProdutos}.
     *
     * @return O total (double) dos produtos.
     */
    private double calcularTotalProdutos() {
        return vendasProdutos == null ? 0.0 : vendasProdutos.stream().mapToDouble(Venda::getValorTotal).sum();
    }

    /**
     * Sincroniza a lista {@code itens} (Produtos) com base na lista
     * {@code vendasProdutos} (Vendas).
     * Extrai o {@link Produto} de cada {@link Venda}.
     */
    private void atualizarItensProdutos() {
        if (vendasProdutos == null) {
            this.itens = new ArrayList<>();
            return;
        }
        this.itens = vendasProdutos.stream()
                .map(Venda::getProduto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Recalcula o {@code valorTotal} da nota somando o total de serviços
     * e o total de produtos.
     */
    private void atualizarValorTotal() {
        this.valorTotal = calcularTotalServicos() + calcularTotalProdutos();
    }


    /**
     * Atualiza o contador estático ({@code contador}) para o último ID
     * conhecido (geralmente o maior ID encontrado ao carregar do JSON).
     *
     * Isso evita a duplicação de IDs após reiniciar a aplicação.
     *
     * @param ultimoId O maior ID encontrado na persistência.
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }


    /**
     * Retorna uma representação em String formatada da nota fiscal.
     *
     * Inclui ID, data, cliente, funcionário, detalhes dos serviços,
     * detalhes dos produtos e o valor total.
     *
     * @return A String formatada da nota.
     */
    @Override
    public String toString() {

//        String clienteNome = (agendamento != null && agendamento.getCliente() != null)
//                ? agendamento.getCliente().getNome()
//                : "N/A";

        String funcionarioNome = (agendamento != null && agendamento.getFuncionario() != null)
                ? agendamento.getFuncionario().getNome()
                : "N/A";

        List<Servico> servicosExibicao = servicos != null ? servicos : new ArrayList<>();
        String listaServicos = "";
        for (Servico s : servicosExibicao) {
            listaServicos += "  • " + s.getNome() + " - R$ " + String.format("%.2f", s.getPreco()) + "\n";
        }

        if (listaServicos.isEmpty()) {
            listaServicos = "  • Nenhum serviço associado\n";
        }

        List<Venda> vendasExibicao = vendasProdutos != null ? vendasProdutos : new ArrayList<>();
        String listaProdutos = "";
        for (Venda venda : vendasExibicao) {
            String produtoNome = venda.getProduto() != null ? venda.getProduto().getNome() : "Produto N/A";
            listaProdutos += "  • " + produtoNome +
                    " x" + venda.getQuantidade() +
                    " - R$ " + String.format("%.2f", venda.getValorTotal()) +
                    "\n";
        }

        if (listaProdutos.isEmpty()) {
            listaProdutos = "  • Nenhum produto vendido\n";
        }

        String clienteNome = (agendamento != null && agendamento.getCliente() != null)
                ? agendamento.getCliente().getNome()
                : vendasProdutos.stream()
                .map(Venda::getCliente)
                .filter(Objects::nonNull)
                .map(Cliente::getNome)
                .findFirst()
                .orElse("N/A");


        return "\n===============" +
                "\n ID Nota: " + id +
                "\n Data de Emissão: " + dataEmissao +
                "\n Cliente: " + clienteNome +
                "\n Funcionário: " + funcionarioNome +
                "\n Serviços:\n" + listaServicos +
                " Produtos:\n" + listaProdutos +
                " Valor Total: R$ " + String.format("%.2f", valorTotal) +
                "\n===============";
    }
}