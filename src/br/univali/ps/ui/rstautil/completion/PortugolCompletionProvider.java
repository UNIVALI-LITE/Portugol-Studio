package br.univali.ps.ui.rstautil.completion;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ArvoreSintaticaAbstrataPrograma;
import br.univali.ps.ui.Editor;
import br.univali.ps.ui.rstautil.PortugolParser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.fife.ui.autocomplete.*;

public final class PortugolCompletionProvider extends LanguageAwareCompletionProvider implements PropertyChangeListener
{
    private List<Completion> dynamicCompletions = new ArrayList<Completion>();
    private Editor.EscopoCursor escopoCursor;

    public PortugolCompletionProvider()
    {
        setDefaultCompletionProvider(createCodeCompletionProvider());
        setStringCompletionProvider(createStringCompletionProvider());
        setCommentCompletionProvider(createCommentCompletionProvider());
    }

    /**
     * Adds shorthand completions to the code completion provider.
     *
     * @param codeCP The code completion provider.
     */
    protected void addShorthandCompletions(DefaultCompletionProvider codeCP)
    {
        codeCP.addCompletion(new ShorthandCompletion(codeCP, "ini", "funcao inicio(cadeia arg[])\n{\n\t${cursor}\n}", "funcao inicio(cadeia arg[]) {}"));
    }

    protected CompletionProvider createCodeCompletionProvider()
    {
        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        cp.addCompletions(addTemplateCompletions(cp));
        //loadCodeCompletionsFromXml(cp);
        //addShorthandCompletions(cp);
       
        return cp;
    }

    /**
     * Returns the provider to use when in a comment.
     *
     * @return The provider.
     * @see #createCodeCompletionProvider()
     * @see #createStringCompletionProvider()
     */
    protected CompletionProvider createCommentCompletionProvider()
    {
        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        cp.addCompletion(new BasicCompletion(cp, "FAZER:", "to-do", "Um lembrete para realizar uma tarefa"));
        cp.addCompletion(new BasicCompletion(cp, "CONSERTE-ME:", "bug", "Um problema que precisa ser arruamdo"));
        return cp;
    }

    /**
     * Returns the completion provider to use when the caret is in a string.
     *
     * @return The provider.
     * @see #createCodeCompletionProvider()
     * @see #createCommentCompletionProvider()
     */
    protected CompletionProvider createStringCompletionProvider()
    {
        DefaultCompletionProvider cp = new DefaultCompletionProvider();
        cp.addCompletion(new BasicCompletion(cp, "\\n", "Nova linha", "Imprime uma nova linha"));
        return cp;
    }

    protected void loadCodeCompletionsFromXml(DefaultCompletionProvider cp)
    {
        ClassLoader cl = getClass().getClassLoader();
        String res = "br/univali/ps/ui/rstautil/completion/portugol.xml";
        if (res != null)
        { // Subclasses may specify a null value
            InputStream in = cl.getResourceAsStream(res);
            try
            {
                if (in != null)
                {
                    cp.loadFromXML(in);
                    in.close();
                }
                else
                {
                    cp.loadFromXML(new File(res));
                }
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace(System.err);
            }
        }
    }

    private void updateGlobalSimbolsCompletions(DefaultCompletionProvider cp, ResultadoAnalise resultadoAnalise, Editor.EscopoCursor escopoCursor)
    {
        // Se houve erros sintáticos, mantemos o autocomplete gerado        
        // com a última ASA válida
        
        if (resultadoAnalise.getNumeroErrosSintaticos() == 0)
        {
            if (dynamicCompletions != null)
            {
                for (Completion completion : dynamicCompletions)
                {
                    cp.removeCompletion(completion);
                }
            }
            //cp.clear();
            
            dynamicCompletions = new ASTCompletionFactory().createCompletions((ArvoreSintaticaAbstrataPrograma) resultadoAnalise.getAsa(), cp, escopoCursor);
            //cp.addCompletions(addTemplateCompletions(cp));
            cp.addCompletions(dynamicCompletions);
            
        }
    }

