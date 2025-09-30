/**
 * Grupo:
 * Guilherme Alves
 * João Batista
 * Matheus Queiroz
 *
 * Referências:
 * - Algoritmo de Conversão Infixa para Posfixa:
 * - Artigo: "Stack | Set 2 (Infix to Postfix)"
 * - Fonte: GeeksforGeeks
 * - URL: https://www.geeksforgeeks.org/stack-set-2-infix-to-postfix/
 *
 * - Algoritmo de Avaliação de Expressão Posfixa:
 * - Artigo: "Stack | Set 4 (Evaluation of Postfix Expression)"
 * - Fonte: GeeksforGeeks
 * - URL: https://www.geeksforgeeks.org/stack-set-4-evaluation-postfix-expression/
 *
 * - Estruturas de Dados Abstratas (Pilha e Fila):
 * - Material de Aula: Slides e exercícios da disciplina de Estruturas de Dados do curso de Ciência da Computação.
 * - Fonte: Universidade Presbiteriana Mackenzie
 *
 * - Implementação de REPL (Read-Eval-Print Loop) e validação de entrada:
 * - Livro: "Estruturas de Dados e Seus Algoritmos" (autores: M. E. F. P. da Silva, J. L. P. da Cunha e R. T. G. de L. F. P. da Costa)
 * - Livro: "Introduction to Algorithms" (autores: Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein)
 *
 */
