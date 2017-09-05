package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.asa.VisitanteASABasico;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroInicializacaoConstante extends ErroSemantico
{
    private NoDeclaracao declaracao;
    private Integer indice = null;
    private Integer linha = null;
    private Integer coluna = null;
    private String codigo = "ErroSemantico.ErroInicializacaoConstante";
    
    public ErroInicializacaoConstante(NoDeclaracaoVariavel declaracao)
    {
        super(declaracao.getInicializacao().getTrechoCodigoFonte());
        this.declaracao = declaracao;
    }
    
    public ErroInicializacaoConstante(NoDeclaracaoVetor declaracao, int indice)
    {
        super(obterTrechoCodigoFonteVetor(declaracao, indice));
        this.declaracao = declaracao;
        this.indice = indice;
    }  
    
    public ErroInicializacaoConstante(NoDeclaracaoMatriz declaracao, int linha, int coluna)
    {
        super(obterTrechoCodigoFonteMatriz(declaracao, linha, coluna));
        this.declaracao = declaracao;
        this.linha = linha;
        this.coluna = coluna;
    }    

   /**
     * {@inheritDoc }
     */
    @Override
    protected String construirMensagem()
    {        
        String mensagem = new ConstrutorMensagem().construirMensagem();
        super.setCodigo(codigo);        
        return mensagem;
    }
    
    private class ConstrutorMensagem extends VisitanteASABasico
    {
        public ConstrutorMensagem()
        {
            
        }        
        
        public String construirMensagem()
        {
            try
            {
                String s = (String) declaracao.aceitar(this);  
                
                return s;         
            }
            catch (Exception e)
            {
                return e.getMessage();
            }
        }

        @Override
        public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();
            
            construtorTexto.append("A constante \"");
            construtorTexto.append(noDeclaracaoVariavel.getNome());
            construtorTexto.append("\" deve ser inicializada com um valor ao invés de uma expressão");
            
            codigo+= ".1";
            return construtorTexto.toString();
        }

        @Override
        public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();
            
            construtorTexto.append("O elemento no índice [");
            construtorTexto.append(indice);
            construtorTexto.append("] do vetor constante \"");
            construtorTexto.append(noDeclaracaoVetor.getNome());
            construtorTexto.append("\" deve ser inicializado com um valor ao invés de uma expressão");
            
            codigo+= ".2";
            return construtorTexto.toString();
        }
        
        @Override
        public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
        {
            StringBuilder construtorTexto = new StringBuilder();
            
            construtorTexto.append("O elemento na posição [");
            construtorTexto.append(linha);
            construtorTexto.append("][");
            construtorTexto.append(coluna);
            construtorTexto.append("] da matriz constante \"");
            construtorTexto.append(noDeclaracaoMatriz.getNome());
            construtorTexto.append("\" deve ser inicializado com um valor ao invés de uma expressão");
            
            codigo+= ".3";
            return construtorTexto.toString();
        }        
    }
    
    private static TrechoCodigoFonte obterTrechoCodigoFonteVetor(NoDeclaracaoVetor declaracaoVetor, int indice)
    {
        NoVetor vetor = (NoVetor) declaracaoVetor.getInicializacao();
        NoExpressao elemento = (NoExpressao) vetor.getValores().get(indice);
        
        return elemento.getTrechoCodigoFonte();
    }
    
    private static TrechoCodigoFonte obterTrechoCodigoFonteMatriz(NoDeclaracaoMatriz declaracaoMatriz, int linha, int coluna)
    {
        NoMatriz matriz = (NoMatriz) declaracaoMatriz.getInicializacao();
        NoExpressao elemento = (NoExpressao) matriz.getValores().get(linha).get(coluna);
        
        return elemento.getTrechoCodigoFonte();
    }
}
