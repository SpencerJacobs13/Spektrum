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

    public Controller(Model model) {
        this.model = model;
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
                    String fileName = file.getAbsolutePath();

                    //trying to resize the image
                    try {
                        bufferedImage = ImageIO.read(fileChooser.getSelectedFile());
                    }catch(IOException m){
                        m.printStackTrace();
                    }
                    image = bufferedImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);

                    //if all is good, update the image view
                    System.out.println("You chose:" + fileChooser.getSelectedFile().getName());
                    ImageIcon imageIcon = new ImageIcon(image);
                    view.imageIcon.setIcon(imageIcon);
                    view.analyzeButton.setVisible(true);

                }
            }
        });//end imageUploadButton anon class
    }
}//end class


