package com.github.davidmoten.asn1;

import java.io.File;
import java.util.Collections;

import org.junit.Test;

public class CompilerTest {

	// @Test
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

}
