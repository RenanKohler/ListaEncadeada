import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

public class Buscador {

    private ArvoreBinaria.No raiz;

    public Buscador(ArvoreBinaria.No raiz) {
        this.raiz = raiz;
    }

    public String buscar(String palavraChave) {
        ArvoreBinaria.No no = null;

//        if (this.raiz.getClass().equals(ArvoreBinaria.No.class)) {
        no = ArvoreBinaria.mapPalavras(raiz).getOrDefault(palavraChave, null);
        if (no == null) return "Palavra nao foi encontrada";
        else return "A palavra: \"" + no.palavra + "\" foi encontrada: " + no.valor + " vezes\n" +
                "Sendo que  nos Arquivos: " + no.contadorArquivo.entrySet().stream().sorted(reverseOrder(HashMap.Entry.comparingByValue(Integer::compare))).collect(Collectors.toCollection(LinkedHashSet::new)) + "\n-------------";
//        }


    }


}
