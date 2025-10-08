package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.PermissoesEnum;

import java.util.Arrays;
import java.util.List;

/**
 * Representa um administrador do sistema.
 * <p>
 * A classe {@code Administrator} herda de {@link Funcionario} e possui todas as permissões disponíveis no sistema.
 * </p>
 * <p>
 * Um administrador pode executar qualquer ação sem restrições.
 * </p>
 */
public class Administrador extends Funcionario {

    /**
     * Retorna todas as permissões disponíveis no sistema.
     *
     * @return uma lista contendo todos os valores do enum {@link PermissoesEnum}
     */
    @Override
    public List<PermissoesEnum> getPermissoes() {
        return Arrays.asList(PermissoesEnum.values());
    }


    /**
     * Verifica se o administrador possui uma determinada permissão.
     * <p>
     * Para a classe {@code Administrator}, este método sempre retorna {@code true},
     * pois administradores possuem todas as permissões.
     * </p>
     *
     * @param permissao a permissão a ser verificada
     * @return {@code true} sempre, já que administradores possuem todas as permissões
     */
    public boolean temPermissao(PermissoesEnum permissao) {
        return true;
    }
}
