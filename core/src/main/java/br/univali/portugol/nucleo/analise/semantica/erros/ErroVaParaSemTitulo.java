package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoTitulo;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Paula
 */
public class ErroVaParaSemTitulo extends ErroSemantico
{
    private NoTitulo noTitulo;

    /**
     * @param referencia     a referência ao símbolo inexistente.
     */
    public ErroVaParaSemTitulo(NoTitulo noTitulo)
    {  super(noTitulo.getTrechoCodigoFonte(),"ErroSemantico.ErroVaParaSemTitulo");

        this.noTitulo = noTitulo;
    }

    /**
     * Obtém a referência ao símbolo inexistente.
     * 
     * @return     a referência ao símbolo inexistente.
     * @since 1.0
     */
    public NoTitulo getNoTitulo()
    {
        return noTitulo;
    }
    
    @Override
    protected String construirMensagem()
    {
        return new ErroVaParaSemTitulo.ConstrutorMensagem().construirMensagem();
    }
     private class ConstrutorMensagem extends VisitanteASABasico
    {
        public ConstrutorMensagem()
        {
            
        }        
        
        public String construirMensagem()
        {
            try
            {
                return (String) noTitulo.aceitar(this);
            }
            catch (ExcecaoVisitaASA e)
            {
                return e.getMessage();
            }
        }
        
        /**
         * Constrói uma mensagem de erro personalizada para uma declaração de titulo.
         *
         * @return     a mensagem de erro personalizada.
         * @since 1.0
         *
         * @see NoTitulo
         */    
        @Override
        public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();

            construtorTexto.append("O rótulo \"");
            construtorTexto.append(noTitulo.getNome());
            construtorTexto.append("\" não foi declarado neste escopo.");

            return construtorTexto.toString();
        }

       
    }
    
}
