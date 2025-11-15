package xela.chris.barbearia.FilaDeEspera;

import xela.chris.barbearia.Gerenciadores.RepositorioJson;
import xela.chris.barbearia.models.Cliente;

import java.util.ArrayList;
import java.util.List;

public class GerenciaFilaDeEspera {
    private RepositorioJson<FilaDeEspera> repo = new RepositorioJson<>(FilaDeEspera.class, "filaDeEspera.json");
    private List<FilaDeEspera> listaDeEspera = new ArrayList<>();

    public GerenciaFilaDeEspera(){
        this.listaDeEspera = new ArrayList<FilaDeEspera>();
        this.carregar();
    }

    public void carregar() {
        listaDeEspera = repo.buscarTodos();
    }

    public void salvarFilaDeEspera(){
        repo.salvarTodos(listaDeEspera);
    }

    public void adicionarClienteNaFila(FilaDeEspera fila){
        listaDeEspera.add(fila);
    }


    public FilaDeEspera buscarComFIFONaLista(){
        if (listaDeEspera.isEmpty()) {
            System.out.println("A fila está vazia!");
            return null;
        }

        return listaDeEspera.remove(0);
    }

    public List<FilaDeEspera> getFilaDeEspera() {
        return listaDeEspera;
    }

}
