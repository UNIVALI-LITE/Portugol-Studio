package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolBaseVisitor;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolLexer;
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
import br.univali.portugol.nucleo.asa.ModoAcesso;
import br.univali.portugol.nucleo.asa.No;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoParametro;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoInclusaoBiblioteca;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoOperacaoDivisao;
import br.univali.portugol.nucleo.asa.NoOperacaoModulo;
import br.univali.portugol.nucleo.asa.NoOperacaoMultiplicacao;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.NoVetor;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

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

    private class Antlr4ParserVisitor extends PortugolBaseVisitor<No> {

        private final ASAPrograma asa = new ASAPrograma();

        public ASAPrograma getAsa() {
            return asa;
        }

        @Override
        public No visitArquivo(ArquivoContext ctx) {
            
            List<NoInclusaoBiblioteca> inclusoes = new ArrayList<>();
            for (InclusaoBibliotecaContext inclusaoBibliotecaContext : ctx.inclusaoBiblioteca()) {
                inclusoes.add((NoInclusaoBiblioteca)inclusaoBibliotecaContext.accept(this));
            }
            asa.setListaInclusoesBibliotecas(inclusoes);   

            List<ParserRuleContext> declaracoesGlobais = new ArrayList<>();
            
            declaracoesGlobais.addAll(ctx.declaracaoVariavel());
            declaracoesGlobais.addAll(ctx.declaracaoArray());
            declaracoesGlobais.addAll(ctx.declaracaoMatriz());
            declaracoesGlobais.addAll(ctx.declaracaoFuncao());
            
            for (ParserRuleContext declaracao : declaracoesGlobais) {
                asa.adicionaDeclaracaoGlobal((NoDeclaracao)declaracao.accept(this));
            }
            
            return null;
        }

        @Override
        public No visitDeclaracaoFuncao(PortugolParser.DeclaracaoFuncaoContext ctx) {

            String nomeFuncao = ctx.ID().getText();
            String nomeTipoRetorno = ctx.TIPO() != null ? ctx.TIPO().getText() : "vazio";
            TipoDado tipoRetorno = TipoDado.obterTipoDadoPeloNome(nomeTipoRetorno);

            NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao(nomeFuncao, tipoRetorno, Quantificador.VALOR);

            if (ctx.listaParametros() != null) { // se a função tem parâmetros
                List<NoDeclaracaoParametro> parametros = new ArrayList<>();
                for (ParametroContext parametroContext : ctx.listaParametros().parametro()) {
                    TipoDado tipo = TipoDado.obterTipoDadoPeloNome(parametroContext.TIPO().getText());
                    String nome = parametroContext.ID().getText();
                    ModoAcesso modoAcesso = (parametroContext.PARAMETRO_POR_REFERENCIA() == null) ? ModoAcesso.POR_VALOR : ModoAcesso.POR_REFERENCIA;
                    Quantificador quantificador = Quantificador.VALOR;
                    if (parametroContext.parametroArray() != null) {
                        quantificador = Quantificador.VETOR;
                    }
                    else if (parametroContext.parametroMatriz() != null) {
                        quantificador = Quantificador.MATRIZ;
                    }

                    parametros.add(new NoDeclaracaoParametro(nome, tipo, quantificador, modoAcesso));
                }
                declaracaoFuncao.setParametros(parametros);
            }
                        
            // percorre os comandos declarados dentro da função
            for (PortugolParser.ComandoContext comandoContext : ctx.comando()) {
                declaracaoFuncao.adicionaBloco((NoBloco)comandoContext.accept(this));
            }

            return declaracaoFuncao;
        }

        // referência para variável
        @Override
        public No visitVariavel(PortugolParser.VariavelContext ctx) {
            String nomeVariavel = ctx.ID().getText();

            String escopo = null;
            PortugolParser.EscopoBibliotecaContext escopoBiblioteca = ctx.escopoBiblioteca();
            if (escopoBiblioteca != null) {
                escopo = escopoBiblioteca.ID().getText();
            }

            return new NoReferenciaVariavel(escopo, nomeVariavel);
        }

        @Override
        public No visitIncrementoUnarioPosfixado(IncrementoUnarioPosfixadoContext ctx) {
            // gerando a mesma estrutura do incremento prefixado
            String nomeVariavel = ctx.ID().getText();
            return GeradorNoOperacao.geraIncrementoUnario(nomeVariavel);
        }

        @Override
        public No visitIncrementoUnarioPrefixado(IncrementoUnarioPrefixadoContext ctx) {
            String nomeVariavel = ctx.ID().getText();
            return GeradorNoOperacao.geraIncrementoUnario(nomeVariavel);
        }

        @Override
        public No visitDecrementoUnarioPosfixado(DecrementoUnarioPosfixadoContext ctx) {
            // gerando a mesma estrutuar do decremento prefixado
            String nomeVariavel = ctx.ID().getText();
            return GeradorNoOperacao.geraDecrementoUnario(nomeVariavel);
        }

        @Override
        public No visitDecrementoUnarioPrefixado(DecrementoUnarioPrefixadoContext ctx) {
            String nomeVariavel = ctx.ID().getText();
            return GeradorNoOperacao.geraDecrementoUnario(nomeVariavel);
        }
        
        @Override
        public No visitAtribuicao(PortugolParser.AtribuicaoContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoAtribuicao.class);
        }

        @Override
        public No visitDeclaracaoVariavel(PortugolParser.DeclaracaoVariavelContext ctx) {
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

        @Override
        public No visitInclusaoBiblioteca(InclusaoBibliotecaContext ctx) {
            String nome = ctx.ID(0).getText();
            
            String alias = null;
            if (ctx.ID(1) != null) {
                alias = ctx.ID(1).getText();
            }
            
            return new NoInclusaoBiblioteca(nome, alias);
        }
        
        @Override
        public No visitTamanhoArray(TamanhoArrayContext ctx) {
            return new NoInteiro(Integer.parseInt(ctx.INT().getText()));
        }

        @Override
        public No visitDeclaracaoMatriz(DeclaracaoMatrizContext ctx) {
            TipoDado tipo = TipoDado.obterTipoDadoPeloNome(ctx.TIPO().getText());
            String nome = ctx.ID().getText();
            
            NoExpressao expressaoLinhas = null;
            if (ctx.tamanhoArray(0) != null) {
                 expressaoLinhas = (NoExpressao) ctx.tamanhoArray(0).accept(this);
            }
            
            NoExpressao expressaoColunas = null;
            if (ctx.tamanhoArray(1) != null) {
                 expressaoColunas = (NoExpressao) ctx.tamanhoArray(1).accept(this);
            }
            
            NoDeclaracaoMatriz matriz = new NoDeclaracaoMatriz(nome, tipo, expressaoLinhas, expressaoColunas, false);
            
            InicializacaoMatrizContext inicializacao = ctx.inicializacaoMatriz();
            if (inicializacao != null) {
                List<List<Object>> linhas = new ArrayList<>();
                for (InicializacaoArrayContext inicializacaoArrayContext : inicializacao.inicializacaoArray()) {
                    List<Object> linha = new ArrayList<>();
                    linhas.add(linha);
                    for (ExpressaoContext expressao : inicializacaoArrayContext.listaExpressoes().expressao()) {
                        linha.add(expressao.accept(this));
                    }
                }
                matriz.setInicializacao(new NoMatriz(linhas));
            }
            
            return matriz; 
        }
        
        @Override
        public No visitDeclaracaoArray(DeclaracaoArrayContext ctx) {
            TipoDado tipo = TipoDado.obterTipoDadoPeloNome(ctx.TIPO().getText());
            String nome = ctx.ID().getText();
            
            NoExpressao tamanho = null;
            if (ctx.tamanhoArray() != null) {
                 tamanho = (NoExpressao) ctx.tamanhoArray().accept(this);
            }
            
            NoDeclaracaoVetor vetor = new NoDeclaracaoVetor(nome, tipo, tamanho, false);
            
            InicializacaoArrayContext inicializacao = ctx.inicializacaoArray();
            if (inicializacao != null) {
                List<Object> valores = new ArrayList<>();
                for (ExpressaoContext expressao : inicializacao.listaExpressoes().expressao()) {
                    valores.add(expressao.accept(this));
                }
                vetor.setInicializacao(new NoVetor(valores));
            }
            
            return vetor; 
        }

        // Loop para (for)
        @Override
        public No visitPara(PortugolParser.ParaContext ctx) {
            return criaNoPara(ctx);
        }

        @Override
        public No visitChamadaFuncao(PortugolParser.ChamadaFuncaoContext ctx) {

            PortugolParser.EscopoBibliotecaContext escopoBiblioteca = ctx.escopoBiblioteca();

            String escopo = (escopoBiblioteca != null) ? escopoBiblioteca.getText() : null;
            String nomeFuncao = ctx.ID().getText();

            NoChamadaFuncao noChamadaFuncao = new NoChamadaFuncao(escopo, nomeFuncao);
            
            if (ctx.listaExpressoes() != null) { // se existem parâmetros sendo passados para a função
                List<NoExpressao> parametros = new ArrayList<>();
                for (ExpressaoContext expressaoContext : ctx.listaExpressoes().expressao()) {
                    parametros.add((NoExpressao)expressaoContext.accept(this));
                }
                noChamadaFuncao.setParametros(parametros);
            }
            
            return noChamadaFuncao;
        }

        @Override
        public No visitReferenciaArray(ReferenciaArrayContext ctx) {
            String escopo = null;
            if (ctx.escopoBiblioteca() != null) {
                escopo = ctx.escopoBiblioteca().getText();
            }
            
            String nomeArray = ctx.ID().getText();

            return new NoReferenciaVetor(escopo, nomeArray, (NoExpressao)ctx.expressao().accept(this));
        }
        
         @Override
        public No visitReferenciaMatriz(ReferenciaMatrizContext ctx) {
            String escopo = null;
            if (ctx.escopoBiblioteca() != null) {
                escopo = ctx.escopoBiblioteca().getText();
            }
            
            String nomeMatriz = ctx.ID().getText();

            NoExpressao expressaoLinha = (NoExpressao)ctx.expressao(0).accept(this);
            NoExpressao expressaoColuna = (NoExpressao)ctx.expressao(1).accept(this);
            return new NoReferenciaMatriz(escopo, nomeMatriz, expressaoLinha, expressaoColuna);
        }

        @Override
        public No visitNumeroInteiro(PortugolParser.NumeroInteiroContext ctx) {
            int valorInteiro = Integer.valueOf(ctx.INT().getText());
            return new NoInteiro(valorInteiro);
        }

        @Override
        public No visitNumeroReal(PortugolParser.NumeroRealContext ctx) {
            double valorDouble = Double.parseDouble(ctx.REAL().getText());
            return new NoReal(valorDouble);
        }

        @Override
        public No visitString(StringContext ctx) {
            return new NoCadeia(ctx.STRING().getText());
        }

        @Override
        public No visitValorLogico(ValorLogicoContext ctx) {
            boolean valor = ctx.LOGICO().getText().equals("verdadeiro");
            return new NoLogico(valor);
        }

        @Override
        public No visitCaracter(CaracterContext ctx) {
            return new NoCaracter(ctx.CARACTER().getText().charAt(0));
        }
        
        @Override
        public No visitOperacaoMenor(PortugolParser.OperacaoMenorContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoLogicaMenor.class);
        }

        @Override
        public No visitOperacaoMenorIgual(PortugolParser.OperacaoMenorIgualContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoLogicaMenorIgual.class);
        }

        @Override
        public No visitOperacaoMaior(PortugolParser.OperacaoMaiorContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoLogicaMaior.class);
        }

        @Override
        public No visitOperacaoMaiorIgual(PortugolParser.OperacaoMaiorIgualContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoLogicaMaiorIgual.class);
        }

        @Override
        public No visitOperacaoIgualdade(PortugolParser.OperacaoIgualdadeContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoLogicaIgualdade.class);
        }

        @Override
        public No visitOperacaoDiferenca(PortugolParser.OperacaoDiferencaContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoLogicaDiferenca.class);
        }

        @Override
        public No visitAdicao(PortugolParser.AdicaoContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoSoma.class);
        }

        @Override
        public No visitDivisao(DivisaoContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoDivisao.class);
        }
        
        @Override
        public No visitMultiplicacao(MultiplicacaoContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoMultiplicacao.class);
        }

        @Override
        public No visitModulo(ModuloContext ctx) {
            return GeradorNoOperacao.gera(ctx, this, NoOperacaoModulo.class);
        }
        
        private NoPara criaNoPara(PortugolParser.ParaContext contexto) {

            PortugolParser.InicializacaoParaContext inicializacaoPara = contexto.inicializacaoPara();
            PortugolParser.CondicaoContext condicao = contexto.condicao();
            PortugolParser.IncrementoParaContext incrementoPara = contexto.incrementoPara();

            NoPara noPara = new NoPara();

            if (inicializacaoPara != null) {
                // TODO tratar as outras inicializações, a gramática só está suportando uma inicialização
                List<NoBloco> inicializacoes = new ArrayList<>();
                inicializacoes.add((NoBloco)inicializacaoPara.accept(this));
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
                noPara.adicionaBloco((NoBloco)comandoContext.accept(this));
            }

            return noPara;
        }

    }

}
