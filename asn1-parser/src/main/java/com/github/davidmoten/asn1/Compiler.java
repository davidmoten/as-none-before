package com.github.davidmoten.asn1;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
				process(parser.getClass(), parser);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private <T> void process(Class<? extends T> cls, T object) {
		for (Method method : cls.getDeclaredMethods()) {
			try {
				Object result = method.invoke(object);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
