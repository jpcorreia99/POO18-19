
import UMCarroJaExceptions.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.Arrays;

import java.time.LocalDateTime;
import java.io.Serializable;

import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
/**
 * Classe onde se encontra e é gerida toda a informação da aplicação, nomeadamente utilizadores e viaturas.
 *
 * @author grupo poo
 * @version (a version number or a date)
 */


public class BancoInformacao implements Serializable
{ 
  /** Frota de carros*/
  private Map<String,Viatura> viaturas;
  /** Map de utilizadores*/
  private Map<String,Ator> utilizadores;  
  /** Utilizador atual */
  private Ator utilizador;
  /** 
   * Construtor por omissao
   */
  public BancoInformacao(){
    this.viaturas = new HashMap<String,Viatura>();
    this.utilizadores = new HashMap<String,Ator>();
    }
    
    /**
     * Construtor por parametros
     * @param frota
     * @param utilizadores
     * @param utilizador
     */
  public BancoInformacao(Map<String,Viatura> viaturas, Map<String,Ator> utilizadores, Ator utilizador){
    this.viaturas = new HashMap<String,Viatura>();
    for(Viatura v : viaturas.values()){
     this.viaturas.put(v.getMatricula(),v.clone());
    }
    this.utilizadores = new HashMap<String, Ator>();
    for (Ator a : utilizadores.values()){
     this.utilizadores.put(a.getEmail(),a.clone()); // estou a usar email como key para o hashmap
    }
    this.utilizador = utilizador; // nao sei se deve ser dado o utilizador ou um clone
   }
  /** Construtor por copia */
  public BancoInformacao(BancoInformacao outroBanco){
    this.viaturas = outroBanco.getViaturas();
    this.utilizador = outroBanco.getUtilizador();
    this.utilizadores = outroBanco.getUtilizadores();
    }
  
  /**
 * Devolve o hashmap que contem os viaturas do sistema
 */
  public Map<String,Viatura> getViaturas(){
  Map<String,Viatura> res = new HashMap<>();
  for(Viatura v : viaturas.values()){
  res.put(v.getMatricula(),v.clone());
  }
  return res;
  }
  
  
  /** Devolve os utilizadores */
  public Map<String,Ator> getUtilizadores(){
    Map<String,Ator> res = new HashMap<>();
    for(Ator a : this.utilizadores.values()){
    res.put(a.getEmail(),a.clone());
    }
    return res;
    }
  /** Devolve o utilizador */
  public Ator getUtilizador(){return this.utilizador;} 
  /** Altera a frota */
  public void setViaturas(Map<String,Viatura> outrasViaturas){
   this.viaturas=new HashMap<>();
   for(String matricula : outrasViaturas.keySet()){
    this.viaturas.put(matricula,outrasViaturas.get(matricula).clone());
   }  
    }
  /**Altera o map de utilizadores */
  public void setUtilizadores(Map<String,Ator> utilizadores){
    this.utilizadores=new HashMap<>();
    for(Ator a: utilizadores.values()){
    this.utilizadores.put(a.getEmail(),a.clone());
    }
  }
  /** Altera o utilizador */
  public void setUtilizador(Ator a){this.utilizador = a.clone();}
  
  /**
   * Metodo equals
   */
  public boolean equals(Object o){
    if(o == this) return true;
    if(o == null || o.getClass()!=this.getClass()) return false;    
    BancoInformacao bi =(BancoInformacao) o;
    return (this.viaturas.equals(bi.getViaturas()) 
      && this.utilizadores.equals(bi.getUtilizadores()))
      && this.utilizador.equals(bi.getUtilizador());    
    }
  /** Cria um clone */
  public BancoInformacao clone(){
    return new BancoInformacao(this);
    }  
  
  /** Devolve uma representaçao textual do objeto*/
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("Viaturas").append(viaturas.toString())
                          .append("\n\nUtilizadores: \n").append(utilizadores.toString())
                          .append("\n\nUtilizador: ").append(utilizador.toString());
    return sb.toString();                      
    } 
    
