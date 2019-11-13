import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Controller {
    private View view;
    private Model model;
    protected Image image;
    protected BufferedImage bufferedImage = null;

    public Controller() {
        this.view = new View(this);

        //anon class to allow th user to upload an image
        view.uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uploadImage();
            }
        });

        view.analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //we need to re-call the other actionPerformed function here because we need a new BufferedImage
                //before we
                model = new Model(bufferedImage);
                view.analyzeButton.setVisible(false);
                setViewColors();
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

            //if all is good, update the image view
            System.out.println("You chose:" + fileChooser.getSelectedFile().getName());
            ImageIcon imageIcon = new ImageIcon(image);
            view.imageIcon.setIcon(imageIcon);
            view.analyzeButton.setVisible(true);
            //do we want to display the file name or the file path?
            view.infoLabel.setText("You chose: " + fileChooser.getSelectedFile().getName());
            //start the model outside of the ANON class so it doesnt have to be final
        }else if(returnVal == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "Invalid file type. Cmon.", "Nope", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setViewColors(){
        //String color1 = setHexString(model.colorHex1);
        view.color1.setBackground(Color.decode(model.colorHex1));
        view.color1.setText(model.colorHex1);

        //String color2 = setHexString(model.colorHex2);
        view.color2.setBackground(Color.decode(model.colorHex2));
        view.color2.setText(model.colorHex2);

        //String color3 = setHexString(model.colorHex3);
        view.color3.setBackground(Color.decode(model.colorHex3));
        view.color3.setText(model.colorHex3);

        //String color4 = setHexString(model.colorHex4);
        view.color4.setBackground(Color.decode(model.colorHex4));
        view.color4.setText(model.colorHex4);

        //String color5 = setHexString(model.colorHex5);
        view.color5.setBackground(Color.decode(model.colorHex5));
        view.color5.setText(model.colorHex5);
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




}//end class