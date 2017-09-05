package br.univali.portugol.nucleo.analise;

import br.univali.portugol.nucleo.mensagens.Erro;

/**
 * @author Fillipi Domingos Pelz
 * @version 1.0
 * 
 * @deprecated     Esta classe era utilizada em versões anteriores para notificar a IDE sobre erros no código fonte
 *                 que ainda não haviam sido tratados. Esta responsabilidade foi transferida para as classes tradutoras
 *                 de erros no pacote br.univali.portugol.nucleo.analise.sintatica.tradutores, portanto, não é 
 *                 mais utilizada e será removida nas versões futuras.
 * 
 */
@Deprecated
public final class ErroAlgoritmoContemErros extends Erro
{
    public ErroAlgoritmoContemErros()
    {
        
    }
    /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        return "O algoritmo contém erros";
    }
}
