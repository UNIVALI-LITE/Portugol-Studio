package br.univali.portugol.corretor.dinamico;

import br.univali.portugol.corretor.dinamico.model.Questao;
import br.univali.portugol.nucleo.ErroCompilacao;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ADMIN
 */
public class CorretorTest {

    public CorretorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

//    private String loadStringResource(String urlResource,String charset) throws UnsupportedEncodingException, IOException{
//        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(url);
//        BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
//        String linha;
//        StringBuilder sb = new StringBuilder();
//        
//        
//        
//        while ((linha = br.readLine()) != null)
//        {
//            sb.append(linha);
//            sb.append(System.lineSeparator()); // TALVEZ isso dê problemas...
//        }
//        
//        br.close();
//        return new String(sb.toString().getBytes(), charset);
//        return FileHand
//    }
    public static String loadStringResource(String urlResource, String charset) throws UnsupportedEncodingException, IOException {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(urlResource);
        StringBuilder reading = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(inputStream, charset);
        BufferedReader reader = new BufferedReader(isr);

        int read;
        char[] buffer = new char[4096];

        while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
            reading.append(buffer, 0, read);
        }
        isr.close();
        reader.close();
        inputStream.close();
        return new String(reading.toString().getBytes(), charset);
    }

    @Test
    public void testExecutar() throws JAXBException, UnsupportedEncodingException, IOException {
        System.out.println("Testando Executar");
        
        System.out.println("*Questão 1");
        Corretor instance = new Corretor(Questao.geraQuestao(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test1.pex", "UTF-8")));
        try {
            System.out.println("**Resposta 1 (certa)");
            assertEquals(10, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test1resp1c.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 2 (errada)");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test1resp2e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 3 (errada)");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test1resp3e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 4 (errada)");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test1resp4e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 5 (errada)");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test1resp5e.por", "ISO-8859-1"), null));
        } catch (ErroCompilacao ex) {
            fail("Exception: " + ex.toString());
        }
        
        System.out.println("*Questão 2");
        instance = new Corretor(Questao.geraQuestao(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2.pex", "UTF-8")));
        try {
            System.out.println("**Resposta 1 (certa) - respostas escritas juntas");
            assertEquals(10, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp1c.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 2 (certa) - respostas escritas separadas");
            assertEquals(10, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp2c.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 3 (errada) - inverter ordem de respostas");
            assertEquals(2, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp3e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 4 (errada) - unica saida (soma das respostas)");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp4e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 5 (errada) - unica saida. Resposta certa mas em cadeia");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp5e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 6 (errada) - unica saida, somente a linha");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp6e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 7 (errada) - três saidas, respostas certas seguidas de uma saida 0");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp7e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 8 (errada) - lê menos do que deveria");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp8e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 9 (errada) - lê mais do que deveria");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp9e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 10 (errada) - duas saidas. Respostas certas mas em cadeia");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp9e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta 11 (errada) - três saidas, um zero seguido das respostas certas.");
            assertEquals(0, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp9e.por", "ISO-8859-1"), null));
            System.out.println("**Resposta novamente 1 (certa) - respostas escritas juntas");
            assertEquals(10, instance.executar(loadStringResource("br/univali/portugol/corretor/dinamico/pex/test2resp1c.por", "ISO-8859-1"), null));
        } catch (ErroCompilacao ex) {
            fail("Exception: " + ex.toString());
        }
    }
}
