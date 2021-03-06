package br.gov.es.sesp.deon.jpa.util;

public class RelacionamentoFake {

	@IncluirCampo
	private long id;

	@IncluirCampo
	private String descricao;

	@IncluirCampo
	private int quantidade;

	@IncluirObjeto
	private NovoRelacionamentoFake novoRelacionamentoFake;

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

	public NovoRelacionamentoFake getNovoRelacionamentoFake() {
		return novoRelacionamentoFake;
	}

	public void setNovoRelacionamentoFake( NovoRelacionamentoFake novoRelacionamentoFake ) {
		this.novoRelacionamentoFake = novoRelacionamentoFake;
	}

}
