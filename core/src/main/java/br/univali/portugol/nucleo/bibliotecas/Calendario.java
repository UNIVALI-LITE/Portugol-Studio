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
    
    //dias
    @DocumentacaoConstante(descricao = "constante que representa o 'Domingo'")
    public static final int DIA_DOMINGO = 1;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Segunda-Feira'")
    public static final int DIA_SEGUNDA_FEIRA = 2;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Terça-Feira'")
    public static final int DIA_TERCA_FEIRA = 3;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Quarta-Feira'")
    public static final int DIA_QUARTA_FEIRA = 4;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Quinta-Feira'")
    public static final int DIA_QUINTA_FEIRA = 5;
    
    @DocumentacaoConstante(descricao = "constante que representa a 'Sexta-Feira'")
    public static final int DIA_SEXTA_FEIRA = 6;
    
    @DocumentacaoConstante(descricao = "constante que representa o   'Sábado'")
    public static final int DIA_SABADO = 7;
    
    
    //meses
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Janeiro'")
    public static final int MES_JANEIRO = 1;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Fevereiro'")
    public static final int MES_FEVEREIRO = 2;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Março'")
    public static final int MES_MARCO = 3;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Abril'")
    public static final int MES_ABRIL = 4;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Maio'")
    public static final int MES_MAIO = 5;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Junho'")
    public static final int MES_JUNHO = 6;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Julho'")
    public static final int MES_JULHO = 7;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Agosto'")
    public static final int MES_AGOSTO = 8;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Setembro'")
    public static final int MES_SETEMBRO = 9;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Outubro'")
    public static final int MES_OUTUBRO = 10;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Novembro'")
    public static final int MES_NOVEMBRO = 11;
    
    @DocumentacaoConstante(descricao = "constante que representa o mês de 'Dezembro'")
    public static final int MES_DEZEMBRO = 12;
    
    //funcoes
    @DocumentacaoFuncao
    (
        descricao = "Recupera o dia no mês atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com o dia no mês com dois digitos, se forem menores que 10 apenas com um digito. Ex: 26."
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
        descricao = "Recupera o mês atual do computador de 1 a 12.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com o mês com dois digitos, se forem menores que 10 apenas com um digito. Ex: 10."
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
        parametros = 
        {
            @DocumentacaoParametro(nome = "formato_12h", descricao = "um <tipo>logico</tipo> que se verdadeiro o retorno será no formato 12h se falso será em 24h")
        },
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "um <tipo>inteiro</tipo> com a hora atual no formato 12h ou 24h com dois digitos, se forem menores que 10 apenas com um digito. Ex: 22 para 24h, se o parâmetro for falso ou 10 para 12h, se o parâmetro for verdadeiro."
    )
    public int hora_atual(boolean formato_12h) throws ErroExecucaoBiblioteca, InterruptedException
    {
        if(!formato_12h)
            return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        else
            return Calendar.getInstance().get(Calendar.HOUR);
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
