import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL; 
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

/**
 * NOTA IMPORTANTE:
 * Caso alguem queira reutilizar este codigo, devera registar-se nos websites das API's:
 *  -https://ipinfodb.com/
 *  -https://openweathermap.org
 *  
 *  E de seguida devera substituir os urls nos metodos getJSONIP e getWeather pelos urls que contem a chave obtida
 *  
 *  Este procedimento tem de ser feito para a obtençao das chaves das API, visto que as chaves que se encontram
 *  nesta classe sao exclusivas para este projeto (UMCarroJa) e a sua utilizaçao fora deste projeto terao consequencias
 *  visto que as API's externas tem um numero limitado de chamadas que qualquer projeto pode fazer
 * 
 * 
 * Classe que controla a chamada a API's externas, 
 * nomeadamente a API do ipInfoBD para localizar o utilizador 
 * e a API do OpenWeatherMAp para obter condiçoes climatericas 
 */
public class ControloAPIs {
  private static JSONObject ip=null;
  private static JSONObject weather=null;
  
  public static void updateIP() throws Exception{
    ip = getJSONIP();
    weather= getWeatherJSON();
    }
    
  /** Devolve um objeto JSONObject correspondente
     ao ficheiro JSON que nos e devolvido quando e feita a chamada
     da API ipInfoBD*/
  private static JSONObject getJSONIP() throws Exception {
     String url = "http://api.ipinfodb.com/v3/ip-city/?key=e0be799dbaeea0a95e9f90874a3b9ae3a2c9281846f0742d9c9e3547e2375fa0&format=json";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
     // optional default is GET
     con.setRequestMethod("GET");
     //add request header
     con.setRequestProperty("User-Agent", "Mozilla/5.0");
     BufferedReader in = new BufferedReader(
             new InputStreamReader(con.getInputStream()));
     String inputLine;
     StringBuffer response = new StringBuffer();
     while ((inputLine = in.readLine()) != null) {
     	response.append(inputLine);
     }
     in.close();
     //print in String
     System.out.println(response.toString());
     //Read JSON response and print
     JSONObject myResponse = new JSONObject(response.toString());
     /*System.out.println("result after Reading JSON Response");
     System.out.println("statusCode- "+myResponse.getString("statusCode"));
     System.out.println("statusMessage- "+myResponse.getString("statusMessage"));
     System.out.println("ipAddress- "+myResponse.getString("ipAddress"));
     System.out.println("countryCode- "+myResponse.getString("countryCode"));
     System.out.println("countryName- "+myResponse.getString("countryName"));
     System.out.println("regionName- "+myResponse.getString("regionName"));
     System.out.println("cityName- "+myResponse.getString("cityName"));
     System.out.println("zipCode- "+myResponse.getString("zipCode"));
     System.out.println("latitude- "+myResponse.getString("latitude"));
     System.out.println("longitude- "+myResponse.getString("longitude"));
     System.out.println("timeZone- "+myResponse.getString("timeZone")); */
     return myResponse;
  }
  

  private static List<String> getLatLon()  {
       List<String> res = new ArrayList<>();
       res.add(ip.getString("latitude"));
       res.add(ip.getString("longitude"));
       return res;
  }
    

    /** recebe a latitude e a longitude do utilizador e devolve as condiçoes climatericas
   Utiliza a API do openWeatherMap
   */       
  private static JSONObject getWeatherJSON() throws Exception {
     List<String> localizacao = getLatLon();
     String url = "http://api.openweathermap.org/data/2.5/forecast?lat="+localizacao.get(0)+"&lon="+localizacao.get(1)+"&APPID=57ac60b5e1a273ea45029e6be26998ca&units=metric";
     URL obj = new URL(url);
     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
     // optional default is GET
     con.setRequestMethod("GET");
     //add request header
     con.setRequestProperty("User-Agent", "Mozilla/5.0");
     int responseCode = con.getResponseCode();
     BufferedReader in = new BufferedReader(
             new InputStreamReader(con.getInputStream()));
     String inputLine;
     StringBuffer response = new StringBuffer();
     while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
     }
     in.close();

     //Read JSON response and print
     JSONObject myResponse = new JSONObject(response.toString());
     System.out.println("City- "+myResponse.getJSONObject("city").getString("name"));
     System.out.println("Weather- "+myResponse.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main"));
     return myResponse;
    } 
    
  
  public static String getCity(){
    return ip.getString("cityName")+","+ip.getString("countryName");
  }  
 
  /** Devolve uma string correspondente as condiçoes meteorologicas no local*/
  public static String getWeather (){
    if (weather!=null){
     return weather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
    }else{
     return "";
    }
  }
}
