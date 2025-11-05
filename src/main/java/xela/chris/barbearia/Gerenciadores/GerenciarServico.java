package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Servico;

import java.util.ArrayList;
import java.util.List;

public class GerenciarServico {
    private List<Servico> servicos = new ArrayList<>();
    private RepositorioJson<Servico> repo = new RepositorioJson<>(Servico.class, "servicos.json");

    public void carregarServicos(){

    }
}


