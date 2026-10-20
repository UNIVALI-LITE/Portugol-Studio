package br.univali.portugol.nucleo.execucao.gerador.helpers;

import br.univali.portugol.nucleo.analise.semantica.TamanhoDinamicoDetector;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoDeclaracaoMatriz;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVariavel;
import br.univali.portugol.nucleo.asa.NoDeclaracaoVetor;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.NoExpressaoLiteral;
import br.univali.portugol.nucleo.asa.NoMatriz;
import br.univali.portugol.nucleo.asa.NoVetor;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.asa.VisitanteASA;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Elieser
 */
public class GeradorDeclaracaoVariavel
{

    
    public void gera(NoMatriz matriz, PrintWriter saida, VisitanteASA visitor, int nivelEscopo) throws ExcecaoVisitaASA
    {
     
        List<List<Object>> valoresNaoNulos = getValoresNaoNulos(matriz);
        
        if (!valoresNaoNulos.isEmpty())
        {
            saida.format("new %s[][]", Utils.getNomeTipoJava(matriz.getTipoResultante()));
        }
        
        saida.append("{");

        int totalLinhas = valoresNaoNulos.size();
        int totalColunas = 0;
        for (int i = 0; i < totalLinhas; i++)
        {
            totalColunas = valoresNaoNulos.get(i).size();
            saida.append("{");
            for (int j = 0; j < totalColunas; j++)
            {
                NoExpressao valor = (NoExpressao) valoresNaoNulos.get(i).get(j);
                valor.aceitar(visitor);

                if (j < totalColunas - 1)
                {
                    saida.append(", ");
                }
            }
            saida.append("}");
            if (i < totalLinhas - 1)
            {
                saida.append(",");
            }
        }

        saida.append("}");
    }

    private List<List<Object>> getValoresNaoNulos(NoMatriz matriz)
    {
        List<List<Object>> valoresNaoNulos = new ArrayList<>();
        List<List<Object>> valoresOriginais = matriz.getValores();
        for (List<Object> linha : valoresOriginais)
        {
            List<Object> novaLinha = new ArrayList<>();
            for (Object valor : linha)
            {
                if (valor !=null)
                {
                    novaLinha.add(valor);
                }
            }
            
            if (!novaLinha.isEmpty()) {
                valoresNaoNulos.add(novaLinha);            
            }
        }
        
        return valoresNaoNulos;
    }

    private List<Object> getValoresNaoNulos(NoVetor vetor)
    {
        List<Object> valoresNaoNulos = new ArrayList<>();
        List<Object> valoresOriginais = vetor.getValores();
        for (Object valor : valoresOriginais)
        {
            if (valor != null)
            {
                valoresNaoNulos.add(valor);
            }
        }
        
        return valoresNaoNulos;
    }
    
    public void gera(NoVetor vetor, PrintWriter saida, VisitanteASA visitor, int nivelEscopo) throws ExcecaoVisitaASA
    {

        List<Object> valoresNaoNulos = getValoresNaoNulos(vetor);
        
        if (!valoresNaoNulos.isEmpty())
        {
            saida.format("new %s[]", Utils.getNomeTipoJava(vetor.getTipoResultante()));
        }
        
        saida.append("{");

        int totalValores = valoresNaoNulos.size();
        for (int i = 0; i < totalValores; i++)
        {
            Object valor = valoresNaoNulos.get(i);
            if (valor instanceof NoExpressaoLiteral)
            {
                saida.append(valor.toString());
            }
            else
            {
                ((NoExpressao) valor).aceitar(visitor);
            }

            if (i < totalValores - 1)
            {
                saida.append(", ");
            }
        }

        saida.append("}");
    }

    public void gera(NoDeclaracaoVetor vetor, PrintWriter saida, VisitanteASA visitor, 
                int nivelEscopo, boolean podeInicializar) throws ExcecaoVisitaASA
    {
        
        String nome = vetor.getNome();
        String tipo = Utils.getNomeTipoJava(vetor.getTipoDado());
        saida.format("%s %s[]", tipo, nome);

        if (podeInicializar || vetor.constante())
        {
            if (vetor.temInicializacao())
            {
                saida.append(" = ");
                vetor.getInicializacao().aceitar(visitor);
            }
            else
            {
                NoExpressao tamanho = vetor.getTamanho();
                if (tamanho != null)
                {
                    saida.format(" = new %s[", tipo);
                    geraTamanhoVetor(tamanho, saida, visitor);
                    saida.append("]");
                }
            }
        }
    }

