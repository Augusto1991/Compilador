package compilador18;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS5 extends AbstractAS {

	public AS5(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException {
		//Agrega un identificador
		
		lexico.devolverBuffer(lexico.getCaracterActual());
		if(!lexico.estaEnTS(lexico.getTokenActual())) {
			lexico.setTokenDesdeAS(lexico.altaEnTS(lexico.getTokenActual(),"identificador",268));
		}
	}

}
