 

import java.util.Comparator;
import java.util.List;
/**
 * Classe que compara dois utilizadores quando ao numero total de kms percorridos
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparadorTotalKms implements Comparator<Cliente>
{
    public int compare(Cliente c1, Cliente c2){
    double sum1 = c1.kmsPercorridos();
    double sum2 = c2.kmsPercorridos();   
    if(sum1>sum2){
    return -1;
    }else{
    return 1;}
    }
}
