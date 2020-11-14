package br.univali.ps.ui.carrossel;

import br.univali.ps.nucleo.Configuracoes;
import br.univali.ps.ui.paineis.ImagePanel;
import br.univali.ps.ui.swing.ColorController;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CarrosselCursos extends JPanel {

    private Logger LOGGER = Logger.getLogger(CarrosselCursos.class.getName());

    private Timer timer;

    private int cursoAtual = -1;

    private List<Curso> cursos = Collections.EMPTY_LIST;

    private final int MARGEM_LAYOUT = 10;

    private final ImagePanel painelImagem;
    private final JPanel painelSetas;
    private final JLabel labelTitulo;
    private final JLabel labelDescricao;

    private final Collection<CarrosselListener> listeners = new ArrayList<>();

    private final String PACOTE_RESOURCES = "br/univali/ps/carrossel/";

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public CarrosselCursos() {

        painelImagem = new ImagePanel();
        labelTitulo = new JLabel();
        labelDescricao = new JLabel();
        painelSetas = new JPanel();

        ImageIO.setUseCache(false); // usando cache somente em memória - https://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html#setUseCache(boolean)

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MouseAdapter parentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CarrosselCursos.this.processMouseEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                CarrosselCursos.this.processMouseMotionEvent(e);
            }

        };

        labelTitulo.addMouseListener(parentListener);
        labelTitulo.addMouseMotionListener(parentListener);
        labelDescricao.addMouseListener(parentListener);
        labelDescricao.addMouseMotionListener(parentListener);
        painelImagem.addMouseListener(parentListener);
        painelImagem.addMouseMotionListener(parentListener);

        MouseListener mouseListener = new MouseListener();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension tamanho = new Dimension((int) (getWidth() * 0.5), -1);
                painelImagem.setMinimumSize(tamanho);
                painelImagem.setPreferredSize(tamanho);
            }
        });
        painelSetas.setMinimumSize(new Dimension(getWidth(), (int) (getWidth() * 0.06f)));
        painelSetas.setPreferredSize(new Dimension(getWidth(), (int) (getWidth() * 0.06f)));
        painelSetas.setOpaque(false);
        setLayout(new BorderLayout(MARGEM_LAYOUT, MARGEM_LAYOUT));

        add(labelTitulo, BorderLayout.NORTH);
        add(labelDescricao, BorderLayout.CENTER);
        add(painelImagem, BorderLayout.EAST);
        add(painelSetas, BorderLayout.SOUTH);

        labelTitulo.setForeground(ColorController.COR_LETRA);
        labelDescricao.setForeground(ColorController.COR_LETRA);

        labelTitulo.setFont(getFont().deriveFont(17f));
        labelDescricao.setFont(getFont().deriveFont(13f));

        setBackground(ColorController.COR_DESTAQUE);

        addListener(new CarrosselListener() {
            @Override
            public void cursosCarregados() {
                LOGGER.log(Level.INFO, "Cursos recomendados carregados!");
            }

            @Override
            public void erroNoCarregamentoCursos() {
                LOGGER.log(Level.WARNING, "Erro no carregamento dos cursos recomendados!");
                setVisible(false);
            }

            @Override
            public void imagensCarregadas() {
                LOGGER.log(Level.INFO, "Imagens dos cursos recomendados carregadas!");
                inicializaTimer();
            }
        });

        executor.submit(() -> {
            try {
                carregaCursos(getInputStreamCursos());
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

    }

    private void inicializaTimer() {
        int delayInicial = 500;
        timer = new Timer(delayInicial, (e) -> {
            try {
                mostraProximoCurso();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        timer.setDelay(cursos.isEmpty() ? 2000 : cursos.get(0).getTempoExibicao());
        timer.start();
    }

    private class MouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int arrowsBackgroundWidth = getLarguraBackgroundSetas();
            if (mouseEstaNasBordas(arrowsBackgroundWidth)) {
                try {
                    if (e.getX() <= arrowsBackgroundWidth) { // mouse in left side 
                        mostraCursoAnterior();
                    } else {
                        mostraProximoCurso();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                abreLinkCursoAtual();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            repaint();
        }

    }

    private String getBaseUrl() {
        if (Configuracoes.rodandoEmDesenvolvimento()) {
            return "";
        }

        return "https://raw.githubusercontent.com/UNIVALI-LITE/Portugol-Studio/master/ide/src/main/resources/";
    }

    private InputStream getInputStreamCursos() throws Exception {
        if (Configuracoes.rodandoEmDesenvolvimento()) {
            LOGGER.log(Level.INFO, "Lendo 'cursos.json' do JAR!");
            return ClassLoader.getSystemClassLoader().getResourceAsStream(PACOTE_RESOURCES + "cursos.json");
        }

        LOGGER.log(Level.INFO, "Lendo 'cursos.json' do github!");

        URL url = new URL(getBaseUrl() + PACOTE_RESOURCES + "cursos.json");

        return url.openStream();
    }

    public void addListener(CarrosselListener listener) {
        listeners.add(listener);
    }

    private void carregaCursos(InputStream stream) {

        if (stream == null) {
            throw new IllegalArgumentException("O stream está nulo!");
        }

        if (cursos.size() > 0) { // cursos já estão carregados?
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            cursos = Arrays.asList(mapper.readValue(stream, Curso[].class));

            for (CarrosselListener listener : listeners) {
                listener.cursosCarregados();
            }

            carregaImagens();

        } catch (IOException e) {
            for (CarrosselListener listener : listeners) {
                listener.erroNoCarregamentoCursos();
            }
            e.printStackTrace();
        }

    }

    private void carregaImagens() {
        if (cursos.isEmpty()) {
            return;
        }

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.submit(() -> {
            try {
                for (Curso curso : cursos) {
                    String caminhoImagem = getCaminhoImagem(curso);
                    ImageIO.read(getStreamImagem(caminhoImagem)); // usa cache em memória da classe ImageIO
                }

                for (CarrosselListener listener : listeners) {
                    listener.imagensCarregadas();
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        });

    }

    private void abreLinkCursoAtual() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Curso curso = cursos.get(cursoAtual);
            try {
                Desktop.getDesktop().browse(new URI(curso.getLink()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void mostraCursoAnterior() throws IOException {

        cursoAtual = (cursoAtual + cursos.size() - 1) % cursos.size();

        Curso curso = cursos.get(cursoAtual);

        mostraCurso(curso);
    }

    private void mostraProximoCurso() throws IOException {
        if (!isShowing()) {
            return;
        }

        cursoAtual = (cursoAtual + 1) % cursos.size();

        Curso curso = cursos.get(cursoAtual);

        mostraCurso(curso);
    }

    private InputStream getStreamImagem(String caminhoImagem) throws Exception {
        if (Configuracoes.rodandoEmDesenvolvimento()) {
            return ClassLoader.getSystemClassLoader().getResourceAsStream(caminhoImagem);
        } else {
            return new URL(caminhoImagem).openStream();
        }
    }

    private String getCaminhoImagem(Curso curso) {
        return getBaseUrl() + PACOTE_RESOURCES + curso.getCaminhoImagem();
    }

    private void mostraCurso(Curso curso) {
        try {

            String caminhoImagem = getCaminhoImagem(curso);

            InputStream stream = getStreamImagem(caminhoImagem);

            labelTitulo.setText("<html> " + curso.getTitulo() + "</html>");
            labelDescricao.setText("<html> " + curso.getDescricao() + "</html>");
            painelImagem.setImagem(ImageIO.read(stream));

            //doLayout();
            if (timer != null) {
                timer.setDelay(curso.getTempoExibicao());

                if (!timer.isRunning()) {
                    timer.start();
                }
            }
            
            LOGGER.log(Level.INFO, "Mostrando curso {0}", curso.getTitulo());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    private void desenhaSetas(Graphics2D g2d, int larguraBackgroundSetas) {
        final Color arrowsColor = new Color(255, 255, 255, 230);
        final int arrowSize = (int) (larguraBackgroundSetas * 0.5);
        final int yposition = 148;
        g2d.setColor(arrowsColor);

        Polygon setaEsquerda = new Polygon();
        setaEsquerda.addPoint(0, 0+yposition);
        setaEsquerda.addPoint(arrowSize, -arrowSize+yposition);
        setaEsquerda.addPoint(arrowSize, arrowSize+yposition);

        setaEsquerda.translate(5, getHeight() / 5);

        Polygon setaDireita = new Polygon();
        setaDireita.addPoint(getWidth(), 0+yposition);
        setaDireita.addPoint(getWidth()- arrowSize, -arrowSize+yposition);
        setaDireita.addPoint(getWidth()- arrowSize, arrowSize+yposition);

        setaDireita.translate(-5, getHeight() / 5);

        g2d.fillPolygon(setaEsquerda);
        g2d.fillPolygon(setaDireita);
    }

    private int getLarguraBackgroundSetas() {
        return (int) (getWidth() * 0.06f);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (isVisible()) {
            final int larguraBackgroundSetas = getLarguraBackgroundSetas();
            if (mouseEstaNasBordas(larguraBackgroundSetas)) {
                Graphics2D g2d = (Graphics2D) g;
                //desenhaBackgroundSetas(g2d, larguraBackgroundSetas);
                desenhaSetas(g2d, larguraBackgroundSetas);
            }
        }
    }

    private boolean mouseEstaNasBordas(int arrowsBackgroundWidth) {
        Point mousePosition = getMousePosition();
        if (mousePosition != null) {
            double mouseX = mousePosition.getX();
            return mouseX < arrowsBackgroundWidth || mouseX >= getWidth() - arrowsBackgroundWidth;
        }

        return false;
    }
}