    public void setEscopoCursor(Editor.EscopoCursor escopoCursor)
    {
        this.escopoCursor = escopoCursor;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt)
    {
        if (PortugolParser.PROPERTY_RESULTADO_ANALISE.equals(evt.getPropertyName()))
        {
            DefaultCompletionProvider cp = (DefaultCompletionProvider) getDefaultCompletionProvider();
            ResultadoAnalise resultadoAnalise = (ResultadoAnalise) evt.getNewValue();

            updateGlobalSimbolsCompletions(cp, resultadoAnalise, escopoCursor);
        }
        
            
    }

    private List<Completion> addTemplateCompletions(CompletionProvider cp)
    {   
        List<Completion> list = new ArrayList<Completion>();
        
        list.add(new TemplateCompletion(cp, "b", "comando","cadeia ${cursor}","Tipo de dado",explicacaoTipoCadeia()));
        
        list.add(new TemplateCompletion(cp, "cadeia", "comando","cadeia ${cursor}","Tipo de dado",explicacaoTipoCadeia()));
        list.add(new TemplateCompletion(cp, "caracter", "comando","caracter ${cursor}","Tipo de dado",explicacaoTipoCaracter()));
        list.add(new TemplateCompletion(cp, "caso","comando","caso ${valor} :\n\t\t${cursor}\n\t\tpare","Instrucao caso",explicacaoComandoCaso()));
	list.add(new TemplateCompletion(cp, "casocontrario","comando","caso contrario :\n\t\t${cursor}\n\t\tpare","Caminho alternativo do escolha",explicacaoEstadoContrario()));
        list.add(new TemplateCompletion(cp, "const","comando","const ${cursor}","Torna um valor constante",explicacaoDeclaracaoConst()));
        
        list.add(new TemplateCompletion(cp, "enquanto", "comando", "enquanto(${condicao})\n{\n\t${cursor}\n}", "Laço de repetição 'enquanto'", explicacaoComandoEnquanto()));
        list.add(new TemplateCompletion(cp, "escolha", "comando", "escolha(${chave})\n{\n\tcaso ${valor} :\n\t\t${cursor}\n\t\tpare\n\tcaso contrario :\n\n\t\tpare\n}", "Bloco escolha", explicacaoComandoEscolha()));
        
        list.add(new TemplateCompletion(cp, "faca", "comando", "faca\n{\n\t${cursor}\n} enquanto(${condicao})", "Laço de repetição 'faça enquanto'", explicacaoComandoFacaEnquanto()));
        list.add(new TemplateCompletion(cp, "falso","comando","falso ${cursor}","Valor do tipo lógico",explicacaoCasoFalso()));        
        list.add(new TemplateCompletion(cp, "funcao","comando", "funcao ${tipo_retorno} ${nome}( ) \n{\n\t${cursor}\n}", "Declaracao de função",explicacaoComandoFuncao()));
        
        list.add(new TemplateCompletion(cp, "inclua", "comando","inclua biblioteca ${cursor}","Usar uma biblioteca de funcoes",explicacaoTipoInteiro()));
        
        list.add(new TemplateCompletion(cp, "inteiro", "comando","inteiro ${cursor}","Tipo de dado",explicacaoTipoInteiro()));
        
        list.add(new TemplateCompletion(cp, "logico","comando","logico ${cursor}", "Tipo de dado",explicacaoTipoLogico()));
        
        
        list.add(new TemplateCompletion(cp, "para", "comando", "para(inteiro ${i} = 0; ${i} < ${limite}; ${i}++)\n{\n\t${cursor}\n}", "Laço de repetição 'para'", explicacaoComandoPara()));
        list.add(new TemplateCompletion(cp, "pare","comando","pare ${cursor}","Parar a execução do bloco atual",explicacaoComandoPare()));
        
        list.add(new TemplateCompletion(cp, "se", "comando", "se(${codicao})\n{\n\t${cursor}\n}", "Desvio condicional simples", explicacaoComandoSe()));
        list.add(new TemplateCompletion(cp, "se", "comando", "se(${codicao})\n{\n\t${cursor}\n}\nsenao \n{\n\t\n}", "Desvio condicional composto", explicacaoComandoSeSenao()));
        list.add(new TemplateCompletion(cp, "senao", "comando", "senao \n{\n\t${cursor}\n}", "Caminho falso", explicacaoComandoSenao()));
        list.add(new TemplateCompletion(cp, "senao", "comando", "senao se(${codicao}) \n{\n\t${cursor}\n}", "Caminho falso condicional", explicacaoComandoSenaoSe()));
        
        list.add(new TemplateCompletion(cp, "real", "comando","real ${cursor}","Tipo de dado",explicacaoTipoReal()));
        list.add(new TemplateCompletion(cp, "retorne","comando","retorne ${expressao}","Retorno de funcao",explicacaoComandoRetorne()));
        
        list.add(new TemplateCompletion(cp, "vazio", "comando","vazio ${cursor}","Tipo de dado",explicacaoEstadoVazio()));
        list.add(new TemplateCompletion(cp, "verdadeiro","comando","verdadeiro ${cursor}","Valor do tipo lógico",explicacaoCasoVerdadeiro()));
        
        return list;
    }

