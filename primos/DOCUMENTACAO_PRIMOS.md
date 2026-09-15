# Documentacao do projeto `primos`

Este projeto conta quantos numeros primos existem no intervalo de `1` ate
`500.000`. Para acelerar o processamento, ele utiliza concorrencia com
`Thread`: dois objetos `Operario` analisam os numeros enquanto um objeto
`Despachante` distribui o trabalho.

O resultado esperado da execucao atual e:

```text
Total: 41538
```

## Objetivo do programa

O programa precisa verificar cada numero do intervalo e descobrir se ele e
primo. Em vez de um unico fluxo fazer todo o trabalho, o programa utiliza:

- `Main`: inicia o processamento e imprime o resultado.
- `Despachante`: controla o intervalo e entrega os numeros aos trabalhadores.
- `Operario`: recebe numeros e verifica quais sao primos.

O desenho geral e:

```text
Main
 |
 | inicia e aguarda
 v
Despachante
 |
 | cria e inicia
 +------------------+
 |                  |
 v                  v
Operario 1       Operario 2
 |                  |
 +---- pedem numeros ao Despachante
```

## Conceitos principais

### Thread

Uma `Thread` representa um fluxo de execucao independente. Neste projeto,
`Despachante` e `Operario` herdam de `Thread`:

```java
public class Despachante extends Thread {
```

```java
public class Operario extends Thread {
```

Quando uma classe herda de `Thread`, ela pode sobrescrever o metodo `run()`.
O codigo colocado dentro de `run()` sera executado quando a thread for
iniciada.

### `start()` e `run()`

```java
operario.start();
```

`start()` cria um novo fluxo de execucao e, internamente, chama `run()` nesse
novo fluxo.

Isto e diferente de chamar diretamente:

```java
operario.run();
```

Uma chamada direta a `run()` seria apenas uma chamada normal de metodo. Ela
nao criaria uma nova thread e o processamento seria sequencial.

### `join()`

```java
despachante.join();
```

`join()` faz a thread atual esperar outra thread terminar.

Neste programa:

1. `Main` inicia o `Despachante`.
2. `Main` chama `join()` e espera o `Despachante` terminar.
3. O `Despachante` inicia os `Operario`s.
4. O `Despachante` espera cada `Operario` terminar.
5. Somente depois disso `Main` imprime o total.

Sem o `join()` de `Main`, a mensagem poderia ser impressa antes do fim do
calculo.

### `synchronized`

O metodo abaixo e acessado simultaneamente pelos dois trabalhadores:

```java
public synchronized int getNumero() {
```

`synchronized` permite que apenas uma thread por vez execute esse metodo para
o mesmo objeto `Despachante`. Assim, a leitura e o incremento de `numero`
formam uma operacao protegida.

Essa parte e uma secao critica porque usa um dado compartilhado:

```java
return numero++;
```

Sem `synchronized`, os dois operarios poderiam ler o mesmo valor antes de um
deles incrementa-lo. Isso poderia fazer um numero ser processado duas vezes e
outro numero nao ser processado.

## 1. Arquivo `Main.java`

Arquivo:

```text
src/main/java/br/com/faculdade/Main.java
```

### 1.1 Pacote

```java
package br.com.faculdade;
```

O `package` informa a qual pacote a classe pertence. Ele corresponde ao
caminho `br/com/faculdade` dentro da pasta de codigo-fonte.

### 1.2 Classe e metodo inicial

```java
public class Main {
    static void main() {
```

`Main` e a classe usada para iniciar a aplicacao. Neste projeto, o metodo
inicial esta escrito no formato simplificado aceito pelo Java 25.

A forma tradicional e mais portavel seria:

```java
public static void main(String[] args) {
```

O vetor `args` recebe argumentos enviados pela linha de comando. Ele nao e
utilizado neste programa.

### 1.3 Criacao do despachante

```java
Despachante despachante = new Despachante(1, 500_000, 2);
```

O construtor recebe tres valores:

- `1`: inicio do intervalo.
- `500_000`: fim do intervalo.
- `2`: quantidade de trabalhadores.

O sublinhado em `500_000` e permitido em numeros inteiros para melhorar a
leitura. Para o compilador, ele representa o mesmo valor que `500000`.

### 1.4 Inicio e espera

```java
try {
    despachante.join();
} catch (Exception e) {

}
```

`start()` inicia a thread do despachante. O `join()` faz `Main` esperar o fim
do despachante antes de continuar.

O metodo `join()` pode gerar uma interrupcao, por isso aparece dentro de um
`try/catch`. O bloco `catch` atual esta vazio: se ocorrer uma excecao, ela e
ignorada. Em um programa real, seria melhor tratar o problema ou restaurar o
estado de interrupcao da thread.

