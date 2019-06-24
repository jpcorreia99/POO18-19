import java.time.LocalDateTime;
import UMCarroJaExceptions.*;
/**
 * Classe que abstrai a utilização da classe Scanner, escondendo todos os
 * problemas relacionados com excepções, e que oferece métodos simples e
 * robustos para a leitura de valores de tipos simples e String.
 *
 * É uma classe de serviços, como Math e outras de Java, pelo que devem ser
 * usados os métodos de classe implementados.
 *
 * Utilizável em BlueJ, NetBeans, CodeBlocks ou Eclipse.
 * 
 * Utilização típica:  int x = Input.lerInt();
 *                     String nome = Input.lerString();
 * 
 * @author F. Mário Martins, adaptada por João Correia
 * @version 1.0 (6/2006)
 */
import static java.lang.System.out;
import static java.lang.System.in;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Input {

 /**
  * Métodos de Classe
  */
    
 public static String lerString(String pergunta, String erro) {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     String txt = "";
     while(!ok) {
         try {
             out.println("\n"+pergunta);
             txt = input.nextLine();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.println(erro+"\n"); 
             }
     }
     //input.close();
     return txt;
  } 

 /** Le um inteiro entre um intervalo*/ 
 public static int lerInt(String mensagem, String erro, int limiteInferior, int limiteSuperior) {
     Scanner input = new Scanner(in);
     boolean ok = false;
     
     int i = 0; 
     while(!ok) {
         try {
             out.println(mensagem+"\n");
             i = input.nextInt();
             if(i<limiteInferior || i>limiteSuperior){
                out.println(erro);
             }else{
             ok = true;
             }
         }
         catch(InputMismatchException e) 
             { out.println("O valor insirido não é um inteiro\n"); 
               input.nextLine(); //liberta o buffer;
             }   
     }
     //input.close();
     return i;
  } 

  /**Le in inteiro com um limite inferior*/
 public static int lerInt(String mensagem, String erro, int limiteInferior) {
     Scanner input = new Scanner(in);
     boolean ok = false;
     
     int i = 0; 
     while(!ok) {
         try {
             out.println("\n"+mensagem);
             i = input.nextInt();
             if(i<limiteInferior ){
                out.println(erro);
             }else{
             ok = true;
             }
         }
         catch(InputMismatchException e) 
             { out.println("O valor insirido não é um inteiro\n"); 
               input.nextLine(); //liberta o buffer;
             }   
     }
     //input.close();
     return i;
  }   
  /** Le um double*/
  public static double lerDouble(String pergunta, String erro) {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     double d = 0.0; 
     while(!ok) {
         try {
             out.println("\n"+pergunta);
             d = input.nextDouble();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.print(erro+"\n");
               input.nextLine();//liberta o buffer 
             }
     }
     //input.close();
     return d;
  }  
  
  /** Le um double com limite inferior*/
  public static double lerDouble(String pergunta, String erro, double limInf) {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     double d = 0.0; 
     while(!ok) {
         try {
             out.println("\n"+pergunta);
             d = input.nextDouble();
             if(d>=limInf){                 
             ok = true;
            }else{
             out.println(erro+"\n");
            }
         }
         catch(InputMismatchException e) 
             { out.print(erro+"\n");
               input.nextLine();//liberta o buffer 
             }
     }
     //input.close();
     return d;
  }  
 
  /** Le um double entre um intervalo*/
  public static double lerDouble(String pergunta, String erro, double limInf, double limSup) {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     double d = 0.0; 
     while(!ok) {
         try {
             out.println("\n"+pergunta);
             d = input.nextDouble();
             if(d>=limInf && d<=limSup){                 
             ok = true;
            }else{
             out.println(erro+"\n");
            }
         }
         catch(InputMismatchException e) 
             { out.print(erro+"\n");
               input.nextLine();//liberta o buffer 
             }
     }
     //input.close();
     return d;
   }   
   /** Le um float*/
   public static float lerFloat() {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     float f = 0.0f; 
     while(!ok) {
         try {
             f = input.nextFloat();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.println("Valor real Invalido"); 
               out.print("Novo valor: ");
               input.nextLine(); 
             }
     }
     //input.close();
     return f;
  }  
  /**Le um boleano*/
   public static boolean lerBoolean() {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     boolean b = false; 
     while(!ok) {
         try {
             b = input.nextBoolean();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.println("Booleano Invalido"); 
               out.print("Novo valor: ");
               input.nextLine(); 
             }
     }
     //input.close();
     return b;
  } 
  
  /**Le um short*/
  public static short lerShort() {
     Scanner input = new Scanner(in);
     boolean ok = false; 
     short s = 0; 
     while(!ok) {
         try {
             s = input.nextShort();
             ok = true;
         }
         catch(InputMismatchException e) 
             { out.println("Short Invalido"); 
               out.print("Novo valor: ");
               input.nextLine(); 
             }
     }
     //input.close();
     return s;
  }  
   
  /** Le uma data sobre a forma de LocalDateTime*/
  public static LocalDateTime lerData(String pergunta){
    out.println(pergunta+"\n");  
    int ano = lerInt("Ano","Ano inválido",1900, 2050);
    int mes = lerInt("Mês: ","Mês inválido",1, 12);    
    int dia =0;
    if(mes == 2){
     dia = lerInt("Dia: ","Dia inválido" ,1, 28); } 
    else if (((mes <= 6) && (mes % 2 == 0)) || ((mes >=9) && (mes%2 == 1))){
     dia = lerInt("Dia: ","Dia inválido",1,30);
    }else{
     dia  = lerInt("Dia: ","Dia inválido",1,31);
    }
    LocalDateTime data = LocalDateTime.of(ano,mes,dia,23,59,0,0);    
    return data;
    }  
  
    /** Le toda a informaçao relativa ao registo de um utilizador*/
    public static void registaUtilizador(BancoInformacao info){
    String nome =Input.lerString("Nome: ","Nome inválido");
    String password = Input.lerString("Password: ","Password inválida");
    String morada = Input.lerString("Morada: ", "Morada inválida");
    LocalDateTime dataNascimento = Input.lerData("Insira a sua data de nascimento\n");
    System.out.println("1-Cliente\n2-Proprietário");
    int tipo = Input.lerInt("Opção: ","Opção inválida",1,2); 
    String nif = Input.lerString("Insira o seu nif:", "nif invalido");
    boolean aux=false;
    while(!aux){
    try{    
     String email = Input.lerString("Email: ","Email inválido");
     if(tipo ==1){
       info.registaCliente(nif,email,password,nome,morada,dataNascimento,0,0);
       aux = true;
     }else{
       info.registaProp(nif,email,password,nome,morada,dataNascimento); 
       aux = true;
     }
    }catch(UtilizadorJaExisteException e){
       System.out.println("Esse email já se encontra registado");
    }
    }
    limpaTela();    
    }
    
    /** Le a informaçao relativa ao login de um utilizador*/
    public static void loginUtilizador(BancoInformacao info){
    boolean aux = false;
    Input.limpaTela();
    while(!aux){
     try{      
      String email = Input.lerString("Email: ", "Não foi possível ler a string");
      String password = Input.lerString("Password: ", "Não foi possível ler a string");
      System.out.println("\n\nLoggin in..\n\n");
      info.loginUtilizador(email,password);
      aux=true;
     }catch(UtilizadorNaoExisteException e){
        System.out.println("O email introduzido não existe\n\n\n");
     }catch(CredenciaisErradasException e){
         System.out.println("A password introduzida está errada\n\n\n");
     }catch(Exception e){
        System.out.println("Não foi possível localizar o utilizador");
    }
    }
    }
    
    /** Limpa o ecra*/
    public static void limpaTela(){
    System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
    
       /** Metodo que pede as coordenadas do utilizador assim como as coordenadas do seu destino*/
    public static void lerCoordenadas(BancoInformacao info){        
    double posXC=Input.lerDouble("Coordenada em X da sua posição","Valor inválido");
    double posYC=Input.lerDouble("Coordenada em Y da sua posição","Valor inválido");
    double posXD=Input.lerDouble("Coordenadas em X do seu destino","Valor inválido");
    double posYD=Input.lerDouble("Coordenadas em Y do seu destino","Valor inválido");
    info.posicionaCliente(posXC,posYC,posXD,posYD);    
    }
    
    /** Metodo que simplesmente serve para quando se pretende pressionar uma tecla qualquer*/
    public static String next(){
    Scanner sc = new Scanner(System.in);
    return sc.next();
    }
}