 

import java.util.Comparator;

/** Classe que compara dois objetos da classe ViaturaInfo(classe que contem a viatura mais informaçao relativa a viagem) 
  * e os ordena emm relaçao ao custo dessa viagem
 */
public class ComparadorCustoViagem  implements Comparator<ViaturaInfo>
{
        public int compare(ViaturaInfo v1, ViaturaInfo v2){
        if(v1.getCustoViagem() <= v2.getCustoViagem())
        return -1; 
        else 
        return 1;
    } 
}
