# Avaliador de Expressões Matemáticas em Java

Este projeto é um interpretador de linha de comando (REPL - Read-Eval-Print Loop) desenvolvido em Java, capaz de avaliar expressões matemáticas complexas, gerenciar variáveis e gravar/reproduzir sequências de comandos. O núcleo do projeto é a implementação dos algoritmas clássicos de conversão infixa-posfixa e avaliação de expressão posfixa, utilizando estruturas de dados como Pilha e Fila.

## Principais Conceitos e Algoritmos

Este projeto é uma demonstração prática de vários conceitos fundamentais de Ciência da Computação:

* **Conversão Infixa para Posfixa (Shunting-yard algorithm):** O programa converte expressões matemáticas legíveis (ex: `5 * (A + 3)`) para a notação posfixa, que é ideal para avaliação computacional. Isso resolve ambiguidades e respeita a precedência de operadores.
* **Avaliação de Expressão Posfixa:** Uma vez na notação posfixa, a expressão é calculada utilizando uma pilha para armazenar operandos e obter o resultado final.
* **Estrutura de Dados de Pilha (Stack):** Utilizada intensivamente nos algoritmos de conversão e avaliação de expressões.
* **Estrutura de Dados de Fila (Queue):** Implementada como um buffer circular para a funcionalidade de gravação e reprodução de comandos (macros).
* **REPL (Read-Eval-Print Loop):** O programa opera em um loop interativo, lendo comandos do usuário, avaliando-os e imprimindo o resultado, simulando um console de interpretador.

## Funcionalidades

### Expressões e Variáveis
* **Avaliação de Expressões:** Suporta os operadores `+`, `-`, `*`, `/` e `^` (potência), respeitando a ordem de precedência e o uso de parênteses `()`.
* **Gerenciamento de Variáveis:**
    * `VARS`: Lista todas as variáveis e seus respectivos valores.
    * `A = 10`: Atribui um valor a uma variável (qualquer letra de A a Z).
    * `RESET`: Apaga todas as variáveis da memória.

### Gravação e Reprodução (Macros)
* **`REC`**: Inicia o modo de gravação. Todos os comandos subsequentes (exceto os de controle) são armazenados em uma fila com capacidade para 10 comandos.
* **`STOP`**: Para o modo de gravação.
* **`PLAY`**: Executa, em ordem, todos os comandos que foram gravados na fila.
* **`ERASE`**: Limpa a fila, apagando a gravação atual.

### Controle
* **`EXIT`**: Encerra o programa.


```bash
javac *.java
