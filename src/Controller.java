import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Controller extends JPanel implements ActionListener {
    private View view;
    private Model model;
    private Image image;
    private BufferedImage bufferedImage = null;
    private Point start = null;
    private boolean canClickBool = false;
    private String imageName;
    private String imagePath;
    private int allPixels;
    private int uniqueColors;
    private SQLiteHelper imageHelper;

    public Controller() {
        this.view = new View(this);
        imageHelper = new SQLiteHelper();
        view.setExtendedState(Frame.MAXIMIZED_BOTH);

        //we want to load in the image previously saved image, if there is one
        imageHelper.getAllImages();
        System.out.println(imageHelper.images);
        if(!imageHelper.images.isEmpty()){
            ImageInfo imageInfo = imageHelper.getLastUsedImage();
            imageName = imageInfo.getName();
            imagePath = imageInfo.getPath();
            allPixels = imageInfo.getAllPixels();
            uniqueColors = imageInfo.getUniqueColors();

            try {
                File file = new File(imagePath);
                //if the file fails to be read,
                bufferedImage = ImageIO.read(file);
                image = bufferedImage;
                image = image.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);
                bufferedImage = imageToBufferedImage(image);

                model = new Model(this, bufferedImage);
                ImageIcon icon = new ImageIcon(image);
                view.imageIcon.setIcon(icon);
                view.analyzeButton.setVisible(true);
                canClickBool = true;

                //setting the text fields on create of system
                view.imageNameCurrent.setText(imageName);
                view.imagePathCurrent.setText(imagePath);
                view.imageUniqueColorsCurrent.setText(String.valueOf(uniqueColors));
                view.imagePixelsCurrent.setText(String.valueOf(allPixels));
            }catch(IOException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(Controller.this, "Whoops. file failed to load. Sorry about that.", "Error.", JOptionPane.WARNING_MESSAGE);
            }
        }

        view.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               super.windowClosing(e);
               int choice = JOptionPane.showConfirmDialog(Controller.this, "Do you want to save this image for next time?", "Closing", JOptionPane.YES_NO_CANCEL_OPTION,
                       JOptionPane.WARNING_MESSAGE, null);
               if (choice == JOptionPane.CANCEL_OPTION) {
                   //let the user stay on the interface
               }else if(choice == JOptionPane.NO_OPTION){
                   imageHelper.deleteAllImages();
                   System.out.println(imageHelper.images);
                   imageHelper.closeConnection();
                   System.exit(0);
               }else if(choice == JOptionPane.YES_OPTION){
                   ImageInfo imageInfo = new ImageInfo(imageName, imagePath, allPixels, uniqueColors);
                   imageHelper.insertImage(imageInfo);
                   imageHelper.closeConnection();
               }
           }
       });

        view.uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bufferedImage = null;
                image = null;
                resetPanel();
                uploadImage();
            }
        });

        view.analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.analyzeButton.setVisible(false);
                setViewColors();
                canClickBool = true;
                view.radioPanel.setVisible(true);

                allPixels = model.totalSize;
                uniqueColors = model.pixelMap.size();

                view.imagePixelsCurrent.setText(String.valueOf(allPixels));
                view.imageUniqueColorsCurrent.setText(String.valueOf(uniqueColors));

                //imageInfo = new ImageInfo(imageName, imagePath, allPixels, uniqueColors);
            }
        });

        view.imageIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(canClickBool) {
                    start = e.getPoint();
                    int x = start.x;
                    int y = start.y;

                    int colorAtClick = model.getRGBatPixel(x, y);
                    int[] rgbColors = model.getRGBArray(colorAtClick);

                    view.color1.setBackground(new Color(rgbColors[0], rgbColors[1], rgbColors[2]));
                    view.color1.setText("R: " + rgbColors[0] + " G: " + rgbColors[1] + " B: " + rgbColors[2]);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e){
                if(canClickBool){
                    view.imageIcon.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }else{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        view.noneRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canClickBool = true;

                ImageIcon icon = new ImageIcon(image);
                view.imageIcon.setIcon(icon);
            }
        });
    }//end constructor

    @Override
    public void actionPerformed(ActionEvent e) {
        Image editImage = bufferedImage;
        editImage = editImage.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);

        String actionCommand = e.getActionCommand();
        if(actionCommand.equals("blackWhite"))
            editImage = model.makeImageGrayscale(editImage);
        else if(actionCommand.equals("negative"))
            editImage = model.makeImageNegative(editImage);
        else if(actionCommand.equals("threshold"))
            editImage = model.makeImageThreshold(editImage);
        else if(actionCommand.equals("quantize"))
            editImage = model.makeImageQuantize(editImage);
        else if(actionCommand.equals("pixelate"))
            editImage = model.makeImagePixelate(editImage);
        else if(actionCommand.equals("edgeDetect"))
            editImage = model.makeImageEdgeDetect(editImage);
        else if(actionCommand.equals("sobel"))
            editImage = model.makeImageSobel(editImage);
        else if(actionCommand.equals("dither"))
            editImage = model.makeImageDither(editImage);

        ImageIcon icon = new ImageIcon(editImage);
        view.imageIcon.setIcon(icon);
        canClickBool = false;
    }

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
            try {
                bufferedImage = ImageIO.read(file);
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Could not read file type. \n  (Must be .jpg or .png)", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            image = bufferedImage;
            image = image.getScaledInstance(view.imageIcon.getWidth(), view.imageIcon.getHeight(), Image.SCALE_SMOOTH);
            bufferedImage = imageToBufferedImage(image);
            model = new Model(this, bufferedImage);

            ImageIcon icon = new ImageIcon(image);
            view.imageIcon.setIcon(icon);
            view.analyzeButton.setVisible(true);

            //updating info in the west panel
            view.imageNameCurrent.setText(file.getName());
            view.imagePathCurrent.setText(file.getPath());
            imageName = fileChooser.getSelectedFile().getName();
            imagePath = file.getPath();
        }else if(returnVal == JFileChooser.ERROR_OPTION) {
            JOptionPane.showMessageDialog(null, "Invalid file type. Cmon.", "Nope", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setViewColors(){
        int[] rgb = model.colorHex1;
        String color = "R: " + rgb[0] + " G: " + rgb[1] + " B: " + rgb[2];
        view.color1.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
        view.color1.setText(color);
        view.mostCommonColorCurrent.setText(color);
    }

    private void resetPanel(){
        view.mostCommonColorCurrent.setText("R: -  G: -  B: -");
        view.imageUniqueColorsCurrent.setText("-");
        view.imagePathCurrent.setText("-");
        view.imageNameCurrent.setText("-");
        view.imagePixelsCurrent.setText("-");
        view.imageIcon.setIcon(new ImageIcon("images/blank-image.jpg"));
        view.color1.setBackground(Color.decode("#DDDDDD"));
        view.radioPanel.setVisible(false);
        view.color1.setText("R: -  G: -  B: -");
        //what else do we need to do to clear the board?
        //model.image = null;
    }

    private BufferedImage imageToBufferedImage(Image sourceImage){
        BufferedImage bufferedImage = new BufferedImage(view.imageIcon.getWidth(), view.imageIcon.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(sourceImage, 0, 0, null);
        g2.dispose();

        return bufferedImage;
    }

}//end class






























