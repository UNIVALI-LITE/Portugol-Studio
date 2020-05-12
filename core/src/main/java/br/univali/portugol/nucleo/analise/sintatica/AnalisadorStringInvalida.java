/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.analise.sintatica;

import br.univali.portugol.nucleo.analise.sintatica.erros.ErroCadeiaIncompleta;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroEscapeUnico;
import br.univali.portugol.nucleo.analise.sintatica.erros.ErroLinhaPuladaEmString;
import br.univali.portugol.nucleo.asa.ASA;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.asa.NoCadeia;
import br.univali.portugol.nucleo.asa.NoCaracter;
import br.univali.portugol.nucleo.asa.NoChamadaFuncao;
import br.univali.portugol.nucleo.asa.NoExpressao;
import br.univali.portugol.nucleo.asa.VisitanteNulo;
import br.univali.portugol.nucleo.mensagens.ErroSintatico;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author 3798925
 */
public class AnalisadorStringInvalida extends VisitanteNulo{
    
     private Collection<ObservadorAnaliseSintatica> observadores = new ArrayList<>();
     
    private ASA asa; 
    public void analisar(ASA asa)
    {
        this.asa = asa;
        if (asa != null)
        {
            try
            {
                asa.aceitar(this);
            }
            catch (Exception excecao)
            {
                //notificarErroSintatico(null);
            }
        }
    }

    @Override
    public Object visitar(NoChamadaFuncao chamadaFuncao) throws ExcecaoVisitaASA {
         List<NoExpressao> parametros = chamadaFuncao.getParametros();
         for (NoExpressao parametro : parametros) {
            parametro.aceitar(this);
        }
         
        return super.visitar(chamadaFuncao); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public Object visitar(NoCadeia noCadeia) throws ExcecaoVisitaASA {
        
//        Pattern pattern = Pattern.compile("(?<![\\\\])\\\\(?![tnbrf\\\"\\\\])");
//        Matcher matcher = pattern.matcher(noCadeia.getValor());
//        
//        while (matcher.find())
//        {
//            String codigo = matcher.group();
//            int start = matcher.start();
//            
//            notificarErroSintatico(new ErroEscapeUnico(noCadeia.getTrechoCodigoFonte().getLinha(), noCadeia.getTrechoCodigoFonte().getColuna() + 1 + start, codigo));
//        }

        String[] cadeia = noCadeia.getValorOriginal().split("");
        Boolean previousEscape = false;
        for (int i = 0; i<cadeia.length; i++) {
            if(!previousEscape && cadeia[i].matches("\\\\"))
            {
                previousEscape = true;
            }
            else if(previousEscape && cadeia[i].matches("[tnbrf\\\"\\\\]"))
            {
                previousEscape = false;
            }
            else if(previousEscape && !cadeia[i].matches("[tnbrf\\\"\\\\]"))
            {
                previousEscape = false;
                notificarErroSintatico(new ErroEscapeUnico(noCadeia.getTrechoCodigoFonte().getLinha(), noCadeia.getTrechoCodigoFonte().getColuna() - 1 + i, cadeia[i]));
            }
            if(cadeia[i].matches("\r?\n"))
            {
                notificarErroSintatico(new ErroLinhaPuladaEmString(noCadeia.getTrechoCodigoFonte().getLinha(), noCadeia.getTrechoCodigoFonte().getColuna(), noCadeia.getValorOriginal()));
            }
        }
        
        return super.visitar(noCadeia); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object visitar(NoCaracter noCaracter) throws ExcecaoVisitaASA {
        
//        Pattern pattern = Pattern.compile("(?<![\\\\])\\\\(?![tnbrf\"\\\\])");
//        Matcher matcher = pattern.matcher(""+noCaracter.getValor());
//        
//        while (matcher.find())
//        {
//            String codigo = matcher.group();
//            int start = matcher.start();
//            
//            notificarErroSintatico(new ErroEscapeUnico(noCaracter.getTrechoCodigoFonte().getLinha(), noCaracter.getTrechoCodigoFonte().getColuna() + 1 + start, codigo));
//        }
        
        return super.visitar(noCaracter); //To change body of generated methods, choose Tools | Templates.
    }
    
    
         
     private void notificarErroSintatico(ErroSintatico erroSintatico) {
        for (ObservadorAnaliseSintatica observador : observadores) {
            observador.tratarErroSintatico(erroSintatico);
        }
    }
     
       /**
     * Permite adicionar um observador à análise sintática. Os observadores
     * serão notificados sobre cada erro sintático encontrado no código fonte e
     * deverão tratá-los apropriadamente, exibindo-os em uma IDE, por exemplo.
     *
     * @param observadorAnaliseSintatica o observador da análise sintática a ser
     * registrado.
     * @since 1.0
     */
    public void adicionarObservador(ObservadorAnaliseSintatica observadorAnaliseSintatica) {
        if (!observadores.contains(observadorAnaliseSintatica)) {
            observadores.add(observadorAnaliseSintatica);
        }
    }

    /**
     * Remove um observador da análise previamente registrado utilizando o
     * método 
     * {@link AnalisadorSintatico#adicionarObservador(br.univali.portugol.nucleo.analise.sintatica.ObservadorAnaliseSintatica) }.
     * Uma vez removido, o observador não será mais notificado dos erros
     * sintáticos encontrados durante a análise.
     *
     * @param observadorAnaliseSintatica um observador de análise sintática
     * previamente registrado.
     * @since 1.0
     */
    public void removerObservador(ObservadorAnaliseSintatica observadorAnaliseSintatica) {
        observadores.remove(observadorAnaliseSintatica);
    }
    
}
