package xela.chris.barbearia.Gerenciadores;

import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.models.RegistroPonto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GerenciadorPonto {

    private List<RegistroPonto> pontos = new ArrayList<>();
    private final RepositorioJson<RegistroPonto> repo = new RepositorioJson<>(RegistroPonto.class, "pontos.json");

    public GerenciadorPonto() {
        this.carregar();
    }

    public void carregar() {
        pontos = repo.buscarTodos();
        if (pontos == null) {
            pontos = new ArrayList<>();
        }
    }

    public synchronized void baterPonto(Funcionario funcionario) {
        String dataAtual = LocalDate.now().toString();
        List<RegistroPonto> registros = repo.listar();

        RegistroPonto pontoHoje = null;
        for(RegistroPonto p : registros){
            if (p.getIdFuncionario() != null
                    && p.getIdFuncionario().getId() == funcionario.getId()
                    && dataAtual.equals(p.getData())) {
                pontoHoje = p;
                break;
            }

        }

        if(pontoHoje == null){
            RegistroPonto novoRegistro = new RegistroPonto();
            novoRegistro.setIdFuncionario(funcionario);
            novoRegistro.setData(dataAtual);
            novoRegistro.setHoraEntrada(LocalTime.now().withNano(0).toString());
            registros.add(novoRegistro);
            System.out.println("Ponto registrado com sucesso! " + funcionario.getNome() + " no horario: " + novoRegistro.getHoraEntrada());
        } else if(pontoHoje.getHoraSaida() == null){
            pontoHoje.setHoraSaida(LocalTime.now().withNano(0).toString());
            System.out.println("Registro de saída salvo com sucesso!" + funcionario.getNome() + " no horario: " + pontoHoje.getHoraSaida());
        } else {
            System.out.println("O funcionario com o nome: " + funcionario.getNome() + ", ja registrou a entrada e saída de hoje.");
        }

        repo.salvarTodos(registros);
        pontos = registros;
    }

    public RegistroPonto buscarPorData(Funcionario funcionario, String data) {
        pontos = repo.buscarTodos();
        if (pontos == null) {
            System.out.println("Nenhum ponto foi encontrado no arquivo!");
            return null;
        }

        for (RegistroPonto r : pontos) {
            if (r.getIdFuncionario() != null &&
                    r.getIdFuncionario().getId() == funcionario.getId() &&
                    data.equals(r.getData())) {
                System.out.println("Ponto encontrado: " + funcionario.getNome() +
                        " em " + data +
                        " - Entrada: " + r.getHoraEntrada() +
                        ", Saída: " + r.getHoraSaida());
                return r;
            }
        }

        System.out.println("Nenhum ponto encontrado para " + funcionario.getNome() + " em " + data);
        return null;
    }


    public void limpar() {
        pontos = new ArrayList<>();
        repo.salvarTodos(new ArrayList<>());
    }
}
