package xela.chris.barbearia.models;

import xela.chris.barbearia.negocio.Agendamento;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa uma nota fiscal emitida para um agendamento.
 * Contém informações sobre a data de emissão, origem (agendamento)
 * e os produtos/itens adquiridos.
 */
public class NotaFiscal {

    private static final AtomicInteger contador = new AtomicInteger(0);


    private int id;
    private Date dataEmissao;
    private Agendamento agendamento;
    private List<Servico> servicos = new ArrayList<>();
    private List<Venda> vendasProdutos = new ArrayList<>();
    private List<Produto> itens = new ArrayList<>();
    private double valorTotal;

    /**
     * Construtor padrão necessário para operações de serialização.
     */
    public NotaFiscal() {
    }


    /**
     * Construtor da classe NotaFiscal.
     *
     * @param agendamento o agendamento cujos serviços compõem a nota fiscal
     */
    public NotaFiscal(Agendamento agendamento) {
        this(agendamento, new ArrayList<>());
    }

    /**
     * Construtor da classe NotaFiscal permitindo incluir vendas de produtos.
     *
     * @param agendamento o agendamento cujos serviços compõem a nota fiscal
     * @param vendasProdutos lista de vendas de produtos a serem vinculadas
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
     * Retorna o identificador da nota fiscal.
     *
     * @return id da nota fiscal.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador da nota fiscal.
     *
     * @param id Novo identificador da nota fiscal.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna a data de emissão da nota fiscal.
     *
     * @return data de emissão.
     */
    public Date getDataEmissao() {
        return dataEmissao;
    }

    /**
     * Define a data de emissão da nota fiscal.
     *
     * @param dataEmissao Nova data de emissão.
     */
    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    /**
     * Retorna o agendamento de origem da nota fiscal.
     *
     * @return agendamento relacionado.
     */
    public Agendamento getAgendamento() {
        return agendamento;
    }

    /**
     * Define o agendamento de origem da nota fiscal.
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
     * Obtém a lista de serviços desta nota fiscal.
     *
     * @return lista de serviços
     */
    public List<Servico> getServicos() {
        return servicos;
    }

    /**
     * Define a lista de serviços e atualiza o valor total conforme os preços.
     *
     * @param servicos nova lista de serviços
     */
    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos != null ? servicos : new ArrayList<>();
        atualizarValorTotal();
    }

    /**
     * Retorna a lista de produtos/itens da nota fiscal.
     *
     * @return lista de produtos.
     */
    public List<Produto> getItens() {
        return itens;
    }

    /**
     * Obtém o valor total da nota fiscal.
     *
     * @return soma dos preços dos serviços
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna a lista de vendas de produtos associadas a esta nota.
     *
     * @return lista de vendas de produtos
     */
    public List<Venda> getVendasProdutos() {
        return vendasProdutos;
    }

    /**
     * Obtém o valor total da nota fiscal.
     *
     * @param vendasProdutos vendas de produtos a serem associadas
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
     * Adiciona uma venda de produto à nota fiscal, atualizando totais automaticamente.
     *
     * @param venda venda a ser adicionada
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

    private double calcularTotalServicos() {
        return servicos == null ? 0.0 : servicos.stream().mapToDouble(Servico::getPreco).sum();
    }

    private double calcularTotalProdutos() {
        return vendasProdutos == null ? 0.0 : vendasProdutos.stream().mapToDouble(Venda::getValorTotal).sum();
    }

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

    private void atualizarValorTotal() {
        this.valorTotal = calcularTotalServicos() + calcularTotalProdutos();
    }


    /**
     * Atualiza o contador estático para sincronização com IDs persistidos.
     *
     * @param ultimoId maior ID encontrado ao carregar dados
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }


    /**
     * Representação em string da nota fiscal, detalhando cada serviço
     * e o valor total. Também formata a data de emissão para melhor
     * legibilidade.
     *
     * @return string com detalhes da nota fiscal
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
