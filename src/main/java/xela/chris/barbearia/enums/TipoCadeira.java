package xela.chris.barbearia.enums;

/**
 * Enumeração que representa os tipos de cadeiras disponíveis na barbearia.
 *
 * <p>Utilizado na classe {@link xela.chris.barbearia.models.Cadeira} para
 * diferenciar as cadeiras por sua função no estabelecimento.</p>
 *
 * @author Sistema Barbearia
 */
public enum TipoCadeira {
    /** Cadeira destinada aos processos de lavagem e secagem. */
    LAVAR_SECAR,

    /** Cadeira destinada a serviços corriqueiros (cortes, barbas, etc.). */
    SERVICO_CORRIQUEIRO
}
