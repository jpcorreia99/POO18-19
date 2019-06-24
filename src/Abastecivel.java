import UMCarroJaExceptions.*;
/**
 * Interface que inclui metodos de abastecimento do carro
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Abastecivel
{   
    /** Altera o consumo da viatura*/
    public void setConsumo(double c);
    /** Devolve o consumo da viatura*/
    public double getConsumo();
    /** Altera o tamanho do deposito/bateria*/
    public void setTamanhoDeposito(int t);
    /** Devolve o tamanho do deposito/bateria*/
    public int getTamanhoDeposito();
    /** Altera quanto combustivel/carga a viatura tem de momento*/
    public void setCombustivel(double Combustivel);
    /** Devolve quanto combustivel/carga a viatura tem de momento*/
    public double getCombustivel();
    /** Retira o combustivel gasto numa viagem ate ao ponto*/
    public void reduzCombustivel(Ponto destino);    
    /**Indica se uma viatura esta a menos de 10% da sua autonomia*/
    public boolean precisaAbastecer();
    /** Enche o deposito/bateria consoante o combustivel/carga que lhe e dado*/
    public void abastece(double q);
    /**Devolve a autonomia do carro em kms*/
    public int getAutonomiaKm();
    /** Devolve a autonomia em percentagem*/
    public int getAutonomiaPC();

}
