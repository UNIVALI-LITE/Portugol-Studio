package br.univali.ps.ui.editor.formatador;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.NoBitwiseNao;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoCaso;
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
import br.univali.portugol.nucleo.asa.NoExpressaoLiteral;
import br.univali.portugol.nucleo.asa.NoFacaEnquanto;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoMenosUnario;
import br.univali.portugol.nucleo.asa.NoNao;
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
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoPare;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.execucao.gerador.helpers.Utils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatadorCodigo
{

    private static final Logger LOGGER = Logger.getLogger(FormatadorCodigo.class.getName());

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

        private boolean declarandoVariaveisGlobais = false;

        private static final Map<Class, String> OPERADORES = new HashMap<>();

        public GeradorCodigoPortugol(ASAPrograma asa, PrintWriter saida)
        {
            this.saida = saida;
            this.asa = asa;
        }

        private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA
        {
            nivelEscopo++;

            for (NoBloco bloco : blocos) {
                saida.append(Utils.geraIdentacao(nivelEscopo));
                bloco.aceitar(this);
                pulaLinha();
            }

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
            declarandoVariaveisGlobais = false;

            pulaLinha();

            String identacao = Utils.geraIdentacao(nivelEscopo);

            saida.append(identacao);

            saida.append("funcao ");

            TipoDado tipoRetorno = funcao.getTipoDado();
            if (tipoRetorno != TipoDado.VAZIO) {
                saida.format("%s ", tipoRetorno.getNome());
            }

            saida.append(funcao.getNome());

            saida.append("("); // abre parênteses dos parâmetros

            List<NoDeclaracaoParametro> parametros = funcao.getParametros();
            for (int i = 0; i < parametros.size(); i++) {
                NoDeclaracaoParametro parametro = parametros.get(i);
                saida.format("%s ", parametro.getTipoDado().getNome());

                if (parametro.getModoAcesso() == ModoAcesso.POR_REFERENCIA) {
                    saida.append("&");
                }

                saida.append(parametro.getNome());

                // parâmetro é vetor ou matriz?
                saida.append(geraCodigoQuantificador(parametro.getQuantificador()));

                if (i < parametros.size() - 1) {
                    saida.append(", ");
                }
            }

            saida.append(")").println(); // fecha parênteses dos parâmetros

            saida.append(identacao);
            saida.println("{"); // abertura do escopo da função

            if (!funcao.getBlocos().isEmpty()) {
                visitarBlocos(funcao.getBlocos());
            } else {
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

            for (NoInclusaoBiblioteca biblioteca : asap.getListaInclusoesBibliotecas()) {
                biblioteca.aceitar(this);
            }

            declarandoVariaveisGlobais = true; // vira false quando encontra a primeira função (inicio)

            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais()) {
                declaracao.aceitar(this);
            }

            saida.print("}"); // fecha programa

            return null;
        }

        @Override
        public Object visitar(NoInclusaoBiblioteca no) throws ExcecaoVisitaASA
        {
            saida.append(Utils.geraIdentacao(1)); // primeiro nível de escopo

            saida.format("inclua biblioteca %s", no.getNome());

            String alias = no.getAlias();
            if (alias != null && !alias.isEmpty()) {
                saida.format(" --> %s", alias);
            }

            pulaLinha();

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
            String valor = noLogico.getValor() ? "verdadeiro" : "falso";
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
            // usando DecimalFormat para evitar a notação científica nos numeros reais: https://stackoverflow.com/questions/16098046/how-do-i-print-a-double-value-without-scientific-notation-using-java/16098306#16098306

            DecimalFormat formato = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            formato.setMaximumFractionDigits(8);
            formato.setMinimumFractionDigits(1);
            saida.append(formato.format(noReal.getValor()));
            
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

        private void visita(NoOperacao no) throws ExcecaoVisitaASA
        {
            if (no.estaEntreParenteses()) {
                saida.append("(");
            }

            assert (no.getOperandoEsquerdo() != null);
            no.getOperandoEsquerdo().aceitar(this);

            String operador = OPERADORES.get(no.getClass());
            saida.format(" %s ", operador);

            assert (no.getOperandoDireito() != null);
            no.getOperandoDireito().aceitar(this);

            if (no.estaEntreParenteses()) {
                saida.append(")");
            }
        }

        @Override
        public Void visitar(NoOperacaoBitwiseLeftShift no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseRightShift no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseE no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseXOR no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoBitwiseOu no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoSoma no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoDivisao no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoModulo no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoSubtracao no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoMultiplicacao no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaOU no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaE no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaDiferenca no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaIgualdade no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMaior no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMaiorIgual no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMenor no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Void visitar(NoOperacaoLogicaMenorIgual no) throws ExcecaoVisitaASA
        {
            visita(no);
            return null;
        }

        @Override
        public Boolean visitar(NoDeclaracaoVariavel no) throws ExcecaoVisitaASA
        {
            if (declarandoVariaveisGlobais) {
                saida.append(Utils.geraIdentacao(nivelEscopo));
            }

            if (no.constante()) {
                saida.append("const ");
            }

            saida.format("%s %s", no.getTipoDado().getNome(), no.getNome());

            if (no.temInicializacao()) {
                saida.append(" = ");
                no.getInicializacao().aceitar(this);
            }

            if (declarandoVariaveisGlobais) {
                pulaLinha(); // separa as declarações globais uma por linha
            }

            return true;
        }

        @Override
        public Void visitar(NoDeclaracaoVetor no) throws ExcecaoVisitaASA
        {
            if (declarandoVariaveisGlobais) {
                saida.append(Utils.geraIdentacao(nivelEscopo));
            }
            
            saida.format("%s %s", no.getTipoDado().getNome(), no.getNome());

            saida.append("[");
            if (no.getTamanho() != null) {
                no.getTamanho().aceitar(this);
            }
            saida.append("]");

            if (no.temInicializacao()) {
                saida.append(" = ");
                no.getInicializacao().aceitar(this);
            }

            if (declarandoVariaveisGlobais) {
                pulaLinha(); // separa as declarações globais uma por linha
            }
            
            return null;
        }

        @Override
        public Void visitar(NoVetor noVetor) throws ExcecaoVisitaASA
        {
            saida.append("{");

            List<Object> valores = noVetor.getValores();
            int totalValores = valores.size();

            for (int i = 0; i < totalValores; i++) {
                Object valor = valores.get(i);
                if (valor instanceof NoExpressaoLiteral) {
                    saida.append(valor.toString());
                } else {
                    ((NoExpressao) valor).aceitar(this);
                }

                if (i < totalValores - 1) {
                    saida.append(", ");
                }
            }

            saida.append("}");

            return null;
        }

        @Override
        public Void visitar(NoDeclaracaoMatriz no) throws ExcecaoVisitaASA
        {
            if (declarandoVariaveisGlobais) {
                saida.append(Utils.geraIdentacao(nivelEscopo));
            }
            
            saida.format("%s %s", no.getTipoDado().getNome(), no.getNome());

            // linhas
            saida.append("[");
            if (no.getNumeroLinhas() != null) {
                no.getNumeroLinhas().aceitar(this);
            }
            saida.append("]");

            // colunas
            saida.append("[");
            if (no.getNumeroColunas() != null) {
                no.getNumeroColunas().aceitar(this);
            }
            saida.append("]");

            if (no.temInicializacao()) {
                saida.append(" = ");
                no.getInicializacao().aceitar(this);
            }
            
            if (declarandoVariaveisGlobais) {
                pulaLinha(); // separa as declarações globais uma por linha
            }

            return null;
        }

        @Override
        public Void visitar(NoMatriz no) throws ExcecaoVisitaASA
        {
            
            saida.append("{");

            List<List<Object>> valores = no.getValores();
            int totalLinhas = valores.size();

            pulaLinha();
            
            for (int i = 0; i < totalLinhas; i++) {

                List<Object> linha = valores.get(i);

                int totalColunas = linha.size();

                saida.append(Utils.geraIdentacao(nivelEscopo + 1));
                saida.append("{");

                for (int j = 0; j < totalColunas; j++) {

                    saida.append(linha.get(j).toString());

                    if (j < totalColunas - 1) {
                        saida.append(", ");
                    }
                }
                saida.append("}");

                if (i < totalLinhas - 1) {
                    saida.append(",");
                    pulaLinha();
                }
            }

            pulaLinha();
            
            saida.append(Utils.geraIdentacao(nivelEscopo));
            saida.append("}");

            return null;
        }

        @Override
        public Void visitar(NoRetorne no) throws ExcecaoVisitaASA
        {
            NoExpressao expressao = no.getExpressao();
            if (expressao != null) {
                saida.append("retorne ");
                expressao.aceitar(this);
            } else {
                saida.append("retorne");
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

            saida.append(nome);

            return null;
        }

        @Override
        public Void visitar(NoEnquanto no) throws ExcecaoVisitaASA
        {

            saida.append("enquanto (");

            no.getCondicao().aceitar(this);

            saida.append(") {").println();

            String identacao = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraIdentacao(nivelEscopo);

            visitarBlocos(no.getBlocos());

            saida.append(identacao).append("}");

            return null;
        }

        @Override
        public Void visitar(NoPara no) throws ExcecaoVisitaASA
        {
            saida.append("para (");

            if (no.getInicializacoes() != null && !no.getInicializacoes().isEmpty()) {

                for (int i = 0; i < no.getInicializacoes().size(); i++) {

                    NoBloco inicializacao = no.getInicializacoes().get(i);

                    if (inicializacao != null) {// && !(inicializacao instanceof NoReferenciaVariavel)) {
                        inicializacao.aceitar(this);

                        if (i < no.getInicializacoes().size() - 1) {
                            saida.append(", ");
                        }
                    }
                }

            }

            saida.append("; "); // separador depois da inicialização do para 

            no.getCondicao().aceitar(this);

            saida.append("; "); // separador depois da condição

            if (no.getIncremento() != null) {
                no.getIncremento().aceitar(this);
            }

            saida.append(") {").println(); // fecha o parênteses do for

            String identacao = Utils.geraIdentacao(nivelEscopo);

            visitarBlocos(no.getBlocos());

            saida.append(identacao).append("}");

            return null;
        }

        @Override
        public Void visitar(NoSe no) throws ExcecaoVisitaASA
        {
            saida.append("se (");

            no.getCondicao().aceitar(this);

            saida.append(") {");

            pulaLinha();

            String identacao = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraIdentacao(nivelEscopo);

            List<NoBloco> blocosVerdadeiros = no.getBlocosVerdadeiros();
            if (blocosVerdadeiros != null) {
                visitarBlocos(blocosVerdadeiros);
            }

            saida.append(identacao).append("}");

            List<NoBloco> blocosFalsos = no.getBlocosFalsos();
            if (blocosFalsos != null) {
                pulaLinha();
                saida.append(identacao)
                        .append("senao {");

                pulaLinha();

                visitarBlocos(blocosFalsos);

                saida.append(identacao).append("}");
            }

            return null;
        }

        @Override
        public Void visitar(NoEscolha no) throws ExcecaoVisitaASA
        {
            saida.append("escolha (");

            no.getExpressao().aceitar(this);

            saida.append(") {").println();

            String identacaoExterna = Utils.geraIdentacao(nivelEscopo);

            List<NoCaso> casos = no.getCasos();
            if (casos != null) {

                nivelEscopo++; // coloca os "caso" aninhados no escolha

                String identacaoInterna = Utils.geraIdentacao(nivelEscopo);

                for (NoCaso caso : casos) {
                    NoExpressao expressaoCaso = caso.getExpressao();
                    if (expressaoCaso != null) {
                        saida.append(identacaoInterna).append("caso ");

                        expressaoCaso.aceitar(this);

                        saida.append(":").println();
                    } else {
                        saida.append(identacaoInterna)
                                .append("caso contrario:");
                        pulaLinha();
                    }

                    visitarBlocos(caso.getBlocos());
                }

                nivelEscopo--;
            }

            saida.append(identacaoExterna).append("}");

            return null;
        }

        @Override
        public Void visitar(NoFacaEnquanto no) throws ExcecaoVisitaASA
        {
            String identacao = br.univali.portugol.nucleo.execucao.gerador.helpers.Utils.geraIdentacao(nivelEscopo);

            saida.append("faca {").println();

            List<NoBloco> blocos = no.getBlocos();
            if (blocos != null) {
                visitarBlocos(blocos);
            }

            saida.append(identacao).append("}").println();

            saida.append(identacao).append("enquanto (");

            no.getCondicao().aceitar(this);

            saida.append(")");

            return null;
        }

        @Override
        public Void visitar(NoOperacaoAtribuicao no) throws ExcecaoVisitaASA
        {
            visita(no);
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
            saida.append("nao ");
            no.getExpressao().aceitar(this);

            return null;
        }

        @Override
        public Void visitar(NoChamadaFuncao no) throws ExcecaoVisitaASA
        {
            String escopoFuncao = (no.getEscopo() != null) ? (no.getEscopo() + ".") : "";
            String nomeFuncao = no.getNome();

            List<NoExpressao> parametrosPassados = no.getParametros();

            saida.format("%s%s(", escopoFuncao, nomeFuncao);

            int totalParametros = parametrosPassados.size();
            for (int i = 0; i < totalParametros; i++) {
                parametrosPassados.get(i).aceitar(this);

                if (i < totalParametros - 1) {
                    saida.append(", ");
                }
            }
            saida.append(")");
            return null;
        }

        @Override
        public Void visitar(NoPare noPare) throws ExcecaoVisitaASA
        {
            saida.append("pare");

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

        private static String geraCodigoQuantificador(Quantificador quantificador)
        {
            if (quantificador == Quantificador.MATRIZ) {
                return "[][]";
            } else if (quantificador == Quantificador.VETOR) {
                return "[]";
            }

            return ""; // quantificador == Quantificador.VALOR
        }

        static {
            OPERADORES.put(NoOperacaoAtribuicao.class, "=");
            OPERADORES.put(NoOperacaoDivisao.class, "/");
            OPERADORES.put(NoOperacaoModulo.class, "%");
            OPERADORES.put(NoOperacaoMultiplicacao.class, "*");
            OPERADORES.put(NoOperacaoSoma.class, "+");
            OPERADORES.put(NoOperacaoSubtracao.class, "-");

            OPERADORES.put(NoMenosUnario.class, "-");

            OPERADORES.put(NoOperacaoLogicaDiferenca.class, "!=");
            OPERADORES.put(NoOperacaoLogicaIgualdade.class, "==");
            OPERADORES.put(NoOperacaoLogicaMaior.class, ">");
            OPERADORES.put(NoOperacaoLogicaMaiorIgual.class, ">=");
            OPERADORES.put(NoOperacaoLogicaMenor.class, "<");
            OPERADORES.put(NoOperacaoLogicaMenorIgual.class, "<=");
            OPERADORES.put(NoOperacaoLogicaOU.class, "ou");
            OPERADORES.put(NoOperacaoLogicaE.class, "e");

            OPERADORES.put(NoOperacaoBitwiseE.class, "&");
            OPERADORES.put(NoOperacaoBitwiseOu.class, "|");
            OPERADORES.put(NoOperacaoBitwiseXOR.class, "^");
            OPERADORES.put(NoOperacaoBitwiseLeftShift.class, "<<");
            OPERADORES.put(NoOperacaoBitwiseRightShift.class, ">>");
        }

    }

}
