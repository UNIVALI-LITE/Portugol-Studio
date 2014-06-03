package br.univali.ps.atualizador;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.nucleo.PortugolStudio;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class GerenciadorAtualizacoes
{
    private static final int INTERVALO_ENTRE_TENTATIVAS_ATUALIZACAO = 60000; // 1 minuto
    private static final int HTTP_OK = 200;

    private static final Logger LOGGER = Logger.getLogger(GerenciadorAtualizacoes.class.getName());
    private static final String uriAtualizacao = PortugolStudio.getInstancia().getUriAtualizacao();
    private static final ExecutorService servicoThread = Executors.newSingleThreadExecutor();

    private static final Map<String, String> caminhosInstalacao = criarMapaCaminhosInstalacao();
    private static final Map<String, String> caminhosRemotos = criarMapaCaminhosRemotos();
    private static final File caminhoScriptAtualizacaoLocal = new File(Configuracoes.getInstancia().getDiretorioInstalacao(), "atualizacao.script");
    private static final File caminhoScriptAtualizacaoTemporario = new File(Configuracoes.getInstancia().getDiretorioInstalacao(), "atualizacao.script.temp");

    private static final String caminhoScriptAtualizacaoRemoto = uriAtualizacao.concat("/atualizacao.script");
    private static final String caminhoHashScriptAtualizacaoRemoto = uriAtualizacao.concat("/atualizacao.hash");

    private ObservadorAtualizacao observadorAtualizacao;

    private static boolean executando = false;

    private static Map<String, String> criarMapaCaminhosInstalacao()
    {
        Configuracoes configuracoes = Configuracoes.getInstancia();
        Map<String, String> mapaCaminhosInstalacao = new HashMap<>();

        mapaCaminhosInstalacao.put("ajuda", configuracoes.getDiretorioAjuda().getAbsolutePath());
        mapaCaminhosInstalacao.put("exemplos", configuracoes.getDiretorioExemplos().getAbsolutePath());
        mapaCaminhosInstalacao.put("plugins", configuracoes.getDiretorioPlugins().getAbsolutePath());
        mapaCaminhosInstalacao.put("aplicacao", configuracoes.getDiretorioAplicacao().getAbsolutePath());
        mapaCaminhosInstalacao.put("bibliotecas", configuracoes.getDiretorioBibliotecas().getAbsolutePath());

        return mapaCaminhosInstalacao;
    }

    private static Map<String, String> criarMapaCaminhosRemotos()
    {
        Map<String, String> mapaCaminhosRemotos = new HashMap<>();

        mapaCaminhosRemotos.put("ajuda", uriAtualizacao.concat("/ajuda"));
        mapaCaminhosRemotos.put("exemplos", uriAtualizacao.concat("/exemplos"));
        mapaCaminhosRemotos.put("plugins", uriAtualizacao.concat("/plugins"));
        mapaCaminhosRemotos.put("bibliotecas", uriAtualizacao.concat("/bibliotecas"));
        mapaCaminhosRemotos.put("aplicacao", uriAtualizacao.concat("/aplicacao"));

        return mapaCaminhosRemotos;
    }

    public synchronized void baixarAtualizacoes()
    {
        if (!executando)
        {
            executando = true;
            servicoThread.execute(new Atualizacao());
        }
    }

    public void setObservadorAtualizacao(ObservadorAtualizacao observadorAtualizacao)
    {
        this.observadorAtualizacao = observadorAtualizacao;
    }

    private final class Atualizacao implements Runnable
    {
        private final List<TarefaAtualizacao> tarefas = new ArrayList<>();

        private boolean houveAtualizacoes = false;
        private boolean houveFalhaAtualizacao = false;

        @Override
        public void run()
        {
            try (CloseableHttpClient clienteHttp = HttpClients.createDefault())
            {
                do
                {
                    try
                    {
                        houveFalhaAtualizacao = false;

                        criarTarefasAtualizacao(clienteHttp);
                        executarTarefasAtualizacao(clienteHttp);
                    }
                    catch (IOException | ZipException excecao)
                    {
                        capturarFalhaAtualizacao(excecao);
                    }
                }
                while (houveFalhaAtualizacao);
            }
            catch (IOException excecao)
            {
                LOGGER.log(Level.WARNING, String.format("Erro ao fechar o cliente HTTP: %s", excecao.getMessage()), excecao);
            }

            notificarConclusaoAtualizacao();
        }

        private void capturarFalhaAtualizacao(Throwable excecao)
        {
            houveFalhaAtualizacao = true;

            LOGGER.log(Level.WARNING, String.format("Erro ao atualizar o Portugol Studio: %s", excecao.getMessage()), excecao);

            try
            {
                Thread.sleep(INTERVALO_ENTRE_TENTATIVAS_ATUALIZACAO); // Aguarda alguns segundos antes da próxima tentativa
            }
            catch (InterruptedException ex)
            {

            }
        }

        private void notificarConclusaoAtualizacao()
        {
            if (observadorAtualizacao != null && houveAtualizacoes)
            {
                observadorAtualizacao.atualizacaoConcluida();
            }
        }

        private void executarTarefasAtualizacao(CloseableHttpClient clienteHttp) throws IOException, ZipException
        {
            houveAtualizacoes = false;

            prepararDiretorioTemporario();

            for (TarefaAtualizacao tarefa : tarefas)
            {
                if (tarefa.precisaAtualizar())
                {
                    tarefa.baixarAtualizacao();
                    houveAtualizacoes = true;
                }
            }

            if (houveAtualizacoes)
            {
                Util.baixarArquivoRemoto(caminhoScriptAtualizacaoRemoto, caminhoScriptAtualizacaoTemporario, clienteHttp);

                validarScriptAtualizacao(clienteHttp);
            }
        }

        private void validarScriptAtualizacao(CloseableHttpClient clienteHttp) throws IOException
        {
            try
            {
                String hashAtualizacaoLocal = Util.calcularHashArquivo(caminhoScriptAtualizacaoTemporario);
                String hashAtualizacaoRemoto = Util.obterHashRemoto(caminhoHashScriptAtualizacaoRemoto, clienteHttp);

                if (!hashAtualizacaoLocal.equals(hashAtualizacaoRemoto))
                {
                    throw new IOException("O script de atualização do Portugol Studio não foi baixado corretamente");
                }
                else
                {
                    caminhoScriptAtualizacaoTemporario.renameTo(caminhoScriptAtualizacaoLocal);
                }
            }
            catch (IOException excecao)
            {
                FileUtils.deleteQuietly(caminhoScriptAtualizacaoTemporario);
                
                throw excecao;
            }
        }

        private void criarTarefasAtualizacao(CloseableHttpClient clienteHttp)
        {
            tarefas.clear();

            criarTarefaAtualizacaoRecurso("ajuda", clienteHttp);
            criarTarefaAtualizacaoRecurso("exemplos", clienteHttp);
            criarTarefaAtualizacaoRecurso("aplicacao", clienteHttp);

            criarTarefasAtualizacaoPlugins(clienteHttp);
        }

        private void criarTarefaAtualizacaoRecurso(String recurso, CloseableHttpClient clienteHttp)
        {
            String caminhoRemoto = caminhosRemotos.get(recurso);
            File caminhoInstalacao = new File(caminhosInstalacao.get(recurso));
            File caminhoTemporario = new File(Configuracoes.getInstancia().getDiretorioTemporario(), recurso);

            tarefas.add(new TarefaAtualizacao(caminhoRemoto, caminhoInstalacao, caminhoTemporario, clienteHttp));
        }

        private void criarTarefasAtualizacaoPlugins(CloseableHttpClient clienteHttp)
        {
            String uriAutoInstalacao = uriAtualizacao.concat("/").concat("plugins.auto");
            List<String> pluginsAtualizar = lerArquivoAutoInstalacao(uriAutoInstalacao, clienteHttp);

            File diretorioPlugins = Configuracoes.getInstancia().getDiretorioPlugins();

            if (diretorioPlugins.exists())
            {
                for (File caminho : diretorioPlugins.listFiles())
                {
                    if (!pluginsAtualizar.contains(caminho.getName()))
                    {
                        pluginsAtualizar.add(caminho.getName());
                    }
                }
            }

            for (String nomePlugin : pluginsAtualizar)
            {

                String caminhoRemoto = caminhosRemotos.get("plugins").concat("/").concat(nomePlugin);

                File caminhoInstalacao = new File(caminhosInstalacao.get("plugins"), nomePlugin);
                File caminhoTemporario = new File(new File(Configuracoes.getInstancia().getDiretorioTemporario(), "plugins"), nomePlugin);

                tarefas.add(new TarefaAtualizacao(caminhoRemoto, caminhoInstalacao, caminhoTemporario, clienteHttp));
            }
        }

        private void prepararDiretorioTemporario()
        {
            Configuracoes configuracoes = Configuracoes.getInstancia();
            File diretorioTemporario = configuracoes.getDiretorioTemporario();

            if (diretorioTemporario.exists())
            {
                FileUtils.deleteQuietly(diretorioTemporario);
            }

            if (caminhoScriptAtualizacaoLocal.exists())
            {
                FileUtils.deleteQuietly(caminhoScriptAtualizacaoLocal);
            }
            
            if (caminhoScriptAtualizacaoTemporario.exists())
            {
                FileUtils.deleteQuietly(caminhoScriptAtualizacaoTemporario);
            }
        }

        private List<String> lerArquivoAutoInstalacao(String uriArquivoAutoInstalacao, CloseableHttpClient clienteHttp)
        {
            List<String> entradas = new ArrayList<>();

            HttpGet httpGet = new HttpGet(uriArquivoAutoInstalacao);

            try (CloseableHttpResponse resposta = clienteHttp.execute(httpGet))
            {
                final int resultado = resposta.getStatusLine().getStatusCode();

                if (resultado == HTTP_OK)
                {
                    String linha;

                    try (InputStream is = resposta.getEntity().getContent(); BufferedReader leitor = new BufferedReader(new InputStreamReader(is)))
                    {
                        while ((linha = leitor.readLine()) != null)
                        {
                            linha = linha.trim();
                            
                            if (linha.length() > 0 && !linha.startsWith("#"))
                            {
                                entradas.add(linha);
                            }
                        }
                    }
                }
            }
            catch (IOException excecao)
            {

            }

            return entradas;
        }
    }
}