package projetop1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Inicializa as classes de controle para variáveis e gravação de comandos
        Variaveis variaveis = new Variaveis();
        Fila filaComandos = new Fila();
        
        // Flags de controle para o estado atual do programa (gravação ou não) e para o loop principal
        boolean estaGravando = false;
        boolean sair = false;

        Scanner leitor = new Scanner(System.in);
        System.out.println("****Avaliador de Expressões Matemáticas****");
        System.out.println("Digite um comando ou EXIT para sair");

        // Loop principal do REPL (Read-Eval-Print Loop) que processa as entradas do usuário
        while (!sair) {
            System.out.print("> ");
            String comandoOriginal = leitor.nextLine().trim();
            // Converte o comando para maiúsculas para facilitar a comparação, exceto para o conteúdo que será gravado
            String comandoProcessado = comandoOriginal.toUpperCase();

            // Lógica de gravação: se a flag 'estaGravando' for verdadeira, os comandos são enfileirados
            if (estaGravando) {
                // Checa se o comando é um dos que não podem ser gravados na fila
                if (comandoProcessado.equals("REC") || comandoProcessado.equals("STOP") || comandoProcessado.equals("PLAY") || comandoProcessado.equals("ERASE") || comandoProcessado.equals("EXIT")) {
                    if (comandoProcessado.equals("STOP")) {
                        estaGravando = false;
                        System.out.printf("Encerrando gravação... (REC: %d/%d)\n", filaComandos.getTamanho(), filaComandos.getCapacidadeMaxima());
                    } else {
                        System.out.println("Erro: comando inválido para gravação.");
                    }
                } else {
                    // Enfileira o comando se a fila não estiver cheia
                    if (!filaComandos.estaCheia()) {
                        filaComandos.enfileirar(comandoOriginal);
                        System.out.printf("(REC: %d/%d) %s\n", filaComandos.getTamanho(), filaComandos.getCapacidadeMaxima(), comandoOriginal);
                        if(filaComandos.estaCheia()){
                            // Para a gravação automaticamente se o limite for atingido
                            estaGravando = false;
                            System.out.println("Gravação parada automaticamente: limite de 10 comandos atingido.");
                        }
                    } 
                }
            } else {
                // Lógica de execução normal: processa os comandos diretamente
                switch (comandoProcessado) {
                    case "EXIT":
                        sair = true;
                        System.out.println("Encerrando...");
                        break;
                    case "RESET":
                        variaveis.reset();
                        System.out.println("Variáveis reiniciadas.");
                        break;
                    case "VARS":
                        variaveis.listar();
                        break;
                    case "REC":
                        if (filaComandos.estaCheia()) {
                            System.out.println("Erro: a fila de gravação está cheia. Use ERASE para limpar.");
                        } else {
                            estaGravando = true;
                            System.out.printf("Iniciando gravação... (REC: %d/%d)\n", filaComandos.getTamanho(), filaComandos.getCapacidadeMaxima());
                        }
                        break;
                    case "STOP":
                        System.out.println("Erro: comando inválido fora do modo de gravação.");
                        break;
                    case "ERASE":
                        filaComandos.apagar();
                        System.out.println("Gravação apagada.");
                        break;
                    case "PLAY":
                        if (filaComandos.estaVazia()) {
                            System.out.println("Não há gravação para ser reproduzida.");
                        } else {
                            System.out.println("Reproduzindo gravação...");
                            int comandosParaExecutar = filaComandos.getTamanho();
                            // Loop para percorrer a fila e executar cada comando
                            for (int i = 0; i < comandosParaExecutar; i++) {
                                String cmd = filaComandos.getComando(i);
                                System.out.println(cmd);
                                processarComando(cmd, variaveis);
                            }
                            System.out.println("Reprodução encerrada.");
                        }
                        break;
                    default:
                        // Chama o método que processa atribuições e expressões
                        processarComando(comandoOriginal, variaveis);
                        break;
                }
            }
        }
        leitor.close();
    }

    /**
     * Processa um comando, determinando se é uma atribuição de variável, um comando de controle ou uma expressão matemática.
     * Esta função é chamada tanto pelo loop principal quanto pelo comando PLAY.
     * @param comando A string de comando a ser processada.
     * @param variaveis O objeto Variaveis para manipular as variáveis.
     */
    private static void processarComando(String comando, Variaveis variaveis) {
        String comandoProcessado = comando.toUpperCase();
        
        // Verifica se é uma atribuição de variável (contém '=')
        if (comando.contains("=")) {
            String comandoSemEspacos = comando.replaceAll("\\s+", "");
            String[] partes = comandoSemEspacos.split("=");
            if (partes.length == 2 && partes[0].length() == 1 && Character.isLetter(partes[0].charAt(0))) {
                try {
                    char var = partes[0].charAt(0);
                    double valor = Double.parseDouble(partes[1]);
                    variaveis.atribuir(var, valor);
                    System.out.println(Character.toUpperCase(var) + " = " + valor);
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Valor inválido para a variável.");
                }
            } else {
                System.out.println("Erro: Atribuição inválida! Use o formato <VAR> = <VALUE>.");
            }
        } 
        // Verifica se é o comando VARS
        else if (comandoProcessado.equals("VARS")) {
            variaveis.listar();
        } 
        // Verifica se é o comando RESET
        else if (comandoProcessado.equals("RESET")) {
            variaveis.reset();
        } 
        // Se não for nenhum dos anteriores, trata como uma expressão matemática
        else {
            Operacoes operacoes = new Operacoes();
            boolean variaveisValidas = true;
            String variaveisInvalidas = "";
            // Verifica se todas as variáveis na expressão foram definidas
            for (int i = 0; i < comando.length(); i++) {
                char atualChar = comando.charAt(i);
                if (Character.isLetter(atualChar)) {
                    if (variaveis.getValor(atualChar) == null) {
                        char varUpperCase = Character.toUpperCase(atualChar);
                        if (variaveisInvalidas.indexOf(varUpperCase) == -1) {
                            System.out.println("Erro: variável " + varUpperCase + " não definida.");
                            variaveisInvalidas += varUpperCase;
                        }
                        variaveisValidas = false;
                    }
                }
            }

            // Se todas as variáveis forem válidas, tenta calcular a expressão
            if (variaveisValidas) {
                try {
                    String expressaoPosfixa = operacoes.conversaoPosfixa(comando);
                    Double resultado = operacoes.calculo(expressaoPosfixa, variaveis);
                    if (resultado != null) {
                        System.out.println(resultado);
                    }
                } catch (Exception e) {
                    System.out.println("Erro: expressão inválida.");
                }
            }
        }
    }
}