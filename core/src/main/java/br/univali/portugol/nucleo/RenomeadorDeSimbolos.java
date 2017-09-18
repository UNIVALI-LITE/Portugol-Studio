package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.No;
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
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoRetorne;
import br.univali.portugol.nucleo.asa.NoSe;
import br.univali.portugol.nucleo.asa.NoTitulo;
import br.univali.portugol.nucleo.asa.NoVaPara;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 03/19/2016
 */
final class RenomeadorDeSimbolos
{
    /**
     * Este método renomeia a declaração e todas as referências de um símbolo no
     * código fonte.
     *
     * @param programa o programa escrito pelo usuário, no qual o símbolo deve
     * ser renomeado.
     *
     * @param linha a linha dentro do programa onde a referência ou declaração
     * do símbolo se encontra
     *
     * @param coluna a coluna dentro da linha do programa onde a referência ou
     * declaração do símbolo se encontra
     *
     * @param novoNome o novo nome que será atribuído ao símbolo
     *
     * @return uma nova versão do código fonte do programa com a declaração e
     * todas as as referências do símbolo atualizadas para o novo nome
     */
    public String renomearSimbolo(String programa, int linha, int coluna, String novoNome) throws ErroAoRenomearSimbolo
    {
        programa = removerInformacoesPortugolStudio(programa);

        try
        {
            Programa programaCompilado = Portugol.compilarParaAnalise(programa);
            BuscadorDeSimbolo buscadorDeSimbolo = new BuscadorDeSimbolo();

            buscadorDeSimbolo.buscarSimbolo(programaCompilado, linha, coluna);

            if (buscadorDeSimbolo.simboloEncontrado())
            {
                NoDeclaracao declaracao = buscadorDeSimbolo.getDeclaracaoSimbolo();
                List<Integer> posicoesOcorrencias = mapearPosicoesReferencias(programa, declaracao.getReferencias());

                posicoesOcorrencias.add(obterPosicaoAbsoluta(programa, declaracao.getTrechoCodigoFonteNome().getLinha(), declaracao.getTrechoCodigoFonteNome().getColuna()));

                String antigoNome = declaracao.getNome();
                StringBuilder builder = new StringBuilder();

                for (int posicao = 0; posicao < programa.length(); posicao++)
                {
                    if (posicoesOcorrencias.contains(posicao))
                    {
                        builder.append(novoNome);
                        posicao = posicao + antigoNome.length() - 1;

                        continue;
                    }

                    builder.append(programa.charAt(posicao));
                }

                return builder.toString();
            }
            else
            {
                throw new ErroAoRenomearSimbolo(String.format("Não foi encontrado nenhum símbolo para renomear na linha %d, coluna %d", linha, coluna));
            }
        }
        catch (ErroCompilacao ex)
        {
            throw new ErroAoRenomearSimbolo("Não é possível renomear um símbolo em um programa que contém erros");
        }
    }

    public NoDeclaracao obterDeclaracaoDoSimbolo(String programa, int linha, int coluna) throws ErroAoTentarObterDeclaracaoDoSimbolo
    {
        programa = removerInformacoesPortugolStudio(programa);

        try
        {
            Programa programaCompilado = Portugol.compilarParaAnalise(programa);
            BuscadorDeSimbolo buscadorDeSimbolo = new BuscadorDeSimbolo();

            buscadorDeSimbolo.buscarSimbolo(programaCompilado, linha, coluna);

            if (buscadorDeSimbolo.simboloEncontrado())
            {
                return buscadorDeSimbolo.getDeclaracaoSimbolo();
            }
            else
            {
                throw new ErroAoTentarObterDeclaracaoDoSimbolo(String.format("Não foi encontrado nenhum símbolo na linha %d, coluna %d", linha, coluna), CausaErroAoTentarObterDeclaracaoDoSimbolo.SIMBOLO_NAO_ENCONTRADO);
            }
        }
        catch (ErroAoRenomearSimbolo ex)
        {
            throw new ErroAoTentarObterDeclaracaoDoSimbolo(ex.getMensagem(), CausaErroAoTentarObterDeclaracaoDoSimbolo.OUTRA);
        }
        catch (ErroCompilacao ex)
        {
            throw new ErroAoTentarObterDeclaracaoDoSimbolo("Não foi possível encontrar o símbolo porque o programa contém erros", CausaErroAoTentarObterDeclaracaoDoSimbolo.PROGRAMA_CONTEM_ERROS);
        }
    }

