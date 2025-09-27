package xela.chris.barbearia;

import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.Repositorios.ClienteJson;
import xela.chris.barbearia.Repositorios.FuncionarioJson;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClienteJson clienteJson = new ClienteJson();
        FuncionarioJson funcionarioJson = new FuncionarioJson();

        System.out.println("Pasta dos JSONs: " + new File("jsons").getAbsolutePath());

        clienteJson.limpar();
        funcionarioJson.limpar();

        Cliente cliente1 = new Cliente("Christian", "1908635986235", "193847-6543689", StatusAtendimentoCliente.EM_ESPERA);
        Cliente cliente2 = new Cliente("xela", "1908635986235", "193847-6543689", StatusAtendimentoCliente.EM_ESPERA);
        Funcionario funcionario1 = new Funcionario("Maria", "3498754367", "45986745", "Atendente",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.GERAR_NOTA));

        clienteJson.adicionar(cliente1);
        clienteJson.adicionar(cliente2);
        funcionarioJson.adicionar(funcionario1);

        System.out.println("Clientes: " + clienteJson.listar());
        System.out.println("Funcionarios: " + funcionarioJson.listar());

        System.out.println("Arquivo de clientes existe: " + new File("jsons/clientes.json").exists());
        System.out.println("Arquivo de funcionarios existe: " + new File("jsons/funcionarios.json").exists());
    }
}