    /**
     * Gera a expressão que define o tamanho de um vetor. Quando o tamanho é
     * dinâmico (depende de uma variável que só tem valor em tempo de
     * execução), a expressão é envolvida por uma chamada a
     * {@code validaTamanhoDinamico}, que verifica se o tamanho obtido é
     * positivo, gerando um erro amigável caso contrário.
     */
    private void geraTamanhoVetor(NoExpressao tamanho, PrintWriter saida, VisitanteASA visitor) throws ExcecaoVisitaASA
    {
        if (TamanhoDinamicoDetector.ehTamanhoDinamico(tamanho))
        {
            saida.append("validaTamanhoDinamico(");
            tamanho.aceitar(visitor);
            saida.append(")");
        }
        else
        {
            tamanho.aceitar(visitor);
        }
    }

    /**
     * Gera a expressão que define o número de linhas ou colunas de uma
     * matriz. Quando o tamanho é dinâmico, a expressão é envolvida por uma
     * chamada a {@code validaTamanhoDinamico}.
     */
    private void geraTamanhoMatriz(NoExpressao tamanho, PrintWriter saida, VisitanteASA visitor) throws ExcecaoVisitaASA
    {
        geraTamanhoVetor(tamanho, saida, visitor);
    }

    public void gera(NoDeclaracaoMatriz matriz, PrintWriter saida, VisitanteASA visitor, 
            int nivelEscopo, boolean podeInicializar) throws ExcecaoVisitaASA
    {
        
        String nome = matriz.getNome();
        String tipo = Utils.getNomeTipoJava(matriz.getTipoDado());
        saida.format("%s %s[][]", tipo, nome);

        if (podeInicializar || matriz.constante())
        {
            if (matriz.temInicializacao())
            {
                saida.append(" = ");
                matriz.getInicializacao().aceitar(visitor);
            }
            else
            {
                NoExpressao numeroLinhas = matriz.getNumeroLinhas();
                NoExpressao numeroColunas = matriz.getNumeroColunas();
                
                if (numeroLinhas != null && numeroColunas != null)
                {
                    saida.append(" = new ").
                            append(tipo)
                            .append("[");
                    
                    geraTamanhoMatriz(matriz.getNumeroLinhas(), saida, visitor);
                    saida.append("][");
                    geraTamanhoMatriz(matriz.getNumeroColunas(), saida, visitor);
                    saida.append("]");
                }
            }
        }

    }

    public boolean gera(NoDeclaracaoVariavel variavel, PrintWriter saida, 
                VisitanteASA visitor, int nivelEscopo, boolean podeInicializar, boolean inicializandoNoPara, int indiceAtualInicializacaoPara) throws ExcecaoVisitaASA
    {

        if (variavel.ehPassadaPorReferencia() && !variavel.temInicializacao())
        {
            return false; //variáveis passadas por referência e não inicializadas (casos raros) não aparecem no código java, caso contrário o código java gerado terá um erro Not a stament para códigos como este: REFS_STRINGS[INDICE];
        }
        
        if (variavel.ehPassadaPorReferencia())
        {
            saida.format("REFS_%s[%s]", 
                    Utils.getNomeTipoJava(variavel.getTipoDado()).toUpperCase(),
                    Utils.geraStringIndice(variavel));
        }
        else
        {
        	if (!inicializandoNoPara || (inicializandoNoPara && indiceAtualInicializacaoPara == 0))
        	{
        		String nome = variavel.getNome();
        		String nomeTipo = Utils.getNomeTipoJava(variavel.getTipoDado());

        		saida.format("%s %s", nomeTipo, nome);
        	}
        	else
        	{
        		String nome = variavel.getNome();

        		saida.format("%s", nome);
        	}
        }

        if ((podeInicializar || variavel.constante()) && variavel.temInicializacao()) //constantes sempre devem ser inicializadas
        {
            saida.append(" = ");

            // verifica se é necessário fazer cast de um double para int quando o parâmetro esperado é int
            boolean precisaDeCast = variavel.getTipoDado() == TipoDado.INTEIRO && variavel.getInicializacao().getTipoResultante() == TipoDado.REAL;

            if (precisaDeCast)
            {
                saida.append("(int) (");
            }

            variavel.getInicializacao().aceitar(visitor);

            if (precisaDeCast)
            {
                saida.append(")");
            }
        }
        
        return true; // 'avisa' que o código de declaração foi gerado
    }
}
