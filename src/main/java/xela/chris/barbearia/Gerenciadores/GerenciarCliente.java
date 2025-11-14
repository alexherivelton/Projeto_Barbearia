package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.Servico;
import xela.chris.barbearia.negocio.Agendamento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gerencia os clientes da barbearia, permitindo adicionar, remover,
 * listar e carregar clientes, persistindo os dados em um arquivo JSON.
 */
public class GerenciarCliente {

    private List<Cliente> clientes = new ArrayList<>();
    private RepositorioJson<Cliente> repo = new RepositorioJson<>(Cliente.class, "clientes.json");


    public GerenciarCliente() {
        this.carregar();
    }


    /**
     * Carrega todos os clientes do arquivo JSON para a lista interna.
     */
    public void carregar() {
        clientes = repo.buscarTodos();
        if (!clientes.isEmpty()) {
            int maiorId = clientes.stream()
                    .mapToInt(Cliente::getId)
                    .max()
                    .orElse(0);
            Cliente.atualizarContador(maiorId);
        }
    }

    public Cliente buscarCliente(int id) {
        Iterator<Cliente> iterator = clientes.iterator();
        while(iterator.hasNext()){
            Cliente cliente = iterator.next();
            if(cliente.getId() == id){
                return cliente;
            }
        }
        System.out.println("Diabo nao encontrado!");
        return null;
    }

    /**
     * Adiciona um novo cliente à lista e atualiza o arquivo JSON.
     *
     * @param cliente Cliente a ser adicionado.
     */
    public void adicionar(Cliente cliente) {
        clientes.add(cliente);
        repo.salvarTodos(clientes);
    }

    /**
     * Remove um cliente da lista pelo CPF e atualiza o arquivo JSON.
     *
     * @param cpf CPF do cliente a ser removido.
     * @return true se o cliente foi encontrado e removido; false caso contrário.
     */
    public boolean removerPorId(int id) {
        boolean removido = clientes.removeIf(c -> id.equals(c.getId()));
        if (removido) {
            repo.salvarTodos(clientes);
        }
        return removido;
    }

    public boolean atualizarCliente(int id, String novoNome, String novoCpf, String novoTelefone, StatusAtendimentoCliente status) {
        List<Cliente> clientes = repo.listar();
        Cliente cliente = buscarCliente(id);

        Cliente clienteParaAtualizar = buscarCliente(id);
        if(clienteParaAtualizar == null){
            System.out.println("Funcionario com o id{" + id + "} nao foi encontrado!");
            return false;
        }

        String nomeAtual = cliente.getNome();
        String cpfAtual = cliente.getCpf();
        String telefoneAtual = cliente.getTelefone();
        StatusAtendimentoCliente statusAtual = cliente.getStatusAtendimentoCliente();

        if(novoNome != null){
            cliente.setNome(novoNome);
        } else {
            cliente.setNome(nomeAtual);
        }

        if(novoCpf != null){
            cliente.setCpf(novoCpf);
        } else {
            cliente.setCpf(cpfAtual);
        }

        if(novoTelefone != null){
            cliente.setTelefone(novoTelefone);
        } else {
            cliente.setTelefone(telefoneAtual);
        }
        //mexer na logica ------0923u892403y5bbdfgsjklbsdfsdfglkjbsdcfg lkjb sdfg lkjbn
        if(status != null){
            cliente.setStatusAtendimentoCliente(status);
        } else {
            cliente.setStatusAtendimentoCliente(statusAtual);
        }


        repo.salvarTodos(clientes);

        System.out.println("Sucesso em atualizar!");
        return true;
    }

    /**
     * Retorna a lista de todos os clientes atualmente gerenciados.
     *
     * @return Lista de clientes.
     */
    public List<Cliente> listar() {
        System.out.println("➡ Serviços carregados: " + clientes.size());
        for (Cliente c : clientes) {
            System.out.println(c);
        }
        return List.of();
    }

    /**
     * Limpa todos os clientes da lista e do arquivo JSON.
     */
    public void limpar() {
        clientes = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
