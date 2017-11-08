package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.Programa;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.sons.SonsUtils;
import br.univali.portugol.nucleo.execucao.ObservadorExecucaoBasico;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Luiz Fernando Noschang
 */
@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém funções que permitem reproduzir sons dentro de um programa. No momento, "
        + "somente o formato MP3 é suportado.",
        versao = "1.0"
)
public final class Sons extends Biblioteca
{
    private static final Logger LOGGER = Logger.getLogger(Sons.class.getName());

    private final Map<Integer, Reproducao> reproducoes = new ConcurrentHashMap<>();

    private int volumeGeral = 100;

    private Programa programa;

    @DocumentacaoFuncao(
            descricao = "Carrega um som na memória para ser reproduzido mais tarde",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho_som", descricao = "o caminho para o arquivo de som no sistema de arquivos")
            },
            retorno = "o endereço de memória no qual o som foi carregado",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public int carregar_som(String caminho_som) throws ErroExecucaoBiblioteca, InterruptedException
    {
        File caminho = resolveCaminho(caminho_som);
        Integer endereco = caminho.hashCode();
        if (!reproducoes.containsKey(endereco))
        {
            try
            {   
                reproducoes.put(endereco, new Reproducao(caminho, endereco));
            }
            catch(Exception ex)
            {
                throw new ErroExecucaoBiblioteca(ex);
            }
        }
        return endereco;
    }

    private File resolveCaminho(String caminho)
    {
        if (programa != null)
        {
            return programa.resolverCaminho(new File(caminho));
        }
        return new File(new File("."), caminho); //isto é útil para poder testar a lib Sons sem inicializá-la com um programa.
    }

