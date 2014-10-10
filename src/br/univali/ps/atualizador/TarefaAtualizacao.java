package br.univali.ps.atualizador;

import br.univali.ps.nucleo.PortugolStudio;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * @author Luiz Fernando Noschang
 */
final class TarefaAtualizacao
{
    private static final Logger LOGGER = Logger.getLogger(TarefaAtualizacao.class.getName());
    private static final int NUMERO_TENTATIVAS_DOWNLOAD = 3;    

    private static final String NOME_ARQUIVO_HASH = "hash";
    private static final String NOME_ARQUIVO_PACOTE = "pacote.zip";

    private final File caminhoInstalacao;
    private final File caminhoTemporario;
    private final CloseableHttpClient clienteHttp;

    private final File caminhoHashInstalacao;
    private final File caminhoHashTemporario;
    private final String caminhoHashRemoto;

    private final File caminhoPacoteTemporario;
    private final String caminhoPacoteRemoto;

    public TarefaAtualizacao(String caminhoRemoto, File caminhoInstalacao, File caminhoTemporario, CloseableHttpClient clienteHttp)
    {
        this.caminhoInstalacao = caminhoInstalacao;
        this.caminhoTemporario = caminhoTemporario;
        this.clienteHttp = clienteHttp;

        this.caminhoHashRemoto = caminhoRemoto.concat("/").concat(NOME_ARQUIVO_HASH);
        this.caminhoHashInstalacao = new File(caminhoInstalacao, NOME_ARQUIVO_HASH);
        this.caminhoHashTemporario = new File(caminhoTemporario, NOME_ARQUIVO_HASH);

        this.caminhoPacoteRemoto = caminhoRemoto.concat("/").concat(NOME_ARQUIVO_PACOTE);
        this.caminhoPacoteTemporario = new File(caminhoTemporario, NOME_ARQUIVO_PACOTE);
    }

    public void baixarAtualizacao() throws IOException, ZipException
    {
        if (precisaAtualizar())
        {
            caminhoTemporario.mkdirs();

            PortugolStudio.getInstancia().getGerenciadorAtualizacoes().verificarUriAtualizacao();            
            baixarPacote();
            
            PortugolStudio.getInstancia().getGerenciadorAtualizacoes().verificarUriAtualizacao();
            baixarHash();
            
            PortugolStudio.getInstancia().getGerenciadorAtualizacoes().verificarUriAtualizacao();
            descompactarPacote();

            caminhoPacoteTemporario.delete();
        }
    }

    public boolean precisaAtualizar() throws IOException
    {
        if (caminhoHashInstalacao.exists())
        {
            String hashRemoto = Util.obterHashRemoto(caminhoHashRemoto, clienteHttp);
            String hashPacoteInstalado = obterHashPacoteInstalado(caminhoHashInstalacao);

            return !hashPacoteInstalado.equals(hashRemoto);
        }

        return true;
    }

    private void baixarPacote() throws IOException
    {
        for (int tentativa = 1; tentativa <= NUMERO_TENTATIVAS_DOWNLOAD; tentativa++)
        {
            IOException erro = null;
            
            try
            {   
                Util.baixarArquivoRemoto(caminhoPacoteRemoto, caminhoPacoteTemporario, clienteHttp);
            }
            catch (IOException ex)
            {
                erro = ex;
            }
            
            if (erro == null && pacoteBaixadoComSucesso())
            {
                break;
            }
            else if (tentativa == NUMERO_TENTATIVAS_DOWNLOAD)
            {
                if (erro != null)
                {
                    throw new IOException(String.format("Erro ao baixar o pacote '%s' após %d tentativa(s)", caminhoPacoteTemporario.getAbsolutePath(), NUMERO_TENTATIVAS_DOWNLOAD), erro);
                }
                else
                {
                    throw new IOException(String.format("Erro ao baixar o pacote '%s' após %d tentativa(s)", caminhoPacoteTemporario.getAbsolutePath(), NUMERO_TENTATIVAS_DOWNLOAD));
                }
            }
        }
    }

    private boolean pacoteBaixadoComSucesso() throws IOException
    {
        String hashRemoto = Util.obterHashRemoto(caminhoHashRemoto, clienteHttp);
        String hashArquivo = Util.calcularHashArquivo(caminhoPacoteTemporario);

        return hashArquivo.equals(hashRemoto);
    }

    private void descompactarPacote() throws ZipException, IOException
    {
        ZipFile arquivoZip = new ZipFile(caminhoPacoteTemporario);
        arquivoZip.extractAll(caminhoPacoteTemporario.getParent());
    }

    private void baixarHash() throws IOException
    {
        Util.baixarArquivoRemoto(caminhoHashRemoto, caminhoHashTemporario, clienteHttp);
    }

    private String obterHashPacoteInstalado(File arquivoHash) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoHash)))
        {
            return reader.readLine();
        }
    }

    public File getCaminhoInstalacao()
    {
        return caminhoInstalacao;
    }

    public File getCaminhoTemporario()
    {
        return caminhoTemporario;
    }
}