//-- Metodos relativos a manipulaçao das viaturas     
    
            
    
    /** Insere uma viatura na frota */
  public void registaViatura(Viatura viatura) throws JaExisteViaturaException{
    if(existeViatura(viatura.getMatricula())){
    throw new JaExisteViaturaException(viatura.getMatricula());
    }else{
    this.viaturas.put(viatura.getMatricula(),viatura.clone());
    }
    }

  /** 
  * Devolve uma viatura dada a sua matricula
  */
  public Viatura devolveViatura(String matricula){
  for(Viatura v : viaturas.values()){
    if(v.getMatricula().equals(matricula)){
     return v.clone();
   }
  }
  return null;// esta linha nunca chega a ser executada visto que o metodo so e chamado se a matricula existir no sistema

  }    
  /** Verifica se ja existe uma viatura com a matricula indicada*/
  public boolean existeViatura(String matricula){
    return viaturas.containsKey(matricula);
  }
    
 /**
 * Aluga uma viatura a um cliente
 */
  public void alugaViatura(Cliente c, String matricula){
  viaturas.get(matricula).alugaViatura(c);
  }    
    
  
 /**
 * Elimina um carro do hashmap
 */

 public void eliminaCarro(String matricula){
 if(existeViatura(matricula)){
  viaturas.remove(matricula);
 }
 }

 /**
 * Altera algum valor a um carro, consoante a opçao
 * 1-altera a marca
 * 2-alterar a posicao do carro  
 * 3-altera a vMedia
 * 4-altera o preço base por km
 * 5-altera a disponibilidade da viaturam para aluguer
 * 6-altera o tamanho do deposito/bateria da viatura
 * 7-altera o consumo da viatura 
 * 8-abastece a viatura
 * 9-abastece o motor secundario
 */  
 //IMPORTANTE: LEMBRAR DE VERIFICAR SE ESTAMOS A DAR O OBJETO CERTO QUANDO ESTAMOS A PEDIR NA MAIN
public void alteraViatura(int opcao, String matricula, Object o){
if (existeViatura(matricula)){
   Viatura v = viaturas.get(matricula);
   switch(opcao){        
    case 1: 
    v.setMarca((String)o);
    break;
    case 2:
    v.setPosViatura((Ponto) o);
    break;
    case 3:
    v.setVMedia((double)o);
    break;
    case 4:
    v.setPrecoBaseKm((double)o);
    break;
    case 5:
    v.setEstado((boolean)o);
    break;
    case 6: 
    ((Abastecivel)v).setTamanhoDeposito((int) o);
    break;
    case 7:
    ((Abastecivel)v).setConsumo((double) o);
    break;
    case 8:
    ((Abastecivel)v).abastece((double) o);
    break;
    case 9:
    ((CarroHibrido)v).abasteceAuxiliar((double)o);
    break;
 // fazemos cast para dar certo os tipos
   }       
}
} 

/**
* Devolve os viaturas sobre a forma da classe CarroInfo ordenados pela distancia ao cliente,
*/
public Set<ViaturaInfo> ordenaDistCliente(Cliente cliente,int filtroCombustivel){
 Set<ViaturaInfo> ts = new TreeSet<>(new ComparadorDistCliente());
 for(Viatura viatura  : this.viaturas.values()){
  if (filtraCarroMotor(viatura,filtroCombustivel) &&viatura.viagemPossivel(cliente.getPosDestino())){
       ts.add(new ViaturaInfo(viatura,cliente));
     }
 }
 return ts; 
}

    
/**
* Devolve os viaturas ordenador por custo de viagem
* Recebe tambem um argumento distanciaLimite que, se positivo, filtra os viaturas que estao a uma distancia menor qure esse argumento
*/
public Set<ViaturaInfo> ordenaCustoViagem(Cliente cliente, int distanciaLimite, int filtroCombustivel){
 Set<ViaturaInfo> ts = new TreeSet<>(new ComparadorCustoViagem());
 boolean inDistancia = false;
 for(Viatura viatura : this.viaturas.values()){
  if((viatura.distancia(cliente.getPosCliente())*1000<=distanciaLimite))
  inDistancia= true;
  else
  inDistancia=false;
  if (viatura.viagemPossivel(cliente.getPosDestino()) && inDistancia && filtraCarroMotor(viatura,filtroCombustivel)){
       ts.add(new ViaturaInfo(viatura,cliente));
  }
 }
 return ts;

}
  