    private String explicacaoComandoPara()
    {
        return "<body><h2>Comando PARA</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>O comando <b>PARA</b>  fornece uma maneira compacta para repetir uma"
                + " sequencia de instruções.</p><p> Essa operação faz um loop "
                + "repetidamente até que uma determinada condição é satisfeita.</p>"
                + "<p>A forma de declaração do <b>PARA</b> pode ser expressa da seguinte forma:</p>"
                + "<p><b>para</b> ( &#60inicialização> ; &#60condição> ; &#60incremento> ) {</p>"
                + "<p>	&#60instruções></p>"
                + "<p>}</p>"
                + "<p>Ao utilizar esta versão do de declaração, tenha em mente que:</p>"
                + "<p># A inicialização inicializa a expressão loop; é executado uma vez, tal como o ciclo começa.</p>"
                + "<p># Quando a condição expressão é avaliada como false , o loop termina.</p>"
                + "<p># O incremento de expressão é invocada depois de cada iteração do loop.</p>";
    }

    private String explicacaoComandoEnquanto()
    {
        return "<body>	<h2> Comando Enquanto </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p> &nbsp; O comando ENQUANTO permite"
                + " que uma sequ&ecirc;ncia de instru&ccedil;&otilde;es sejam"
                + " executada v&aacute;rias vezes, at&eacute; que uma ou mais"
                + " condi&ccedil;&otilde;es sejam satisfeitas, ou seja, repete "
                + "um conjunto de instru&ccedil;&otilde;es sem que seja "
                + "necess&aacute;rio escreve-l&aacute; v&aacute;rias vezes. </p>"
                + "<p> &nbsp; Para utilizar o comando ENQUANTO voc&ecirc; "
                + "deve usar a palavra reservada <b>enquanto</b> e entre"
                + " par&ecirc;nteses colocar a condi&ccedil;&atilde;o, "
                + "ap&oacute;s o fecha par&ecirc;nteses deve se abrir e "
                + "fechar chaves e entre elas colocar a "
                + "instru&ccedil;&atilde;o(&otilde;es) a ser repetida enquanto"
                + " a condi&ccedil;&atilde;o(&otilde;es) for verdadeira. "
                + "</p>	<table class='tabela2'> <tr class='linha2'> "
                + "<td><b> COMANDO ENQUANTO</b></td></tr><tr class='linha1'><td><p>&nbsp; "
                + "&nbsp; <b>enquanto</b>(&lt;condi&ccedil;&atilde;o,condi&ccedil;&otilde;es&gt;) <br>"
                + "&nbsp; <b>{</b> <br>	&nbsp;&nbsp;&nbsp;&nbsp;	"
                + "&lt;intru&ccedil;&atilde;o,instru&ccedil;&otilde;es&gt; <br>"
                + "&nbsp; <b>}</b> <br>	</td></tr></table>";
    }
    
