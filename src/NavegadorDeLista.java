import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Scanner;
import java.time.LocalDateTime;

/**
 * Classe responsavel por navegar listas de viaturas/alugueres
 */
public class NavegadorDeLista
{ 
    /** ArrayList que guarda todas as sublistas com x carros*/
    ArrayList<ArrayList<String>> listaStrings;
    /** Numero de elementos a aprecer por pagina*/
    private int tamanhoPag;
    /** Indice da pagina em que estamos*/
    private int pagAtual;
    /** Numero de paginas total*/
    private int nPags;
   
    
    /** 
     * Construtor em que se recebe a lista de viaturas, mais propriamente uma lista de ViaturaInfo e 
     * cria-se uma lista de listas de strings, em que em cada lista tem um numero de strings, sendo esse numero definido 
     * pela variavel tamanhoPag, e que cada string represena uma viatura
     */
    public NavegadorDeLista(){
    this.tamanhoPag=10;
    this.pagAtual=0;    
    this.listaStrings = new ArrayList<>();    
    }

    /** Cria o navegador para quando se pretende alugar uma viatura*/
   public void criaNavegadorViaturaInfo(Set<ViaturaInfo> setViaturaInfo){
    setPagAtual(0);   
    setNPags(0);
    ArrayList<String>lista = new ArrayList<String>();
    lista = converteSetViaturaInfoString(setViaturaInfo);
    this.listaStrings = new ArrayList<>();
   if(lista.isEmpty())
    {
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Não há viaturas disponiveis para esses parametros");
        listaStrings.add(aux);
        setNPags(1);
    }
    else
    {
      Iterator<String> it = lista.iterator();

      while(it.hasNext()){
       ArrayList<String> aux = new ArrayList<>(); 
        aux.add("Tipo            Matricula  Marca Estado VMedia  Consumo       Autonomia     Rating    Dist a Si    Custo  Chegada Posição da Viatura\n\n");
        for(int i=0; i<this.tamanhoPag && it.hasNext();i++){
        aux.add(it.next());
        }
        listaStrings.add(aux);
        setNPags(getNPags()+1);
      }
    }           
   }

   /** Cria um navegador para quando se pretende gerir as viaturas*/
   public void criaNavegadorViatura(List<Viatura> listaViatura){
    setPagAtual(0);   
    setNPags(0);
    ArrayList<String>lista = new ArrayList<String>();
    lista = converteListaViaturaString(listaViatura);
    this.listaStrings = new ArrayList<>();
   if(lista.isEmpty())
    {
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Não há viaturas");
        listaStrings.add(aux);
        setNPags(1);
    }
    else
    {
      Iterator<String> it = lista.iterator();

      while(it.hasNext()){
       ArrayList<String> aux = new ArrayList<>(); 
        aux.add("Tipo            Matricula  Marca Estado VMedia  Consumo      Autonomia     Rating   Faturação\n\n");
        for(int i=0; i<this.tamanhoPag && it.hasNext();i++){
        aux.add(it.next());
        }
        listaStrings.add(aux);
        setNPags(getNPags()+1);
      }
    }           
   }   
    
   /** Cria um navegador para quando se pretende consultar os alugueres*/
    public void criaNavegadorOcorrenciaAluguer(List<OcorrenciaAluguer> historial){
    setPagAtual(0);
    setNPags(0);
    ArrayList<String>lista = new ArrayList<String>();
    lista = converteListaAluguerString(historial);
    this.listaStrings = new ArrayList<>();
    if(lista.isEmpty())
    {
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Não há alugueres disponíveis");
        listaStrings.add(aux);
        setNPags(1);
    }
    else
    {
      Iterator<String> it = lista.iterator();
      while(it.hasNext()){          
        ArrayList<String> aux = new ArrayList<>(); 
        aux.add("Email                 Matricula   Marca    Data       Hora     Ponto do cliente  Ponto da viatura          Destino              Dist  Custo\n\n");
        for(int i=0; i<this.tamanhoPag && it.hasNext();i++){
        aux.add(it.next());
        }
        listaStrings.add(aux);
        setNPags(getNPags()+1);
      }
    }
    }
   
