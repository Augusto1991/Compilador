package compilador18;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AS6 extends AbstractAS {

	public AS6(Lex lexico) {
		super(lexico);
	}
	
	@Override
	public void ejecutar(HashMap<String, Object> tablaSimbolos) throws IOException {
		//Agrega un integer
		
		long checkMe = Long.parseLong(lexico.getTokenActual());
		if ((checkMe > Math.pow(-2,15)) && checkMe < (Math.pow(2,15)-1)) {
			lexico.setTokenDesdeAS(lexico.altaEnTS(lexico.getTokenActual(),"integer",270));
		} else {
			lexico.notificarError("WARNING El integer esta fuera de rango. Se pondra rango maximo");
			lexico.setTokenDesdeAS(lexico.altaEnTS(lexico.getTokenActual(),"integer",270));
		}
	}

}
