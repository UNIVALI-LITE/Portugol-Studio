package br.univali.portugol.nucleo;

import br.univali.portugol.nucleo.asa.NoDeclaracaoBase;
import br.univali.portugol.nucleo.asa.NoReferencia;
import br.univali.portugol.nucleo.asa.TrechoCodigoFonte;
import br.univali.portugol.nucleo.programa.Programa;

import java.util.ArrayList;
import java.util.List;

/**
 * Renomeia a declaração e todas as referências de um símbolo no
 * código fonte de um programa.
 *
 * @author Luiz Fernando Noschang
 * @since 03/19/2016
 */
final class RenomeadorDeSimbolos
{
    /**
     * Renomeia a declaração e todas as referências de um símbolo no
     * código fonte.
     *
     * @param programa o programa escrito pelo usuário, no qual o símbolo deve
     * ser renomeado.
     *
     * @param linha a linha dentro do programa onde a referência ou declaração
     * do símbolo se encontra
     *
     * @param coluna a coluna dentro da linha do programa onde a referência ou
     * declaração do símbolo se encontra
     *
     * @param novoNome o novo nome que será atribuído ao símbolo
     *
     * @return uma nova versão do código fonte do programa com a declaração e
     * todas as as referências do símbolo atualizadas para o novo nome
     */
    public String renomearSimbolo(String programa, int linha, int coluna, String novoNome) throws ErroAoRenomearSimbolo
    {
        programa = removerInformacoesPortugolStudio(programa);

        try
        {
            Programa programaCompilado = Portugol.compilarParaAnalise(programa);
            BuscadorDeSimbolo buscadorDeSimbolo = new BuscadorDeSimbolo();

            buscadorDeSimbolo.buscarSimbolo(programaCompilado, linha, coluna);

            if (buscadorDeSimbolo.simboloEncontrado())
            {
                NoDeclaracaoBase declaracao = buscadorDeSimbolo.getDeclaracaoSimbolo();
                List<Integer> posicoesOcorrencias = mapearPosicoesReferencias(programa, declaracao.getReferencias());

                posicoesOcorrencias.add(obterPosicaoAbsoluta(programa, declaracao.getTrechoCodigoFonteNome().getLinha(), declaracao.getTrechoCodigoFonteNome().getColuna()));

                String antigoNome = declaracao.getNome();
                StringBuilder builder = new StringBuilder();

                for (int posicao = 0; posicao < programa.length(); posicao++)
                {
                    if (posicoesOcorrencias.contains(posicao))
                    {
                        builder.append(novoNome);
                        posicao = posicao + antigoNome.length() - 1;

                        continue;
                    }

                    builder.append(programa.charAt(posicao));
                }

                return builder.toString();
            }
            else
            {
                throw new ErroAoRenomearSimbolo(String.format("Não foi encontrado nenhum símbolo para renomear na linha %d, coluna %d", linha, coluna), ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO);
            }
        }
        catch (ErroCompilacao ex)
        {
            throw new ErroAoRenomearSimbolo("Não é possível renomear um símbolo em um programa que contém erros", ErroAoRenomearSimbolo.Tipo.ERRO_USUARIO);
        }
    }

    public NoDeclaracaoBase obterDeclaracaoDoSimbolo(String programa, int linha, int coluna) throws ErroAoTentarObterDeclaracaoDoSimbolo, ErroAoRenomearSimbolo
    {
        programa = removerInformacoesPortugolStudio(programa);

        try
        {
            Programa programaCompilado = Portugol.compilarParaAnalise(programa);
            BuscadorDeSimbolo buscadorDeSimbolo = new BuscadorDeSimbolo();

            buscadorDeSimbolo.buscarSimbolo(programaCompilado, linha, coluna);

            if (buscadorDeSimbolo.simboloEncontrado())
            {
                return buscadorDeSimbolo.getDeclaracaoSimbolo();
            }
            else
            {
                throw new ErroAoTentarObterDeclaracaoDoSimbolo(String.format("Não foi encontrado nenhum símbolo na linha %d, coluna %d", linha, coluna), CausaErroAoTentarObterDeclaracaoDoSimbolo.SIMBOLO_NAO_ENCONTRADO);
            }
        }
        catch (ErroAoRenomearSimbolo ex)
        {
            throw new ErroAoRenomearSimbolo(ex.getMensagem(), ex.getTipo());
        }
        catch (ErroCompilacao ex)
        {
            throw new ErroAoTentarObterDeclaracaoDoSimbolo("Não foi possível encontrar o símbolo porque o programa contém erros", CausaErroAoTentarObterDeclaracaoDoSimbolo.PROGRAMA_CONTEM_ERROS);
        }
    }

    private List<Integer> mapearPosicoesReferencias(String programa, List<NoReferencia> referencias)
    {
        List<Integer> posicoes = new ArrayList<>();

        for (NoReferencia referencia : referencias)
        {
            TrechoCodigoFonte trecho = referencia.getTrechoCodigoFonteNome();
            posicoes.add(obterPosicaoAbsoluta(programa, trecho.getLinha(), trecho.getColuna()));
        }

        return posicoes;
    }

    private int obterPosicaoAbsoluta(String programa, int linha, int coluna)
    {
        int linhaAtual = 1;
        int posicao = 0;

        for (int caracter = 0; caracter < programa.length(); caracter++)
        {
            if (linhaAtual == linha)
            {
                return caracter + coluna;
            }

            if (programa.charAt(caracter) == '\n')
            {
                linhaAtual = linhaAtual + 1;
            }
        }

        return posicao;
    }

    private String removerInformacoesPortugolStudio(String codigoFonte)
    {
        codigoFonte = codigoFonte.replaceAll(System.lineSeparator(), Portugol.QUEBRA_DE_LINHA);
        
        int inicio = codigoFonte.lastIndexOf("/* $$$ Portugol Studio $$$");

        if (inicio >= 0)
        {
            // Quando as informações do Portugol Studio são inseridas no arquivo, é adicionada uma quebra de linha
            // antes do bloco de informações. Ao carregar o arquivo é necessário remover esta quebra para evitar
            // que o arquivo cresça indefinidamente a cada salvamento. Esta remoção é feita retrocedendo 1 caracter,
            // que corresponde ao '\n'

            inicio = inicio - 1;
            StringBuilder sb = new StringBuilder(codigoFonte);

            sb.delete(inicio, codigoFonte.length());
            codigoFonte = sb.toString();
        }

        // Remove a tag de cursor que foi incluída nas versões anteriores do Portugol Studio
        codigoFonte = codigoFonte.replace("/*${cursor}*/", "");

        return codigoFonte;
    }
}
