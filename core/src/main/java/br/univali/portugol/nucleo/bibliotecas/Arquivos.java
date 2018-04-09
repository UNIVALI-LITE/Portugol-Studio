package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.programa.Programa;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.awt.KeyboardFocusManager;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Luiz Fernando Noschang
 */
@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca permite ler e escrever arquivos",
        versao = "1.3"
)
public final class Arquivos extends Biblioteca
{
    private static enum ModoAcesso
    {
        LEITURA, ESCRITA, ACRESCENTAR
    }

    private static final int NUMERO_MAXIMO_ARQUIVOS = 10;

    private Programa programa;
    private Arquivo[] arquivos;
    private DialogoSelecaoArquivo dialogoSelecao;
    private FiltroExtensao filtroTodosArquivos;

    @DocumentacaoConstante(descricao = "indica à biblioteca que o arquivo deve ser aberto apenas para leitura")
    public static final int MODO_LEITURA = ModoAcesso.LEITURA.ordinal();

    @DocumentacaoConstante(descricao = "indica à biblioteca que o arquivo deve ser aberto apenas para escrita")
    public static final int MODO_ESCRITA = ModoAcesso.ESCRITA.ordinal();
    
    @DocumentacaoConstante(descricao = "indica à biblioteca que o arquivo deve ser aberto apenas para escrita que acrescenta ao final do arquivo")
    public static final int MODO_ACRESCENTAR = ModoAcesso.ACRESCENTAR.ordinal();

    @DocumentacaoFuncao(
            descricao = "Abre um arquivo para leitura ou escrita. No modo leitura, caso o arquivo informado não exista, será gerado "
            + "um erro. No modo escrita, caso o arquivo informado não exista, ele tentará ser criado, se a criação do arquivo "
            + "falhar, então será gerado um erro.<br><br>"
            + "<b>IMPORTANTE:</b> ao abrir o arquivo no modo de escrita, o conteúdo do arquivo é apagado para que o novo conteúdo "
            + "seja escrito. Caso seja necessário manter o conteúdo atual do arquivo, deve-se armazená-lo em uma variável e depois "
            + "escrevê-lo novamente no arquivo.",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_arquivo", descricao = "o nome do arquivo que se quer abrir")
                ,

                @DocumentacaoParametro(nome = "modo_acesso", descricao = "determina se o arquivo será aberto para leitura ou para escrita.<br>Constantes aceitas: MODO_LEITURA | MODO_ESCRITA | MODO_ACRESCENTAR")
            },
            retorno = "o endereço de memória onde o arquivo foi carregado",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public int abrir_arquivo(String caminho_arquivo, int modo_acesso) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File arquivo = programa.resolverCaminho(new File(caminho_arquivo));

        if (!arquivoAberto(arquivo))
        {
            if (modo_acesso >= 0 && modo_acesso <= ModoAcesso.values().length)
            {
                int indice = obterProximoIndiceLivre();

                arquivos[indice] = new Arquivo(arquivo);
                arquivos[indice].abrir(ModoAcesso.values()[modo_acesso]);

                return indice;
            }

            throw new ErroExecucaoBiblioteca(String.format("Modo de acesso inválido: %d", modo_acesso));
        }

