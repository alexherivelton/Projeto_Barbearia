package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.Comparator;

/**
 * Compara dois objetos {@link Agendamento} com base no nome do **primeiro serviço**
 * listado em cada agendamento.
 *
 * <p>Este comparador é projetado para ordenar agendamentos alfabeticamente
 * com base no nome do primeiro serviço ({@code getServicos().get(0).getNome()}).
 *
 * A lógica de comparação trata extensivamente casos nulos e listas vazias
 * para evitar {@link NullPointerException} e garantir uma ordenação consistente,
 * priorizando valores nulos ou vazios (colocando-os "antes" na ordenação):
 *
 * Compara a nulidade dos próprios objetos {@code Agendamento}.
 * Compara a nulidade da lista de serviços ({@code getServicos()}).
 * Compara se a lista de serviços está vazia ({@code isEmpty()}).
 * Se ambos os agendamentos possuírem pelo menos um serviço, compara
 * o nome do primeiro serviço ({@code get(0).getNome()}) de forma lexicográfica
 * (alfabética), ignorando maiúsculas e minúsculas.
 *
 *
 */
public class AgendamentoServicoComparators implements Comparator<Agendamento> {

    /**
     * Compara dois agendamentos (a1 e a2) com base no nome do primeiro serviço de cada um.
     *
     * @param a1 o primeiro objeto Agendamento a ser comparado.
     * @param a2 o segundo objeto Agendamento a ser comparado.
     * @return um inteiro negativo se {@code a1} for considerado "menor" que {@code a2},
     * zero se forem considerados "iguais" (com base nos critérios),
     * ou um inteiro positivo se {@code a1} for considerado "maior" que {@code a2}.
     */
    @Override
    public int compare(Agendamento a1, Agendamento a2) {
        if (a1 == null && a2 == null) return 0;
        if (a1 == null) return -1;
        if (a2 == null) return 1;


        if (a1.getServicos() == null && a2.getServicos() == null) return 0;
        if (a1.getServicos() == null) return -1;
        if (a2.getServicos() == null) return 1;


        if (a1.getServicos().isEmpty() && a2.getServicos().isEmpty()) return 0;
        if (a1.getServicos().isEmpty()) return -1;
        if (a2.getServicos().isEmpty()) return 1;


        String servico1 = a1.getServicos().get(0).getNome().toLowerCase();
        String servico2 = a2.getServicos().get(0).getNome().toLowerCase();

        int len = Math.min(servico1.length(), servico2.length());

        for (int i = 0; i < len; i++) {
            char ch1 = servico1.charAt(i);
            char ch2 = servico2.charAt(i);

            if (ch1 != ch2) {
                return ch1 - ch2;
            }
        }

        return servico1.length() - servico2.length();
    }
}
