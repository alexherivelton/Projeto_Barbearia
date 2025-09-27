package xela.chris.barbearia.negocio;

import xela.chris.barbearia.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Barbearia {

    private List<Cliente> clientes;
    private int proximoId = 1;

    public Barbearia (){
        this.clientes = new ArrayList();
    }

    public void adicionarCliente(Cliente cliente){
        cliente.setId(proximoId);
        clientes.add(cliente);
        proximoId++;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }


    public int getProximoId() {
        return proximoId;
    }

    public void setProximoId(int proximoId) {
        this.proximoId = proximoId++;
    }
}
