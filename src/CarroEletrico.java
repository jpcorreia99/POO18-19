import java.math.BigDecimal;
/**
 * Write a description of class CarroEletrico here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CarroEletrico extends Carro
{ 
   /** Construtor por omissao*/ 
  public CarroEletrico(){
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
  public CarroEletrico(String matricula,String idDono,String marca ,Ponto posCarro, double vMedia,double precoBaseKm, int tamanhoDeposito, double consumo, double combustivel){
    super(matricula,idDono,marca,posCarro,vMedia,precoBaseKm,tamanhoDeposito, consumo,combustivel);    
   }
    
  /** Construtor por copia*/  
  public CarroEletrico(CarroEletrico outroCarro){
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
 /** Indica se o carro tem carga suficiente para efetuar a viagem*/
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
    public CarroEletrico clone(){
    return new CarroEletrico(this);
    }
    /** Metodo que devolve uma representaçao textual do objeto*/
    public  String toString(){
    StringBuilder sb = new StringBuilder();
    String estado = "";
    if(getEstado()){estado = "livre";}else{estado="ocupado";} 
    if(getAutonomiaPC()<10){sb.append("(!!)");}
     sb.append("Carro elétrico").append("  ")                    
                      .append(getMatricula()).append("   ")
                      .append(getMarca()).append(" ")
                      .append(estado).append(" ")
                      .append(Math.round(3600/getVMedia())).append("Km/h ")
                      .append(Math.round(getConsumo() * 10000d) / 100d).append("Wh/100km  ")
                      .append(getAutonomiaKm()).append("Km(").append(getAutonomiaPC()).append("%)   ")
                      .append(Math.round(getClassificacao() * 100f) / 100f).append("/100(").append(getNAlugueres()).append(")");
    return sb.toString(); 
    };
    
    public void reduzCombustivel(Ponto destino){
      double dist = distancia(destino);
      setCombustivel((float)(getCombustivel()-dist*(getConsumo())));
    }
    
    /** Enche a bateria consoante a carga que lhe e dado*/
    public void abastece(double q){
     if(q+getCombustivel()>getTamanhoDeposito()){
        setCombustivel(getTamanhoDeposito());//enche o deposito*/
        }else{
        setCombustivel(getCombustivel()+q);
        }
    }
}
