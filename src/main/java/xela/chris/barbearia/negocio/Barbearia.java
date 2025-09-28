package xela.chris.barbearia.negocio;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class Barbearia {

    private List<Cliente> clientes;
    private List<Produto> produtos;

    public Barbearia (){
        this.clientes = new ArrayList();
        this.produtos = new ArrayList();
    }


    public List<Cliente> getClientes() {
        return clientes;
    }

}
