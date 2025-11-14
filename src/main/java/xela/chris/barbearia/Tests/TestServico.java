package xela.chris.barbearia.Tests;

import xela.chris.barbearia.Gerenciadores.GerenciarServico;
import xela.chris.barbearia.models.Servico;

public class TestServico {
    public static void main(String[] args) {

        GerenciarServico gs = new GerenciarServico();

        System.out.println("=== TESTE DE GERENCIAMENTO DE SERVIÇOS ===");

        // 🔹 1. Carregar serviços do JSON
        System.out.println("\n➡️ Carregando serviços...");
        gs.carregar();
        // 🔹 2. Adicionar novos serviços
//        System.out.println("\n➡️ Adicionando novos serviços...");
        gs.adicionar(new Servico("Corte de cabelo", 30.0,true ,"Corte masculino com finalização"));
        gs.adicionar(new Servico("Barba tradicional", 25.0, false ,"Barba feita com toalha quente e navalha"));
        gs.adicionar(new Servico("Sobrancelha", 15.0, true ,"Design de sobrancelha masculino"));
        gs.adicionar(new Servico("Corte + Barba", 50.0,true ,"Pacote completo: corte e barba"));

        gs.salvarTodosProdutos();


        // 🔹 3. Listar todos os serviços
//        System.out.println("\n➡️ Listando serviços...");
//        gs.listar();

        // 🔹 4. Buscar um serviço específico pelo ID
//        System.out.println("\n➡️ Buscando serviço com ID 2...");
//        System.out.println(gs.buscarPorId(2));

//        // 🔹 5. Atualizar um serviço existente
//        System.out.println("\n➡️ Atualizando serviço com ID 2...");
//        boolean atualizado = gs.atualizar(2, "Barba Premium", 35.0, "Barba com hidratação e massagem facial");
//        System.out.println("Atualização realizada: " + atualizado);

//        // 🔹 6. Listar novamente para verificar a atualização
//        System.out.println("\n➡️ Listando serviços após atualização...");
        gs.listar();

//        // 🔹 7. Remover um serviço
//        System.out.println("\n➡️ Removendo serviço com ID 1...");
//        boolean removido = gs.removerPorId(1);
//        System.out.println("Serviço removido: " + removido);

//        // 🔹 8. Listar novamente para verificar a remoção
//        System.out.println("\n➡️ Listando serviços após remoção...");
//        gs.listar();


        System.out.println("\n=== FIM DO TESTE ===");
    }
}
