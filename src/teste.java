import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class teste {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("O que deseja procurar");
        String busca = teclado.nextLine();
        File dir = new File("C:\\teste");
        ListaEncadeada lista = new ListaEncadeada();
        lista = criaLisaEncadeada(dir, busca, false);
        System.out.println(lista.exibeLista());
    }

    /**
     * cria a lista encadeada
     *
     * @param dir   corresponde ao diretório base onde será realizada a busca dos arquivos txt
     * @param busca corresponde a palavra chave que sera utilizada para realizar a busca
     * @param asc   caso verdadeiro ordena a lista em ordem ascendente, caso falso ordena a lista em ordem descendente
     * @return retorna uma lista encadeada sendo que cada item da lista utiliza o nome do arquivo como chave e o numero de ocorrências da busca como valor
     */
    public static ListaEncadeada criaLisaEncadeada(final File dir, String busca, boolean asc) {
        ListaEncadeada retorno = new ListaEncadeada();

        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.getName().contains(".txt")) {
                int count = getResultCount(fileEntry, busca);
                retorno.insereInicio(count, fileEntry.getName());
            }
        }
        if (asc) {
            retorno.ordenar();
        } else {
            retorno.ordenarDesc();
        }
        return retorno;
    }

    /**
     * retorno o numero de ocorrências do parâmetro busca no arquivo
     *
     * @param fileEntry arquivo o qual sera realizada a busca da palavra-chave
     * @param busca     corresponde a palavra chave que sera utilizada para realizar a busca
     * @return retorna o numero de ocorrências da palavra chave no arquivo
     */
    public static Integer getResultCount(File fileEntry, String busca) {
        int count = 0;
        try (Scanner myReader = new Scanner(fileEntry)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] palavras = data.split(" ");
                for (String palavra : palavras) {
                    if (palavra.equalsIgnoreCase(busca)) {
                        count++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Deu ruinzao");
            e.printStackTrace();
        }
        return count;
    }
}
