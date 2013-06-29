package br.univali.ps.ui;

import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.dominio.PortugolDocumentoListener;
import br.univali.ps.nucleo.PortugolStudio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import org.fife.ui.autocomplete.AbstractCompletionProvider;
import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.FunctionCompletion;
import org.fife.ui.autocomplete.ParameterizedCompletion;
import org.fife.ui.rsyntaxtextarea.ErrorStrip;
import org.fife.ui.rsyntaxtextarea.RSyntaxDocument;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.FoldParserManager;
import org.fife.ui.rsyntaxtextarea.parser.AbstractParser;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParseResult;
import org.fife.ui.rsyntaxtextarea.parser.DefaultParserNotice;
import org.fife.ui.rsyntaxtextarea.parser.ParseResult;
import org.fife.ui.rtextarea.RTextScrollPane;

public class Editor extends JPanel implements AlteradorFonte, PortugolDocumentoListener{

    private RSyntaxTextArea textArea = null;
    private RTextScrollPane scrollPane = null;
    private AutoCompletion autoCompletion = null;
    private ErrorStrip errorStrip;
    private MyParser notificaErrosEditor;
        
    public Editor() {
        final PortugolDocumento portugolDocumento = new PortugolDocumento();
        portugolDocumento.addPortugolDocumentoListener(this);
        textArea = new RSyntaxTextArea(portugolDocumento);
        
        FoldParserManager.get().addFoldParserMapping("text/por", new CurlyFoldParser(true, false));
        textArea.setSyntaxEditingStyle("text/por");
        textArea.setCodeFoldingEnabled(true);
        
        textArea.setUseFocusableTips(true);
        notificaErrosEditor = new MyParser();
        textArea.addParser(notificaErrosEditor);
        errorStrip = new ErrorStrip(textArea);
               
        
        scrollPane = new RTextScrollPane(textArea);
        autoCompletion = new AutoCompletion(createCompletionProvider());
        autoCompletion.install(textArea);
        autoCompletion.setShowDescWindow(true);
        textArea.setAntiAliasingEnabled(true);
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);  
        this.add(errorStrip, BorderLayout.LINE_END);
    }
    @Override
    public void aumentarFonte (){                
        final Font fonteAtual = textArea.getFont();
        float novoTamanho = fonteAtual.getSize() + 4;
        if(novoTamanho < 70  ){
            textArea.setFont(fonteAtual.deriveFont(novoTamanho));        
        }
    }
    @Override 
    public void diminuirFonte (){
        final Font fonteAtual = textArea.getFont();
        float novoTamanho = fonteAtual.getSize() - 4;
        if(novoTamanho > 12  ){
            textArea.setFont(fonteAtual.deriveFont(novoTamanho));        
        }
        
    }
    
    public void setPortugolDocumento(PortugolDocumento documento){
        documento.addPortugolDocumentoListener(this);
        textArea.setDocument(documento);
    }

    public PortugolDocumento getPortugolDocumento() {
        return (PortugolDocumento)textArea.getDocument();
    }
    
    public void destacarErros(ResultadoAnalise resultadoAnalise){
        
        if (resultadoAnalise.getNumeroTotalErros() > 0)
        {
            notificaErrosEditor.setErros(resultadoAnalise);
        }       
    }
    
    private Object tag = null;
        
    public void iniciarDepuracao(){
        textArea.setHighlightCurrentLine(false);
    }
    
    public void pararDepuracao(){
        textArea.removeAllLineHighlights();
        textArea.setHighlightCurrentLine(true);
        
    }
    
    public void destacarLinha(int linha)
    {
        try
        {
            if (tag != null)
                textArea.removeLineHighlight(tag);
            
            tag = textArea.addLineHighlight(linha - 1, new Color(0f, 1f, 0f, 0.15f));            
        }
        catch (BadLocationException ex)
        {
            Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void posicionaCursor(int linha, int coluna){
        textArea.setCaretPosition(0);
        try {
            while (textArea.getLineOfOffset(textArea.getCaretPosition()) < (linha - 1)) {
                textArea.setCaretPosition(textArea.getCaretPosition() + 1);
            }
            textArea.setCaretPosition(textArea.getCaretPosition() + coluna);
            textArea.requestFocus();
        } catch (BadLocationException ex) {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
    }

    @Override
    public void requestFocus() {
        textArea.requestFocus();
    }

    @Override
    public void documentoModificado(boolean status)
    {
        ResultadoAnalise resultadoAnalise = Portugol.analisar(textArea.getText());
        notificaErrosEditor.setErros(resultadoAnalise);        
        
    }

    @Override
    public void nomeArquivoAlterado(String nome)
    {
      
    }
    
    private class MyParser extends AbstractParser {

        ResultadoAnalise resultadoAnalise = null;
        
        @Override
        public ParseResult parse(RSyntaxDocument rsd, String string)
        {
            DefaultParseResult defaultParseResult = new DefaultParseResult(this);
            if (this.resultadoAnalise != null){
                for (ErroSemantico erro : resultadoAnalise.getErrosSemanticos())
                {
                    try
                    {                   
                        int indice = textArea.getLineStartOffset(erro.getLinha() - 1) + erro.getColuna();            
                        int tamanhoTexto = erro.getTrechoCodigoFonte().getTamanhoTexto();            
                        DefaultParserNotice notice = new DefaultParserNotice(this, erro.getMensagem(), erro.getLinha() - 1, indice, tamanhoTexto);
                        notice.setShowInEditor(true);
                        defaultParseResult.addNotice(notice);
                    }
                    catch (BadLocationException ex)
                    {
                        Logger.getLogger(Editor.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
                
            }
            return defaultParseResult;
        }

        private void setErros(ResultadoAnalise resultadoAnalise)
        {
            
            this.resultadoAnalise = resultadoAnalise;            
            textArea.forceReparsing(this);
        }
        
    }
    
    private CompletionProvider createCompletionProvider() {

		// A DefaultCompletionProvider is the simplest concrete implementation
		// of CompletionProvider.  This provider has no understanding of
		// language semantics. It simply checks the text entered up to the
		// caret position for a match against known completions. This is all
		// that is needed in the majority of cases.
		AbstractCompletionProvider provider  = new DefaultCompletionProvider();
                
		// Add completions for all Java keywords.  A BasicCompletion is just
		// a straightforward word completion.
		provider.addCompletion(new BasicCompletion(provider, "programa","","<html><h1>Programa<h1><p>server para declarar um programa!<p></html>"));
		//provider.addCompletion(new BasicCompletion(provider, "biblioteca","",explicacaoBiblioteca()));
		provider.addCompletion(new BasicCompletion(provider, "se","",explicacaoComandoSe()));
		provider.addCompletion(new BasicCompletion(provider, "senao","",explicacaoComandoSenao()));
		provider.addCompletion(new BasicCompletion(provider, "defina","",explicacaoComandoDefina()));
		provider.addCompletion(new BasicCompletion(provider, "inteiro","",explicacaoTipoInteiro()));
		provider.addCompletion(new BasicCompletion(provider, "vazio","",explicacaoEstadoVazio()));
		provider.addCompletion(new BasicCompletion(provider, "real","",explicacaoTipoReal()));
		provider.addCompletion(new BasicCompletion(provider, "caracter","",explicacaoTipoCaracter()));
		provider.addCompletion(new BasicCompletion(provider, "logico","",explicacaoTipoLogico()));
		provider.addCompletion(new BasicCompletion(provider, "cadeia","",explicacaoTipoCadeia()));
		provider.addCompletion(new BasicCompletion(provider, "funcao","",explicacaoComandoFuncao()));
		provider.addCompletion(new BasicCompletion(provider, "escolha","",explicacaoComandoEscolha()));
		provider.addCompletion(new BasicCompletion(provider, "caso","",explicacaoComandoCaso()));
		provider.addCompletion(new BasicCompletion(provider, "pare","",explicacaoComandoPare()));
                provider.addCompletion(new BasicCompletion(provider, "para","",explicacaoComandoPara()));
		provider.addCompletion(new BasicCompletion(provider, "contrario","",explicacaoEstadoContrario()));
		provider.addCompletion(new BasicCompletion(provider, "faca","",explicacaoComandoFacaEnquanto()));
		provider.addCompletion(new BasicCompletion(provider, "enquanto","",explicacaoComandoEnquanto()));
		provider.addCompletion(new BasicCompletion(provider, "retorne","",explicacaoComandoRetorne()));
		provider.addCompletion(new BasicCompletion(provider, "falso","",explicacaoCasoFalso()));
		provider.addCompletion(new BasicCompletion(provider, "verdadeiro","",explicacaoCasoVerdadeiro()));
		provider.addCompletion(new BasicCompletion(provider, "const","",explicacaoDeclaracaoConst()));
		
		return provider;

    }

    
    public String explicacaoComandoEnquanto(){
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
    public String explicacaoComandoSenao(){
        return "<body> <h2> Comando SENAO</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p><p class='subtitulo'>O <b>SENAO</b> é a declaração de controle de fluxo "
                + "que fornece um caminho secundário de "
                + "execução quando uma cláusula <b>SE</b> avaliada como <b>FALSO</b>.</p>"
                + "<p><b>se</b> (&#60condição>){</p>" 
                +"<p>      &#60instruções></p>" 
                +"<p>}senão {</p>" 
                +"<p>      &#60instruções></p>" 
                +"<p>}</p>";
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
    public String explicacaoComandoPara(){
        return "<body><h2>Comando PARA</h2> <p class='subtitulo'>"
                + "O que &eacute;?</p> <p>O comando <b>PARA</b>  fornece uma maneira compacta para repetir uma"
                + " sequencia de instruções.</p><p> Essa operação faz um loop "
                + "repetidamente até que uma determinada condição é satisfeita.</p>"
                + "<p>A forma de declaração do <b>PARA</b> pode ser expressa da seguinte forma:</p>"
                +"<p><b>para</b> ( &#60inicialização> ; &#60condição> ; &#60incremento> ) {</p>"
                +"<p>	&#60instruções></p>"
                +"<p>}</p>"
                +"<p>Ao utilizar esta versão do de declaração, tenha em mente que:</p>"
                +"<p># A inicialização inicializa a expressão loop; é executado uma vez, tal como o ciclo começa.</p>"
                +"<p># Quando a condição expressão é avaliada como false , o loop termina.</p>"
                +"<p># O incremento de expressão é invocada depois de cada iteração do loop.</p>";
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
     
    public RSyntaxTextArea getTextArea() 
    {
        return textArea;
    }
}
