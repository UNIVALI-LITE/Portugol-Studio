package br.univali.ps.ui.editor.formatador;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.ProgramaVazio;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoContinue;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoEnquanto;
import br.univali.portugol.nucleo.asa.NoEscolha;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
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
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorAtribuicao;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorAtributo;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorChamadaMetodo;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorDeclaracaoMetodo;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorDeclaracaoVariavel;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorOperacao;
import br.univali.portugol.nucleo.execucao.gerador.helpers.GeradorSwitchCase;
import br.univali.portugol.nucleo.execucao.gerador.helpers.Utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatadorCodigo
{

    private static final Logger LOGGER = Logger.getLogger(FormatadorCodigo.class.getName());

    private final GeradorChamadaMetodo geradorChamadaMetodo = new GeradorChamadaMetodo();
    private final GeradorSwitchCase geradorSwitchCase = new GeradorSwitchCase();
    private final GeradorDeclaracaoMetodo geradorDeclaracaoMetodo = new GeradorDeclaracaoMetodo();
    private final GeradorOperacao geradorOperacao = new GeradorOperacao();
    private final GeradorAtributo geradorAtributo = new GeradorAtributo();
    private final GeradorDeclaracaoVariavel geradorDeclaracaoVariavel = new GeradorDeclaracaoVariavel();
    private final GeradorAtribuicao geradorAtribuicao = new GeradorAtribuicao();

    private boolean processandoVariaveisGlobais = false; // não inicializa as variáveis quando está processando as variáveis globais

    public static String formata(String codigo) throws ErroCompilacao
    {
        Programa programa = Portugol.compilarParaAnalise(codigo);
        ASAPrograma asa = programa.getArvoreSintaticaAbstrata();

        StringWriter stringWriter = new StringWriter();

        try {

            GeradorCodigoPortugol gerador = new GeradorCodigoPortugol(asa, new PrintWriter(stringWriter));
            gerador.visitar(asa);

        } catch (ExcecaoVisitaASA ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return stringWriter.toString();
    }

    private static class GeradorCodigoPortugol extends VisitanteASABasico
    {

        private final PrintWriter saida;
        private final ASAPrograma asa;
        private int nivelEscopo = 1;

        public GeradorCodigoPortugol(ASAPrograma asa, PrintWriter saida)
        {
            this.saida = saida;
            this.asa = asa;
        }

        private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA
        {
            nivelEscopo++;

            //Utils.visitarBlocos(blocos, saida, this, nivelEscopo);
            nivelEscopo--;
        }

        public GeradorCodigoPortugol pulaLinha()
        {
            saida.println();
            return this;
        }

        @Override
        public Object visitar(NoDeclaracaoFuncao funcao) throws ExcecaoVisitaASA
        {
            
            pulaLinha();
            
            String identacao = Utils.geraIdentacao(nivelEscopo);
            
            saida.append(identacao);
            
            saida.append("funcao ");
            
            TipoDado tipoRetorno = funcao.getTipoDado();
            if (tipoRetorno != TipoDado.VAZIO) {
                saida.append(tipoRetorno.getNome());
            }
            
            saida.append(funcao.getNome());
            
            saida.append("("); // abre parênteses dos parâmetros
            
            saida.append(")").println(); // fecha parênteses dos parâmetros
            
            saida.append(identacao);
            saida.println("{"); // abertura do escopo da função
            
            if (funcao.getBlocos().isEmpty()) {
                saida.println(); // linha em branco quando a função é vazia
            }
            
            saida.append(identacao);
            saida.println("}"); // fechamento do escopo da função
            
            return null;
        }

        @Override
        public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
        {
            
            saida.println("programa");
            saida.println("{");
            
            for (NoInclusaoBiblioteca listaInclusoesBiblioteca : asap.getListaInclusoesBibliotecas()) {
                listaInclusoesBiblioteca.aceitar(this);
            }

            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais()) {
                declaracao.aceitar(this);
            }

            saida.print("}"); // fecha programa
            
            return null;
        }

        @Override
        public Void visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
        {
            saida.append(String.valueOf(noInteiro.getValor()));
            return null;
        }

        @Override
        public Void visitar(NoLogico noLogico) throws ExcecaoVisitaASA
        {
            String valor = noLogico.getValor() ? "true" : "false";
            saida.append(valor);
            return null;
        }

        @Override
        public Void visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
        {
            String valor = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.preservaCaracteresEspeciais(noCaracter.getValor().toString());
            valor = "'" + valor + "'";
            saida.append(valor);
            return null;
        }

        @Override
        public Void visitar(NoReal noReal) throws ExcecaoVisitaASA
        {
            String valor = String.valueOf(noReal.getValor());
            saida.append(valor);
            return null;
        }

        @Override
        public Void visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
        {
            String valor = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.preservaCaracteresEspeciais(noCadeia.getValor());
            valor = '\"' + valor + '\"';
            saida.append(valor);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseLeftShift no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseRightShift no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseE no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseXOR no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseOu no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoSoma no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoDivisao no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoModulo no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoSubtracao no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoMultiplicacao no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaOU no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaE no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaDiferenca no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaIgualdade no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMaior no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMaiorIgual no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMenor no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMenorIgual no) throws ExcecaoVisitaASA
        {
            //geradorOperacao.gera(no, saida, this);
            return null;
        }

        @Override
        public Boolean visitar(NoDeclaracaoVariavel noDeclaracao) throws ExcecaoVisitaASA
        {
            //boolean podeInicializar = !processandoVariaveisGlobais;
            //return geradorDeclaracaoVariavel.gera(noDeclaracao, saida, this, nivelEscopo, podeInicializar, inicializandoNoPara, indiceAtualInicializacaoPara);
            return true;
        }

        @Override
        public Void visitar(NoDeclaracaoVetor no) throws ExcecaoVisitaASA
        {
//            boolean podeInicializar = !processandoVariaveisGlobais;
//            geradorDeclaracaoVariavel.gera(no, saida, this, nivelEscopo, podeInicializar);
            return null;
        }

        @Override
        public Void visitar(NoVetor noVetor) throws ExcecaoVisitaASA
        {
            //geradorDeclaracaoVariavel.gera(noVetor, saida, this, nivelEscopo);
            return null;
        }

        @Override
        public Void visitar(NoDeclaracaoMatriz noDeclaracao) throws ExcecaoVisitaASA
        {
            //boolean podeInicializar = !processandoVariaveisGlobais;
            //geradorDeclaracaoVariavel.gera(noDeclaracao, saida, this, nivelEscopo, podeInicializar);
            return null;
        }

        @Override
        public Void visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
        {
            //geradorDeclaracaoVariavel.gera(noMatriz, saida, this, nivelEscopo);
            return null;
        }

        @Override
        public Void visitar(NoRetorne no) throws ExcecaoVisitaASA
        {
            NoExpressao expressao = no.getExpressao();
            if (expressao != null) {
                saida.append("return ");
                if (no.temPai()) {

                    if (no.getPai() instanceof NoDeclaracaoFuncao) {
                        TipoDado tipoRetornoFuncao = ((NoDeclaracaoFuncao) no.getPai()).getTipoDado();
                        if (expressao.getTipoResultante() == TipoDado.REAL && tipoRetornoFuncao == TipoDado.INTEIRO) {
                            saida.append("(int)");
                        }
                    }
                } else {
                    throw new IllegalStateException("retorne não tem pai!");
                }

                expressao.aceitar(this);
            } else {
                saida.append("return");
            }

            return null;
        }

        @Override
        public Void visitar(NoReferenciaVetor no) throws ExcecaoVisitaASA
        {
            saida.append(no.getNome());

            saida.append("[");
            no.getIndice().aceitar(this);
            saida.append("]");

            return null;
        }

        @Override
        public Void visitar(NoReferenciaMatriz no) throws ExcecaoVisitaASA
        {
            saida.append(no.getNome())
                    .append("[");

            no.getLinha().aceitar(this);

            saida.append("][");

            no.getColuna().aceitar(this);

            saida.append("]");

            return null;
        }

        @Override
        public Void visitar(NoReferenciaVariavel no) throws ExcecaoVisitaASA
        {
            String nome = no.getNome();
            String escopo = no.getEscopo();
            if (escopo != null) {
                escopo = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.getNomeBiblioteca(escopo, asa);
                saida.append(escopo).append(".");
            }

            NoDeclaracao declaracao = no.getOrigemDaReferencia();
            boolean ehParametroPorReferencia = declaracao instanceof NoDeclaracaoParametro && (((NoDeclaracaoParametro) declaracao).getModoAcesso() == ModoAcesso.POR_REFERENCIA);
            if (ehParametroPorReferencia || no.ehPassadoPorReferencia()) {
                String stringIndice = ehParametroPorReferencia ? no.getNome() : br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraStringIndice(no);
                String nomeTipo = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.getNomeTipoJava(declaracao.getTipoDado()).toUpperCase();
                saida.format("REFS_%s[%s]", nomeTipo, stringIndice);
            } else {
                saida.append(nome);
            }

            return null;
        }

        @Override
        public Void visitar(NoEnquanto no) throws ExcecaoVisitaASA
        {

            saida.append("while(");

            no.getCondicao().aceitar(this);

            saida.append(")").println();

            String identacao = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraIdentacao(nivelEscopo);

            saida.append(identacao).append("{").println();

            visitarBlocos(no.getBlocos());

            saida.println();

            saida.append(identacao).append("}").println();

            return null;
        }

        @Override
        public Void visitar(NoPara no) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Void visitar(NoSe no) throws ExcecaoVisitaASA
        {
            saida.append("if(");

            no.getCondicao().aceitar(this);

            saida.append(")").println();

            String identacao = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraIdentacao(nivelEscopo);

            saida.append(identacao).append("{").println();

            List<NoBloco> blocosVerdadeiros = no.getBlocosVerdadeiros();
            if (blocosVerdadeiros != null) {
                visitarBlocos(blocosVerdadeiros);
                saida.println();
            }

            saida.append(identacao).append("}").println();

            List<NoBloco> blocosFalsos = no.getBlocosFalsos();
            if (blocosFalsos != null) {
                saida.append(identacao).append("else").println();
                saida.append(identacao).append("{").println();

                visitarBlocos(blocosFalsos);

                saida.println();

                saida.append(identacao).append("}").println();
            }

            return null;
        }

        private boolean simularBreakCaso = false;

        @Override
        public Void visitar(NoEscolha no) throws ExcecaoVisitaASA
        {
            boolean contemCasosNaoConstantes = GeradorSwitchCase.contemCasosNaoConstantes(no);
            simularBreakCaso = contemCasosNaoConstantes;

            if (!contemCasosNaoConstantes) {
                //geradorSwitchCase.geraSwitchCase(no, saida, this, nivelEscopo, opcoes, seed);
            } else {
                //geradorSwitchCase.geraSeSenao(no, saida, this, nivelEscopo, opcoes);
            }
            simularBreakCaso = false;
            return null;
        }

        @Override
        public Void visitar(NoFacaEnquanto no) throws ExcecaoVisitaASA
        {
            String identacao = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraIdentacao(nivelEscopo);

            saida.append("do").println();
            saida.append(identacao).append("{").println();

            List<NoBloco> blocos = no.getBlocos();
            if (blocos != null) {
                visitarBlocos(blocos);
                saida.println();
            }

            saida.append(identacao).append("}").println();

            saida.append(identacao).append("while(");

            no.getCondicao().aceitar(this);

            saida.append(");").println();

            return null;
        }

        @Override
        public Void visitar(NoOperacaoAtribuicao no) throws ExcecaoVisitaASA
        {
            //geradorAtribuicao.gera(no, saida, this, nivelEscopo);
            return null;
        }

        @Override
        public Void visitar(NoMenosUnario no) throws ExcecaoVisitaASA
        {
            saida.append("-");
            no.getExpressao().aceitar(this);

            return null;
        }

        @Override
        public Void visitar(NoNao no) throws ExcecaoVisitaASA
        {
            saida.append("!");
            no.getExpressao().aceitar(this);

            return null;
        }

        @Override
        public Void visitar(NoChamadaFuncao no) throws ExcecaoVisitaASA
        {
            //geradorChamadaMetodo.gera(no, saida, this, asa, opcoes, nivelEscopo);
            return null;
        }

        @Override
        public Void visitar(NoPare noPare) throws ExcecaoVisitaASA
        {
            if (simularBreakCaso) {
                saida.append(GeradorSwitchCase.geraNomeVariavelBreak(nivelEscopo - 1))
                        .append(" = true");
            } else {
                saida.append("break");
            }

            return null;
        }

        @Override
        public Object visitar(NoBitwiseNao no) throws ExcecaoVisitaASA
        {
            saida.append("~");
            no.getExpressao().aceitar(this);

            return null;
        }

        @Override
        public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
        {
            saida.append("continue");

            return null;
        }

        public GeradorCodigoPortugol geraChaveAberturaClasse()
        {
            saida.append("{").println();

            saida.println();

            return this;
        }

        public GeradorCodigoPortugol geraChaveFechamentoClasse()
        {
            saida.append("}").println();

            return this;
        }

    }

}
