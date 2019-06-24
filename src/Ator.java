import java.util.*;
import java.lang.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.io.Serializable;
/**
 * Classe que descreve um ator da aplicaçao.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Ator implements Serializable
{
    /** Representa o nif de um cliente*/
    private String nif;
    /** Representa o email de um ator */ 
    private String email;
    /** Representa a password de um ator */
    private String password;
    /** Representa a morada de um ator */
    private String morada;
    /** Representa a data de nascimento de um ator */
    private LocalDateTime dataNascimento;
    /** Representa o nome de um ator */
    private String nome;
    /** Lista com os alugueres realizados pelo ator */
    protected List<OcorrenciaAluguer> historial;
    /** Total de pontos que lhe foram atribuidos na classificaçao */
    private float classificacao;  
    /** nº de vezes que foi classificado */
    private int nClassificacoes;
    
    
    /**
     * Construtor para objetos da classe Atores por omissao
     */
    public Ator()
    {
        this.nif = "0";
        this.nome ="Matumbino";
        this.email="Matumbo";
        this.password="2";
        this.morada="The shire";
        this.dataNascimento = LocalDateTime.now();
        this.historial = new ArrayList<OcorrenciaAluguer>();
        this.classificacao = 0;
        this.nClassificacoes=0;
    }
    /** 
    * Construtor por parametros
    * @nif
    * @param email
    * @param pw
    * @param nome
    * @param morada
    * @param dataNascimento
    */
    public Ator(String nif, String email, String pw,String nome,String morada, LocalDateTime dataNascimento)
    {
        this.nif = nif;
        this.email = email;
        this.password = pw;
        this.nome = nome;
        this.morada=morada;
        this.dataNascimento=dataNascimento;
        this.historial = new ArrayList<OcorrenciaAluguer>();
        this.classificacao=0;
        this.nClassificacoes=0;
    }
    /**
     * Construtor por copia
     */
    public Ator (Ator umAtor)
    {
        this.nif = umAtor.getNif();
        this.nome = umAtor.getNome();
        this.email = umAtor.getEmail();
        this.password = umAtor.getPassword();
        this.morada = umAtor.getMorada();
        this.dataNascimento = umAtor.getDataNascimento();
        this.historial = umAtor.getHistorial();
        this.classificacao=umAtor.getClassificacao();
        this.nClassificacoes=umAtor.getNClassificacoes();
    } 
    /**
     * Compara atores
     * @return 'true' se forem iguais
     */
    public boolean equals(Object obj)
    {
        if(this == obj)
        return true; // se têm o mesmo endereço
        if( (obj == null) || (this.getClass() != obj.getClass()))
        return false; // se o objeto é nulo ou então não tem a mesma classe que o cliente 
        Ator c = (Ator) obj;
        return  this.email.equals(c.getEmail());
    }
    /** Cria uma representacao textual do ator */
    public String toString()
    {
        StringBuilder s = new StringBuilder ();
        s.append("Nome: " ).append(this.getNome());
        s.append("\nNif: ").append(this.getNif());
        s.append("\nEmail: ").append( this.getEmail());
        s.append("\nMorada: ").append( this.getMorada());
        s.append("\nClassificação: ").append(Math.round(getClassificacao() * 100f) / 100f)
        .append("/100(").append(getNClassificacoes()).append(")");
        return s.toString();
    }
    /** Cria um clone do ator */
    public abstract  Ator clone();
    
    /** Retorna o nif*/
    public String getNif(){return this.nif;};
    /** Retorna email */
    public String getEmail(){return this.email;}
    /** Retorna password */
    public String getPassword(){return this.password;}
    /** Retorna nome */
    public String getNome(){return this.nome;}
    /** Retorna morada */
    public String getMorada(){return this.morada;}
    /** Retorna data de nascimento */
    public LocalDateTime getDataNascimento(){return this.dataNascimento;}
    /** Retorna o historial de alugueres*/
    public List<OcorrenciaAluguer> getHistorial(){
        List <OcorrenciaAluguer> res = new ArrayList<OcorrenciaAluguer>();
        for(OcorrenciaAluguer a: this.historial){
            res.add(a.clone());
        }
     return res;
     }
    /** Retorna a classificaçao do utilizador*/ 
    public float getClassificacao(){return this.classificacao;}
    /** Retorna o numero de vezes que foi classificado*/
    public int getNClassificacoes(){return this.nClassificacoes;}
    /** Altera o nif*/
    public void setNif(String a){this.nif = a;}
    /** Altera o email*/
    public void setEmail(String a) {this.email=a;}
    /**Altera a password*/
    public void setPassword(String a) {this.password=a;}
    /** Altera o nome*/
    public void setNome(String a) {this.nome=a;}
    /** Altera a morada*/
    public void setMorada(String a) {this.morada=a;}
    /** Altera a data de nascimento*/
    public void setDataNascimento(LocalDateTime a) {this.dataNascimento=a;}
    /** Altera o historial de alugueres */
    public void setHistorial(List <OcorrenciaAluguer> l)
    {
        this.historial = new ArrayList<>();
        for(OcorrenciaAluguer a : l){
            this.historial.add(a);
        }
    }
    /** Altera o numero de classificacoes */
    public void setNumClassificacao(int a) {this.nClassificacoes = a;}
    /** Altera a classificacao */
    public void setClassificacao(float c){ this.classificacao = c;}
    
    /**
     * recebe uma nova classificaçao e atualiza a classificaçao geral do cliente
     */
    public void classificaAtor( int c)
    {
        float sum = nClassificacoes*classificacao;
        nClassificacoes++;
        this.classificacao = (sum+c) / nClassificacoes ;
    }
    /** Retorna a classificacao do ator */
    public float devolveClassificacao(){
    Float classificacao = getClassificacao();
    // arredonda para duas casas decimais
    BigDecimal bd = new BigDecimal(Float.toString(classificacao));
    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    return bd.floatValue();
    }
    /** Adiciona uma ocorrencia de aluguer a lista de ocurrencias de alugueres */
    public void adicionaOcorrenciaAluguer(OcorrenciaAluguer o){
    this.historial.add(o);
    }
    
    /** Devolve a informaçao da utltima viagem*/
    public OcorrenciaAluguer getUltimaViagem(){
    return this.historial.get(0).clone();
    }
}