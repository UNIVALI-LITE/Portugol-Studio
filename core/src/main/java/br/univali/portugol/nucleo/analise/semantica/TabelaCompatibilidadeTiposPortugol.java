package br.univali.portugol.nucleo.analise.semantica;

import br.univali.portugol.nucleo.analise.semantica.erros.ExcecaoImpossivelDeterminarTipoDado;
import br.univali.portugol.nucleo.analise.semantica.erros.ExcecaoValorSeraConvertido;
import br.univali.portugol.nucleo.asa.NoOperacao;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseE;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseLeftShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseOu;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseRightShift;
import br.univali.portugol.nucleo.asa.NoOperacaoBitwiseXOR;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaE;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaOU;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import br.univali.portugol.nucleo.asa.TipoDado;
import java.util.TreeMap;
import java.util.Map;
import static br.univali.portugol.nucleo.asa.TipoDado.CADEIA;
import static br.univali.portugol.nucleo.asa.TipoDado.CARACTER;
import static br.univali.portugol.nucleo.asa.TipoDado.INTEIRO;
import static br.univali.portugol.nucleo.asa.TipoDado.LOGICO;
import static br.univali.portugol.nucleo.asa.TipoDado.REAL;
import static br.univali.portugol.nucleo.asa.TipoDado.TODOS;
import static br.univali.portugol.nucleo.asa.TipoDado.VAZIO;
import static br.univali.portugol.nucleo.analise.semantica.TabelaCompatibilidadeTiposPortugol.Resultado.*;
import java.util.Comparator;

/**
 *
 * @author Fillipi Domingos Pelz
 */
public final class TabelaCompatibilidadeTiposPortugol implements TabelaCompatibilidadeTipos
{
    static enum Resultado { TIPOS_INCOMPATIVEIS, OCORRE_CONVERSAO };
    
    private Map<Class<? extends NoOperacao>, Object[][]> tabelas;
    private Object[][] tabelaChamadaFuncao;    
    private Object[][] tabelaRetornoFuncao;
    
    public static final TabelaCompatibilidadeTiposPortugol INSTANCE = new TabelaCompatibilidadeTiposPortugol();
    
    private final class ComparadorClasse implements Comparator<Class>
    {
        @Override
        public int compare(Class classeA, Class classeB)
        {
            return classeA.getName().compareTo(classeB.getName());
        }
    }
    
    private TabelaCompatibilidadeTiposPortugol()
    {
        tabelas = new TreeMap<Class<? extends NoOperacao>, Object[][]>(new ComparadorClasse());
        
        tabelas.put(NoOperacaoAtribuicao.class, criarTabelaCompatibilidadeAtribuicao());
        tabelas.put(NoOperacaoDivisao.class, criarTabelaCompatibilidadeDivisaoMultiplicacaoSubtracao());
        tabelas.put(NoOperacaoLogicaDiferenca.class, criarTabelaCompatibilidadeDiferencaIgualdade());
        tabelas.put(NoOperacaoLogicaE.class, criarTabelaCompatibilidadeEOu());
        tabelas.put(NoOperacaoLogicaIgualdade.class, criarTabelaCompatibilidadeDiferencaIgualdade());
        tabelas.put(NoOperacaoLogicaMaior.class, criarTabelaCompatibilidadeMaiorMaiorIgualMenorMenorIgual());
        tabelas.put(NoOperacaoLogicaMaiorIgual.class, criarTabelaCompatibilidadeMaiorMaiorIgualMenorMenorIgual());
        tabelas.put(NoOperacaoLogicaMenor.class, criarTabelaCompatibilidadeMaiorMaiorIgualMenorMenorIgual());
        tabelas.put(NoOperacaoLogicaMenorIgual.class, criarTabelaCompatibilidadeMaiorMaiorIgualMenorMenorIgual());
        tabelas.put(NoOperacaoLogicaOU.class, criarTabelaCompatibilidadeEOu());
        tabelas.put(NoOperacaoModulo.class, criarTabelaCompatibilidadeModulo());
        tabelas.put(NoOperacaoMultiplicacao.class, criarTabelaCompatibilidadeDivisaoMultiplicacaoSubtracao());        
        tabelas.put(NoOperacaoSoma.class, criarTabelaCompatibilidadeSoma());
        tabelas.put(NoOperacaoSubtracao.class, criarTabelaCompatibilidadeDivisaoMultiplicacaoSubtracao());
        tabelas.put(NoOperacaoBitwiseLeftShift.class, criarTabelaCompatibilidadeBitwise());
        tabelas.put(NoOperacaoBitwiseRightShift.class, criarTabelaCompatibilidadeBitwise());
        tabelas.put(NoOperacaoBitwiseE.class, criarTabelaCompatibilidadeBitwise());
        tabelas.put(NoOperacaoBitwiseOu.class, criarTabelaCompatibilidadeBitwise());
        tabelas.put(NoOperacaoBitwiseXOR.class, criarTabelaCompatibilidadeBitwise());
        
        tabelaChamadaFuncao = criarTabelaCompatibilidadeChamadaFuncao();
        tabelaRetornoFuncao = criarTabelaCompatibilidadeRetornoFuncao();
    }

