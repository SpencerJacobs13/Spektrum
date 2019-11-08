import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    Controller controller;
    protected JButton analyzeButton;
    protected JButton uploadButton;
    protected JLabel imageIcon;

    public View(Controller con) {
        super("Welcome");
        this.controller = con;
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 800));

        setUpUI();
        pack();
    }


    private void setUpUI(){
        JPanel contentPanel = (JPanel) getContentPane();

        //the image and save button are part of a vertical box layout
        //JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5 ,5, 5));

        JLabel infoLabel = new JLabel("Upload an image. Please." +
                " It will be an adventure.");
        infoLabel.setFont(new Font("serif", Font.PLAIN, 24));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(infoLabel, BorderLayout.NORTH);

        imageIcon = new JLabel(new ImageIcon("images/blank-image.jpg"));

        uploadButton = new JButton(new ImageIcon("images/upload.png"));

        //create a new panel for the CENTER of our contentPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(imageIcon);
        centerPanel.add(uploadButton);

        //add the center panel to the overall content panel
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        analyzeButton = new JButton("Analyze");
        analyzeButton.setVisible(false);
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(analyzeButton);

        JPanel colorViewOutput = new JPanel();
        colorViewOutput.setLayout(new GridLayout(2, 5));
        contentPanel.add(colorViewOutput, BorderLayout.SOUTH);

    }











}//end class
