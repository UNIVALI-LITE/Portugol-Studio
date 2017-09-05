/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.GeradorDeExemplosDeInicializacao;
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;

/**
 *
 * @author fillipi
 */
public class ErroSimboloNaoInicializado extends ErroSemantico
{
    private final Simbolo simbolo;
    private final NoReferencia noReferencia;

    public ErroSimboloNaoInicializado(NoReferencia noReferencia, Simbolo simbolo)
    {
        super(noReferencia.getTrechoCodigoFonteNome(), "ErroSemantico.ErroSimboloNaoInicializado");
        this.noReferencia = noReferencia;
        this.simbolo = simbolo;        
    }

    @Override
    protected String construirMensagem()
    {
        StringBuilder construtorTexto = new StringBuilder();        
        
        if (simbolo instanceof Variavel)
        {
            construtorTexto.append("A variável \"");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("\" não foi inicializada. Você deve inicializar a variável antes de poder utilizá-la no programa. ");
            construtorTexto.append("Você pode inicializar a variável atribuindo um valor do tipo \"");
            construtorTexto.append(simbolo.getTipoDado().getNome());
            construtorTexto.append("\". Exemplo: ");
            construtorTexto.append(GeradorDeExemplosDeInicializacao.gerarExemploDeInicializacaoDeVariavel((Variavel) simbolo));
            construtorTexto.append(". Você também pode usar a função \"leia\" para ler um valor digitado pelo usuário. Exemplo: ");
            construtorTexto.append("leia(");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append(")");
            
        }
        else if (simbolo instanceof Vetor)
        {
            construtorTexto.append("O vetor \"");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("\" não foi inicializado. Você deve inicializar o vetor antes de poder utilizá-lo no programa. Você pode inicializar o vetor atribuindo valores do tipo \"");
            construtorTexto.append(simbolo.getTipoDado().getNome());
            construtorTexto.append("\". Exemplo: ");
            construtorTexto.append(GeradorDeExemplosDeInicializacao.gerarExemploDeInicializacaoDeVetor((Vetor) simbolo, 4));
            construtorTexto.append(". Você também pode usar a função \"leia\" para ler valores digitados pelo usuário. Exemplo: ");
            construtorTexto.append("leia(");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("[0])");
        }
        else if (simbolo instanceof Matriz)
        {
            construtorTexto.append("A matriz \"");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("\" não foi inicializada. Você deve inicializar a matriz antes de poder utilizá-la no programa. Você pode inicializar a matriz atribuindo valores do tipo \"");
            construtorTexto.append(simbolo.getTipoDado().getNome());
            construtorTexto.append("\". Exemplo: ");
            construtorTexto.append(GeradorDeExemplosDeInicializacao.gerarExemploDeInicializacaoDeMatriz((Matriz) simbolo, 3, 3));
            construtorTexto.append(". Você também pode usar a função \"leia\" para ler valores digitados pelo usuário. Exemplo: ");
            construtorTexto.append("leia(");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("[0][0])");
            
        }
        
        return construtorTexto.toString();                
    }
    
}
