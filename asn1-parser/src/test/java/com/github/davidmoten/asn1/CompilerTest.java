package com.github.davidmoten.asn1;

import java.io.File;
import java.util.Collections;

import org.junit.Test;

public class CompilerTest {

	@Test
	public void test() {
		Compiler c = new Compiler(Collections.singletonList(new File(
				"src/main/asn1/x420-extras/P1File.asn")));
		c.compile();
	}

	@Test
	public void testBigger() {
		Compiler c = new Compiler(Collections.singletonList(new File(
				"src/main/asn1/x420/MTAAbstractService.asn")));
		c.compile();
	}

	@Test
	public void testBigger2() {
		for (File f : new File("src/main/asn1/x420").listFiles()) {
			System.out.println(f);
			Compiler c = new Compiler(Collections.singletonList(f));
			c.compile();
		}
	}

}
