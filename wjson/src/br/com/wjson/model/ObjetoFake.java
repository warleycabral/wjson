package br.com.wjson.model;

import br.com.wjson.annotations.IncluirCampo;
import br.com.wjson.annotations.IncluirObjeto;

public class ObjetoFake {
	
	@IncluirCampo
	private long id;

	@IncluirCampo
	private String descricao;

	@IncluirCampo
	private int quantidade;
	
	@IncluirObjeto
	private RelacionamentoFake relacionamento;

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

	public RelacionamentoFake getRelacionamento() {
		return relacionamento;
	}

	public void setRelacionamento( RelacionamentoFake relacionamento ) {
		this.relacionamento = relacionamento;
	}

}
