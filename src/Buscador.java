import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


import static java.util.Collections.reverseOrder;

public class Buscador {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        List<String> palavrasChaves = new ArrayList<>();
        File dir = new File("C:\\teste");
        boolean sair = false;
        System.out.println("O que deseja procurar");
        String palavra = teclado.next();
        ArvoreBinaria.No raiz = ArvoreBinaria.mapearFrequenciaPalavras(dir);
        Buscador buscador = new Buscador(raiz);
        System.out.println(buscador.buscar(palavra));


//        while (!sair){
//            System.out.println("O que deseja procurar");
//            String palavra = teclado.next();
//            if(!palavrasChaves.contains(palavra) && !palavra.equals("0"))palavrasChaves.add(palavra);
//            sair= palavra.equals("0");
//            System.out.println("digite 1 para adicionar uma nova palavra a busca.Digite 0 para efetuar a busca: ");
//           try{
//               sair = teclado.nextInt() != 1;
//           }
//           catch (InputMismatchException e){
//               System.out.println("Entrada Invalida");
//               sair = false;
//           }
//        }
//        palavrasChaves.sort(String::compareTo);
//        HashMap<String, ArvoreBinaria.No> hm = new HashMap<String, ArvoreBinaria.No>();
//        ArvoreBinaria.No raiz = ArvoreBinaria.mapearFrequenciaPalavras(dir);
//        String[] palavrasArray = new String[palavrasChaves.size()];
//        Buscador buscador = new Buscador(raiz);
//        System.out.println( buscador.buscar(palavrasChaves.toArray(palavrasArray)));

    }



    private ArvoreBinaria.No raiz;

    public Buscador(ArvoreBinaria.No raiz) {
        this.raiz = raiz;
    }

    // parte 3
    public String buscar(String palavraChave) {
        ArvoreBinaria.No no = null;

//        if (this.raiz.getClass().equals(ArvoreBinaria.No.class)) {
        no = ArvoreBinaria.mapPalavras(raiz).getOrDefault(palavraChave, null);
        if (no == null) return "Palavra nao foi encontrada";
        else return "\n-------------\nA palavra: \"" + no.palavra + "\" foi encontrada: " + no.valor + " vezes\n" +
                "Sendo que  nos Arquivos: " + no.contadorArquivo.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Integer::compare))).collect(Collectors.toCollection(LinkedHashSet::new)) + "\n-------------\n";
//        }


    }

    //parte 4
    public  String  buscar(String [] palavrasChave) {
        ArvoreBinaria.No no = null;
        HashMap<String, String> mapResultado = new HashMap();
        String resultado ="";
        StringBuilder sb = new StringBuilder(resultado);
//        if (this.raiz.getClass().equals(ArvoreBinaria.No.class)) {
        for (String palavraChave: palavrasChave
             ) {

            no = ArvoreBinaria.mapPalavras(raiz).getOrDefault(palavraChave, null);
            if (no == null)  resultado = "\n-------------\nA palavra: \"" + palavraChave + "\"  nao foi encontrada\n-------------\n";
            else
            resultado ="\n-------------\nA palavra: \"" + no.palavra + "\" foi encontrada: " + no.valor + " vezes\n" +
                    "Sendo que  nos Arquivos: " + no.contadorArquivo.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Integer::compare))).collect(Collectors.toCollection(LinkedHashSet::new)) + "\n-------------\n";
            sb.append(resultado);
//            mapResultado.putIfAbsent(palavraChave,resultado);
                   }
            return sb.toString();
    }


}
