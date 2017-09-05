package br.univali.portugol.ajuda;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class CarregadorAjuda
{
    private static final Pattern padraoTitulo = Pattern.compile("<title>(?<titulo>.+)</title>");
    
    private int indice;
    private ExtratorDadosHtml extrator;
    private List<ObservadorCarregamentoAjuda> observadores;
    private List<PreProcessadorConteudo> preProcessadores;
            
    public CarregadorAjuda()
    {
        extrator = new ExtratorDadosHtml();
        observadores = new ArrayList<>();
        preProcessadores = new ArrayList<>();        
    }
    
    public void adicionarObservadorCarregamento(ObservadorCarregamentoAjuda observador)
    {
        if (!observadores.contains(observador))
        {
            observadores.add(observador);
        }
    }

    public void removerObservadorCarregamento(ObservadorCarregamentoAjuda observador)
    {
        if (observadores.contains(observador))
        {
            observadores.remove(observador);
        }
    }
    
    public void adicionarPreProcessadorConteudo(PreProcessadorConteudo preProcessador)
    {
        if (!preProcessadores.contains(preProcessador))
        {
            preProcessadores.add(preProcessador);
        }
    }
    
    public void removerPreProcessadorConteudo(PreProcessadorConteudo preProcessador)
    {
        if (preProcessadores.contains(preProcessador))
        {
            preProcessadores.remove(preProcessador);
        }
    }
    
    private void notificarCarregamentoAjudaIniciado(int numeroTopicos)
    {
        for (ObservadorCarregamentoAjuda observador : observadores)
        {
            observador.carregamentoAjudaIniciado(numeroTopicos);
        }
    }
    
    private void notificarCarregamentoTopicoIniciado(int indiceTopico)
    {
        for (ObservadorCarregamentoAjuda observador : observadores)
        {
            observador.carregamentoTopicoIniciado(indiceTopico);
        }
    }
    
    private void notificarCarregamentoTopicoFinalizado(int indiceTopico)
    {
        for (ObservadorCarregamentoAjuda observador : observadores)
        {
            observador.carregamentoTopicoFinalizado(indiceTopico);
        }
    }
    
    private void notificarCarregamentoAjudaFinalizado()
    {
        for (ObservadorCarregamentoAjuda observador : observadores)
        {
            observador.carregamentoAjudaFinalizado();
        }
    }
    
    public Ajuda carregar(File caminho) throws ErroCarregamentoAjuda
    {
        if (caminhoValido(caminho))
        {
            File diretorioTopicos = new File(caminho.getPath(), "topicos");
        
            if (estruturaDiretoriosValida(diretorioTopicos))
            {
                notificarCarregamentoAjudaIniciado(contarTopicos(diretorioTopicos));
                Ajuda ajuda = carregarAjuda(diretorioTopicos);
                notificarCarregamentoAjudaFinalizado();
                
                return ajuda;
            }
        }
        
        return null;        
    }

    private boolean caminhoValido(File caminho) throws ErroCarregamentoAjuda
    {
        if (!caminho.exists()) throw new ErroCarregamentoAjuda(String.format("O caminho '%s' não existe", getNomeCaminho(caminho)));
        if (!caminho.isDirectory()) throw new ErroCarregamentoAjuda(String.format("O caminho '%s' não é um diretório", getNomeCaminho(caminho)));
        if (!caminho.canRead()) throw new ErroCarregamentoAjuda(String.format("O diretório '%s' não pode ser lido", getNomeCaminho(caminho)));
        if (!contemDiretorioTopicos(caminho)) throw new ErroCarregamentoAjuda(String.format("A pasta 'topicos' não foi encontrada no diretório '%s'", getNomeCaminho(new File(caminho.getPath(), "topicos"))));
        
        return true;
    }

    private boolean contemDiretorioTopicos(File caminho)
    {
        for (File filho : caminho.listFiles())
        {
            if (filho.getName().toLowerCase().equals("topicos"))
            {
                return true;
            }
        }
        
        return false;
    }
    
    private String getNomeCaminho(File caminho)
    {
        try
        {
            return caminho.getCanonicalPath();            
        }
        catch (Exception excecao)
        {
            return caminho.getAbsolutePath();
        }
    }

    private boolean estruturaDiretoriosValida(File diretorioTopicos) throws ErroCarregamentoAjuda
    {
        for (File filho : diretorioTopicos.listFiles())
        {
            validarDiretorio(filho);
        }
        
        return true;
    }
    
    private void validarDiretorio(File diretorio) throws ErroCarregamentoAjuda
    {
        boolean indexEncontrado = false;
        
        for (File filho : diretorio.listFiles())
        {
            if (filho.isDirectory())
            {
                validarDiretorio(filho);
            }
            else if (filho.isFile() && !indexEncontrado)
            {
                if (filho.getName().toLowerCase().equals("index.html"))
                {
                    indexEncontrado = true;
                }
            }
        }
        
        if (!indexEncontrado)
        {
            throw new ErroCarregamentoAjuda(String.format("Não foi possível carregar a ajuda, pois a estrutura de diretórios é inválida. O diretório '%s' deve conter um arquivo chamado 'index.html'", getNomeCaminho(diretorio)));
        }
    }
    
    private int contarTopicos(File diretorioTopicos)
    {
        int total = 0;
        
        for (File filho : diretorioTopicos.listFiles())
        {
            total = total + contarArquivos(filho);
        }
        
        return total;
    }
    
    private int contarArquivos(File caminho)
    {
        int total = 0;
        
        if (caminho.isDirectory())
        {
            for (File filho : caminho.listFiles())
            {
                total = total +  contarArquivos(filho);
            }
        } 
        else if (caminho.isFile())
        {
            total = total + 1;
        }
        
        return total;
    }
    
    private Ajuda carregarAjuda(File diretorioTopicos) throws ErroCarregamentoAjuda
    {
        indice = 0;
        
        Ajuda ajuda = new Ajuda();
                
        for (File filho : diretorioTopicos.listFiles())
        {
            incluirTopicos(filho, ajuda.getTopicos());
        }
        
        return ajuda;
    }
    
    private void incluirTopicos(File caminho, Topicos topicos) throws ErroCarregamentoAjuda
    {
        if (caminho.isDirectory())
        {
            Topico topico = carregarTopico(obterIndex(caminho));
            topicos.incluir(topico);
                
            for (File filho : caminho.listFiles())
            {
                incluirTopicos(filho, topico.getSubTopicos());                
            }
        }
        else if (caminho.isFile() && !caminho.getName().toLowerCase().equals("index.html"))
        {
            topicos.incluir(carregarTopico(caminho));
        }
    }
    
    private File obterIndex(File diretorio)
    {
        for (File filho : diretorio.listFiles())
        {
            if (filho.isFile() && filho.getName().toLowerCase().equals("index.html"))
            {
                return filho;
            }
        }
        
        return null;
    }
    
    private Topico carregarTopico(File arquivoHtml) throws ErroCarregamentoAjuda
    {
        indice = indice + 1;
        
        notificarCarregamentoTopicoIniciado(indice);
        
        String html = carregarHtml(arquivoHtml);
        
        extrator.extrair(html, getNomeCaminho(arquivoHtml));
                
        TopicoHtml topico = new TopicoHtml(arquivoHtml);
        
        topico.setTitulo(extrator.getTitulo());
        topico.setIcone(extrator.getIcone());
        topico.setConteudo(preProcessarConteudo(html, topico));
        topico.setArquivoOrigem(arquivoHtml);
        
        notificarCarregamentoTopicoFinalizado(indice);
        
        return topico;
    }
    
    private String preProcessarConteudo(String conteudo, Topico topico)
    {
        for (PreProcessadorConteudo preProcessador : preProcessadores)
        {
            conteudo = preProcessador.processar(conteudo, topico);
        }
        
        return conteudo;
    }
    
    private String carregarHtml(File arquivo) throws ErroCarregamentoAjuda
    {
        int caracteresLidos = 0;
        char[] buffer = new char[1048576]; // 1 MB
        StringBuilder html = new StringBuilder();        
        
        try
        {
            
            InputStream fluxoEntrada = new FileInputStream(arquivo);
            InputStreamReader leitor = new InputStreamReader(fluxoEntrada, "UTF-8");
            
            try
            {
                while ( ( caracteresLidos = leitor.read(buffer, 0, buffer.length)) > 0)
                {
                    html.append(buffer, 0, caracteresLidos);
                }
            }            
            finally
            {
                try { fluxoEntrada.close(); } catch (Exception excecao) { }
                try { leitor.close(); } catch (Exception excecao) { }
            }
        }
        catch (Exception excecao)
        {
            throw new ErroCarregamentoAjuda(String.format("Erro ao carregar o arquivo '%s' da ajuda: %s", getNomeCaminho(arquivo), excecao.getMessage()));
        }
        return html.toString();
    }
}
