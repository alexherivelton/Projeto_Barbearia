package xela.chris.barbearia.enums;

/**
 * Enumeração que representa as permissões disponíveis no sistema da barbearia.
 *
 * <p>As permissões definem quais ações cada tipo de funcionário pode realizar no sistema.
 * As permissões são atribuídas automaticamente com base no cargo do funcionário,
 * conforme definido na classe {@link xela.chris.barbearia.models.Funcionario}.</p>
 *
 * @author Sistema Barbearia
 */
public enum PermissoesEnum {
    /** Permissão para cadastrar novos funcionários no sistema. */
    CAD_FUNC,

    /** Permissão para gerar relatórios de balanço mensal. */
    GERAR_BALANCO_MENSAL,

    /** Permissão para realizar operações de balanço mensal. */
    FAZER_BALANCO_MENSAL,

    /** Permissão para cadastrar novos clientes. */
    CADASTRAR_CLIENTE,

    /** Permissão para criar novos agendamentos. */
    CRIAR_AGENDAMENTO,

    /** Permissão para verificar a agenda de atendimentos. */
    VERIFICAR_AGENDA,

    /** Permissão para verificar informações de clientes. */
    VERIFICAR_CLIENTE,

    /** Permissão para gerar notas fiscais. */
    GERAR_NOTA
}
