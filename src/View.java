import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    protected JButton analyzeButton;
    protected JButton uploadButton;
    protected JLabel imageIcon;

    public View() {
        super("Welcome");
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 900));

        setUpUI();
        pack();
    }


    private void setUpUI(){
        JPanel contentPanel = (JPanel) getContentPane();

        //the image and save button are part of a vertical box layout
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JLabel infoLabel = new JLabel("Upload an image, and I will tell you what the most common colors are." +
                "It will be an adventure.");
        contentPanel.add(infoLabel);

        imageIcon = new JLabel(new ImageIcon("images/upload-button.png"));
        contentPanel.add(imageIcon);


        analyzeButton = new JButton("Analyze");


        uploadButton = new JButton("Upload Image");


        JPanel colorViewOutput = new JPanel();
        colorViewOutput.setLayout(new GridLayout(2, 5));


    }











}//end class
