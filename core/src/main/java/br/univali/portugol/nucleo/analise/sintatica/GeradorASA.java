package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolBaseVisitor;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser.*;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.TerminalNode;

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
            
            declaracoesGlobais.addAll(ctx.declaracaoListaVariaveis());
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
        public No visitDeclaracaoListaVariaveis(DeclaracaoListaVariaveisContext ctx) {
            TipoDado tipo = TipoDado.obterTipoDadoPeloNome(ctx.TIPO().getText());
            NoListaDeclaracaoVariaveis no = new NoListaDeclaracaoVariaveis(tipo);
            
            int totalVariaveis = ctx.ID().size();
            for (int i = 0; i < totalVariaveis; i++) {
                String nomeVariavel = ctx.ID(i).getText();
                
                NoDeclaracaoVariavel noVariavel = new NoDeclaracaoVariavel(nomeVariavel, tipo);
                
                ExpressaoContext inicializacao = ctx.expressao(i);
                if (inicializacao != null) { // a variável tem inicialização?
                    noVariavel.setInicializacao((NoExpressao)inicializacao.accept(this));
                }
                
                no.adicionaDeclaracao(noVariavel);
            }
            
            return no;
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

            declaracaoFuncao.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            declaracaoFuncao.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.FUNCAO(), ctx.getText().length()));
            
            if (ctx.TIPO() != null) {
                declaracaoFuncao.setTrechoCodigoFonteTipoDado(getTrechoCodigoFonte(ctx.TIPO()));
            }
            
            return declaracaoFuncao;
        }

        @Override
        public No visitNegacao(NegacaoContext ctx) {
            NoNao noNao = new NoNao((NoExpressao)ctx.expressao().accept(this));
            
            noNao.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.OP_NAO(), ctx.getText().length()));
            
            return noNao;
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

            NoReferenciaVariavel no = new NoReferenciaVariavel(escopo, nomeVariavel);
            
            no.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            no.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ID(), ctx.getText().length()));
            
            return no;
        }

        @Override
        public No visitPare(PareContext ctx) {
            NoPare noPare = new NoPare();
            noPare.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.PARE()));
            return noPare;
        }

        @Override
        public No visitCaso(CasoContext casoContext) {
            NoExpressao expressao = casoContext.CONTRARIO() == null ? (NoExpressao) casoContext.expressao().accept(this) : null;
            NoCaso caso = new NoCaso(expressao);
            caso.setBlocos(getBlocos(casoContext.comando()));
            caso.setTrechoCodigoFonte(getTrechoCodigoFonte(casoContext.CASO(), casoContext.getText().length()));
            return caso;
        }
        
        @Override
        public No visitEscolha(EscolhaContext ctx) {
            NoEscolha noEscolha = new NoEscolha((NoExpressao)ctx.expressao().accept(this));
            
            List<NoCaso> casos = new ArrayList<>();
            for (CasoContext casoContext : ctx.caso()) {
                casos.add((NoCaso)casoContext.accept(this));
            }
             
            noEscolha.setCasos(casos);
            
            noEscolha.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ESCOLHA(), ctx.getText().length()));
            
            return noEscolha;
        }
        
        private List<NoBloco> getBlocos(List<ComandoContext> ctx) {
            List<NoBloco> blocos = new ArrayList<>();
            for (ComandoContext comando : ctx) {
                blocos.add((NoBloco)comando.accept(this));
            }
            return blocos;
        }
        
        private List<NoBloco> getBlocos(ListaComandosContext ctx) {
            return getBlocos(ctx.comando());
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
            NoOperacaoAtribuicao atribuicao = GeradorNoOperacao.gera(ctx, this, NoOperacaoAtribuicao.class);
            
            NoExpressao esquerda = (NoExpressao)ctx.expressao(0).accept(this);
            atribuicao.setTrechoCodigoFonte(new TrechoCodigoFonte(esquerda.getTrechoCodigoFonte(), ctx.getText().length()));
            
            return atribuicao;
        }

        @Override
        public No visitDeclaracaoVariavel(PortugolParser.DeclaracaoVariavelContext ctx) {
            TipoDado tipoVariavel = TipoDado.obterTipoDadoPeloNome(ctx.TIPO().getText());
            String nomeVariavel = ctx.ID().getText();
            boolean constante = ctx.CONSTANTE() != null;
            NoDeclaracaoVariavel noDeclaracaoVariavel = new NoDeclaracaoVariavel(nomeVariavel, tipoVariavel, constante);

            // a declaração da variável tem uma inicialização?
            if (ctx.expressao() != null) {
                NoExpressao inicializacao = (NoExpressao) ctx.expressao().accept(this);
                noDeclaracaoVariavel.setInicializacao(inicializacao);
            }
            
            noDeclaracaoVariavel.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            noDeclaracaoVariavel.setTrechoCodigoFonteTipoDado(getTrechoCodigoFonte(ctx.TIPO()));
            
            // FALTA TRATAR O CASO DAS CONSTATES
            noDeclaracaoVariavel.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.TIPO(), ctx.getText().length()));
            
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
            NoInteiro noInteiro = new NoInteiro(Integer.parseInt(ctx.INT().getText()));
            noInteiro.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.INT()));
            return noInteiro;
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
            
            matriz.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            matriz.setTrechoCodigoFonteTipoDado(getTrechoCodigoFonte(ctx.TIPO()));
            
            // TODO TRATAR as constates
            matriz.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.TIPO(), ctx.getText().length()));
            
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
            
            vetor.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            vetor.setTrechoCodigoFonteTipoDado(getTrechoCodigoFonte(ctx.TIPO()));
            
            // TODO TRATAR as constates
            vetor.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.TIPO(), ctx.getText().length()));
            
            return vetor; 
        }

        // Loop para (for)
        @Override
        public No visitPara(PortugolParser.ParaContext contexto) {
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
            noPara.setBlocos(getBlocos(contexto.listaComandos()));

            noPara.setTrechoCodigoFonte(getTrechoCodigoFonte(contexto.PARA(), contexto.getText().length()));
            
            return noPara;
        }

        @Override
        public No visitFacaEnquanto(FacaEnquantoContext ctx) {
            NoFacaEnquanto facaEnquanto = new NoFacaEnquanto((NoExpressao)ctx.expressao().accept(this));
            facaEnquanto.setBlocos(getBlocos(ctx.listaComandos()));
            facaEnquanto.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.FACA(), ctx.getText().length()));
            return facaEnquanto;
        }

        @Override
        public No visitEnquanto(EnquantoContext ctx) {
            NoEnquanto enquanto = new NoEnquanto((NoExpressao)ctx.expressao().accept(this));
            enquanto.setBlocos(getBlocos(ctx.listaComandos()));
            enquanto.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ENQUANTO(), ctx.getText().length()));
            return enquanto;
        }
        
        @Override
        public No visitSe(SeContext ctx) {
            NoSe se = new NoSe((NoExpressao)ctx.expressao().accept(this));
            
            se.setBlocosVerdadeiros(getBlocos(ctx.listaComandos(0)));
            
            if (ctx.SENAO() != null) {
                se.setBlocosFalsos(getBlocos(ctx.listaComandos(1)));
            }
            
            se.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.SE(), ctx.getText().length()));
            
            return se;
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
            
            noChamadaFuncao.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            noChamadaFuncao.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ID(), ctx.getText().length()));
            
            return noChamadaFuncao;
        }

        @Override
        public No visitReferenciaArray(ReferenciaArrayContext ctx) {
            String escopo = null;
            if (ctx.escopoBiblioteca() != null) {
                escopo = ctx.escopoBiblioteca().getText();
            }
            
            String nomeArray = ctx.ID().getText();

            NoReferenciaVetor noReferenciaVetor = new NoReferenciaVetor(escopo, nomeArray, (NoExpressao)ctx.expressao().accept(this));
            
            noReferenciaVetor.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            noReferenciaVetor.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ID(), ctx.getText().length()));
            
            return noReferenciaVetor;
        }
        
        private TrechoCodigoFonte getTrechoCodigoFonte(TerminalNode node, int tamanho) {
            Token symbol = node.getSymbol();
            int linha = symbol.getLine();
            int coluna = symbol.getCharPositionInLine();
            return new TrechoCodigoFonte(linha, coluna, tamanho);
        }
        
        private TrechoCodigoFonte getTrechoCodigoFonte(TerminalNode node) {
            int tamanho = node.getSymbol().getText().length();
            return getTrechoCodigoFonte(node, tamanho);
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
            
            NoReferenciaMatriz matriz = new NoReferenciaMatriz(escopo, nomeMatriz, expressaoLinha, expressaoColuna);
            
            matriz.setTrechoCodigoFonteNome(getTrechoCodigoFonte(ctx.ID()));
            matriz.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ID(), ctx.getText().length()));
            
            return matriz;
        }

        @Override
        public No visitNumeroInteiro(PortugolParser.NumeroInteiroContext ctx) {
            int valorInteiro = Integer.valueOf(ctx.INT().getText());
            
            NoInteiro noInteiro = new NoInteiro(valorInteiro);
            noInteiro.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.INT()));
            return noInteiro;
        }

        @Override
        public No visitNumeroReal(PortugolParser.NumeroRealContext ctx) {
            double valorDouble = Double.parseDouble(ctx.REAL().getText());
            NoReal noReal = new NoReal(valorDouble);
            noReal.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.REAL()));
            return noReal;
        }

        @Override
        public No visitExpressaoEntreParenteses(ExpressaoEntreParentesesContext ctx) {
            NoExpressao expressao = (NoExpressao)ctx.expressao().accept(this);
            expressao.setEstaEntreParenteses(true);
            expressao.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.ABRE_PARENTESES(), ctx.getText().length()));
            return expressao;
        }

        @Override
        public No visitMenosUnario(MenosUnarioContext ctx) {
            NoMenosUnario noMenosUnario = new NoMenosUnario((NoExpressao)ctx.expressao().accept(this));
            noMenosUnario.setTrechoCodigoFonteMenos(getTrechoCodigoFonte(ctx.OP_SUBTRACAO()));
            noMenosUnario.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.OP_SUBTRACAO(), ctx.getText().length()));
            return noMenosUnario;
        }
        
        @Override
        public No visitString(StringContext ctx) {
            NoCadeia noCadeia = new NoCadeia(ctx.STRING().getText());
            noCadeia.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.STRING()));
            return noCadeia;
        }

        @Override
        public No visitValorLogico(ValorLogicoContext ctx) {
            boolean valor = ctx.LOGICO().getText().equals("verdadeiro");
            NoLogico noLogico = new NoLogico(valor);
            noLogico.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.LOGICO()));
            return noLogico;
        }

        @Override
        public No visitCaracter(CaracterContext ctx) {
            NoCaracter noCaracter = new NoCaracter(ctx.CARACTER().getText().charAt(1));
            noCaracter.setTrechoCodigoFonte(getTrechoCodigoFonte(ctx.CARACTER()));
            return noCaracter;
        }
        
        private <TipoContexto extends ParserRuleContext> 
        NoOperacao criaNoOperacao(TipoContexto contexto, PortugolBaseVisitor visitor, Class<? extends NoOperacao> clazz, TerminalNode operador) {
            
            NoOperacao no = GeradorNoOperacao.gera(contexto, visitor, clazz);
            no.setTrechoCodigoFonteOperador(getTrechoCodigoFonte(operador));
            no.setTrechoCodigoFonte(new TrechoCodigoFonte(no.getOperandoEsquerdo().getTrechoCodigoFonte(), contexto.getText().length()));
            return no;
        }
        
        @Override
        public No visitOperacaoMenor(PortugolParser.OperacaoMenorContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoLogicaMenor.class, ctx.OP_MENOR());
        }

        @Override
        public No visitOperacaoMenorIgual(PortugolParser.OperacaoMenorIgualContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoLogicaMenorIgual.class, ctx.OP_MENOR_IGUAL());
        }

        @Override
        public No visitOperacaoMaior(PortugolParser.OperacaoMaiorContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoLogicaMaior.class, ctx.OP_MAIOR());
        }

        @Override
        public No visitOperacaoMaiorIgual(PortugolParser.OperacaoMaiorIgualContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoLogicaMaiorIgual.class, ctx.OP_MAIOR_IGUAL());
        }

        @Override
        public No visitOperacaoIgualdade(PortugolParser.OperacaoIgualdadeContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoLogicaIgualdade.class, ctx.OP_IGUALDADE());
        }

        @Override
        public No visitOperacaoDiferenca(PortugolParser.OperacaoDiferencaContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoLogicaDiferenca.class, ctx.OP_DIFERENCA());
         }

        @Override
        public No visitAdicao(PortugolParser.AdicaoContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoSoma.class, ctx.OP_ADICAO());
        }

        @Override
        public No visitDivisao(DivisaoContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoDivisao.class, ctx.OP_DIVISAO());
        }
        
        @Override
        public No visitMultiplicacao(MultiplicacaoContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoMultiplicacao.class, ctx.OP_MULTIPLICACAO());
        }

        @Override
        public No visitModulo(ModuloContext ctx) {
            return criaNoOperacao(ctx, this, NoOperacaoModulo.class, ctx.OP_MOD());
                    
        }

    }

}
