package br.univali.portugol.corretor.dinamico;

import br.univali.portugol.corretor.dinamico.model.Caso;
import br.univali.portugol.corretor.dinamico.model.Entrada;
import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.portugol.corretor.dinamico.model.Saida;
import br.univali.portugol.nucleo.ErroCompilacao;
import br.univali.portugol.nucleo.Portugol;
import br.univali.portugol.nucleo.programa.*;
import br.univali.portugol.nucleo.asa.TipoDado;
import br.univali.portugol.nucleo.execucao.ObservadorExecucao;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.execucao.es.Armazenador;
import br.univali.portugol.nucleo.simbolos.Simbolo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Corretor de questões.<br>
 * Corrige algoritmos escritos no Portugol Studio usando o método executar.<br>
 *
 * @author
 */
public class Corretor {

    private Comparador comparador = new Comparador();
    private List<Entrada> entradasAtuais;
    private int indiceEntradaAtual;
    private List<Saida> saidasEncontras;
    private Questao questao;
    private List<CasoFalho> casosFalhos;
    private List<Caso> casosAcertados;
    private boolean execucaoFinalizada;
    private boolean erroTempoExecucao;
    private HashSet<String> mensagens;

    /**
     * Inicia uma instancia do corretor para corrigir uma Questão.
     *
     * @param questao Questão a ser avaliada.
     */
    public Corretor(Questao questao) {
        this.questao = questao;
    }

    public List<CasoFalho> getCasosFalhos() {
        return casosFalhos;
    }

    public List<Caso> getCasosAcertados() {
        return casosAcertados;
    }

    public Iterable<String> listarMensagens() {
        return mensagens;
    }

    /**
     * Executa o código fonte passado por parametro de acordo com o PEX que foi
     * passado para esta instancia na construção dela.
     *
     * @param codigoFonte código fonte portugol a ser corrigido
     * @param parametros ...
     * @return uma nota baseado em quantos casos de teste foram respondidos.<br>
     * 10 = Todos passaram<br>
     * 1 - 9 = Alguns passaram outros não.<br>
     * 0 = Nenhum teste passou.
     * @throws ErroCompilacao
     */
    public int executar(String codigoFonte, String[] parametros) throws ErroCompilacao {
        //Inicia as listas.
        List<Caso> casosDeTeste = new ArrayList();
        casosFalhos = new ArrayList<CasoFalho>();
        casosAcertados = new ArrayList<Caso>();
        mensagens = new HashSet<String>();
        for (Caso caso : questao.getCasos()) {
            casosDeTeste.add(caso);
        }
        //Compila o código fonte.
        Programa programa = null;
        try {
            programa = Portugol.compilarParaAnalise(codigoFonte);
        } catch (ErroCompilacao ex) {
            //E se der erro de compilação cospe a exceção na cara
            mensagens.add(ex.getMensagem());
            throw ex;
        }
        //Não sei o porque mas não sou eu que vou mecher com as forças misteriosa do desconhecido.
        if (programa == null) {
            return 0;
        }
        //Para cada caso agora vai começar a fazer as verificações se acertou ou errou o caso
        for (Caso caso : questao.getCasos()) {
            corrigirCaso(programa, parametros, caso);
        }

        int total = questao.getCasos().size();
        int acertados = questao.getCasos().size() - casosFalhos.size();

        int nota = acertados * 10 / total;

        if (nota == 10) {
            mensagens.add("Parabéns!");
        }

        return nota;
    }

