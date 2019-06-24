import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Classe que descreve um carro da nossa aplicaçao;
 *
 * @author grupoPoo
 * @version (a version number or a date)
 */
public abstract class Carro extends Viatura implements Abastecivel
{  
   // Variaveis de instancia

   /**Tamanho do deposito/bateria*/
   private int tamanhoDeposito;
   /** Consumo medio por minuto */
   private double consumo;
   /** Quantidade de combustivel existente, de momento.*/
   private double combustivel;

   public Carro(){
    super();
    this.tamanhoDeposito=50;
    this.consumo = 0.054;
    this.combustivel=30;
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
   public Carro (String matricula,String idDono,String marca,
                  Ponto posCarro, double vMedia,double precoBaseKm, 
                  int tamanhoDeposito, double consumo, double combustivel){
    super(matricula,idDono,marca,posCarro,vMedia,precoBaseKm);
    this.tamanhoDeposito = tamanhoDeposito;
    this.combustivel = combustivel;
    this.consumo = consumo;
   } 
   
   /** 
    * Contrutor por copia
    * @param carro Carro outroCarro
    */
   public Carro(Carro outroCarro){ 
    super(outroCarro);   
    this.tamanhoDeposito = outroCarro.getTamanhoDeposito();
    this.consumo = outroCarro.getConsumo();
    this.combustivel = outroCarro.getCombustivel();
   }
   
 
    


   
    public void setConsumo(double consumo){this.consumo=consumo;}       
   /** Altera o tamanho do deposito */
   public void setTamanhoDeposito(int tamanhoDeposito){this.tamanhoDeposito = tamanhoDeposito;}
       
   /** Altera o combustivel/energia existente no carro */    
   public void setCombustivel(double combustivel){this.combustivel=combustivel;} 

 
   
   /** Devolve a quantidade de combustivel/eletricidade gasta quando é percorrido 1km*/
   public double getConsumo(){return this.consumo;}
   
   /** Devolve o tamanho do deposito/bateria */  
   public int getTamanhoDeposito(){return this.tamanhoDeposito;}
             

   /** Devolve quanto combustivel/carga o carro ainda tem*/
   public double getCombustivel(){return this.combustivel;}   
         

        
    /**
     * Indica se o carro esta a menos de 10% da capacidade
     * @return
     */
    public boolean precisaAbastecer(){
     return (getCombustivel()/getTamanhoDeposito()<=0.1); 
    }
        
    /**
     * Aluga o carro ao cliente
     * Procedimento:
     *  1. Incrementa o numero de vezes que o carro foi alugado
     *  2. Adiciona a ocorrencia do aluguer ao historial
     *  3. Reduz o combustivel gasto
     *  4. Desloca o carro ate ao destino
     * 
     */
    
    public void alugaViatura(Cliente cliente){
    OcorrenciaAluguer o = new OcorrenciaAluguer(cliente,this.clone());
    adicionaHistorial(o); 
    reduzCombustivel(cliente.getPosDestino());
    setPosViatura(cliente.getPosDestino());

    }
    
    

        
    /**
     * Reduz o combustivel, devido a ter sido feita uma viagem
     */
    
    public abstract void reduzCombustivel(Ponto destino);
    
}

