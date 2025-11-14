package xela.chris.barbearia.Security;

import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.models.Funcionario;

public interface AcessoLogin {
    boolean login(String usuario, String senha);
    boolean temPermissao(PermissoesEnum permissao);

    Funcionario getFuncionario();
    Funcionario getUsuarioLogado();
}