### 1.5 Impressao do resultado

```java
System.out.print("Total: " + despachante.total());
```

Depois que o despachante termina, `total()` retorna a quantidade acumulada de
primos. A concatenacao junta o texto `Total: ` com o valor inteiro.

## 2. Arquivo `Despachante.java`

Arquivo:

```text
src/main/java/br/com/faculdade/Despachante.java
```

O `Despachante` e responsavel por distribuir os numeros e reunir os
resultados dos trabalhadores.

### 2.1 Atributos

```java
private int inicio, fim;
private Operario[] operarios;
private int total;
private int numero;
```

- `inicio`: primeiro numero do intervalo.
- `fim`: ultimo numero do intervalo.
- `operarios`: vetor que armazena as threads trabalhadoras.
- `total`: soma final da quantidade de primos encontrados.
- `numero`: proximo numero que sera entregue.

Os atributos sao `private`, portanto nao podem ser acessados diretamente por
outras classes. O acesso acontece por meio de metodos, como `getNumero()` e
`total()`.

### 2.2 Construtor

```java
public Despachante(int inicio, int fim, int operarios) {
    this.inicio = inicio;
    this.fim = fim;
    this.operarios = new Operario[operarios];
    this.numero = inicio;
}
```

O construtor recebe os dados necessarios para o processamento.

`this.inicio` significa o atributo da classe. O `inicio` sem `this` e o
parametro recebido pelo construtor. O mesmo vale para `fim`.

```java
this.operarios = new Operario[operarios];
```

Cria um vetor com a quantidade de posicoes indicada pelo parametro. Nesse
caso, o vetor tera duas posicoes.

```java
this.numero = inicio;
```

Define que o primeiro numero a ser distribuido sera o inicio do intervalo.

### 2.3 Metodo `run()` do despachante

```java
@Override
public void run() {
```

`@Override` informa que o metodo esta sobrescrevendo o `run()` herdado de
`Thread`. O `void` indica que o metodo nao retorna valor.

#### Criacao dos trabalhadores

```java
for (int i = 0; i < operarios.length; i++) {
    this.operarios[i] = new Operario(this);
    this.operarios[i].start();
}
```

O laco percorre todas as posicoes do vetor:

1. Cria um `Operario`.
2. Passa `this` para o construtor.
3. Guarda o trabalhador no vetor.
4. Inicia sua thread com `start()`.

O `this` representa o proprio objeto `Despachante`. Dessa forma, cada
`Operario` recebe uma referencia ao despachante e consegue chamar
`despachante.getNumero()`.

Depois do laco, os trabalhadores estao executando concorrentemente.

#### Espera e soma dos resultados

```java
for (int i = 0; i < operarios.length; i++) {
    try{
        this.operarios[i].join();
        total += this.operarios[i].primos();
    } catch (Exception e) {

    }
}
```

O segundo laco espera cada trabalhador finalizar. Quando um trabalhador
termina:

1. `join()` libera o despachante somente depois do fim da thread.
2. `primos()` retorna o contador daquele trabalhador.
3. Esse valor e adicionado a `total`.

Cada trabalhador possui seu proprio contador. Por isso, os operarios nao
alteram simultaneamente a mesma variavel `total`. A soma final e feita pelo
despachante depois dos `join()`.

### 2.4 Distribuicao dos numeros

```java
public synchronized int getNumero() {
    if (numero > fim)
        return -1;
    else
        return numero++;
}
```

Esse metodo entrega o proximo numero disponivel.

Quando ainda existem numeros:

```java
return numero++;
```

O valor atual e retornado e depois `numero` e incrementado. Por exemplo, se
`numero` vale `10`, o metodo retorna `10` e deixa o atributo valendo `11`.

Quando o limite foi ultrapassado:

```java
if (numero > fim)
    return -1;
```

O valor `-1` funciona como um sentinela. Ele avisa ao trabalhador que nao ha
mais numeros para processar.

O teste e `numero > fim`, e nao `numero >= fim`, porque o numero igual a
`fim` ainda precisa ser analisado. Assim, o intervalo e inclusivo:

```text
inicio <= numero <= fim
```

### 2.5 Resultado total

```java
public int total() {
    return total;
}
```

Retorna a soma das quantidades de primos encontrados pelos operarios.

Esse metodo e chamado por `Main` depois de `despachante.join()`. Portanto, no
fluxo normal, o resultado ja foi completamente calculado.

## 3. Arquivo `Operario.java`

Arquivo:

```text
src/main/java/br/com/faculdade/Operario.java
```

Cada `Operario` e uma thread que recebe numeros e contabiliza os primos que
encontra.

### 3.1 Atributos e construtor

```java
private int primos;
private Despachante despachante;
```

