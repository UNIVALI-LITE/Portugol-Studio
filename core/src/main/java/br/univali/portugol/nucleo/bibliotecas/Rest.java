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
import br.univali.portugol.nucleo.bibliotecas.objetos.CacheObjetos;
import br.univali.portugol.nucleo.bibliotecas.objetos.Objeto;
import br.univali.portugol.nucleo.bibliotecas.web.RestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Schade
 */
@PropriedadesBiblioteca(tipo = COMPARTILHADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém uma série de funções para criar e trabalhar com requisições para serviços REST",
        versao = "1.0"
)
public final class Rest extends Biblioteca{
    public Rest()
    {    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP GET para o endereço especificado, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita")
            },
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String get(String endereco) throws ErroExecucaoBiblioteca, InterruptedException
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
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String delete(String endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.get(endereco);
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
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String post(String endereco, String objeto) throws ErroExecucaoBiblioteca, InterruptedException
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
            retorno = "O arquivo JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String put(String endereco, String objeto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.put(endereco,objeto);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
}
