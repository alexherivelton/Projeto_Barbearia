package xela.chris.barbearia.models;

import java.util.concurrent.atomic.AtomicInteger;

import xela.chris.barbearia.enums.TipoCadeira;

/**
 * Representa uma cadeira da barbearia, podendo ser utilizada em diferentes
 * tipos de atendimento, conforme definido pelo {@link TipoCadeira}.
 *
 * Cada cadeira recebe automaticamente um ID único baseado em um contador interno.
 */
public class Cadeira {

    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private String nome;
    private TipoCadeira tipo;

    /**
     * Construtor padrão.
     */
    public Cadeira() {}

    /**
     * Construtor que cria uma nova cadeira já com ID gerado automaticamente.
     *
     * @param nome nome da cadeira (ex.: "Cadeira 01", "Barbearia Aço")
     * @param tipo tipo da cadeira (ex.: BARBEARIA, ESPERA)
     */
    public Cadeira(String nome, TipoCadeira tipo) {
        this.id = contador.incrementAndGet();
        this.nome = nome;
        this.tipo = tipo;
    }

    /**
     * Retorna o ID único da cadeira.
     *
     * @return ID da cadeira
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome da cadeira.
     *
     * @return nome da cadeira
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o tipo da cadeira.
     *
     * @return tipo da cadeira
     */
    public TipoCadeira getTipo() {
        return tipo;
    }

    /**
     * Atualiza o contador interno de IDs.
     * Usado ao carregar dados persistidos para manter a sequência correta.
     *
     * @param ultimoId maior ID encontrado nos dados já existentes
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Retorna uma representação textual da cadeira.
     *
     * @return string com informações básicas da cadeira
     */
    @Override
    public String toString() {
        return "Cadeira{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
