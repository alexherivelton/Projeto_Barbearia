package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Cliente;
import xela.chris.barbearia.models.Funcionario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável por gerenciar os funcionários do sistema.
 */
public class GerenciadorFuncionario {

    private List<Funcionario> funcionarios = new ArrayList<>();
    private final RepositorioJson<Funcionario> repo = new RepositorioJson<>(Funcionario.class, "funcionarios.json");;

    public GerenciadorFuncionario() {
        this.carregar();
    }

    public void carregar() {
        funcionarios = repo.buscarTodos();
        if (!funcionarios.isEmpty()) {
            int maiorId = funcionarios.stream()
                    .mapToInt(Funcionario::getId)
                    .max()
                    .orElse(0);
            Funcionario.atualizarContador(maiorId);
        }
    }

    /**
     * Adiciona um novo funcionário (somente quem tiver permissão de administrador pode).
     */
    public synchronized void adicionarFuncionario(Funcionario funcionario) {

        List<Funcionario> funcionarios = repo.listar();
        funcionarios.add(funcionario);
        repo.salvarTodos(funcionarios);
    }
    /**
     * Lista todos os funcionários (somente administrador).
     */
    public List<Funcionario> listarFuncionarios() {

        return funcionarios;
    }

    /**
     * Remove um funcionário (somente administrador).
     */
    public void removerFuncionario(int id) {

        List<Funcionario> funcionarios = repo.listar();
        funcionarios.removeIf(f -> f.getId() == id);
        repo.salvarTodos(funcionarios);
    }

    public Funcionario buscarFuncionario(int id) {
        Iterator<Funcionario> iterator = funcionarios.iterator();
        while(iterator.hasNext()){
            Funcionario funcionario = iterator.next();
            if(funcionario.getId() == id){
                return funcionario;
            }
        }
        System.out.println("Diabo nao encontrado!");
        return null;
    }

    public boolean atualizarFuncionario(int id, String novoNome, String novoCpf, String novoTelefone, String novoCargo, String novoUsuario, String novaSenha) {
        List<Funcionario> funcionarios = repo.listar();
        Funcionario funcionario = buscarFuncionario(id);

        Funcionario funcionarioParaAtualizar = buscarFuncionario(id);
        if(funcionarioParaAtualizar == null){
            System.out.println("Funcionario com o id{" + id + "} nao foi encontrado!");
            return false;
        }

        String nomeAtual = funcionario.getNome();
        String cpfAtual = funcionario.getCpf();
        String telefoneAtual = funcionario.getTelefone();
        String cargoAtual = funcionario.getCargo();
        String usuarioAtual = funcionario.getUsuario();
        String senhaAtual = funcionario.getSenha();

        if(novoNome != null){
            funcionario.setNome(novoNome);
        } else {
            funcionario.setNome(nomeAtual);
        }

        if(novoCpf != null){
            funcionario.setCpf(novoCpf);
        } else {
            funcionario.setCpf(cpfAtual);
        }

        if(novoTelefone != null){
            funcionario.setTelefone(novoTelefone);
        } else {
            funcionario.setTelefone(telefoneAtual);
        }


        if(novoCargo != null){
            funcionario.setCargo(novoCargo);
            funcionario.definirPermissoesPorCargo(novoCargo);
        } else {
            funcionario.setCargo(cargoAtual);
            funcionario.definirPermissoesPorCargo(cargoAtual);
        }

        if(novoUsuario != null){
            funcionario.setUsuario(novoUsuario);
        } else {
            funcionario.setUsuario(usuarioAtual);
        }

        if(novaSenha != null){
            funcionario.setSenha(novaSenha);
        } else {
            funcionario.setSenha(senhaAtual);
        }

        repo.salvarTodos(funcionarios);

        System.out.println("Sucesso em atualizar!");
        return true;
    }


    public List<Funcionario> listar() {
        return repo.buscarTodos();
    }

    public void limpar() {
        repo.salvarTodos(new ArrayList<>());
    }

}
