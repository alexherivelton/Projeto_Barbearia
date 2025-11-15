package xela.chris.barbearia.enums;

/**
 * Enumeração que representa os possíveis status de atendimento de um cliente.
 *
 * <p>Utilizado na classe {@link xela.chris.barbearia.models.Cliente} para
 * rastrear o estado atual do cliente no sistema.</p>
 *
 * @author Sistema Barbearia
 */
public enum StatusAtendimentoCliente {
    /** Cliente está sendo atendido no momento. */
    EM_ATENDIMENTO,

    /** Cliente está aguardando para ser atendido. */
    EM_ESPERA,

    /** Cliente já foi atendido. */
    ATENDIDO,

    /** Cliente possui um agendamento marcado. */
    AGENDADO
}
