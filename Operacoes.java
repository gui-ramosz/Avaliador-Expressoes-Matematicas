package projetop1;

public class Operacoes {
    /**
     * Define a prioridade dos operadores matemáticos.
     * @param operador O caractere do operador.
     * @return Um valor inteiro que representa a prioridade (quanto maior, mais prioritário).
     */
    private int prioridade(char operador) {
        if (operador == '+' || operador == '-') {
            return 1;
        } else if (operador == '*' || operador == '/') {
            return 2;
        } else if (operador == '^') {
            return 3;
        }
        return -1; // Retorna -1 para caracteres que não são operadores
    }

    /**
     * Converte uma expressão matemática da notação infixa para a notação posfixa.
     * @param expressaoInfixa A string da expressão infixa.
     * @return A string da expressão convertida para posfixa.
     * @throws IllegalArgumentException Se a expressão contiver um operador inválido ou parênteses desbalanceados.
     */
    public String conversaoPosfixa(String expressaoInfixa) {
        Pilha pilhaOperadores = new Pilha(expressaoInfixa.length());
        StringBuilder saidaPosfixa = new StringBuilder();

        for (int i = 0; i < expressaoInfixa.length(); i++) {
            char atualChar = expressaoInfixa.charAt(i);

            if (Character.isLetter(atualChar)) {
                // Se for um operando (letra), adiciona diretamente na saída
                saidaPosfixa.append(atualChar);
            } else if (Character.isWhitespace(atualChar)) {
                // Ignora espaços em branco
                continue;
            } else if (atualChar == '(') {
                // Se for um parêntese de abertura, empilha
                pilhaOperadores.empilhar(atualChar);
            } else if (atualChar == ')') {
                // Se for um parêntese de fechamento, desempilha operadores até encontrar o parêntese de abertura correspondente
                while (!pilhaOperadores.estaVazia() && (char) pilhaOperadores.topo() != '(') {
                    saidaPosfixa.append(pilhaOperadores.desempilhar());
                }
                // Desempilha o parêntese de abertura, mas não o adiciona na saída
                if (!pilhaOperadores.estaVazia()) {
                    pilhaOperadores.desempilhar(); 
                }
            } else if (prioridade(atualChar) != -1) { // Verifica se é um operador válido
                // Se for um operador, desempilha operadores de maior ou igual prioridade e os adiciona à saída
                while (!pilhaOperadores.estaVazia() && (char) pilhaOperadores.topo() != '(' && prioridade((char) pilhaOperadores.topo()) >= prioridade(atualChar)) {
                    saidaPosfixa.append(pilhaOperadores.desempilhar());
                }
                // Empilha o operador atual
                pilhaOperadores.empilhar(atualChar);
            } else {
                // Se o caractere não for letra, espaço, parêntese ou operador válido, lança uma exceção
                throw new IllegalArgumentException("Operador inválido: " + atualChar);
            }
        }
        // Ao final da varredura, desempilha o restante dos operadores para a saída
        while (!pilhaOperadores.estaVazia()) {
             Object topo = pilhaOperadores.topo();
             if (topo instanceof Character && (char) topo == '(') {
                 // Se um parêntese de abertura for encontrado aqui, a expressão é inválida
                 throw new IllegalArgumentException("Parênteses desbalanceados.");
             }
            saidaPosfixa.append(pilhaOperadores.desempilhar());
        }
        return saidaPosfixa.toString();
    }

    /**
     * Avalia uma expressão matemática na notação posfixa.
     * @param saidaPosfixa A string da expressão na notação posfixa.
     * @param variaveis O objeto Variaveis que contém os valores das variáveis.
     * @return O resultado do cálculo da expressão.
     * @throws IllegalArgumentException Se a expressão contiver um operador desconhecido.
     * @throws ArithmeticException Se ocorrer uma divisão por zero.
     */
    public Double calculo(String saidaPosfixa, Variaveis variaveis) {
        Pilha resultado = new Pilha(saidaPosfixa.length());

        for (int i = 0; i < saidaPosfixa.length(); i++) {
            char atualChar = saidaPosfixa.charAt(i);

            if (Character.isLetter(atualChar)) {
                // Se for um operando, obtém o valor da variável e o empilha
                Double valor = variaveis.getValor(atualChar);
                if (valor == null) {
                    return null;
                }
                resultado.empilhar(valor);
            }
            else{
                // Se for um operador, desempilha os dois últimos operandos, realiza a operação e empilha o resultado
                Double op2 = (double) resultado.desempilhar();
                Double op1 = (double) resultado.desempilhar();
                if (atualChar == '+') {
                    Double opResolvida = op1 + op2;
                    resultado.empilhar(opResolvida);
                } 
                else if (atualChar == '-') {
                    Double opResolvida = op1 - op2;
                    resultado.empilhar(opResolvida);
                } 
                else if (atualChar == '*') {
                    Double opResolvida = op1 * op2;
                    resultado.empilhar(opResolvida);
                }
                else if (atualChar == '/') {
                    if (op2 == 0) {
                        throw new ArithmeticException("Divisão por zero.");
                    }
                    Double opResolvida = op1 / op2;
                    resultado.empilhar(opResolvida);
                }
                else if (atualChar == '^') {
                    Double opResolvida = Math.pow(op1, op2);
                    resultado.empilhar(opResolvida);
                }
                else {
                    throw new IllegalArgumentException("Operador desconhecido.");
                }
            }
        }
        // O resultado final estará no topo da pilha
        return (Double) resultado.desempilhar();
    }
}