    /** Cria um navegador para quando se pretende o top 10 clientes*/
    public void criaNavegadorCliente(List<Cliente> listCliente){
    setPagAtual(0);   
    setNPags(0);
    ArrayList<String>lista = new ArrayList<String>();
    lista = converteListClienteString(listCliente);
    this.listaStrings = new ArrayList<>();
   if(lista.isEmpty())
    {
        ArrayList<String> aux = new ArrayList<>();
        aux.add("Não há clientes");
        listaStrings.add(aux);
        setNPags(1);
    }
    else
    {
      Iterator<String> it = lista.iterator();

      while(it.hasNext()){
       ArrayList<String> aux = new ArrayList<>(); 
      
        for(int i=0; i<this.tamanhoPag && it.hasNext();i++){
        aux.add(it.next());
        }
        listaStrings.add(aux);
        setNPags(getNPags()+1);
      }
    }           
   }     
    
    
    /**
    * Converte uma lista de carros numa string, recebe um set de ViaturaInfo
    */
    public ArrayList<String> converteSetViaturaInfoString(Set<ViaturaInfo> listViaturaInfo){
    ArrayList<String> res = new ArrayList<>(); 
    for(ViaturaInfo vi : listViaturaInfo){       
     res.add(vi.toString()+"\n\n");
    }
    return res;
    }
    
    /**
    * Converte uma lista de carros numa string, recebe um set de ViaturaInfo
    */
    public ArrayList<String> converteListaViaturaString(List<Viatura> listaViatura){
    ArrayList<String> res = new ArrayList<>(); 
    for(Viatura v : listaViatura){       
     res.add(v.toString()+"  "+v.totalFaturado()+"€\n\n");
    }
    return res;
    }
    
    /**
    * Converte uma lista de clients numa lista de strings, recebe uma lista de Cliente
    */
    public ArrayList<String> converteListClienteString(List<Cliente> listCliente){
    ArrayList<String> res = new ArrayList<>(); 
    for(Cliente c : listCliente){       
     res.add(c.toString()+"\n\n");
    }
    return res;
    }
    
    /** Passa a lista de ocurrencias de aluguer para uma lista de strings */
    public ArrayList<String> converteListaAluguerString(List<OcorrenciaAluguer> l){
    ArrayList<String> res = new ArrayList<>(); 
    for(OcorrenciaAluguer o : l){
    res.add(o.toString()+"\n\n");
    }
    return res;
    }
    /** Devolve a pag atual */
    public int getPagAtual(){return pagAtual;}
    /** Altera a pag atual */
    public void setPagAtual(int pagAtual){this.pagAtual=pagAtual;}
    /**Devolve o numero de paginas */
    public int getNPags(){return this.nPags;}
    /** Altera o numero de paginas */
    public void setNPags(int nPags){this.nPags=nPags;}
    /** Mostra a pagina ao utilizador */
    public void mostraPag(){
     Input.limpaTela();   
     System.out.println("A mostrar a página " + (getPagAtual()+1)+" de "+(getNPags()));
     for(String s : listaStrings.get(getPagAtual())){  
      System.out.print(s);  
     }
     System.out.println(new String(new char[13]).replace("\0", "\r\n"));
    }
    /** Avança de pagina, mostrando a pagina seguinte ao utilizador (caso esta pagina exista) */
    public void pagSeguinte(){
    if(getPagAtual()==getNPags()-1){
    System.out.println("Não há mais páginas a mostrar.");
    }
    else{
    setPagAtual(getPagAtual()+1);
    mostraPag();
    }
    }
    /** Retrocede de pagina, mostrando a pagina seguinte ao utilizador (caso esta pagina exista) */
    public void pagAnterior(){
    if(getPagAtual()==0){
    System.out.println("Já está na primeira página.");}
    else{
    setPagAtual(getPagAtual()-1);
    mostraPag();
    }
    }
    
