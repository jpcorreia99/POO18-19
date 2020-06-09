import UMCarroJaExceptions.*;
import java.util.Set;
import java.util.TreeSet;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;z
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Classe principal da aplicaçao onde sao geridos principalmente os menus
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{   
    
    private static  BancoInformacao info;
    private static NavegadorDeLista navegador;
    
    public static void main(String [] args){       
       Input.limpaTela();
       initApp("dados/dadosApp.txt");
       navegador = new NavegadorDeLista();     
       menuInicial();
       System.out.println("A guardar os dados da aplicação"); 
       guardaEstado("dados/dadosApp.txt");
       System.out.println("Guardados! Tenha um bom dia!"); 
    }
 

    /** Metodo encarregue de gerir o carregamento de dados para a aplicaçao*/
    public static void initApp(String ficheiro){
    List<Exception> excecoes = new ArrayList<>();    
    try{
    System.out.println("A Carregar os dados...\n");    
    info = BancoInformacao.carregaEstado(ficheiro);
    }catch(FileNotFoundException e){
      System.out.println("O ficheiro indicado não existe, caso isto seja a primeira utilização, é algo normal\n");
      System.out.println("A processar os logs...\n");
      excecoes = leFicheiroLogs("dados/logsPOO_carregamentoInicial.bak");
    }catch(IOException e){
      System.out.println("IO exception\n");
      System.out.println("A processar os logs...\n");
      excecoes = leFicheiroLogs("dados/logsPOO_carregamentoInicial.bak");
    }catch(ClassNotFoundException e){
      System.out.println("O objeto existente no ficheiro não corresponde ao que se pretende ler");
      System.out.println("A processar os logs...\n");
      excecoes = leFicheiroLogs("dados/logsPOO_carregamentoInicial.bak");
    }
    
    if(!excecoes.isEmpty()){
     processaExceptionsLogs(excecoes);
    }
    }
    
    /** Metodo responsavel por ler o ficheiro de logs e o transformar em string interpretaveis*/
    public static List<Exception> leFicheiroLogs(String ficheiro){
     info = new BancoInformacao();   
     BufferedReader reader = null;
     List<String> linhas = new ArrayList<>();
     try {   
     reader = new BufferedReader(new FileReader(ficheiro));
     String line=null;
     while ((line = reader.readLine()) != null) {
         if(line.length()>0){
         linhas.add(line);
        }
     }
     } catch (IOException e) {
       System.out.println("Erro a ler as linhas do ficheiro");
     }
     // remover o cabeçalho
     for(int i=1;i<=8;i++)
     {
     linhas.remove(0);
     } 
     List<Exception> res = info.interpretaLogs(linhas);
     return res;
    }  
    
    /** Metodo responsavel por avisar o utilizador de todos os erros que ocorreram com o carregamento de dados*/
    public static void processaExceptionsLogs(List<Exception> listaExcecoes){
     System.out.println("Erros ao processar os logs: ");
    for( Exception e : listaExcecoes){
     if(e instanceof UtilizadorJaExisteException) 
       System.out.println("-Tentou-se registar um utilizador que já existe com o mail "+e.getMessage());
     else if(e instanceof UtilizadorNaoExisteException)
       System.out.println("-Tentou-se classificar um utilizador não existente com o mail "+e.getMessage()); 
     else if(e instanceof AluguerImpossivelException) 
       System.out.println("-Foi impossível realizar a viagem para "+e.getMessage());
     else if(e instanceof JaExisteViaturaException)
       System.out.println("-Tentou-se registar uma viatura que já existe com a matrícula "+e.getMessage());
     else if(e instanceof ViaturaNaoExisteException)
       System.out.println("-Tentou-se classificar uma viatura que não existe com a matrícula "+e.getMessage());
    }
   }
    
   /** Metodo responsavel por carregar os dados da aplicaçao para um ficheiro*/
   public static void guardaEstado(String ficheiro){
    try {
    info.guardaEstado(ficheiro);
    }
    catch(NullPointerException e){
    System.out.println("Erro ao guardar no ficheiro o ficheiro");
    }catch(FileNotFoundException e){
    System.out.println("Ficheiro não encontrado");
    }catch(IOException e){
    System.out.println("Não foi possível escrever no ficheiro"); 
    }    
    }
    
    /** Menu inicial da nossa aplicaçao, onde s epode escolher registar um utilizador ou fazer login*/
    public static void menuInicial(){  
    int opt = -1;
    while(opt!=0){    
    System.out.println("\n\n\n1-Registo\n2-Login\n\n0-Fechar");
    opt = Input.lerInt("Opção: ","Opção inválida",0,2);
    if(opt == 1){
        Input.registaUtilizador(info);
    }else if(opt==2){
        Input.loginUtilizador(info);
        if(info.getUtilizador() instanceof Cliente){
        menuCliente();
        }else{
        menuProprietario();
        }            
    }
   }
  }
    

   
   /**Menu onde se recolhem as varias informaçoes necessarias para registar uma viatura*/
   public static void menuRegistoViatura(){
    Input.limpaTela();   
    System.out.println("Tipo de viatura: \n1-Carro\n2-Mota\n3-Bicicleta\n");
    int tipo = Input.lerInt("Tipo de viatura: ", "Opçao invalida",1,3);
    String idDono = info.getUtilizador().getEmail();
    boolean ok=false;
    String matricula="";
    while(!ok){
     matricula = Input.lerString("Matrícula da viatura: ","Matrícula inválida");
     if(info.existeViatura(matricula)){
       System.out.println("Essa matrícula já se encontra registada");
     }else{
       ok = true;
    }
     }
    String marca = Input.lerString("Marca da viatura: ", "A string introduzida não é válida");
    double posx = Input.lerDouble("Posição da viatura (coordenada x):","Coordenada não válida");
    double posy = Input.lerDouble("Posição da viatura (coordenada y):","Coordenada não válida");
    Ponto posViatura = new Ponto(posx,posy);
    double vMedia =Input.lerDouble("Velocidade média (km/h): ", "Velocidade não válida",0);
    vMedia= 3600/vMedia;//conversao para s/km
    double precoBaseKm = Input.lerDouble("Preço base por km (€): ","Valor inválido",0);
    Viatura viaturaX = null;
    if (tipo==1) // se for carro
    {   
        System.out.println("\nTipo de carro: \n1-Combustão\n2-Elétrico\n3-Híbrido\n");
        int tipoCarro = Input.lerInt("Tipo de carro: ", "Opção inválida", 1,3);
        if(tipoCarro ==1 || tipoCarro ==3){
         int tamanhoDeposito = Input.lerInt("Tamanho do depósito (em Litros): ","Tamanho inválido",0);
         double consumo =Input.lerDouble("Consumo (Litros/100km): ","Consumo inválido",0)/100;
         double combustivel = Input.lerDouble("Quantidade de combustivel existente (em Litros): ","Quantidade inválida",0,tamanhoDeposito);
          if (tipoCarro ==1){
            viaturaX = new CarroCombustao ( matricula,idDono, marca, posViatura,
            vMedia, precoBaseKm, tamanhoDeposito, consumo, combustivel);  
          }else{
           int capacidadeAuxiliar = Input.lerInt("Tamanho do depósito auxiliar (em Wh)","Tamanho inválido",0);
           double combustivelAuxiliar =Input.lerInt("Quantidade de carga existente de momento (em Wh): ","Tamanho inválido",0,capacidadeAuxiliar); 
           double consumoAuxiliar =Input.lerDouble("Consumo (Wh/100km): ","Consumo inválido",0)/100;
           viaturaX  = new CarroHibrido (matricula,idDono, marca, posViatura,
             vMedia, precoBaseKm, tamanhoDeposito, consumo, combustivel, capacidadeAuxiliar,consumoAuxiliar,combustivelAuxiliar);     
         }
        }else if(tipoCarro ==2)
        {int tamanhoDeposito = Input.lerInt("Tamanho do depósito (em Wh): ","Tamanho inválido",0);
         double consumo =Input.lerDouble("Consumo (Wh/100km): ","Consumo inválido",0)/100;
         double combustivel = Input.lerDouble("Quantidade de combustivel existente (em Wh): ","Quantidade inválida",0,tamanhoDeposito);
         viaturaX = new CarroEletrico (matricula,idDono, marca, posViatura,
           vMedia, precoBaseKm, tamanhoDeposito, consumo, combustivel);
        }
    }
    else if (tipo ==2) // se for mota
    {
        int tamanhoDeposito = Input.lerInt("Tamanho do depósito (em Litros): ","Tamanho inválido",0);
        double consumo =Input.lerDouble("Consumo (Litros/100km): ","Consumo inválido",0)/100;
        double combustivel = Input.lerDouble("Quantidade de combustivel existente (em Litros): ","Quantidade inválida",0);
        viaturaX = new Mota (matricula,idDono, marca,posViatura, vMedia, precoBaseKm, tamanhoDeposito, consumo, combustivel);
    }
    else // se for bicicleta
    {
        System.out.println("\nTipo de bicicleta:\n1-Montanha\n2-Estrada\n");
        int aux = Input.lerInt("Tipo de bicicleta: ", "Opção inválida", 1,2);
        String modelo;
        if (aux==1) modelo ="Montanha";
        else modelo = "Estrada";
        viaturaX  = new Bicicleta (matricula,idDono,marca,posViatura, vMedia, precoBaseKm, modelo); // consumo da bicicleta é 0
        
    }
    try{
    info.registaViatura(viaturaX); // adiciona a bicicleta criada à frota
    }catch(JaExisteViaturaException e){} // nao se faz nada pois ja se verificou antes que a viatura era unica
    Input.limpaTela();  
}
    
   
    
   
    /** Menu onde se apresentam as varias opçoes de um cliente */
   public static void menuCliente (){
    Input.limpaTela();
    Cliente cliente=(Cliente)(info.getUtilizador());
    if(cliente.getEstadoPedido()==3){// se o cliente tiver feito uma viagem que ainda nao classificou
    Input.limpaTela();    
    System.out.println("Bem vindo "+info.getUtilizador().getNome()+"(Cliente) "+", "+info.getUtilizador().devolveClassificacao()+"/100\n");
    int classificacao = Input.lerInt("Classifique a sua viagem de 1 a 100", "Valor inválido",1, 100);
    info.classificaViatura(classificacao);
    }   
    int opt = -1;
    while(opt!=0){
    Input.limpaTela();   
    System.out.println("Bem vindo "+cliente.getNome()+"(Cliente) "+", "+cliente.devolveClassificacao()+"/100 \n");
    System.out.println("Localização: "+ControloAPIs.getCity()+"\nCondições climatéricas: "+ControloAPIs.getWeather()+"\n");
    if(cliente.getClassificacao()>95){
     System.out.println("Visto que tem uma classificação acima de 95, terá direito a um desconto de 10% nas suas viagens.\n\n");
    }
    int estadoPedido =cliente.getEstadoPedido();
    switch (estadoPedido){
    case 1 : System.out.println("\n\nO seu pedido ainda está a ser processado pelo proprietário da viatura\n\n");
             break;
    case 2 : System.out.println ("\n\nO seu pedido foi rejeitado pelo dono da viatura\n\n"); 
             break;
    case 3 : System.out.println("\n\nO seu pedido foi aceite, boa viagem!");
             break;
    }
    System.out.println("Aluguer de carro: ");
    System.out.println("  1-Ordenado pela distância a si\n  2-Ordenado pelo custo de viagem\n  3-Ordenado pelo custo dentro de uma distância a pé");
    System.out.println("  4-Alugar por um id específico\n  5-Alugar por um consumo\n  6-Alugar por marca");
    System.out.println("\n7-Consultar historial\n8-Verificar informação sobre si\n\n0-Log Out\n");
    if(estadoPedido == 1 ){
     while(opt != 0  && opt <7){   
      opt = Input.lerInt("Opção: ","Opção inválida",0,8);
      if(opt>0 && opt<7){ 
        System.out.println("Não pode alugar um carro visto ainda ter um pedido de aluguer pendente");
      }
     }
    }else{
     opt = Input.lerInt("Opção: ","Opção inválida",0,8);
    }
    Input.limpaTela();
    if(opt >=1 && opt<=6){
     String carroEscolhido = menuEscolheViaturaCliente(opt);
     if(!carroEscolhido.equals("0")){
       info.enviaPedidoAluguer(carroEscolhido);
     }   
     opt = -1;//para voltar ao menu de cliente;
    }else if(opt ==7){
        gereHistorial();
        opt = -1;//para voltar ao menu de cliente;
    }else if(opt==8){
        Input.limpaTela();
        System.out.println(cliente.toString());
        System.out.println("Prima qualquer tecla para regressar ao menu anterior");
        Input.next();
        opt=-1;
    }
    Input.limpaTela();
   }
  }
  
    /** Menu onde se apresentam as varias opçoes de um proprietario*/
   public static void menuProprietario(){  
   Input.limpaTela();    
   int opt = -1;
   Proprietario proprietario =(Proprietario)info.getUtilizador();
    while(opt!=0){
    Input.limpaTela();
    System.out.println("Bem vindo "+proprietario.getNome()+"(Proprietário)"+",("+proprietario.devolveClassificacao()+"/100)\n\n");
    System.out.println("Localização: "+ControloAPIs.getCity()+"\nCondições climatéricas: "+ControloAPIs.getWeather()+"\n"); 
    System.out.println("1-Registar Viatura\n2-Verificar pedidos de aluguer("+proprietario.numPedidos()+" pedidos a verificar)\n3-Consultar historial");
    System.out.println("4-Gerir viaturas\n5-Gerir Classificações("+proprietario.numViagensPorClassificar()+" alugueres por classificar");
    System.out.println("6-Top 10 clientes por número de alugueres\n7-Top 10 clientes por distância percorrida\n\n0-Sair");
    opt = Input.lerInt("Opção: ","Opção inválida",0,7);
    if(opt ==1){
        menuRegistoViatura();
    }    
    else if(opt ==2){       
        gerePedidos();
    }else if(opt == 3){
        gereHistorial();
        opt = -1;//para voltar ao menu de cliente;
    }else if(opt == 4){
        String matricula =menuEscolheViaturaProp();
        if(!matricula.equals("0")){
         menuAlteracaoViatura(matricula);
        }
    }else if(opt==5){
        gereClassificacoes();
    }else if(opt==6){
        navegador.criaNavegadorCliente(info.topClientesNumAlugueres());
        navegador.navegaLista();
    }else if(opt==7){
        navegador.criaNavegadorCliente(info.topClientesTotalKms());
        navegador.navegaLista();
    }   
   }
   Input.limpaTela();   
   }
    
   /** Menu que gera os pedidos de aluguer */
   public static void gerePedidos(){
    Input.limpaTela();   
     String opt = "s";
    if(((Proprietario)(info.getUtilizador())).numPedidos()==0){
      Input.limpaTela();
      System.out.println("Não há pedidos de aluguer a mostrar");
      System.out.println(new String(new char[13]).replace("\0", "\r\n"));
      System.out.println("Pressione qualquer tecla para voltar ao menu anterior");
      Input.next();
    }else{
       while(((Proprietario)(info.getUtilizador())).numPedidos()!=0 && (opt.equals("s") || opt.equals("S") ||opt.equals("n") || opt.equals("N"))){
         Input.limpaTela();
         System.out.println("Tem "+ ((Proprietario)(info.getUtilizador())).numPedidos()+" pedidos restantes por gerir\n\n");
         System.out.println("Email                 Matricula   Marca    Data       Hora     Ponto do cliente  Ponto da viatura          Destino              Dist  Custo\n\n");
         OcorrenciaAluguer o =((Proprietario)(info.getUtilizador())).getPedido();
         System.out.println(o.toString());
         System.out.println(new String(new char[13]).replace("\0", "\r\n"));
         System.out.println("Pressione 'S' para aceitar o pedido, 'N' para o rejeitar ou qualquer outra tecla para voltar ao menu anterior");
         opt = Input.next();
         if(opt.equals("s") || opt.equals("S")) {
             info.aceitaPedido();
         }else if(opt.equals("n") || opt.equals("N")){
             info.rejeitaPedido();
         }
       }
    }
    Input.limpaTela();   
    }

   /** Menu onde o proprietario gera as viagens que ainda nao classificou*/
   public static void gereClassificacoes(){
     Input.limpaTela(); 
     int classificacao = 0;
    if(((Proprietario)(info.getUtilizador())).numViagensPorClassificar()==0){
      Input.limpaTela();
      System.out.println("Não há pedidos de aluguer a mostrar");
      System.out.println(new String(new char[13]).replace("\0", "\r\n"));
      System.out.println("Pressione qualquer tecla para voltar ao menu anterior");
      Input.next();
    }else{    
     List<OcorrenciaAluguer> viagensPorClassificar = ((Proprietario)info.getUtilizador()).getViagensPorClassificar();
     for(OcorrenciaAluguer o : viagensPorClassificar){        
         Input.limpaTela();
         System.out.println("Tem "+ ((Proprietario)(info.getUtilizador())).numViagensPorClassificar()+" pedidos restantes por gerir\n\n");
         System.out.println("Nif Cliente  Matricula  Marca      Data     Hora    Ponto do cliente  Ponto da viatura   Destino    Distância  Custo\n\n");        
         System.out.println(o.toString());
         System.out.println(new String(new char[13]).replace("\0", "\r\n"));
         classificacao = Input.lerInt("Classifique o cliente de 1 a 100","Classificação inválida",1, 100);
         info.classificaCliente(classificacao,o);
     }
       }
    Input.limpaTela();      
    }
        

   
    /** Metodo que ordena as viaturas segundo um parametro
     *  Recebe um inteiro que sinaliza a opçao acerca de qual a maneira de ordenar os carros
     * 1-Ordenado pela distância a si  
     * 2-Ordenado pelo custo de viagem
     * 3-Ordenado pelo custo dentro de uma distância a pé
     * 4-Alugar por um id específico
     * 5-Alugar por um consumo
     * 6-Alugar por marca
     * 
     * De seguida o utilizador escolhe uma das viaturas
    */
   public static String menuEscolheViaturaCliente(int opt){
    Input.limpaTela(); 
    Input.lerCoordenadas(info);
    Input.limpaTela();
    System.out.println("\n0-Qualquer viatura\n1-Carro de combustão\n2-Carro elétrico\n3-Carro híbrido\n\nNota: A opção 1 e 2 contém carros híbridos");
    int filtro = Input.lerInt("Opção:","Opção inválida:",0,3);     
    int idCarro=-1;
    String auxId="";
    Set<ViaturaInfo> setViaturaInfo = new TreeSet();
    if(opt == 1){     
     setViaturaInfo = info.ordenaDistCliente((Cliente)info.getUtilizador(),filtro);
    }else if(opt==2){
     setViaturaInfo = info.ordenaCustoViagem((Cliente)info.getUtilizador(),filtro);
    } 
    else if(opt == 3){
     int dist = Input.lerInt("Distância limite a pé (em metros): ","Distância inválida",0);
     setViaturaInfo = info.ordenaCustoViagem((Cliente)info.getUtilizador(),dist,filtro);
    }else if(opt == 4){
     setViaturaInfo = info.viaturasDisponiveis((Cliente)info.getUtilizador(),filtro);
    }else if(opt == 5){  
     double consumo = Input.lerDouble("Introduza o consumo desejado(em L/Km)", "Consumo inválido",0);    
     setViaturaInfo = info.filtraConsumo((Cliente)info.getUtilizador(),consumo,filtro);
    }else if(opt == 6){ 
     String marca = Input.lerString("Indique a marca do carro: ", "Marca inválida");    
     setViaturaInfo = info.filtraMarca((Cliente)info.getUtilizador(),marca,filtro);       
    }
    Input.limpaTela();
    navegador.criaNavegadorViaturaInfo(setViaturaInfo);   
    return navegador.escolheViaturaCliente(info,setViaturaInfo);
  }
  
  /** Menu onde o proprietario decide que mudança ira fazer a viatura*/

  public static void menuAlteracaoViatura(String matricula){
    Viatura v = info.devolveViatura(matricula);
    int opt = -1;
    int limiteOpt=0;//indica at que opçao o utilizador pode escolher
    while(opt!=0){
    Input.limpaTela();
    System.out.println("Bem vindo "+info.getUtilizador().getNome()+"(Proprietário)\n\n");
    System.out.println("1-Alterar a marca\n2-Alterar a posicao do carro\n3-Alterar a velocidade media\n4-Alterar o preço base por km\n5-Alterar a disponibilidade da viatura\n6-Consultar faturação num intervalo de tempo");
    limiteOpt=5;
    if(v instanceof Abastecivel){
       System.out.println("7-Alterar o tamanho do depósito\n8-Alterar o consumo\n9-Abastecer viatura ");
       limiteOpt=9;
       if(v instanceof CarroHibrido){
        System.out.println("10-Abastecer motor secundário");
        limiteOpt=10;
    }
    }
    System.out.println("\n0-Sair");
    opt = Input.lerInt("Opção: ","Opção inválida",0,limiteOpt); 
    Input.limpaTela();
    switch(opt){
     case 1: String marca = Input.lerString("Nova Marca:","A string introduzida não é válida");
              info.alteraViatura(1,matricula,marca);
              break;
     case 2:  double posX = Input.lerDouble("Nova posição em X:","Posição inválida");
              double posY = Input.lerDouble("Nova posição em Y:","Posição inválida");
              info.alteraViatura(2,matricula,new Ponto(posX,posY));
              break;
     case 3:  double vmedia = Input.lerDouble("Nova velocidade media(em km/h):","Valor inválido");
              info.alteraViatura(3,matricula,3600/vmedia);
              break;
     case 4:  double precoBaseKm = Input.lerDouble("Nova preço por km percorrido:","Valor inválido");
              info.alteraViatura(4,matricula,precoBaseKm);
              break;
     case 5:  int estado = Input.lerInt("1-Disponível\n2-Indisponível\n","Insira um número válido",1,2);
              if(estado==1){info.alteraViatura(5,matricula,true);}else{info.alteraViatura(5,matricula,false);}
              break;
     case 6:  LocalDateTime inicio = Input.lerData("Insira a data de início da pesquisa: \n");
              Input.limpaTela();
              LocalDateTime fim = Input.lerData("Insira a data de início da pesquisa: \n");
              Input.limpaTela();              
              System.out.println("Total faturado pela viatura "+v.getMarca()+" no intervalo mencionado: "+v.totalFaturadoDatas(inicio, fim)+"€");
              System.out.println("\nPrima qualquer tecla para regressar ao menu anterior");
              Input.next();
              break;
     case 7:                int tamanhoDeposito = Input.lerInt("Tamanho do depósito/bateria:", "Valor inválido()",0);
              info.alteraViatura(5,matricula,tamanhoDeposito);
              break;  
     case 8:  double consumo = Input.lerDouble("Consumo(em L ou Wh/100Km):", "Valor inválido",0);
              info.alteraViatura(6,matricula,consumo/100);
              break;
     case 9:  Abastecivel a = (Abastecivel)info.devolveViatura(matricula);
              double quantidade = Input.lerDouble("Quantidade a abastecer:", "Valor inválido, introduza um valor entre 0 e "+Math.round(a.getTamanhoDeposito()-a.getCombustivel()), 0,(a.getTamanhoDeposito()-a.getCombustivel())); 
              info.alteraViatura(7,matricula,quantidade);
              break;
     case 10:  double quantidadeAux = Input.lerDouble("Quantidade a abastecer (L ou Wh):", "Valor inválido");
              info.alteraViatura(8,matricula,quantidadeAux);
              break;           
    }
   }
   Input.limpaTela();   
 }
  
  
  
   public static void gereHistorial(){
       Input.limpaTela();   
        LocalDateTime comeco = Input.lerData("Insira a data de início da pesquisa: \n");
        Input.limpaTela();
        LocalDateTime fim = Input.lerData("Insira a data de fim da pesquisa: \n");
        Input.limpaTela();        
        navegador.criaNavegadorOcorrenciaAluguer(info.historialUtilizadorDatas(comeco,fim));
        navegador.navegaLista();    
    }   
    
    

  /** Ao inves do metodo para o cliente, este mostra todos os carros existentes, de um proprietario
   * Nessa altura, o proprietario tem a hipotese de escolher uma viatura.
   * O id dessa viatura sera devolvido para que depois a viatura seja processada adequadamente
   */
  public static String menuEscolheViaturaProp(){    
    Input.limpaTela();
    List<Viatura> lista = info.viaturasUtilizador(info.getUtilizador().getEmail());
    navegador.criaNavegadorViatura(lista);
    return navegador.escolheViaturaProprietario(info,(Proprietario)info.getUtilizador());
  }
  
  
  
}  

