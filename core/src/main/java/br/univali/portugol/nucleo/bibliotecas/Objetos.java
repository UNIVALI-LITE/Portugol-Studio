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
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * @author Gabriel Schade
 */
@PropriedadesBiblioteca(tipo = COMPARTILHADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém uma série de funções para criar e trabalhar com objetos",
        versao = "1.0"
)
public final class Objetos extends Biblioteca
{
    private final CacheObjetos cacheObjetos;
    public Objetos()
    {
        cacheObjetos = CacheObjetos.criar();
    }

    @DocumentacaoFuncao(
            descricao = "Realiza a criação de um objeto a partir de uma cadeia no formato JSON (JavaScript Object Notation)",
             parametros =
            {
                @DocumentacaoParametro(nome = "json", descricao = "texto no formato JSON para criar o objeto")
            },
            referencia = "https://pt.wikipedia.org/wiki/JSON",
            retorno = "O endereço de memória no qual o objeto foi carregada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int criar_objeto_via_json(String json) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.criarObjeto(new Objeto(json));
    }
    
    @DocumentacaoFuncao(
            descricao = "Realiza a criação de um objeto vazio em memória",
            retorno = "O endereço de memória no qual o objeto foi carregada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int criar_objeto() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.criarObjeto(new Objeto());
    }

    @DocumentacaoFuncao(
            descricao = "Realiza a atribuição de uma propriedade do objeto no endereço informado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor alterado"),
                @DocumentacaoParametro(nome = "valor", descricao = "o valor que será atribuido para a propriedade"),
            },
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void atribuir_propriedade(int endereco, String propriedade, Object valor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        cacheObjetos.obterObjeto(endereco).atribuirPropriedade(propriedade, valor);
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo inteiro no objeto do endereço informado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),
            },
            retorno = "o valor da propriedade informada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int obter_valor_propriedade_tipo_inteiro(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.obterObjeto(endereco).obterPropriedadeInteiro(propriedade);
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo real no objeto do endereço informado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),
            },
            retorno = "o valor da propriedade informada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public double obter_valor_propriedade_tipo_real(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.obterObjeto(endereco).obterPropriedadeReal(propriedade);
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo logico no objeto do endereço informado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),
            },
            retorno = "o valor da propriedade informada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public boolean obter_valor_propriedade_tipo_logico(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.obterObjeto(endereco).obterPropriedadeLogico(propriedade);
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo caracter no objeto do endereço informado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),
            },
            retorno = "o valor da propriedade informada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public char obter_valor_propriedade_tipo_caracter(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.obterObjeto(endereco).obterPropriedadeCaracter(propriedade);
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo cadeia no objeto do endereço informado",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),
            },
            retorno = "o valor da propriedade informada",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String obter_valor_propriedade_tipo_cadeia(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.obterObjeto(endereco).obterPropriedadeCadeia(propriedade);
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Libera o objeto do endereço informado da memória",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
            },
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void liberar_objeto(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        cacheObjetos.liberarObjeto(endereco);
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Libera todos os objetos previamente armazenados na memória",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void liberar() throws ErroExecucaoBiblioteca, InterruptedException
    {
        cacheObjetos.liberar();
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Obtém a cadeia que representa o objeto inteiro no formato JSON (JavaScript Object Notation)",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
            },
            referencia = "https://pt.wikipedia.org/wiki/JSON",
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String obter_json(int endereco) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try{
            return cacheObjetos.obterObjeto(endereco).obterJson();    
        } catch (JsonProcessingException ex) {
            throw new ErroExecucaoBiblioteca("Não foi possível obter o JSON deste objeto.");
        }
    }
    
    @DocumentacaoFuncao(
            descricao
            = "Identifica se o objeto contém ou não a propriedade informada",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado"),
                @DocumentacaoParametro(nome = "propriedade", descricao = "propriedade utilizada para verificação")
            },
            autores =
            {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public boolean contem_propriedade(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cacheObjetos.obterObjeto(endereco).contemPropriedade(propriedade);
    }
    
    
    
}
