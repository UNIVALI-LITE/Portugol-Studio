![logo](https://raw.githubusercontent.com/UNIVALI-LITE/Portugol-Studio/master/portulogo.png)
# Portugol-Studio

[![Gitter](https://badges.gitter.im/UNIVALI-LITE/Portugol-Studio.svg)](https://gitter.im/UNIVALI-LITE/Portugol-Studio?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge) [![License](https://img.shields.io/badge/License-GPL--3.0-4AB495.svg)](https://github.com/UNIVALI-LITE/Portugol-Studio/blob/master/LICENSE.md)

O [Portugol Studio](http://lite.acad.univali.br/portugol/) é um ambiente para aprender a programar, voltado para os iniciantes em programação que falam o idioma português. Possui uma sintaxe fácil, diversos exemplos e materiais de apoio à aprendizagem. Também possibilita a criação de jogos e outras aplicações.
***
Os principais recursos do Portugol Studio são:

* Interface simples e didática
* Sistema de ajuda e material de apoio ao aprendizado
* Atualização automática
* Árvore estrutural do programa
* Editor de código fonte com recursos avançados
* Depurador interativo
* Suporte à bibliotecas e desenvolvimento de jogos

O **Portugol Studio** é resultado de um esforço continuado que já envolveu vários artigos científicos, além de 6 trabalhos de conclusão de curso e uma dissertação de mestrado em computação. A ferramenta possui mais de 90 mil downloads e tem sido utilizada em diversas universidades e institutos de tecnologia no Brasil e em outros países de idioma português.

## Guia para usuários iniciantes (Windows)

### 1. Instalando o Portugol Studio

1. Baixe o arquivo de instalação do Portugol Studio em seu computador

2. Dê um duplo clique no ícone do arquivo de instalação

3. Confirme o acesso em modo "Administrador" e siga as instruções de instalação na tela

### 2. Executando o Portugol Studio

Após a instalação do Portugol Studio, um ícone de atalho deverá ter sido criado na área
de trabalho do usuário. Para executar o Portugol Studio, dê um duplo clique no atalho.

Na primeira vez em que o Portugol Studio for executado, é possível que seja exibida uma 
mensagem pedindo a liberação do programa no Firewall do sistema operacional. Caso esta
mensagem seja exibida, selecione a opção "Permitir" ou "Desbloquear". Se esta opção não
for ativada, o Portugol Studio não será capaz de realizar as atualizações automáticas.

O programa de instalação associa automaticamente os arquivos com extensão ".por" com o 
Portugol Studio. Desta forma, é possível abrir rapidamente um arquivo Portugol dando
um duplo clique sobre o ícone do arquivo.

O Portugol Studio também permite executar os programas em Portugol de forma independente,
sem a necessidade de abrí-los no ambiente. São disponibilizadas duas opções:

    1. Executar em modo console: executa o programa em uma janela de linha de comando
	   do Windows, como um programa nativo

    2. Executar em modo Gráfico: executa o programa como um aplicativo de janela do 
	   Windows. Nenhuma janela de linha de comando é exibida neste modo. Só funciona
	   com programas que utilizam a biblioteca "Graficos" do Portugol
		
Para acessar as duas opções mencionadas, clique com o botão direito do mouse sobre o
ícone do arquivo Portugol que deseja executar.


### 3. Resolução de Problemas

Durante a instalação do Portugol Studio, o assistente de instalação procura por versões
anteriores do Portugol Studio e tenta removê-las automaticamente. Em alguns casos, esta
remoção pode falhar fazendo com que o Portugol Studio não execute ou apresente erros na
tela.

Se isto ocorrer, execute os procedimentos a seguir:

1. Abra o painel de controle do Windows e acesse a opção "Programas e Recursos" ou 
   "Adicionar e Remover Programas".
   
2. Selecione o Portugol Studio na lista de programas instalados e clique na opção
   "Remover"/"Desinstalar". Aguarde até que o Portugol Studio seja completamente
   removido do computador
   
3. Abra uma nova janela do Windows Explorer (navegador de arquivos) e na barra de endereços
   digite o seguinte caminho: C:\ProgramData\UNIVALI. Pressione ENTER
   
4. Se o sistema informar que o caminho não existe, prossiga para a próxima etapa. Se o 
   caminho existir, será exibida uma pasta chamada "Portugol Studio". Apague esta pasta e todo
   seu conteúdo. Após a remoção da pasta, prossiga para a próxima etapa
   
5. Execute novamente o programa de instalação do Portugol Studio, conforme descrito no item
   1 deste documento
   


## Compilando o Portugol
Se você está interessado em ajudar no desenvolvimento do  **Portugol Studio**, ou testar a versão mais atual do sistema, é necessário seguir alguns passos.
Primeiramente é preciso a IDE [NetBeans](https://netbeans.org/) e ter os Projetos que fazem parte do Portugol Studio
### Projetos Necessários
* Portugol Ajuda
* Portugol Núcleo
* Portugol Relator de Erros
* Portugol Studio Plugins
* Portugol Utils
* Portugol Studio Recursos

>Estes Projetos Podem ser encontrados em nossa [Pagina](https://github.com/UNIVALI-LITE)

***
O **Portugol Studio** é desenvolvido pelo [LITE](http://lite.acad.univali.br/)
