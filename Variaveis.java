package projetop1;

public class Variaveis {
    // Array para armazenar os valores das variáveis (A-Z)
    private Double[] memoria;

    // Construtor: Inicializa a memória para 26 variáveis, de 'A' a 'Z'
    public Variaveis() {
        this.memoria = new Double[26];
    }

    /**
     * Atribui um valor para uma variável.
     * @param var A letra da variável (case-insensitive).
     * @param valor O valor real a ser atribuído.
     * @return true se a atribuição foi bem-sucedida, false caso contrário.
     */
    public boolean atribuir(char var, double valor){
        var = Character.toUpperCase(var);
        int indice = var - 'A';
        if(indice >= 0 && indice < 26){
            this.memoria[indice] = valor;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Lista todas as variáveis que já têm um valor definido.
     * Implementa a lógica do comando VARS.
     */
    public void listar(){
        boolean encontrouAlguma = false;
        for(int i = 0; i < this.memoria.length; i++){
            if(this.memoria[i] != null){
                char var = (char) (i + 'A');
                System.out.println(var + "=" + this.memoria[i]);
                encontrouAlguma = true;
                }        
            }
        if(!encontrouAlguma){
            System.out.println("Nenhuma variável definida!");
        }
    }

    /**
     * Retorna o valor de uma variável específica.
     * @param var A letra da variável que queremos consultar (case-insensitive).
     * @return O valor (Double) da variável, ou null se ela não estiver definida.
     */
    public Double getValor(char var) {
        var = Character.toUpperCase(var);
        int indice = var - 'A';
        if (indice >= 0 && indice < 26) {
            return this.memoria[indice]; 
        }
        return null;
    }

    /**
     * Reinicia todas as variáveis, limpando a memória.
     * Implementa a lógica do comando RESET.
     */
    public void reset(){
        this.memoria = new Double[26];
    }   
}