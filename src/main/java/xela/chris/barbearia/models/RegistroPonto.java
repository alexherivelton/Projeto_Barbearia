package xela.chris.barbearia.models;

/**
 * Representa um registro de ponto de um funcionário da barbearia.
 *
 * <p>Cada registro contém a data, horário de entrada, horário de saída
 * e a referência ao funcionário que realizou o ponto.</p>
 *
 * <p>Esta classe é utilizada para controle de jornada, histórico de presença
 * e relatórios de frequência.</p>
 */
public class RegistroPonto {

    /** Funcionário ao qual o registro de ponto pertence. */
    private Funcionario idFuncionario;

    /** Data do registro (ex.: "12/11/2025"). */
    private String data;

    /** Horário de entrada no trabalho (ex.: "08:00"). */
    private String horaEntrada;

    /** Horário de saída do trabalho (ex.: "17:30"). */
    private String horaSaida;

    /**
     * Construtor padrão.
     *
     * <p>Utilizado principalmente por frameworks de serialização.</p>
     */
    public RegistroPonto() {}

    /**
     * Construtor que cria um registro contendo a data e horário de entrada.
     *
     * @param data        data do registro
     * @param horaEntrada horário de entrada
     * @param horaSaida   horário de saída (pode ser preenchido depois)
     */
    public RegistroPonto(String data, String horaEntrada, String horaSaida) {
        this.data = data;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    /**
     * Retorna o funcionário associado ao registro de ponto.
     *
     * @return funcionário do registro
     */
    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    /**
     * Define o funcionário associado ao registro de ponto.
     *
     * @param idFuncionario funcionário do registro
     */
    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    /**
     * Retorna a data do registro.
     *
     * @return data do registro
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data do registro.
     *
     * @param data nova data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Retorna o horário de entrada registrado.
     *
     * @return horário de entrada
     */
    public String getHoraEntrada() {
        return horaEntrada;
    }

    /**
     * Define o horário de entrada.
     *
     * @param horaEntrada novo horário de entrada
     */
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    /**
     * Retorna o horário de saída registrado.
     *
     * @return horário de saída
     */
    public String getHoraSaida() {
        return horaSaida;
    }

    /**
     * Define o horário de saída.
     *
     * @param horaSaida novo horário de saída
     */
    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }
}
