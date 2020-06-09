import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Classe que descreve uma bicicleta da nossa aplicaçao;
 *
 * @author grupoPoo
 * @version (a version number or a date)
 */
public class Bicicleta extends Viatura
{
   // Variaveis de instancia
   /** Tipo de bicicleta
   *     Montanha
   *     Estrada
   */
   private String tipo;
    

    /**
     * Construtor por omissao
     */
    public Bicicleta()
    {
        super();
        this.tipo = "Montanha";
    }
    
    /** 
    * Construtor por parametros
    * @param matricula
    * @param idDono
    * @param marca
    * @param posBicicleta
    * @param vMedia
    * @param precoBaseKMmo
    */
   public Bicicleta (String matricula,String idDono,String marca,Ponto posBicicleta,double vMedia,double precoBaseKm, String tipo)
   {
    super(matricula,idDono,marca,posBicicleta,vMedia,precoBaseKm);
    this.tipo = tipo;
   } 
   
    /**
     * Constructor for objects of class Bicicleta por copia
     */
    public Bicicleta(Bicicleta b)
    {
        super(b);
        this.tipo = b.getTipo();
    }

    
    /** Devolve o tipo da bicicleta*/
    public String getTipo(){return this.tipo;}
    /** Altera o tipo da bicicleta*/
    public void setTipo(String a) {this.tipo = a;}
    
    /** Verifica se a bicicleta se encontra livre */
    public boolean viagemPossivel(Ponto p)
    {
        return this.getEstado();
    }
    
    /**
     * Aluga a mota ao cliente
     */
    public void alugaViatura(Cliente cliente)
    {
        OcorrenciaAluguer o = new OcorrenciaAluguer(cliente,this.clone());
        adicionaHistorial(o);
        setPosViatura(cliente.getPosCliente());
    }
    
    /**
     * Cria uma copia do objeto
     * @return bicicleta
     */
    public Viatura clone (){return new Bicicleta(this);}
    
    /**
     * Cria uma representaçao textual da bicicleta
     */
    public String toString()
    {
    StringBuilder sb = new StringBuilder();
    //arredondamento da classificaçao para 1 casa decimal
    BigDecimal bd = new BigDecimal(Float.toString(this.getClassificacao()));
    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
    float classificacao =  bd.floatValue();
    String estado = "";
    if(getEstado()){estado = "livre";}else{estado="ocupado";}
    
    sb.append("Bike").append(" de ").append(tipo).append("  ") 
                      .append(getMatricula()).append(" ")
                      .append(getMarca()).append(" ")
                      .append(estado).append(" ")
                      .append(Math.round(3600/getVMedia())).append("km/h ")
                      .append("-------       ----------- ")
                      .append(Math.round(getClassificacao() * 100f) / 100f).append("/100(").append(getNAlugueres()).append(")");;
    return sb.toString();
    }
}
