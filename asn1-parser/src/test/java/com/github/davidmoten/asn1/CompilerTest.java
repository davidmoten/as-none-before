package com.github.davidmoten.asn1;

import java.io.File;
import java.util.Collections;
import java.util.TreeSet;

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
	public void testExpectingDotErrorDoesNotOccur() {
		Compiler c = new Compiler(Collections.singletonList(new File(
				"src/test/resources/expectingDot.asn")));
		c.compile();
	}
	
	@Test
	public void test2() {
		try {
		Compiler c = new Compiler(Collections.singletonList(new File(
				"src/test/resources/AuthenticationFramework.asn")));
		c.compile();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	

	@Test
	public void testAll() {
		TreeSet<String> good = new TreeSet<String>();
		TreeSet<String> bad = new TreeSet<String>();
		for (File f : new File("src/main/asn1/x420").listFiles()) {
			System.out.println(f);
			try {
				Compiler c = new Compiler(Collections.singletonList(f));
				c.compile();
				good.add(f.getName() + "=" + f.length());
			} catch (RuntimeException e) {
				bad.add(f.getName() + "\t" + f.length() + "\t"
						+ e.getMessage().split("\n")[0]);
			}
		}
		System.out.println();
		System.out.println("----- Good -----");
		for (String line : good)
			System.out.println(line.split("\n")[0]);
		System.out.println();
		System.out.println("----- Bad -----");
		for (String line : bad)
			System.out.println(line.split("\n")[0]);
	}

}