/**
* Devolve os viaturas ordenador por custo de viagem
  */
public Set<ViaturaInfo> ordenaCustoViagem(Cliente cliente, int filtroCombustivel){
 Set<ViaturaInfo> ts = new TreeSet<>(new ComparadorCustoViagem()); 
 for(Viatura viatura : this.viaturas.values()){
  if (filtraCarroMotor(viatura,filtroCombustivel) && viatura.viagemPossivel(cliente.getPosDestino())){
       ts.add(new ViaturaInfo(viatura,cliente));
 }
 }
 return ts;
}
  
/**
 * Devolve todos os viaturas disponiveis
 */
public Set<ViaturaInfo> viaturasDisponiveis(Cliente cliente,int filtroCombustivel){
  return this.viaturas.values().stream()
                    .filter(v-> v.viagemPossivel(cliente.getPosDestino()) && filtraCarroMotor(v,filtroCombustivel))
                    .map(v-> new ViaturaInfo(v,cliente))
                    .collect(Collectors.toSet());
}

/**
 * Devolve todos os viaturas disponiveis dentro de um certo intervalo de consumo
 * 
 */
public Set<ViaturaInfo> filtraConsumo(Cliente cliente,double consumo,int filtroCombustivel){ 
  
  return this.viaturas.values().stream()
                    .filter(v-> v instanceof Carro)
                    .filter(v-> v.viagemPossivel(cliente.getPosDestino()) && filtraCarroMotor(v,filtroCombustivel) &&
                          ((Abastecivel)v).getConsumo() >=consumo-0.02 && ((Abastecivel) v).getConsumo() <=consumo+0.02)
                    .map(c-> new ViaturaInfo(c,cliente))
                    .collect(Collectors.toSet());
}
    
/**
* Devolve os viaturas pertencentes a um utilizador 
*/     
public List<Viatura> viaturasUtilizador(String idDono){
   return this.viaturas.values().stream()
                      .filter(v->v.getIdDono().equals(idDono))
                      .map(Viatura :: clone)
                      .collect(Collectors.toList());
}  

/** 
 * Devolve todos os viaturas disponiveis pertencentes a uma marca
 */    
public Set<ViaturaInfo> filtraMarca(Cliente cliente, String marca,int filtroCombustivel){
 return this.viaturas.values().stream()
                    .filter(v-> v.getMarca().equals(marca) 
                     && v.viagemPossivel(cliente.getPosDestino()) && filtraCarroMotor(v,filtroCombustivel))
                    .map(c->new ViaturaInfo(c,cliente))
                    .collect(Collectors.toSet());
}

/**
* Devolve os viaturas pertencentes a um utilizador 
*/     
public List<Viatura> viaturasUtilizadorAbasteciveis(String idDono){
   return this.viaturas.values().stream()
                      .filter(v->v.getIdDono().equals(idDono) && v instanceof Abastecivel)
                      .map(Viatura :: clone)
                      .collect(Collectors.toList());
} 




/** 
 * Devolve se a viatura corresponde ao filtro indicado
 * 0 - nao filtra
 * 1 - combustao
 * 2 - eletrico
 * 3 - hibrido
 */    
public boolean filtraCarroMotor(Viatura viatura, int filtroCombustivel){
  switch(filtroCombustivel){
    case 1: return ((viatura instanceof CarroCombustao) || (viatura instanceof CarroHibrido));            
    case 2: return ((viatura instanceof CarroEletrico) || (viatura instanceof CarroHibrido));
    case 3: return (viatura instanceof CarroHibrido);
    default : return true;        
   }
}





