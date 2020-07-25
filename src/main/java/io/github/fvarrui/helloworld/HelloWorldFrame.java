package io.github.fvarrui.helloworld;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("serial")
public class HelloWorldFrame extends JFrame {

	public HelloWorldFrame() throws IOException {
		super("Hello World");
		initFrame();
        initContent();
        setVisible(true);
	}

	public void initContent() throws IOException {

		File info = new File("info.txt");

		String content = FileUtils.readFileToString(info, Charset.forName("UTF-8"));

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(new JLabel(content));
        getContentPane().add(new JLabel(info.getAbsolutePath()));
        getContentPane().add(new JLabel("PATH=" + System.getenv("PATH")));
        getContentPane().add(new JLabel("JAVA_HOME=" + System.getenv("JAVA_HOME")));
        getContentPane().add(new JLabel("java.home=" + System.getProperty("java.home")));
        
	}

	public void initFrame() throws IOException {
		setSize(640, 200);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/world.png")));
	}
	
	public static void main(String[] args) {
		System.out.println("Starting app ... ");
		System.out.println("PATH=" + System.getenv("PATH"));
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					new HelloWorldFrame();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
	}
	
}
