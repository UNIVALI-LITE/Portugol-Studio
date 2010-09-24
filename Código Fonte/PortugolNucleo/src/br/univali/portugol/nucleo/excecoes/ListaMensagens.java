package br.univali.portugol.nucleo.excecoes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Luiz Fernando Noschang
 * @since 25/08/2010
 * @version 1.0.0
 *
 */

public class ListaMensagens implements Iterable<Mensagem>
{
    private int numeroErros = 0;
    private int numeroAvisos = 0;
    private int numeroMensagens = 0;

    private List<Mensagem> mensagens;

    public ListaMensagens()
    {
        mensagens = new ArrayList<Mensagem>();
    }

    public void adicionar(Aviso aviso)
    {
        adicionarMensagem(aviso);
        numeroAvisos = numeroAvisos + 1;
    }

    public void adicionar(Erro erro)
    {
        adicionarMensagem(erro);
        numeroErros = numeroErros + 1;
    }

    private void adicionarMensagem(Mensagem mensagem)
    {
        mensagens.add(mensagem);
        numeroMensagens = numeroMensagens + 1;
    }

    public int getNumeroErros()
    {
        return numeroErros;
    }

    public int getNumeroMensagens()
    {
        return numeroMensagens;
    }

    public int getNumeroAvisos()
    {
        return numeroAvisos;
    }

    @Override
    public Iterator<Mensagem> iterator()
    {
        return mensagens.iterator();
    }
}
