import java.time.LocalDateTime;
/**
 * Uma classe que contem informaçao relativa a uma viagem com um carro especfico
 * E usada para se conseguir ordenar os carros por fatores que dependem tambem do cliente
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ViaturaInfo 
{    
    private Cliente cliente;
    private Viatura viatura; 
    private float distCliente;
    private float custoViagem;
    
    /** Construtor mais relevante, em que lhe e dado a viatura e o cliente que nela pretende viajar*/
    public ViaturaInfo(Viatura viatura, Cliente cliente){
        this.cliente=cliente.clone();
        this.viatura=viatura;
        this.distCliente = (float)viatura.distancia(cliente.getPosCliente());
        this.custoViagem = viatura.custoViagem(cliente.getPosDestino(),cliente.getClassificacao());
    }
    
    /** Construtor por copia*/
    public ViaturaInfo(ViaturaInfo outroViaturaInfo){
    this.viatura = outroViaturaInfo.getViatura();
    this.cliente = outroViaturaInfo.getCliente(); 
    this.distCliente = outroViaturaInfo.getDistCliente();
    this.custoViagem = outroViaturaInfo.getCustoViagem();
    }
    
    public Viatura getViatura() {return this.viatura;}
    public Cliente getCliente(){return this.cliente.clone();}
    public float getDistCliente(){return this.distCliente;}
    public float getCustoViagem(){return this.custoViagem;}
        
    /** Devolve uma representaçao textual do objeto*/
    public String toString(){
    
    StringBuilder sb = new StringBuilder();

    double distAoCliente =(double)Math.round(this.getDistCliente() * 100d) / 100d;
    double custo = (double)Math.round(this.getCustoViagem() * 100d) / 100d;
    int duracaoViagem = viatura.calculaTempoAPe(cliente.getPosCliente()) + viatura.calculaTempoAoDestino(cliente.getPosDestino());
    LocalDateTime tChegada = LocalDateTime.now().plusMinutes(duracaoViagem);     
    //construçao da string
      sb.append(viatura.toString()).append(" ")
          .append(distAoCliente).append("km      ")
          .append(custo).append("€").append("   ")
          .append(tChegada.getHour()).append("h").append(tChegada.getMinute()).append("m  ")
          .append(viatura.getPosViatura());
          return sb.toString();
    
    }
    
    /** Devolve uma copia do objeto*/
    public ViaturaInfo clone(){
    return new ViaturaInfo(this);
    }
    
    /** Compara dois objetos*/
    public boolean equals(Object o){
     if(this == o) return true;
     if(o==null || o.getClass()!=this.getClass()){return false;}
     ViaturaInfo c = (ViaturaInfo) o;
     return this.getViatura().equals(c.getViatura());
    }
}