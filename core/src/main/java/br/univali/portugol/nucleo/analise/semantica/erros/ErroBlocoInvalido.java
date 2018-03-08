package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoLogico;
import br.univali.portugol.nucleo.asa.NoOperacaoLogica;
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVariavel;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroBlocoInvalido extends ErroSemantico
{
    private final NoBloco bloco;

    public ErroBlocoInvalido(NoBloco bloco)
    {
        super(bloco.getTrechoCodigoFonte());
        this.bloco = bloco;
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();

        if ((bloco instanceof NoOperacaoLogica) || (bloco instanceof NoLogico))
        {
            builder.append("Esta expressão lógica não faz sentido se estiver sozinha no código. Você pode utilizar a expressão como condição em um dos seguintes comandos: 'se', 'enquanto', 'faca-enquanto'");

            if (bloco instanceof NoOperacaoLogica)
            {
                NoOperacaoLogica operacao = (NoOperacaoLogica) bloco;

                if (operacao.getOperandoEsquerdo() instanceof NoReferencia)
                {
                    NoReferencia referencia = (NoReferencia) operacao.getOperandoEsquerdo();

                    builder.append(". Se você estiver tentando atribuir um valor ou expressão ");

                    if (referencia instanceof NoReferenciaVariavel)
                    {
                        builder.append("à variável ");
                    }
                    else if (referencia instanceof NoReferenciaVetor)
                    {
                        builder.append("ao vetor ");
                    }
                    else if (referencia instanceof NoReferenciaMatriz)
                    {
                        builder.append("à matriz ");
                    }

                    builder.append("\"");
                    builder.append(referencia.getNome());
                    builder.append("\", utilize o operador '=' ao invés do operador '=='");
                }
            }
        }
        else
        {
            builder.append("Esta expressão não faz sentido se estiver sozinha no código. Você pode atribuir a expressão a uma variável, vetor, matriz ou passá-la como parâmetro em uma chamada de função");
        }

        return builder.toString();
    }
}
