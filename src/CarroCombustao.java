import UMCarroJaExceptions.*;
/**
 * Write a description of class CarroCombustao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CarroCombustao extends Carro
{   
    /** Construtor por omissao*/
    public CarroCombustao(){
    super();
    }
    
    /** 
    * Construtor por parametros
    * @param idDono
    * @param marca
    * @param posCarro
    * @param matricula
    * @param vMedia
    * @param precoBaseKm
    * @param tamanhoDeposito
    * @param consumo
    * @param combustivel
    */
    public CarroCombustao( String matricula,String idDono,String marca,
                          Ponto posCarro, double vMedia,double precoBaseKm, 
                          int tamanhoDeposito, double consumo, double combustivel){
    super(matricula,idDono,marca,posCarro,vMedia,precoBaseKm,tamanhoDeposito, consumo,combustivel);    
    }
    
    /** Construtor por copia*/
    public CarroCombustao(CarroCombustao outroCarro){
    super(outroCarro);
    }
   
    /**Devolve a autonomia do carro em kms*/   
   public int getAutonomiaKm(){
    return (int) (getCombustivel()/getConsumo());
    }

   /**Devolve a autonomia do carro em percentagem*/   
   public int getAutonomiaPC(){
    return  (int) ((getCombustivel()/getTamanhoDeposito())*100);
    }    
    
    /** Indica se o carro tem combustivel suficiente para efetuas a viagem*/
    public  boolean viagemPossivel(Ponto destino)
    {
      double dist = distancia(destino);
      return dist*getConsumo()<getCombustivel() && !precisaAbastecer() && getEstado();
    }
    
   /**
   * Método que faz uma cópia do objecto receptor da mensagem.
   * Para tal invoca o construtor de cópia.
   * 
   * @return objecto clone do objecto que recebe a mensagem.
   */
    public CarroCombustao clone(){
    return new CarroCombustao(this);
    }
    /** Metodo que devolve uma representaçao textual do objeto*/
    public  String toString(){
     StringBuilder sb = new StringBuilder();
     String estado = "";
     if(getEstado()){estado = "livre";}else{estado="ocupado";}
     if(getAutonomiaPC()<10){sb.append("(!!)");}
     sb.append("Carro gasolina").append("  ")                    
                      .append(getMatricula()).append("   ")
                      .append(getMarca()).append(" ")
                      .append(estado).append(" ")
                      .append(Math.round(3600/getVMedia())).append("km/h ")
                      .append(Math.round(getConsumo() * 10000d) / 100d).append("L/100km  ")
                      .append(getAutonomiaKm()).append("Km(").append(getAutonomiaPC()).append("%)   ")
                      .append(Math.round(getClassificacao() * 100f) / 100f).append("/100(").append(getNAlugueres()).append(")");
     if(getAutonomiaPC()<10){sb.append("(!!!!) ");}
     return sb.toString();   
    } 
   
    
    public void reduzCombustivel(Ponto destino){
      double dist = distancia(destino);
      setCombustivel((float)(getCombustivel()-dist*(getConsumo())));
    }
    
    /** Enche o deposito consoante o combustivel que lhe e dado*/
    public void abastece(double q){
        if(q>getTamanhoDeposito()-getCombustivel()){
         setCombustivel(getTamanhoDeposito());
        }else{
        setCombustivel(getCombustivel()+q);
        } 
    }
    
}
