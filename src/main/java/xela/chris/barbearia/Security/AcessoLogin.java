package xela.chris.barbearia.Security;

import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.models.Funcionario;

/**
 * Interface responsável por definir o contrato para sistemas de autenticação e autorização
 * dentro da aplicação da barbearia.
 *
 * <p>Ela estabelece os métodos essenciais para:</p>
 * <ul>
 *     <li>Validar o login de um funcionário.</li>
 *     <li>Verificar permissões baseadas no cargo/perfil.</li>
 *     <li>Obter o funcionário atualmente logado no sistema.</li>
 * </ul>
 *
 * <p>Classes que implementarem esta interface devem garantir a segurança dos dados
 * de login e controle adequado de privilégios.</p>
 */
public interface AcessoLogin {

    /**
     * Realiza a tentativa de login com as credenciais fornecidas.
     *
     * @param usuario nome de usuário digitado
     * @param senha   senha correspondente
     * @return {@code true} se o login for bem-sucedido, {@code false} caso contrário
     */
    boolean login(String usuario, String senha);

    /**
     * Verifica se o usuário atualmente logado possui a permissão especificada.
     *
     * @param permissao permissão requerida
     * @return {@code true} se o usuário tiver a permissão, {@code false} caso contrário
     */
    boolean temPermissao(PermissoesEnum permissao);

    /**
     * Retorna o funcionário associado ao processo de autenticação atual.
     * Pode ser usado para recuperar dados do funcionário após o login.
     *
     * @return objeto {@link Funcionario} autenticado ou {@code null} se não houver autenticação
     */
    Funcionario getFuncionario();

    /**
     * Retorna o funcionário que está logado no momento.
     * Em implementações típicas, corresponde ao mesmo retorno de {@link #getFuncionario()}.
     *
     * @return funcionário logado ou {@code null} se ninguém estiver logado
     */
    Funcionario getUsuarioLogado();
}
