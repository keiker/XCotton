package org.fusesource.jansi;

import org.fusesource.jansi.internal.WindowsSupport;

public class JavaTest {
	public static void main(String[] args) {
		//WindowsSupport.setConsoleMode();
		System.out.println(WindowsSupport.getConsoleMode());
	}
}
