package xela.chris.barbearia.enums;

public enum ServicosEnum {
    CORTE("Corte de cabelo", 30.0, "Corte moderno ou tradicional, feito com tesoura ou máquina, conforme o estilo do cliente."),
    BARBA("Barba tradicional", 25.0, "Barbear completo com toalha quente, alinhamento e finalização com loção refrescante."),
    SOBRANCELHA("Design de sobrancelha", 15.0, "Modelagem leve e precisa, mantendo o formato natural e harmônico do rosto."),
    CORTE_E_BARBA("Corte + Barba", 50.0, "Pacote completo com corte de cabelo e barba, ideal para renovar o visual com estilo.");


    private final String nome;
    private final double preco;
    private final String descricao;

    ServicosEnum(String nome, double preco, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }
}
