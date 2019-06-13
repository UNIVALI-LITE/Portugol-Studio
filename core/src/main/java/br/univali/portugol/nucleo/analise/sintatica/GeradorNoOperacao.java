package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolBaseVisitor;
import br.univali.portugol.nucleo.analise.sintatica.antlr4.PortugolParser;
import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoExpressao;
import java.lang.reflect.Constructor;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 *
 * @author Elieser
 */
class GeradorNoOperacao {

    public static <TipoContexto extends ParserRuleContext, TipoRetorno extends NoBloco> 
    TipoRetorno gera(TipoContexto ctx, PortugolBaseVisitor visitor, Class<TipoRetorno> clazz) {
        try {
            ParserRuleContext opEsquerdo = ctx.getRuleContext(PortugolParser.ExpressaoContext.class, 0);// ctx.expressao(0);
            ParserRuleContext opDireito = ctx.getRuleContext(PortugolParser.ExpressaoContext.class, 1);//ctx.expressao(1);
            Constructor<TipoRetorno> constructor = clazz.getConstructor(NoExpressao.class, NoExpressao.class);

            return constructor.newInstance((NoExpressao) opEsquerdo.accept(visitor), (NoExpressao) opDireito.accept(visitor));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
}
