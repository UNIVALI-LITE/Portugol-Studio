package br.univali.portugol.nucleo.analise.sintatica.erros;

import br.univali.portugol.nucleo.analise.sintatica.AnalisadorSintatico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;

/**
 * Erro gerado pelo analisador sintático quando o nome utilizado em uma declaração 
 * está em informato inválido
 * <p>
 * No Portugol, os nomes de símbolos devem iniciar com um underline "_", uma letra maiúscula
 * ou uma letra minúscula, seguidos de underline, letra maiúscula, letra minúscula ou número.
 * Não é permitido iniciar um nome com um número e não são permitidos caracteres especiais:
 * (@, #, $, %, ç, á, Ê...). Não há limitação quanto ao comprimento do nome.
 * <p>
 * Exemplo:
 * <code><pre>
 * 
 *      funcao exemploNomeIncompativel()
 *      {
 *           inteiro _valido           // Válido
 *           inteiro 1invalido         // Inválido
 *           inteiro v                 // Válido
 *           inteiro valido2           // Válido
 *           inteiro valido_3          // Válido
 *           inteiro inválido          // Inválido
 *           inteiro inv@lido          // Inválido
 *           inteiro _v_a_l__i_do_253  // Válido
 *           inteiro esteNomeDeVariavelApesarDeCompridoEValido // Válido
 *      }
 * 
 * </pre></code>
 * 
 * 
 * @author Fillipi Domingos Pelz
 * @version 1.0
 * 
 * @see AnalisadorSintatico
 */
public final class ErroNomeIncompativel extends ErroSintatico
{
    /**
     * 
     * @param linha      a linha onde o erro ocorreu.
     * @param coluna     a coluna onde o erro ocorreu.
     * @since 1.0
     */
    public ErroNomeIncompativel(int linha, int coluna)
    {
        super(linha, coluna,"ErroSintatico.ErroNomeIncompativel");
    }

    /**
     * 
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {
        return "Problema na nomeclatura da declaração, uma declaração deve iniciar com letras";
    }
}