     public String explicacaoBiblioteca(){
        return "<h2>Biblioteca</h2><p class='subtitulo'>"
                + "O que &eacute;?</p><p>Chama a biblioteca passado após está declaração.</p><p>Exemplo:</p>"
                + "<p>biblioteca funcoesVetoriais";
    }
    public String explicacaoComandoSe(){
        return "<body> <h2> Comando SE </h2><p class='subtitulo'>"
                + "O que &eacute;?</p><p>A operação SE é a mais básica de todas as declarações de controle de fluxo. </p>"
                + " <p>Ele diz para os seu programa executar uma determinada seção do código "
                + "<b>SOMENTE</b> se um determinado teste avalia a <b>VERDADEIRO</b>.</p>"
                + "<p>Exemplo: </p> "
                + "<p>se (&#60condição>){</p>  <p>    &#60instruções></p> <p>}</p>";
    }
    public String explicacaoComandoSeSenao(){
        return "<body> <h2> Comando SE e SENAO</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p><p class='subtitulo'>O <b>SENAO</b> é a declaração de controle de fluxo "
                + "que fornece um caminho secundário de "
                + "execução quando uma cláusula <b>SE</b> anterior for avaliada como <b>FALSO</b>.</p>"
                + "<p><b>se</b> (&#60condição>){</p>" 
                +"<p>      &#60instruções></p>" 
                +"<p>} <b>senão</b> {</p>" 
                +"<p>      &#60instruções></p>" 
                +"<p>}</p>";
    }
    
    public String explicacaoComandoSenao(){
        return "<body> <h2> Comando SENAO</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p><p class='subtitulo'>O <b>SENAO</b> é a declaração de controle de fluxo "
                + "que fornece um caminho secundário de "
                + "execução quando uma cláusula <b>SE</b> anterior for avaliada como <b>FALSO</b>.</p>"
                + "<b>senão</b> {</p>" 
                + "<p>      &#60instruções></p>" 
                + "<p>}</p>";
    }
    
    public String explicacaoComandoSenaoSe(){
        return "<body> <h2> Comando SENAO seguido por SE </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p><p class='subtitulo'>O <b>SENAO</b> é a declaração de controle de fluxo "
                + "que fornece um caminho secundário de "
                + "execução quando uma cláusula <b>SE</b> anterior for avaliada como <b>FALSO</b> um novo teste SE é realizado.</p>"
                + "<b>senão</b> <b>se</b> (&#60condição>){</p>" 
                + "<p>      &#60instruções></p>" 
                + "<p>}</p>";
    }
    
