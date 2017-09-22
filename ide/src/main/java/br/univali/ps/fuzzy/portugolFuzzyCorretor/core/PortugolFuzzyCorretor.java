/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.ps.fuzzy.portugolFuzzyCorretor.core;

import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.asa.ASAPrograma;
import br.univali.portugol.nucleo.asa.ExcecaoVisitaASA;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import br.univali.ps.fuzzy.portugolFuzzyCorretor.view.TelaAdicionarDados;
import br.univali.ps.ui.telas.TelaCustomBorder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import org.antlr.stringtemplate.language.ActionEvaluator.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author 5674867
 */

public class PortugolFuzzyCorretor {
    
    private String codigoPortugol;
    private int numeroVariaveis;
    private int numeroFuncoes;
    private int numeroLinhas;
    private int numeroAvisosTrunc=0;
    private int numeroAvisosOcult=0;
    private int numeroAvisosConv=0;
    private int numeroErrosSint=0;
    private int numeroErrosSem=0;
    private List<AvisoAnalise> avisos;
    private List<ErroAnalise> erros;
    private static PortugolFuzzyCorretor instance = null;
    private String classe = "Anon";
    private String aluno = "101";
    TelaCustomBorder main = new TelaCustomBorder("Dados de usuário");
    TelaAdicionarDados adicionarDados = new TelaAdicionarDados();    
    
    private PortugolFuzzyCorretor() {
        this.codigoPortugol = "";
        main.setPanel(adicionarDados);
        main.setLocationRelativeTo(null);
    }
    
    public static PortugolFuzzyCorretor getInstance(){
        if(instance==null){
            instance= new PortugolFuzzyCorretor();
        }
        return instance;
    }
    
    public void setDados()
    {
        main.setVisible(true);
    }
    
    public void fecharTelaDados()
    {
        main.setVisible(false);
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }   
        
    public void setCode(String codigoPortugol) {
        this.codigoPortugol = codigoPortugol;
    }
    public String interpretarCodigo(String codigoPortugol) throws ExcecaoVisitaASA, ErroCompilacao{
        setCode(codigoPortugol);
        return interpretarCodigo();
    }
    
    public String interpretarCodigo() throws ExcecaoVisitaASA{
        StringBuilder mensagens = new StringBuilder();
        

        ResultadoAnalise analise;
        
        try{
            Programa program = Portugol.compilarParaAnalise(this.codigoPortugol);
            analise = program.getResultadoAnalise();
            ASAPrograma asap = program.getArvoreSintaticaAbstrata();
            ContadorVariaveis counter = new ContadorVariaveis(asap);
            this.numeroVariaveis = counter.locais + counter.globais;
            this.numeroFuncoes = counter.funcoes;
            this.numeroLinhas = contarNumeroLinhas();
        }catch( ErroCompilacao e){
            analise = e.getResultadoAnalise();
        } 
        
        mensagens.append("var: ").append(this.numeroVariaveis).append("\n");
        mensagens.append("func: ").append(this.numeroFuncoes).append("\n");
        mensagens.append("Linhas: ").append(this.numeroLinhas).append("\n");
        this.avisos = analise.getAvisos();
        this.erros = analise.getErros();
        numeroAvisosTrunc=0;
        numeroAvisosOcult=0;
        numeroAvisosConv=0;
        for (AvisoAnalise aviso : this.avisos) {
            if(aviso.getMensagem().contains("truncado")){
                numeroAvisosTrunc++;
            }
            if(aviso.getMensagem().contains("convertido")){
                numeroAvisosConv++;
            }
            if(aviso.getMensagem().contains("ocultando")){
                numeroAvisosOcult++;
            }
            mensagens.append(aviso.getMensagem()).append("\n");
        }
        
//        System.out.println(analise.getErrosSemanticos().size());
//        System.out.println(analise.getErrosSintaticos().size());
        
        numeroErrosSint+=analise.getErrosSintaticos().size();
        numeroErrosSem+=analise.getErrosSemanticos().size();
        for (ErroAnalise erro : this.erros) {
            mensagens.append(erro.getMensagem()).append("\n");
        }
        if(numeroAvisosConv>12){
            numeroAvisosConv=12;
        }
        if(numeroAvisosTrunc>12){
            numeroAvisosTrunc=12;
        }
        if(numeroAvisosOcult>12){
            numeroAvisosOcult=12;
        }
        if(this.numeroVariaveis>12){
            this.numeroVariaveis = 12;
        }
        if(this.numeroFuncoes>10){
            this.numeroFuncoes = 10;
        }
        if(this.numeroLinhas>112){
            this.numeroLinhas = 112;
        }
        return mensagens.toString();        
    }
    
