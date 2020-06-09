import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.io.Serializable;
/**
 * Classe abstrata que retrata uma viatura da nossa aplicaçao.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Viatura implements Serializable, Comparable<Viatura>
{

    
    
   private String idDono;
   /** Matricula do Carro*/
   private String matricula;
   /** Marca e modelo da viatura*/   
   private String marca;
   /** Posiçao da viatura*/
   private Ponto posViatura;
   /** Estado da viatura
    * true - Livre
    * false - Ocupado
    */
   private boolean estado;
   /** Velocidade media por kilometro (quantos segundos demora a percorrer um kilometro) */
   private double vMedia;
   /** Preço base por km*/
   private double precoBaseKm;
   /** Lista contendo o historico de aluguer do veiculo*/
   private List<OcorrenciaAluguer> historial;
   /** Classificaçao do veiculo*/
   private float classificacao;
   /** Nº de vezes que foi alugado*/
   private int nAlugueres;

    
   /** Construtor por omissao de Viatura */
   public Viatura(){
    this.matricula="";
    this.idDono="";
    this.marca = "";
    this.posViatura=new Ponto();
    this.estado=true;
    this.vMedia=0;
    this.precoBaseKm=0;
    this.classificacao = 0;
    this.nAlugueres =0;
    this.historial = new ArrayList<OcorrenciaAluguer>();
    }
    /** 
    * Construtor por parametros
    * @param idDono
    * @param marca
    * @param posViatyra
    * @param vMedia
    * @param precoBaseKm
    */
   public Viatura(String matricula,String idDono ,String marca, Ponto posViatura,double vMedia,double precoBaseKm){  
    this.matricula = matricula;
    this.idDono =idDono;    
    this.marca = marca;
    this.posViatura = posViatura.clone();
    this.estado = true;
    this.vMedia = vMedia;
    this.precoBaseKm=precoBaseKm;
    this.classificacao=0;
    this.nAlugueres =0;
    this.historial = new ArrayList<OcorrenciaAluguer>();
    }  
   /** Construtor por copia de viatura */
   public Viatura(Viatura outraViatura){
   this.idDono = outraViatura.getIdDono();
   this.matricula = outraViatura.getMatricula();
   this.marca = outraViatura.getMarca();
   this.posViatura=outraViatura.getPosViatura();
   this.estado = outraViatura.getEstado();
   this.vMedia=outraViatura.getVMedia();
   this.precoBaseKm = outraViatura.getPrecoBaseKm();
   this.historial =outraViatura.getHistorial();
   this.classificacao = outraViatura.getClassificacao();
   this.nAlugueres =outraViatura.getNAlugueres();
   }
   /** Devolve a marca de uma viatura*/
   public String getMarca(){return this.marca;}
   /** Devolve a matricula do carro */
   public String getMatricula(){return this.matricula;}   
   /** Devolve o dono de uma viatura*/
   public String getIdDono(){return this.idDono;}
   /** Devolve a posicao de uma viatura*/
   public Ponto getPosViatura(){return this.posViatura.clone();}
   /** Devolve o estado de uma viatura*/
   public boolean getEstado(){return this.estado;}
   /** Devolve a velocidade media de uma viatura*/
   public double getVMedia(){return this.vMedia;}
   /** Devolve o preço base por km de uma viatura*/
   public double getPrecoBaseKm(){return this.precoBaseKm;}
   /** Devolve a classificacao de uma viatura*/
   public float getClassificacao(){return this.classificacao;}
   /** Devolve o numero de vezes que uma viatura foi alugada*/
   public int getNAlugueres(){return this.nAlugueres;} 
   /** Devolve a lista de ocurrencias de aluguer de uma viatura*/
   public ArrayList<OcorrenciaAluguer> getHistorial(){
    ArrayList<OcorrenciaAluguer> res = new ArrayList<OcorrenciaAluguer>();
    for(OcorrenciaAluguer o: historial){
     res.add(o.clone());
    }
    return res;
    }
   /** Altera a marca de uma viatura */
   public void setMarca(String marca){this.marca=marca;}
   /** Altera a matricula do carro */
   public void setMatricula(String matricula){this.matricula=matricula;}
   /** Altera a posicao de uma viatura */
   public void setPosViatura(Ponto posViatura){this.posViatura=posViatura.clone();}
   /** Altera o estado de uma viatura */
   public void setEstado(boolean estado){this.estado=estado;}
   /** Altera a velocidade media de uma viatura */
   public void setVMedia(double vMedia){this.vMedia=vMedia;}
   /** Altera o preço base por km de uma viatura */
   public void setPrecoBaseKm(double precoBaseKm){this.precoBaseKm=precoBaseKm;}
   /** Altera a classifcacao de uma viatura */
   public void setClassificacao(float classificacao){this.classificacao=classificacao;}
   /** Altera o numero de alugueres de uma viatura */
   public void setNAlugueres(int nAlugueres){this.nAlugueres=nAlugueres;}
   /** Altera o historial de uma viatura */
   public void setHistorial(ArrayList<OcorrenciaAluguer> historial){
   this.historial= new ArrayList<OcorrenciaAluguer>();
   for(OcorrenciaAluguer o: historial){
    this.historial.add(o.clone());
    }
   }
   
   /** Adiciona uma ocorrencia de aluguer ao historial da viatura*/
   public void adicionaHistorial(OcorrenciaAluguer o){
    this.historial.add(o.clone());
    }
   
   /** Metodo que determina se dois objeos sao iguais*/
   public boolean equals(Object o){
    if(this == o) return true;
    if(o==null || o.getClass()!=this.getClass()){return false;}
    Viatura v= (Viatura) o;
    return this.matricula.equals(v.getMatricula());
    } 
    
    /**Estabelece a ordem natural dos objetos*/
   public int compareTo(Viatura v){
        if(classificacao <= v.getClassificacao())
        return -1; 
        else 
        return 1;
    }
    

   /** O utilizador da uma classificaçao ao carro, alterando a classificacao geral
     * @param classificacao
     */
    public void classificaViatura(int n){
    setNAlugueres(getNAlugueres()+1);
    if (this.getNAlugueres()==1) { // nunca foi avaliado
      this.classificacao=n;
    }else{
      this.classificacao=(this.getClassificacao() * (this.getNAlugueres()-1) + n) /  this.getNAlugueres();
    }
   }
   
   /**
     * Calcula a distanciaa um ponto
     */
    public double distancia(Ponto ponto){ 
      return posViatura.distancia(ponto);
    }
   
   /**
     * Indica o tempo(em minutos) que o cliente vai demorar a chegar ao destino
     */
    public int calculaTempoAoDestino(Ponto destino){
      double dist= distancia(destino);
      return (int)(dist*getVMedia()/60); // adicionar condicionantes
    }
    
