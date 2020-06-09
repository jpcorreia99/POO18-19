 

import java.util.Comparator;
/**
 * Classe que compara dois objetos da classe ViaturaInfo(classe que contem a viatura mais informaçao relativa a viagem) 
 *  e os ordena emm relaçao a distancia ao cliente
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparadorDistCliente implements Comparator<ViaturaInfo>
{
    public int compare(ViaturaInfo v1, ViaturaInfo v2){
        if(v1.getDistCliente() <= v2.getDistCliente())
        return -1; 
        else 
        return 1;
    } 
}
