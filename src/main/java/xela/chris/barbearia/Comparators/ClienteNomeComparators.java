package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.models.Cliente;
import java.util.Comparator;

public class ClienteNomeComparators implements Comparator<Cliente> {

    @Override
    public int compare(Cliente c1, Cliente c2) {
        if (c1 == null && c2 == null) {
            return 0;
        }
        if (c1 == null) {
            return -1;
        }
        if (c2 == null) {
            return 1;
        }

        String n1 = c1.getNome().toLowerCase();
        String n2 = c2.getNome().toLowerCase();

        int len = Math.min(n1.length(), n2.length());

        for (int i = 0; i < len; i++) {
            char ch1 = n1.charAt(i);
            char ch2 = n2.charAt(i);

            if (ch1 != ch2) {
                return ch1 - ch2;
            }
        }

        return n1.length() - n2.length();
    }
}