package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolBaseVisitor;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoInteiro;
import br.univali.portugol.nucleo.asa.NoOperacaoAtribuicao;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaDiferenca;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaIgualdade;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaior;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMaiorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenor;
import br.univali.portugol.nucleo.asa.NoOperacaoLogicaMenorIgual;
import br.univali.portugol.nucleo.asa.NoOperacaoSoma;
import br.univali.portugol.nucleo.asa.NoPara;
import br.univali.portugol.nucleo.asa.NoReal;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser.*;
import br.univali.portugol.nucleo.asa.NoOperacaoSubtracao;
import java.util.ArrayList;
import java.util.List;

public class GeradorASA {

    private final PortugolParser parser;

    public GeradorASA(PortugolParser parser) {
        this.parser = parser;
    }

    public ASA geraASA() {

        Antlr4ParserVisitor visitor = new Antlr4ParserVisitor();

        visitor.visitArquivo(parser.arquivo()); // invoca a primeira regra da gramática

        return visitor.getAsa();
    }

    private class Antlr4ParserVisitor extends PortugolBaseVisitor<NoBloco> {

        private final ASAPrograma asa = new ASAPrograma();

        public ASAPrograma getAsa() {
            return asa;
        }

        @Override
        public NoBloco visitDeclaracaoFuncao(PortugolParser.DeclaracaoFuncaoContext ctx) {

            String nomeFuncao = ctx.ID().getText();
            String nomeTipoRetorno = ctx.TIPO() != null ? ctx.TIPO().getText() : "vazio";
            TipoDado tipoRetorno = TipoDado.obterTipoDadoPeloNome(nomeTipoRetorno);

            NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao(nomeFuncao, tipoRetorno, Quantificador.VALOR);

            // percorre os comandos declarados dentro da função
            for (PortugolParser.ComandoContext comandoContext : ctx.comando()) {
                declaracaoFuncao.adicionaBloco(comandoContext.accept(this));
            }

            asa.adicionaDeclaracaoGlobal(declaracaoFuncao);

            return declaracaoFuncao;
        }

        // referência para variável
        @Override
        public NoBloco visitVariavel(PortugolParser.VariavelContext ctx) {
            String nomeVariavel = ctx.ID().getText();

            String escopo = null;
            PortugolParser.EscopoBibliotecaContext escopoBiblioteca = ctx.escopoBiblioteca();
            if (escopoBiblioteca != null) {
                escopo = escopoBiblioteca.ID().getText();
            }

            return new NoReferenciaVariavel(escopo, nomeVariavel);
        }

        @Override
        public NoBloco visitIncrementoUnarioPosfixado(IncrementoUnarioPosfixadoContext ctx) {
            // gerando a mesma estrutura do incremento prefixado
            String nomeVariavel = ctx.ID().getText();
            NoReferenciaVariavel referenciaVariavel = new NoReferenciaVariavel(null, nomeVariavel);
            return new NoOperacaoAtribuicao(referenciaVariavel, new NoOperacaoSoma(referenciaVariavel, new NoInteiro(1)));
        }

        @Override
        public NoBloco visitIncrementoUnarioPrefixado(IncrementoUnarioPrefixadoContext ctx) {
            String nomeVariavel = ctx.ID().getText();
            NoReferenciaVariavel referenciaVariavel = new NoReferenciaVariavel(null, nomeVariavel);
            return new NoOperacaoAtribuicao(referenciaVariavel, new NoOperacaoSoma(referenciaVariavel, new NoInteiro(1)));
        }

        @Override
        public NoBloco visitDecrementoUnarioPosfixado(DecrementoUnarioPosfixadoContext ctx) {
            // gerando a mesma estrutuar do decremento prefixado
            String nomeVariavel = ctx.ID().getText();
            NoReferenciaVariavel referenciaVariavel = new NoReferenciaVariavel(null, nomeVariavel);
            return new NoOperacaoAtribuicao(referenciaVariavel, new NoOperacaoSubtracao(referenciaVariavel, new NoInteiro(1)));
        }

        @Override
        public NoBloco visitDecrementoUnarioPrefixado(DecrementoUnarioPrefixadoContext ctx) {
            String nomeVariavel = ctx.ID().getText();
            NoReferenciaVariavel referenciaVariavel = new NoReferenciaVariavel(null, nomeVariavel);
            return new NoOperacaoAtribuicao(referenciaVariavel, new NoOperacaoSubtracao(referenciaVariavel, new NoInteiro(1)));
        }
        
