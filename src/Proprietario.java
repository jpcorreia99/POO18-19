import java.util.*;
import java.lang.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Classe que descreve um Proprietario da nossa aplicaçao;
 *
 * @author grupoPoo
 * @version (a version number or a date)
 */
public class Proprietario extends Ator
{

  /** Lista de pedidos de aluguer */
  private List<OcorrenciaAluguer> pedidosAluguer;

  /** Construtor vazio */
  public Proprietario()
  {   super();
      this.pedidosAluguer= new ArrayList<>();
  }
  /**
   * Construtor por parametro
   */
  public Proprietario(String nif,String email, String password,String nome,String morada, LocalDateTime dataNascimento){
    super(nif,email,password,nome,morada,dataNascimento);
    this.pedidosAluguer = new ArrayList<OcorrenciaAluguer>();      
    }
    
  /** Construtor por copia*/
  public Proprietario(Proprietario p){
    super(p);
    this.pedidosAluguer=p.getPedidosAluguer();
    }
  /** Devolve a lista de pedidos de aluguer */
  public List<OcorrenciaAluguer> getPedidosAluguer(){
    List<OcorrenciaAluguer> res = new ArrayList<>();
    for(OcorrenciaAluguer o : pedidosAluguer){
    res.add(o.clone());
    }
    return res;
    }
  /** Altera a lista de pedidos de aluguer */
  public void setPedidosAluguer(List<OcorrenciaAluguer> pedidosAluguer){
     this.pedidosAluguer = new ArrayList<>();
      for(OcorrenciaAluguer o : pedidosAluguer){
        this.pedidosAluguer.add(o.clone());
      }
  }
    
  /** Devolve o primeiro pedido de aluguer da lista */
    public OcorrenciaAluguer getPedido(){
        return this.pedidosAluguer.get(0).clone();
    }
    
  /** Devolve a primeiraOcorrencia  
  /** Elimina o primeiro pedido da lista de pedidos de aluguer */
    public void eliminaPedido(){
    this.pedidosAluguer.remove(0);
    }
  /** Adiciona um pedido de aluguer a lista de pedidos de aluguer */
    public void adicionaPedido(OcorrenciaAluguer o){
        this.pedidosAluguer.add(o.clone());
    }
  
  /** Aceita um pedido, tranferindo-o da lista de pedidos para o historial*/
  public void aceitaPedido(){     
     adicionaOcorrenciaAluguer(this.pedidosAluguer.get(0));
     eliminaPedido();
    }
    
    /** Verifica se existem pedidos de aluguer
   * @return 'true' se houver pelo menos um pedido
   */
  public boolean temPedidos(){return !this.pedidosAluguer.isEmpty();}
    
    
  /**
     * Compara proprietarios
     * @return 'true' se forem iguais
     */
  public boolean equals(Object obj) {return super.equals(obj);}

  /** Clona o proprietario*/
  public Proprietario clone(){
    return new Proprietario(this);
    }  
    
    /** Devolve o numero de pedidos de aluguer */
  public int numPedidos(){
    return (getPedidosAluguer().size());
   }  
   
  /** Devolve o numero de viagens que o proprietario ainda nao classificou */
  public int numViagensPorClassificar(){
    int count=0;
    List<OcorrenciaAluguer> lista = getHistorial();
    for(OcorrenciaAluguer o:lista){
     if(!o.getFoiClassificada()){
        count++;
        }
    }
    return count;
   }  
   
  /** 
   * Devolve uma lista de todas as viagens que o proprietario ainda nao classificou
   */ 
  public List<OcorrenciaAluguer> getViagensPorClassificar(){
      return this.getHistorial().stream().filter(o -> !o.getFoiClassificada())
                                         .map(OcorrenciaAluguer :: clone)
                                         .collect(Collectors.toList());
    } 
  
  /** Metodo que e chamado quando o proprietario classifica uma viagem
   * Vai alterar essa ocorrenciaAluguer de modo a indicar que ja foi avaliada
   */
  public void classificouViagem(){
    List<OcorrenciaAluguer> lista = historial;
    boolean  alterado=false;
    for(int i =0; !alterado && !lista.isEmpty();i++){
     OcorrenciaAluguer o = lista.get(i);
     if(!o.getFoiClassificada()){
      alterado = true;
      o.setFoiClassificada(true);
     }
    }   
    
  }

    

}  
