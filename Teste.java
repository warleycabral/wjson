package br.gov.es.sesp.deon.jpa.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Teste {

	static List<Class<? extends Object>> tiposJah = new ArrayList<Class<? extends Object>>();

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
		objetoFake2.setRelacionamento( relacionamentoFake );

		novoRelacionamentoFake.setObjetoFake( objetoFake2 );

		System.out.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
		System.out.println( "<objetoFake>" );
		printFields( objetoFake );
		System.out.println( "</objetoFake>" );

	}

	private static void printFields( Object objParam ) {
		tiposJah.add( objParam.getClass() );

		Field[] fields = objParam.getClass().getDeclaredFields();

		for ( Field field : fields ) {
			IncluirCampo isCampo = field.getAnnotation( IncluirCampo.class );
			IncluirObjeto isObjeto = field.getAnnotation( IncluirObjeto.class );
			IncluirLista isLista = field.getAnnotation( IncluirLista.class );

			field.setAccessible( true );
			if ( isCampo != null ) {

				try {
					Object obj = field.get( objParam );

					System.out.println( "<" + field.getName() + ">" + obj + "</" + field.getName() + ">" );
				} catch ( Exception e ) {
					e.printStackTrace();
				}

			} else if ( isObjeto != null && tipoNaoEstaNaLista( objParam.getClass() ) ) {
				System.out.println( "<" + field.getName() + ">" );
				try {
					printFields( field.get( objParam ) );
				} catch ( IllegalArgumentException e ) {
					e.printStackTrace();
				} catch ( IllegalAccessException e ) {
					e.printStackTrace();
				}
				System.out.println( "</" + field.getName() + ">" );

			} else if ( isLista != null ) {
				Array array = (Array)objParam;
			}

		}
	}

	private static boolean tipoNaoEstaNaLista( Class<? extends Object> class1 ) {
		boolean naoEstaNaLista = true;

		int count = 0;

		for ( Class<? extends Object> ojb : tiposJah ) {
			if ( ojb.equals( class1 ) )
				count++;
		}

		if ( count >= 2 )
			naoEstaNaLista = false;

		return naoEstaNaLista;
	}

}