    public void calcularFuzzy(File rules){
        
        
        FIS fis = FIS.load(rules.getPath(),true);
        if (fis == null){
            System.err.println("Opa");
            return;
        }
        System.out.println("sint: "+ numeroErrosSint);
        System.out.println("sem: "+ numeroErrosSem);
        System.out.println("conv: "+numeroAvisosConv);
        System.out.println("trunc: "+numeroAvisosTrunc);
        System.out.println("ocult: "+numeroAvisosOcult);
        System.out.println("var: "+numeroVariaveis);
        System.out.println("func: "+ numeroFuncoes);
        System.out.println("lin: "+numeroLinhas);
        String [] variaveisErro = {"erro_sintatico", "erro_semantico", "erros"};
        double [] valoresVariaveisErro = {numeroErrosSint,numeroErrosSem}; // Fazer atribuição de erros e avisos corretamente depois
        double errosFuzzy = calcularFuzzyBlock(fis.getFunctionBlock("erro_block"), variaveisErro , valoresVariaveisErro, false);
        
        String [] variaveisAviso = {"aviso_conversao", "aviso_truncacao", "aviso_ocultacao", "avisos"};
        double [] valoresVariaveisAviso = {numeroAvisosConv, numeroAvisosTrunc, numeroAvisosOcult}; // Fazer atribuição de erros e avisos corretamente depois
        double avisosFuzzy = calcularFuzzyBlock(fis.getFunctionBlock("aviso_block"), variaveisAviso, valoresVariaveisAviso, false);
        
        String [] variaveisProblemas = {"erros", "avisos", "problemas"};
        double [] valoresVariaveisProblemas = {errosFuzzy, avisosFuzzy};
        double problemas = calcularFuzzyBlock(fis.getFunctionBlock("problema_block"), variaveisProblemas, valoresVariaveisProblemas, false);
        
        String [] variaveisComplexidade = {"variaveis", "funcoes", "linhas", "complexidade"};
        double [] valoresVariaveisComplexidade = {this.numeroVariaveis, this.numeroFuncoes, this.numeroLinhas}; 
        double complexidade = calcularFuzzyBlock(fis.getFunctionBlock("complexidade_block"), variaveisComplexidade, valoresVariaveisComplexidade, false);

        String [] variaveisNecessidade = {"problemas", "complexidade", "necessidade"};
        double [] valoresVariaveisNecessidade = {problemas, complexidade}; 
        double necessidade = calcularFuzzyBlock(fis.getFunctionBlock("necessidade_block"), variaveisNecessidade, valoresVariaveisNecessidade, false);
        sendPost(""+necessidade);
        numeroErrosSint=0;
        numeroErrosSem=0;
    }

    public double calcularFuzzyBlock(FunctionBlock fb, String[] variaveisFuzzy, double[] valoresVariaveisFuzzy, boolean exibirChart){
        for (int i = 0; i < variaveisFuzzy.length - 1; i++) {
            fb.setVariable(variaveisFuzzy[i], valoresVariaveisFuzzy[i]);
        }
        fb.evaluate();
        fb.getVariable(variaveisFuzzy[variaveisFuzzy.length - 1]).defuzzify();
        System.out.println(variaveisFuzzy[variaveisFuzzy.length - 1] + " " + fb.getVariable(variaveisFuzzy[variaveisFuzzy.length - 1]).getValue());
        if(exibirChart){
            JFuzzyChart.get().chart(fb);
        }
        return fb.getVariable(variaveisFuzzy[variaveisFuzzy.length - 1]).getValue();
    }
    
    private int contarNumeroLinhas(){
        int linhas = 0;
        Scanner scanner = new Scanner(this.codigoPortugol); // Para possibilitar a edição do texto dentro do programa
                                                            // Caso não seja necessário, contabilizar dentro do FileController
        while (scanner.hasNextLine()){
          linhas++;
          scanner.nextLine();
        }
        scanner.close();
        return linhas;
    }
    
    private void sendPost(String score)
    {
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost("http://portugol-fuzzy.herokuapp.com/api/scores");

        // Request parameters and other properties.
        List<BasicNameValuePair> params = new ArrayList<>(3);
        params.add(new BasicNameValuePair("student", aluno));
        params.add(new BasicNameValuePair("class", classe));
        params.add(new BasicNameValuePair("score", score));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    // do something useful
                } finally {
                    instream.close();
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PortugolFuzzyCorretor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PortugolFuzzyCorretor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getNumeroFuncoes() {
        return numeroFuncoes;
    }

    public int getNumeroLinhas() {
        return numeroLinhas;
    }

    public int getNumeroVariaveis() {
        return numeroVariaveis;
    }

    public List<AvisoAnalise> getAvisos() {
        return avisos;
    }

    public List<ErroAnalise> getErros() {
        return erros;
    }
    
    public String getCodigoPortugol() {
        return codigoPortugol;
    }
    
}
