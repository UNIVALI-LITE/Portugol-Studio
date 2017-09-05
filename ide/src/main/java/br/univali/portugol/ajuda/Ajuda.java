package br.univali.portugol.ajuda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz Fernando Noschang
 */
public final class Ajuda {

    private static final Pattern padraoCaminho = Pattern.compile("^(/([^/])+)+$");

    private Topicos topicos;

    Ajuda()
    {
        topicos = new Topicos();
    }

    public Topicos getTopicos()
    {
        return topicos;
    }

    public Topico obterTopico(String caminho) throws ErroCaminhoTopicoInvalido, ErroTopicoNaoEncontrado
    {
        if (caminhoValido(caminho))
        {
            List<String> titulos = extrairTitulos(caminho);

            Topicos subtopicos = null;
            Topico topico = this.topicos.obter(titulos.get(0));

            if (topico != null)
            {
                for (int i = 1; i < titulos.size(); i++)
                {
                    if (topico != null)
                    {
                        subtopicos = topico.getSubTopicos();
                        topico = subtopicos.obter(titulos.get(i));
                    }
                }
            }

            if (topico != null)
            {
                return topico;
            }

            throw new ErroTopicoNaoEncontrado(caminho);
        }

        throw new ErroCaminhoTopicoInvalido(caminho);
    }

    public Topico obterTopicoPeloDiretorio(String caminho) throws ErroCaminhoTopicoInvalido, ErroTopicoNaoEncontrado
    {
        if (caminho.startsWith("/"))
        {
            caminho = caminho.replaceFirst("/", "");
        }
        if (caminho.startsWith("topicos"))
        {
            caminho = caminho.replaceFirst("topicos", "");
        } else
        {
            throw new ErroCaminhoTopicoInvalido("O primeiro caminho do topico deve ser a pasta \"topicos\"");
        }
        if (caminhoValido(caminho))
        {
            List<String> caminhos = extrairTitulos(caminho);
            return obterTopicoPeloDiretorioRecursivo(topicos, caminhos);
        }
        throw new ErroCaminhoTopicoInvalido("O caminho dentro de topicos \"" + caminho + "\" não é um caminho válido");
    }

    private Topico obterTopicoPeloDiretorioRecursivo(Topicos topicos, List<String> caminhos) throws ErroTopicoNaoEncontrado, ErroCaminhoTopicoInvalido
    {
        return obterTopicoPeloDiretorioRecursivo(topicos, caminhos, 0);
    }

    private Topico obterTopicoPeloDiretorioRecursivo(Topicos topicos, List<String> caminhos, int nivelProfundidade) throws ErroTopicoNaoEncontrado, ErroCaminhoTopicoInvalido
    {
        if (nivelProfundidade < caminhos.size() - 1)
        {
            for (Topico topico : topicos)
            {
                if (topico instanceof TopicoHtml)
                {
                    TopicoHtml topicoHtml = (TopicoHtml) topico;
                    String pastaDoIndex = topicoHtml.getArquivoOrigem().getParentFile().getName();
                    if (pastaDoIndex.equals(caminhos.get(nivelProfundidade)))
                    {
                        String nomeArquivo = topicoHtml.getArquivoOrigem().getName();
                        if (caminhos.get(nivelProfundidade + 1).equalsIgnoreCase(nomeArquivo))
                        {
                            return topicoHtml;
                        } else
                        {
                            return obterTopicoPeloDiretorioRecursivo(topico.getSubTopicos(), caminhos, nivelProfundidade + 1);
                        }
                    }
                }
            }
            String relatorioErro = "Erro na busca do tópico, a função estava em \"";

            for (int i = 0; i < nivelProfundidade; i++)
            {
                relatorioErro += caminhos.get(i) + "->";
            }
            relatorioErro += "\" tentando encontrar \"" + caminhos.get(nivelProfundidade) + "\"";
            throw new ErroCaminhoTopicoInvalido(relatorioErro);
        } else
        {
            for (Topico topico : topicos)
            {
                if (topico instanceof TopicoHtml)
                {
                    TopicoHtml topicoHtml = (TopicoHtml) topico;
                    String nomeArquivo = topicoHtml.getArquivoOrigem().getName();

                    if (nomeArquivo.equals(caminhos.get(nivelProfundidade)))
                    {
                        return topico;
                    }
                }
            }
            String relatorioErro = "Erro, o diretório \"";

            for (int i = 0; i < nivelProfundidade; i++)
            {
                relatorioErro += caminhos.get(i) + "->";
            }
            relatorioErro += "\" foi encontrado mas o arquivo \"" + caminhos.get(nivelProfundidade) + "\" não existe.";
            throw new ErroTopicoNaoEncontrado(relatorioErro);
        }

    }

    private boolean caminhoValido(String caminho) throws ErroCaminhoTopicoInvalido
    {
        return (caminho != null && padraoCaminho.matcher(caminho).find());
    }

    private List<String> extrairTitulos(String caminho)
    {
        List<String> titulos = new ArrayList<>();

        titulos.addAll(Arrays.asList(caminho.split("/")));
        titulos.remove(0);

        return titulos;
    }
}