        throw new ErroExecucaoBiblioteca(String.format("O arquivo '%s' já está aberto", arquivo.getAbsolutePath()));
    }

    @DocumentacaoFuncao(
            descricao = "Fecha um arquivo aberto anteriormente",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do arquivo")
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void fechar_arquivo(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Arquivo arquivo = obterArquivo(endereco);
        arquivo.fechar();

        arquivos[endereco] = null;
    }

    @DocumentacaoFuncao(
            descricao = "Verifica se o arquivo chegou ao fim, isto é, se todas as linhas já foram lidas. Esta função só é executada se o arquivo "
            + "estiver aberto em modo de leitura. Se o arquivo estiver em modo de escrita, será gerado um erro.",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do arquivo")
            },
            retorno = "<tipo>verdadeiro</tipo> se o arquivo tiver chegado ao fim. Caso contrário retorna <tipo>falso</tipo>",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public boolean fim_arquivo(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return obterArquivo(endereco).fim();
    }

    @DocumentacaoFuncao(
            descricao = "Lê a próxima linha do arquivo. Esta função só é executada se o arquivo estiver aberto em modo de "
            + "leitura. Se o arquivo estiver em modo de escrita, será gerado um erro.",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do arquivo")
            },
            retorno = "Uma <tipo>cadeia</tipo> contendo o conteudo da linha lida.",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public String ler_linha(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return obterArquivo(endereco).ler();
    }

    @DocumentacaoFuncao(
            descricao = "Escreve uma linha no arquivo. Esta função só é executada se o arquivo estiver aberto em modo de "
            + "escrita. Se o arquivo estiver em modo de leitura, será gerado um erro.",
            parametros =
            {
                @DocumentacaoParametro(nome = "linha", descricao = "a linha a ser escrita no arquivo")
                ,
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do arquivo")
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void escrever_linha(String linha, int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        obterArquivo(endereco).escrever(linha);
    }
    
    @DocumentacaoFuncao(
            descricao = "Pesquisa por um determinado texto no arquivo e substitui todas as ocorrências por um texto alternativo",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço do arquivo")
                ,
                @DocumentacaoParametro(nome = "texto_pesquisa", descricao = "o texto que será pesquisado no arquivo")
                ,
                @DocumentacaoParametro(nome = "texto_substituto", descricao = "o texto pelo qual as ocorrências serão substituídas")
                ,
                @DocumentacaoParametro(nome = "primeira_ocorrencia", descricao = "confirma se substituirá apenas a primeira ocorrência no texto, caso contrário, substituirá todas")
            },
            autores =
            {
                @Autor(nome = "Adson Marques da Silva Esteves", email = "shiandson@gmail.com")
            }
    )
    public void substituir_texto(String endereco, String texto_pesquisa, String texto_substituto, boolean onlyFirst) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File arquivo = programa.resolverCaminho(new File(endereco));
        Path path = Paths.get(arquivo.toURI());
        String charset = "ISO-8859-1";
        String text;
        try {
            text = new String(Files.readAllBytes(path), charset);
        
            if(onlyFirst)
            {
                text = text.replaceFirst(texto_pesquisa, texto_substituto);
            }
            else
            {
                text = text.replaceAll(texto_pesquisa, texto_substituto);
            }            
            Files.write(path, text.getBytes(charset));
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca(String.format("Não foi possível substituir no arquivo '%s'", arquivo.getAbsolutePath()));
        } 
    }

    @DocumentacaoFuncao(
            descricao = "Verifica se um determinado arquivo existe no sistema de arquivos",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_arquivo", descricao = "o caminho do arquivo que se quer verificar")
            },
            retorno = "<tipo>verdadeiro</tipo> se o arquivo existir",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public boolean arquivo_existe(String caminho_arquivo) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File arquivo = programa.resolverCaminho(new File(caminho_arquivo));

        return arquivo.isFile() && arquivo.exists();
    }

    @DocumentacaoFuncao(
            descricao = "Remove um arquivo do sistema de arquivos",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_arquivo", descricao = "o caminho do arquivo que ser quer apagar")
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void apagar_arquivo(String caminho_arquivo) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File arquivo = programa.resolverCaminho(new File(caminho_arquivo));

        try
        {
            if (arquivo.isFile())
            {
                Files.delete(arquivo.toPath());
            }
            else
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível apagar o arquivo '%s'", arquivo.getAbsolutePath()));
            }
        }
        catch (IOException excecao)
        {
            throw new ErroExecucaoBiblioteca(String.format("Não foi possível apagar o arquivo '%s'", arquivo.getAbsolutePath()));
        }
    }

    @Override
    public void inicializar(Programa programa, List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException
    {
        this.programa = programa;
        this.arquivos = new Arquivo[NUMERO_MAXIMO_ARQUIVOS];
    }

    @Override
    public void finalizar() throws ErroExecucaoBiblioteca, InterruptedException
    {
        for (int indice = NUMERO_MAXIMO_ARQUIVOS - 1; indice >= 0; indice--)
        {
            if (arquivos[indice] != null)
            {
                arquivos[indice].fechar();
                arquivos[indice] = null;
            }
        }
    }

    private Arquivo obterArquivo(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (endereco >= 0 && endereco < NUMERO_MAXIMO_ARQUIVOS)
        {
            Arquivo arquivo = arquivos[endereco];

            if (arquivo != null)
            {
                return arquivo;
            }
        }

        throw new ErroExecucaoBiblioteca("O endereço de memória especificado não aponta para um arquivo");
    }

    private boolean arquivoAberto(File arq)
    {
        String caminho = arq.getAbsolutePath();

        for (int indice = NUMERO_MAXIMO_ARQUIVOS - 1; indice >= 0; indice--)
        {
            Arquivo arquivo = arquivos[indice];

            if (arquivo != null && arquivo.getCaminho().equals(caminho))
            {
                return true;
            }
        }

        return false;
    }

    private int obterProximoIndiceLivre() throws ErroExecucaoBiblioteca, InterruptedException
    {
        for (int indice = NUMERO_MAXIMO_ARQUIVOS - 1; indice >= 0; indice--)
        {
            if (arquivos[indice] == null)
            {
                return indice;
            }
        }

        throw new ErroExecucaoBiblioteca("O número máximo de arquivos que podem ser abertos ao mesmo tempo foi atingido");
    }

    @DocumentacaoFuncao(
            descricao = "Abre um janela que permite ao usuário navegar nos diretórios do computador e selecionar um arquivo",
            parametros =
            {
                @DocumentacaoParametro(nome = "formatos_suportados",
                        descricao = "Define os formatos de arquivos que poderão ser selecionados. Um formato de "
                        + "arquivo é formado por uma descrição e uma lista de extensões válidas. A descrição deve "
                        + "estar separada da lista de extensões pelo caracter '|' e cada extensão deverá estar "
                        + "separada da outra pelo caracter ','. Ex.: 'Arquivos de texto|txt', 'Arquivos de imagem|png,jpg,jpeg,bmp'"
                )
                ,
                @DocumentacaoParametro(nome = "aceitar_todos_arquivos",
                        descricao = "Quando verdadeiro, inclui automaticamente um formato que permite selecionar "
                        + "qualquer arquivo. Este formato também será incluído se nenhum outro formato for informado "
                        + "no parâmetro 'formatos_suportados'"
                )
            },
            retorno = "O arquivo_selecionado ou uma string vazia caso o usuário tenha cancelado.",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
                ,
                @Autor(nome = "Elieser A. de Jesus", email = "elieser@univali.br")

            }
    )
    public String selecionar_arquivo(final String formatos_suportados[], final boolean aceitar_todos_arquivos) throws ErroExecucaoBiblioteca, InterruptedException
    {
        synchronized (Arquivos.this)
        {
            final ResultadoSelecao resultadoSelecao = new ResultadoSelecao();

            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    LookAndFeel previousLF = UIManager.getLookAndFeel();
                    try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        JFileChooser dialogo = obterDialogoSelecao();
                        List<FileFilter> filtros = criarFiltros(formatos_suportados);

                        for (FileFilter filtro : dialogo.getChoosableFileFilters())
                        {
                            dialogo.removeChoosableFileFilter(filtro);
                        }

                        for (FileFilter filtro : filtros)
                        {
                            dialogo.addChoosableFileFilter(filtro);
                        }

                        if (aceitar_todos_arquivos || formatos_suportados.length == 0)
                        {
                            dialogo.addChoosableFileFilter(obterFiltroTodosArquivos());
                        }

                        Window janelaPai = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();

                        if (dialogo.showDialog(janelaPai, null) == JFileChooser.APPROVE_OPTION)
                        {
                            
                            String arquivoSelecionado = obterCaminhoArquivo(dialogo.getSelectedFile());
                            resultadoSelecao.setArquivoSelecionado(arquivoSelecionado);
                        }
                        UIManager.setLookAndFeel(previousLF);
                        synchronized (Arquivos.this)
                        {
                            Arquivos.this.notifyAll();
                        }
                    }
                    catch (ErroExecucaoBiblioteca excecao)
                    {
                        throw new RuntimeException(excecao);
                    }
                    catch (ClassNotFoundException ex)
                    {
                        Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (InstantiationException ex)
                    {
                        Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (IllegalAccessException ex)
                    {
                        Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    catch (UnsupportedLookAndFeelException ex)
                    {
                        Logger.getLogger(Arquivos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            wait();

            return resultadoSelecao.getArquivoSelecionado();
        }
    }

    @DocumentacaoFuncao(descricao = "Altera um vetor para que ele represente as pastas existentes em um diretório",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_pai",
                        descricao = "Define o diretório cujas pastas serão listadas"
                )
                ,
                @DocumentacaoParametro(nome = "vetor_pastas",
                        descricao = "Vetor destino que contará com as pastas encontradas"
                )
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
                ,
                @Autor(nome = "Alisson Steffens", email = "noschang@univali.br")
            }
    )
    public void listar_pastas(final String caminho_pai, final String[] vetor_pastas) throws ErroExecucaoBiblioteca, InterruptedException
    {
        int indice = 0;
        File diretorio = programa.resolverCaminho(new File(caminho_pai));

        if (diretorio.exists())
        {
            File[] pastas = diretorio.listFiles();

            if (pastas.length > vetor_pastas.length)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível listar as pastas pois o vetor passado é muito pequeno. O diretório escolhido possui %d pastas, mas o vetor passado comporta apenas %d elementos. Aumente o tamanho do vetor ou escolha outro diretório para listar as pastas.", pastas.length, vetor_pastas.length));
            }

            for (File pasta : pastas)
            {
                if (pasta.isDirectory())
                {
                    vetor_pastas[indice] = pasta.getName();
                    indice++;
                }
            }

            for (int i = indice; i < vetor_pastas.length; i++)
            {
                vetor_pastas[i] = "";
            }
        }
        else
        {
            throw new ErroExecucaoBiblioteca(caminho_pai + " não é um caminho possível");
        }
    }

    @DocumentacaoFuncao(descricao = "Altera um vetor para que ele represente os arquivos existentes em um diretório",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_pai",
                        descricao = "Define o diretório cujas pastas serão listadas"
                )
                ,
                @DocumentacaoParametro(nome = "vetor_arquivos",
                        descricao = "Vetor destino que contará com as pastas encontradas"
                )
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
                ,
                @Autor(nome = "Alisson Steffens", email = "noschang@univali.br")
            }
    )
    public void listar_arquivos(final String caminho_pai, final String[] vetor_arquivos) throws ErroExecucaoBiblioteca, InterruptedException
    {
        int indice = 0;
        File diretorio = programa.resolverCaminho(new File(caminho_pai));

        if (diretorio.exists())
        {
            File[] arquivos_listados = diretorio.listFiles();

            if (arquivos_listados.length > vetor_arquivos.length)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível listar os arquivos pois o vetor passado é muito pequeno. O diretório escolhido possui %d arquivos, mas o vetor passado comporta apenas %d elementos. Aumente o tamanho do vetor ou escolha outro diretório para listar os arquivos.", arquivos_listados.length, vetor_arquivos.length));
            }

            for (File arquivo : arquivos_listados)
            {
                if (arquivo.isFile())
                {
                    vetor_arquivos[indice] = arquivo.getName();
                    indice++;
                }
            }

            for (int i = indice; i < vetor_arquivos.length; i++)
            {
                vetor_arquivos[i] = "";
            }
        }
        else
        {
            throw new ErroExecucaoBiblioteca(caminho_pai + " não é um caminho possível");
        }
    }

    @DocumentacaoFuncao(descricao = "Altera um vetor para que ele represente os arquivos existentes em um diretório",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_pai",
                        descricao = "Define o diretório cujas pastas serão listadas"
                )
                ,
                @DocumentacaoParametro(nome = "vetor_arquivos",
                        descricao = "Vetor destino que contará com as pastas encontradas"
                )
                ,
                @DocumentacaoParametro(nome = "vetor_tipos",
                        descricao = "Vetor destino que contará com as pastas encontradas"
                )
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
                ,
                @Autor(nome = "Alisson Steffens", email = "noschang@univali.br")
            }
    )
    public void listar_arquivos_por_tipo(final String caminho_pai, final String[] vetor_arquivos, final String[] vetor_tipos) throws ErroExecucaoBiblioteca, InterruptedException
    {
        int indice = 0;
        File diretorio = programa.resolverCaminho(new File(caminho_pai));

        if (diretorio.exists())
        {
            File[] arquivos_listados = diretorio.listFiles(new java.io.FileFilter()
            {
                @Override
                public boolean accept(File pathname)
                {
                    for (String vetor_tipo : vetor_tipos)
                    {
                        if (pathname.toString().toLowerCase().endsWith(vetor_tipo.toLowerCase()))
                        {
                            return true;
                        }
                    }

                    return false;

                }
            });

            if (arquivos_listados.length > vetor_arquivos.length)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível listar os arquivos pois o vetor passado é muito pequeno. O diretório escolhido possui %d arquivos, mas o vetor passado comporta apenas %d elementos. Aumente o tamanho do vetor ou escolha outro diretório para listar os arquivos.", arquivos_listados.length, vetor_arquivos.length));
            }

            for (File arquivo : arquivos_listados)
            {
                if (arquivo.isFile())
                {
                    vetor_arquivos[indice] = arquivo.getName();
                    indice++;
                }
            }

            for (int i = indice; i < vetor_arquivos.length; i++)
            {
                vetor_arquivos[i] = "";
            }

        }
        else
        {
            throw new ErroExecucaoBiblioteca(caminho_pai + " não é um caminho possível");
        }
    }
    
    @DocumentacaoFuncao(descricao = "Cria pastas no caminho informado caso elas não existam",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho",
                        descricao = "Caminho onde as pastas serão criadas"
                )
            },
            autores =
            {
                @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
            }
    )
    public void criar_pasta(String caminho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File diretorio = programa.resolverCaminho(new File(caminho));
        try{
            if(!diretorio.exists());
                diretorio.mkdirs();
        }catch(Exception ex){
            throw new ErroExecucaoBiblioteca("Não foi possivel criar um diretorio em " + caminho);
        }
    }

    private String obterCaminhoArquivo(File arquivo)
    {
        try
        {
            return arquivo.getCanonicalPath();
        }
        catch (IOException excecao)
        {
            return arquivo.getAbsolutePath();
        }
    }

    private List<FileFilter> criarFiltros(String formatos[]) throws ErroExecucaoBiblioteca
    {
        List<FileFilter> filtros = new ArrayList<>();

        for (int i = 0; i < formatos.length; i++)
        {
            String formato = formatos[i];

            try
            {
                String[] partes = formato.split("\\|");

                filtros.add(new FiltroExtensao(partes[0], partes[1]));
            }
            catch (Exception excecao)
            {
                throw new ErroExecucaoBiblioteca(String.format("O formato de arquivo '%s' é inválido", formato));
            }
        }

        return filtros;

    }

    private final class ResultadoSelecao
    {
        private String arquivoSelecionado = "";

        public void setArquivoSelecionado(String arquivoSelecionado)
        {
            this.arquivoSelecionado = arquivoSelecionado;
        }

        public String getArquivoSelecionado()
        {
            return arquivoSelecionado;
        }
    }

    private final class FiltroExtensao extends FileFilter
    {
        private final String descricao;
        private final List<String> extensoes;

        public FiltroExtensao(String descricao, String extensoes)
        {
            this.extensoes = obterExtensoes(extensoes);
            this.descricao = obterDescricao(descricao);
        }

        public String getExtensaoPrincipal()
        {
            return extensoes.get(0);
        }

        private List<String> obterExtensoes(String extensoes)
        {
            List<String> ext = new ArrayList<>();

            String[] exts = extensoes.split(",");

            for (String e : exts)
            {
                ext.add(e.trim().toLowerCase());
            }

            return ext;
        }

        private String obterDescricao(String descricao)
        {
            String desc = descricao;

            desc = desc.concat(" (");

            for (int i = 0; i < extensoes.size(); i++)
            {
                desc = desc.concat("*." + extensoes.get(i));

                if (i < extensoes.size() - 1)
                {
                    desc = desc.concat(", ");
                }
            }

            desc = desc.concat(")");

            return desc;
        }

        @Override
        public boolean accept(File arquivo)
        {
            if (arquivo.isFile())
            {
                for (String extensao : extensoes)
                {
                    if (arquivo.getName().toLowerCase().endsWith("." + extensao) || extensao.equals("*"))
                    {
                        return true;
                    }
                }

                return false;
            }

            return true;
        }

        @Override
        public String getDescription()
        {
            return descricao;
        }
    }

    private final class DialogoSelecaoArquivo extends JFileChooser
    {
        @Override
        public File getSelectedFile()
        {
            File arquivo = super.getSelectedFile();

            if (arquivo != null)
            {
                String extensao = ((FiltroExtensao) getFileFilter()).getExtensaoPrincipal();

                if (!extensao.equals("*"))
                {
                    if (!arquivo.getName().toLowerCase().endsWith("." + extensao))
                    {
                        arquivo = new File(arquivo.getPath().concat("." + extensao));
                    }
                }
            }

            return arquivo;
        }
    }

    private FiltroExtensao obterFiltroTodosArquivos()
    {
        if (filtroTodosArquivos == null)
        {
            filtroTodosArquivos = new FiltroExtensao("Todos os arquivos", "*");
        }

        return filtroTodosArquivos;
    }

    private DialogoSelecaoArquivo obterDialogoSelecao()
    {
        if (dialogoSelecao == null)
        {
            dialogoSelecao = new DialogoSelecaoArquivo();
            dialogoSelecao.setMultiSelectionEnabled(false);
            dialogoSelecao.setApproveButtonText("Selecionar");
            dialogoSelecao.setDialogTitle("Selecionar arquivo");
            dialogoSelecao.setAcceptAllFileFilterUsed(false);
        }

        return dialogoSelecao;

    }

    private final class Arquivo
    {
        private static final String charset = "ISO-8859-1";
        private final File arquivo;

        private ModoAcesso modoAcesso;
        private boolean fim = false;

        private BufferedReader leitor;
        private BufferedWriter escritor;

        public Arquivo(File arquivo)
        {
            this.arquivo = arquivo;
        }

        public void abrir(ModoAcesso modoAcesso) throws ErroExecucaoBiblioteca, InterruptedException
        {
            this.modoAcesso = modoAcesso;

            if (modoAcesso == ModoAcesso.LEITURA)
            {
                abrirParaLeitura();
            }
            else if (modoAcesso == ModoAcesso.ESCRITA)
            {
                abrirParaEscrita();
            }
            else if (modoAcesso == ModoAcesso.ACRESCENTAR)
            {
                abrirParaEscritaAcrescentadora();
            }
        }

        private void abrirParaLeitura() throws ErroExecucaoBiblioteca, InterruptedException
        {
            try
            {
                leitor = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), charset));
            }
            catch (FileNotFoundException | UnsupportedEncodingException excecao)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível abrir o arquivo '%s' para leitura", arquivo.getAbsolutePath()));
            }
        }

        private void abrirParaEscrita() throws ErroExecucaoBiblioteca, InterruptedException
        {
            try
            {
                if (arquivo.getParentFile() != null)
                {
                    arquivo.getParentFile().mkdirs();
                }

                escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo), charset));
            }
            catch (IOException excecao)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível abrir o arquivo '%s' para escrita", arquivo.getAbsolutePath()));
            }
        }
        
        private void abrirParaEscritaAcrescentadora() throws ErroExecucaoBiblioteca, InterruptedException
        {
            try
            {
                if (arquivo.getParentFile() != null)
                {
                    arquivo.getParentFile().mkdirs();
                }

                escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivo, true), charset));
            }
            catch (IOException excecao)
            {
                throw new ErroExecucaoBiblioteca(String.format("Não foi possível abrir o arquivo '%s' para escrita", arquivo.getAbsolutePath()));
            }
        }

        public String ler() throws ErroExecucaoBiblioteca, InterruptedException
        {
            if (modoAcesso == ModoAcesso.LEITURA)
            {
                try
                {
                    String linha = leitor.readLine();

                    if (linha == null)
                    {
                        linha = "";
                        fim = true;
                    }

                    return linha;
                }
                catch (IOException excecao)
                {
                    throw new ErroExecucaoBiblioteca(String.format("Não foi possível ler a próxima linha do arquivo '%s'", arquivo.getAbsolutePath()));
                }
            }
            else
            {
                throw new ErroExecucaoBiblioteca(String.format("O arquivo '%s' está aberto em modo de escrita", arquivo.getAbsolutePath()));
            }
        }

        public void escrever(String linha) throws ErroExecucaoBiblioteca, InterruptedException
        {
            if (modoAcesso == ModoAcesso.ESCRITA || modoAcesso == ModoAcesso.ACRESCENTAR)
            {
                try
                {
                    escritor.write(linha.replace("\n", "").replace("\r", ""));
                    escritor.newLine();
                    escritor.flush();
                }
                catch (IOException excecao)
                {
                    throw new ErroExecucaoBiblioteca(String.format("Não foi possível escrever no arquivo '%s'", arquivo.getAbsolutePath()));
                }
            }
            else
            {
                throw new ErroExecucaoBiblioteca(String.format("O arquivo '%s' está aberto em modo de leitura", arquivo.getAbsolutePath()));
            }
        }

        public boolean fim() throws ErroExecucaoBiblioteca, InterruptedException
        {
            if (modoAcesso == ModoAcesso.LEITURA)
            {
                return fim;
            }
            else
            {
                throw new ErroExecucaoBiblioteca(String.format("O arquivo '%s' está aberto em modo de escrita", arquivo.getAbsolutePath()));
            }
        }

        public void fechar() throws ErroExecucaoBiblioteca, InterruptedException
        {
            try
            {
                if (modoAcesso == ModoAcesso.ESCRITA)
                {
                    escritor.flush();
                    escritor.close();
                }
                else if (modoAcesso == ModoAcesso.LEITURA)
                {
                    leitor.close();
                }
            }
            catch (IOException | NullPointerException excecao)
            {

            }
        }

        private String getCaminho()
        {
            return arquivo.getAbsolutePath();
        }
    }
}
