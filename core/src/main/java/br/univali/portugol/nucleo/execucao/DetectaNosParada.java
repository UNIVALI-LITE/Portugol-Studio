package br.univali.portugol.nucleo.execucao;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.asa.*;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fillipi
 */
public class DetectaNosParada implements VisitanteASA
{
    private List<NoBloco> nosParada;
    
    private boolean depuracaoDetalhada = false;

    private Integer linhaBlocoAnterior = null;
    
    public DetectaNosParada(boolean depuracaoDetalhada)
    {
        this.depuracaoDetalhada = depuracaoDetalhada;
    }
    
    private Object interpretarListaBlocos(List<NoBloco> blocos) throws ExcecaoVisitaASA
    {        
        if (blocos != null)
        {
            for (NoBloco noBloco : blocos)
            {
                noBloco.aceitar(this);
            }
        }
        return null;
    }
    
    public List<NoBloco> executar(Programa programa, String[] parametros) throws ErroExecucao
    {
        nosParada = new ArrayList<>();
        
        try
        {
            programa.getArvoreSintaticaAbstrata().aceitar(this);
        }
        catch (ExcecaoVisitaASA ex)
        {
            Logger.getLogger(DetectaNosParada.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nosParada;
    }

    @Override
    public Object visitar(NoDeclaracaoFuncao declaracaoFuncao) throws ExcecaoVisitaASA
    {
        interpretarListaBlocos(declaracaoFuncao.getBlocos());        
        return null;
    }
    
    @Override
    public Object visitar(ASAPrograma asap) throws ExcecaoVisitaASA
    {
        List<NoDeclaracao> listaDeclaracoesGlobais = asap.getListaDeclaracoesGlobais();
        for (NoDeclaracao noDeclaracao : listaDeclaracoesGlobais)
        {
            if (noDeclaracao instanceof NoDeclaracaoFuncao) {
                noDeclaracao.aceitar(this);
            } else {
                nosParada.add(noDeclaracao);
            }
        }
        return null;
    }
    
    @Override
    public Object visitar(NoCaso noCaso) throws ExcecaoVisitaASA
    {
        if (noCaso.getExpressao() != null) 
            nosParada.add(noCaso.getExpressao());
        return null;
    }

    @Override
    public Object visitar(NoEnquanto noEnquanto) throws ExcecaoVisitaASA
    {
        nosParada.add(noEnquanto.getCondicao());
        interpretarListaBlocos(noEnquanto.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoEscolha noEscolha) throws ExcecaoVisitaASA
    {
        List<NoCaso> casos = noEscolha.getCasos();
        nosParada.add(noEscolha.getExpressao());
            
        for(NoCaso caso : casos){
            interpretarListaBlocos(caso.getBlocos());
        }
        return null;
    }

    @Override
    public Object visitar(NoFacaEnquanto noFacaEnquanto) throws ExcecaoVisitaASA
    {        
        nosParada.add(noFacaEnquanto.getCondicao());
        interpretarListaBlocos(noFacaEnquanto.getBlocos());        
        return null;
    }

    @Override
    public Object visitar(NoPara noPara) throws ExcecaoVisitaASA
    {
        NoExpressao condicao = noPara.getCondicao();
        nosParada.add(noPara);
        nosParada.add(noPara.getIncremento());  
        if (depuracaoDetalhada) {
            if (noPara.getInicializacoes() != null && !noPara.getInicializacoes().isEmpty())
            {
                nosParada.add(noPara.getInicializacoes().get(0));
            }
            
            nosParada.add(condicao);
        }
        interpretarListaBlocos(noPara.getBlocos());
        return null;
    }

    @Override
    public Object visitar(NoSe noSe) throws ExcecaoVisitaASA
    {
        nosParada.add(noSe.getCondicao());
        List<NoBloco> blocosVer = noSe.getBlocosVerdadeiros(); 
        List<NoBloco> blocosFal = noSe.getBlocosFalsos();
        interpretarListaBlocos(blocosVer);
        interpretarListaBlocos(blocosFal);

        return null;
    }

    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA
    {
        if (depuracaoDetalhada) {
            nosParada.add(chamadaFuncao);
        } else { 
            if (linhaBlocoAnterior == null || chamadaFuncao.getTrechoCodigoFonte().getLinha() != linhaBlocoAnterior) {
                linhaBlocoAnterior = chamadaFuncao.getTrechoCodigoFonte().getLinha();
                nosParada.add(chamadaFuncao);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoMatriz noDeclaracaoMatriz) throws ExcecaoVisitaASA
    {
        if (depuracaoDetalhada) {
            nosParada.add(noDeclaracaoMatriz);
        } else { 
            if (linhaBlocoAnterior == null || noDeclaracaoMatriz.getTrechoCodigoFonteNome().getLinha() != linhaBlocoAnterior) {
                linhaBlocoAnterior = noDeclaracaoMatriz.getTrechoCodigoFonteNome().getLinha();
                nosParada.add(noDeclaracaoMatriz);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVariavel noDeclaracaoVariavel) throws ExcecaoVisitaASA
    {
        if (depuracaoDetalhada) {
            nosParada.add(noDeclaracaoVariavel);
        } else { 
            if (linhaBlocoAnterior == null || noDeclaracaoVariavel.getTrechoCodigoFonteNome().getLinha() != linhaBlocoAnterior) {
                linhaBlocoAnterior = noDeclaracaoVariavel.getTrechoCodigoFonteNome().getLinha();
                nosParada.add(noDeclaracaoVariavel);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoVetor noDeclaracaoVetor) throws ExcecaoVisitaASA
    {
        if (depuracaoDetalhada) {
            nosParada.add(noDeclaracaoVetor);
        } else { 
            if (linhaBlocoAnterior == null || noDeclaracaoVetor.getTrechoCodigoFonteNome().getLinha() != linhaBlocoAnterior) {
                linhaBlocoAnterior = noDeclaracaoVetor.getTrechoCodigoFonteNome().getLinha();
                nosParada.add(noDeclaracaoVetor);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoInteiro noInteiro) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoLogico noLogico) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMatriz noMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoMenosUnario noMenosUnario) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoNao noNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaIgualdade noOperacaoLogicaIgualdade) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaDiferenca noOperacaoLogicaDiferenca) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoAtribuicao noOperacaoAtribuicao) throws ExcecaoVisitaASA
    {
        if (depuracaoDetalhada) {
            nosParada.add(noOperacaoAtribuicao);
        } else { 
            if (linhaBlocoAnterior == null || noOperacaoAtribuicao.getTrechoCodigoFonte().getLinha() != linhaBlocoAnterior) {
                linhaBlocoAnterior = noOperacaoAtribuicao.getTrechoCodigoFonte().getLinha();
                nosParada.add(noOperacaoAtribuicao);
            }
        }
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaE noOperacaoLogicaE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaOU noOperacaoLogicaOU) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaior noOperacaoLogicaMaior) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMaiorIgual noOperacaoLogicaMaiorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenor noOperacaoLogicaMenor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoLogicaMenorIgual noOperacaoLogicaMenorIgual) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSoma noOperacaoSoma) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoSubtracao noOperacaoSubtracao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoDivisao noOperacaoDivisao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoMultiplicacao noOperacaoMultiplicacao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoModulo noOperacaoModulo) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoPare noPare) throws ExcecaoVisitaASA
    {
        nosParada.add(noPare);
        return null;
    }

    @Override
    public Object visitar(NoReal noReal) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaMatriz noReferenciaMatriz) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVariavel noReferenciaVariavel) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoReferenciaVetor noReferenciaVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoRetorne noRetorne) throws ExcecaoVisitaASA
    {
        nosParada.add(noRetorne);
        return null;
    }

    @Override
    public Object visitar(NoVetor noVetor) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoDeclaracaoParametro noDeclaracaoParametro) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoInclusaoBiblioteca noInclusaoBiblioteca) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseRightShift noOperacaoBitwiseRightShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseE noOperacaoBitwiseE) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseOu noOperacaoBitwiseOu) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseXOR noOperacaoBitwiseXOR) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoBitwiseNao noOperacaoBitwiseNao) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoOperacaoBitwiseLeftShift noOperacaoBitwiseLeftShift) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoContinue noContinue) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoTitulo noTitulo) throws ExcecaoVisitaASA
    {
        return null;
    }

    @Override
    public Object visitar(NoVaPara noVaPara) throws ExcecaoVisitaASA
    {
        return null;
    }

}
