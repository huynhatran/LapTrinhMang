
package laptrinhmang_doan;


import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

public class ReviewFrame extends JFrame{
    private JPanel panel_36;
    private JFXPanel javafxPanel;
    public JTabbedPane tabbedPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ReviewFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ReviewFrame(String conutryName) {
        initialize();
        initSwingComponents();
        loadJavaFXScene(conutryName);
    }

    public ReviewFrame() {
        initialize();
        initSwingComponents();
        //loadJavaFXScene(conutryName);
    }
    private void initialize() {
        this.setBounds(100, 100, 1300, 800); // set size frame
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setForeground(new Color(0, 0, 128));
        tabbedPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        getContentPane().add(tabbedPane);
    }

    public void initSwingComponents() {
        javafxPanel = new JFXPanel();
        javafxPanel.setBounds(0, 0, this.getWidth(), this.getHeight()); // Set size google
        panel_36 = new JPanel();
        panel_36.setLayout(null);
        panel_36.setBounds(0, 0, this.getWidth(), this.getHeight());
        panel_36.add(javafxPanel);
        tabbedPane.addTab("Review",
            new ImageIcon("src/Icons/literature-30.png"), panel_36);
    }

    public void loadJavaFXScene(String countryName) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                BorderPane borderPane = new BorderPane();
                WebView webComponent = new WebView();
                webComponent.getEngine().load("https://vi.wikipedia.org/wiki/"+countryName);////https://cacnuoc.vn/nhat-ban/
                borderPane.setCenter(webComponent);
                Scene scene = new Scene(borderPane);
                javafxPanel.setScene(scene);
                webComponent.autosize();
            }
        });
    }

}