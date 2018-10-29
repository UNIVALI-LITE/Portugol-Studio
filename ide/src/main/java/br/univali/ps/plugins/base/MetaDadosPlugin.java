package br.univali.ps.plugins.base;

import java.awt.Image;
import java.io.File;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author Luiz Fernando Noschang
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nome", "versao", "classe", "descricao", "autores" })
public final class MetaDadosPlugin {
	@JsonIgnore
	private Class classe;

	@JsonProperty("nome")
	private String nome;

	@JsonProperty("versao")
	private String versao;

	@JsonProperty("classe")
	private String nomeClasse;

	@JsonProperty("descricao")
	private String descricao;

	@JsonProperty("autores")
	private List<Autor> autores = null;

	@JsonIgnore
	private String licenca;

	@JsonIgnore
	private Image icone16x16;

	@JsonIgnore
	private Image icone32x32;

	@JsonIgnore
	private File arquivoJar;

	MetaDadosPlugin() {

	}
	
	public Class getClasse() {
		return classe;
	}
	
	public void setClasse(Class classe) {
		this.classe = classe;
	}
	
	@JsonProperty("classe")
	String getNomeClasse() {
		return nomeClasse;
	}
	
	@JsonProperty("classe")
	void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

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

	@JsonProperty("descricao")
	public String getDescricao() {
		return descricao;
	}

	@JsonProperty("descricao")
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@JsonProperty("autores")
	public List<Autor> getAutores() {
		return autores;
	}

	@JsonProperty("autores")
	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}

	public String getLicenca() {
		return licenca;
	}

	void setLicenca(String licenca) {
		this.licenca = licenca;
	}

	public Image getIcone16x16() {
		return icone16x16;
	}

	void setIcone16x16(Image icone16x16) {
		this.icone16x16 = icone16x16;
	}

	public Image getIcone32x32() {
		return icone32x32;
	}

	void setIcone32x32(Image icone32x32) {
		this.icone32x32 = icone32x32;
	}

	public File getArquivoJar() {
		return arquivoJar;
	}

	void setArquivoJar(File arquivoJar) {
		this.arquivoJar = arquivoJar;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MetaDadosPlugin) {
			return ((MetaDadosPlugin) obj).getClass().equals(this.getClass());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 2339 + (this.getClass() != null ? this.getClass().hashCode() : 0)
				+ (this.getVersao() != null ? this.getVersao().hashCode() : 0);
	}
}
