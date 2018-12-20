package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Matriz;
import java.math.BigInteger;

/**
 *
 * @author LITE
 */
public class ErroTamanhoMaximoMatriz extends ErroSemantico
{
    private final int tamanhoLinhasDeclarado;
    private final int tamanhoColunasDeclarado;
    private final String nomeMatrix;
    private final BigInteger bigProduto; 
    private String codigo = "ErroSemantico.ErroTamanhoMaximoMatriz";

    public ErroTamanhoMaximoMatriz(int tamanhoLinhasDeclarado, int tamanhoColunasDeclarado, String nomeMatrix, BigInteger bigProduto, TrechoCodigoFonte trechoCodigoFonte)
    {
        super(trechoCodigoFonte);
        this.tamanhoLinhasDeclarado = tamanhoLinhasDeclarado;
        this.tamanhoColunasDeclarado = tamanhoColunasDeclarado;
        this.nomeMatrix = nomeMatrix;
        this.bigProduto = bigProduto;
        super.setCodigo(codigo);
    }

    
    
    @Override
    protected String construirMensagem()
    {
        return "A matriz '"+nomeMatrix+"' está sendo declarada com "+bigProduto+" posições ("+tamanhoLinhasDeclarado+" x "+tamanhoColunasDeclarado+"), porém o número máximo de posições é "+Matriz.TAMANHO_MAXIMO+". Informe tamanhos cujo produto seja menor ou igual a "+Matriz.TAMANHO_MAXIMO+" para corrigir o problema";
    }
    
}