- `primos`: contador local daquele trabalhador.
- `despachante`: referencia ao objeto que distribui os numeros.

```java
public Operario(Despachante despachante) {
    this.despachante = despachante;
}
```

O trabalhador recebe o despachante no momento da criacao e guarda essa
referencia. Isso permite que ele solicite novos numeros.

### 3.2 Metodo `run()`

```java
@Override
public void run() {
    while (true) {
        int numero = despachante.getNumero();
        if (numero == -1)
            break;
        if (ehPrimo(numero)) primos++;
    }
}
```

O `while (true)` cria um laco que continuaria indefinidamente. Ele termina
quando a instrucao `break` e executada.

Fluxo de cada trabalhador:

1. Pede um numero ao despachante.
2. Verifica se recebeu `-1`.
3. Se recebeu `-1`, encerra o laco.
4. Caso contrario, testa se o numero e primo.
5. Se for primo, incrementa seu contador.
6. Volta ao passo 1.

O contador `primos` pertence a um unico objeto `Operario`. Cada thread altera
apenas o proprio contador, o que evita disputa entre os trabalhadores nessa
variavel.

### 3.3 Metodo `primos()`

```java
public int primos() {
    return primos;
}
```

Retorna quantos numeros primos foram encontrados por aquele trabalhador. O
despachante usa esse metodo para calcular o total geral.

### 3.4 Metodo `ehPrimo()`

```java
private static boolean ehPrimo(int n) {
    if (n == 1) return false;
    for (int i = 2; i < n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}
```

Esse metodo retorna um valor booleano:

- `true`: o numero e primo.
- `false`: o numero nao e primo.

#### Tratamento do numero 1

```java
if (n == 1) return false;
```

O numero `1` nao e primo porque um numero primo precisa possuir exatamente
dois divisores positivos: `1` e ele mesmo.

#### Teste dos divisores

```java
for (int i = 2; i < n; i++) {
```

O laco testa todos os possiveis divisores entre `2` e `n - 1`.

```java
if (n % i == 0) return false;
```

O operador `%` retorna o resto da divisao. Se o resto for zero, `i` divide
`n` exatamente, entao `n` possui um divisor alem de `1` e dele mesmo. Nesse
caso, o metodo pode retornar `false` imediatamente.

Se nenhum divisor for encontrado, o metodo executa:

```java
return true;
```

#### Exemplos

Para `7`:

```text
7 % 2 != 0
7 % 3 != 0
7 % 4 != 0
7 % 5 != 0
7 % 6 != 0
```

Nenhuma divisao teve resto zero, entao `7` e primo.

Para `12`:

```text
12 % 2 == 0
```

O metodo encontra um divisor logo na primeira verificacao e retorna `false`.

## Fluxo completo da execucao

Considerando o codigo atual, a ordem geral e:

1. A JVM inicia `Main.main()`.
2. `Main` cria um `Despachante` para o intervalo de `1` a `500.000`.
3. O atributo `numero` do despachante recebe `1`.
4. `Main` chama `despachante.start()`.
5. O `run()` do despachante cria dois `Operario`s.
6. Os dois operarios sao iniciados com `start()`.
7. Cada operario solicita numeros usando `getNumero()`.
8. O `synchronized` garante que cada chamada receba um numero diferente.
9. Cada operario chama `ehPrimo()` e atualiza seu contador local.
10. Quando nao ha mais numeros, `getNumero()` retorna `-1`.
11. Cada operario recebe `-1` e encerra seu `while`.
12. O despachante espera os dois operarios com `join()`.
13. O despachante soma os contadores individuais em `total`.
14. `Main` espera o despachante terminar.
15. `Main` imprime o total.

## O que acontece com os primeiros numeros

Com inicio em `1`, uma possivel distribuicao e:

```text
Operario 1 recebe 1
Operario 2 recebe 2
Operario 1 recebe 3
Operario 2 recebe 4
Operario 1 recebe 5
Operario 2 recebe 6
```

Essa ordem e apenas um exemplo. Como as threads executam concorrentemente, a
ordem exata pode variar. O importante e que o `synchronized` impede que duas
threads recebam o mesmo numero.

Tambem nao e obrigatorio que cada operario receba exatamente metade do
intervalo. O trabalhador mais rapido pode solicitar mais numeros.

## Por que usar dois contadores locais?

Uma alternativa seria todos os trabalhadores incrementarem diretamente um
unico `total`:

```java
total++;
```

Isso criaria outra disputa de concorrencia. O incremento nao e uma operacao
indivisivel: ele envolve ler, somar e gravar. Duas threads poderiam perder
atualizacoes.

O codigo atual evita esse problema porque cada operario altera seu proprio
`primos`. Depois que as threads terminam, o despachante soma os resultados:

