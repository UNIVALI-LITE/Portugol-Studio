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
import br.univali.portugol.nucleo.bibliotecas.objetos.CacheObjetos;
import br.univali.portugol.nucleo.bibliotecas.objetos.Objeto;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import javax.naming.directory.InvalidAttributeValueException;

/**
 *
 * @author Gabriel Schade
 */
@PropriedadesBiblioteca(tipo = COMPARTILHADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém uma série de funções para criar e trabalhar com objetos",
        versao = "1.0"
)
public final class Objetos extends Biblioteca {

    @Override
    public void finalizar() throws ErroExecucaoBiblioteca, InterruptedException {
        cacheObjetos.liberar();
    }

    private final CacheObjetos cacheObjetos;

    public Objetos() {
        cacheObjetos = CacheObjetos.criar();
    }

    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo inteiro"
    )
    public static final int TIPO_INTEIRO = 1;

    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo cadeia"
    )
    public static final int TIPO_CADEIA = 2;

    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo caracter"
    )
    public static final int TIPO_CARACTER = 3;

    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo real"
    )
    public static final int TIPO_REAL = 4;

    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo logico"
    )
    public static final int TIPO_LOGICO = 5;
    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo objeto"
    )
    public static final int TIPO_OBJETO = 6;
    @DocumentacaoConstante(
            descricao = "Constante para definir o tipo vetor"
    )
    public static final int TIPO_VETOR = 7;

    @DocumentacaoFuncao(
            descricao = "Realiza a criação de um objeto a partir de uma cadeia no formato JSON (JavaScript Object Notation)",
            parametros
            = {
                @DocumentacaoParametro(nome = "json", descricao = "texto no formato JSON para criar o objeto")
            },
            referencia = "https://pt.wikipedia.org/wiki/JSON",
            retorno = "O endereço de memória no qual o objeto foi carregada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int criar_objeto_via_json(String json) throws ErroExecucaoBiblioteca, InterruptedException {
        return cacheObjetos.criarObjeto(new Objeto(json, Objeto.JSON));
    }

    @DocumentacaoFuncao(
            descricao = "Realiza a criação de um objeto a partir de uma cadeia no formato XML (eXtensible Markup Language)",
            parametros
            = {
                @DocumentacaoParametro(nome = "xml", descricao = "texto no formato XML para criar o objeto")
            },
            referencia = "https://pt.wikipedia.org/wiki/XML",
            retorno = "O endereço de memória no qual o objeto foi carregada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int criar_objeto_via_xml(String xml) throws ErroExecucaoBiblioteca, InterruptedException {
        return cacheObjetos.criarObjeto(new Objeto(xml, Objeto.XML));
    }

    @DocumentacaoFuncao(
            descricao = "Realiza a criação de um objeto vazio em memória",
            retorno = "O endereço de memória no qual o objeto foi carregada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int criar_objeto() throws ErroExecucaoBiblioteca, InterruptedException {
        return cacheObjetos.criarObjeto(new Objeto());
    }

    @DocumentacaoFuncao(
            descricao = "Realiza a atribuição de uma propriedade do objeto no endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor alterado")
                ,
                @DocumentacaoParametro(nome = "valor", descricao = "o valor que será atribuido para a propriedade"),},
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void atribuir_propriedade(int endereco, String propriedade, Object valor) throws ErroExecucaoBiblioteca, InterruptedException {
        cacheObjetos.obterObjeto(endereco).atribuirPropriedade(propriedade, valor);
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo inteiro no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int obter_propriedade_tipo_inteiro(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            return cacheObjetos.obterObjeto(endereco).obterPropriedadeInteiro(propriedade);
        } catch (InvalidAttributeValueException | ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo real no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public double obter_propriedade_tipo_real(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            return cacheObjetos.obterObjeto(endereco).obterPropriedadeReal(propriedade);
        } catch (InvalidAttributeValueException | ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo logico no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public boolean obter_propriedade_tipo_logico(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            return cacheObjetos.obterObjeto(endereco).obterPropriedadeLogico(propriedade);
        } catch (InvalidAttributeValueException | ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo caracter no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public char obter_propriedade_tipo_caracter(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            return cacheObjetos.obterObjeto(endereco).obterPropriedadeCaracter(propriedade);
        } catch (InvalidAttributeValueException | ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo cadeia no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String obter_propriedade_tipo_cadeia(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            return cacheObjetos.obterObjeto(endereco).obterPropriedadeCadeia(propriedade);
        } catch (InvalidAttributeValueException | ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de uma propriedade do tipo objeto no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int obter_propriedade_tipo_objeto(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            HashMap map
                    = cacheObjetos.obterObjeto(endereco).obterPropriedadeObjeto(propriedade);
            return cacheObjetos.criarObjeto(new Objeto(map));
        } catch (JsonProcessingException ex) {
            throw new ErroExecucaoBiblioteca("Não foi possível obter a propriedade deste objeto.");
        } catch (InvalidAttributeValueException | ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
                ,
                @DocumentacaoParametro(nome = "indice", descricao = "o índice do elemento do vetor que será obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int obter_propriedade_tipo_objeto_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            Object valor = obter_propriedade_em_vetor(endereco, propriedade, indice);
            HashMap map = Objeto.obterPropriedadeObjeto(valor);
            return cacheObjetos.criarObjeto(new Objeto(map));
        } catch (ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
                ,
                @DocumentacaoParametro(nome = "indice", descricao = "o índice do elemento do vetor que será obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public char obter_propriedade_tipo_caracter_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            Object valor = obter_propriedade_em_vetor(endereco, propriedade, indice);
            return Objeto.obterPropriedadeCaracter(valor);
        } catch (ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
                ,
                @DocumentacaoParametro(nome = "indice", descricao = "o índice do elemento do vetor que será obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public boolean obter_propriedade_tipo_logico_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            Object valor = obter_propriedade_em_vetor(endereco, propriedade, indice);
            return Objeto.obterPropriedadeLogico(valor);
        } catch (ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
                ,
                @DocumentacaoParametro(nome = "indice", descricao = "o índice do elemento do vetor que será obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public double obter_propriedade_tipo_real_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            Object valor = obter_propriedade_em_vetor(endereco, propriedade, indice);
            return Objeto.obterPropriedadeReal(valor);
        } catch (ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
                ,
                @DocumentacaoParametro(nome = "indice", descricao = "o índice do elemento do vetor que será obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int obter_propriedade_tipo_inteiro_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            Object valor = obter_propriedade_em_vetor(endereco, propriedade, indice);
            return Objeto.obterPropriedadeInteiro(valor);
        } catch (ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o valor de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
                ,
                @DocumentacaoParametro(nome = "indice", descricao = "o índice do elemento do vetor que será obtido"),},
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String obter_propriedade_tipo_cadeia_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            Object valor = obter_propriedade_em_vetor(endereco, propriedade, indice);
            return Objeto.obterPropriedadeCadeia(valor);
        } catch (ClassCastException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém o tamanho de um vetor armazenado no objeto do endereço informado",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "a descrição da propriedade que terá o valor obtido")
            },
            retorno = "o valor da propriedade informada",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int obter_tamanho_vetor_propriedade(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        int tamanhoVetor = obterVetorEmPropriedadeModoSeguro(endereco, propriedade,
                (array) -> {
                    return array.size();
                });
        return tamanhoVetor;
    }

    @DocumentacaoFuncao(
            descricao
            = "Libera o objeto do endereço informado da memória",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
            },
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void liberar_objeto(int endereco) throws ErroExecucaoBiblioteca, InterruptedException {
        cacheObjetos.liberarObjeto(endereco);
    }

    @DocumentacaoFuncao(
            descricao
            = "Libera todos os objetos previamente armazenados na memória",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public void liberar() throws ErroExecucaoBiblioteca, InterruptedException {
        cacheObjetos.liberar();
    }

    @DocumentacaoFuncao(
            descricao
            = "Obtém a cadeia que representa o objeto inteiro no formato JSON (JavaScript Object Notation)",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
            },
            referencia = "https://pt.wikipedia.org/wiki/JSON",
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public String obter_json(int endereco) throws ErroExecucaoBiblioteca, InterruptedException {
        try {
            return cacheObjetos.obterObjeto(endereco).obterJson();
        } catch (JsonProcessingException ex) {
            throw new ErroExecucaoBiblioteca("Não foi possível obter o JSON deste objeto.");
        }
    }

    @DocumentacaoFuncao(
            descricao
            = "Identifica se o objeto contém ou não a propriedade informada",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "propriedade utilizada para verificação")
            },
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public boolean contem_propriedade(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        return cacheObjetos.obterObjeto(endereco).contemPropriedade(propriedade);
    }

    @DocumentacaoFuncao(
            descricao
            = "Identifica o tipo da propriedade informada",
            parametros
            = {
                @DocumentacaoParametro(nome = "endereco", descricao = "o endereço onde o objeto foi armazenado")
                ,
                @DocumentacaoParametro(nome = "propriedade", descricao = "propriedade utilizada para verificação")
            },
            autores
            = {
                @Autor(nome = "Gabriel Schade", email = "gabrielschade@univali.br")
            }
    )
    public int tipo_propriedade(int endereco, String propriedade) throws ErroExecucaoBiblioteca, InterruptedException {
        int tipo = 0;
        Object valor;
        try {
            valor = cacheObjetos.obterObjeto(endereco).obterPropriedade(propriedade);
            if (valor instanceof String) {
                if (((String) valor).length() == 1) {
                    tipo = TIPO_CARACTER;
                } else {
                    tipo = TIPO_CADEIA;
                }
            } else if (valor instanceof Integer) {
                tipo = TIPO_INTEIRO;
            } else if (valor instanceof Double) {
                tipo = TIPO_REAL;
            } else if (valor instanceof Boolean) {
                tipo = TIPO_LOGICO;
            } else if (valor instanceof HashMap) {
                tipo = TIPO_OBJETO;
            } else if (valor instanceof ArrayList) {
                tipo = TIPO_VETOR;
            }
            return tipo;
        } catch (InvalidAttributeValueException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }

    }

    private Object obter_propriedade_em_vetor(int endereco, String propriedade, int indice) throws ErroExecucaoBiblioteca {
        Object retorno = obterVetorEmPropriedadeModoSeguro(endereco, propriedade,
                (array) -> {
                    if (indice < 0 || indice > array.size() - 1) {
                        return null;
                    } else {
                        return array.get(indice);
                    }
                });
        if (retorno == null) {
            throw new ErroExecucaoBiblioteca(
                    "Você tentou acessar um índice de vetor inválido.\n"
                            + "O índice deve ser menor que o número de elementos que o vetor possui.\n"
                            + "Por exemplo, se foi declarado um vetor com 5 elementos (inteiro vetor[5]), o maior índice possível é 4.\n"
                            + "Além disso, o índice de um vetor não pode ser negativo.");
        }
        return retorno;
    }

    private <T> T obterVetorEmPropriedadeModoSeguro(int endereco, String propriedade, Function<ArrayList, T> funcao) throws ErroExecucaoBiblioteca {
        try {
            Objeto objeto = cacheObjetos.obterObjeto(endereco);
            Object propriedade_objeto = objeto.obterPropriedade((String) propriedade);
            if (propriedade_objeto instanceof ArrayList) {
                ArrayList array = (ArrayList) propriedade_objeto;
                return funcao.apply(array);
            } else {
                throw new ErroExecucaoBiblioteca("A propriedade \"".concat(propriedade).concat("\" não é um vetor."));
            }
        } catch (InvalidAttributeValueException ex) {
            throw new ErroExecucaoBiblioteca(ex.getMessage());
        }
    }

}
