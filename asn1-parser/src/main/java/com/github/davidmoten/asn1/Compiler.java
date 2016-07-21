package com.github.davidmoten.asn1;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.RuleNode;

import com.github.davidmoten.asn1.ASNParser.ModuleDefinitionContext;

public class Compiler {

    private final List<File> files;

    public Compiler(List<File> files) {
        this.files = files;
    }

    // public void compile() {
    // for (File file : files) {
    // try {
    // CharStream s = new ANTLRFileStream(file.getPath());
    //
    // Asn1Lexer lexer = new Asn1Lexer(s);
    // CommonTokenStream tokens = new CommonTokenStream(lexer);
    //
    // Asn1Parser parser = new Asn1Parser(tokens);
    //
    // moduleDefinitions_return defs = parser.moduleDefinitions();
    // process(defs, 0);
    // } catch (IOException e) {
    // throw new RuntimeException(e);
    // } catch (RecognitionException e) {
    // e.printStackTrace();
    // }
    // }
    // }

    public void compile() {
        for (File file : files) {
            try {
                CharStream s = new ANTLRFileStream(file.getPath());

                ASNLexer lexer = new ASNLexer(s);
                CommonTokenStream tokens = new CommonTokenStream(lexer);

                ASNParser parser = new ASNParser(tokens);

                ModuleDefinitionContext defs = parser.moduleDefinition();
                process(defs, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (RecognitionException e) {
                e.printStackTrace();
            }
        }
    }

    private void process(ModuleDefinitionContext r, final int maxDepth) {
        ParseTreeVisitor<Object> visitor = new AbstractParseTreeVisitor<Object>() {

            int depth = 0;

            @Override
            public Object visit(ParseTree tree) {
                return tree.accept(this);
            }

            @Override
            public Object visitChildren(RuleNode node) {
                // System.out.println(node.getText());
                depth += 1;
                if (depth <= maxDepth) {
                    return node.accept(this);
                } else {
                    return node;
                }
            }

        };
        r.accept(visitor);
    }

    private void process(CommonTree t, int depth) {
        for (int i = 0; i < depth; i++)
            System.out.print("  ");
        System.out.println(t.getText() + ":" + t.getType());
        if (t.getChildCount() > 0)
            for (Object c : t.getChildren()) {
                process((CommonTree) c, depth + 1);
            }
    }

}
