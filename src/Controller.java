import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
                    //start the model outside of the ANON class so it doesnt have to be final
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid file type. Cmon.", "Nope", JOptionPane.ERROR_MESSAGE);
                }
            }
        });//end imageUploadButton anon class


        view.analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model = new Model(bufferedImage);
            }
        });

    }//end constructor

}//end class


