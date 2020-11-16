package br.univali.portugol.nucleo.asa;

import java.util.ArrayList;
import java.util.List;

/**
 * @see NoContinue
 */
public final class NoParametroFuncao extends NoBloco
{
    List<NoDeclaracaoParametro> parametros;
    /**
     * {@inheritDoc }
     */
    
    
    
    public NoParametroFuncao()
    {
        parametros = new ArrayList<>();
    }

    public List<NoDeclaracaoParametro> getParametros() {
        return parametros;
    }

    public void addParametro(NoDeclaracaoParametro parametro) {
        this.parametros.add(parametro);
    }

    @Override
    public Object aceitar(VisitanteASA visitante) throws ExcecaoVisitaASA {
        return visitante.visitar(this);
    }
}
