package xela.chris.barbearia;

import xela.chris.barbearia.models.Funcionario;

public class Main {
    public static void main(String[] args) {

        Funcionario cliente1 = new Funcionario();
        cliente1.setNome("Joao");

        System.out.println(cliente1.getNome());

    }
}