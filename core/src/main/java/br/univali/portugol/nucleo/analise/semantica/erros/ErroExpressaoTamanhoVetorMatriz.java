package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.NoBloco;
import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author fillipi
 */
public class ErroExpressaoTamanhoVetorMatriz extends ErroSemantico 
{
    private NoDeclaracaoBase declaracao;
    private NoBloco tamanho;
    private String valorErrado;
    private String codigo = "ErroSemantico.ErroTamanhoVetorMatriz.";
        
    public ErroExpressaoTamanhoVetorMatriz(NoDeclaracaoBase declaracao, NoBloco tamanho, String valorErrado)
    {
        super(tamanho.getTrechoCodigoFonte(),"ErroSemantico.ErroTamanhoVetorMatriz");
        this.declaracao = declaracao;
        this.tamanho = tamanho;
        this.valorErrado = valorErrado;
    }

    public ErroExpressaoTamanhoVetorMatriz(NoDeclaracaoBase declaracao, NoBloco tamanho)
    {
        super(tamanho.getTrechoCodigoFonte(),"ErroSemantico.ErroTamanhoVetorMatriz");
        this.declaracao = declaracao;
        this.tamanho = tamanho;
    }

    @Override
    protected String construirMensagem()
    {
        if(this.valorErrado == null || this.valorErrado.equals(""))
        {
            if (declaracao instanceof NoDeclaracaoVetor)
            {
                    codigo += "1";
                    super.setCodigo(codigo);

                return String.format("O tamanho do vetor '%s' deve ser um valor ou uma constante do tipo inteiro e positivo \n Ex: vetor[3]", declaracao.getNome());
            }
            else
            {            
                String aux = (tamanho == ((NoDeclaracaoMatriz) declaracao).getNumeroLinhas())? "linhas" : "colunas";
                codigo += "2";
                super.setCodigo(codigo);

                return String.format("O número de %s da matriz '%s' deve ser um valor ou uma constante do tipo inteiro e positivo \n Ex: matriz[5][4]", aux, declaracao.getNome());
            }
        }
        else
        {
            if (declaracao instanceof NoDeclaracaoVetor)
            {
                    codigo += "1";
                    super.setCodigo(codigo);

                return String.format("A variavel '%s' do tamanho do vetor '%s' deve ser uma variavel ou valor constante do tipo inteiro e positivo \n Ex: \n const inteiro x = 3 \n vetor[x]",this.valorErrado, declaracao.getNome());
            }
            else
            {            
                String aux = (tamanho == ((NoDeclaracaoMatriz) declaracao).getNumeroLinhas())? "linhas" : "colunas";
                codigo += "2";
                super.setCodigo(codigo);

                return String.format("A variavel '%s' no número de %s da matriz '%s' deve ser um valor ou uma constante do tipo inteiro e positivo \n Ex: \n const inteiro x = 3 \n matriz[x][5]",this.valorErrado, aux, declaracao.getNome());
            }
        }
    }
}
