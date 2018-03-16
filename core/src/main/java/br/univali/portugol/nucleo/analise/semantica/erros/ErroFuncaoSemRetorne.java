package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoDeclaracaoFuncao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author 4276663
 */
public class ErroFuncaoSemRetorne extends ErroSemantico
{
    String nomeFuncao;
    public ErroFuncaoSemRetorne(NoDeclaracaoFuncao noDeclaracaoFuncao){
        super(noDeclaracaoFuncao.getTrechoCodigoFonteNome());
        this.nomeFuncao = noDeclaracaoFuncao.getNome();
    }
    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("A função \"");
        builder.append(nomeFuncao);
        builder.append("\" possui situações que não retornam nenhum valor. Verifique se todos os desvios condicionais, laços de repetição e casos do comando \"escolha\" possuem o comando \"retorne\". Você também pode incluir o comando \"retorne\" no final da função");
        return builder.toString();
    }
    
}
