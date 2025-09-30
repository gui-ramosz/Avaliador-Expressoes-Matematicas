package projetop1;

public class Pilha {

    // Atributos da Classe
    private Object[] dados; // Array para armazenar os elementos da pilha
    private int topo; // Índice do elemento no topo da pilha
    private int capacidade; // Tamanho máximo da pilha

    // Método Construtor: Inicializa a pilha com uma capacidade definida
    public Pilha(int tamanho){
        this.capacidade = tamanho;
        this.dados = new Object[tamanho];
        this.topo = -1; // A pilha começa vazia, então o topo é -1
    }

    // Métodos da Pilha

    /**
     * Verifica se a pilha está vazia
     * @return true se a pilha estiver vazia, false caso contrário
     */
    public boolean estaVazia(){
        return this.topo == -1;
    }

    /**
     * Verifica se a pilha está cheia
     * @return true se a pilha estiver cheia, false caso contrário
     */
    public boolean estaCheia(){
        return this.topo == this.capacidade - 1;
    }

    /**
     * Adiciona um elemento no topo da pilha
     * @param valor O objeto a ser adicionado na pilha
     */
    public void empilhar (Object valor){
        if(estaCheia()){
            System.err.println("Erro: A pilha está cheia!");
        } else {
            this.topo++;
            this.dados[this.topo] = valor;
        }
    }
    
    /**
     * Remove e retorna o elemento do topo da pilha
     * @return O objeto que estava no topo da pilha ou null se estiver vazia
     */
    public Object desempilhar(){
        if (estaVazia()){
            System.err.println("Erro: A pilha está vazia!");
            return null;
        } else {
            Object valorDoTopo = this.dados[this.topo];
            this.topo--;
            return valorDoTopo;
        }
    }

    /**
     * Retorna o elemento do topo da pilha sem removê-lo
     * @return O objeto que está no topo ou null se estiver vazia
     */
    public Object topo(){
        if(estaVazia()){
            System.err.println("Erro: A pilha está vazia!");
            return null;
        } else {
            return this.dados[this.topo];
        }
    }        
}