    private void corrigirCaso(Programa programa, String[] parametros, Caso caso) {
        //Reset
        execucaoFinalizada = false;
        erroTempoExecucao = false;
        saidasEncontras = new ArrayList<Saida>();
        entradasAtuais = caso.getEntradas();
        //?
        for (Entrada entrada : entradasAtuais) {
            entrada.reiniciarContador();
        }
        indiceEntradaAtual = 0;
        //Executa um programa portugol.
        programa.setEntrada(new OuvinteEntrada());
        programa.setSaida(new OvinteSaida());
        programa.adicionarObservadorExecucao(new OuvinteExecucao());
        programa.executar(parametros, Programa.Estado.BREAK_POINT);
        //Se demorar demais ai ele corta a execução <- Meio dramático não ?
        long inicioexecucao = System.currentTimeMillis();
        while (!execucaoFinalizada) {
            try {
                Thread.sleep(250);
                long tempoAtual = System.currentTimeMillis();
                if (tempoAtual - inicioexecucao > (1000 * 60)) {
                    mensagens.add("A sua resposta está demorando muito para executar, verifique se não há algum laço de repetição infinito.");
                    programa.interromper();
                    casosFalhos.add(new CasoFalho(caso, null));
                    return;
                }
            } catch (InterruptedException ex) {

            }
        }
        //Erro tempo de execução
        //Programa pede mais entradas do que o solicitado.
        if (erroTempoExecucao) {
            mensagens.add("Ocorreu um erro em tempo de execução (Runtime Error), verifique se eu programa espera todas as entradas.");
            casosFalhos.add(new CasoFalho(caso, null));
            return;
        }
        //Programa pede menos entradas do que o solicitado.
        if (caso.getEntradas().size() > indiceEntradaAtual) {
            mensagens.add("Ao corrigir a resposta, algumas entradas não foram solicitadas, verifique se a quantidade de chamadas ao comando leia condiz com o enunciado.");
            casosFalhos.add(new CasoFalho(caso, null));
            return;
        }
        //Programa emite mais saidas do que o solicitado.
        if (caso.getSaidas().size() < saidasEncontras.size()) {
            mensagens.add("Seu programa emetiu mais saídas (comando escreva) do que o esperado.");
            casosFalhos.add(new CasoFalho(caso, null));
            return;
        }
        //Programa emite menos saidas do que o solicitado.
        if (caso.getSaidas().size() > saidasEncontras.size()) {
            mensagens.add("Seu programa emetiu menos saídas (comando escreva) do que o esperado.");
            casosFalhos.add(new CasoFalho(caso, null));
            return;
        }
        //Pega as saidas esperadas e compara tudo.
        final List<Saida> saidasEsperadas = caso.getSaidas();

        for (int i = 0; i < saidasEsperadas.size(); i++) {
            Object valorEsperado = saidasEsperadas.get(i).getValorSaida();
            Object valorEncontrado = null;
            List<Saida> saidasEncontradasTipoCorreto = new ArrayList<Saida>();

            if (saidasEncontras.get(i).getTipodado().equals(saidasEsperadas.get(i).getTipodado())) {
                Saida saidaEncontrada = saidasEncontras.get(i);
                if (!saidaEncontrada.isVisualizada()) {
                    valorEncontrado = saidaEncontrada.getValorSaida();
                    saidaEncontrada.setVisualizada();
                    saidasEncontradasTipoCorreto.add(saidaEncontrada);
                }
            } else {
                mensagens.add("Uma saída esperada deveria ser do tipo " + saidasEsperadas.get(i).getTipodado() + " mas foi recebido o tipo " + saidasEncontras.get(i).getTipodado());
                casosFalhos.add(new CasoFalho(caso, null));
                return;
            }

            if (saidasEncontradasTipoCorreto.isEmpty() || valorEncontrado == null) {
                mensagens.add("Nenhuma saída foi contabilizada. Verifique se foi utilizado o comando escreva e se os tipos das variáveis utilizadas nas chamadas ao comando escreva condizem com o enunciado.");
                casosFalhos.add(new CasoFalho(caso, null));
                return;
            } else if (!comparador.afirmarIgualdade(valorEsperado, valorEncontrado)) {
                saidasEncontradasTipoCorreto.clear();
                for (int c = 0; c < saidasEncontras.size(); c++) {
                    Saida saida = saidasEncontras.get(c);
                    if (saida.getTipodado().equals(saidasEsperadas.get(c).getTipodado())) {
                        saidasEncontradasTipoCorreto.add(saida);
                    }
                }
                if (saidasEncontradasTipoCorreto.size() < saidasEsperadas.size()) {
                    mensagens.add("Ao corrigir a resposta, algumas saidas não foram solicitadas, verifique se a quantidade de chamadas ao comando escreva condiz com o enunciado.");
                }
                casosFalhos.add(new CasoFalho(caso, saidasEncontradasTipoCorreto));
                return;
            }
            casosAcertados.add(caso);
        }
    }

    private class OuvinteExecucao implements ObservadorExecucao {

