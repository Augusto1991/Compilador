package compilador18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS3 extends AbstractAS {

	public AS3(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException {
		//Para palabras reservadas
		
		lexico.devolverBuffer(lexico.getCaracterActual());
		if(lexico.estaEnTS(lexico.getTokenActual())) {
			lexico.setTokenDesdeAS(lexico.getTokenActual());
		}else {
			lexico.notificarError("ERROR La palabra reservada no esta en la Tabla de Simbolos");
		}
	}

}
