package br.univali.portugol.nucleo.analise;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.mensagens.Aviso;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.Erro;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import br.univali.portugol.nucleo.mensagens.ErroSemantico;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda informações sobre os erros ocorridos e os avisos gerados durante a análise 
 * de um código fonte.
 * 
 * @author Luiz Fernando Noschang
 * @version 1.0
 * 
 * @see Aviso
 * @see Erro
 * @see ErroSemantico
 * @see ErroSintatico
 */
public final class ResultadoAnalise
{   
    private final List<ErroAnalise> erros;
    private final List<ErroSintatico> errosSintaticos;
    private final List<ErroSemantico> errosSemanticos;    
    private final List<AvisoAnalise> avisos;
    private Programa programa;

    public ResultadoAnalise()
    {
        erros = new ArrayList<>();
        errosSintaticos = new ArrayList<>();
        errosSemanticos = new ArrayList<>();
        
        avisos = new ArrayList<>();
    }    
    
    public void setPrograma(Programa programa)
    {
        this.programa = programa;
    }

    public Programa getPrograma()
    {
        return programa;
    }
    
    /**
     * Permite adicionar um aviso ao resultado da análise
     * 
     * @param aviso     o aviso gerado durante a análise.
     * @since 1.0
     */
    public void adicionarAviso(AvisoAnalise aviso)
    {
        avisos.add(aviso);
    }

    /**
     * Permite adicionar um erro ao resultado da análise.
     * 
     * @param erro     o erro gerado durante a análise.
     * @since 1.0
     */    
    public void adicionarErro(ErroAnalise erro)
    {
        erros.add(erro);
        
        if (erro instanceof ErroSintatico) errosSintaticos.add((ErroSintatico) erro);
        else
        if (erro instanceof ErroSemantico) errosSemanticos.add((ErroSemantico) erro);
    }
    
    /**
     * Verifica se o código contém erros, independente do tipo de erro.
     * 
     * @return  true se o algoritmo contiver algum erro, caso contrpario retorna false
     */
    public boolean contemErros()
    {
        return !erros.isEmpty();
    }
    
    /**
     * Verifica se o código contém erros semânticos
     * 
     * @return  true se o algoritmo contiver algum erro semântico, caso contrpario retorna false
     */
    public boolean contemErrosSemanticos()
    {
        return !errosSemanticos.isEmpty();
    }    
    
    /**
     * Verifica se o código contém erros sintáticos
     * 
     * @return  true se o algoritmo contiver algum erro sintático, caso contrpario retorna false
     */
    public boolean contemErrosSintaticos()
    {
        return !errosSintaticos.isEmpty();
    }
    
    /**
     * Verifica se o código contém algum aviso. Os avisos servem para indicar problemas no 
     * código que não irão necessariamente impedir sua execução, mas que podem trazer melhorias
     * quando corrigidos
     * 
     * @return 
     */
    public boolean contemAvisos()
    {
        return !avisos.isEmpty();
    }    
    
    
    /**
     * Obtém a lista de erros ocorridos durante a análise do código fonte.
     * 
     * @return     a lista de erros ocorridos durante a análise do código fonte.
     * @since 1.0
     */    
    public List<ErroAnalise> getErros()
    {
        return new ArrayList<>(erros);
    }

    /**
     * Obtém a lista dos avisos gerados durante a análise do código fonte.
     * 
     * @return     a lista dos avisos gerados durante a análise do código fonte.
     * @since 1.0
     */
    public List<AvisoAnalise> getAvisos()
    {
        return new ArrayList<>(avisos);
    }

    /**
     * Obtém a lista de erros sintáticos ocorridos durante a análise do código fonte.
     * 
     * @return     a lista de erros sintáticos ocorridos durante a análise do código fonte.
     * @since 1.0
     */        
    public List<ErroSintatico> getErrosSintaticos()
    {
        return new ArrayList<>(errosSintaticos);
    }

    /**
     * Obtém a lista de erros semânticos ocorridos durante a análise do código fonte.
     * 
     * @return     a lista de erros semânticos ocorridos durante a análise do código fonte.
     * @since 1.0
     */        
    public List<ErroSemantico> getErrosSemanticos()
    {
        return new ArrayList<>(errosSemanticos);
    }
}