        @Override
        public NoBloco visitAtribuicao(PortugolParser.AtribuicaoContext ctx) {
            GeradorNoOperacao<AtribuicaoContext, NoOperacaoAtribuicao> gerador = new GeradorNoOperacao<>(NoOperacaoAtribuicao.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitDeclaracaoVariavel(PortugolParser.DeclaracaoVariavelContext ctx) {
            TipoDado tipoVariavel = TipoDado.obterTipoDadoPeloNome(ctx.TIPO().getText());
            String nomeVariavel = ctx.ID().getText();
            NoDeclaracaoVariavel noDeclaracaoVariavel = new NoDeclaracaoVariavel(nomeVariavel, tipoVariavel);

            // a declaração da variável tem uma inicialização?
            if (ctx.expressao() != null) {
                NoExpressao inicializacao = (NoExpressao) ctx.expressao().accept(this);
                noDeclaracaoVariavel.setInicializacao(inicializacao);
            }

            return noDeclaracaoVariavel;
        }

        // Loop para (for)
        @Override
        public NoBloco visitPara(PortugolParser.ParaContext ctx) {
            return criaNoPara(ctx);
        }

        @Override
        public NoBloco visitChamadaFuncao(PortugolParser.ChamadaFuncaoContext ctx) {

            PortugolParser.EscopoBibliotecaContext escopoBiblioteca = ctx.escopoBiblioteca();

            String escopo = (escopoBiblioteca != null) ? escopoBiblioteca.getText() : null;
            String nomeFuncao = ctx.ID().getText();

            return new NoChamadaFuncao(escopo, nomeFuncao);
        }

        @Override
        public NoBloco visitNumeroInteiro(PortugolParser.NumeroInteiroContext ctx) {
            int valorInteiro = Integer.valueOf(ctx.INT().getText());

            return new NoInteiro(valorInteiro);
        }

        @Override
        public NoBloco visitNumeroReal(PortugolParser.NumeroRealContext ctx) {
            double valorDouble = Double.parseDouble(ctx.REAL().getText());
            return new NoReal(valorDouble);
        }

        @Override
        public NoBloco visitOperacaoMenor(PortugolParser.OperacaoMenorContext ctx) {
            GeradorNoOperacao<OperacaoMenorContext, NoOperacaoLogicaMenor> gerador = new GeradorNoOperacao<>(NoOperacaoLogicaMenor.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitOperacaoMenorIgual(PortugolParser.OperacaoMenorIgualContext ctx) {
            GeradorNoOperacao<OperacaoMenorIgualContext, NoOperacaoLogicaMenorIgual> gerador = new GeradorNoOperacao<>(NoOperacaoLogicaMenorIgual.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitOperacaoMaior(PortugolParser.OperacaoMaiorContext ctx) {
            GeradorNoOperacao<OperacaoMaiorContext, NoOperacaoLogicaMaior> gerador = new GeradorNoOperacao<>(NoOperacaoLogicaMaior.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitOperacaoMaiorIgual(PortugolParser.OperacaoMaiorIgualContext ctx) {
            GeradorNoOperacao<OperacaoMaiorIgualContext, NoOperacaoLogicaMaiorIgual> gerador = new GeradorNoOperacao<>(NoOperacaoLogicaMaiorIgual.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitOperacaoIgualdade(PortugolParser.OperacaoIgualdadeContext ctx) {
            GeradorNoOperacao<OperacaoIgualdadeContext, NoOperacaoLogicaIgualdade> gerador = new GeradorNoOperacao<>(NoOperacaoLogicaIgualdade.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitOperacaoDiferenca(PortugolParser.OperacaoDiferencaContext ctx) {
            GeradorNoOperacao<OperacaoDiferencaContext, NoOperacaoLogicaDiferenca> gerador = new GeradorNoOperacao<>(NoOperacaoLogicaDiferenca.class);
            return gerador.gera(ctx, this);
        }

        @Override
        public NoBloco visitAdicao(PortugolParser.AdicaoContext ctx) {
            GeradorNoOperacao<AdicaoContext, NoOperacaoSoma> gerador = new GeradorNoOperacao<>(NoOperacaoSoma.class);
            return gerador.gera(ctx, this);
        }
        
        private NoPara criaNoPara(PortugolParser.ParaContext contexto) {

            PortugolParser.InicializacaoParaContext inicializacaoPara = contexto.inicializacaoPara();
            PortugolParser.CondicaoContext condicao = contexto.condicao();
            PortugolParser.IncrementoParaContext incrementoPara = contexto.incrementoPara();

            NoPara noPara = new NoPara();

            if (inicializacaoPara != null) {
                // TODO tratar as outras inicializações, a gramática só está suportando uma inicialização
                List<NoBloco> inicializacoes = new ArrayList<>();
                inicializacoes.add(inicializacaoPara.accept(this));
                noPara.setInicializacoes(inicializacoes);
            }

            if (condicao != null) {
                noPara.setCondicao((NoExpressao) condicao.accept(this));
            }

            if (incrementoPara != null) {
                noPara.setIncremento((NoExpressao) incrementoPara.accept(this));
            }

            // percorre os comandos filhos do loop
            for (PortugolParser.ComandoContext comandoContext : contexto.comando()) {
                noPara.adicionaBloco(comandoContext.accept(this));
            }

            return noPara;
        }

    }

}
