package xela.chris.barbearia.servicos;

import xela.chris.barbearia.Gerenciadores.GerenciadorProduto;
import xela.chris.barbearia.Gerenciadores.GerenciarVenda;
import xela.chris.barbearia.models.Produto;
import xela.chris.barbearia.models.Venda;

public class ServicoVenda {

    private final GerenciadorProduto gerenciadorProduto;
    private final GerenciarVenda gerenciarVenda;

    public ServicoVenda() {
        this.gerenciadorProduto = new GerenciadorProduto();
        this.gerenciarVenda = new GerenciarVenda();
    }

    public ServicoVenda(GerenciadorProduto gerenciadorProduto, GerenciarVenda gerenciarVenda) {
        this.gerenciadorProduto = gerenciadorProduto;
        this.gerenciarVenda = gerenciarVenda;
    }

    public boolean efetuarVenda(int produtoId, int quantidade, String data) {
        gerenciadorProduto.carregar();
        Produto produto = gerenciadorProduto.buscarPorId(produtoId);
        if (produto == null) {
            return false;
        }
        if (!gerenciadorProduto.atualizarEstoque(produtoId, quantidade)) {
            return false;
        }
        gerenciarVenda.carregar();
        Venda venda = new Venda(produto, quantidade, data);
        gerenciarVenda.adicionar(venda);
        return true;
    }

    public double calcularTotalVendas() {
        gerenciarVenda.carregar();
        return gerenciarVenda.calcularTotalVendas();
    }
}