    private List<Integer> mapearPosicoesReferencias(String programa, List<NoReferencia> referencias)
    {
        List<Integer> posicoes = new ArrayList<>();

        for (NoReferencia referencia : referencias)
        {
            TrechoCodigoFonte trecho = referencia.getTrechoCodigoFonteNome();
            posicoes.add(obterPosicaoAbsoluta(programa, trecho.getLinha(), trecho.getColuna()));
        }

        return posicoes;
    }

    private int obterPosicaoAbsoluta(String programa, int linha, int coluna)
    {
        int linhaAtual = 1;
        int posicao = 0;

        for (int caracter = 0; caracter < programa.length(); caracter++)
        {
            if (linhaAtual == linha)
            {
                return caracter + coluna;
            }

            if (programa.charAt(caracter) == '\n')
            {
                linhaAtual = linhaAtual + 1;
            }
        }

        return posicao;
    }

    private final class BuscadorDeSimbolo implements VisitanteASA
    {
        private ASAPrograma asa;

        private int linha;
        private int coluna;
        private NoDeclaracao declaracaoSimbolo = null;

        public void buscarSimbolo(Programa programa, int linha, int coluna) throws ErroAoRenomearSimbolo
        {
            try
            {
                this.linha = linha;
                this.coluna = coluna - 1;
                this.asa = programa.getArvoreSintaticaAbstrata();

                programa.getArvoreSintaticaAbstrata().aceitar(this);
            }
            catch (ExcecaoVisitaASA ex)
            {
                if (ex.getCause() instanceof ErroAoRenomearSimbolo)
                {
                    throw (ErroAoRenomearSimbolo) ex.getCause();
                }

                throw new ErroAoRenomearSimbolo(ex.getMessage());
            }
        }

        public boolean simboloEncontrado()
        {
            return declaracaoSimbolo != null;
        }

        public NoDeclaracao getDeclaracaoSimbolo()
        {
            return declaracaoSimbolo;
        }

        @Override
        public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
        {
            for (NoDeclaracao declaracao : asap.getListaDeclaracoesGlobais())
            {
                if (simboloEncontrado())
                {
                    break;
                }

                declaracao.aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = declaracaoFuncao.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                declaracaoSimbolo = declaracaoFuncao;

                return null;
            }

            if (declaracaoFuncao.getParametros() != null)
            {
                for (NoDeclaracaoParametro parametro : declaracaoFuncao.getParametros())
                {
                    parametro.aceitar(this);

                    if (simboloEncontrado())
                    {
                        return null;
                    }
                }
            }

            visitarBlocos(declaracaoFuncao.getBlocos());

            return null;
        }

        private void visitarBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return;
            }

