package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoConstante;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.util.Calendar;

@PropriedadesBiblioteca(tipo = TipoBiblioteca.COMPARTILHADA)
@DocumentacaoBiblioteca(descricao = "Esta biblioteca é utilizada para retornar a data e(ou) hora do computador", versao = "1.0")
public final class Calendario extends Biblioteca{
    
    @DocumentacaoConstante(descricao = "constante que representa o 'Domingo'")
    public static final int DOMINGO = 1;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Segunda-Feira'")
    public static final int SEGUNDA_FEIRA = 2;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Terça-Feira'")
    public static final int TERCA_FEIRA = 3;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Quarta-Feira'")
    public static final int QUARTA_FEIRA = 4;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Quinta-Feira'")
    public static final int QUINTA_FEIRA = 5;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Sexta-Feira'")
    public static final int SEXTA_FEIRA = 6;
    
    @DocumentacaoConstante(descricao = "constante que representa o   'Sábado'")
    public static final int SABADO = 7;
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera o dia atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com o dia com dois digitos, se forem menores que 10 apenas com um digito. Ex: 26."
    )
    public int dia_mes_atual() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera o dia da semana de 1 a 7.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com o dia da semana. Ex: 1, para Domingo."
    )
    public int dia_semana_atual() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera o mes atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com o mes com dois digitos, se forem menores que 10 apenas com um digito. Ex: 12."
    )
    public int mes_atual() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera o ano atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com o ano. Ex: 2012."
    )
    public int ano_atual() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera os digitos da hora atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com a hora atual no formato 24h com dois digitos, se forem menores que 10 apenas com um digito. Ex: 22."
    )
    public int hora_atual() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera os digitos do minuto atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com os minutos atuais com dois digitos, se forem menores que 10 apenas com um digito. Ex: 45."
    )
    public int minuto_atual() throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera os digitos dos segundos atuais do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com os segundos atuais com dois digitos, se forem menores que 10 apenas com um digito. Ex: 32."
    )
    public int segundo_atual()  throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.SECOND);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera os digitos dos milisegundos atuais do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com os milisegundos atuais, com um, dois ou três digitos. Ex: 426."
    )
    public int milisegundo_atual()  throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.MILLISECOND);
    }
    
    @DocumentacaoFuncao
    (
        descricao = "De acordo com o valor de 1 a 7 informado retornará um dia da semana completo.",
        parametros =
        {
            @DocumentacaoParametro(nome = "numero_dia", descricao = "um <tipo>inteiro</tipo> referente a um dia da semana"),
            @DocumentacaoParametro(nome = "caixa_alta", descricao = "<tipo>logico</tipo> para retorno em em caracteres maiúsculos"),
            @DocumentacaoParametro(nome = "caixa_baixa", descricao = "<tipo>logico</tipo> para retorno em em caracteres minúsculos")
        },
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "uma <tipo>cadeia</tipo> com o dia da semana completo. Ex: Segunda-Feira."
    )
    public String dia_semana_completo(int numero_dia, boolean caixa_alta, boolean caixa_baixa) throws ErroExecucaoBiblioteca, InterruptedException
    {
        String[] dias = {"Domingo","Segunda-Feira","Terça-Feira","Quarta-Feira","Quinta-Feira","Sexta-Feira","Sabado"};
        if(numero_dia > 0 && numero_dia < 8){
            switch(numero_dia){
                case 1:
                    if(caixa_alta){
                        return dias[0].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[0].toLowerCase();
                    }
                    return dias[0];
                case 2:
                    if(caixa_alta){
                        return dias[1].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[1].toLowerCase();
                    }
                    return dias[1];
                case 3:
                    if(caixa_alta){
                        return dias[2].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[2].toLowerCase();
                    }
                    return dias[2];
                case 4:
                    if(caixa_alta){
                        return dias[3].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[3].toLowerCase();
                    }
                    return dias[3];
                case 5:
                    if(caixa_alta){
                        return dias[4].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[4].toLowerCase();
                    }
                    return dias[4];
                case 6:
                    if(caixa_alta){
                        return dias[5].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[5].toLowerCase();
                    }
                    return dias[5];
                case 7:
                    if(caixa_alta){
                        return dias[6].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[6].toLowerCase();
                    }
                    return dias[6];
                default:
                    return "Dia invalido";
            }
        }else{
            throw new ErroExecucaoBiblioteca("'"+ numero_dia + "' não corresponde a um dia da semana válido.");
        }
    }
    
    @DocumentacaoFuncao
    (
        descricao = "De acordo com o valor de 1 a 7 informado retornará um dia da semana de forma curta.",
        parametros =
        {
            @DocumentacaoParametro(nome = "numero_dia", descricao = "um <tipo>inteiro</tipo> referente a um dia da semana"),
            @DocumentacaoParametro(nome = "caixa_alta", descricao = "<tipo>logico</tipo> para retorno em em caracteres maiúsculos"),
            @DocumentacaoParametro(nome = "caixa_baixa", descricao = "<tipo>logico</tipo> para retorno em em caracteres minúsculos")
        },
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "uma <tipo>cadeia</tipo> com o dia da semana de forma curta. Ex: Segunda, para Segunda-Feira."
    )
    public String dia_semana_curto(int numero_dia, boolean caixa_alta, boolean caixa_baixa) throws ErroExecucaoBiblioteca, InterruptedException
    {
        String[] dias = {"Domingo","Segunda","Terça","Quarta","Quinta","Sexta","Sabado"};
        if(numero_dia > 0 && numero_dia < 8){
            switch(numero_dia){
                case 1:
                    if(caixa_alta){
                        return dias[0].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[0].toLowerCase();
                    }
                    return dias[0];
                case 2:
                    if(caixa_alta){
                        return dias[1].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[1].toLowerCase();
                    }
                    return dias[1];
                case 3:
                    if(caixa_alta){
                        return dias[2].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[2].toLowerCase();
                    }
                    return dias[2];
                case 4:
                    if(caixa_alta){
                        return dias[3].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[3].toLowerCase();
                    }
                    return dias[3];
                case 5:
                    if(caixa_alta){
                        return dias[4].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[4].toLowerCase();
                    }
                    return dias[4];
                case 6:
                    if(caixa_alta){
                        return dias[5].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[5].toLowerCase();
                    }
                    return dias[5];
                case 7:
                    if(caixa_alta){
                        return dias[6].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[6].toLowerCase();
                    }
                    return dias[6];
                default:
                    return "Dia invalido";
            }
        }else{
            throw new ErroExecucaoBiblioteca("'"+ numero_dia + "' não corresponde a um dia da semana válido.");
        }
    }
    
    @DocumentacaoFuncao
    (
        descricao = "De acordo com o valor de 1 a 7 informado retornará um dia da semana abreviado.",
        parametros =
        {
            @DocumentacaoParametro(nome = "numero_dia", descricao = "um <tipo>inteiro</tipo> referente a um dia da semana"),
            @DocumentacaoParametro(nome = "caixa_alta", descricao = "<tipo>logico</tipo> para retorno em em caracteres maiúsculos"),
            @DocumentacaoParametro(nome = "caixa_baixa", descricao = "<tipo>logico</tipo> para retorno em em caracteres minúsculos")
        },
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "uma <tipo>cadeia</tipo> com o dia da semana abreviado. Ex: Seg, para Segunda-Feira."
    )
    public String dia_semana_abreviado(int numero_dia, boolean caixa_alta, boolean caixa_baixa) throws ErroExecucaoBiblioteca, InterruptedException
    {
        String[] dias = {"Dom","Seg","Ter","Qua","Qui","Sex","Sab"};
        if(numero_dia > 0 && numero_dia < 8){
            switch(numero_dia){
                case 1:
                    if(caixa_alta){
                        return dias[0].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[0].toLowerCase();
                    }
                    return dias[0];
                case 2:
                    if(caixa_alta){
                        return dias[1].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[1].toLowerCase();
                    }
                    return dias[1];
                case 3:
                    if(caixa_alta){
                        return dias[2].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[2].toLowerCase();
                    }
                    return dias[2];
                case 4:
                    if(caixa_alta){
                        return dias[3].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[3].toLowerCase();
                    }
                    return dias[3];
                case 5:
                    if(caixa_alta){
                        return dias[4].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[4].toLowerCase();
                    }
                    return dias[4];
                case 6:
                    if(caixa_alta){
                        return dias[5].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[5].toLowerCase();
                    }
                    return dias[5];
                case 7:
                    if(caixa_alta){
                        return dias[6].toUpperCase();
                    }else if(caixa_baixa){
                        return dias[6].toLowerCase();
                    }
                    return dias[6];
                default:
                    return "Dia invalido";
            }
        }else{
            throw new ErroExecucaoBiblioteca("'"+ numero_dia + "' não corresponde a um dia da semana válido.");
        }
    }
}
