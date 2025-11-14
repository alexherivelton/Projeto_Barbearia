package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.negocio.Agendamento;

import java.util.Comparator;

public class AgendamentoServicoComparators implements Comparator<Agendamento> {

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
