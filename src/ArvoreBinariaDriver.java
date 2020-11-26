import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class ArvoreBinariaDriver {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("O que deseja procurar");
        String palavraChave = teclado.nextLine();
        File dir = new File("C:\\teste");

        HashMap<String, ArvoreBinaria.No> hm = new HashMap<String, ArvoreBinaria.No>();
        ArvoreBinaria.No raiz = ArvoreBinaria.mapearFrequenciaPalavras(dir);
        Buscador buscador = new Buscador(raiz);
        System.out.println(buscador.buscar(palavraChave));
        System.out.println("\n\n\n\n\n\n---totais");
        hm = (HashMap<String, ArvoreBinaria.No>) ArvoreBinaria.mapPalavras(raiz);
        hm.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Comparator.comparingInt(q -> q.valor)))).forEach(x -> System.out.println("A palavra: \"" + x.getKey() + "\" foi encontrada: " + x.getValue().valor + " vezes\n" +
                "Sendo que  nos Arquivos: " + x.getValue().contadorArquivo.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Integer::compare))).collect(Collectors.toCollection(LinkedHashSet::new)) + "\n-------------"));
//        ArvoreBinaria.No raiz1 = null;
//               raiz1 = ArvoreBinaria.insereEmLvlOrder(raiz1,"pão","teste.txt");


//        ArvoreBinaria.No raiz2 = null;
//        raiz2 = ArvoreBinaria.mapearFrequenciaPalavra(dir,"com");
//        hm = (HashMap<String, ArvoreBinaria.No>) ArvoreBinaria.mapPalavras(raiz2);
//        hm.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Comparator.comparingInt(q -> q.valor)))).forEach(x -> System.out.println("A palavra: \"" + x.getKey() + "\" foi encontrada: " + x.getValue().valor +" vezes\n" +
//                "Sendo que  nos Arquivos: " + x.getValue().contadorArquivo.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Integer::compare))).collect(Collectors.toCollection(LinkedHashSet::new)) + "\n-------------"));

    }
}

