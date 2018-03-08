package br.univali.portugol.nucleo.compilador;

import org.apache.commons.exec.LogOutputStream;

import java.util.LinkedList;
import java.util.List;

class CollectingLogOutputStream extends LogOutputStream {
    private final List<String> lines = new LinkedList<>();

    @Override
    protected void processLine(String line, int level) {
        lines.add(line);
    }

    public List<String> getLines() {
        return lines;
    }
}
