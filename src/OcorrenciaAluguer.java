import java.time.Month;
import java.time.LocalDateTime;
import java.io.Serializable;
/**
 * Classe que contem informaçao relativa a uma ocorrencia de aluguer de um Carro; 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class OcorrenciaAluguer implements Serializable
{
    /** Altura do inicio do aluguer*/
    private LocalDateTime data;
    /** Matricula que foi alugado*/
    private Viatura viatura;
    /** Cliente que alugou o carro*/
    private String  emailCliente;
    /** Nif do cliente
    /** custo da viagem*/
    private float custo;
    /** Ponto onde o cliente esta quando faz o pedido*/
    private Ponto partidaCliente;
    /** Ponto de partida*/
    private Ponto partidaViatura;
    /** Ponto de chegada*/
    private Ponto destino;    
    /** Distancia percorrida*/
    private double distancia;
    /** Indica se o proprietario ja classificou esta viagem*/
    private boolean foiClassificada;
    
    /** Construtor em que se recebe um cliente e uma viatura e dai se extrai a informaçao relevante*/
    public OcorrenciaAluguer (Cliente cliente,Viatura viatura) {
       this.data = LocalDateTime.now();
       this.viatura=viatura.clone();
       this.emailCliente=cliente.getEmail();
       this.custo = viatura.custoViagem(cliente.getPosDestino(),cliente.getClassificacao());
       this.partidaCliente = cliente.getPosCliente();
       this.partidaViatura = viatura.getPosViatura();
       this.destino = cliente.getPosDestino();
       this.distancia = partidaViatura.distancia(destino);
       this.foiClassificada=false;
    }
    
    /** Construtor por copia*/
  public OcorrenciaAluguer(OcorrenciaAluguer outroAluguer){
      this.data=outroAluguer.getData();
      this.viatura = outroAluguer.getViatura();
      this.emailCliente=outroAluguer.getEmailCliente();
      this.custo = outroAluguer.getCusto();
      this.partidaCliente=outroAluguer.getPartidaCliente();
      this.partidaViatura=outroAluguer.getPartidaViatura();
      this.destino = outroAluguer.getDestino();
      this.distancia = outroAluguer.getDistancia();
      this.foiClassificada = outroAluguer.getFoiClassificada();
    }
    
    /**
     * Devolve a data do aluguer
     * Nota: Nao e possivel fazer clone do localDateTime pelo que se cria esta versao
     */
    public LocalDateTime getData(){
    int ano = data.getYear();
    int mes = data.getMonthValue();
    int dia = data.getDayOfMonth();
    int hora = data.getHour();
    int min = data.getMinute();
    return data.of(ano,mes,dia,hora,min);
    }
    
    /**Devolve o custo  da viagem*/
    public float getCusto() {return this.custo;}
    /** Devolve o carro da viagem*/
    public Viatura getViatura(){return this.viatura.clone();}
    /** Devolve o nif cliente que fez a viagem*/
    public String getEmailCliente(){return this.emailCliente;}
    /** Devolve o pontoonde o cliente esta antes de efetuar a viagem*/
    public Ponto getPartidaCliente(){return this.partidaCliente.clone();}    
    /** Devolve o ponto de partida da viagem*/
    public Ponto getPartidaViatura(){return this.partidaViatura.clone();}
    /** Devolve o ponto de chegada do carro*/
    public Ponto getDestino(){return this.destino.clone();}
    /** Devolve se ja foi classificada*/
    public boolean getFoiClassificada(){return this.foiClassificada;}
    /** Devolve a distancia percorrida*/
    public double getDistancia(){return this.distancia;}
    /** Altera o boleano que indica se ja foi classificada*/
    public void setFoiClassificada(boolean b){this.foiClassificada=b;}
    
   /**
   * Método que faz uma cópia do objecto receptor da mensagem.
   * Para tal invoca o construtor de cópia.
   * 
   * @return objecto clone do objecto que recebe a mensagem.
   */
    public OcorrenciaAluguer clone(){
     return new OcorrenciaAluguer(this);
    }
   
    /** verifica a igualdade entre dois objetos*/
    public boolean equals(Object o){
    if(o==this) return true;
    if(o==null || o.getClass()!=this.getClass())return false;
    OcorrenciaAluguer oc = (OcorrenciaAluguer)o;
    return this.data.getDayOfMonth()==(oc.getData().getDayOfMonth())
           && this.data.getMonth().getValue()==(oc.getData().getHour())
           && this.data.getHour()==(oc.getData().getHour())
           && this.viatura.equals(oc.getViatura()); 
    }
    
    /** Devolve uma representaçao textual de uma ocorrencia de aluger de um carro*/
    public String toString(){
    int ano = data.getYear();
    int mes = data.getMonth().getValue();
    int dia = data.getDayOfMonth();
    int hora = data.getHour();
    int min = data.getMinute();
    StringBuilder sb = new StringBuilder();
    sb.append(emailCliente).append("   ")//devolve so a parte antes do email
                     .append(viatura.getMatricula()).append(" - ").append(viatura.getMarca()).append(" ").append("  ")                          
                     .append(dia).append("/").append(mes).append("/").append(ano).append("   ")
                     .append(hora).append("h").append(min).append("m").append("  ")
                     .append(getPartidaCliente().toString()).append(" ")
                     .append(partidaViatura.toString()).append("  ")
                     .append(destino.toString()).append("  ")
                     .append(" ").append(Math.round(distancia*100d)/100d).append("Km ")
                     .append(custo).append("€");
    return sb.toString();                             
    }
}
