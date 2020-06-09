 

import java.util.Comparator;
/**
 * Classe que compara dois dois clientes quanto ao numero de alugueres que ja efetuaras
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparadorNumAlugueres implements Comparator<Cliente>
{
     public int compare(Cliente c1, Cliente c2){
        if(c1.getHistorial().size() >= c2.getHistorial().size())
        return -1; 
        else 
        return 1;
    }    
}
