package laptrinhmang_doan;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class ReviewFrame1 {

    private JFXPanel jfxPanel ;
    private String chuoi;
    public ReviewFrame1(String string){
        this.chuoi = string;
        createAndShowWindow();
    }
    public ReviewFrame1(){
        this.chuoi = "Vietnam";
        createAndShowWindow();
    }
    public void createAndShowWindow() {
        JFrame frame = new JFrame();
        JButton quit = new JButton("Quit");
        quit.addActionListener(event -> System.exit(0));
        jfxPanel = new JFXPanel();
        Platform.runLater(this::createJFXContent);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(quit);

        frame.add(BorderLayout.CENTER, jfxPanel);
        frame.add(BorderLayout.SOUTH, buttonPanel);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createJFXContent() {
        WebView webView = new WebView();
        webView.getEngine().load("https://vi.wikipedia.org/wiki/"+chuoi);
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
    }

    public static void main(String[] args) {
        ReviewFrame1 swingApp = new ReviewFrame1();
        //SwingUtilities.invokeLater(swingApp::createAndShowWindow);
    }
}