//--------------    
    
  /** Verifica se existe determinado utilizador no map 'utilizadores' */
  public boolean existeUtilizador(String email){return utilizadores.containsKey(email);} 
  /** Verifica se existe determinado utilizador com esse nif */
  public boolean existeNif(String nif){
     boolean encontrou=false;
     for(Ator a : this.utilizadores.values()){
        if(a.getNif().equals(nif)){
         encontrou = true;
        }
     }
     return encontrou;
    }
  
  /** Devolve o historial do utilizador atual*/
  public List<OcorrenciaAluguer> historialUtilizador(){
    return utilizador.getHistorial();
    }
   
  /** Devolve o historial do utilizador atual entre duas datas*/
  public List<OcorrenciaAluguer> historialUtilizadorDatas(LocalDateTime inicio,LocalDateTime fim){
    List<OcorrenciaAluguer> historial = utilizador.getHistorial();
    List<OcorrenciaAluguer> res = new ArrayList<>();
    for(OcorrenciaAluguer o : historial){
      LocalDateTime data =o.getData();
      if(data.isAfter(inicio) && data.isBefore(fim)){
       res.add(o);
      }
    } 
    return res;
  }
    
  
  /** Envia um pedido de aluguer para o proprietario da viatura que se pretende alugar*/
  public void enviaPedidoAluguer(String matricula){
    Viatura viatura = devolveViatura(matricula); 
    String idDono = viatura.getIdDono();
    Proprietario p= (Proprietario)utilizadores.get(idDono);
    Cliente cliente= (Cliente) utilizador;
    cliente.setEstadoPedido(1);//indica que um pedido esta a ser procesado
    p.adicionaPedido(new OcorrenciaAluguer(cliente,viatura));
    }
    
  /** O proprietario aceita o pedido de aluguer e o cliente faz a viagem
     * Procedimento:
     * Processa-se o aluguer no que concerne a viatura utilizando o metodo existente na classe Frota
     * Processa-se o aluguer no que concerne ao cliente utilizando o metodo existente na classe Cliente
     * Adiciona-se a ocorrencia ao historial do proprietario
     * Elimina-se o pedido da lista dos pedidos do propietario do CarroCombustao 
     */
  public void aceitaPedido(){
    OcorrenciaAluguer o =((Proprietario)this.utilizador).getPedido();
    
    String clienteId = o.getEmailCliente();
    Cliente cliente = (Cliente) utilizadores.get(clienteId);
    cliente.aluga(o.getViatura());
    cliente.setEstadoPedido(3);//indica ao utilizador que tera de avaliar a viagem quando fizer login
    
    String matricula = o.getViatura().getMatricula();
    alugaViatura(cliente,matricula);

    Proprietario prop =(Proprietario)utilizador;
    prop.aceitaPedido();      
  }
  
  /** Elimina o pedido de aluguer da lista de pedidos do utilizador*/
  public void rejeitaPedido(){
    OcorrenciaAluguer o =((Proprietario)this.utilizador).getPedido();
    ((Proprietario)this.utilizador).eliminaPedido();
    String clienteId = o.getEmailCliente();
    Cliente cliente = (Cliente) utilizadores.get(clienteId);
    cliente.setEstadoPedido(2);
    }

  /** Metodo que consulta o aluguer que esta por classificar e classifica a viatura desse aluguer*/  
  public void classificaViatura(int classificacao){
    OcorrenciaAluguer ultimaViagem = utilizador.getUltimaViagem();
    String matricula = ultimaViagem.getViatura().getMatricula();
    viaturas.get(matricula).classificaViatura(classificacao);
    ((Cliente)this.utilizador).setEstadoPedido(0);    
    String idDono = ultimaViagem.getViatura().getIdDono();
    Ator dono = utilizadores.get(idDono);
    dono.classificaAtor(classificacao);
    }    

  /** Devolve a lista com os pedidos de aluguer */
  public List<OcorrenciaAluguer> getPedidosAluguer() {
    return ((Proprietario) this.utilizador).getPedidosAluguer();
    } 
    

  /** Metodo que classifica o cliente que efetuou o aluguer*/  
  public void classificaCliente(int classificacao,OcorrenciaAluguer o){
    String emailCliente=o.getEmailCliente();
    Cliente cliente = (Cliente)utilizadores.get(emailCliente);
    cliente.classificaAtor(classificacao);
    ((Proprietario)this.utilizador).classificouViagem();
    } 
    
  /**Regista o cliente na aplicaçao*/
  public void registaCliente(String nif, String email,String password,String nome, String morada, LocalDateTime dataNascimento,double posX, double posY)
  throws UtilizadorJaExisteException
  {
      if(!utilizadores.containsKey(email)){
       Cliente cliente = new Cliente(nif,email,password,nome,morada,dataNascimento,posX,posY);
       this.utilizadores.put(cliente.getEmail(),cliente);
      }else{
        throw new UtilizadorJaExisteException(email);
      }

  }

  /** O proprietario é registado na aplicaçao*/
  public void registaProp(String nif, String email,String password,String nome, String morada, LocalDateTime dataNascimento)
  throws UtilizadorJaExisteException{   
      if(!utilizadores.containsKey(email)){
       Proprietario proprietario = new Proprietario(nif,email,password,nome,morada,dataNascimento);
       this.utilizadores.put(proprietario.getEmail(),proprietario);
      }else{
        throw new UtilizadorJaExisteException(email);
        }
  }  
  
  /** Faz login do utilizador
     */
  public void loginUtilizador(String email,String password) throws CredenciaisErradasException,UtilizadorNaoExisteException,Exception 
  {     
      if(utilizadores.containsKey(email)){
          Ator a = utilizadores.get(email); 
          if (a.getPassword().equals(password))
          {
              this.utilizador = a;
              ControloAPIs.updateIP();
          }else{
            throw new CredenciaisErradasException();
            }
      }else{
        throw new UtilizadorNaoExisteException();
        }
  }
  
  /**
   * Metodo que posiçiona o utilizador quando ele requere uma viagem
   * Da-se a posiçao de partida e a posiçao de destino*/
  public void posicionaCliente(double posXC, double posYC, double posXD, double posYD){
    Ponto partida = new Ponto(posXC,posYC);
    Ponto destino = new Ponto(posXD,posYD);
    ((Cliente) utilizador).setPosCliente(partida);
    ((Cliente) utilizador).setPosDestino(destino);
    }

    /** Devolve os 10 clientes mais ativos em numero de alugueres*/
    public List<Cliente> topClientesNumAlugueres(){
   Set<Cliente> ts = new TreeSet<>(new ComparadorNumAlugueres());
   for(Ator a : this.utilizadores.values()){
    if (a instanceof Cliente)
    {
       ts.add(((Cliente)a).clone());
    }
   }
   
   Iterator<Cliente> it = ts.iterator();
   int count = 0;
   List<Cliente> res = new ArrayList<>();
   while (it.hasNext()&&count<10) {
         res.add(it.next());
         count++;
    }
   return res;   
   }
   
    /** Devolve os 10 clientes com maior distancia percorrida */
  public List<Cliente> topClientesTotalKms(){
   Set<Cliente> ts = new TreeSet<>(new ComparadorTotalKms());
   for(Ator a : this.utilizadores.values()){
    if (a instanceof Cliente)
    {
       ts.add(((Cliente)a).clone());
    }
   }
   
   Iterator<Cliente> it = ts.iterator();
   int count = 0;
   List<Cliente> res = new ArrayList<>();
   while (it.hasNext() && count <10) {
         res.add(it.next());
         count++;
    }
   return res;   
   }   

 // ----- relativo a ficheiros   
   
   /** Guarda o estado da aplicaçao utilizando o FObjecOutputStream*/
  public void guardaEstado(String nomeFicheiro) throws FileNotFoundException, IOException{
    FileOutputStream fos = new FileOutputStream(nomeFicheiro);
    ObjectOutputStream oos = new ObjectOutputStream(fos);    
    oos.writeObject(this);
    oos.flush();
    oos.close();
    }    
    
  /** Carrega o estado da aplicaçao usando o ObjectInputStream*/  
  public static BancoInformacao carregaEstado(String nomeFicheiro) throws FileNotFoundException,IOException,ClassNotFoundException{
    FileInputStream fis = new FileInputStream(nomeFicheiro);
    ObjectInputStream ois = new ObjectInputStream(fis);
    BancoInformacao bi = (BancoInformacao)ois.readObject();
    ois.close();   
    return bi;
    }   

  /** Interpreta a lista de strings que representam o ficheiro de logs*/
  public List<Exception> interpretaLogs(List<String> logs) {
        List<Exception> excecoes = new ArrayList<Exception>();
     for(String s : logs){         
        String[] parts = s.split(":|\\,");        
        if(parts[0].equals("NovoCliente")){
          try{
          LocalDateTime dataNascimento = LocalDateTime.of(1990,1,1,0,0);             
          registaCliente(parts[2],parts[3],"1",parts[1],parts[4],dataNascimento,Double.parseDouble(parts[5]),Double.parseDouble(parts[6]));
         }catch(UtilizadorJaExisteException e){
          excecoes.add(e);   
         }
            
            
        }else if(parts[0].equals("NovoProp")){
          try{  
           LocalDateTime dataNascimento = LocalDateTime.of(1990,1,1,0,0);
           registaProp(parts[2],parts[3],"1",parts[1],parts[4],dataNascimento);
          }catch(UtilizadorJaExisteException e){
            excecoes.add(e);
          }
                    
        }else if(parts[0].equals("NovoCarro")){
            //tipo,marca,matricula,nif,velocidade media,preço por km, consumo por km, autonomia, X, Y
            //String matricula,String idDono,String marca, ,Ponto posCarro, double vMedia,double precoBaseKm, int tamanhoDeposito, double consumo, double combustivel)
          Ponto posCarro= new Ponto(Double.parseDouble(parts[9]),Double.parseDouble(parts[10]));
          int tamanhoDeposito = (int) (Double.parseDouble(parts[8])*Double.parseDouble(parts[7])); //autonomia*consumo
          if(!existeViatura(parts[2])){
              Viatura c = null;
            if(parts[1].equals("Electrico")){
            c = new CarroEletrico(parts[3],parts[4]+"@gmail.com",parts[2],posCarro,Double.parseDouble(parts[5]),
                                 Double.parseDouble(parts[6]), tamanhoDeposito, Double.parseDouble(parts[7]),tamanhoDeposito);
            }else if(parts[1].equals("Gasolina")){
            c =new CarroCombustao(parts[3],parts[4]+"@gmail.com",parts[2],posCarro,Double.parseDouble(parts[5]),
                                 Double.parseDouble(parts[6]), tamanhoDeposito, Double.parseDouble(parts[7]),tamanhoDeposito);
            }else if(parts[1].equals("Hibrido")){
            c =new CarroHibrido(parts[3],parts[4]+"@gmail.com",parts[2],posCarro,Double.parseDouble(parts[5]),
                                 Double.parseDouble(parts[6]), tamanhoDeposito-20, Double.parseDouble(parts[7]),tamanhoDeposito-20,
                                 20,Double.parseDouble(parts[7]),20);//retiraram-se 20 litros da capacidade principal para a dar ao motor auxiliar
            
            }
            try{
            registaViatura(c);
            }catch(JaExisteViaturaException e){
             excecoes.add(e);
            }
            
        }        
       }
       //--Aluguer: nif cliente, X destino, Y destino, tipoCombustivel , preferencia
       else if(parts[0].equals("Aluguer")){
           try{
            processaAluguerLogs(parts[1]+"@gmail.com", Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), parts[4], parts[5]);
           }catch(AluguerImpossivelException e){
            excecoes.add(e);
           }
        }else if(parts[0].equals("Classificar")){
        if(parts[1].length()==9){
         try{
            classificaAtorLogs(parts[1]+"@gmail.com", Integer.parseInt(parts[2]));
         }catch(UtilizadorNaoExisteException e){
            excecoes.add(e);
         }
        }else{
         try{   
            classificaViaturaLogs(parts[1], Integer.parseInt(parts[2]));
         }catch(ViaturaNaoExisteException e){
            excecoes.add(e);  
         }
       }       
    }    
  }
  return excecoes;
}
  
  /** Como o processamento de um aluguer dos logs e diferente do processamento normal, tem de se criar um metodo que 
   * "evita" o envio de um pedido ao proprietario do carro */ 
  public void processaAluguerLogs(String idCliente, double posXDest, double posYDest,String tipoCombustivel,String preferencia)
    throws AluguerImpossivelException{
      if(existeUtilizador(idCliente)){
       this.utilizador = this.utilizadores.get(idCliente);
       Cliente cliente=(Cliente)this.utilizador;
       posicionaCliente(cliente.getPosCliente().getX(),cliente.getPosCliente().getY(),posXDest,posYDest);   
       String matricula="";
       int filtroCombustivel =0;
       if(tipoCombustivel.equals("Gasolina")){ filtroCombustivel= 1;}else{filtroCombustivel=2;} 
       if(preferencia.equals("MaisPerto")){
        matricula=ordenaDistClienteLogs(cliente,filtroCombustivel); //comment
        }else{
        matricula=ordenaCustoViagemLogs(cliente,filtroCombustivel);
       }
       if(!matricula.equals("")){
        Viatura viatura = viaturas.get(matricula);
        //criaçao da ocorrencia
        OcorrenciaAluguer o = new OcorrenciaAluguer(cliente,viatura);       
        o.setFoiClassificada(true);//para evitar a mecanica da nossa aplicaçao em que se mostram que viagens ainda nao foram classificadas
       // processamento do proprietario
        String idDono = viatura.getIdDono();
        Proprietario prop =(Proprietario)utilizadores.get(idDono);
        prop.adicionaOcorrenciaAluguer(o);
        // processamento da viatura
        viatura.alugaViatura(cliente);//processamento do aluguer no que concerne a viatura
        cliente.aluga(o.getViatura());//processamento do aluguer no que concerne o cliente
              

        }else{throw new AluguerImpossivelException ("x: "+posXDest+", y: "+posYDest);}
      }
     
    }
       
    
    /** Classifica um utilizador dado o seu id(email) e uma classificacao de 1 a 100*/
    public void classificaAtorLogs(String idAtor,int classificacao) throws UtilizadorNaoExisteException{
    if(existeUtilizador(idAtor)){
     Ator ator = utilizadores.get(idAtor);
     ator.classificaAtor(classificacao);
    }else{
     throw new UtilizadorNaoExisteException(idAtor);
    }
    }

