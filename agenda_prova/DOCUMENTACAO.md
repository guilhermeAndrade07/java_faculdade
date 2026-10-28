# Documentação do projeto Agenda

## Visão geral

Este projeto implementa uma agenda gráfica em Java usando Swing. É possível incluir, consultar, navegar, editar e excluir contatos. Os contatos ficam guardados em uma `ArrayList` na memória; não há banco de dados nem gravação em arquivo, então os dados são perdidos quando o programa é encerrado.

Um contato pode ser uma pessoa ou uma empresa. Pessoas podem ter sexo e ser marcadas como amigas; pessoas e empresas podem ser marcadas como clientes.

## Arquivos Java (`src`)

### `Main.java`

Ponto de entrada do programa.

- `main(String[] args)`: cria uma instância de `AgendaGUI`. A criação da janela inicia a interface e seu funcionamento.

### `Contato.java`

Representa os dados de um contato. A classe não desenha a interface nem controla as ações da agenda.

**Atributos**

- `contador`: contador estático usado para gerar IDs nos contatos criados pelo construtor padrão.
- `id`: identificador do contato.
- `tipo`: `'P'` para pessoa ou `'E'` para empresa.
- `nome` e `telefone`: dados textuais do contato.
- `sexo`: `'M'` ou `'F'` para pessoa; espaço (`' '`) para empresa.
- `amigo` e `cliente`: indicam se o contato possui essas características.

**Construtores**

- `Contato()`: incrementa `contador`, atribui o novo valor a `id` e inicializa o contato como pessoa do sexo masculino. Os demais dados começam sem preenchimento.
- `Contato(int id, char tipo, String nome, char sexo, String telefone, boolean amigo, boolean cliente)`: inicializa todos os campos com os valores recebidos. Não altera o contador estático.

**Métodos**

- `getId()`, `getTipo()`, `getNome()`, `getSexo()` e `getTelefone()`: retornam os respectivos dados.
- `isAmigo()` e `isCliente()`: retornam os valores dos indicadores booleanos.
- `setTipo()`, `setNome()`, `setSexo()`, `setTelefone()`, `setAmigo()` e `setCliente()`: atualizam os respectivos dados.

### `Widgets.java`

Classe-base com métodos auxiliares para criar componentes Swing. Cada método cria um componente, ajusta seu tamanho ao tamanho preferido e o devolve para uso na interface.

- `criarJLabel(String mensagem)`: cria um rótulo.
- `criarJTextField(int tamanho)`: cria um campo de texto com a quantidade de colunas indicada.
- `criarJRadioButton(String mensagem, boolean selecionado)`: cria uma opção de seleção e define se começa selecionada.
- `criarJComboBox(String... valores)`: cria uma lista suspensa e adiciona as opções recebidas.
- `criarJCheckBox(String mensagem)`: cria uma caixa de seleção.
- `criarJButton(String mensagem)`: cria um botão.

### `AgendaWidgets.java`

Especializa `Widgets` e reúne os componentes usados pela agenda. Ela separa a criação dos controles da lógica que trata os contatos.

- `AgendaWidgets()`: chama os métodos que constroem a barra de ferramentas e a área de dados.
- `criarComponentesBarraFerramentas()`: cria os botões de navegação (`|<`, `<`, `>`, `>|`), inclusão (`+`), exclusão (`-`), edição (`Δ`), salvamento (`✓`) e cancelamento (`↶`), além do rótulo que mostra a posição atual.
- `criarComponentesAreaDados()`: cria os rótulos, campos de ID, nome e telefone, opções Pessoa/Empresa, lista de sexo e caixas Amigo/Cliente. Os botões Pessoa e Empresa são colocados em um `ButtonGroup`, de modo que somente um deles possa ficar selecionado.
- Métodos `get...()`: devolvem os componentes para que `AgendaGUI` possa adicioná-los à janela, ler seus valores, alterar seu estado ou registrar eventos. Há um getter para cada componente.

### `RowPanel.java`

Painel Swing que organiza componentes em linhas usando posicionamento absoluto (`setLayout(null)`).

- `RowPanel()`: configura o painel sem um gerenciador de layout.
- `setSpacing(int horizontal, int vertical)`: define os espaçamentos horizontal e vertical. Também inicializa a posição de desenho (`x` e `y`) com esses valores.
- `addRow(JComponent... components)`: calcula a altura do componente mais alto da linha, posiciona cada componente lado a lado e centralizado verticalmente, adiciona-os ao painel e avança a posição vertical para a próxima linha. Ao terminar, reinicia a posição horizontal no espaçamento definido.

### `AgendaGUI.java`

