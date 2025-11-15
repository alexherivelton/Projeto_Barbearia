package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um serviço oferecido pela barbearia, como corte de cabelo,
 * barba, sobrancelha ou outros atendimentos. Cada serviço possui um ID
 * gerado automaticamente, além de nome, preço, descrição e uma flag indicando
 * se utiliza ou não os recursos de lavagem/secagem.
 */
public class Servico {

    /** Contador usado para geração automática e thread-safe de IDs. */
    private static final AtomicInteger contador = new AtomicInteger(0);

    /** 
     * Questão 11: Contador de instâncias usando encapsulamento (private static com get/set).
     * Vantagens: Controle total sobre acesso, validação possível, melhor para manutenção.
     * Desvantagens: Mais verboso, requer métodos get/set.
     */
    private static int contadorInstanciasEncapsulado = 0;

    /**
     * Questão 12: Contador de instâncias usando protected static.
     * Vantagens: Acesso direto para subclasses, mais simples.
     * Desvantagens: Menos controle, pode ser modificado diretamente por subclasses.
     */
    protected static int contadorInstanciasProtected = 0;

    /** Identificador único do serviço. */
    private int id;

    /** Nome do serviço (ex.: "Corte", "Barba", "Corte + Barba"). */
    private String nome;

    /** Preço do serviço em reais. */
    private double preco;

    /** Indica se o serviço utiliza o processo de lavagem e secagem. */
    private boolean utilizaLavagemSecagem = false;

    /** Descrição detalhada do serviço. */
    private String descricao;

    /**
     * Construtor padrão requerido pelo Jackson para operações de desserialização.
     * Não inicializa ID automaticamente.
     */
    public Servico() {
    }

    /**
     * Construtor usado para criar um novo serviço manualmente.
     *
     * @param nome nome do serviço
     * @param preco valor cobrado pelo serviço
     * @param utilizaLavagemSecagem {@code true} se o serviço exigir lavagem/secagem
     * @param descricao texto explicativo ou informativo sobre o serviço
     */
    public Servico(String nome, double preco, boolean utilizaLavagemSecagem, String descricao) {
        this.id = contador.incrementAndGet();
        this.nome = nome;
        this.preco = preco;
        this.utilizaLavagemSecagem = utilizaLavagemSecagem;
        this.descricao = descricao;
        
        // Incrementa os contadores de instâncias (Questões 11 e 12)
        contadorInstanciasEncapsulado++;
        contadorInstanciasProtected++;
    }

    /**
     * Verifica se o serviço utiliza lavagem e secagem.
     *
     * @return {@code true} se utiliza, {@code false} caso contrário
     */
    public boolean isUtilizaLavagemSecagem() {
        return utilizaLavagemSecagem;
    }

    /**
     * Define se o serviço utiliza lavagem e secagem.
     *
     * @param utilizaLavagemSecagem valor booleano indicando o uso
     */
    public void setUtilizaLavagemSecagem(boolean utilizaLavagemSecagem) {
        this.utilizaLavagemSecagem = utilizaLavagemSecagem;
    }

    /**
     * Retorna o ID do serviço.
     *
     * @return identificador único
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente o ID do serviço.
     *
     * @param id valor a ser atribuído
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o nome do serviço.
     *
     * @return nome do serviço
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do serviço.
     *
     * @param nome texto com o nome desejado
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna o preço do serviço.
     *
     * @return valor em reais
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Atualiza o preço do serviço.
     *
     * @param preco novo valor
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Retorna a descrição do serviço.
     *
     * @return texto descritivo
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do serviço.
     *
     * @param descricao texto detalhado
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Atualiza o contador de IDs para manter consistência ao carregar
     * serviços já existentes do armazenamento.
     *
     * @param ultimoId último ID utilizado
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Questão 11: Getter para contador encapsulado.
     * @return número de instâncias criadas
     */
    public static int getContadorInstanciasEncapsulado() {
        return contadorInstanciasEncapsulado;
    }

    /**
     * Questão 11: Setter para contador encapsulado.
     * @param valor novo valor do contador
     */
    public static void setContadorInstanciasEncapsulado(int valor) {
        contadorInstanciasEncapsulado = valor;
    }

    /**
     * Questão 12: Getter para contador protected (para demonstração).
     * @return número de instâncias criadas usando protected
     */
    public static int getContadorInstanciasProtected() {
        return contadorInstanciasProtected;
    }

    /**
     * Questão 12: Setter para contador protected (para demonstração).
     * @param valor novo valor do contador
     */
    public static void setContadorInstanciasProtected(int valor) {
        contadorInstanciasProtected = valor;
    }

    /**
     * Retorna uma representação textual formatada do serviço.
     *
     * @return string contendo os dados do serviço
     */
    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + id +
                "\n Nome: " + nome +
                "\n Preço: R$ " + preco +
                "\n Descrição: " + descricao +
                "\n===============";
    }
}
