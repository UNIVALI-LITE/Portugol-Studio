package br.univali.ps.plugins.base;

/**
 * Classe base para a criação de plugins do Portugol Studio. Todos os plugins para
 * o Portugol Studio deverão seguir as seguintes regras de implementação:
 * <ul>
 * <li>
 * <p>
 * O Plugin deve estender a classe {@link Plugin}</p><br/></li>
 * <li>
 * <p>
 * A classe do plugin deve ser <strong>pública</strong>, <strong>final</strong>
 * e não pode ser <strong>estática</strong>, <strong>anônima</strong>,
 * <strong>sintética</strong>, <strong>membro</strong> ou <strong>local</strong>
 * </p>
 * <br/>
 * </li>
 * <li>
 * <p>
 * A classe do plugin deve possuir um construtor público que não recebe parâmetros
 * </p>
 * <br/>
 * </li>
 * <li>
 * <p>
 * O JAR do plugin, deve conter os sguintes arquivos:
 * </p>
 * <ul>
 * <li><b>licensa.txt:</b> contém um texto descrevendo os termos de licença do plugin</li><br/>
 * <li><b>icone_16x16.png:</b> uma imagem no formato PNG com dimensões 16x16 pixels que
 * representa o plugin na interface do usuário</li><br/>
 * <li><b>icone_32x32.png:</b> uma imagem no formato PNG com dimensões 32x32 pixels que
 * representa o plugin na interface do usuário</li><br/>
 * <li><b>plugin.xml:</b> um XML que descreve as características do plugin. O XML deve
 * seguir o formato estabelecido pelo schema presente no endereço:
 * <a href="http://siaiacad17.univali.br/~alice/portugol/studio/plugins/plugin.xsd">
 * http://siaiacad17.univali.br/~alice/portugol/studio/plugins/plugin.xsd</a>
 * </li>
 * </ul>
 * </ul>
 *
 * @author Luiz Fernando Noschang
 */
public abstract class Plugin
{
    private MetaDadosPlugin metaDados;

    public final MetaDadosPlugin getMetaDados()
    {
        return metaDados;
    }

    void setMetaDados(MetaDadosPlugin metaDados)
    {
        this.metaDados = metaDados;
    }
    
    /**
     * Obtém a visão principal do plugin.
     *
     * @return a visão do plugin
     */
    public abstract VisaoPlugin getVisao();

    /**
     * Método utilizado para inicializar o plugin. Este método será chamado automaticamente
     * pelo {@link GerenciadorPlugins} cada vez que o plugin for instalado em um {@link UtilizadorPlugins}
     *
     * @param utilizador o objeto que está utilizando este plugin
     */
    protected void inicializar(UtilizadorPlugins utilizador)
    {

    }
    
    /**
     * Método utilizado para finalizar o plugin. Este método será chamado automaticamente
     * pelo {@link GerenciadorPlugins} cada vez que o plugin for desinstalado de um {@link UtilizadorPlugins}
     *
     * @param utilizador o objeto que está utilizando este plugin
     */
    protected void finalizar(UtilizadorPlugins utilizador)
    {
        
    }
}
