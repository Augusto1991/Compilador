package compilador18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS4 extends AbstractAS {

	public AS4(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) {

		if(lexico.getTokenActual().length() < 25) {
			lexico.agregarCaracterAlToken(lexico.getCaracterActual());
		}else{
			lexico.notificarError("WARNING Se esta truncando el identificador");
		}		

	}

}
