package xela.chris.barbearia.Comparators;

import xela.chris.barbearia.models.Cliente;
import java.util.Comparator;

/**
 * Compara dois objetos {@link Cliente} com base em seus nomes para fins de ordenação.
 *
 * A comparação trata objetos {@code Cliente} nulos, colocando-os antes
 * dos objetos não nulos (um {@code null} é "menor" que um objeto não nulo).
 *
 * A comparação principal é feita lexicograficamente (alfabeticamente)
 * nos nomes obtidos de {@code getNome()}, após convertê-los para minúsculas
 * (ignorando a diferença entre maiúsculas e minúsculas).
 *
 * <b>Atenção:</b> Esta implementação não trata o caso em que {@code getNome()}
 * retorna {@code null} para um objeto {@code Cliente} não nulo. Se o nome de um
 * cliente for {@code null}, este método lançará uma {@link NullPointerException}
 * ao tentar chamar {@code .toLowerCase()}.
 */
public class ClienteNomeComparators implements Comparator<Cliente> {

    /**
     * Compara dois objetos {@link Cliente} (c1 e c2) com base em seus nomes.
     *
     * A lógica de comparação é a seguinte:
     *
     * Se ambos os clientes forem nulos, são considerados iguais (retorna 0).
     * Se {@code c1} for nulo, é considerado "menor" (retorna -1).
     * Se {@code c2} for nulo, {@code c1} é considerado "maior" (retorna 1).
     * Se ambos não forem nulos, seus nomes ({@code getNome()}) são convertidos
     * para minúsculas e comparados lexicograficamente (caractere por caractere).
     * Se uma string de nome for um prefixo da outra (ex: "Ana" e "Ana Maria"),
     * a mais curta é considerada "menor".
     *
     *
     *
     * @param c1 o primeiro objeto Cliente a ser comparado.
     * @param c2 o segundo objeto Cliente a ser comparado.
     * @return um inteiro negativo se {@code c1} for menor que {@code c2},
     * zero se forem iguais (com base no nome),
     * ou um inteiro positivo se {@code c1} for maior que {@code c2}.
     * @throws NullPointerException se {@code c1} ou {@code c2} não for nulo,
     * mas seu respectivo método {@code getNome()} retornar {@code null}.
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