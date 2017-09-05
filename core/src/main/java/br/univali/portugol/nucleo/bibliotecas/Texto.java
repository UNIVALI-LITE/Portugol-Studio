package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;

/**
 *
 * @author Luiz Fernando Noschang
 */
@PropriedadesBiblioteca(tipo = TipoBiblioteca.COMPARTILHADA)
@DocumentacaoBiblioteca(descricao = "Esta biblioteca contém funções para manipulação de texto (dados do tipo <tipo>cadeia</tipo>)", versao = "1.1")
public final class Texto extends Biblioteca
{
    
    private StringBuilder stringBuilder = new StringBuilder(1024);
    
    
    @DocumentacaoFuncao
    (
        descricao = "Conta o número de caracteres existentes em uma <tipo>cadeia</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cadeia", descricao = "um valor qualquer do tipo <tipo>cadeia</tipo>")
        },
        retorno = "o número de caracteres na <tipo>cadeia</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public int numero_caracteres(String cadeia) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cadeia.length();
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Transforma os caracteres de uma <tipo>cadeia</tipo> em caracteres maiúsculos",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "um valor qualquer do tipo <tipo>cadeia</tipo>")
        },
        retorno = "a <tipo>cadeia</tipo> com os caracteres transformados",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String caixa_alta(String cad) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cad.toUpperCase();
    }    
    
    @DocumentacaoFuncao
    (
        descricao = "Transforma os caracteres de uma <tipo>cadeia</tipo> em caracteres minúsculos",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "um valor qualquer do tipo <tipo>cadeia</tipo>")
        },
        retorno = "a <tipo>cadeia</tipo> com os caracteres transformados",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String caixa_baixa(String cad) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cad.toLowerCase();
    }

    @DocumentacaoFuncao
    (
        descricao = "Pesquisa por um determinado texto em uma <tipo>cadeia</tipo> e substitui todas as ocorrências por um texto alternativo",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> que será pesquisada"),
            @DocumentacaoParametro(nome = "texto_pesquisa", descricao = "o texto que será pesquisado na <tipo>cadeia</tipo>"),
            @DocumentacaoParametro(nome = "texto_substituto", descricao = "o texto pelo qual as ocorrências serão substituídas")
        },
        retorno = "a <tipo>cadeia</tipo> resultante da substituição",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String substituir(String cad, String texto_pesquisa, String texto_substituto) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cad.replace(texto_pesquisa, texto_substituto);
    }

    @DocumentacaoFuncao
    (
        descricao = 
            
              "Concatena o <tipo>caracter</tipo> informado, à esquerda da <tipo>cadeia</tipo>, até que a "
            + "<tipo>cadeia</tipo> fique do <param>tamanho</param> indicado.\n\n"
            
            + "Se o tamanho da <tipo>cadeia</tipo> for maior ou igual ao <param>tamanho</param> informado, "
            + "nada é feito", 
        
        parametros = 
        {
            @DocumentacaoParametro(nome = "car", descricao = "o <tipo>caracter</tipo> que será concatenado á esquerda da <tipo>cadeia</tipo>"),
            @DocumentacaoParametro(nome = "tamanho", descricao = "o tamanho final da <tipo>cadeia</tipo>"),
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> que será transformada")
        },
        retorno = "a <tipo>cadeia</tipo> transformada",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br"),
            @Autor(nome = "Fillipi Domingos Pelz", email = "fillipi@univali.br")
        }
    )    
    
    public String preencher_a_esquerda(char car, int tamanho, String cad) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (cad.length() < tamanho)
        {
            stringBuilder.setLength(0); // limpa o buffer antes de usar
            int diferenca = tamanho - cad.length();
            for (int i = 1; i <= diferenca; i++)
            {
                stringBuilder.append(car);
            }
            
            cad = stringBuilder.append(cad).toString();
        }
        
        return cad;
    }    
    
    @DocumentacaoFuncao
    (
        descricao = 
            
              "Obtém um <tipo>caracter</tipo> da <tipo>cadeia</tipo> a partir de seu <param>índice</param>.\n\n"
            
            + "O <param>índice</param> deve estar entre 0 e o número de caracteres da <tipo>cadeia</tipo>", 
        
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> da qual será obtido o caracater"),
            @DocumentacaoParametro(nome = "indice", descricao = "o indice do caracter que se deseja obter")            
        },
        retorno = "o <tipo>caracater</tipo> no <param>índice</param> informado",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )    
    public char obter_caracter(String cad, int indice) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (indice < 0)
        {
            throw new ErroExecucaoBiblioteca(String.format("O índice do caracter (%d) é menor que 0", indice));
        }
        else if (indice > cad.length() - 1)
        {
            throw new ErroExecucaoBiblioteca(String.format("O índice do caracter (%d) é maior que o número de caracteres na cadeia (%d)", indice, cad.length()));
        }
        else
        {
            return cad.charAt(indice);
        }
    }

    @DocumentacaoFuncao
    (
        descricao = "Procura por um texto dentro de uma cadeia e, caso encontrado, retorna a posição da primeira ocorrência",
            
        parametros = 
        {
            @DocumentacaoParametro(nome = "texto", descricao = "o texto que será procurado na cadeia"),
            @DocumentacaoParametro(nome = "cad", descricao = "a cadeia dentro da qual o texto será procurado"),
            
            @DocumentacaoParametro
            (
                nome = "posicao_incial", 
                    
                descricao = "a posição inicial a partir da qual o texto será procurado. Para procurar a partir do início da cadeia " +
                            "deve-se informar a posição 0"
            )
        },
          
        retorno = "a posição da primeira ocorrência do texto, caso ele seja encontrado. Caso o texto não seja encontrado na cadeia " +
                  "o valor retornado é -1",
        
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public int posicao_texto(String texto, String cadeia, int posicao_inicial) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cadeia.indexOf(texto, posicao_inicial);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Extrai uma parte da cadeia delimitada pela posição inicial e final. Exemplos: <br><br>" +
                    "extrair_subtexto(\"salgado\", 0, 3) // resultado: \"sal\"<br><br>" +
                    "extrair_subtexto(\"salgado\", 3, 7) // resultado: \"gado\"<br><br>" +
                    "extrair_subtexto(\"salgado\", 1, 5) // resultado: \"alga\"",

        retorno = "uma <tipo>cadeia</tipo> contendo o subtexto",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a cadeia a partir da qual será extraído o subtexto"),
            @DocumentacaoParametro(nome = "posicao_inicial", descricao = "a posição dentro da cadeia onde começará o subtexto"),
            @DocumentacaoParametro(nome = "posicao_final", descricao = "a posição dentro da cadeia onde terminará o subtexto")
        },
        
        autores = 
        {
                @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }
    )
    public String extrair_subtexto(String cadeia, int posicao_inicial, int posicao_final) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            return cadeia.substring(posicao_inicial, posicao_final);
        }
        catch (IndexOutOfBoundsException excecao)
        {
            throw new ErroExecucaoBiblioteca("Posição inicial ou final inválida. A posição deve estar entre 0 e o tamanho da cadeia");
        }
    }
}
