package xela.chris.barbearia.Security;

import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.models.Funcionario;

import java.util.List;


public class Proxy implements AcessoLogin {
    private GerenciadorFuncionario gf;
    public Funcionario usuarioLogado;

    public Proxy(GerenciadorFuncionario gerenciadorFuncionario) {
        this.gf = gerenciadorFuncionario;
    }

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

    @Override
    public Funcionario getFuncionario() {
        return null;
    }

    @Override
    public Funcionario getUsuarioLogado() {
        return usuarioLogado;
    }
}
