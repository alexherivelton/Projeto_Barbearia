package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.enums.TipoCadeira;
import xela.chris.barbearia.models.Cadeira;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciarCadeira {

    private List<Cadeira> cadeiras = new ArrayList<>();
    private RepositorioJson<Cadeira> repo = new RepositorioJson<>(Cadeira.class, "cadeiras.json");

    public GerenciarCadeira() {
        carregar();
        if (cadeiras.isEmpty()) {
            inicializarCadeiras();
            salvar();
        }
    }

    private void inicializarCadeiras() {
        // 1 para lavar e secar
        cadeiras.add(new Cadeira("Cadeira Lavagem 1", TipoCadeira.LAVAR_SECAR));
        // 2 para atividades corriqueiras
        cadeiras.add(new Cadeira("Cadeira Serviço 1", TipoCadeira.SERVICO_CORRIQUEIRO));
        cadeiras.add(new Cadeira("Cadeira Serviço 2", TipoCadeira.SERVICO_CORRIQUEIRO));
    }

    public void carregar() {
        cadeiras = repo.buscarTodos();
        if (!cadeiras.isEmpty()) {
            int maiorId = cadeiras.stream()
                    .mapToInt(Cadeira::getId)
                    .max()
                    .orElse(0);
            Cadeira.atualizarContador(maiorId);
        }
    }

    public void salvar() {
        repo.salvarTodos(cadeiras);
    }

    public List<Cadeira> listarCadeiras() {
        return cadeiras;
    }

    public Cadeira buscarPorId(int id) {
        return cadeiras.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Cadeira> buscarPorTipo(TipoCadeira tipo) {
        return cadeiras.stream()
                .filter(c -> c.getTipo() == tipo)
                .collect(Collectors.toList());
    }
}
