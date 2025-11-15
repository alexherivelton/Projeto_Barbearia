package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.negocio.Agendamento;
import java.util.Comparator;

/**
 * Compara dois objetos {@link Agendamento} com base em suas strings de data e hora
 * ({@code dataHora}) para fins de ordenação.
 *
 * A comparação trata casos nulos, colocando-os antes dos objetos não nulos.
 * A comparação principal é feita lexicograficamente (alfabeticamente)
 * nas strings {@code dataHora}, convertidas para minúsculas para ignorar
 * a diferença entre maiúsculas e minúsculas. Se uma string for um prefixo
 * da outra, a string mais curta é considerada "menor".
 */
public class AgendamentoDataComparator implements Comparator<Agendamento> {

    /**
     * Compara dois objetos {@link Agendamento} com base em seus atributos {@code dataHora}.
     *
     * A lógica de comparação é a seguinte:
     *
     * Se ambos os agendamentos forem nulos, eles são considerados iguais (retorna 0).
     * Se {@code a1} for nulo, ele é considerado "menor" que {@code a2} (retorna -1).
     * Se {@code a2} for nulo, {@code a1} é considerado "maior" (retorna 1).
     * Se ambos não forem nulos, suas strings {@code dataHora} são comparadas
     * lexicograficamente, caractere por caractere, após conversão para minúsculas.
     * Se as strings diferirem, o resultado da subtração dos primeiros caracteres
     * diferentes é retornado.
     * Se uma string for um prefixo da outra (ex: "abc" e "abcd"), a diferença
     * em seus comprimentos é retornada (a mais curta vem primeiro).
     *
     *
     *
     * @param a1 o primeiro objeto Agendamento a ser comparado.
     * @param a2 o segundo objeto Agendamento a ser comparado.
     * @return um inteiro negativo se {@code a1} for menor que {@code a2},
     * zero se forem iguais (com base na {@code dataHora}),
     * ou um inteiro positivo se {@code a1} for maior que {@code a2}.
     */
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