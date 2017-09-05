package br.univali.portugol.corretor.dinamico;

import br.univali.portugol.corretor.dinamico.model.Questao;
import java.io.InputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Unmarshal {

    JAXBContext jc;

    public Questao execute(InputStream stream) throws JAXBException {
        jc = JAXBContext.newInstance(Questao.class);

        Unmarshaller u = jc.createUnmarshaller();
        Questao unmarshal = (Questao) u.unmarshal(stream);
        return unmarshal;
    }
}
