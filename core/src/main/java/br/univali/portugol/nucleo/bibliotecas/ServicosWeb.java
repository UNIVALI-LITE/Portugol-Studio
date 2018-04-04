/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas;

import static br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca.*;
import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.web.RestClient;
import java.io.IOException;

/**
 *
 * @author Gabriel Schade
 */
@PropriedadesBiblioteca(tipo = COMPARTILHADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém uma série de funções para trabalhar com serviços web",
        versao = "1.0"
)
public final class ServicosWeb extends Biblioteca{
    public ServicosWeb()
    {    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP GET para o endereço especificado, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita")
            },
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#GET",
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String obter_dados(String endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.get(endereco);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP DELETE para o endereço especificado, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita")
            },
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#DELETE",
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String excluir_dados(String endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.delete(endereco);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP POST para o endereço especificado, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita"),
                @DocumentacaoParametro(nome = "objeto", descricao = "objeto no formato JSON passado por parâmetro no corpo da requisição")
            },
             referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#POST",
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String publicar_dados(String endereco, String objeto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.post(endereco,objeto);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP PUT para o endereço especificado, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita"),
                @DocumentacaoParametro(nome = "objeto", descricao = "objeto no formato JSON passado por parâmetro no corpo da requisição")
            },
             referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#PUT",
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String colocar_dados(String endereco, String objeto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.put(endereco,objeto);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
}
