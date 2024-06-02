package io.github.fvarrui.helloworld;

import javafx.application.Application;

public class Main {
	
	private static void version() {
		System.out.println("HelloWorld 1.0.0");
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Starting Swing app ... ");
			System.out.println("PATH=" + System.getenv("PATH"));	
			HelloWorldFrame.main(args);
		}
		else if ("--javafx".equals(args[0])) {
			System.out.println("Starting JavaFX app ... ");
			System.out.println("PATH=" + System.getenv("PATH"));	
			Application.launch(HelloWorldApp.class, args);
		}
		else if ("--version".equals(args[0])) {
			version();
		}
	}

}
