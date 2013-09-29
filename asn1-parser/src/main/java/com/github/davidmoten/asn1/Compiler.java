package com.github.davidmoten.asn1;

import java.io.File;
import java.io.IOException;
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
		for (File file:files) {
			try {
				CharStream s = new ANTLRFileStream(file.getPath());
				asn1Lexer lexer = new asn1Lexer(s);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				asn1Parser parser = new asn1Parser(tokens);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