Janela principal e responsável pela lógica da agenda. Mantém a lista de contatos, acompanha qual registro está selecionado e decide se a tela está consultando, incluindo ou editando.

**Atributos**

- `widgets`: conjunto de componentes da interface, criado por `AgendaWidgets`.
- `contatos`: `ArrayList<Contato>` que mantém os registros durante a execução.
- `pos`: índice do contato exibido na lista. Começa em `-1`, que indica que não há contato selecionado.
- `estado`: modo atual da tela: `"query"` (consulta), `"insert"` (inclusão) ou `"update"` (edição).

**Construtor e montagem da tela**

- `AgendaGUI()`: define o título, tamanho e comportamento ao fechar a janela; chama `configurarLayout()` e `configurarEvents()`; exibe a janela e atualiza os controles.
- `configurarLayout()`: cria a barra de ferramentas, coloca nela os botões e o indicador de posição, monta a área de dados usando `RowPanel` e organiza as duas áreas com `BorderLayout`.
- `configurarEvents()`: associa os cliques dos botões e as mudanças das opções Pessoa/Empresa aos métodos que tratam essas ações.

**Ações dos botões**

- `btnPrimeiro_click(...)`: seleciona o primeiro contato (`pos = 0`) e atualiza a tela.
- `btnAnterior_click(...)`: diminui o índice para exibir o contato anterior e atualiza a tela.
- `btnProximo_click(...)`: aumenta o índice para exibir o próximo contato e atualiza a tela.
- `btnUltimo_click(...)`: seleciona o último contato e atualiza a tela.
- `btnIncluir_click(...)`: muda para o modo de inclusão, cria um contato, adiciona-o ao fim da lista, seleciona-o e atualiza a tela.
- `btnEditar_click(...)`: muda para o modo de edição e atualiza os controles para permitir alterações.
- `btnExcluir_click(...)`: pede confirmação. Se a resposta for positiva, remove o contato atual e ajusta `pos` quando o registro removido era o último. Depois atualiza a tela.
- `btnSalvar_click(...)`: obtém o contato sendo incluído ou editado; lê os campos da tela e atualiza os dados do objeto. Para pessoa, grava tipo, sexo e indicador de amigo. Para empresa, grava tipo, sexo em branco e `amigo` como falso. Em ambos os casos, grava nome, telefone e indicador de cliente. Ao final, volta ao modo de consulta.
- `btnCancelar_click(...)`: se estava incluindo, remove o contato temporário criado para essa inclusão. Em seguida, volta ao modo de consulta e atualiza a tela. Na edição, as alterações feitas nos campos ainda não foram aplicadas ao objeto, portanto a atualização volta a exibir os dados originais.
- `rbPessoa_click(...)`: durante inclusão ou edição, habilita sexo e a opção Amigo.
- `rbEmpresa_click(...)`: durante inclusão ou edição, desabilita sexo e a opção Amigo.

**Atualização da interface**

- `atualizaGUI()`: sincroniza os controles com o estado e a posição atual.
  - Habilita ou desabilita navegação, inclusão, edição, exclusão, salvamento e cancelamento conforme o modo e a quantidade de registros.
  - Define quais campos podem ser editados.
  - Se houver um contato selecionado (`pos >= 0`), copia os dados dele para os campos e seleciona Pessoa ou Empresa.
  - Se não houver contato selecionado, limpa os campos e seleciona os valores iniciais.
  - Atualiza o indicador de posição no formato `posição/quantidade`.

## Arquivos de configuração e suporte

- `agenda_prova.iml`: configuração do módulo do IntelliJ IDEA. Define `src` como pasta de código-fonte e usa o JDK configurado no projeto.
- `.gitignore`: lista arquivos e diretórios gerados ou específicos de IDEs que o Git deve ignorar, como `out/`, `bin/`, `.vscode/` e arquivos de configuração de outras IDEs.
- `.idea/`: configurações locais do projeto no IntelliJ IDEA. Esses arquivos ajudam a IDE a abrir e configurar o projeto; não contêm a lógica da agenda.
- `out/`: arquivos `.class` compilados a partir dos fontes Java. São gerados pelo processo de compilação e não são os arquivos-fonte que devem ser editados.

## Fluxo resumido de uso

1. `Main.main()` cria `AgendaGUI`.
2. A janela cria os componentes com `AgendaWidgets` e `Widgets`, organiza-os e registra os eventos.
3. As ações do usuário alteram `contatos`, `pos` e/ou `estado`.
4. `atualizaGUI()` atualiza os campos e os botões conforme esses valores.
5. Os registros existem somente na memória enquanto a aplicação está aberta.
