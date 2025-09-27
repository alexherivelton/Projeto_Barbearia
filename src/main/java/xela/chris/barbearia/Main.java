package xela.chris.barbearia;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.negocio.Barbearia;

public class Main {
    public static void main(String[] args) {

        Barbearia penteFino = new Barbearia();

        Funcionario f1 = new Funcionario();
        Cliente c1 = new Cliente("Rodolfo", "14027245601", "38997302679");
        Cliente c2 = new Cliente("Rodolfo", "14027245601", "38997302679");

        penteFino.adicionarCliente(c1);
        penteFino.adicionarCliente(c2);
        System.out.println(penteFino.getClientes());
    }
}