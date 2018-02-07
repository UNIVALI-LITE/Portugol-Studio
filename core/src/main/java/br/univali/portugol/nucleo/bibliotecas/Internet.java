/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.graficos.ImagemGenerica;
import br.univali.portugol.nucleo.bibliotecas.web.FileTransfer;
import br.univali.portugol.nucleo.bibliotecas.web.ResourcesGetter;

/**
 *
 * @author Alisson
 */
@PropriedadesBiblioteca(tipo = TipoBiblioteca.COMPARTILHADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém diversas funções de conexão com a web",
        versao = "0.1"
)
public final class Internet extends Biblioteca{
    
    @DocumentacaoFuncao(
            descricao = "Obtém o conteúdo de um página web",
            retorno = "O conteúdo de uma pagina web",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho", descricao = "o caminho de onde o conteúdo deverá ser obtido")
            },
            autores =
            {
                @Autor(nome = "Alisson Steffens", email = "ali.steffens@gmail.com")
            }
    )
    public String obter_texto(String caminho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            String a = ResourcesGetter.carregarRecursos(caminho);
            if(a.isEmpty()){
                throw new ErroExecucaoBiblioteca("O caminho "+caminho+" não tem nenhum conteúdo");
            }
            return a;
        }
        catch (Exception excecao)
        {
            throw new ErroExecucaoBiblioteca("Não foi possível obter o conteúdo de "+caminho);
        }
    }
    
//    @DocumentacaoFuncao(
//            descricao = "Obtém recursos de um página web",
//            retorno = "Os recursos de uma pagina web",
//            parametros =
//            {
//                @DocumentacaoParametro(nome = "caminho", descricao = "o caminho de onde o conteúdo deverá ser obtido")
//            },
//            autores =
//            {
//                @Autor(nome = "Alisson Steffens", email = "ali.steffens@gmail.com")
//            }
//    )
//    public int obter_recurso(String caminho) throws ErroExecucaoBiblioteca, InterruptedException
//    {
//        try
//        {
//            int endereco = Graficos.cacheImagens.adicionarImagem(new ImagemGenerica(FileTransfer.downloadImage(caminho)));
//            return endereco;
//        }
//        catch (Exception excecao)
//        {
//            throw new ErroExecucaoBiblioteca("Não foi possível obter o conteúdo de "+caminho);
//        }
//    }
}
