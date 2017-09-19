package br.univali.ps.nucleo;

/**
 * @author Elieser
 */
public interface Mutex {

    InstanciaPortugolStudio conectarInstanciaPortugolStudio() throws ErroConexaoInstancia;

    boolean existeUmaInstanciaExecutando();

    void finalizar();

    void inicializar() throws ErroCriacaoMutex;
    
    public static final class ErroCriacaoMutex extends Exception
    {
        public ErroCriacaoMutex(Throwable causa)
        {
            super("Erro ao criar o Mutex do Portugol Studio", causa);
        }
    }
    
    public static final class ErroConexaoInstancia extends Exception
    {
        public ErroConexaoInstancia(Throwable causa)
        {
            super("Erro ao conectar com a instância do Portugol Studio. Provavelmente o aplicativo fechou inesperadamente", causa);
        }
    }

    
}
