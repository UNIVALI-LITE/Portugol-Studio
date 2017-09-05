package br.univali.portugol.nucleo.bibliotecas.base;

/**
 * 
 * @author Luiz Fernando Noschang
 */
public final class MetaDadosParametros extends ColecaoMetaDados<MetaDadosParametro>
{
    MetaDadosParametros()
    {
        super("Não é permitido alterar os metadados dos parâmetros", false);
    }
}
