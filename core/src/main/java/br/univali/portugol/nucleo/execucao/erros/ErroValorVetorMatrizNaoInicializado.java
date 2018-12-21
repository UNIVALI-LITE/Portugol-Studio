package br.univali.portugol.nucleo.execucao.erros;

import br.univali.portugol.nucleo.GeradorDeExemplosDeInicializacao;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.NoReferenciaMatriz;
import br.univali.portugol.nucleo.asa.NoReferenciaVetor;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Vetor;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroValorVetorMatrizNaoInicializado extends ErroExecucao
{
    private final NoReferencia referencia;
    private final Simbolo simbolo;
    private int linha;
    private int coluna;
    private int indice;
    private String codigo = "ErroExecucao.ErroValorVetorMatrizNaoInicializado.";

    public ErroValorVetorMatrizNaoInicializado(Vetor vetor, NoReferenciaVetor referenciaVetor, int indice)
    {
        this.simbolo = vetor;
        this.referencia = referenciaVetor;
        this.indice = indice;
        this.setLinha(referencia.getTrechoCodigoFonteNome().getLinha());
        this.setColuna(referencia.getTrechoCodigoFonteNome().getColuna());
        codigo += "1";
        super.setCodigo(codigo);
    }

    public ErroValorVetorMatrizNaoInicializado(Matriz matriz, NoReferenciaMatriz referenciaMatriz, int linha, int coluna)
    {
        this.simbolo = matriz;
        this.referencia = referenciaMatriz;
        this.linha = linha;
        this.coluna = coluna;
        this.setLinha(referencia.getTrechoCodigoFonteNome().getLinha());
        this.setColuna(referencia.getTrechoCodigoFonteNome().getColuna());
        codigo += "2";
        super.setCodigo(codigo);
    }

    /**
     * {@inheritDoc }
     *
     * @return o texto da mensagem
     */
    @Override
    protected String construirMensagem()
    {
        return new ConstrutorMensagem().construirMensagem();
    }

    private final class ConstrutorMensagem extends VisitanteASABasico
    {
        public ConstrutorMensagem()
        {

        }

        public String construirMensagem()
        {
            try
            {
                return (String) referencia.aceitar(this);
            }
            catch (ExcecaoVisitaASA e)
            {
                return e.getMessage();
            }
        }

        @Override
        public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();

            construtorTexto.append(String.format("O elemento do vetor '%s', na posição [%d] não foi inicializado", noReferenciaVetor.getNome(), indice));
            construtorTexto.append(". Você pode inicializar o elemento atribuindo um valor do tipo \"");
            construtorTexto.append(simbolo.getTipoDado().getNome());
            construtorTexto.append("\". Exemplo: ");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("[");
            construtorTexto.append(indice);
            construtorTexto.append("] = ");
            construtorTexto.append(GeradorDeExemplosDeInicializacao.gerarValorAleatorio(simbolo.getTipoDado()));
            construtorTexto.append(". Você também pode usar a função \"leia\" para ler um valor digitado pelo usuário. Exemplo: ");
            construtorTexto.append("leia(");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("[");
            construtorTexto.append(indice);
            construtorTexto.append("])");

            return construtorTexto.toString();
        }
        
        @Override
        public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();

            construtorTexto.append(String.format("O elemento da matriz '%s', na linha [%d] e coluna [%d] não foi inicializado", noReferenciaMatriz.getNome(), linha, coluna));
            construtorTexto.append(". Você pode inicializar o elemento atribuindo um valor do tipo \"");
            construtorTexto.append(simbolo.getTipoDado().getNome());
            construtorTexto.append("\". Exemplo: ");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("[");
            construtorTexto.append(linha);
            construtorTexto.append("][");
            construtorTexto.append(coluna);
            construtorTexto.append("] = ");
            construtorTexto.append(GeradorDeExemplosDeInicializacao.gerarValorAleatorio(simbolo.getTipoDado()));
            construtorTexto.append(". Você também pode usar a função \"leia\" para ler um valor digitado pelo usuário. Exemplo: ");
            construtorTexto.append("leia(");
            construtorTexto.append(simbolo.getNome());
            construtorTexto.append("[");
            construtorTexto.append(linha);
            construtorTexto.append("][");
            construtorTexto.append(coluna);
            construtorTexto.append("])");

            return construtorTexto.toString();
        }
    }
}
