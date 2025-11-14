package xela.chris.barbearia.models;

public class RegistroPonto {
    private Funcionario idFuncionario;
    private String data;
    private String horaEntrada;
    private String horaSaida;


    public RegistroPonto(){}
    public RegistroPonto(String data, String horaEntrada, String horaSaida){
        this.data = data;
        this.horaEntrada = horaEntrada;
    }

    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }
}
