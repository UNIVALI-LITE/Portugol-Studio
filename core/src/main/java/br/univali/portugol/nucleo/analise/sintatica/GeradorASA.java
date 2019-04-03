package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolBaseVisitor;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.asa.Quantificador;
import br.univali.portugol.nucleo.asa.TipoDado;

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
    
    private class Antlr4ParserVisitor extends PortugolBaseVisitor<Void> 
    {
        private final ASAPrograma asa = new ASAPrograma();

        public ASAPrograma getAsa() {
            return asa;
        }

        @Override
        public Void visitDeclaracaoFuncao(PortugolParser.DeclaracaoFuncaoContext ctx) {
            
            String nomeFuncao = ctx.ID().getText();
            String nomeTipoRetorno = ctx.TIPO() != null ? ctx.TIPO().getText() : "vazio";
            TipoDado tipoRetorno = TipoDado.obterTipoDadoPeloNome(nomeTipoRetorno);
            
            NoDeclaracaoFuncao declaracaoFuncao = new NoDeclaracaoFuncao(nomeFuncao, tipoRetorno, Quantificador.VALOR);
            
            asa.adicionaDeclaracaoGlobal(declaracaoFuncao);
            
            return super.visitDeclaracaoFuncao(ctx);
        }

    }

}
