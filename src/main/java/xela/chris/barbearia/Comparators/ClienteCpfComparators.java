package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.models.Cliente;

import java.util.Comparator;

/**
 * Compara dois objetos {@link Cliente} com base em suas strings de CPF
 * para fins de ordenação.
 *
 * A comparação trata objetos {@code Cliente} nulos, colocando-os antes
 * dos objetos não nulos (um {@code null} é "menor" que um objeto não nulo).
 *
 * A comparação principal é feita lexicograficamente (alfabeticamente,
 * caractere por caractere) nas strings de CPF obtidas de {@code getCpf()}.
 * Esta comparação é sensível a maiúsculas e minúsculas.
 *
 * <b>Atenção:</b> Esta implementação não trata o caso em que {@code getCpf()}
 * retorna {@code null} para um objeto {@code Cliente} não nulo. Se o CPF de um
 * cliente for {@code null}, este método lançará uma {@link NullPointerException}
 * ao tentar acessar {@code cpf1.length()} ou {@code cpf1.charAt(i)}.
 */
public class ClienteCpfComparators implements Comparator<Cliente> {
    /**
     * Compara dois objetos {@link Cliente} (c1 e c2) com base em seus CPFs.
     *
     * A lógica de comparação é a seguinte:
     *
     * Se ambos os clientes forem nulos, são considerados iguais (retorna 0).
     * Se {@code c1} for nulo, é considerado "menor" (retorna -1).
     * Se {@code c2} for nulo, {@code c1} é considerado "maior" (retorna 1).
     * Se ambos não forem nulos, seus CPFs ({@code getCpf()}) são comparados
     * lexicograficamente (caractere por caractere, sensível a maiúsculas/minúsculas).
     * Se uma string de CPF for um prefixo da outra (ex: "123" e "1234"),
     * a mais curta é considerada "menor".
     *
     *
     *
     * @param c1 o primeiro objeto Cliente a ser comparado.
     * @param c2 o segundo objeto Cliente a ser comparado.
     * @return um inteiro negativo se {@code c1} for menor que {@code c2},
     * zero se forem iguais (com base no CPF),
     * ou um inteiro positivo se {@code c1} for maior que {@code c2}.
     * @throws NullPointerException se {@code c1} ou {@code c2} não for nulo,
     * mas seu respectivo método {@code getCpf()} retornar {@code null}.
     */
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