/** Calcula o custo total da viagem
     * @param posicao em x do destino
     * @param posicao em y do destino
     * @param classificacao do cliente
     * @param condiçoes meteorologicas
     * 
     * Alteraçoes ao custo: 
     *  - se um utilizador tiver um rating acima de 95%, tera um desconto de 10% 
     *  - se for hora de ponta o preço aumenta
     *  - se estiver a chover o preço aumenta 
     */
    
    public float custoViagem(Ponto destino, float classificacao){ 
    double dist = distancia(destino);
    double custo = precoBaseKm*dist; 
    LocalDateTime now = LocalDateTime.now();
    int horas =now.getHour();
    //Horas de ponta
    if((horas>=8 && horas<=10) || (horas>=17 && horas <=19)){
    custo=custo*1.25;
    }else if(horas>=12 && horas<=14){
    custo = custo*1.1;
    }
    
    if(classificacao>=95)
     custo=custo* 0.9;
     

    BigDecimal bd = new BigDecimal(Double.toString(custo));
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.floatValue();
    }

   /** Indica qual foi o total faturado pela viatura*/
   public double totalFaturado(){
   double sum =0;
   for(OcorrenciaAluguer o : historial){
    sum+=o.getCusto();
    }
   return Math.round(sum*100d)/100d;
   }
   
  /** Indica qual foi o total faturado pela viatura num intervalo de tempo*/ 
  public double totalFaturadoDatas(LocalDateTime inicio, LocalDateTime fim){
   double sum =0;
   for(OcorrenciaAluguer o : historial){
    LocalDateTime data = o.getData();   
    if(data.isAfter(inicio) && data.isBefore(fim)){
      sum+=o.getCusto();
    }  
    }
   return Math.round(sum*100d)/100d;
   } 
  
  
    /**
     * Indica o tempo (em minutos) que o cliente vai demorar a chegar a viatura
     */
   public int calculaTempoAPe(Ponto posCliente){
     double dist = distancia(posCliente);
     return (int)((dist/4)*60);
   }
    
   /** Metodo que processa o aluguer de uma viatura*/
   public abstract void alugaViatura(Cliente cliente);
   
   /** Metodo que devolve uma representaçao textual do objeto*/
   public abstract String toString(); 
   
   /**
   * Método que faz uma cópia do objecto receptor da mensagem.
   * Para tal invoca o construtor de cópia.
   * 
   * @return objecto clone do objecto que recebe a mensagem.
   */
   public abstract Viatura clone();
   
   /**
    
     * Indica se a viatua reune as condiçoes necessarias para fazer a viagem ate ao ponto dado
     * @param Coordenadas do destino
     */
    public abstract boolean viagemPossivel(Ponto destino);
       
}
