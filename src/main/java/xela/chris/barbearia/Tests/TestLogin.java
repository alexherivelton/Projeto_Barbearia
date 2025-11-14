package xela.chris.barbearia.Tests;

import xela.chris.barbearia.FacadeMediator.AgendamentoFacade;
import xela.chris.barbearia.Gerenciadores.*;
import xela.chris.barbearia.Security.Proxy;
import xela.chris.barbearia.enums.PermissoesEnum;
import xela.chris.barbearia.models.Funcionario;
import java.util.Scanner;

public class TestLogin {
    public static void main(String[] args) {

        AgendamentoFacade ag = new AgendamentoFacade();
        GerenciadorFuncionario gf = new GerenciadorFuncionario();
        Proxy acesso = new Proxy(gf);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BARBEARIA - LOGIN ===");

        System.out.print("Usuário: ");
        String user = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (acesso.login(user, senha)) {
            Funcionario logado = acesso.getUsuarioLogado();
            System.out.println("Bem-vindo, " + logado.getNome() + "!");

            System.out.println("\n--- TESTANDO PERMISSÕES ---");

            if (acesso.temPermissao(PermissoesEnum.CAD_FUNC)) {
                System.out.println("Pode cadastrar funcionários");
            }

            if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) {
                System.out.println("Pode criar agendamentos");
            }

            if (acesso.temPermissao(PermissoesEnum.GERAR_BALANCO_MENSAL)) {
                System.out.println("Pode gerar balanço");
            }

            GerenciarAgendamento agendamentos = new GerenciarAgendamento();

            if (acesso.temPermissao(PermissoesEnum.CRIAR_AGENDAMENTO)) {
                System.out.println("\nCriando agendamento...");
                   ag.criarAgendamento(1,3,2, "15/10/25 - 15:30");
            } else {
                System.out.println("\nNão pode criar agendamentos");
            }

        } else {
            System.out.println("Login falhou!");
        }

        scanner.close();
    }
}