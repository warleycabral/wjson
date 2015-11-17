package br.gov.es.sesp.deon.jpa.util;

import java.util.List;

public class NovoRelacionamentoFake {

	@IncluirCampo
	private long id;

	@IncluirCampo
	private String descricao;

	@IncluirCampo
	private int quantidade;

	@IncluirLista
	private List<ObjetoFake> listaObjetosFakes;

	public long getId() {
		return id;
	}

	public void setId( long id ) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao( String descricao ) {
		this.descricao = descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade( int quantidade ) {
		this.quantidade = quantidade;
	}

	public List<ObjetoFake> getListaObjetosFakes() {
		return listaObjetosFakes;
	}

	public void setListaObjetosFakes( List<ObjetoFake> listaObjetosFakes ) {
		this.listaObjetosFakes = listaObjetosFakes;
	}

}