    /** Permite ao utilizador navegar na lista, isto e, avançar e retroceder nas paginas */
    public void navegaLista(){
    Scanner sc = new Scanner (System.in);
    String opt = ",";
    mostraPag();
    while(opt.equals(",") || opt.equals(".")){      
    System.out.println("Prima',' para regressar à página anterior, '.' para  ir para a página seguinte, e qualquer outra tecla para regressar");
    opt = sc.next();
    if (opt.equals(",")) {pagAnterior();}
     else if(opt.equals(".")){pagSeguinte();}    
    }
  }
  
  /** Metodo que permite navegar a lista de Viaturas e ainda escolher um ou decidir voltar atras
     *Recebe a frota contendo todos os carros e recebe a lista de viaturasInfo ordenadas e nas quais  possivel viajar
      */
   public String escolheViaturaCliente(BancoInformacao bi,Set<ViaturaInfo> setViaturas){    
    mostraPag();
    String matricula="-1";
    while(matricula.equals("-1")  && !matricula.equals("0")){ 
    matricula = Input.lerString("Insira o id do carro que deseja, 0 para regressar ao menu anterior, ',' para regressar à página anterior, '.' para  ir para a página seguinte"
    ,"Não foi possível ler a sua opção");
    if (matricula.equals(",")) {pagAnterior();matricula="-1";}
    else if(matricula.equals(".")){pagSeguinte();matricula="-1";}
    else{   
      if(!matricula.equals("0")){ 
       if(!bi.existeViatura(matricula)){ 
       System.out.println("Essa viatura não existe");
       matricula ="-1";
       }else{ //verifica se a viatura selecionada existe na lista de viaturas info 
           boolean disponivel =false;
           ViaturaInfo viaturaViagem = null;
           for(ViaturaInfo vi : setViaturas){ //metodo contais nao esta a funcionar
            if(vi.getViatura().equals(bi.devolveViatura(matricula))){;
              disponivel = true;
              viaturaViagem=vi.clone();
              System.out.println("kk");              
              break;
            }
           }
           if(!disponivel){
              System.out.println("Essa viatura não se encontra disponível para a viagem");
              matricula ="-1";
           }else{                     
              System.out.println("\nViatura escolhida: \n");
              System.out.println(viaturaViagem.toString());
              String decisao = Input.lerString("\nConfirma a escolha(s/n):", "Não foi possível ler a sua opção");
              if(!decisao.equals("S") && !decisao.equals("s")){matricula="0";}//nao e escolhida nenhuma viatura
           }
       } 
     }
    }
   }
    return matricula;    
  }
    
  /** Metodo que permite navegar a lista de Viaturas e ainda escolher um ou decidir voltar atras
     *Recebe a frota contendo todos os carros e recebe a lista de viaturasInfo ordenadas e nas quais  possivel viajar
     *certificando-se assim que so e escolhido uma viatura apresentada
      */
   public  String escolheViaturaProprietario(BancoInformacao bi,Proprietario proprietario){
    Scanner sc = new Scanner(System.in);  
    mostraPag();
    String matricula="-1";
    while(matricula.equals("-1")  && !matricula.equals("0")){ 
    matricula = Input.lerString("Insira o id do carro que deseja, 0 para regressar ao menu anterior, ',' para regressar à página anterior, '.' para  ir para a página seguinte"
    ,"Não foi possível ler a sua opção");
    if (matricula.equals(",")) {pagAnterior();matricula="-1";}
    else if(matricula.equals(".")){pagSeguinte();matricula="-1";}
    else{       
      if(!matricula.equals("0")){
       if(!bi.existeViatura(matricula)){
       System.out.println("Não existe nenhuma viatura com esse Id");
       matricula="-1";
       }else if(!bi.devolveViatura(matricula).getIdDono().equals(proprietario.getEmail())){ //verifica se a viatura selecionada pertence ao nosso utilizador
       System.out.println("Essa viatura não lhe pertence");
       matricula ="-1";
       }else{
        Viatura v = bi.devolveViatura(matricula);
        System.out.println("\nViatura escolhida: \n");
        System.out.println(v.toString());
        String decisao = Input.lerString("\nConfirma a escolha(s/n):", "Não foi possível ler a sua opção");
        if(!decisao.equals("S") && !decisao.equals("s")){matricula="0";}//nao e escolhida nenhuma viatura
        }
      }  
     }
    }
    return matricula;    
    }    
}