        @Override
        public void execucaoIniciada(Programa programa) {
            execucaoFinalizada = false;
            indiceEntradaAtual = 0;
        }

        @Override
        public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao) {
            execucaoFinalizada = true;
            if (resultadoExecucao.getErro() != null) {
                erroTempoExecucao = true;
            }
        }

        @Override
        public void execucaoResumida() {
            
        }

        @Override
        public void execucaoPausada() {

        }

        @Override
        public void highlightLinha(int linha) {
            //Não há necessidade de implementar
        }

        @Override
        public void highlightDetalhadoAtual(int linha, int coluna, int tamanho) {
            //Não há necessidade de implementar
        }

    }

    private class OuvinteEntrada implements br.univali.portugol.nucleo.execucao.es.Entrada {

        @Override
        public void solicitaEntrada(TipoDado tipoDado, Armazenador armazenador) throws InterruptedException {
            if(indiceEntradaAtual >= entradasAtuais.size()){
                armazenador.cancelarLeitura();
                mensagens.add("O seu programa pede mais entradas (comando leia) do que o necessário.");
                return;
            }
            Entrada entrada = entradasAtuais.get(indiceEntradaAtual);
            if (!entrada.isVetor() || (entrada.isVetor() && entrada.isUltimoElementoVetor())) {
                indiceEntradaAtual++;
            }
            Object valorEntrada = entrada.getValorEntrada();
            //System.out.println("Entrada: "+valorEntrada);

            try {
                switch (tipoDado) {
                    case INTEIRO:
                        armazenador.setValor((Integer) valorEntrada);
                        break;
                    case LOGICO:
                        armazenador.setValor((Boolean) valorEntrada);
                        break;
                    case REAL:
                        armazenador.setValor((Double) valorEntrada);
                        break;
                    case CARACTER:
                        armazenador.setValor((Character) valorEntrada);
                        break;
                    case CADEIA:
                        armazenador.setValor((String) valorEntrada);
                        break;
                    default:
                        armazenador.setValor(tipoDado.getValorPadrao());
                }
            } catch (ClassCastException cce) {
                armazenador.cancelarLeitura();
                mensagens.add("Houve uma incompatibilidade de tipos na entrada de dados, verifique se os tipos das variáveis utilizadas nas chamadas ao comando leia condizem com o enunciado.");
            }

        }

    }

    private class OvinteSaida implements br.univali.portugol.nucleo.execucao.es.Saida {

        @Override
        public void limpar() throws InterruptedException {
        }

        @Override
        public void escrever(String valor) throws InterruptedException {
            //System.out.println("Saida: "+valor);
            Saida saidaEncontrada = new Saida();
            saidaEncontrada.setValor(valor);
            saidaEncontrada.setTipodado("cadeia");
            saidasEncontras.add(saidaEncontrada);
        }

        @Override
        public void escrever(boolean valor) throws InterruptedException {
            //System.out.println("Saida: "+valor);
            Saida saidaEncontrada = new Saida();
            if (valor) {
                saidaEncontrada.setValor("verdadeiro");
            } else {
                saidaEncontrada.setValor("falso");
            }
            saidaEncontrada.setTipodado("logico");
            saidasEncontras.add(saidaEncontrada);
        }

        @Override
        public void escrever(int valor) throws InterruptedException {
            //System.out.println("Saida: "+valor);
            Saida saidaEncontrada = new Saida();
            saidaEncontrada.setValor(Integer.toString(valor));
            saidaEncontrada.setTipodado("inteiro");
            saidasEncontras.add(saidaEncontrada);
        }

        @Override
        public void escrever(double valor) throws InterruptedException {
            //System.out.println("Saida: "+valor);
            Saida saidaEncontrada = new Saida();
            saidaEncontrada.setValor(Double.toString(valor));
            saidaEncontrada.setTipodado("real");
            saidasEncontras.add(saidaEncontrada);
        }

        @Override
        public void escrever(char valor) throws InterruptedException {
            //System.out.println("Saida: "+valor);
            Saida saidaEncontrada = new Saida();
            saidaEncontrada.setValor(Character.toString(valor));
            saidaEncontrada.setTipodado("caracter");
            saidasEncontras.add(saidaEncontrada);
        }

    }
}
