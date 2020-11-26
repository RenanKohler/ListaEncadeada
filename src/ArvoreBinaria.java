// Java implementation of
// the above approach

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

class ArvoreBinaria {

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


    /**
     * Função responsável por criar um novo nó passando como parâmetro a palavra que será adicionada ao nó e
     * em qual arquivo ela foi encontrado
     *
     * @param palavra
     * @param arquivo
     * @return
     */
    static No add(String palavra, String arquivo) {

        No novoNo = new No();
        novoNo.palavra = palavra;
        novoNo.valor = 1;
        novoNo.esquerda = novoNo.direita = null;
        //variável responsável por armazenar a frequência da palavra nos arquivos
        novoNo.contadorArquivo = new HashMap<String, Integer>();
        //se o arquivo ainda nao existe no hashmap ele é adicionado com o valor 1
        novoNo.contadorArquivo.putIfAbsent(arquivo, 1);
        return novoNo;
    }


    /**
     * Função responsável por adicionar um nó na estrutura da arvore inserindo-o na ordem
     *
     * @param raiz    - nó correspondente a raiz da arvore
     * @param palavra - palavra que será adicionada a arvore binária
     * @param arquivo - arquivo no qual a palavra foi encontrada
     * @return retorno o nó raiz
     */
    static No insereEmLvlOrder(No raiz, String palavra, String arquivo) {

        //caso o nó raiz esteja nulo adiciona o nó como raiz
        if (raiz == null) {
            return add(palavra, arquivo);
        }


        Queue<No> Q = new LinkedList<No>();
        Q.add(raiz);

        while (!Q.isEmpty()) {

            No temp = Q.peek();
            Q.remove();

            //caso a palavra já exista incrementa  a frequência
            if (Objects.equals(temp.palavra, palavra)) {
                if (temp.contadorArquivo.containsKey(arquivo)) {
                    int valorAntigo = temp.contadorArquivo.get(arquivo);
                    temp.contadorArquivo.replace(arquivo, valorAntigo, valorAntigo + 1);
                } else {
                    temp.contadorArquivo.putIfAbsent(arquivo, 1);
                }
                temp.valor++;
                break;
            }

            // caso o nó a esquerda esteja nulo adiciona-se o novo nó à esquerda
            if (temp.esquerda == null) {

                temp.esquerda = add(palavra, arquivo);
                break;
            } else {

                //caso a palavra exista como uma folha à esquerda, incrementa a frequência e sai do loop
                if (temp.esquerda.palavra.equals(palavra)) {
                    if (temp.esquerda.contadorArquivo.containsKey(arquivo)) {
                        int valorAntigo = temp.esquerda.contadorArquivo.get(arquivo);
                        temp.esquerda.contadorArquivo.replace(arquivo, valorAntigo, valorAntigo + 1);
                    } else {
                        temp.esquerda.contadorArquivo.putIfAbsent(arquivo, 1);
                    }
                    temp.esquerda.valor++;
                    break;
                }

              //adiciona na variável Q para processamento posterior
                Q.add(temp.esquerda);
            }
// caso o nó a direita esteja nulo adiciona-se o novo nó à direita
            if (temp.direita == null) {
                temp.direita = add(palavra, arquivo);
                break;
            } else {
                //caso a palavra exista como uma folha à direita, incrementa a frequência e sai do loop
                if (temp.direita.palavra.equals(palavra)) {
                    if (temp.direita.contadorArquivo.containsKey(arquivo)) {
                        int valorAntigo = temp.direita.contadorArquivo.get(arquivo);
                        temp.direita.contadorArquivo.replace(arquivo, valorAntigo, valorAntigo + 1);
                    } else {
                        temp.direita.contadorArquivo.putIfAbsent(arquivo, 1);
                    }
                    temp.direita.valor++;
                    break;
                }

                //adiciona na variável Q para processamento posterior
                Q.add(temp.direita);
            }
        }

        return raiz;
    }
    /**
     * Método responsável por armazenar em map todas as palavras da arvore binária
     *
     * @param raiz - representa a raiz da arvore binaria;
     * @return retorna um map com todas as palavras e suas frequências;
     */
    public static Map<String, No> mapPalavras(No raiz) {
        HashMap<String, No> retorno = new HashMap<String, No>();


        if (raiz != null) {
            retorno.putIfAbsent(raiz.palavra, raiz);
            retorno.putAll(mapPalavras(raiz.esquerda));
            retorno.putAll(mapPalavras(raiz.direita));
        }
        return retorno;

    }


    /**
     * Cria uma arvore binaria contendo a frequência da palavra chave nos arquivos .txt do diretório
     * passado como parâmetro
     *
     * @param dir representa o diretório onde será realizada a busca
     * @param busca representa a palavra-chave
     * @return retorno um no
     */
    public static No mapearFrequenciaPalavra(final File dir,String busca) {
        No retorno = null;
        final String regexCamelCase = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        final String regexComma = "[^,]+";
        final String regexDot = "[^.]+";
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.getName().contains(".txt")) {
                try (Scanner myReader = new Scanner(fileEntry)) {
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String[] palavras = data.split(" ");
                        for (String palavra : palavras) {

                            palavra = palavra.replace(",", "");
                            if (palavra.length() > 1 || !palavra.equals(""))
                                if(busca.equalsIgnoreCase(palavra))
                                retorno = insereEmLvlOrder(retorno, palavra, fileEntry.getName());

                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Deu ruinzao");
                    e.printStackTrace();
                }
            }
        }

        return retorno;
    }


    /**
     * Cria uma arvore binaria contendo a frequência de todas as palavras encontradas nos arquivos .txt do diretório
     * passado como parâmetro
     *
     * @param dir representa o diretório onde será realizada a busca
     * @return retorno um no
     */
    public static No mapearFrequenciaPalavras(final File dir) {
        No retorno = null;
        final String regexCamelCase = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";
        final String regexComma = "[^,]+";
        final String regexDot = "[^.]+";
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.getName().contains(".txt")) {
                try (Scanner myReader = new Scanner(fileEntry)) {
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String[] palavras = data.split(" ");
                        for (String palavra : palavras) {

                            palavra = palavra.replace(",", "");
                            if (palavra.length() > 1 || !palavra.equals(""))
                                retorno = insereEmLvlOrder(retorno, palavra, fileEntry.getName());

                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Deu ruinzao");
                    e.printStackTrace();
                }
            }
        }

        return retorno;
    }


    /**
     * Classe No corresponde a um Item da ListaEncadeada
     */
    public static class No {
        /**
         * Atributo responsável por armazenar a frequência do atributo palavra nos arquivos, onde a chave representa
         * o nome do arquivo e o valor representa a a frequência da palavra
         */
        HashMap<String, Integer> contadorArquivo;
        /**
         * Atributo que representa a palavra chave
         */
        String palavra;
        /**
         * Atributo que representa a a frequência total do palavra em todos os arquivos
         */
        int valor;
        /**
         * Atributo que representa o nó à esquerda do nó atual
         */
        No esquerda;
        /**
         * Atributo que representa o nó à direita do nó atual
         */
        No direita;

    }


}