```java
total += this.operarios[i].primos();
```

Essa soma acontece no despachante, depois do `join()` de cada trabalhador.

## Diferenca entre concorrencia e paralelismo

- Concorrencia: varias tarefas progridem no mesmo periodo.
- Paralelismo: varias tarefas executam ao mesmo tempo, geralmente em nucleos
  diferentes do processador.

O programa foi escrito para trabalhar de forma concorrente. Em uma maquina
com mais de um nucleo, os dois operarios tambem podem executar em paralelo.

## Complexidade do teste de primalidade

O metodo atual testa divisores ate `n - 1`. No pior caso, para um numero
primo grande, ele faz aproximadamente `n - 2` testes.

Portanto, o teste de um numero possui complexidade aproximada `O(n)`. Como o
programa testa muitos numeros, o processamento pode demorar.

Uma versao mais eficiente poderia testar somente ate a raiz quadrada de `n`:

```java
private static boolean ehPrimo(int n) {
    if (n < 2) return false;
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) return false;
    }
    return true;
}
```

Essa melhoria nao faz parte do codigo atual; ela aparece aqui como ponto de
estudo. Se `n` possuir um divisor maior que sua raiz quadrada, tambem existira
um divisor correspondente menor que a raiz.

## Perguntas comuns de prova

### Por que `Operario` herda de `Thread`?

Para que cada objeto possa executar seu metodo `run()` em um fluxo de
execucao separado.

### Por que usar `start()` em vez de `run()`?

`start()` inicia uma nova thread. Chamar `run()` diretamente nao cria uma
thread nova.

### Qual dado e compartilhado entre as threads?

O objeto `Despachante`, principalmente o atributo `numero`, e compartilhado
pelos `Operario`s.

### Por que `getNumero()` e `synchronized`?

Para proteger a leitura e o incremento de `numero`, evitando que dois
trabalhadores recebam o mesmo valor.

### Para que serve o `-1`?

Ele e um sentinela que indica que o intervalo terminou e que o trabalhador
deve sair do laco.

### Para que serve `join()`?

Para fazer uma thread esperar outra terminar antes de continuar.

### Por que `total` nao e incrementado pelos operarios?

Para evitar que varias threads alterem a mesma variavel ao mesmo tempo. Cada
operario conta localmente e o despachante soma depois.

### O intervalo inclui o numero final?

Sim. O numero final e processado porque a condicao de encerramento e
`numero > fim`.

### O numero 1 e primo?

Nao. O codigo trata esse caso explicitamente com `if (n == 1) return false`.

### O resultado depende da ordem em que as threads executam?

Nao. A ordem de distribuicao pode variar, mas cada numero e entregue uma vez
e todos os resultados sao somados ao final.

## Observacoes sobre o codigo atual

- O programa usa Java 25 no `pom.xml`.
- O metodo `main()` esta no formato simplificado aceito pelo Java 25; a forma
  tradicional `public static void main(String[] args)` e mais compativel com
  versoes antigas.
- Os blocos `catch` estao vazios. Isso evita que o programa mostre a excecao,
  mas tambem esconde possiveis erros.
- `ehPrimo()` foi feita para o intervalo iniciado em `1`. Para uma funcao
  geral, tambem seria importante tratar `n <= 0` como nao primo.
- A implementacao atual e correta para o intervalo utilizado, mas o teste de
  divisores pode ser otimizado ate a raiz quadrada.
- O tamanho do vetor de operarios pode ser alterado no terceiro argumento do
  construtor, por exemplo `new Despachante(1, 500_000, 4)`.

## 4. Arquivo `pom.xml`

Arquivo:

```text
pom.xml
```

O `pom.xml` e o arquivo de configuracao do Maven. Ele identifica o projeto e
define informacoes usadas durante a compilacao.

```xml
<groupId>br.com.faculdade</groupId>
<artifactId>primos</artifactId>
<version>1.0-SNAPSHOT</version>
```

- `groupId`: identifica o grupo ou organizacao do projeto.
- `artifactId`: nome do projeto.
- `version`: versao atual. `SNAPSHOT` indica uma versao em desenvolvimento.

```xml
<maven.compiler.source>25</maven.compiler.source>
<maven.compiler.target>25</maven.compiler.target>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
```

Essas propriedades informam que o codigo deve ser compilado usando a versao
25 da linguagem Java e que os arquivos usam codificacao UTF-8.

## Resumo para memorizar

```text
Main cria o Despachante.
Despachante inicia os Operarios.
Operarios pedem numeros ao Despachante.
synchronized entrega um numero por vez.
Operarios testam primalidade e contam localmente.
-1 indica fim do trabalho.
join espera as threads terminarem.
Despachante soma os contadores.
Main imprime o resultado.
```
