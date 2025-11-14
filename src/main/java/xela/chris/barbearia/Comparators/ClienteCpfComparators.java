package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.models.Cliente;

import java.util.Comparator;

public class ClienteCpfComparators implements Comparator<Cliente> {
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

        String cpf1 = c1.getCpf();  // ← Mais descritivo
        String cpf2 = c2.getCpf();  // ← Mais descritivo

        int len = Math.min(cpf1.length(), cpf2.length());

        for (int i = 0; i < len; i++) {
            char ch1 = cpf1.charAt(i);
            char ch2 = cpf2.charAt(i);

            if (ch1 != ch2) {
                return ch1 - ch2;
            }
        }

        return cpf1.length() - cpf2.length();
    }
}
