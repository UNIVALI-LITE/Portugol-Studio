package br.univali.ps.ui.abas.abaAjuda;

/**
 *
 * @author 4276663
 */
public class TipoUrlInvalidoException extends Exception
{
    public TipoUrlInvalidoException(String tipoFornecido)
    {
        super("Na ajuda em html, foi passado o tipo \""+tipoFornecido+" que não é considerado valido.");
    }
}
