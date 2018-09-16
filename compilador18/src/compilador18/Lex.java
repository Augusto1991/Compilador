package compilador18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Lex {
	public static void main(String[] args) throws IOException {
		String path = "ejemplo.txt";//;args[0]; TODO completar con el path para el ejecutable
		System.out.println(path);
		try {
			BufferedReader reader;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8"))); 
			int c = reader.read();
			System.out.println(c);
			while ((c = reader.read())!=-1) {
				System.out.println(c);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Archivo no encontrado");
        }
	}
}