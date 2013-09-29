package com.github.davidmoten.asn1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;

public class Compiler {

	private final List<File> files;

	public Compiler(List<File> files) {
		this.files = files;
	}

	public void compile() {
		for (File file : files) {
			try {
				CharStream s = new ANTLRFileStream(file.getPath());

				Asn1Lexer lexer = new Asn1Lexer(s);
				CommonTokenStream tokens = new CommonTokenStream(lexer);

				Asn1Parser parser = new Asn1Parser(tokens);
				for (String token : parser.getTokenNames()) {
					System.out.println(token);
				}
				process(parser, 0);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void process(Object object, int depth) {
		if (depth > 3)
			return;
		if (object == null
				|| object.getClass().getName().startsWith("java.lang"))
			return;
		for (Method method : object.getClass().getDeclaredMethods()) {
			try {
				if (method.getParameterTypes().length == 0
						&& (method.getModifiers() & Modifier.PUBLIC) != 0) {
					try {
						System.out.print(method.getName() + "=");
						Object result = method.invoke(object);
						System.out.println(result);
						process(result,depth+1);
					} catch (RuntimeException e) {
						System.out.println(e.getClass().getSimpleName() + "("
								+ e.getMessage() + ")");
					}
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