    public String explicacaoComandoDefina(){
        return "";
    }
    public String explicacaoTipoInteiro(){
        return "<body> <h2> Tipo Inteiro </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p><p>Está expressão é usada para declarar uma variavel do tipo Inteiro, ou seja, "
                +"qualquer número (negativo, nulo ou positivo)"
                + " que pertença ao conjunto dos "
                +"números inteiros.</p>"
                +"<p>Exemplos: </p><p>1520 |  0  | -2  | 25 | 1324 | -43 </p>";
    }
    public String explicacaoEstadoVazio(){
        return "<h2> Estado Vazio </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p>"
                + "<p>Especifica que que a variavel está vazia, ou seja, não possui valor algum.</p>"
                + "<p>Exemplo: </p>"
                + "<p>var = vazio</p>";
    }
    public String explicacaoTipoReal(){
        return "<body> <h2> Tipo Real </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>Está expressão é usada para declarar uma variavel do tipo Real, ou seja, "
                + "qualquer número (negativo, nulo ou positivo) que pertença ao conjunto dos"
                +"números reais. </p>"
                +"<p>Exemplos: </p><p> 23.5  |  2520.25  |  97.5</p>";
    }
    public String explicacaoTipoCaracter(){
        return "<body> <h2> Tipo Caracter </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>Está expressão é usada para declarar uma variavel do tipo Caracter, ou seja, "
                + "Qualquer informação composta de apenas UM caracter alfanumérico ou especial.</p>"
                +"<p>Os caracteres são representados entre aspas simples.</p>"
                +"<p>Exemplos: </p><p> 'a' | 't' | '#' | '8' | '+' </p>";
    }
    public String explicacaoTipoLogico(){
        return "<body> <h2> Tipo Lógico </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>Está expressão é usada para declarar uma variavel do tipo Lógico, ou seja, "
                + "uma variavel que pode assumir somente dois valores, <b>VERDADEIRO</b> ou <b>FALSO</b>. </p>"
                + "<p>Exemplo: </p>"
                + "<p>logico variavel</p>";
    }
    public String explicacaoTipoCadeia(){
        return "<body> <h2> Tipo Cadeia </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>Está expressão é usada para declarar uma variavel do tipo Cadeia, ou seja, "
                + " qualquer informação composta de MAIS DE UM caracter alfanumérico ou"
                +"especial.</p><p> As cadeias são representadas entre aspas duplas.</p>"
                +"<p>Exemplos: </p><p> \"abc\" | \"Bom Dia !\" | \"Luis Inácio\" |\"1998\"</p>";
    }
    public String explicacaoComandoFuncao(){
        return "";
    }
    public String explicacaoComandoEscolha(){
        return "";
    }
    public String explicacaoComandoCaso(){
        return "";
    }
    public String explicacaoComandoPare(){
        return "<body><h2>Comando PARA</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p>"
                + "<p>O comando <b>PARE</b> é usado para terminar "
                + "a execução de um laço ENQUANTO, FACA ENQUANTO ou PARA, pulando para o próximo comando depois do laço</p>"
                + "<p>Exemplo: </p>"
                + "<p>enquanto (&#60condição>){</p>"
                + "se (&#60condição>){"
                + "pare"
                + "}"
                + "}";
    }    
    public String explicacaoComandoFacaEnquanto(){
        return "<body>	<h2> Comando Faca Enquanto </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> &nbsp;"
                + "<p>O comando <b>FACA ENQUANTO</b> funciona da mesma forma que o <b>ENQUANTO</b>, a unica"
                + "diferença é que o este comando executa uma vez antes de testa a condição.</p>"
                + "<p>Exemplo: </p>"
                + "<p><b>faca</b> {</p>"
                + "<p>&#60instruções></p>"
                + "<p>}<b>enquanto</b>(&#60condição>)</p>";
    }
    public String explicacaoComandoRetorne(){
        return "<body>	<h2> Retorne </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> &nbsp;";
    }
    public String explicacaoCasoFalso(){
        return "<body>	<h2> Falso </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> &nbsp;"
                + "<p>Está condição serve para atribuir valor a uma variavel ou para não validar uma condição de um"
                + "laço de repetição ou desvio condicional.</p>"
                + "<p>Exemplo atribuição: </p>"
                + "<p>logico variavel = verdaddeiro</p>"
                + "<p>Exemplo validação:</p>"
                + "<p>se (<b>falso</b>){</p>"
                + "<p>&#60instruções></p>"
                + "<p>}</p>";
    }
    public String explicacaoCasoVerdadeiro(){
        return "<body>	<h2> Verdadeiro </h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> &nbsp;"
                + "<p>Está condição serve para atribuir valor a uma variavel ou para validar uma condição de um"
                + "laço de repetição ou desvio condicional.</p>"
                + "<p>Exemplo atribuição: </p>"
                + "<p>logico variavel = verdaddeiro</p>"
                + "<p>Exemplo validação:</p>"
                + "<p>se (<b>verdadeiro</b>){</p>"
                + "<p>&#60instruções></p>"
                + "<p>}</p>";
    }
    public String explicacaoDeclaracaoConst(){
        return   "<body> <h2>Declaração de Constante</h2>"
                +"<p>Além das variáveis os nossos programas podem armazenar informações"
                +"por meio de “constantes”.</p><p> As constantes são informações que foram inseridas pelo "
                +"próprio programador no código do algoritmo.</p> "
                +"<p>Exemplo: </p>"
                +"<p>const inteiro idade_usuario = 37</p>"                
                +"<p>Resultado = 100 - idade_usuario</p>";
    }
     public String explicacaoEstadoContrario(){
        return "";
    }
}
