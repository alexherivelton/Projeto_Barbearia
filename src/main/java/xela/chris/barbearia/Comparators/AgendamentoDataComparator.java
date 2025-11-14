package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.negocio.Agendamento;
import java.util.Comparator;

public class AgendamentoDataComparator implements Comparator<Agendamento> {

    @Override
    public int compare(Agendamento a1, Agendamento a2) {
        if (a1 == null && a2 == null) {
            return 0;
        }
        if (a1 == null) {
            return -1;
        }
        if (a2 == null) {
            return 1;
        }

        String data1 = a1.getDataHora().toLowerCase();
        String data2 = a2.getDataHora().toLowerCase();

        int len = Math.min(data1.length(), data2.length());

        for (int i = 0; i < len; i++) {
            char ch1 = data1.charAt(i);
            char ch2 = data2.charAt(i);

            if (ch1 != ch2) {
                return ch1 - ch2;
            }
        }

        return data1.length() - data2.length();
    }
}