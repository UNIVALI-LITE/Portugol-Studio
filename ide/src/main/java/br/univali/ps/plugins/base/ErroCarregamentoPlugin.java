package br.univali.ps.plugins.base;

import java.io.File;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class ErroCarregamentoPlugin extends Exception
{

    public ErroCarregamentoPlugin(String mensagem)
    {
        super(mensagem);
    }
    
    public ErroCarregamentoPlugin(String mensagem, File arquivoJar, Class classePlugin)
    {
        super(String.format("Erro ao carregar o plugin '%s' do arquivo '%s': %s", classePlugin.getName(), Util.obterCaminhoAbsoluto(arquivoJar), mensagem));
    }

    public ErroCarregamentoPlugin(String mensagem, File arquivoJar)
    {
        super(String.format("Erro ao carregar o arquivo de plugin '%s'", Util.obterCaminhoAbsoluto(arquivoJar)));
    }
}
