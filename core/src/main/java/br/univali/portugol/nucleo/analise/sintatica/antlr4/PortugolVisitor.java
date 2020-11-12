// Generated from Portugol.g4 by ANTLR 4.7.2
package br.univali.portugol.nucleo.analise.sintatica.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PortugolParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PortugolVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PortugolParser#arquivo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArquivo(PortugolParser.ArquivoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#inclusaoBiblioteca}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusaoBiblioteca(PortugolParser.InclusaoBibliotecaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#listaDeclaracoes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaDeclaracoes(PortugolParser.ListaDeclaracoesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#declaracao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracao(PortugolParser.DeclaracaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#declaracaoVariavel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracaoVariavel(PortugolParser.DeclaracaoVariavelContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#declaracaoMatriz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracaoMatriz(PortugolParser.DeclaracaoMatrizContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#inicializacaoMatriz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializacaoMatriz(PortugolParser.InicializacaoMatrizContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#linhaMatriz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinhaMatriz(PortugolParser.LinhaMatrizContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#colunaMatriz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColunaMatriz(PortugolParser.ColunaMatrizContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#declaracaoArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracaoArray(PortugolParser.DeclaracaoArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#inicializacaoArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializacaoArray(PortugolParser.InicializacaoArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#tamanhoArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTamanhoArray(PortugolParser.TamanhoArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#declaracaoFuncao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracaoFuncao(PortugolParser.DeclaracaoFuncaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#listaParametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaParametros(PortugolParser.ListaParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(PortugolParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#parametroArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametroArray(PortugolParser.ParametroArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#parametroMatriz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametroMatriz(PortugolParser.ParametroMatrizContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#comando}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComando(PortugolParser.ComandoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#atribuicao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribuicao(PortugolParser.AtribuicaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atribuicaoCompostaSoma}
	 * labeled alternative in {@link PortugolParser#atribuicaoComposta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribuicaoCompostaSoma(PortugolParser.AtribuicaoCompostaSomaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atribuicaoCompostaSubtracao}
	 * labeled alternative in {@link PortugolParser#atribuicaoComposta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribuicaoCompostaSubtracao(PortugolParser.AtribuicaoCompostaSubtracaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atribuicaoCompostaMultiplicacao}
	 * labeled alternative in {@link PortugolParser#atribuicaoComposta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribuicaoCompostaMultiplicacao(PortugolParser.AtribuicaoCompostaMultiplicacaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atribuicaoCompostaDivisao}
	 * labeled alternative in {@link PortugolParser#atribuicaoComposta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribuicaoCompostaDivisao(PortugolParser.AtribuicaoCompostaDivisaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#retorne}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetorne(PortugolParser.RetorneContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#se}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSe(PortugolParser.SeContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#senao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSenao(PortugolParser.SenaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#enquanto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnquanto(PortugolParser.EnquantoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#facaEnquanto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFacaEnquanto(PortugolParser.FacaEnquantoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#para}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPara(PortugolParser.ParaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#listaComandos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaComandos(PortugolParser.ListaComandosContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#inicializacaoPara}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializacaoPara(PortugolParser.InicializacaoParaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#condicao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondicao(PortugolParser.CondicaoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#incrementoPara}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementoPara(PortugolParser.IncrementoParaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#escolha}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEscolha(PortugolParser.EscolhaContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#caso}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaso(PortugolParser.CasoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#pare}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPare(PortugolParser.PareContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#indiceArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndiceArray(PortugolParser.IndiceArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code chamadaFuncao}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChamadaFuncao(PortugolParser.ChamadaFuncaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(PortugolParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negacaoBitwise}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegacaoBitwise(PortugolParser.NegacaoBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenciaArray}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenciaArray(PortugolParser.ReferenciaArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numeroReal}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeroReal(PortugolParser.NumeroRealContext ctx);
	/**
	 * Visit a parse tree produced by the {@code maisUnario}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMaisUnario(PortugolParser.MaisUnarioContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoDiferenca}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoDiferenca(PortugolParser.OperacaoDiferencaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code menosUnario}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMenosUnario(PortugolParser.MenosUnarioContext ctx);
	/**
	 * Visit a parse tree produced by the {@code adicao}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdicao(PortugolParser.AdicaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoXor}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoXor(PortugolParser.OperacaoXorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoMaiorIgual}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoMaiorIgual(PortugolParser.OperacaoMaiorIgualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code decrementoUnarioPrefixado}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecrementoUnarioPrefixado(PortugolParser.DecrementoUnarioPrefixadoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code incrementoUnarioPosfixado}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementoUnarioPosfixado(PortugolParser.IncrementoUnarioPosfixadoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiplicacao}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicacao(PortugolParser.MultiplicacaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoOuLogico}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoOuLogico(PortugolParser.OperacaoOuLogicoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoIgualdade}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoIgualdade(PortugolParser.OperacaoIgualdadeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoShiftRight}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoShiftRight(PortugolParser.OperacaoShiftRightContext ctx);
	/**
	 * Visit a parse tree produced by the {@code divisao}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDivisao(PortugolParser.DivisaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressaoEntreParenteses}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressaoEntreParenteses(PortugolParser.ExpressaoEntreParentesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoMenorIgual}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoMenorIgual(PortugolParser.OperacaoMenorIgualContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenciaMatriz}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenciaMatriz(PortugolParser.ReferenciaMatrizContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoMaior}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoMaior(PortugolParser.OperacaoMaiorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numeroInteiro}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumeroInteiro(PortugolParser.NumeroInteiroContext ctx);
	/**
	 * Visit a parse tree produced by the {@code caracter}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaracter(PortugolParser.CaracterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenciaParaVariavel}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenciaParaVariavel(PortugolParser.ReferenciaParaVariavelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code valorLogico}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValorLogico(PortugolParser.ValorLogicoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoMenor}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoMenor(PortugolParser.OperacaoMenorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoShiftLeft}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoShiftLeft(PortugolParser.OperacaoShiftLeftContext ctx);
	/**
	 * Visit a parse tree produced by the {@code incrementoUnarioPrefixado}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementoUnarioPrefixado(PortugolParser.IncrementoUnarioPrefixadoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoELogico}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoELogico(PortugolParser.OperacaoELogicoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code decrementoUnarioPosfixado}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecrementoUnarioPosfixado(PortugolParser.DecrementoUnarioPosfixadoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoOrBitwise}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoOrBitwise(PortugolParser.OperacaoOrBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code modulo}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModulo(PortugolParser.ModuloContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subtracao}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtracao(PortugolParser.SubtracaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code negacao}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegacao(PortugolParser.NegacaoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code operacaoAndBitwise}
	 * labeled alternative in {@link PortugolParser#expressao}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperacaoAndBitwise(PortugolParser.OperacaoAndBitwiseContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#listaExpressoes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListaExpressoes(PortugolParser.ListaExpressoesContext ctx);
	/**
	 * Visit a parse tree produced by {@link PortugolParser#escopoBiblioteca}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEscopoBiblioteca(PortugolParser.EscopoBibliotecaContext ctx);
}