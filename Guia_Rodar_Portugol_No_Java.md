# Portugol Console
O Portugol-Console é um interpretador auto-executável da linguagem Portugol 2.0.
***

## Guia de como usar o Portugol-Console para interpretar algoritmos em Portugol criados por meio de uma classe java

O Console pode ser usado sem a necessidade do [Portugol-Studio](http://lite.acad.univali.br/portugol/), para isso é preciso ter:
* A IDE [Netbeans](https://netbeans.org/downloads/)
* O projeto do [Portugol-Console](https://github.com/UNIVALI-LITE/Portugol-Console)
* O projeto do [Portugol-Nucleo](https://github.com/UNIVALI-LITE/Portugol-Nucleo)

### 1. Abrindo os projetos necessários

1. Abra no Netbeans os projetos do Portugol-Nucleo e do Portugol-Console, para isso:

		Arquivo -> Abrir Projeto

### 2. Criando e Configurando seu Projeto 

1. Criar seu projeto java:
		
		Arquivo -> Novo Projeto -> Java -> Aplicação Java
    
Dê um nome ao seu projeto, marque as opções **Usar Pasta dedicada para Armazenar Bibliotecas** e **Criar Classe Principal**, é aconselhável não alterar as opções padrão, a não ser que você já saiba o que está fazendo, depois disso é só finalizar.

2. Escrevendo o código:

Seu código deverá ser escrito dentro do método **public static void main(String[] args)** e seu algoritmo em Portugol precisará estar guardado dentro de uma **String**. Neste exemplo será usado um algoritmo em Portugol que converte uma temperatura em Celsius para Fahrenheit, mas você pode usar o algoritmo que preferir. Depois de faze-lo teremos parecido com isso:
```java
	String AlgoritmoPortugol = "programa" +
                                "{" +
                                    "funcao inicio()" +
                                    "{" +
                                    "   real c, f" +
                                    "   escreva(\"Qual a temperatura em Celsius? :\")" +
                                    "   leia(c)" +
                                    "   f = c * (9.0/5.0) + 32.0" +
                                    "   escreva(\"Temperatura em Fahrenheit: \",f)" +
                                    "}" +
                                "}";

```

Após termos nosso algoritmo em Portugol será necessário criar um arquivo com ele, para isso usaremos a classe *PrintWriter*, para que ela funcione é necessario informar ao java, portanto logo abaixo da primeira linha do programa, que contém **package nome_do_seu_projeto**  é necessário adicionar os seguintes comandos: 
```java
	import java.io.IOException;
	import java.io.PrintWriter;
```
E após o código do seu Algoritmo do Portugol adicione as instruções para criar o arquivo:
```java
	try{
            PrintWriter criarArquivo = new PrintWriter("programa.por", "ISO-8859-1"); //isso define que o arquivo criado será ".por",voce pode alterar o nome do arquivo, mas mantenha esta codificação.
            criarArquivo.println(AlgoritmoPortugol); //Aqui vem a String com o algoritmo.
            criarArquivo.close();
        } catch (IOException e) {
            System.out.println("Algo deu errado ao criar o arquivo.");
        }
```
Por fim criaremos outra **String** que armazenará o caminho do nosso arquivo criado, além disso será necessário criar um *array* de **Strings** com apenas uma posição e nesta posição guardar o caminho do nosso arquivo. Isso é preciso devido ao funcionamento do Console, pois ele espera receber um *array* por parametro. E após isso iremos realizar a chamada do Portugol-Console informando esse *array*. 

Será necessário importar também a classe do Console, para isso adicione a linha abaixo junto aos outros **imports**, que estão após linha **package**, que é a primeira do programa.
```java
	import br.univali.portugol.Console;
```
E após o trexo de criação do arquivo adicione:
```java
	String CaminhoPrograma = ".\\programa.por"; //informe o nome do arquivo gerado anteriormente.
    String [] arg = new String[1]; 
    arg[0]= CaminhoPrograma;
    Console.main(arg);
```

### 3. Executando o programa

Para que seu algoritmo funcione corretamente é necessário realizar algumas configurações dentro das propriedades do projeto primeiro.

1.	Adicionando Bibliotecas necessárias:

Na aba projetos, clique no sinal ' + ', a esquerda do seu projeto, localize a pasta **Bibliotecas**, clique com o botão direito sobre ela:

	Adicionar Projeto -> Localize em seu computador o projeto do Portugol-Console e repita o processo para adicionar o Portugol-Nucleo.

Clique novamente com o botão direito sobre a pasta **Bibliotecas**:

		Adicionar JAR/Pasta -> Localize em seu computador a pasta do Portugol-Console -> Pasta Lib.
        
Repita o processo para os 3 arquivos JAR desta pasta:
* antlr-runtime-3.5.2.jar
* commons-exec-1.3.jar
* jlayer-1.0.1.jar

2.	Incluir Opção de VM:

Ainda aba projetos, clique com o botão direito sobre o seu projeto:

	Propriedades(última opção) -> Executar -> Localize o campo: Opções de VM.    

Adicione a seguinte instrução:

	-Dnetbeans=true
    
Pressione *Ok* para guardar a configuração.

### Agora você pode executar seu projeto

	Pressionando F6 ou no menu superior Executar -> Executar(primeira opção).