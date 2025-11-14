package xela.chris.barbearia.Security;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.models.Funcionario;

import java.util.List;

/**
 * Implementação do padrão **Protection Proxy** responsável por controlar
 * o acesso ao sistema de login e verificação de permissões.
 *
 * <p>
 * Esta classe funciona como uma camada intermediária entre o usuário e o sistema,
 * garantindo que:
 * </p>
 *
 * <ul>
 *   <li>Somente funcionários cadastrados consigam fazer login.</li>
 *   <li>As permissões sejam verificadas antes de executar ações sensíveis.</li>
 *   <li>O sistema registre falhas e tentativas de acesso indevido.</li>
 * </ul>
 *
 * <p>
 * O Proxy utiliza o {@link GerenciadorFuncionario} para buscar funcionários
 * e validar credenciais.
 * </p>
 */
public class Proxy implements AcessoLogin {

    /** Gerenciador responsável por acessar a lista de funcionários cadastrados. */
    private GerenciadorFuncionario gf;

    /** Funcionário atualmente autenticado no sistema. */
    public Funcionario usuarioLogado;

    /**
     * Construtor que inicializa o proxy com o gerenciador de funcionários.
     *
     * @param gerenciadorFuncionario instância usada para validar usuários no login
     */
    public Proxy(GerenciadorFuncionario gerenciadorFuncionario) {
        this.gf = gerenciadorFuncionario;
    }

    /**
     * Realiza a tentativa de login verificando usuário e senha.
     *
     * @param usuario nome de usuário digitado
     * @param senha   senha fornecida
     * @return {@code true} se as credenciais estiverem corretas, {@code false} caso contrário
     */
    @Override
    public boolean login(String usuario, String senha) {
        if (usuario == null || senha == null) {
            System.out.println("Usuario/Senha inválidos");
            return false;
        }

        List<Funcionario> funcionarios = gf.listarFuncionarios();
        for (Funcionario f : funcionarios) {
            if (usuario.equals(f.getUsuario()) && senha.equals(f.getSenha())) {
                usuarioLogado = f;
                System.out.println("Login: " + usuario + " (" + f.getCargo() + ")");
                return true;
            }
        }
        System.out.println("Falha no Login! Tente Novamente.");
        return false;
    }

    /**
     * Verifica se o usuário logado possui a permissão necessária para executar determinada ação.
     *
     * @param permissao permissão exigida
     * @return {@code true} se o usuário possuir a permissão; {@code false} caso contrário
     */
    @Override
    public boolean temPermissao(PermissoesEnum permissao) {
        if (usuarioLogado == null) {
            System.out.println("Voce nao esta logado. Faca o Login primeiro.");
            return false;
        }

        boolean permitido = usuarioLogado.temPermissao(permissao);

        if (!permitido) {
            System.out.println("Usuario:" + usuarioLogado.getUsuario() + " não tem permissão para: " + permissao);
        }

        return permitido;
    }

    /**
     * Retorna o funcionário associado à autenticação.
     * <p>
     * Observação: nesta implementação, o método retorna sempre {@code null},
     * devendo ser ajustado caso seja necessário fornecer esse dado diretamente.
     * </p>
     *
     * @return {@code null} (comportamento atual)
     */
    @Override
    public Funcionario getFuncionario() {
        return null;
    }

    /**
     * Retorna o funcionário atualmente logado no sistema.
     *
     * @return funcionário logado ou {@code null} se ninguém estiver autenticado
     */
    @Override
    public Funcionario getUsuarioLogado() {
        return usuarioLogado;
    }
}
