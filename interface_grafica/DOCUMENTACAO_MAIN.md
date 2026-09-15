# Documentacao dos arquivos `Main.java`

Este projeto possui dois arquivos `Main.java`:

- `src/main/java/br/com/faculdade/Main.java`: exemplo simples de saudacao.
- `agenda/src/main/java/br/com/faculdade/Main.java`: formulario de uma agenda.

Os dois programas utilizam **Java Swing**, uma biblioteca do Java para criacao de interfaces graficas.

## Conceitos basicos do Swing

- `JFrame`: janela principal da aplicacao.
- `JLabel`: texto fixo exibido na tela.
- `JTextField`: campo para digitacao de texto.
- `JButton`: botao que executa uma acao.
- `JRadioButton`: opcao de selecao, normalmente usada em grupo.
- `JComboBox`: lista suspensa de opcoes.
- `JCheckBox`: caixa de selecao que pode ser marcada ou desmarcada.
- `JOptionPane`: janela de mensagem ou dialogo.
- `ActionListener`: mecanismo que reage a eventos, como cliques em botoes.

## 1. `Main.java` da aplicacao principal

Arquivo: `src/main/java/br/com/faculdade/Main.java`

### 1.1 Pacote e importacoes

```java
package br.com.faculdade;

import javax.swing.*;
import java.awt.event.*;
```

`package` informa a qual pacote a classe pertence. O pacote deve corresponder ao caminho das pastas do arquivo.

`javax.swing.*` permite utilizar os componentes visuais, como `JFrame`, `JLabel`, `JTextField` e `JButton`.

O pacote `java.awt.event` fornece classes relacionadas a eventos. Neste arquivo, o evento e registrado por meio de uma expressao lambda.

### 1.2 Classe e metodo inicial

```java
public class Main {
    static void main(String[] args) {
```

`Main` e a classe que concentra o programa. O metodo `main` e o ponto de inicio da execucao.

`String[] args` recebe argumentos enviados pela linha de comando. Neste programa, ele nao e utilizado.

> Forma convencional do metodo inicial: `public static void main(String[] args)`.

### 1.3 Criacao e configuracao da janela

```java
JFrame janela = new JFrame();
janela.setTitle("Janela Principal");
janela.setSize(500,500);
janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
janela.setLayout(null);
```

Passo a passo:

1. `new JFrame()` cria uma janela.
2. `setTitle` define o texto da barra de titulo.
3. `setSize(500, 500)` define largura e altura em pixels.
4. `setDefaultCloseOperation` encerra o programa quando a janela e fechada.
5. `setLayout(null)` desativa o gerenciador de layout. Assim, cada componente precisa receber tamanho e coordenadas manualmente.

### 1.4 Criacao dos textos

```java
JLabel ola_mundo = new JLabel("Ola mundao  ");
ola_mundo.setSize(ola_mundo.getPreferredSize());
ola_mundo.setLocation(10,10);
janela.add(ola_mundo);
```

`JLabel` exibe um texto que o usuario nao edita.

- `getPreferredSize()` obtem o tamanho recomendado pelo Swing.
- `setLocation(10, 10)` posiciona o componente a 10 pixels da esquerda e do topo.
- `add` adiciona o componente dentro da janela.

Depois, o programa cria outro `JLabel` para perguntar o nome:

```java
JLabel name = new JLabel("Qual o seu nome?");
name.setSize(name.getPreferredSize());
int y = 10 + ola_mundo.getSize().height + 10;
name.setLocation(10, y);
janela.add(name);
```

A variavel `y` calcula a posicao vertical usando a altura do primeiro texto mais espacamentos. Isso evita escolher completamente a posicao no formato de um numero fixo.

### 1.5 Campo de nome

```java
JTextField inputName = new JTextField(30);
inputName.setSize(inputName.getPreferredSize());
int x = 10 + name.getSize().width + 10;
inputName.setLocation(x, y);
janela.add(inputName);
```

`JTextField` permite que o usuario digite o nome.

- O numero `30` representa a quantidade aproximada de colunas visuais.
- `getText()` sera utilizado depois para ler o que foi digitado.
- A coordenada `x` coloca o campo ao lado do texto “Qual o seu nome?”.

### 1.6 Botao e evento de clique

```java
JButton button = new JButton("ok");
button.setSize(button.getPreferredSize());
button.setLocation(10, y + inputName.getSize().height + 10);
janela.add(button);
```

O botao e criado, dimensionado, posicionado e adicionado a janela.