            if (blocos != null)
            {
                for (NoBloco bloco : blocos)
                {
                    if (simboloEncontrado())
                    {
                        break;
                    }

                    bloco.aceitar(this);
                }
            }
        }

        @Override
        public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noDeclaracaoVariavel.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                declaracaoSimbolo = noDeclaracaoVariavel;
                
                return null;
            }
            
            if (noDeclaracaoVariavel.getInicializacao() != null)
            {
                noDeclaracaoVariavel.getInicializacao().aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            if (noCaso.getExpressao() != null)
            {
                noCaso.getExpressao().aceitar(this);
            }

            visitarBlocos(noCaso.getBlocos());

            return null;
        }

        @Override
        public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = chamadaFuncao.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                if (chamadaFuncao.getEscopo() == null)
                {
                    declaracaoSimbolo = chamadaFuncao.getOrigemDaReferencia();

                    return null;
                }
                else
                {
                    throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("A função \"%s\" não pode ser renomeada porque pertence a uma biblioteca", chamadaFuncao.getNome())), asa, chamadaFuncao);
                }
            }

            if (chamadaFuncao.getParametros() != null)
            {
                for (NoExpressao expressao : chamadaFuncao.getParametros())
                {
                    if (simboloEncontrado())
                    {
                        return null;
                    }

                    expressao.aceitar(this);
                }
            }

            return null;
        }

        @Override
        public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            noEnquanto.getCondicao().aceitar(this);

            if (simboloEncontrado())
            {
                return null;
            }

            visitarBlocos(noEnquanto.getBlocos());

            return null;
        }

        @Override
        public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            noEscolha.getExpressao().aceitar(this);

            for (NoCaso caso : noEscolha.getCasos())
            {
                if (simboloEncontrado())
                {
                    return null;
                }

                caso.aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            noFacaEnquanto.getCondicao().aceitar(this);

            if (simboloEncontrado())
            {
                return null;
            }

            visitarBlocos(noFacaEnquanto.getBlocos());

            return null;
        }

        @Override
        public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            noMenosUnario.getExpressao().aceitar(this);

            return null;
        }

        @Override
        public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            noNao.getExpressao().aceitar(this);

            return null;
        }

        private Object visitarOperacao(NoOperacao operacao) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            operacao.getOperandoEsquerdo().aceitar(this);

            if (simboloEncontrado())
            {
                return null;
            }

            operacao.getOperandoDireito().aceitar(this);

            return null;
        }

        @Override
        public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaIgualdade);
        }

        @Override
        public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaDiferenca);
        }

        @Override
        public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoAtribuicao);
        }

        @Override
        public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaE);
        }

        @Override
        public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaOU);
        }

        @Override
        public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaMaior);
        }

        @Override
        public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaMaiorIgual);
        }

        @Override
        public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaMenor);
        }

        @Override
        public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoLogicaMenorIgual);
        }

        @Override
        public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoSoma);
        }

        @Override
        public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoSubtracao);
        }

        @Override
        public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoDivisao);
        }

        @Override
        public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoMultiplicacao);
        }

        @Override
        public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoModulo);
        }

        @Override
        public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoBitwiseLeftShift);
        }

        @Override
        public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoBitwiseRightShift);
        }

        @Override
        public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoBitwiseE);
        }

        @Override
        public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoBitwiseOu);
        }

        @Override
        public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
        {
            return visitarOperacao(noOperacaoBitwiseXOR);
        }

        @Override
        public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
        {
            noOperacaoBitwiseNao.getExpressao().aceitar(this);

            return null;
        }

        @Override
        public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            if (noPara.getInicializacoes() != null)
            {
                for (NoBloco inicializacao : noPara.getInicializacoes())
                {
                    inicializacao.aceitar(this);
                }
            }

            if (simboloEncontrado())
            {
                return null;
            }

            if (noPara.getCondicao() != null)
            {
                noPara.getCondicao().aceitar(this);
            }

            if (simboloEncontrado())
            {
                return null;
            }

            if (noPara.getIncremento() != null)
            {
                noPara.getIncremento().aceitar(this);
            }

            visitarBlocos(noPara.getBlocos());

            return null;
        }

        @Override
        public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noReferenciaMatriz.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                if (noReferenciaMatriz.getEscopo() == null)
                {
                    declaracaoSimbolo = noReferenciaMatriz.getOrigemDaReferencia();

                    return null;
                }
                else
                {
                    throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("A matriz \"%s\" não pode ser renomeada porque pertence a uma biblioteca", noReferenciaMatriz.getNome())), asa, noReferenciaMatriz);
                }
            }

            if (noReferenciaMatriz.getLinha() != null)
            {
                noReferenciaMatriz.getLinha().aceitar(this);
            }

            if (simboloEncontrado())
            {
                return null;
            }

            if (noReferenciaMatriz.getColuna() != null)
            {
                noReferenciaMatriz.getColuna().aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noReferenciaVetor.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                if (noReferenciaVetor.getEscopo() == null)
                {
                    declaracaoSimbolo = noReferenciaVetor.getOrigemDaReferencia();

                    return null;
                }
                else
                {
                    throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("O vetor \"%s\" não pode ser renomeado porque pertence a uma biblioteca", noReferenciaVetor.getNome())), asa, noReferenciaVetor);
                }
            }

            if (noReferenciaVetor.getIndice() != null)
            {
                noReferenciaVetor.getIndice().aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            if (noRetorne.getExpressao() != null)
            {
                noRetorne.getExpressao().aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            noSe.getCondicao().aceitar(this);

            if (simboloEncontrado())
            {
                return null;
            }

            visitarBlocos(noSe.getBlocosVerdadeiros());

            if (simboloEncontrado())
            {
                return null;
            }

            visitarBlocos(noSe.getBlocosFalsos());

            return null;
        }

        @Override
        public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            if (noMatriz.getValores() != null)
            {
                for (List<Object> linhaMatriz : noMatriz.getValores())
                {
                    for (Object objeto : linhaMatriz)
                    {
                        if (objeto instanceof No)
                        {
                            ((No) objeto).aceitar(this);

                            if (simboloEncontrado())
                            {
                                return null;
                            }

                        }
                    }
                }
            }

            return null;
        }

        @Override
        public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            if (noVetor.getValores() != null)
            {
                for (Object object : noVetor.getValores())
                {
                    if (object instanceof No)
                    {
                        ((No) object).aceitar(this);

                        if (simboloEncontrado())
                        {
                            return null;
                        }
                    }
                }
            }

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noDeclaracaoMatriz.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                declaracaoSimbolo = noDeclaracaoMatriz;

                return null;
            }

            if (noDeclaracaoMatriz.getNumeroLinhas() != null)
            {
                noDeclaracaoMatriz.getNumeroLinhas().aceitar(this);
            }

            if (simboloEncontrado())
            {
                return null;
            }

            if (noDeclaracaoMatriz.getNumeroColunas() != null)
            {
                noDeclaracaoMatriz.getNumeroColunas().aceitar(this);
            }

            if (simboloEncontrado())
            {
                return null;
            }

            if (noDeclaracaoMatriz.getInicializacao() != null)
            {
                noDeclaracaoMatriz.getInicializacao().aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noDeclaracaoVetor.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                declaracaoSimbolo = noDeclaracaoVetor;

                return null;
            }

            if (noDeclaracaoVetor.getTamanho() != null)
            {
                noDeclaracaoVetor.getTamanho().aceitar(this);
            }

            if (simboloEncontrado())
            {
                return null;
            }

            if (noDeclaracaoVetor.getInicializacao() != null)
            {
                noDeclaracaoVetor.getInicializacao().aceitar(this);
            }

            return null;
        }

        @Override
        public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noReferenciaVariavel.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                if (noReferenciaVariavel.getEscopo() == null)
                {
                    declaracaoSimbolo = noReferenciaVariavel.getOrigemDaReferencia();

                    return null;
                }
                else
                {
                    throw new ExcecaoVisitaASA(new ErroAoRenomearSimbolo(String.format("A constante \"%s\" não pode ser renomeada porque pertence a uma biblioteca", noReferenciaVariavel.getNome())), asa, noReferenciaVariavel);
                }
            }

            return null;
        }

        @Override
        public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
        {
            if (simboloEncontrado())
            {
                return null;
            }

            TrechoCodigoFonte trecho = noDeclaracaoParametro.getTrechoCodigoFonteNome();

            if (trecho.getLinha() == linha && trecho.getColuna() == coluna)
            {
                declaracaoSimbolo = noDeclaracaoParametro;
            }

            return null;
        }

        @Override
        public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.            
        }

        @Override
        public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
        {
            return null;
        }

        @Override
        public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
        {
            return null;
        }
    }

    private String removerInformacoesPortugolStudio(String codigoFonte)
    {
        codigoFonte = codigoFonte.replaceAll(System.lineSeparator(), Portugol.QUEBRA_DE_LINHA);
        
        int inicio = codigoFonte.lastIndexOf("/* $$$ Portugol Studio $$$");

        if (inicio >= 0)
        {
            // Quando as informações do Portugol Studio são inseridas no arquivo, é adicionada uma quebra de linha
            // antes do bloco de informações. Ao carregar o arquivo é necessário remover esta quebra para evitar
            // que o arquivo cresça indefinidamente a cada salvamento. Esta remoção é feita retrocedendo 1 caracter,
            // que corresponde ao '\n'

            inicio = inicio - 1;
            StringBuilder sb = new StringBuilder(codigoFonte);

            sb.delete(inicio, codigoFonte.length());
            codigoFonte = sb.toString();
        }

        // Remove a tag de cursor que foi incluída nas versões anteriores do Portugol Studio
        codigoFonte = codigoFonte.replace("/*${cursor}*/", "");

        return codigoFonte;
    }
}
