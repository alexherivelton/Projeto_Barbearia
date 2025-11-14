package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciadorPonto;
import xela.chris.barbearia.Gerenciadores.GerenciadorFuncionario;
import xela.chris.barbearia.models.Funcionario;

public class TestPonto {
    public static void main(String[] args) {
        GerenciadorPonto gp = new GerenciadorPonto();
        GerenciadorFuncionario gf = new GerenciadorFuncionario();

//        gp.limpar();

         Funcionario f = gf.buscarFuncionario(1); // busca funcionário pelo id

        if(f != null){
            gp.baterPonto(f);
        } else{
            System.out.println("FUncionario do diabo nao encontrado!");
        }
//        gp.buscarPorData(gf.buscarFuncionario(f.getId()),"2025-11-13");
    }
}