/** Classifica uma viatura dada a sua matricula e um valor de 1 a 100*/
public void classificaViaturaLogs(String matricula ,int classificacao) throws ViaturaNaoExisteException{
 if(existeViatura(matricula)){
  Viatura v = devolveViatura(matricula);
  viaturas.get(matricula).classificaViatura(classificacao);
  }else{
   throw new ViaturaNaoExisteException(matricula);
  }
}


/**
* Devolve a matricula da viatura mais perto do utilizador
*/
public String ordenaDistClienteLogs(Cliente cliente,int filtroCombustivel){
 String matricula = "";
 double dist = -1;
 Ponto posCliente = cliente.getPosCliente();
 Ponto posDestino = cliente.getPosDestino();
 for(Viatura viatura  : this.viaturas.values()){
       if(filtraCarroMotor(viatura,filtroCombustivel) && viatura.viagemPossivel(posDestino) 
       && (dist==-1 || dist>viatura.distancia(posCliente))){
        dist =  viatura.distancia(posCliente);  
        matricula = viatura.getMatricula();
       }
 }
 return matricula; 
}



/**
* Devolve a matricula da viatura mais barata em que se pode fazer a viagem
  */
public String ordenaCustoViagemLogs(Cliente cliente, int filtroCombustivel){
 String matricula =""; 
 double custo=-1;
 Ponto posCliente = cliente.getPosCliente();
 Ponto posDestino = cliente.getPosDestino();
 float classificacao = cliente.getClassificacao();
 for(Viatura viatura : this.viaturas.values()){
      if(filtraCarroMotor(viatura,filtroCombustivel) && viatura.viagemPossivel(posDestino) 
       && (custo==-1 || custo>viatura.custoViagem(posDestino,classificacao))){
        custo = viatura.custoViagem(posDestino,classificacao);
        matricula = viatura.getMatricula();
       } 
 }
 return matricula;
}

}
