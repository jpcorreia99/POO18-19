
/**
 * Write a description of class CarroHibrido here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CarroHibrido extends Carro
{   
    /** Capacidade do motor eletrico do carro*/
    private int tamanhoDepositoAuxiliar;
    /** Consumo do motor eletrico*/
    private double consumoAuxiliar;
    /** Quantidade de carga restante no motor eletrico*/
    private double combustivelAuxiliar;
    
    /** Construtor por omissao*/
    public CarroHibrido(){
    super();
    this.tamanhoDepositoAuxiliar = 2000;
    this.consumoAuxiliar = 60;
    this.combustivelAuxiliar = 1500;
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
    * @param tamanhoDepositoAuxiliar
    * @param consumoAuxiliar
    * @param combustivelAuxiliar
    */
    
    public CarroHibrido(String matricula,String idDono,String marca, 
                         Ponto posCarro, double vMedia,double precoBaseKm, 
                         int tamanhoDeposito, double consumo, double combustivel
                         ,int tamanhoDepositoAuxiliar, double consumoAuxiliar, double combustivelAuxiliar){
    super(matricula,idDono,marca,posCarro,vMedia,precoBaseKm,tamanhoDeposito, consumo,combustivel);  
    this.tamanhoDepositoAuxiliar = tamanhoDepositoAuxiliar;
    this.consumoAuxiliar = consumoAuxiliar;
    this.combustivelAuxiliar = combustivelAuxiliar;
    }
    
    /** Construtor por copia 
     * Recebe como argumento outro carro hibrido
     */
    public CarroHibrido(CarroHibrido outroCarro){
    super(outroCarro);
    this.tamanhoDepositoAuxiliar= outroCarro.getTamanhoDepositoAuxiliar();
    this.consumoAuxiliar = outroCarro.getConsumoAuxiliar();
    this.combustivelAuxiliar = outroCarro.getCombustivelAuxiliar();
    }

    public int getTamanhoDepositoAuxiliar(){return this.tamanhoDepositoAuxiliar;}
    public double getConsumoAuxiliar(){return this.consumoAuxiliar;}
    public double getCombustivelAuxiliar(){return this.combustivelAuxiliar;}
    
    public void setTamanhoDepositoAuxiliar(int c){this.tamanhoDepositoAuxiliar =c;}
    public void setConsumoAuxiliar(double c){this.consumoAuxiliar=c;}
    public void setCombustivelAuxiliar(double c){this.combustivelAuxiliar=c;}

   /**Devolve a autonomia do carro em kms*/   
   public int getAutonomiaKm(){
    return (int) ((getCombustivel()/getConsumo())+(getCombustivelAuxiliar()/getConsumoAuxiliar()));
    }

   /**Devolve a autonomia do carro em percentagem*/   
   public int getAutonomiaPC(){
    double autonomiaTotal =getTamanhoDeposito()/getConsumo()  + getTamanhoDepositoAuxiliar()/getConsumoAuxiliar();
    return  (int) ((getAutonomiaKm()/autonomiaTotal)*100);
    }
    
    
    /** Indica se o carro tem carga suficiente para efetuar a viagem
      * No caso especial de ser carro hibrido tem em atençao o facto de o carro ter dois depositos
     */
    public  boolean viagemPossivel(Ponto destino)
    {       
      double dist = distancia(destino);
      return !(getAutonomiaPC()<10) && dist<(getAutonomiaKm())  && getEstado();//
    }
    
   /**
   * Método que faz uma cópia do objecto receptor da mensagem.
   * Para tal invoca o construtor de cópia.
   * 
   * @return objecto clone do objecto que recebe a mensagem.
   */
    public CarroHibrido clone(){
    return new CarroHibrido(this);
    }
    
    /** Metodo que devolve uma representaçao textual do objeto*/
    public  String toString(){
    StringBuilder sb = new StringBuilder();
    String estado = "";
    if(getEstado()){estado = "livre";}else{estado="ocupado";}
    if(getAutonomiaPC()<10){sb.append("(!!)");}
    sb.append("Carro Híbrido").append("   ") 
                      .append(getMatricula()).append(" ")
                      .append(getMarca()).append(" ")
                      .append(estado).append(" ")
                      .append(Math.round(3600/getVMedia())).append("km/h ")
                      .append(Math.round((getConsumo() * 10000d) / 100d)).append("L/100km+ ")                      
                      .append(Math.round((getConsumoAuxiliar() * 100d) / 100d)).append("Wh/km  ")
                      .append(getAutonomiaKm()).append("km(").append(getAutonomiaPC()).append("%)   ")
                      .append(Math.round(getClassificacao() * 100f) / 100f).append("/100(").append(getNAlugueres()).append(")");
    return sb.toString();
   
    }; 
    
    /** 
     * Reduz o combustivel que foi gasto duranta a viagem
     * Verifica se tera de usar o motor auxiliar
     */
    public void reduzCombustivel(Ponto destino){
      double dist = distancia(destino);
      if(dist*getConsumo()<getCombustivel()){
      setCombustivel(getCombustivel()-dist*getConsumo());
      }else{
      dist = dist - getCombustivel()/getConsumo();
      setCombustivel(0);    
      setCombustivelAuxiliar(getCombustivelAuxiliar()-dist*(getConsumoAuxiliar()));
      }
    }
    
    /** Enche o deposito consoante o combustivel que lhe e dado*/
    public void abastece(double q){
     if(q+getCombustivel()>getTamanhoDeposito()){
        setCombustivel(getTamanhoDeposito());//enche o deposito*/
        }else{
        setCombustivel(getCombustivel()+q);
        }        
    }
    /** Enche a bateria auxiliar consoante a carga que lhe e dado*/
    public void abasteceAuxiliar(double q){
        if(q+getCombustivelAuxiliar()>getTamanhoDepositoAuxiliar()){
        setCombustivelAuxiliar(getTamanhoDepositoAuxiliar());//enche o deposito*/
        }else{
        setCombustivelAuxiliar(getCombustivelAuxiliar()+q);
        }
    }        
 }
