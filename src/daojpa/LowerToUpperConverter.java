/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 *
 */

package daojpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=false)	//nao é automatico para todos atributos
public class LowerToUpperConverter 
 implements AttributeConverter<String, String> { //<origem,destino>
	@Override
	public String convertToDatabaseColumn(String atributo) {
		//converte para maiusculas
		return (atributo == null) ? null : atributo.toUpperCase();  
	}
	@Override
	public String convertToEntityAttribute(String coluna) {
		//converte para minusculas
		return (coluna == null) ? null : coluna.toLowerCase();		
	}
}
