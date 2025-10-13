package xela.chris.barbearia;

import xela.chris.barbearia.Gerenciadores.GerenciadorLogin;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.models.Funcionario;
import xela.chris.barbearia.Gerenciadores.RepositorioJson;
import xela.chris.barbearia.security.TokenService;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de teste para o sistema de login da barbearia.
 * <p>
 * Esta classe realiza as seguintes operações:
 * <ul>
 *     <li>Cria um repositório JSON de funcionários;</li>
 *     <li>Adiciona um funcionário de teste ao repositório;</li>
 *     <li>Autentica o funcionário usando login e senha;</li>
 *     <li>Gera um token JWT caso o login seja bem-sucedido;</li>
 *     <li>Verifica se o token é válido;</li>
 *     <li>Exibe as permissões associadas ao token.</li>
 * </ul>
 * </p>
 * <p>
 * Esta classe é utilizada apenas para testes e demonstração do fluxo de login e
 * gerenciamento de tokens.
 * </p>
 */
public class TestLogin {

    /**
     * Método principal que executa o teste de login.
     * <p>
     * O método realiza os seguintes passos:
     * <ol>
     *     <li>Cria um repositório JSON de funcionários;</li>
     *     <li>Adiciona um funcionário de teste (com permissões de cadastro de cliente);</li>
     *     <li>Tenta autenticar o funcionário usando login e senha;</li>
     *     <li>Exibe se o login foi bem-sucedido ou não;</li>
     *     <li>Se bem-sucedido, gera e valida o token, mostrando suas permissões.</li>
     * </ol>
     * </p>
     *
     * @param args argumentos da linha de comando (não utilizados neste teste)
     */
    public static void main(String[] args) {
        // Criar repositório de funcionários (salva em JSON)
        RepositorioJson<Funcionario> repo = new RepositorioJson<>(Funcionario.class, "funcionarios.json");

        // Adicionar funcionário teste (só precisa fazer uma vez)
        List<Funcionario> lista = new ArrayList<>();
        Funcionario funcionario1 = new Funcionario(
                "Christian",
                "17600724600",
                "33998642761",
                "Barbeiro",
                "chris",
                "1234",
                List.of(PermissoesEnum.CADASTRAR_CLIENTE, PermissoesEnum.CAD_FUNC)
        );
        lista.add(funcionario1);
        repo.salvarTodos(lista);

        // Testar login
        GerenciadorLogin login = new GerenciadorLogin();
        String token = login.autenticar("chris", "1234");

        if (token != null) {
            System.out.println("✅ Login bem-sucedido!");
            System.out.println("Token gerado: " + token);

            // Testar se o token é válido
            boolean valido = TokenService.tokenValido(token);
            System.out.println("Token é válido? " + valido);

            // Mostrar permissões
            System.out.println("Permissões do token: " + TokenService.getPermissoesDoToken(token));

        } else {
            System.out.println("❌ Falha no login!");
        }
    }
}
