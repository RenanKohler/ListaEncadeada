import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


/**
 * Classe correspondente a estrutura de ListaEncadeada
 *
 */
public class ListaEncadeada {

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
                retorno.insereOrdenado(count, fileEntry.getName());
//                retorno.insereInicio(count, fileEntry.getName());
            }
        }
//        if (asc) {
//            retorno.ordenar();
//        } else {
//            retorno.ordenarDesc();
//        }
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


    /**
     * primeiro elemento da lista("cabeça")
     */
    private No inicio;

    /**
     * Método Construtor
     */
    public ListaEncadeada() {
        // construtor da lista
        inicio = null;
    }

    /**
     * Verifica se existem elementos na lista
     * @return retorna verdadeiro caso o no inicial esteja nulo
     */
    public boolean isEmpty() {

        return inicio == null;
    }

    /**
     * verifica se existe na ListaEncadeada o valor passado como argumento
     * @param valor parâmetro que será pesquisado na lista
     * @return retorna verdadeiro caso encontre o valor na lista
     */
    public boolean procurarValor(int valor) {

        // percorre a ListaEncadeada
        for (No no = inicio; no != null; no = no.proximo)
          //encontrou o valor
            if (valor == no.valor) return true;
        // não encontrou o elemento
        return false;
    }

    /**
     * verifica se existe na ListaEncadeada a chave passada como argumento
     * @param chave parâmetro que será pesquisado na lista
     * @return retorna verdadeiro caso encontre o valor na lista
     */
    public boolean existeChave(String chave) {

        // percorre a ListaEncadeada
        for (No no = inicio; no != null; no = no.proximo)
            //encontrou o valor
            if (Objects.equals(chave, no.chave)) return true;
        // não encontrou o elemento
        return false;
    }
    public No procurarChave(String chave) {

        // percorre a ListaEncadeada
        for (No no = inicio; no != null; no = no.proximo)
            //encontrou o valor
            if (Objects.equals(chave, no.chave)) return no;
        // não encontrou o elemento
        return null;
    }

    /**
     * Método responsável por inserir um novo nó na lista, o nó inicial passa a ser o próximo nó
     * @param valor - valor do nó
     * @param chave - chave do nó
     */
    public void insereInicio(int valor, String chave) {

        //Instancia um novo nó utilizando os valores passados como parâmetro
        No novoNo = new No(valor, chave);
        //novoNo -> inicio antigo
        novoNo.proximo = inicio;
        // inicio -> novoNo
        inicio = novoNo;
    }

    /**
     * Método responsável por inserir um novo na ListaEncadeada ja posicionando de maneira ordenada(Descendente)
     * @param valor - valor do nó
     * @param chave - chave do nó
     */
    public void insereOrdenado(int valor, String chave){
        //Instancia uma variável do tipo No com valores passados por parâmetro
        No novoNo = new No(valor, chave);
        //Verifica se existe elementos na lista ou se o valor do no inicial é menor ou igual ao do novoNo
        if (inicio == null || inicio.valor <= novoNo.valor)
        {
            /*
               caso verdadeiro o valor do nó inicial passa a ser o novoNo e próximo nó passa se tornar o valor do nó
               inicial
             */
            novoNo.proximo = inicio;
            inicio = novoNo;
        }
        else
        {
            /*
            !! valor do nó  inicial é maior
               caso falso o valor do nó inicial nao se altera
             */

            // instancia uma variável do tipo nó, atribui-se o nó inicial para variável atual
            No atual = inicio;
            /*
            Esse loop localiza a posição do nó anterior a posição de inserção do novoNó
            Enquanto o valor do próximo nó náo for nulo !!e o valor  do próximoNo valor for maior que o do novoNo
            */
            while (atual.proximo != null && atual.proximo.valor > novoNo.valor)
            {
                //Move o nó atual para direita atribuindo o próximo nó como seu valor
                atual  = atual.proximo;
            }

            /*
                insere o novoNo entre o nó atual e o próximo nó do atual
             */
           // o próximo nó em relação ao novoNo se torna o nó próximo nó do atual
            novoNo.proximo = atual.proximo;
            //   o próximo nó em relação ao atual se torna o novoNo
            atual.proximo = novoNo;
        }
    }

    /**
     * Método responsável por retirar da lista o nó inicial, reposicionando o próximo nó para a posição inicial
     */
    public void removeInicio() {

        //move o inicio da lista para o próximo nó
        inicio = inicio.proximo;
    }

    /**
     * Cria um objeto do tipo List<no> com todos os nós da ListaEncadeada(o campo prox do objeto nó é atribuído como nulo)
     * @return lista com os nós da estrutura de ListaEncadeada
     */
    public  List<No> nos() {
        
        // Instancia uma variável do tipo List<no>
        List<No> retorno = new ArrayList<>();  
        
        // percorre a ListaEncadeada
        for (No no = inicio; no != null; no = no.proximo) {
            //Instancia um novo nó utilizando os valores da variável no como parâmetro
            No novo = new No(no.valor, no.chave);
            //Adiciona na List o novo objeto do tipo No
            retorno.add(novo);
        }
        // retorno a lista contendo os nós da estrutura de ListaEncadeada
        return retorno;
    };

    /**
     * 
     * @return retorna uma string contendo todos os nós da ListaEncadeada
     */
    public String exibeLista() {
        
        //teste para verificar se a ListaEncadeada esta vazia
        if (isEmpty()) return "Lista vazia\n"; 

        //Objeto responsável por criar a string de retorno
        StringBuilder str = new StringBuilder("ListaEncadeada: ");

        // percorre a ListaEncadeada
        for (No no = inicio; no != null; no = no.proximo)
            // adicionada na string os campos chave e valor do nó
            str.append(" ").append(no.chave).append(": ").append(no.valor);

        return str + "\n";
    }

    /**
     * Método responsável por ordenar em ordem ascendente a ListaEncadeada
     */
    public void ordenar() {

        // chamada do método responsável por realizar a ordenação em ordem ascendente
        inicio = MergeSort(inicio);
    }

    /**
     * Método responsável por ordenar em ordem ascendente a ListaEncadeada
     */
    public void ordenarDesc() {

        // chamada do método responsável por realizar a ordenação em ordem ascendente
        inicio = MergeSort(inicio);

        //region cria uma nova ListaEncadeada em ordem crescente
        List<ListaEncadeada.No> nos = nos();

        //retira todos os nós da ListaEncadeada
        limpaListaEncadeada(nos);

        //Adiciona os nós em ordem descente na ListaEncadeada
        for (No no : nos) {
            insereInicio(no.valor, no.chave);
        }
    }

    /**
     *
     * @return retorna o número de elementos da ListaEncadeada
     */
    private int countListaEncadeada(){

        //instância e atribui valor para a variável que sera utilizado como retorno da função
        int retorno = 0;

        //Caso o nó inicial esteja vazio retorna o valor 0
        if (inicio == null) return retorno;

        // percorre a ListaEncadeada incrementando em 1 a variável retorno
        for (No no = inicio; no != null; no = no.proximo) retorno++;

        return retorno;

    }


    /**
     * remove todos os nós de uma lista encadeada
     * @param nos recebe um objeto do lista
     */
    private void limpaListaEncadeada(List<No> nos) {
        int tamanhoLista = countListaEncadeada();
        //Realiza uma iteração  para cada elemento da ListaEncadeada removendo o elemento inicial
        for(int i = 0; i<tamanhoLista; i++){
            //chamada do método responsável pela remoção do elemento inicial
            removeInicio();
        }
    }

    /**
     * Método responsável por consolidar duas ListasEncadeada
     * @param lista1
     * @param lista2
     * @return retorna  uma ListaEncadeada a qual contem as duas listas passadas como parâmetro
     */
    public static No SortedMerge(No lista1, No lista2)
    {
        //verifica se a primeira lista está nula, caso verdadeiro retorna a segunda lista
        if (lista1 == null)
            return lista2;

        //verifica se a segunda lista está nula, caso verdadeiro retorna a primeira lista
        else if (lista2 == null)
            return lista1;

        //instância uma variável do tipo No que será utilizada com retorno
        No retorno;

        //caso o valor do nó 1(lista1) seja menor ou igual ao do nó 2(lista2)
        if (lista1.valor <= lista2.valor)
        {
            //a variável de retorno se torna a primeira ListaEncadeada
            retorno = lista1;

            /*
             Chama recursivamente o método de agrupamento e ordenação (SortedMerge) para realizar a ordenação dos próximos nós da
              ListaEncadeada utilizando como parâmetro o próximo do nó da variável de retorno e o segundo nó(lista2) do chamada
              do método
             */
            retorno.proximo = SortedMerge(lista1.proximo, lista2);
        }

        //caso o valor do nó 1(lista1) seja maior ou igual ao do nó 2(lista 2)
        else
        {
            //a variável de retorno se torna a segunda ListaEncadeada
            retorno = lista2;

             /*
             Chama recursivamente o método de agrupamento e ordenação(SortedMerge) para realizar a ordenação dos próximos nós da
              ListaEncadeada utilizando como parâmetro o próximo do nó da variável de retorno e o primeiro nó(lista1) do chamada
              do método
             */
            retorno.proximo = SortedMerge(lista1, lista2.proximo);
        }

        // retorna a ListaEncadeada ordenada em ordem crescente
        return retorno;
    }

    /**
     * Divide a ListaEncadeada em 2 metades
     * @param ponteiro(o valor inicial do ponteiro é o nó inicial da ListaEncadeada ) parâmetro utilizado para dividir a ListaEncadeada em 2
     * @return retorna um array com as duas partes da ListaEncadeada
     */
    public static No[] dividirLista(No ponteiro)
    {

        // verifica se o nó(ponteiro) passado como parâmetro está nulo ou se o próximo elemento do nó(ponteiro) está nulo
        if (ponteiro == null || ponteiro.proximo == null) {

            // retorna um array de Nó, onde o primeiro elemento é o nó passado como parâmetro e um segundo nó(nulo)
            return new No[]{ ponteiro, null } ;
        }

        //instancia e atribui valor para as variáveis anterior(o nó passado como parâmetro(ponteiro))
        //backward
        No anterior = ponteiro;
        // posterior(o nó correspondente a propriedade prox do ponteiro(referencia ao próximo nó da ListaEncadeada ))
        //forward
        No posterior = ponteiro.proximo;

        // Forward moves twice and backward moves once
        /*
        Enquanto a variável posterior nao for nula, move-se a posterior  2 posições e parte esquerda 1 posição
        !!MOVER SIGNIFICA Atribuir o valor do nó atual como o próximo valor da ListaEncadeada para isso utiliza-se a
        propriedade prox do Objeto No
         */
        while (posterior != null)
        {
            //move a posterior uma posição da listaEncadeada
            posterior = posterior.proximo;

            //caso a posterior não seja nula
            if (posterior != null)
            {
                //move a anterior uma posição da listaEncadeada
                anterior = anterior.proximo;
                //move a posterior uma posição da listaEncadeada
                posterior = anterior.proximo;
            }
        }

       //Cria a variável de retorno que contem  as duas partes da ListaEncadeada
        No[] retorno = new No[]{ ponteiro, anterior.proximo};

        //atribui como nulo o valor da propriedade prox da anterior
        anterior.proximo = null;

        //retorna a variável retorno -> array com as 2 partes da lista
        return retorno;
    }

    //Ordena  ListaEncadeada utilizando-se do algorítimo Merge Sort
    public No MergeSort(No primeiroNo) {

        // verifica se o parâmetro primeiroNo está nulo ou se o próximo elemento está nulo
        if (primeiroNo == null || primeiroNo.proximo == null) {

            //Caso verdadeiro utiliza o no passado como parâmetro como retorno do método
            return primeiroNo;
        }

        // cria um array com as 2 partes da ListaEncadeada através da chamada do método dividirLista
        No[] partesLista = dividirLista(primeiroNo);

        //instancia a variável do tipo nó e atribui a posição 0 do variável partesLista como seu valor -> parte esquerda da ListaEncadeada
        No primeiraMetade = partesLista[0];

        //instancia a variável do tipo nó e atribui a posição 1 do variável partesLista como seu valor -> parte direita da ListaEncadeada
        No segundaMetade = partesLista[1];

        //ordena a primeiraMetade da ListaEncadeada chamando recursivamente o método MergeSort
        primeiraMetade = MergeSort(primeiraMetade);

        //ordena a segundaMetade da ListaEncadeada chamando recursivamente o método MergeSort
        segundaMetade = MergeSort(segundaMetade);

        // a chamada do método sortedMerge se torna o retorno desta função agrupando as duas metades ordenadas
        return SortedMerge(primeiraMetade, segundaMetade);
    }

    /**
     * Classe No corresponde a um Item da ListaEncadeada
     */
    public static class No {
        // próximo nó na lista
        public No proximo;
        // valor do No atual da lista
        public int valor;
        // chave do No atual da Lista
        public String chave;

        /**
         * Método Construtor da classe No, responsável por criar uma instancia do objeto do No atribuindo-se os valores
         *  passados como parâmetro para as propriedades valor e chave
         * @param val
         * @param chave
         */
        public No(int val, String chave) { //construtor do nó da lista
            this.valor = val;
            this.proximo = null;
            this.chave = chave;
        }
    }


}


