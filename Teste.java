package br.gov.es.sesp.deon.jpa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class Teste {
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		ObjetoFake objetoFake = new ObjetoFake();
		
		objetoFake.setId( 1 );
		objetoFake.setDescricao( "Descrição do objeto fake" );
		objetoFake.setQuantidade( 10 );
		
		objetoFake.setRelacionamento( new RelacionamentoFake() );
		objetoFake.getRelacionamento().setId( 2 );
		objetoFake.getRelacionamento().setDescricao( "Descrição do relacionamento fake" );
		objetoFake.getRelacionamento().setQuantidade( 20 );
		
		
        printFields( objetoFake );

	}

	private static void printFields( Object objParam ) {
		Field[] fields = objParam.getClass().getDeclaredFields();
		
		for ( Field field : fields ) {
			IncluirCampo isCampo = field.getAnnotation( IncluirCampo.class );
			IncluirObjeto isObjeto = field.getAnnotation( IncluirObjeto.class );

			field.setAccessible( true );
			if ( isCampo != null ) {
				
				try {
					Object obj = field.get( objParam );
					
					System.out.println( "<" + field.getName() + ">" + obj + "<\\" + field.getName() + ">" );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
				

			} else {
				
				if ( isObjeto != null ) {
					System.out.println( "<" + field.getName() + ">" );
					try {
						printFields( field.get( objParam ) );
					} catch ( IllegalArgumentException e ) {
						e.printStackTrace();
					} catch ( IllegalAccessException e ) {
						e.printStackTrace();
					}
					System.out.println( "<\\" + field.getName() + ">" );
					
				}
				
			}
		}
	}
	
	

}
