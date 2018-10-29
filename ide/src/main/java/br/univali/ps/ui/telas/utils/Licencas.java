package br.univali.ps.ui.telas.utils;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.univali.ps.nucleo.PortugolStudio;
import br.univali.ps.ui.utils.FileHandle;

/**
*
* @author Rafael Ferreira Costa
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "recurso" })
public final class Licencas {

	private static final Logger LOGGER = Logger.getLogger(Licencas.class.getName());
	
	@JsonProperty("recurso")
	private List<Recurso> recursos = null;

	@JsonProperty("recurso")
	public List<Recurso> getRecursos() {
		return recursos;
	}

	@JsonProperty("recurso")
	public void setRecurso(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({ "nome", "versao", "arquivo", "url", "descricao" })
	public static final class Recurso {

		@JsonProperty("nome")
		private String nome;
		
		@JsonProperty("versao")
		private String versao;
		
		@JsonProperty("arquivo")
		private String arquivo;
		
		@JsonProperty("url")
		private String url;
		
		@JsonProperty("descricao")
		private String descricao;
		
		@JsonIgnore
		private String licenca;

		@JsonProperty("nome")
		public String getNome() {
			return nome;
		}

		@JsonProperty("nome")
		public void setNome(String nome) {
			this.nome = nome;
		}

		@JsonProperty("versao")
		public String getVersao() {
			return versao;
		}

		@JsonProperty("versao")
		public void setVersao(String versao) {
			this.versao = versao;
		}

		@JsonProperty("arquivo")
		public String getArquivo() {
			return arquivo;
		}

		@JsonProperty("arquivo")
		public void setArquivo(String arquivo) {
			this.arquivo = arquivo;
		}

		@JsonProperty("url")
		public String getUrl() {
			return url;
		}

		@JsonProperty("url")
		public void setUrl(String url) {
			this.url = url;
		}

		@JsonProperty("descricao")
		public String getDescricao() {
			return descricao;
		}

		@JsonProperty("descricao")
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getLicenca() {
			return licenca;
		}

		public void setLicenca(String licenca) {
			this.licenca = licenca;
		}

	}

	public static Licencas carregar() {
		Licencas licencas = carregarJson();

		if (licencas != null) {
			for (Recurso recurso : licencas.getRecursos()) 
			{
				recurso.setLicenca(carregarLicenca(recurso.getArquivo()));
			}
			
			CompardorRecurso comparador = new CompardorRecurso();
			Collections.sort(licencas.getRecursos(), comparador);
		}

		return licencas;
	}

	private static String carregarLicenca(String arquivo) {
		try 
		{
			return FileHandle.read(Licencas.class.getClassLoader().getResourceAsStream(String.format("br/univali/ps/licencas/%s", arquivo)), "UTF-8");
			
		} catch (Exception excecao) {
			LOGGER.log(Level.SEVERE, String.format("Erro ao carregar a licença '%s'", arquivo), excecao);
		}

		return "Erro ao carregar a licença";
	}

	private static Licencas carregarJson() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			InputStream is = Licencas.class.getClassLoader().getResourceAsStream("br/univali/ps/licencas/licencas.json");
			
			return mapper.readValue(is, Licencas.class);
		} catch (Exception excecao) {
			PortugolStudio.getInstancia().getTratadorExcecoes().exibirExcecao(excecao);
		}

		return null;
	}
	
	/**
     * Coloca a aba do Portugol Studio sempre na primeira posição. Depois coloca a aba do
     * Portugol Núcleo na segunda posição. Ordena as demais abas por ordem alfabética.
     */
    private static final class CompardorRecurso implements Comparator<Recurso>
    {
        @Override
        public int compare(Recurso r1, Recurso r2)
        {
            if (r1.getNome().equals("Portugol Studio"))
            {
                return -1000000;
            }
            else if (r2.getNome().equals("Portugol Studio"))
            {
                return 1000000;
            }
            else if (r1.getNome().equals("Portugol Core"))
            {
                return -999999;
            }
            else if (r2.getNome().equals("Portugol Core"))
            {
                return 999999;
            }
            else
            {
                return r1.getNome().toUpperCase().compareTo(r2.getNome().toUpperCase());
            }
        }
    }
}