/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univali.portugol.nucleo.bibliotecas;

import br.univali.portugol.nucleo.bibliotecas.base.Biblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.ErroExecucaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.TipoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.Autor;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoBiblioteca;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoFuncao;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.DocumentacaoParametro;
import br.univali.portugol.nucleo.bibliotecas.base.anotacoes.PropriedadesBiblioteca;
import br.univali.portugol.nucleo.programa.Programa;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author Alisson
 */



@PropriedadesBiblioteca(tipo = TipoBiblioteca.RESERVADA)
@DocumentacaoBiblioteca(
        descricao = "Esta biblioteca contém diversas funções de conexão com a web",
        versao = "0.1"
)
public final class Internet extends Biblioteca{
    private int timeout = 2000;
    Programa programa;

    @Override
    public void inicializar(Programa programa, List<Biblioteca> bibliotecasReservadas) throws ErroExecucaoBiblioteca, InterruptedException {
        this.programa = programa;
        super.inicializar(programa, bibliotecasReservadas); //To change body of generated methods, choose Tools | Templates.
    }
    
    @DocumentacaoFuncao(
            descricao = "Define um valor de tempo limite de espera",
            parametros =
            {
                @DocumentacaoParametro(nome = "tempo", descricao = "o tempo limite de espera")
            },
            autores =
            {
                @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
            }
    )
    public void definir_tempo_limite(int time) throws ErroExecucaoBiblioteca, InterruptedException
    {
        this.timeout = time;
    }
    
    @DocumentacaoFuncao(
            descricao = "Obtém o conteúdo de um página web",
            retorno = "O conteúdo de uma pagina web",
            parametros =
            {
                @DocumentacaoParametro(nome = "caminho", descricao = "o caminho de onde o conteúdo deverá ser obtido")
            },
            autores =
            {
                @Autor(nome = "Alisson Steffens Henrique", email = "ali.steffens@gmail.com")
            }
    )
    public String obter_texto(String caminho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            String a = Request.Get(caminho).connectTimeout(timeout).socketTimeout(timeout).execute().returnContent().asString();
            if(a.isEmpty()){
                throw new ErroExecucaoBiblioteca("O caminho "+caminho+" não tem nenhum conteúdo");
            }
            return a;
        }
        catch (Exception excecao)
        {
            throw new ErroExecucaoBiblioteca("Não foi possível obter o conteúdo de "+caminho);
        }
    }
    
    @DocumentacaoFuncao(
            descricao = "Obtém recursos de um página web",
            parametros =
            {
                @DocumentacaoParametro(nome = "endereço", descricao = "o endereço de onde o conteúdo deverá ser obtido"),
                @DocumentacaoParametro(nome = "caminho", descricao = "o caminho de onde o conteúdo deverá ser salvo")
            },
            autores =
            {
                @Autor(nome = "Alisson Steffens", email = "ali.steffens@gmail.com")
            }
    )
    public void baixar_imagem(String endereco,String caminho) throws ErroExecucaoBiblioteca, InterruptedException
    {
        try
        {
            BufferedImage a = ImageIO.read(Request.Get(endereco).connectTimeout(timeout).socketTimeout(timeout).execute().returnContent().asStream());
            File arquivo = programa.resolverCaminho(new File(caminho));
            ImageIO.write(a, "PNG", arquivo);
        }
        catch (Exception excecao)
        {
            throw new ErroExecucaoBiblioteca("Não foi possível obter o conteúdo de "+endereco);
        }
    }
}
