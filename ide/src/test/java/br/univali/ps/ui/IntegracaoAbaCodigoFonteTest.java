package br.univali.ps.ui;

import br.univali.portugol.nucleo.asa.NoDeclaracao;
import br.univali.ps.ui.abas.AbaCodigoFonte;
import br.univali.ps.ui.inspetor.InspetorDeSimbolos;
import java.awt.Point;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTree;
import javax.swing.text.Caret;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import org.assertj.swing.core.ComponentDragAndDrop;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.Containers;
import org.assertj.swing.fixture.JListFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.fixture.JTreeFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.timing.Condition;
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
    public void arrastaReferenciaParaVariavelDoEditorParaInspetor() throws Exception {
        String variaveis[] = {"contador"};
        arrastaVariavelDoEditorParaInspetor(variaveis, 4, true);
    }
    
    @Test
    public void arrastaVariavelRepetidamenteDaArvoreParaInspetor() throws Exception {
        String variaveis[] = {"contador"};
        arrastaVariavelDaArvoreParaInspetor(variaveis, 4); // arrasta a mesma variável 4 vezes para o inspetor, o inspetor não deve duplicar
    }
    
    @Test
    public void arrastaVariavelDaArvoreParaInspetor() throws Exception {
        String variaveis[] = {"contador"};
        arrastaVariavelDaArvoreParaInspetor(variaveis, 1); // arrasta a mesma variável para o inspetor, o inspetor não deve duplicar
    }

    /***
     * @param nomesVariaveis As variáveis que serão arrastadas
     * @param arrastamentos Quantos arrastamentos serão simulados
     */
    private void arrastaVariavelDaArvoreParaInspetor(String nomesVariaveis[], int arrastamentos) {
        // cria uma nova aba de código fonte na thread do Swing
        AbaCodigoFonte aba = GuiActionRunner.execute(() -> AbaCodigoFonte.novaAba());
        Containers.showInFrame(robot(), aba);

        // obtém os componentes
        final InspetorDeSimbolos inspetor = aba.getInspetor();
        final JTree arvore = aba.getArvore();
        final JTextComponent editor = aba.getEditor().getTextArea();

        JListFixture fixtureInspetor = new JListFixture(robot(), inspetor);
        JTreeFixture fixtureArvore = new JTreeFixture(robot(), arvore);
        JTextComponentFixture fixtureEditor = new JTextComponentFixture(robot(), editor);

        fixtureEditor.setText(getCodigoFonte()); // seta o código fonte no editor
        
        Pause.pause(new Condition("aguarda a construção da ávore") {
            @Override
            public boolean test() {
                DefaultMutableTreeNode raiz = (DefaultMutableTreeNode)arvore.getModel().getRoot();
                return (raiz.getChildCount() > 0);
            }
        }); 

        DefaultMutableTreeNode raiz = (DefaultMutableTreeNode)arvore.getModel().getRoot();
        
        for (int i = 0; i < arrastamentos; i++) {

            for (String variavel : nomesVariaveis) {

                int linha = getLinhaVariavel(raiz, variavel);

                fixtureArvore.clickRow(linha); // necessário para evitar que a janela de renomeação abra
                Pause.pause(500); // necessário para evitar que a janela de renomeação abra
                fixtureArvore.drag(linha);
                fixtureInspetor.drop();
            }
        }

        // verifica se o inspetor contém o número correto de item depois do drag and drop
        fixtureInspetor.requireItemCount(nomesVariaveis.length);
    }

    @SuppressWarnings("unchecked")
    private int getLinhaVariavel(DefaultMutableTreeNode root, String variavel) 
    {
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            NoDeclaracao declaracao = (NoDeclaracao)node.getUserObject();
            if (declaracao.getNome().equalsIgnoreCase(variavel)) {
                return node.getLevel() - 1;
            }
        }
        return 0;
    }

    @Test
    public void arrastaVariavelRepetidamenteDoEditorParaInspetor() throws Exception {
        String variaveis[] = {"contador"};
        arrastaVariavelDoEditorParaInspetor(variaveis, 4); // arrasta a mesma variável 4 vezes para o inspetor, o inspetor não deve duplicar
    }

    @Test
    public void arrastaVariavelDoEditorParaInspetor() throws Exception {
        String variaveis[] = {"contador"};
        arrastaVariavelDoEditorParaInspetor(variaveis, 1); // arras a variável 'contador' apenas uma vez para o inspetor
    }

    private void arrastaVariavelDoEditorParaInspetor(String nomesVariaveis[], int arrastamentos) {
        arrastaVariavelDoEditorParaInspetor(nomesVariaveis, arrastamentos, false);
    }
    
    /***
     * @param nomesVariaveis As variáveis que serão arrastadas
     * @param arrastamentos Quantos arrastamentos serão simulados
     * @param usaReferencias Arrasta as declarações das variáveis (false) ou suas referências (true)?
     */
    private void arrastaVariavelDoEditorParaInspetor(String nomesVariaveis[], int arrastamentos, boolean usaReferencias) {
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

                if (usaReferencias) { // seleciona a última ocorrência da variável no código (ela será uma referência para a variável, não será a sua declaração)

                    Pattern pattern = Pattern.compile(variavel);
                    Matcher matcher = pattern.matcher(fixtureEditor.text());
                    while (matcher.find()) { // seleciona todas as ocorrências (em sequência) da variável. No final a última ocorrência sera arrastada
                        int start = matcher.start();
                        int end = matcher.end();
                        fixtureEditor.selectText(start, end);
                    }
                }
                
                Caret caret = editor.getCaret();
                Pause.pause(new Condition("espera a atualização da posição do cursor") {
                    @Override
                    public boolean test() {
                        return caret.getMagicCaretPosition() != null;
                    }
                });

                Point caretPos = caret.getMagicCaretPosition();

                dnd.drag(editor, new Point(caretPos.x - 20, caretPos.y)); // a localização da variável
                dnd.drop(inspetor, new Point(30, 30)); // arrasta para o canto superior esquerdo do inspetor
            }
        }

        // verifica se o inspetor contém o número correto de item depois do drag and drop
        fixtureInspetor.requireItemCount(nomesVariaveis.length);
    }

    private static String getCodigoFonte() {
        return " programa {                      \n"
                + "  funcao inicio() {           \n"
                + "      inteiro contador = 0    \n"
                + "      contador++              \n"
                + "      teste(contador)         \n"                
                + "  }                           \n"
                + "                              \n"
                + "  funcao teste(inteiro c) {   \n"
                + "                              \n"
                + "  }                           \n"                
                + "}                             \n";
    }

}
