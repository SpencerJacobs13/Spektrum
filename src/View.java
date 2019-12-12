import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class View extends JFrame {
    private Controller controller;
     protected JButton analyzeButton;
     protected JButton uploadButton;
     protected JLabel imageIcon;
     JRadioButton noneRadioButton;

     protected JPanel radioPanel;
     protected JLabel imageNameCurrent;
     protected JLabel imagePathCurrent;
     protected JLabel imagePixelsCurrent;
     protected JLabel imageUniqueColorsCurrent;
     protected JLabel mostCommonColorCurrent;

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

        JLabel infoLabel = new JLabel(" ");
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

        //add the center panel to the overall content panel
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        analyzeButton = new JButton("Analyze");
        analyzeButton.setVisible(false);
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(analyzeButton);

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(10, 1));
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
        contentPanel.add(color1, BorderLayout.SOUTH);

        noneRadioButton = new JRadioButton("None");

        JRadioButton blackWhiteRadioButton = new JRadioButton("Black/White");
        blackWhiteRadioButton.setActionCommand("blackWhite");
        blackWhiteRadioButton.addActionListener(controller);

        JRadioButton negativeRadioButton = new JRadioButton("Negative");
        negativeRadioButton.addActionListener(controller);
        negativeRadioButton.setActionCommand("negative");

        JRadioButton thresholdButton = new JRadioButton("Threshold");
        thresholdButton.addActionListener(controller);
        thresholdButton.setActionCommand("threshold");

        JRadioButton quantizeButton = new JRadioButton("Quantize");
        quantizeButton.addActionListener(controller);
        quantizeButton.setActionCommand("quantize");

        JRadioButton pixelateButton = new JRadioButton("Pixelate");
        pixelateButton.addActionListener(controller);
        pixelateButton.setActionCommand("pixelate");

        JRadioButton edgeDetectionButton = new JRadioButton("Edge Detect");
        edgeDetectionButton.addActionListener(controller);
        edgeDetectionButton.setActionCommand("edgeDetect");

        JRadioButton sobelButton = new JRadioButton("Sobel's Edge");
        sobelButton.addActionListener(controller);
        sobelButton.setActionCommand("sobel");

        JRadioButton ditherButton = new JRadioButton("Dither");
        ditherButton.addActionListener(controller);
        ditherButton.setActionCommand("dither");

        noneRadioButton.setSelected(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(noneRadioButton);
        buttonGroup.add(blackWhiteRadioButton);
        buttonGroup.add(negativeRadioButton);
        buttonGroup.add(thresholdButton);
        buttonGroup.add(quantizeButton);
        buttonGroup.add(pixelateButton);
        buttonGroup.add(edgeDetectionButton);
        buttonGroup.add(sobelButton);
        buttonGroup.add(ditherButton);


        radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(9, 2));
        radioPanel.setBorder(BorderFactory.createTitledBorder("Edit Options"));
        radioPanel.add(blackWhiteRadioButton);
        radioPanel.add(negativeRadioButton);
        radioPanel.add(thresholdButton);
        radioPanel.add(quantizeButton);
        radioPanel.add(pixelateButton);
        radioPanel.add(edgeDetectionButton);
        radioPanel.add(sobelButton);
        radioPanel.add(ditherButton);
        radioPanel.add(noneRadioButton);
        radioPanel.setVisible(false);
        radioPanel.setBorder(BorderFactory.createTitledBorder("Edit Options"));

        //add buttons to all canvas.
        contentPanel.add(radioPanel, BorderLayout.EAST);
    }
}//end class
