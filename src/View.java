import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class View extends JFrame {
    Controller controller;
    protected JButton analyzeButton;
    protected JButton uploadButton;
    protected JLabel imageIcon;
    protected JLabel infoLabel;

    //the color labels
    JLabel color1;
    JLabel color2;
    JLabel color3;
    JLabel color4;
    JLabel color5;

    public View(Controller con) {
        super("Welcome");
        this.controller = con;
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 1000));

        setUpUI();
        pack();
    }


    private void setUpUI(){
        JPanel contentPanel = (JPanel) getContentPane();

        //the image and save button are part of a vertical box layout
        //JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5 ,5, 5));

        infoLabel = new JLabel("Upload an image. Please." +
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
        centerPanel.setPreferredSize(new Dimension(400, 400));

        //add the center panel to the overall content panel
        contentPanel.add(centerPanel, BorderLayout.CENTER);

        analyzeButton = new JButton("Analyze");
        analyzeButton.setVisible(false);
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(analyzeButton);

        JPanel colorViewOutput = new JPanel();
        colorViewOutput.setLayout(new GridLayout(1, 5, 2, 1));

        color1 = new JLabel("", SwingConstants.CENTER);
        color1.setOpaque(true);
        color1.setPreferredSize(new Dimension(100, 200));
        color1.setFont(new Font("default", Font.BOLD, 24));
        color1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colorViewOutput.add(color1);

        color2 = new JLabel("", SwingConstants.CENTER);
        color2.setOpaque(true);
        color2.setFont(new Font("default", Font.BOLD, 24));
        color2.setPreferredSize(new Dimension(100, 200));
        color2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colorViewOutput.add(color2);

        color3 = new JLabel("", SwingConstants.CENTER);
        color3.setOpaque(true);
        color3.setPreferredSize(new Dimension(100, 200));
        color3.setFont(new Font("default", Font.BOLD, 24));
        color3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colorViewOutput.add(color3);

        color4 = new JLabel("", SwingConstants.CENTER);
        color4.setOpaque(true);
        color4.setPreferredSize(new Dimension(100, 200));
        color4.setFont(new Font("default", Font.BOLD, 24));
        color4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colorViewOutput.add(color4);

        color5 = new JLabel("", SwingConstants.CENTER);
        color5.setOpaque(true);
        color5.setPreferredSize(new Dimension(100, 200));
        color5.setFont(new Font("default", Font.BOLD, 24));
        color5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        colorViewOutput.add(color5);

        contentPanel.add(colorViewOutput, BorderLayout.SOUTH);
    }











}//end class
