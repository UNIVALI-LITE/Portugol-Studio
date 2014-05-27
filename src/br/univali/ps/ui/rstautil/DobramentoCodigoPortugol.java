package br.univali.ps.ui.rstautil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.folding.CurlyFoldParser;
import org.fife.ui.rsyntaxtextarea.folding.Fold;

/**
 *
 * @author Luiz Fernando
 */
public final class DobramentoCodigoPortugol extends CurlyFoldParser
{

    @Override
    public List getFolds(RSyntaxTextArea textArea)
    {
        List<Fold> folds = super.getFolds(textArea);

        for (Fold fold : folds)
        {
            ajustarOffset(fold, textArea);
        }

        return folds;
    }

    private void ajustarOffset(Fold fold, RSyntaxTextArea textArea)
    {
        try
        {
            int startOffset = getStartOffset(fold);
            int endOffset = getEndOffset(fold);

            String text = textArea.getText(startOffset, endOffset - startOffset);

            if (text.startsWith("{"))
            {
                int newOffset = getNewOffset(textArea, startOffset);
                
                setStartOffset(fold, newOffset, textArea);
            }
            
            for (int i = 0; i < fold.getChildCount(); i++)
            {
                ajustarOffset(fold.getChild(i), textArea);
            }
        }
        catch (BadLocationException excecao)
        {

        }
    }
    
    private int getNewOffset(RSyntaxTextArea textArea, final int startOffset)
    {
        try
        {
            int newOffset = startOffset;
            String text = textArea.getText(0, startOffset);
            
            for (int i = text.length() - 1; i >= 0; i--)
            {
                newOffset = newOffset - 1;
                
                if (text.charAt(i) != '\r' && text.charAt(i) != '\n' && text.charAt(i) != '\t' && text.charAt(i) != ' ')
                {
                    return newOffset;
                }
            }
        }
        catch (BadLocationException excecao)
        {
            
        }
        
        return startOffset;
    }

    private int getStartOffset(Fold fold)
    {
        try
        {
            Field field = Fold.class.getDeclaredField("startOffs");
            field.setAccessible(true);
            Position posicao = (Position) field.get(fold);

            return posicao.getOffset();
        }
        catch (NoSuchFieldException | SecurityException | IllegalAccessException excecao)
        {

        }

        return -1;
    }

    private int getEndOffset(Fold fold)
    {
        try
        {
            Field field = Fold.class.getDeclaredField("endOffs");
            field.setAccessible(true);
            Position posicao = (Position) field.get(fold);

            return posicao.getOffset();
        }
        catch (NoSuchFieldException | SecurityException | IllegalAccessException excecao)
        {

        }

        return -1;
    }

    private void setStartOffset(Fold fold, int startOffset, RSyntaxTextArea textArea)
    {
        try
        {
            Field field = Fold.class.getDeclaredField("startOffs");
            field.setAccessible(true);

            field.set(fold, textArea.getDocument().createPosition(startOffset));
        }
        catch (BadLocationException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException excecao)
        {

        }
    }
}
