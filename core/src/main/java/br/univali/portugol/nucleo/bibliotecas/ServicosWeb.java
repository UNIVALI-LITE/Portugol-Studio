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
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.web.RestClient;
import java.io.IOException;
import java.net.HttpURLConnection;

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

    private HttpURLConnection obterConexaoEmCache() throws ErroExecucaoBiblioteca{
        HttpURLConnection conexao = RestClient.obterConexaoEmCache();
        if(conexao == null)
            throw new ErroExecucaoBiblioteca("É necessário criar uma conexão customizada antes de adicionar o cabeçalho");
        
        return conexao;
    }

    @DocumentacaoConstante(
            descricao = "Constante para definir o método HTTP GET",
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#GET"
    )
    public static final int OBTER = 1;
    @DocumentacaoConstante(
            descricao = "Constante para definir o método HTTP POST",
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#POST"
    )
    public static final int PUBLICAR = 2;
    @DocumentacaoConstante(
            descricao = "Constante para definir o método HTTP PUT",
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#PUT"
    )
    public static final int ATUALIZAR = 3;
    @DocumentacaoConstante(
            descricao = "Constante para definir o método HTTP DELETE",
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#DELETE"
    )
    public static final int EXCLUIR = 4;
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP GET para o endereço especificado, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita")
            },
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol#GET",
            retorno = "a cadeia JSON contendo a resposta da requisição",
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
            retorno = "a cadeia JSON contendo a resposta da requisição",
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
            retorno = "a cadeia JSON contendo a resposta da requisição",
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
            retorno = "a cadeia JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String atualizar_dados(String endereco, String objeto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try {
            return RestClient.put(endereco,objeto);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Abre uma conexão customizável com o endereço especificado.",
             parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "endereço para o qual a requisição será feita")
            },
             referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void abrir_conexao(String endereco) throws ErroExecucaoBiblioteca, InterruptedException{
        try {
            RestClient.abrirConexao(endereco);
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na criação da requisição.");
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Adiciona um cabeçalho na conexão customizável, para utilizar esta função é necessário abrir uma conexão antes.",
             parametros =
            {
                @DocumentacaoParametro(nome = "chave", descricao = "chave do cabeçalho que será incluído na conexão"),
                @DocumentacaoParametro(nome = "valor", descricao = "valor do cabeçalho que será incluído na conexão")
            },
             referencia = "https://pt.wikipedia.org/wiki/Lista_de_campos_de_cabe%C3%A7alho_HTTP",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void adicionar_cabecalho(String chave, String valor) throws ErroExecucaoBiblioteca, InterruptedException{
        HttpURLConnection conexao = obterConexaoEmCache();       
        RestClient.incluirCabecalhoNaRequisicao( conexao,chave, valor);    
    }
    
    @DocumentacaoFuncao(
            descricao = "Adiciona um objeto no formato JSON na conexão customizável, para utilizar esta função é necessário abrir uma conexão antes. Esta função deve ser utilizada para informar os parâmetros dos métodos HTTP POST e PUT.",
            parametros =
            {
                @DocumentacaoParametro(nome = "objeto", descricao = "objeto no formato JSON passado por parâmetro no corpo da requisição")
            },
             referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void adicionar_parametros(String objeto) throws ErroExecucaoBiblioteca, InterruptedException{       
        RestClient.adicionarParametroNaConexao(objeto);
    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza uma chamada HTTP através de uma conexão customizada, retornando uma cadeia no formato JSON como resposta",
             parametros =
            {
                @DocumentacaoParametro(nome = "metodoHttp", descricao = "Método HTTP que será utilizado nesta execução (GET, POST, PUT ou DELETE).")
            },
            referencia = "https://pt.wikipedia.org/wiki/Hypertext_Transfer_Protocol",
            retorno = "a cadeia JSON contendo a resposta da requisição",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String fazer_requisicao(int metodoHttp) throws ErroExecucaoBiblioteca, InterruptedException
    {       
        try {
            String retorno = "";
            switch(metodoHttp){
                case OBTER:
                    retorno = RestClient.get(obterConexaoEmCache());
                    break;
                case PUBLICAR:
                    retorno = RestClient.post(obterConexaoEmCache());
                    break;
                case ATUALIZAR:
                    retorno = RestClient.put(obterConexaoEmCache());
                    break;
                case EXCLUIR:
                    retorno = RestClient.delete(obterConexaoEmCache());
                    break;
            }
            return retorno;
        } catch (IOException ex) {
            throw new ErroExecucaoBiblioteca("Ocorreu um problema na requisição.");
        }
    }
    

}
