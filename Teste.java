package br.gov.es.sesp.deon.jpa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Teste {

	static List<Class<? extends Object>> tiposJahSerializados = new ArrayList<Class<? extends Object>>();
	static StringBuilder result = new StringBuilder();

	/**
	 * @param args
	 */
	public static void main( String[] args ) {

		NovoRelacionamentoFake novoRelacionamentoFake = new NovoRelacionamentoFake();
		novoRelacionamentoFake.setId( 3 );
		novoRelacionamentoFake.setDescricao( "Descrição do novo relacionamento fake" );
		novoRelacionamentoFake.setQuantidade( 0 );

		RelacionamentoFake relacionamentoFake = new RelacionamentoFake();
		relacionamentoFake.setId( 2 );
		relacionamentoFake.setDescricao( "Descrição do relacionamento fake" );
		relacionamentoFake.setQuantidade( 10 );
		relacionamentoFake.setNovoRelacionamentoFake( novoRelacionamentoFake );

		ObjetoFake objetoFake = new ObjetoFake();
		objetoFake.setId( 1 );
		objetoFake.setDescricao( "Descrição do objeto fake" );
		objetoFake.setQuantidade( 10 );
		objetoFake.setRelacionamento( relacionamentoFake );

		ObjetoFake objetoFake2 = new ObjetoFake();
		objetoFake2.setId( 999 );
		objetoFake2.setDescricao( "FAKE 2" );
		objetoFake2.setQuantidade( 333 );

		ObjetoFake objetoFake3 = new ObjetoFake();
		objetoFake3.setId( 888 );
		objetoFake3.setDescricao( "FAKE 3" );
		objetoFake3.setQuantidade( 444 );

		List<ObjetoFake> listaObjeto = new ArrayList<ObjetoFake>();
		listaObjeto.add( objetoFake2 );
		listaObjeto.add( objetoFake3 );

		novoRelacionamentoFake.setListaObjetosFakes( listaObjeto );

		Object objetoParametro = null;
		objetoParametro = objetoFake;

		serializeObjeto( objetoParametro );

		System.out.println( result );

	}

	private static void serializeObjeto( Object objetoParametro ) {
		appendAssinaturaUTF8();

		String elementoRaiz = objetoParametro.getClass().getSimpleName();
		elementoRaiz = firstCharToLower( elementoRaiz );

		tagAbertura( elementoRaiz );

		serializeFields( objetoParametro );

		tagFechamento( elementoRaiz );

	}

	private static void serializeFields( Object objParam ) {
		tiposJahSerializados.add( objParam.getClass() );

		Field[] fields = objParam.getClass().getDeclaredFields();

		for ( Field field : fields ) {
			IncluirCampo isCampo = field.getAnnotation( IncluirCampo.class );
			IncluirObjeto isObjeto = field.getAnnotation( IncluirObjeto.class );
			IncluirLista isLista = field.getAnnotation( IncluirLista.class );

			field.setAccessible( true );
			if ( isCampo != null ) {

				try {
					Object obj = field.get( objParam );

					conteudoTag( field.getName(), obj );
				} catch ( Exception e ) {
					e.printStackTrace();
				}

			} else if ( isObjeto != null && tipoNaoEstaNaLista( objParam.getClass() ) ) {
				tagAbertura( field.getName() );
				try {
					serializeFields( field.get( objParam ) );
				} catch ( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch ( IllegalAccessException e ) {
					e.printStackTrace();
				}
				tagFechamento( field.getName() );

			} else if ( isLista != null ) {
				try {
					List<? extends Object> lista = (List<? extends Object>)field.get( objParam );

					tagAbertura( field.getName() );
					for ( Object object : lista ) {
						tagAberturaItemLista( firstCharToLower( object.getClass().getSimpleName() ), String.valueOf( lista.indexOf( object ) ) );

						serializeFields( object );

						tagFechamentoItemLista( firstCharToLower( object.getClass().getSimpleName() ), String.valueOf( lista.indexOf( object ) ) );

					}
					tagFechamento( field.getName() );
				} catch ( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch ( IllegalAccessException e ) {
					e.printStackTrace();
				}
			}

		}
	}

	private static void conteudoTag( String fieldName, Object obj ) {

		tagAbertura( fieldName );

		result.append( obj );

		tagFechamento( fieldName );

	}

	private static boolean tipoNaoEstaNaLista( Class<? extends Object> class1 ) {
		boolean naoEstaNaLista = true;

		int count = 0;

		for ( Class<? extends Object> obj : tiposJahSerializados ) {
			if ( obj.equals( class1 ) )
				count++;
		}

		if ( count >= 2 )
			naoEstaNaLista = false;

		return naoEstaNaLista;
	}

	private static String firstCharToLower( String elementoRaiz ) {
		return Character.toLowerCase( elementoRaiz.charAt( 0 ) ) + elementoRaiz.substring( 1 );
	}

	private static void tagFechamento( String fieldName ) {
		appendMenorBarra();

		appendFieldName( fieldName );

		appendMaior();
	}

	private static void tagAbertura( String fieldName ) {
		appendMenor();

		appendFieldName( fieldName );

		appendMaior();
	}

	private static void tagAberturaItemLista( String fieldName, String item ) {
		appendMenor();

		appendFieldName( fieldName );
		appendFieldName( item );

		appendMaior();
	}

	private static void tagFechamentoItemLista( String fieldName, String item ) {
		appendMenorBarra();

		appendFieldName( fieldName );
		appendFieldName( item );

		appendMaior();
	}

	private static void appendFieldName( String fieldName ) {
		result.append( fieldName );
	}

	private static void appendMenorBarra() {
		result.append( "</" );
	}

	private static void appendMaior() {
		result.append( ">" );
	}

	private static void appendMenor() {
		result.append( "<" );
	}

	private static void appendAssinaturaUTF8() {
		result.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
	}
}
