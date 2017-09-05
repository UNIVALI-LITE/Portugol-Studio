package br.univali.portugol.nucleo.analise.semantica.erros;

import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.simbolos.Matriz;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import br.univali.portugol.nucleo.simbolos.Variavel;
import br.univali.portugol.nucleo.simbolos.Vetor;

public final class ErroInicializacaoInvalida extends ErroSemantico
{
    private final NoExpressao inicializacao;
    private final NoDeclaracao declaracao;
    private Simbolo simbolo;
    private String codigo = "ErroSemantico.ErroInicializacaoInvalida.";

    public ErroInicializacaoInvalida(NoDeclaracaoInicializavel declaracao)
    {
       
        super(declaracao.getInicializacao().getTrechoCodigoFonte());
        this.declaracao = declaracao;
        this.inicializacao = declaracao.getInicializacao();
    }

    public ErroInicializacaoInvalida(Simbolo simbolo, NoDeclaracaoInicializavel declaracao)
    {
        this(declaracao);
        this.simbolo = simbolo;
    }
    
    @Override
    protected String construirMensagem()
    {
        StringBuilder builder = new StringBuilder();
        String direito = null;
        builder.append("Não é possível inicializar ");
        
        if (declaracao instanceof NoDeclaracaoVariavel)
        {
            if (declaracao.constante())
            {
                direito = "a constante \"%s\" ";
                codigo+="1";
            }
            else
            {
                direito = "a variável \"%s\" ";
                codigo+="2";
            }
            
        }
        else if (declaracao instanceof NoDeclaracaoVetor)
        {
            direito = "o vetor \"%s\" ";
            codigo+="3";
        }
        else if (declaracao instanceof NoDeclaracaoMatriz)
        {
            direito = "a matriz \"%s\" ";
            codigo+="4";
        }
        
        builder.append(String.format(direito, declaracao.getNome()));
        
        String esquerdo = null;
        
        if (inicializacao instanceof NoVetor)
        {
            builder.append("com um vetor");
            codigo+="1";
        }
        else if (inicializacao instanceof NoMatriz)
        {
            builder.append("com uma matriz");
            codigo+="2";
        }
        else if (inicializacao instanceof NoReferenciaVariavel){
            if (simbolo != null){
                if (simbolo instanceof Variavel)
                {
                    esquerdo = "com o valor da variável \"%s\"";
                    codigo+="3";
                }
                else if (simbolo instanceof Vetor)
                {
                    esquerdo = "com os valores do vetor \"%s\"";
                    codigo+="4";
                }
                else if (simbolo instanceof Matriz)
                {
                    esquerdo ="os valores da matriz \"%s\"";
                    codigo+="5";
                }
                builder.append(String.format(esquerdo,simbolo.getNome()));
            }
        } else {
            builder.append("com um valor ou uma expressão");
            codigo+="6";
        }
        
        super.setCodigo(codigo);
        return builder.toString();
    }
    
}
