package br.univali.ps.ui.acoes;

import br.univali.ps.dominio.PortugolDocumento;
import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.Editor;
import br.univali.ps.ui.util.FileHandle;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

public class AcaoSalvarArquivo extends Acao
{
    private PortugolDocumento documento;
    private AcaoSalvarComo salvarComo;
    private int posicaoCursor;
    private Editor editorCodigoFonte;

    public AcaoSalvarArquivo()
    {
        super("Arquivo salvo com sucesso");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }

    /* GAMBIARRA. O certo aqui seria não passar o editor de código fonte, mas adotei como solução temporária */
    public void configurar(PortugolDocumento documento, AcaoSalvarComo acaoSalvarComo, Editor editorCodigoFonte)
    {
        this.documento = documento;
        this.salvarComo = acaoSalvarComo;
        this.editorCodigoFonte = editorCodigoFonte;
    }

    public void setPosicaoCursor(int posicaoCursor)
    {
        this.posicaoCursor = posicaoCursor;
    }

    public PortugolDocumento getDocumento()
    {
        return documento;
    }

    @Override
    protected void executar(ActionEvent e) throws Exception
    {
        try
        {
            String texto = documento.getText(0, documento.getLength());
            texto = inserirInformacoesPortugolStudio(texto);

            if (documento.getFile() != null)
            {
                FileHandle.save(texto, documento.getFile());
                documento.setChanged(false);
            }
            else
            {
                salvarComo.actionPerformed(e);
            }
        }
        catch (BadLocationException ex)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
        catch (Exception ex)
        {
            PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(ex);
        }
    }

    private String inserirInformacoesPortugolStudio(String texto)
    {
        StringBuilder sb = new StringBuilder(texto);

        sb.append("\n/* $$$ Portugol Studio $$$ ");
        sb.append("\n * ");
        sb.append("\n * Esta seção do arquivo guarda informações do Portugol Studio.");
        sb.append("\n * Você pode apagá-la se estiver utilizando outro editor.");
        sb.append("\n * ");

        inserirInformacoesCursor(sb);
        inserirInformacoesDobramentoCodigo(sb);

        sb.append("\n */");

        return sb.toString();
    }

    private void inserirInformacoesCursor(StringBuilder sb)
    {
        if (posicaoCursor >= 0)
        {
            sb.append(String.format("\n * @POSICAO-CURSOR = %d; ", posicaoCursor));
        }
    }

    private void inserirInformacoesDobramentoCodigo(StringBuilder sb)
    {
        List<Integer> linhasCodigoDobradas = editorCodigoFonte.getLinhasCodigoDobradas();
        
        if (linhasCodigoDobradas != null && !linhasCodigoDobradas.isEmpty())
        {
            StringBuilder linhas = new StringBuilder("[");

            for (int i = 0; i < linhasCodigoDobradas.size(); i++)
            {
                linhas.append(linhasCodigoDobradas.get(i).toString());

                if (i < linhasCodigoDobradas.size() - 1)
                {
                    linhas.append(", ");
                }
            }

            linhas.append("]");

            sb.append(String.format("\n * @DOBRAMENTO-CODIGO = %s;", linhas));
        }
    }
}
