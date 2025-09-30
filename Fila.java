package projetop1;

public class Fila {
    // Array para armazenar os comandos na fila
    private String[] comandos;
    // Índices para controlar o início e o fim da fila em um array circular
    private int inicio;
    private int fim;
    // Contador do número de elementos na fila
    private int tamanho;
    private final int CAPACIDADE_MAXIMA = 10;

    // Método Construtor: Inicializa a fila
    public Fila() {
        this.comandos = new String[CAPACIDADE_MAXIMA];
        this.inicio = 0;
        this.fim = -1;
        this.tamanho = 0;
    }

    /**
     * Adiciona um comando no final da fila
     * @param comando A string de comando a ser enfileirada
     */
    public void enfileirar(String comando) {
        if (estaCheia()) {
            System.err.println("Erro: Fila cheia. Não é possível adicionar mais comandos.");
            return;
        }
        // Move o índice 'fim' em um array circular
        fim = (fim + 1) % CAPACIDADE_MAXIMA;
        comandos[fim] = comando;
        tamanho++;
    }

    /**
     * Remove e retorna o comando do início da fila
     * @return O comando que estava no início da fila
     */
    public String desenfileirar() {
        if (estaVazia()) {
            System.err.println("Erro: Fila vazia.");
            return null;
        }
        String comando = comandos[inicio];
        // Move o índice 'inicio' em um array circular
        inicio = (inicio + 1) % CAPACIDADE_MAXIMA;
        tamanho--;
        return comando;
    }

    /**
     * Verifica se a fila está vazia
     * @return true se a fila não tiver comandos, false caso contrário
     */
    public boolean estaVazia() {
        return tamanho == 0;
    }

    /**
     * Verifica se a fila está cheia
     * @return true se a fila atingiu sua capacidade máxima, false caso contrário
     */
    public boolean estaCheia() {
        return tamanho == CAPACIDADE_MAXIMA;
    }
    
    /**
     * Apaga todos os comandos da fila, reinicializando-a
     */
    public void apagar() {
        this.comandos = new String[CAPACIDADE_MAXIMA];
        this.inicio = 0;
        this.fim = -1;
        this.tamanho = 0;
    }

    /**
     * Retorna um comando em uma posição específica sem removê-lo (para o comando PLAY)
     * @param index O índice do comando na fila
     * @return O comando na posição especificada
     */
    public String getComando(int index) {
        if (index < 0 || index >= tamanho) {
            return null;
        }
        int realIndex = (inicio + index) % CAPACIDADE_MAXIMA;
        return comandos[realIndex];
    }
    
    /**
     * Retorna o número atual de comandos na fila
     * @return O tamanho atual da fila
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Retorna a capacidade máxima da fila
     * @return O valor da capacidade máxima
     */
    public int getCapacidadeMaxima() {
        return CAPACIDADE_MAXIMA;
    }
}