```java
button.addActionListener(e -> {
    String nome = inputName.getText();
    JOptionPane.showMessageDialog(
        null,
        "Ola, " + nome + "!",
        "Saudacao",
        JOptionPane.INFORMATION_MESSAGE
    );
});
```

`addActionListener` registra o que deve acontecer quando o botao for clicado.

1. `inputName.getText()` recupera o nome digitado.
2. O texto e concatenado com `"Ola, "`.
3. `showMessageDialog` mostra uma janela de informacao.

Por fim:

```java
janela.setVisible(true);
```

Essa chamada torna a janela visivel. Sem ela, a janela seria criada, mas nao apareceria para o usuario.

## 2. `Main.java` da aplicacao `agenda`

Arquivo: `agenda/src/main/java/br/com/faculdade/Main.java`

Este programa segue o mesmo processo geral: cria uma janela, adiciona componentes, registra eventos e torna a janela visivel. A diferenca e que ele possui um formulario maior.

### 2.1 Estruturas auxiliares

```java
import java.util.ArrayList;
import java.util.List;
```

Essas classes permitem armazenar:

- Os `JCheckBox` criados dinamicamente.
- As opcoes que foram marcadas pelo usuario.

```java
List<JCheckBox> listaOpcoes = new ArrayList<>();
```

`List<JCheckBox>` significa uma lista que armazena objetos do tipo `JCheckBox`.

### 2.2 Criacao da janela

```java
JFrame janela = new JFrame();
janela.setTitle("AGENDA");
janela.setSize(400,400);
janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
janela.setLayout(null);
```

Aqui e criada uma janela de 400 por 400 pixels. O uso de `setLayout(null)` faz com que todas as coordenadas sejam calculadas manualmente.

### 2.3 Campo ID

```java
JLabel id = new JLabel("Id");
id.setSize(id.getPreferredSize());
id.setLocation(10,10);
janela.add(id);

JTextField inputId = new JTextField(10);
inputId.setSize(inputId.getPreferredSize());
int x = 10 + id.getSize().width + 10;
inputId.setLocation(x, 10);
janela.add(inputId);
```

O `JLabel` identifica o campo. O `JTextField` recebe o ID digitado. A variavel `x` posiciona o campo depois do texto “Id”, deixando 10 pixels de espacamento.

### 2.4 Opcoes Pessoa ou Empresa

```java
JRadioButton inputPessoa = new JRadioButton("Pessoa");
JRadioButton inputEmpresa = new JRadioButton("Empresa");
ButtonGroup inputRadios = new ButtonGroup();
inputRadios.add(inputPessoa);
inputRadios.add(inputEmpresa);
```

Os dois `JRadioButton` representam escolhas alternativas. O `ButtonGroup` garante que apenas uma opcao possa ser selecionada por vez.

Depois, cada radio button recebe tamanho e localizacao e e adicionado a janela.

### 2.5 Campo Nome

```java
JLabel name = new JLabel("Nome: ");
name.setSize(name.getPreferredSize());
int y = 10 + id.getSize().height + 10;
name.setLocation(10, y);
janela.add(name);

JTextField inputName = new JTextField(15);
inputName.setSize(inputName.getPreferredSize());
int xNome = 10 + name.getSize().width + 10;
inputName.setLocation(xNome, y);
janela.add(inputName);
```

O campo Nome e colocado na linha abaixo do ID. O calculo de `y` usa a altura do campo anterior e um espacamento de 10 pixels.

### 2.6 Sexo e telefone

```java
String[] sexos = {"Masculino", "Femenino", "outro"};
JComboBox<String> inputSexo = new JComboBox<>(sexos);
```

O vetor `sexos` contem as opcoes exibidas no `JComboBox`. O usuario escolhe uma delas.

O telefone utiliza outro campo de texto:

```java
JTextField inputTelefone = new JTextField(10);
```

O programa posiciona o combo box e o campo de telefone na mesma linha, calculando as coordenadas com base na largura dos componentes anteriores.

### 2.7 Checkboxes criados em um laco

```java
String[] opcoes = {"Amigo", "Cliente   "};

for (String opcao : opcoes) {
    JCheckBox cb = new JCheckBox(opcao);
    cb.setSize(cb.getPreferredSize());
    cb.setLocation(xCheckbox, yCheckbox);
    janela.add(cb);
    listaOpcoes.add(cb);
    yCheckbox += cb.getSize().height + 5;
}
```

O `for` percorre o vetor de opcoes. Para cada texto:

1. Cria um `JCheckBox`.
2. Define seu tamanho.
3. Define sua posicao.
4. Adiciona o checkbox na janela.
5. Guarda o checkbox na lista `listaOpcoes`.
6. Aumenta `yCheckbox` para que o proximo fique abaixo do anterior.

Guardar os componentes na lista e importante porque permite percorrer todos eles depois, tanto para verificar quais foram marcados quanto para limpa-los.

### 2.8 Botao Agendar

```java
JButton button1 = new JButton("Agendar");
button1.setSize(button1.getPreferredSize());
button1.setLocation(10, yCheckbox + 10);
janela.add(button1);
```

O botao e colocado abaixo dos checkboxes.

Quando ele e clicado, o programa le todos os campos:

```java
String id_colect = inputId.getText();
String entidade = "";
```

`getText()` recupera os valores dos campos de texto. A variavel `entidade` comeca vazia e sera preenchida conforme o radio button selecionado:

```java
if (inputPessoa.isSelected()) {
    entidade = "Pessoa";
} else if (inputEmpresa.isSelected()) {
    entidade = "Empresa";
}
```

`isSelected()` retorna `true` quando o componente esta selecionado.

O restante dos valores e obtido assim:

```java
String nome = inputName.getText();
String sexoSelecionado = (String) inputSexo.getSelectedItem();
String tel = inputTelefone.getText();
```

- `getText()` le campos de texto.
- `getSelectedItem()` le a opcao escolhida no combo box.
- O cast `(String)` informa que o item selecionado sera tratado como texto.

Para descobrir os checkboxes marcados:

```java
List<String> opcoesMarcadas = new ArrayList<>();
for (JCheckBox cb : listaOpcoes) {
    if (cb.isSelected()) {
        opcoesMarcadas.add(cb.getText());
    }
}
```

O programa percorre a lista, verifica cada checkbox e adiciona o texto dos itens selecionados em `opcoesMarcadas`.

Depois, uma mensagem e montada:

```java
String mensagem = "Ola, " + nome + "!\n" +
        "ID: " + id_colect + "\n" +
        "Entidade: " + entidade + "\n" +
        "Sexo: " + sexoSelecionado + "\n" +
        "Telefone: " + tel + "\n" +
        "Opcoes: " + opcoesMarcadas;
```

`\n` quebra a linha. A lista `opcoesMarcadas` e convertida para texto automaticamente durante a concatenacao.

Por fim, `JOptionPane.showMessageDialog` apresenta os dados na tela.

### 2.9 Botao Cancelar

O botao Cancelar registra outro evento:

```java
buttonCancelar.addActionListener(e -> {
    inputId.setText("");
    inputName.setText("");
    inputTelefone.setText("");
    inputRadios.clearSelection();
    inputSexo.setSelectedIndex(0);

    for (JCheckBox cb : listaOpcoes) {
        cb.setSelected(false);
    }
});
```

Quando clicado:

1. Os campos de texto recebem uma string vazia.
2. `clearSelection()` desmarca Pessoa ou Empresa.
3. `setSelectedIndex(0)` seleciona novamente a primeira opcao do combo box.
4. O `for` percorre os checkboxes e desmarca todos.

Esse botao nao fecha a janela. Ele apenas restaura o formulario ao estado inicial.

### 2.10 Exibicao da janela

```java
janela.setVisible(true);
```

Essa e a ultima etapa da montagem da interface. A janela so aparece depois que todos os componentes e eventos foram configurados.

## Fluxo geral para memorizar

Nos dois programas, o fluxo e:

1. Importar as bibliotecas.
2. Declarar a classe `Main`.
3. Criar o `JFrame`.
4. Configurar titulo, tamanho, fechamento e layout.
5. Criar os componentes visuais.
6. Definir tamanho e posicao dos componentes.
7. Adicionar os componentes ao `JFrame`.
8. Registrar os eventos dos botoes com `addActionListener`.
9. Ler os valores com `getText`, `isSelected` e `getSelectedItem`.
10. Exibir ou limpar os dados.
11. Tornar a janela visivel com `setVisible(true)`.

## Observacoes importantes

- O programa da agenda nao salva os dados permanentemente. Ele apenas exibe os valores em uma mensagem.
- `setLayout(null)` facilita o primeiro aprendizado, mas layouts sao mais indicados para interfaces que precisam se adaptar ao tamanho da janela.
- Os campos nao possuem validacao. Portanto, o usuario pode clicar em `Agendar` sem preencher os dados.
- O texto `Femenino` provavelmente deveria ser `Feminino`.
- Para executar de forma convencional, o metodo inicial deve ser declarado como `public static void main(String[] args)`.
