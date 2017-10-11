package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import java.util.Calendar;

@PropriedadesBiblioteca(tipo = TipoBiblioteca.COMPARTILHADA)
@DocumentacaoBiblioteca(descricao = "Esta biblioteca é utilizada para retornar a data e(ou) hora do computador", versao = "1.0")
public final class Calendario extends Biblioteca{
    
    @DocumentacaoFuncao
    (
        descricao = "Recupera o dia atual do computador.",
        autores = 
        {
            @Autor(nome = "Rafael Ferreira Costa", email = "rafaelcosta@edu.univali.br")
        },
        retorno = "o dia com dois digitos, se forem menores que 10 apenas com um digito. Ex: 26."
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
        retorno = "o dia da semana. Ex: 1, para Domingo."
    )
    public int dia_semana_atual()  throws ErroExecucaoBiblioteca, InterruptedException
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
        retorno = "o mes com dois digitos, se forem menores que 10 apenas com um digito. Ex: 12."
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
        retorno = "o ano. Ex: 2012."
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
        retorno = "a hora atual no formato 24h com dois digitos, se forem menores que 10 apenas com um digito. Ex: 22."
    )
    public int hora_atual()  throws ErroExecucaoBiblioteca, InterruptedException
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
        retorno = "os minutos atuais com dois digitos, se forem menores que 10 apenas com um digito. Ex: 45."
    )
    public int minuto_atual()  throws ErroExecucaoBiblioteca, InterruptedException
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
        retorno = "os segundos atuais com dois digitos, se forem menores que 10 apenas com um digito. Ex: 32."
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
        retorno = "os milisegundos atuais, com um, dois ou três digitos. Ex: 426."
    )
    public int milisegundo_atual()  throws ErroExecucaoBiblioteca, InterruptedException
    {
        return Calendar.getInstance().get(Calendar.MILLISECOND);
    }
    
}