    private TipoDado obterTipoDado(Object[][] tabela, TipoDado tipoDadoA, TipoDado tipoDadoB) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido
    {
        Object retorno = tabela[tipoDadoA.ordinal()][tipoDadoB.ordinal()];

        if (retorno == TIPOS_INCOMPATIVEIS)
        {
            throw new ExcecaoImpossivelDeterminarTipoDado();
        }
        else if (retorno == OCORRE_CONVERSAO)
        {
            throw new ExcecaoValorSeraConvertido(tipoDadoB, tipoDadoA);
        }
        
        return (TipoDado) retorno;
    }

    @Override
    public TipoDado obterTipoRetornoOperacao(Class<? extends NoOperacao> operacao, TipoDado tipoDadoOperandoEsquerdo, TipoDado tipoDadoOperandoDireito) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido
    {
        return obterTipoDado(tabelas.get(operacao), tipoDadoOperandoEsquerdo, tipoDadoOperandoDireito);
    }

    @Override
    public TipoDado obterTipoRetornoPassagemParametro(TipoDado tipoDadoEsperado, TipoDado tipoDadoPassado) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido
    {
        return obterTipoDado(tabelaChamadaFuncao, tipoDadoEsperado, tipoDadoPassado);
    }
    
    @Override
    public TipoDado obterTipoRetornoFuncao(TipoDado tipoDadoEsperado, TipoDado tipoDadoPassado) throws ExcecaoImpossivelDeterminarTipoDado, ExcecaoValorSeraConvertido
    {
        return obterTipoDado(tabelaRetornoFuncao, tipoDadoEsperado, tipoDadoPassado);
    }
    
    private Object[][] criarTabelaCompatibilidadeChamadaFuncao()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = CARACTER;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = OCORRE_CONVERSAO;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = OCORRE_CONVERSAO;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = REAL;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = CARACTER;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[TODOS.ordinal()][REAL.ordinal()] = REAL;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        return tabela;
    }
    
    
     private Object[][] criarTabelaCompatibilidadeRetornoFuncao()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = CARACTER;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = OCORRE_CONVERSAO;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = OCORRE_CONVERSAO;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = REAL;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = CARACTER;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[TODOS.ordinal()][REAL.ordinal()] = REAL;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        return tabela;
    }
    
    
    

    private Object[][] criarTabelaCompatibilidadeAtribuicao()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = CARACTER;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = OCORRE_CONVERSAO;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = OCORRE_CONVERSAO;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = REAL;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        return tabela;
    }

    private Object[][] criarTabelaCompatibilidadeDivisaoMultiplicacaoSubtracao()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = REAL;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = REAL;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = REAL;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }

    private Object[][] criarTabelaCompatibilidadeDiferencaIgualdade()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = LOGICO;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = LOGICO;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = LOGICO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = LOGICO;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = LOGICO;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = LOGICO;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }

    private Object[][] criarTabelaCompatibilidadeEOu()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }

    private Object[][] criarTabelaCompatibilidadeMaiorMaiorIgualMenorMenorIgual()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = LOGICO;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = LOGICO;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = LOGICO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = LOGICO;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = LOGICO;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = LOGICO;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }

    private Object[][] criarTabelaCompatibilidadeModulo()
    {
         int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }
    
     private Object[][] criarTabelaCompatibilidadeBitwise()
    {
         int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }
    
    private Object[][] criarTabelaCompatibilidadeSoma()
    {
        int tamanho = TipoDado.values().length;
        Object[][] tabela = new Object[tamanho][tamanho];
        
        tabela[CADEIA.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][CARACTER.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][INTEIRO.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][LOGICO.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][REAL.ordinal()] = CADEIA;
        tabela[CADEIA.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CADEIA.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[CARACTER.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[CARACTER.ordinal()][CARACTER.ordinal()] = CADEIA;
        tabela[CARACTER.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[CARACTER.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[INTEIRO.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[INTEIRO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][INTEIRO.ordinal()] = INTEIRO;
        tabela[INTEIRO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][REAL.ordinal()] = REAL;
        tabela[INTEIRO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[INTEIRO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
                 
        tabela[LOGICO.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[LOGICO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][LOGICO.ordinal()] = LOGICO;
        tabela[LOGICO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[LOGICO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[REAL.ordinal()][CADEIA.ordinal()] = CADEIA;
        tabela[REAL.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][INTEIRO.ordinal()] = REAL;
        tabela[REAL.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][REAL.ordinal()] = REAL;
        tabela[REAL.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[REAL.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[VAZIO.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[VAZIO.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;
        
        tabela[TODOS.ordinal()][CADEIA.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][CARACTER.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][INTEIRO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][LOGICO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][REAL.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][VAZIO.ordinal()] = TIPOS_INCOMPATIVEIS;
        tabela[TODOS.ordinal()][TODOS.ordinal()] = TIPOS_INCOMPATIVEIS;        
        
        return tabela;
    }
}
