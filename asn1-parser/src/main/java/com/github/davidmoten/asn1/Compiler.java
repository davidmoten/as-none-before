package com.github.davidmoten.asn1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

import com.github.davidmoten.asn1.Asn1Parser.moduleDefinitions_return;

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

				moduleDefinitions_return defs = parser.moduleDefinitions();
				process(defs, 0);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (RecognitionException e) {
				e.printStackTrace();
			}
		}
	}

	private void process(ParserRuleReturnScope r, int depth) {
		process((CommonTree) r.getTree(), depth);
	}

	private void process(CommonTree t, int depth) {
		for (int i = 0; i < depth; i++)
			System.out.print(" ");
		System.out.println(t.getText());
		if (t.getChildCount() > 0)
			for (Object c : t.getChildren()) {
				process((CommonTree) c, depth + 1);
			}
	}

}
