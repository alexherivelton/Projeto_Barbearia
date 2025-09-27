package xela.chris.barbearia.negocio;

import xela.chris.barbearia.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Barbearia {

    private List<Cliente> clientes;

    public Barbearia (){
        this.clientes = new ArrayList();
    }


    public List<Cliente> getClientes() {
        return clientes;
    }

}
