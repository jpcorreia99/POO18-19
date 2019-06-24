import UMCarroJaExceptions.*;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Classe que descreve uma mota da nossa aplicaçao;
 *
 * @author grupoPoo
 * @version (a version number or a date)
 */
public class Mota extends Viatura implements Abastecivel
{
   /**
   * Numero de motas inseridas no sistema(nao diminui com eliminaçoes), serve para atribuir um id unico para hashear a mota
   */
   private static int motaCount=0;
   /**
    * Devolve o numero de instancias Mota criadas(nao diminui com eliminaçoes)
    */
   public static int getMotaCount() {
        return motaCount;
    }       
    

   /**Tamanho do deposito/bateria*/
   private int tamanhoDeposito;
   /** Consumo medio por minuto */
   private double consumo;
   /** Quantidade de combustivel existente, de momento.*/
   private double combustivel;
   
   /** Construtor por omissao*/
   public Mota(){
    super();
    this.tamanhoDeposito=30;
    this.consumo = 1;
    this.combustivel=20;
    }
   
   /** 
    * Construtor por parametros
    * @param idDono
    * @param marca
    * @param modelo
    * @param posMota
    * @param matricula
    * @param vMedia
    * @param precoBaseKM
    * @param tamanhoDeposito
    * @param consumo
    * @param combustivel
    */
   public Mota (String matricula,String idDono,String marca,Ponto posMota, double vMedia,double precoBaseKm, int tamanhoDeposito, double consumo, double combustivel){
    super(matricula,idDono,marca,posMota,vMedia,precoBaseKm);
    this.tamanhoDeposito = tamanhoDeposito;
    this.combustivel = combustivel;
    this.consumo=consumo;
   } 
   
   /** 
    * Contrutor por copia
    * @param Mota outraMota
    */
   public Mota (Mota outraMota){ 
    super(outraMota);   
    this.consumo = outraMota.getConsumo();
    this.tamanhoDeposito = outraMota.getTamanhoDeposito();
    this.combustivel = outraMota.getCombustivel();
   }
   
   //Sets e Gets
    
    
   /** Altera o tamanho do deposito */
   public void setTamanhoDeposito(int tamanhoDeposito){this.tamanhoDeposito = tamanhoDeposito;}
       
   /** Altera o combustivel/energia existente na mota */    
   public void setCombustivel(double combustivel){this.combustivel=combustivel;} 

   /**Altera o consumo da mota */
   public void setConsumo(double consumo){this.consumo=consumo;} 

   
   /** Devolve o tamanho do deposito/bateria */  
   public int getTamanhoDeposito(){return this.tamanhoDeposito;}
             
   /** Devolve quanto combustivel/carga a mota ainda tem*/
   public double getCombustivel(){return this.combustivel;}   
   
   /** Devolve o consumo da mota */
   public double getConsumo(){return this.consumo;}
   
   /**Devolve a autonomia do carro em kms*/   
   public int getAutonomiaKm(){
    return (int) (getCombustivel()/getConsumo());
    }

   /**Devolve a autonomia do carro em percentagem*/   
   public int getAutonomiaPC(){
    return  (int) ((getCombustivel()/getTamanhoDeposito())*100);
    }
    
   /**  
    /**
     * Cria uma copia do objeto
     * @return mota
     */
    public Mota clone(){
      return new Mota(this);
    }
    
    /**
     * Cria uma representaçao textual da mota
     */
    public String toString(){
    StringBuilder sb = new StringBuilder();
    //arredondamento da classificaçao para 1 casa decimal
    BigDecimal bd = new BigDecimal(Float.toString(this.getClassificacao()));
    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
    float classificacao =  bd.floatValue();
    
    String estado = "";
    if(getEstado()){estado = "livre";}else{estado="ocupado";}
    if(getAutonomiaPC()<10){sb.append("(!!)");}
    sb.append("Mota a gasolina").append("   ") 
                      .append(getMatricula()).append(" ")
                      .append(getMarca()).append(" ")
                      .append(estado).append(" ")
                      .append(Math.round(3600/getVMedia())).append("km/h ")
                      .append(Math.round((getConsumo() * 10000d) / 100d)).append("L/100km  ")
                      .append(getAutonomiaKm()).append("km(").append(getAutonomiaPC()).append("%)   ")                      
                      .append(Math.round(getClassificacao() * 100f) / 100f).append("/100(").append(getNAlugueres()).append(")").append(getCombustivel()/getConsumo());
    
    return sb.toString();
    }    
    
    
    // Metodos uteis    
    
    /**
     * Indica se a mota esta a menos de 10% da capacidade
     * @return
     */
    public boolean precisaAbastecer(){
     return (getCombustivel()/getTamanhoDeposito()<=0.1); 
    }
    

    
    /**
     * Indica se a mota tem combustivel suficiente para efetuar a viagem e se encontra livre
     * @param Coordenadas do destino
     */
    public boolean viagemPossivel(Ponto destino){
      double dist = distancia(destino);
      return dist*getConsumo()<getCombustivel() && !precisaAbastecer() && this.getEstado();
    }
    
    /**
     * Reduz o combustivel, devido a ter sido feita uma viagem
     */
    public void reduzCombustivel(Ponto destino){
      double dist = distancia(destino);
      setCombustivel((float)(getCombustivel()-dist*(getConsumo())));
    }
        
    /**
     * Aluga a mota ao cliente
     */
    public void alugaViatura(Cliente cliente){
        
        OcorrenciaAluguer o = new OcorrenciaAluguer(cliente,this.clone());
        adicionaHistorial(o);
        reduzCombustivel(cliente.getPosCliente());
        setPosViatura(cliente.getPosCliente());  

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