    @DocumentacaoFuncao(
            descricao = "Libera a memória utilizada por um som que tenha sido previamente carregado. Se o som estiver sendo reproduzido, "
            + "todas as reproduções deste som serão interrompidas",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do som")
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void liberar_som(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.remove(endereco);
        if (reproducao != null)
        {
            reproducao.interrompe(true);
        }
    }

    @DocumentacaoFuncao(
            descricao = "Reproduz um som previamente carregado na memória. O som é reproduzido de forma assíncrona, ou seja, "
            + "o som ficará reproduzindo no fundo, enquanto o programa executa as próximas instruções normalmente. "
            + "Um mesmo som pode ser reproduzido várias vezes e pode se sobrepor a outros sons.",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória do som a ser reproduzido"),
                @DocumentacaoParametro(nome = "repetir", descricao = "determina se o som deve ficar repetindo até ser manualmente interrompido")
            },
            retorno
            = "o endereço de memória da reprodução de som. Este endereço é utilizado quando se deseja interromper "
            + "esta reprodução de som especificamente.",
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public int reproduzir_som(int endereco, boolean repetir) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            reproducao.inicia(repetir);
        }
        else
        {
            throw new ErroExecucaoBiblioteca("Endereço de som inválido (" + endereco + ")!");
        }
        return endereco;
    }
    
        @DocumentacaoFuncao(
            descricao = "Pausa uma reprodução específica de um som que está sendo executada no momento",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da reprodução que se quer interromper")
            },
            autores =
            {
                @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
                @Autor(nome = "Alisson Steffens Henrique", email = "ash@edu.univali.br")
            }
    )
    public void pausar_som(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            reproducao.pausa(false); // não feche o clip de áudio para reutilizá-lo
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Interrompe uma reprodução específica de um som que está sendo executada no momento",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da reprodução que se quer interromper")
            },
            autores =
            {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
            }
    )
    public void interromper_som(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            reproducao.interrompe(false); // não feche o clip de áudio para reutilizá-lo
        }
    }
    
        @DocumentacaoFuncao(
            descricao = "Obtêm o tamanho da música em milisegundos",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da reprodução que se quer alterar o volume")
            },
            retorno = "tempo total em milissegundos do som",
            autores =
            {
                @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
                @Autor(nome = "Alisson Steffens Henrique", email = "ash@edu.univali.br")
            }
    )
    public int obter_tamanho_musica(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            return (int)reproducao.getTamanhoMusica()/1000;
        }
        return 0;
    }
    
            @DocumentacaoFuncao(
            descricao = "Obtêm a posição atual da música em milisegundos",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da reprodução que se quer alterar o volume")
            },
            retorno = "o tempo atual em milissegundos do som",
            autores =
            {
                @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
                @Autor(nome = "Alisson Steffens Henrique", email = "ash@edu.univali.br")
            }
    )
    public int obter_posicao_atual_musica(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            return (int)reproducao.getPosicaoAtualMusica()/1000;
        }
        return 0;
    }
    
    @DocumentacaoFuncao(
            descricao = "Define a posição atual da música em milisegundos",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da reprodução que se quer alterar o volume"),
                @DocumentacaoParametro(nome = "milissegundos", descricao = "o tempo em milissegundos que deseja colocar a musica")
            },
            autores =
            {
                @Autor(nome = "Adson Marques da Silva Esteves", email = "adson@edu.univali.br"),
                @Autor(nome = "Alisson Steffens Henrique", email = "ash@edu.univali.br")
            }
    )
    public void definir_posicao_atual_musica(int endereco, int milissegundos) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            if(reproducao.clipTime>0){
                reproducao.setPosicaoMusica(milissegundos*1000);
            }
            else{
                reproducao.pausa(false);
                reproducao.setPosicaoMusica(milissegundos*1000);
                reproducao.inicia(false);
            }
            
            
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Define um novo volume para um som que já está sendo executado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço de memória da reprodução que se quer alterar o volume"),
                @DocumentacaoParametro(nome = "volume", descricao = "o novo volume entre 0 e 100")
            },
            autores =
            {
                @Autor(nome = "Elieser A. de Jesus", email = "elieser@univali.br")
            }
    )
    public void definir_volume_reproducao(int endereco, int volume) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            reproducao.setVolume(volume / 100f);
        }
        else
        {
            LOGGER.log(Level.WARNING, "Índice de reprodução não encontrado!");
        }
    }

    @DocumentacaoFuncao(
            descricao = "Define o volume geral",
            parametros =
            {
                @DocumentacaoParametro(nome = "volume", descricao = "O novo volume geral (entre 0 e 100)")
            },
            autores =
            {
                @Autor(nome = "Elieser A. de Jesus", email = "elieser@univali.br")
            }
    )
    public void definir_volume(int volume) throws ErroExecucaoBiblioteca, InterruptedException
    {
        volumeGeral = volume;
        float volumeFloat = volume / 100f;
        for (Map.Entry<Integer, Reproducao> entry : reproducoes.entrySet())
        {
            Reproducao reproducao = entry.getValue();
            reproducao.setVolumeGeral(volumeFloat);
        }
    }

    @DocumentacaoFuncao(
            descricao = "Retorna o volume geral",
            retorno = "Um valor do tipo inteiro entre 0 e 100 representando o volume geral atual.",
            autores =
            {
                @Autor(nome = "Elieser A. de Jesus", email = "elieser@univali.br")
            }
    )
    public int obter_volume() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return volumeGeral;
    }

    @DocumentacaoFuncao(
            descricao = "Retornar o volume de uma reprodução de som",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "O endereço de memória da reprodução que se quer obter o volume")
            },
            retorno = "Um valor do tipo inteiro entre 0 e 100 representando o volume atual da reprodução. Caso a reprodução não exista ou já tenha sido finalizada o valor -1 será retornado",
            autores =
            {
                @Autor(nome = "Elieser A. de Jesus", email = "elieser@univali.br")
            }
    )
    public int obter_volume_reproducao(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        Reproducao reproducao = reproducoes.get(endereco);
        if (reproducao != null)
        {
            return (int) (reproducao.getVolume() * 100);
        }
        return -1;
    }

    @Override
    public void inicializar(Programa programa, List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException
    {
        this.programa = programa;
        this.programa.adicionarObservadorExecucao(new ObservadorExecucaoBasico()
        {
            @Override
            public void execucaoEncerrada(Programa programa, ResultadoExecucao resultadoExecucao)
            {
                limparCacheReproducoes();
            }
            
        });
    }

    @Override
    public void finalizar() throws ErroExecucaoBiblioteca, InterruptedException
    {
        LOGGER.log(Level.INFO, "Finalizando biblioteca de Sons, fechando {0} reproduções.", reproducoes.size());
        limparCacheReproducoes();
    }

    private void limparCacheReproducoes(){
        LOGGER.log(Level.CONFIG, "Limpando cache de sons ({0} clips)", reproducoes.size());
        for (Map.Entry<Integer, Reproducao> entry : reproducoes.entrySet())
        {
            Reproducao reproducao = entry.getValue();
            reproducao.interrompe(true); // fecha o clip de áudio
        }
        reproducoes.clear();
    }
    
    private class Reproducao
    {
        private Clip clip;
        private final Integer endereco; 
        private float volume = 1.0f;
        private float volumeGeral = 1.0f;
        private long clipTime = 0;
        private FloatControl controleDeVolume = null;
    
        public Reproducao(File som, Integer endereco) throws IOException, UnsupportedAudioFileException
        {
            this.endereco = endereco;
            try
            {
                clip = AudioSystem.getClip();
                AudioInputStream stream = criaStream(som);
                clip.open(stream);
                stream.close();
                
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN))
                {
                    controleDeVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                }
            }
            catch (Exception excecao)
            {
                LOGGER.log(Level.WARNING, "Não foi possível criar ou abrir uma linha de execução de áudio para " + som.getAbsolutePath(), excecao);
                clip = null;
            }
        }

        /**
         * @param volume da reprodução Entre 0.0 e 1.0
         */
        void setVolume(float volume)
        {
            if (clip == null || controleDeVolume == null)
            {
                return;
            }

            this.volume = limitaValorDoVolume(volume);

            float valorLinear = this.volume * this.volumeGeral;
            float volumeExponencial = SonsUtils.linearParaExponencial(valorLinear); //É possível converter o valor linear para decibéis diretamente, entretanto converter os valores lineares para exponenciais faz com que as alterações de volume se adequem melhor à audição humana. Mais detalhes em http://www.dr-lex.be/info-stuff/volumecontrols.html
            float valorEmDecibeis = SonsUtils.linearParaDecibel(volumeExponencial);
            if (valorEmDecibeis < controleDeVolume.getMinimum())
            {
                valorEmDecibeis = controleDeVolume.getMinimum();
            }
            else if (valorEmDecibeis > controleDeVolume.getMaximum())
            {
                valorEmDecibeis = controleDeVolume.getMaximum();
            }
            controleDeVolume.setValue(valorEmDecibeis);
        }

        void setVolumeGeral(float volumeGeral) //esse 'workaround' no volume geral foi usado porque o Java não permite manipular o volume geral
        {
            this.volumeGeral = volumeGeral;
            setVolume(this.volume); //atualiza o volume
        }

        public float getVolume()
        {
            return volume;
        }

        public int getEndereco()
        {
            return endereco;
        }
        
        public long getPosicaoAtualMusica()
        {
            if(clipTime>0)
            {
                return clipTime;
            }
            return clip.getMicrosecondPosition();
        }
        
        public long getTamanhoMusica()
        {
            return clip.getMicrosecondLength();
        }
        
        public void setPosicaoMusica(long microssecond){
            clipTime = microssecond;
        }

        public void inicia(boolean repetir)
        {
            if (clip == null)
            {
                return;
            }
            if (clip.isRunning())
            {
                clip.stop();
            }            
            clip.setFramePosition(0);
            if(clipTime>0)
            {
                clip.setMicrosecondPosition(clipTime);
                clipTime=0;
                clip.start();
            }
            if (!repetir)
            {
                clip.loop(0);
            }
            else
            {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        
        public void pausa(boolean fechaClip)
        {
            if (clip == null)
            {
                return;
            }
            clipTime=clip.getMicrosecondPosition();
            clip.stop();
            clip.flush();
            if (fechaClip)
            {
                clip.close();
            }
        }

        public void interrompe(boolean fechaClip)
        {
            if (clip == null)
            {
                return;
            }
            clip.stop();
            clip.flush();
            if (fechaClip)
            {
                clip.close();
            }
        }
    }

    private static float limitaValorDoVolume(float volume)
    {
        if (volume < 0)
        {
            return 0;
        }
        else if (volume > 1)
        {
            return 1;
        }
        return volume;
    }

    private static AudioInputStream criaStream(File som)
            throws UnsupportedAudioFileException, IOException
    {
        AudioInputStream fluxoCodificado = AudioSystem.getAudioInputStream(som);
        AudioFormat formatoCodificado = fluxoCodificado.getFormat();
        AudioFormat formatoFinal = new AudioFormat(formatoCodificado.getSampleRate(), 16, formatoCodificado.getChannels(), true, formatoCodificado.isBigEndian());
        return AudioSystem.getAudioInputStream(formatoFinal, fluxoCodificado);
    }

}
