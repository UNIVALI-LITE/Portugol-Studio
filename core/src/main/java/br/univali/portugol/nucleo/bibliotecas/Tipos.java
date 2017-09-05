package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz Fernando Noschang
 */
@PropriedadesBiblioteca(tipo = TipoBiblioteca.COMPARTILHADA)
@DocumentacaoBiblioteca(descricao = "Esta biblioteca contém funções que permitem converter os tipos de dado do Portugol entre si", versao = "1.0")
public final class Tipos extends Biblioteca
{
    private static final Pattern PADRAO_INTEIRO_NOTACAO_HEXADECIMAL = Pattern.compile("^(0x|0X)?([0-9]|[a-f]|[A-F])+$");
    private static final Pattern PADRAO_INTEIRO_NOTACAO_BINARIA = Pattern.compile("^(0b|0B)?[0-1]+$");
    private static final Pattern PADRAO_INTEIRO_NOTACAO_DECIMAL = Pattern.compile("^-?\\d+$");
    private static final Pattern PADRAO_REAL = Pattern.compile("^-?\\d+\\.\\d+$");
    private static final Pattern PADRAO_LOGICO = Pattern.compile("^verdadeiro|falso$");
    
    @DocumentacaoFuncao
    (
        descricao = 
            
              "Verifica se a <tipo>cadeia</tipo> passada por parâmetro representa um valor do tipo <tipo>inteiro</tipo> "
            + "escrito na notação definida pelo parâmetro <param>base</param>",
        
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> a ser verificada"),
            
            @DocumentacaoParametro
            (
                nome = "base", 
                
                descricao = 
            
                      "a base que deverá ser assumida ao realizar a verificação. Os valores possíveis são: "
                    + "2, 10 e 16.\n\n"

                    + "O valor 2 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação binária.\n"
                    + "Ex.: 0b1001; 01101001; 101; 0B1101.\n\n"

                    + "O valor 10 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação decimal.\n"
                    + "Ex.: 52; -34; 0; 71.\n\n"

                    + "O valor 16 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação hexadecimal.\n"
                    + "Ex.: 0xFF5AC; 0XDf5Ac01B; A0B551ce; ff00ff.\n\n"

                    + "Caso a base informada seja diferente de qualquer um destes valores, será gerado um erro de execução."            
            
            )
        },
        retorno = "<tipo>verdadeiro</tipo> se a <tipo>cadeia</tipo> representar um valor do tipo <tipo>inteiro</tipo>, caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean cadeia_e_inteiro(String cad, int base) throws ErroExecucaoBiblioteca, InterruptedException
    {
        switch (base)
        {
            case 2: return PADRAO_INTEIRO_NOTACAO_BINARIA.matcher(cad).find();
            case 10: return PADRAO_INTEIRO_NOTACAO_DECIMAL.matcher(cad).find();
            case 16: return PADRAO_INTEIRO_NOTACAO_HEXADECIMAL.matcher(cad).find();
        }
        
        throw new ErroExecucaoBiblioteca(String.format("A base informada (%d) é inválida, a base deve ser um dos seguintes valores: 2; 10; 16", base));
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Verifica se a <tipo>cadeia</tipo> passada por parâmetro representa um valor do tipo <tipo>real</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> a ser verificada")
        },
        retorno = "<tipo>verdadeiro</tipo> se a <tipo>cadeia</tipo> representar um valor do tipo <tipo>real</tipo>, caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean cadeia_e_real(String cad) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return PADRAO_REAL.matcher(cad).find();
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Verifica se a <tipo>cadeia</tipo> passada por parâmetro representa um valor do tipo <tipo>logico</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> a ser verificada")
        },
        retorno = "<tipo>verdadeiro</tipo> se a <tipo>cadeia</tipo> representar um valor do tipo <tipo>logico</tipo>, caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean cadeia_e_logico(String cad) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return PADRAO_LOGICO.matcher(cad).find();
    }

    @DocumentacaoFuncao
    (
        descricao = "Verifica se a <tipo>cadeia</tipo> passada por parâmetro representa um valor do tipo <tipo>caracter</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "cad", descricao = "a <tipo>cadeia</tipo> a ser verificada")
        },
        retorno = "<tipo>verdadeiro</tipo> se a <tipo>cadeia</tipo> representar um valor do tipo <tipo>caracter</tipo>, caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean cadeia_e_caracter(String cad) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return cad.length() == 1;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>cadeia</tipo> em um valor do tipo <tipo>caracter</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>caracter</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public char cadeia_para_caracter(String valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        if (valor.length() == 1)
        {
            return valor.charAt(0);
        }
        
        throw new ErroExecucaoBiblioteca(String.format("o valor '%s' não é um caracter válido", valor));
    }

    @DocumentacaoFuncao
    (
        descricao = 
            
              "Converte um valor do tipo <tipo>cadeia</tipo> em um valor do tipo <tipo>inteiro</tipo> " 
            + "utilizando a <param>base</param> informada",
        
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido"),
            @DocumentacaoParametro
            (
                nome = "base", 
                
                descricao = 
            
                    "a notação em que o número <tipo>inteiro</tipo> está representado na <tipo>cadeia</tipo>. Os valores "
                    +"possíveis são: 2, 10 e 16.\n\n"

                    + "O valor 2 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação binária.\n"
                    + "Ex.: 0b1001; 01101001; 101; 0B1101.\n\n"

                    + "O valor 10 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação decimal.\n"
                    + "Ex.: 52; -34; 0; 71.\n\n"

                    + "O valor 16 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação hexadecimal.\n"
                    + "Ex.: 0xFF5AC; 0XDf5Ac01B; A0B551ce; ff00ff.\n\n"

                    + "Caso a base informada seja diferente de qualquer um destes valores, será gerado um erro de execução."
            )
        },
        retorno = "um valor do tipo <tipo>inteiro</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public int cadeia_para_inteiro(String valor, int base) throws ErroExecucaoBiblioteca, InterruptedException
    {      
        if (base == 2 || base == 10 || base == 16)
        {
            try
            {
                if (base == 16)
                {
                    valor = valor.replaceFirst("0X", "").replaceFirst("0x", "");
                }
                
                if (base == 2)
                {
                    valor = valor.replaceFirst("0B", "").replaceFirst("0b", "");
                }
                
                Long val = Long.parseLong(valor, base);
                 
                if (val >= 2147483648L)
                {
                    val -= (1L << 32);
                }
                return val.intValue();
            }
            catch (Exception ex)
            {
                throw new ErroExecucaoBiblioteca(String.format("o valor '%s' não é um número inteiro válido", valor));
            }
        }
        
        throw new ErroExecucaoBiblioteca(String.format("A base informada (%d) é inválida, a base deve ser um dos seguintes valores: 2; 10; 16", base));
    }

    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>cadeia</tipo> em um valor do tipo <tipo>real</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>real</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public double cadeia_para_real(String valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        try
        {
            return Double.parseDouble(valor);
        }
        catch (NumberFormatException ex)
        {
            throw new ErroExecucaoBiblioteca(String.format("o valor '%s' não é um número real válido", valor));
        }
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>cadeia</tipo> em um valor do tipo <tipo>logico</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>logico</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean cadeia_para_logico(String valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        switch (valor)
        {
            case "verdadeiro": return true;
            case "falso": return false;
        }
        
        throw new ErroExecucaoBiblioteca(String.format("o valor '%s' não é um valor lógico válido", valor));
    }

    @DocumentacaoFuncao
    (
        descricao = "Verifica se o <tipo>inteiro</tipo> passado por parâmetro representa um valor do tipo <tipo>caracter</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "int", descricao = "o <tipo>inteiro</tipo> a a ser verificado")
        },
        retorno = "<tipo>verdadeiro</tipo> se o <tipo>inteiro</tipo> representar um valor do tipo <tipo>caracter</tipo>, caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean inteiro_e_caracter(int _int) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return (_int >= 0 && _int <= 9);
    }    
    
    @DocumentacaoFuncao
    (
        descricao = 
            
              "Converte um valor do tipo <tipo>inteiro</tipo> em um valor do tipo <tipo>cadeia</tipo> "
            + "utilizando a <param>base</param> informada",
        
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido"),
            @DocumentacaoParametro
            (
                nome = "base", 
                
                descricao = 
            
                    "a notação em que o número <tipo>inteiro</tipo> está representado na <tipo>cadeia</tipo>. Os valores "
                    +"possíveis são: 2, 10 e 16.\n\n"

                    + "O valor 2 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação binária.\n"
                    + "Ex.: 0b1001; 01101001; 101; 0B1101.\n\n"

                    + "O valor 10 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação decimal.\n"
                    + "Ex.: 52; -34; 0; 71.\n\n"

                    + "O valor 16 assume que a <tipo>cadeia</tipo> representa um número <tipo>inteiro</tipo> escrito em notação hexadecimal.\n"
                    + "Ex.: 0xFF5AC; 0XDf5Ac01B; A0B551ce; ff00ff.\n\n"

                    + "Caso a base informada seja diferente de qualquer um destes valores, será gerado um erro de execução."
            )            
        },
        retorno = "um valor do tipo <tipo>cadeia</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String inteiro_para_cadeia(int valor, int base) throws ErroExecucaoBiblioteca, InterruptedException
    {     
        if (base == 2 || base == 10 || base == 16)
        {
            try
            {
                switch (base)
                {   
                    case 2: return lpad(32, Integer.toBinaryString(valor));
                    case 10: return Integer.toString(valor);
                    case 16: return lpad(8, Integer.toHexString(valor).toUpperCase());
                }
            }
            catch (Exception ex)
            {
                throw new ErroExecucaoBiblioteca(String.format("o valor '%s' não é um número inteiro válido", valor));
            }
        }
        
        throw new ErroExecucaoBiblioteca(String.format("A base informada (%d) é inválida, a base deve ser um dos seguintes valores: 2; 10; 16", base));
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>inteiro</tipo> em um valor do tipo <tipo>caracter</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>caracter</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public char inteiro_para_caracter(int valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        if (valor >= 0 && valor <= 9)
        {
            return String.valueOf(valor).charAt(0);
        }
        
        throw new ErroExecucaoBiblioteca(String.format("o valor '%d' não é um caracter válido", valor));
    }

    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>inteiro</tipo> em um valor do tipo <tipo>logico</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "<tipo>falso</tipo> se o valor for menor ou igual a 0, <tipo>verdadeiro</tipo> se o valor for maior ou igual a 1",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean inteiro_para_logico(int valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        if (valor <= 0)            
        {
            return false;
        }
        
        return true;
    }    
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>inteiro</tipo> em um valor do tipo <tipo>real</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>real</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public double inteiro_para_real(int valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return valor;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Verifica se o <tipo>caracter</tipo> passado por parâmetro representa um valor do tipo <tipo>inteiro</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "car", descricao = "o <tipo>caracter</tipo> a ser verificado")
        },
        retorno = "<tipo>verdadeiro</tipo> se o <tipo>caracter</tipo> representar um valor do tipo <tipo>inteiro</tipo>, caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean caracter_e_inteiro(char car) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return cadeia_e_inteiro(new String(new char[]{ car }), 10);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Verifica se o <tipo>caracter</tipo> passado por parâmetro representa um valor do tipo <tipo>logico</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "car", descricao = "o <tipo>caracter</tipo> a ser verificado")
        },
        retorno = "<tipo>verdadeiro</tipo> se o <tipo>caracter</tipo> for um dos seguintes valores: 'S', 's', 'N' ou 'n'. Caso contrário, retorna <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean caracter_e_logico(char car) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return (car == 'S' || car == 's' || car == 'N' || car == 'n');
    }
    
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>caracter</tipo> em um valor do tipo <tipo>cadeia</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>cadeia</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String caracter_para_cadeia(char valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return new String(new char[]{ valor });
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>caracter</tipo> em um valor do tipo <tipo>inteiro</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>caracter</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public int caracter_para_inteiro(char valor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return cadeia_para_inteiro(caracter_para_cadeia(valor), 10);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>caracter</tipo> em um valor do tipo <tipo>logico</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "<tipo>verdadeiro</tipo> se o <tipo>caracter</tipo> for um dos seguintes valores: 'S', 's'; <tipo>falso</tipo> se o <tipo>caracter</tipo> for um dos seguintes valores: 'N', 'n'",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public boolean caracter_para_logico(char valor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (valor == 'S' || valor == 's')
        {
            return true;
        }
        else if (valor == 'N' || valor == 'n')
        {
            return false;
        }
        
        throw new ErroExecucaoBiblioteca(String.format("o valor '%s' não é um valor lógico válido", valor));
    }    

    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>logico</tipo> em um valor do tipo <tipo>cadeia</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>cadeia</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String logico_para_cadeia(boolean valor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return (valor)? "verdadeiro" : "falso";
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>logico</tipo> em um valor do tipo <tipo>inteiro</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "1 se o valor passado por parâmetro for <tipo>verdadeiro</tipo>, 0 se o valor passado por parâmetro for <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public int logico_para_inteiro(boolean valor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return (valor)? 1 : 0;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>logico</tipo> em um valor do tipo <tipo>caracter</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "'S' se o valor passado por parâmetro for <tipo>verdadeiro</tipo>, 'N' se o valor passado por parâmetro for <tipo>falso</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public char logico_para_caracter(boolean valor) throws ErroExecucaoBiblioteca, InterruptedException
    {
        return (valor)? 'S' : 'N';
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>real</tipo> em um valor do tipo <tipo>cadeia</tipo>",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>cadeia</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public String real_para_cadeia(double valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return String.valueOf(valor);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Converte um valor do tipo <tipo>real</tipo> em um valor do tipo <tipo>inteiro</tipo>. Se o valor <tipo>real</tipo> tiver uma porção fracionária (Ex.: 9.56), o valor será truncado (Ex.: 9)",
        parametros = 
        {
            @DocumentacaoParametro(nome = "valor", descricao = "o valor a ser convertido")
        },
        retorno = "um valor do tipo <tipo>inteiro</tipo>",
        autores = 
        {
            @Autor(nome = "Luiz Fernando Noschang", email = "noschang@univali.br")
        }        
    )    
    public int real_para_inteiro(double valor) throws ErroExecucaoBiblioteca, InterruptedException
    {        
        return (int) valor;
    }
    
    private String lpad(int quantidade, String cadeia) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if (cadeia.length() < quantidade)
        {
            StringBuilder sb = new StringBuilder();
            
            int diferenca = quantidade - cadeia.length();
            
            for (int i = 1; i <= diferenca; i++)
            {
                sb.append("0");
            }
            
            cadeia = sb.append(cadeia).toString();
        }
        
        return cadeia;
    }    
}
