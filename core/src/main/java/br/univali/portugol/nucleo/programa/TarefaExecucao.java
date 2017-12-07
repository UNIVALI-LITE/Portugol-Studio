package br.univali.portugol.nucleo.programa;

import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.execucao.ModoEncerramento;
import br.univali.portugol.nucleo.execucao.ResultadoExecucao;
import br.univali.portugol.nucleo.execucao.TradutorRuntimeException;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroEstouroPilha;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroExecucaoNaoTratado;
import br.univali.portugol.nucleo.execucao.erros.mensagens.ErroMemoriaInsuficiente;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa uma tarefa para disparar a execução do programa com os
 * parâmetros e a estratégia selecionada. Futuramente podemos refatorar para
 * executar a partir de um pool de threads.
 */
final class TarefaExecucao implements Runnable
{
    private Programa programa;
    private final String[] parametros;
    private final ResultadoExecucao resultadoExecucao;

    public TarefaExecucao(Programa programa, String[] parametros)
    {
        this.programa = programa;
        this.parametros = parametros;
        this.resultadoExecucao = new ResultadoExecucao();
    }

    @Override
    public void run()
    {
        long horaInicialExecucao = System.currentTimeMillis();

        try
        {
            programa.notificarInicioExecucao();
            programa.inicializaBibliotecasIncluidas();
            programa.inicializar(); // reinicializa todas as variaveis antes de
                            // executar
            programa.executar(parametros);
        }
        catch (OutOfMemoryError erroMemoria)
        {
            ErroMemoriaInsuficiente erroExecucao = new ErroMemoriaInsuficiente();
            erroExecucao.setLinha(programa.getUltimaLinha());
            erroExecucao.setColuna(programa.getUltimaColuna());

            resultadoExecucao.setModoEncerramento(ModoEncerramento.ERRO);
            resultadoExecucao.setErro(erroExecucao);
        }
        catch (StackOverflowError e)
        {
            ErroExecucao erroExecucao = new ErroEstouroPilha();

            erroExecucao.setLinha(programa.getUltimaLinha());
            erroExecucao.setColuna(programa.getUltimaColuna());

            resultadoExecucao.setModoEncerramento(ModoEncerramento.ERRO);
            resultadoExecucao.setErro(erroExecucao);
        }
        catch (RuntimeException e)
        {
            ErroExecucao erroExecucao = new ErroExecucaoNaoTratado(e);

            TradutorRuntimeException<? extends RuntimeException> tradutor = Programa.getTradutoresRuntimeException().get(e.getClass());

            if (tradutor != null)
            {
                erroExecucao = tradutor.traduzir(e, programa, programa.getUltimaLinha(), programa.getUltimaColuna());
            }

            if (erroExecucao.getLinha() == 0)
            {
                erroExecucao.setLinha(programa.getUltimaLinha());
            }

            if (erroExecucao.getColuna() == 0)
            {
                erroExecucao.setColuna(programa.getUltimaColuna());
            }

            resultadoExecucao.setModoEncerramento(ModoEncerramento.ERRO);
            resultadoExecucao.setErro(erroExecucao);
        }
        catch (ErroExecucao erroExecucao)
        {
            /*
             * Este tratamento de erros é legado da versão antiga do núcleo
             * que utilizava o Interpretador de código.
             *
             * Nesta nova versão com código compilado, este erro nunca será
             * jogado. No lugar do ErroExecucao, será sempre jogado um
             * RuntimeException, que deverá então ser tratado e traduzido de
             * acordo com o tipo de erro.
             *
             * Em algum momento, devemos mudar a assinatura do método
             * "executar" para que não jogue mais esta exceção. Não farei
             * agora porque teria que alterar o gerador de código Java e os
             * testes unitários.
             *
             */

            erroExecucao.setLinha(programa.getUltimaLinha());
            erroExecucao.setColuna(programa.getUltimaColuna());

            resultadoExecucao.setModoEncerramento(ModoEncerramento.ERRO);
            resultadoExecucao.setErro(erroExecucao);
        }

        catch (InterruptedException excecao)
        {
            resultadoExecucao.setModoEncerramento(ModoEncerramento.INTERRUPCAO);
        }
        catch (Exception excecao)
        {
            if (excecao.getCause() instanceof InterruptedException)
            {
                resultadoExecucao.setModoEncerramento(ModoEncerramento.INTERRUPCAO);
            }
            else
            {
                ErroExecucao erroExecucao = new ErroExecucaoNaoTratado(excecao);

                erroExecucao.setLinha(programa.getUltimaLinha());
                erroExecucao.setColuna(programa.getUltimaColuna());

                resultadoExecucao.setModoEncerramento(ModoEncerramento.ERRO);
                resultadoExecucao.setErro(erroExecucao);
            }
        }
        finally
        {
            try
            {
                programa.finalizaBibliotecasIncluidas();
            }
            catch (InterruptedException | ErroExecucaoBiblioteca ex)
            {
                Logger.getLogger(Programa.class.getName()).log(Level.SEVERE, "Não era pra acontecer", ex);
            }
        }

        resultadoExecucao.setTempoExecucao(System.currentTimeMillis() - horaInicialExecucao);

        programa.notificarEncerramentoExecucao(resultadoExecucao);

        programa.getObservadores().clear(); // remove todos os listeners quando termina de
                                // executar
    }

    public void continuar(Estado estado)
    {
        synchronized (programa.getLOCK())
        {
            if (programa.isLendo())
            {
                programa.setLeituraIgnorada(true);
            }

            programa.setEstado(estado);
            programa.getLOCK().notifyAll();
        }
    }
}
