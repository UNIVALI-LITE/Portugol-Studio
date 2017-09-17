package br.univali.ps.ui;

import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.inspetor.InspetorDeSimbolos;
import java.awt.Point;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import org.assertj.swing.core.ComponentDragAndDrop;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.Containers;
import org.assertj.swing.fixture.JListFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.timing.Pause;
import org.junit.Test;

/**
 * @author Elieser
 */
public class IntegracaoAbaCodigoFonteTest extends AssertJSwingJUnitTestCase {

    @Override
    protected void onSetUp() {
        // 
    }
    
    @Test
    public void arrastaVariavelRepetidamenteDoEditorParaInspetor() throws Exception 
    {
        String variaveis[] = {"contador"};
        arrastaVariavelDoEditorParaInspetor(variaveis, 4); // arrasta a mesma variável 4 vezes para o inspetor, o inspetor não deve duplicar
    }
    
    @Test
    public void arrastaVariavelDoEditorParaInspetor() throws Exception 
    {
        String variaveis[] = {"contador"};
        arrastaVariavelDoEditorParaInspetor(variaveis, 1); // arras a variável 'contador' apenas uma vez para o inspetor
    }
    
    private void arrastaVariavelDoEditorParaInspetor(String nomesVariaveis[], int arrastamentos) 
    {
        // cria uma nova aba de código fonte na thread do Swing
        AbaCodigoFonte aba = GuiActionRunner.execute(() -> AbaCodigoFonte.novaAba());
        Containers.showInFrame(robot(), aba);

        // obtém os componentes
        JTextComponent editor = aba.getEditor().getTextArea();
        InspetorDeSimbolos inspetor = aba.getInspetor();
        JTextComponentFixture fixtureEditor = new JTextComponentFixture(robot(), editor);
        JListFixture fixtureInspetor = new JListFixture(robot(), inspetor);

        fixtureEditor.setText(getCodigoFonte()); // seta o código fonte no editor

        ComponentDragAndDrop dnd = new ComponentDragAndDrop(robot());
        
        for (int i = 0; i < arrastamentos; i++) {

            fixtureEditor.selectText(0, 0); // limpa a seleção de texto no editor
        
            for (String variavel : nomesVariaveis) {

                fixtureEditor.select(variavel); // seleciona a variável no editor de código fonte
            
                Pause.pause(2000); // espera a atualização da posição do cursor
                Caret caret = editor.getCaret();
            
                Point caretPos = caret.getMagicCaretPosition();
            
                dnd.drag(editor, new Point(caretPos.x - 20, caretPos.y)); // a localização da variável
                dnd.drop(inspetor, new Point(30, 30)); // arrasta para o canto superior esquerdo do inspetor
            }
        }
         
        // verificar se o inspetor contém 1 item depois do drag and drop
        fixtureInspetor.requireItemCount(1);        
    }
    
    private static String getCodigoFonte()
    {
        return  " programa {                     \n"
                + "  funcao inicio() {           \n"
                + "      inteiro contador = 0    \n"
                + "  }                           \n"
                + "}                             \n";
    }

}
