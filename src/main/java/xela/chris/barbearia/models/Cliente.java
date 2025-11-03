package xela.chris.barbearia.models;

import xela.chris.barbearia.enums.StatusAtendimentoCliente;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Representa um cliente da barbearia.
 *
 * <p>
 * A classe {@code Cliente} herda de {@link Pessoa} e adiciona informações
 * específicas de clientes, como o status do atendimento e um identificador
 * único gerado automaticamente.
 * </p>
 *
 * <p>
 * O ID é gerado de forma sequencial usando um contador estático
 * ({@link AtomicInteger}), garantindo que cada cliente possua um número único.
 * </p>
 */
public class Cliente extends Pessoa {

    private static final AtomicInteger contador = new AtomicInteger(0);
    private int id;
    private StatusAtendimentoCliente statusAtendimentoCliente;

    /**
     * Construtor padrão da classe {@code Cliente}.
     * <p>
     * É necessário definir manualmente os atributos posteriormente.
     * </p>
     */
    public Cliente() {
    }

    /**
     * Construtor completo da classe {@code Cliente}.
     *
     * <p>
     * O ID é gerado automaticamente ao criar uma nova instância.
     * </p>
     *
     * @param nome                     o nome completo do cliente
     * @param cpf                      o CPF do cliente
     * @param telefone                 o número de telefone do cliente
     * @param statusAtendimentoCliente o status atual do atendimento
     */
    public Cliente(String nome, String cpf, String telefone, StatusAtendimentoCliente statusAtendimentoCliente) {
        super(nome, cpf, telefone);
        this.id = contador.incrementAndGet();
        this.statusAtendimentoCliente = statusAtendimentoCliente;
    }

    /**
     * Retorna o status atual do atendimento do cliente.
     *
     * @return o status do atendimento
     */
    public StatusAtendimentoCliente getStatusAtendimentoCliente() {
        return statusAtendimentoCliente;
    }

    /**
     * Define o status atual do atendimento do cliente.
     *
     * @param statusAtendimentoCliente o novo status de atendimento
     */
    public void setStatusAtendimentoCliente(StatusAtendimentoCliente statusAtendimentoCliente) {
        this.statusAtendimentoCliente = statusAtendimentoCliente;
    }

    /**
     * Retorna o identificador único do cliente.
     *
     * @return o ID do cliente
     */
    public int getId() {
        return id;
    }

    /**
     * Define manualmente o identificador do cliente.
     *
     * <p>
     * Este método deve ser usado com cautela, pois o ID normalmente
     * é gerado automaticamente.
     * </p>
     *
     * @param id o novo identificador do cliente
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Atualiza o contador de IDs para garantir que os próximos registros
     * continuem a sequência a partir do último ID existente.
     *
     * @param ultimoId maior ID encontrado ao carregar os dados
     */
    public static void atualizarContador(int ultimoId) {
        contador.set(ultimoId);
    }

    /**
     * Retorna uma representação textual do cliente.
     *
     * <p>
     * Inclui o ID, nome, CPF anonimizado, telefone formatado e status.
     * </p>
     *
     * @return uma string contendo as informações principais do cliente
     */
    @Override
    public String toString() {
        return "\n===============" +
                "\n ID: " + getId() +
                "\n Nome: " + getNome() +
                "\n CPF: " + cpfPseudoAnonimizado() +
                "\n Telefone: " + telefoneCorreto() +
                "\n Status: " + statusAtendimentoCliente +
                "\n";
    }
}
