import javafx.scene.control.RadioButton;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class View extends JFrame {
    private Controller controller;
     JButton analyzeButton;
     JButton uploadButton;
     JLabel imageIcon;
     JLabel infoLabel;
     JRadioButton blackWhiteRadioButton;
     JRadioButton negativeRadioButton;
     JRadioButton noneRadioButton;
     JPanel radioPanel;
     JLabel imageNameCurrent;
     JLabel imagePathCurrent;
     JLabel imagePixelsCurrent;
     JLabel imageUniqueColorsCurrent;
     JLabel mostCommonColorCurrent;
    ButtonGroup buttonGroup;

    //the color label
    JLabel color1;

    public View(Controller con) {
        super("Welcome");
        this.controller = con;
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(1000, 1100));

        setUpUI();
        pack();
    }


    private void setUpUI(){
        JPanel contentPanel = (JPanel) getContentPane();

        //the image and save button are part of a vertical box layout
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5 ,5, 5));

        infoLabel = new JLabel(" ");
        contentPanel.add(infoLabel, BorderLayout.NORTH);

        //new ImageIcon("images/blank-image.jpg")
        ImageIcon blankIcon = new ImageIcon("images/blank-image.jpg");

        imageIcon = new JLabel(blankIcon);
        uploadButton = new JButton(new ImageIcon("images/upload.png"));

        //create a new panel for the CENTER of our contentPanel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(imageIcon);
        centerPanel.add(uploadButton);
        //centerPanel.setPreferredSize(new Dimension(600, 800));

        //add the center panel to the overall content panel
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        analyzeButton = new JButton("Analyze");
        analyzeButton.setVisible(false);
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(analyzeButton);

        //JPanel colorViewOutput = new JPanel();
        //colorViewOutput.setLayout(new GridLayout(1, 5, 2, 1));

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(5, 2));
        informationPanel.setBorder(BorderFactory.createTitledBorder("Image Information"));

        JLabel imageNameLabel = new JLabel("Name:");
        imageNameLabel.setFont(new Font("default", Font.BOLD, 18));
        informationPanel.add(imageNameLabel);

        imageNameCurrent = new JLabel("-");
        informationPanel.add(imageNameCurrent);

        JLabel imagePathLabel = new JLabel("Path:");
        imagePathLabel.setFont(new Font("default", Font.BOLD, 18));
        informationPanel.add(imagePathLabel);

        imagePathCurrent = new JLabel("-");
        informationPanel.add(imagePathCurrent);

        JLabel imagePixelsLabel = new JLabel("Total Pixels:");
        imagePixelsLabel.setFont(new Font("default", Font.BOLD, 18));
        informationPanel.add(imagePixelsLabel);

        imagePixelsCurrent = new JLabel("-");
        informationPanel.add(imagePixelsCurrent);

        JLabel imageUniqueColorsLabel = new JLabel("Unique Colors: ");
        imageUniqueColorsLabel.setFont(new Font("default", Font.BOLD, 18));
        informationPanel.add(imageUniqueColorsLabel);

        imageUniqueColorsCurrent = new JLabel("-");
        informationPanel.add(imageUniqueColorsCurrent);

        JLabel mostCommonColorLabel = new JLabel("Dominant Color: ");
        mostCommonColorLabel.setFont(new Font("default", Font.BOLD, 18));
        informationPanel.add(mostCommonColorLabel);

        mostCommonColorCurrent = new JLabel("-");
        informationPanel.add(mostCommonColorCurrent);

        contentPanel.add(informationPanel, BorderLayout.WEST);

        color1 = new JLabel("", SwingConstants.CENTER);
        color1.setOpaque(true);
        color1.setPreferredSize(new Dimension(100, 150));
        color1.setFont(new Font("default", Font.BOLD, 24));
        color1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        color1.setBackground(Color.decode("#DDDDDD"));
        color1.setText("R: -  G: -  B: -");
        //colorViewOutput.add(color1);
        contentPanel.add(color1, BorderLayout.SOUTH);

        buttonGroup = new ButtonGroup();
        blackWhiteRadioButton = new JRadioButton("Black/White");
        negativeRadioButton = new JRadioButton("Negative");
        noneRadioButton = new JRadioButton("None");
        noneRadioButton.setSelected(true);
        buttonGroup.add(noneRadioButton);
        buttonGroup.add(blackWhiteRadioButton);
        buttonGroup.add(negativeRadioButton);

        radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Edit Options"));
        radioPanel.add(blackWhiteRadioButton);
        radioPanel.add(negativeRadioButton);
        radioPanel.add(noneRadioButton);
        radioPanel.setVisible(false);
        radioPanel.setBorder(BorderFactory.createTitledBorder("Edit Options"));

        //add buttons to all canvas.
        contentPanel.add(radioPanel, BorderLayout.EAST);

        //contentPanel.add(colorViewOutput, BorderLayout.SOUTH);
    }

    public ImageIcon getScaledImageIcon(Image srcImg){
        ImageIcon icon = new ImageIcon(srcImg);
        int width = -1; // -1 to preserve aspect ratio
        int height = -1;
        if (icon.getIconWidth() <= icon.getIconHeight()) {
            height = imageIcon.getHeight();
        }
        else {
            width = imageIcon.getWidth();
        }
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image, icon.getDescription());
        return icon;
    }













}//end class
