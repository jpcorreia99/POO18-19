import java.util.*;
import java.lang.*;
import java.time.LocalDateTime;
/**
 * Classe que descreve um Cliente da nossa aplicaçao;
 *
 * @author grupoPoo
 * @version (a version number or a date)
 */
public class Cliente extends Ator
{

   /** Posicao onde o cliente se encontra */
    private Ponto posCliente;
   /** Posicao para onde o cliente se pretende deslocar */
    private Ponto posDestino;
   /** 
    * Inteiro que revela informaçao em relaçao ao ultimo pedido feito pelo cliente
    * 0 - Nenhum pedido foi feito
    * 1 - O Pedido esta a ser processado
    * 2 - O Pedido foi rejeitado
    * 3 - O Pedido foi aceite e ainda falta classificar a viagem
    *  */
    private int estadoPedido;
    
    /**
     * Construtor por omissao
     */
    public Cliente()
    {
      super();  
      this.posCliente= new Ponto(1,1);
      this.posDestino= new Ponto(3.5,6.7);
      this.estadoPedido = 0;
    }
    
    /** 
    * Construtor por parametros
    * @param email
    * @param password
    * @param nome
    * @param morada
    * @param dataNascimento
    */
    public Cliente(String nif,String email, String password,String nome,String morada, LocalDateTime dataNascimento,double posX, double posY)
    {
      super(nif,email,password,nome,morada,dataNascimento);  
      this.posCliente= new Ponto(posX,posY);
      this.posDestino= new Ponto();
      this.estadoPedido=0;
    }
    
    /**
     * Construtor por copia
     */
    public Cliente (Cliente umC)
    {   
        super(umC);
        this.posCliente = umC.getPosCliente();
        this.posDestino = umC.getPosDestino();
        this.estadoPedido = umC.getEstadoPedido();
    }
    
    /**
     * Cria uma copia do objeto
     * @return cliente
     */
    public Cliente clone(){return new Cliente(this);}
    
    /**
     * Compara clientes
     * @return 'true' se forem iguais
     */
    public boolean equals(Object obj)
    {
        if(this == obj)
        return true; // se têm o mesmo endereço
        if( (obj == null) || (this.getClass() != obj.getClass()))
        return false; // se o objeto é nulo ou então não tem a mesma classe que o cliente
        return super.equals(obj); // sao o mesmo cliente se forem o mesmo Ator
    }
    /** Devolve a posicao atual do cliente */
    public Ponto getPosCliente(){return this.posCliente.clone();}
    /** Devolve a posicao destino do cliente */
    public Ponto getPosDestino(){return this.posDestino.clone();}   
    /** Devolve o estado do pedido do cliente*/
    public int getEstadoPedido(){return this.estadoPedido;}
    /** Altera a posicao atual do cliente */
    public void setPosCliente(Ponto p){this.posCliente = p;}
    /** Altera a posicao destino do cliente */
    public void setPosDestino(Ponto p){this.posDestino = p;}
    /** Altera o estado do ultimo pedido feito pelo cliente*/
    public void setEstadoPedido(int estado){this.estadoPedido=estado;}


    
    /** Devolve quantos kms o cliente ja percorreu*/
    public double kmsPercorridos(){
    int sum = 0;
    for(OcorrenciaAluguer o : historial){
     sum+=o.getDistancia();
    }  
    return sum;
    }
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(super.toString());
        s.append("\nNúmero de alugueres: ").append(getHistorial().size());
        s.append("\nDistância percorrida: ").append(kmsPercorridos()).append("kms");
        return s.toString();
    }
    /** Processamente de um aluguer, da parte do cliente*/
    public void aluga(Viatura viatura){
    OcorrenciaAluguer o = new OcorrenciaAluguer(this,viatura);
    adicionaOcorrenciaAluguer(o);
    setPosCliente(posDestino);
    }
}