import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Controller extends JPanel {
    private View view;
    private Model model;
    protected Image image;
    protected BufferedImage bufferedImage = null;
    private Point start = null;
    private boolean canClickBool = false;

    public Controller() {
        this.view = new View(this);
        //anon class to allow th user to upload an image
        view.uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bufferedImage = null;
                image = null;
                uploadImage();
            }
        });

        view.analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //we need to re-call the other actionPerformed function here because we need a new BufferedImage
                //before we
                view.livePixelButton.setVisible(true);
                model = new Model(bufferedImage);
                view.analyzeButton.setVisible(false);
                view.livePixelButton.setVisible(true);
                setViewColors();
                canClickBool = true;
                view.radioPanel.setVisible(true);
            }
        });

        view.imageIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                start = e.getPoint();
                int x = start.x;
                int y = start.y;

                int colorAtClick = model.getRGBatPixel(x, y);
                int[] rgbColors = model.getRGBArray(colorAtClick);
                //String rgbStr = Integer.toHexString(rgbColors[0]) + Integer.toHexString(rgbColors[1]) + Integer.toHexString(rgbColors[2]);

                view.color1.setBackground(new Color(rgbColors[0], rgbColors[1], rgbColors[2]));
                view.color1.setText("R: " + rgbColors[0] + " G: " + rgbColors[1] + " B: " + rgbColors[2]);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e){
                int x = e.getX();
                int y = e.getY();
                System.out.println("mouse entered");
                Cursor cursor1 = view.imageIcon.getCursor();
                    if(canClickBool){
                    //view.imageIcon.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("images/dropper.png").getImage(), new Point(0, 0), "dropper"));
                    view.imageIcon.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    //setSize(100, 100);
                    }else{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        view.blackWhiteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage blackWhiteBufferedImage = bufferedImage;
                blackWhiteBufferedImage = model.makeImageGrayscale(blackWhiteBufferedImage);

                Image blackWhiteImage = blackWhiteBufferedImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);

                //blackWhiteImage = resize(blackWhiteImage, view.imageIcon.getWidth(), view.imageIcon.getHeight());

                ImageIcon icon = new ImageIcon(blackWhiteImage);
                view.imageIcon.setBackground(Color.lightGray);
                view.imageIcon.setIcon(icon);
            }
        });

        view.noneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon(image);
                view.imageIcon.setIcon(icon);
            }
        });

    }//end constructor

    //helper function to allow user to upload a new picture
    private void uploadImage(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG",
                "jpg", "png");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(null);
        final File file = fileChooser.getSelectedFile();

        //do this stuff if the image is legit
        if(returnVal == JFileChooser.APPROVE_OPTION){
            //trying to resize the image
            try {
                bufferedImage = ImageIO.read(fileChooser.getSelectedFile());
            }catch(IOException m){
                m.printStackTrace();
            }
            //setting image to size of JPanel
            image = bufferedImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);
            bufferedImage = resize(bufferedImage, view.imageIcon.getWidth(), view.imageIcon.getHeight());

            ImageIcon icon = new ImageIcon(image);
            view.imageIcon.setIcon(icon);
            view.analyzeButton.setVisible(true);
            //do we want to display the file name or the file path?
            view.infoLabel.setText("You chose: " + fileChooser.getSelectedFile().getName());
        }else if(returnVal == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "Invalid file type. Cmon.", "Nope", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setViewColors(){
        String color1 = setHexString(model.colorHex1);
        view.color1.setBackground(Color.decode(color1));
        view.color1.setText(color1);
//        String color2 = setHexString(model.colorHex2);
//        view.color2.setBackground(Color.decode(color2));
//        view.color2.setText(color2);
//
//        String color3 = setHexString(model.colorHex3);
//        view.color3.setBackground(Color.decode(color3));
//        view.color3.setText(color3);
//
//        String color4 = setHexString(model.colorHex4);
//        view.color4.setBackground(Color.decode(color4));
//        view.color4.setText(color4);
//
//        String color5 = setHexString(model.colorHex5);
//        view.color5.setBackground(Color.decode(color5));
//        view.color5.setText(color5);
    }

    private String setHexString(String originalString){
        String newHexString = "";
        String zero = "0";

        for(int i = 0; i < originalString.length(); i++){
            newHexString += originalString.charAt(i);
            if(originalString.charAt(i) == '0'){
                newHexString += zero;
            }
        }
        return "#" + newHexString;
    }

    private BufferedImage resize(BufferedImage image, int w, int h){
        Image temp = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(temp, 0, 0, null);

        return newImage;
    }


}//end class