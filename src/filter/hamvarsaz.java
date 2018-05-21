package filter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by kaaveh on 4/10/16.
 */
public class hamvarsaz {
    public static void main(String[] args) {
        File input = new File("/home/kaaveh/input.JPG");
        File output = new File("/home/kaaveh/output.JPG");

        BufferedImage imageInput = null;
        Color color;

        try {
            imageInput = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = imageInput.getWidth();
        int height = imageInput.getHeight();
        int [][] inputMatrix = new int[height][width];
        int [][] outputMatrix = new int[height][width];
        //customize your mask
        int[][] mask = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
        int sum = 0;
        for (int i=0; i< mask.length; i++){
            for (int j=0; j< mask.length; j++){
                sum += mask[i][j];
            }
        }

        //initial inputMatrix
        for (int i=0; i< height; i++){
            for (int j=0; j< width; j++){
                color = new Color(imageInput.getRGB(j, i));
                inputMatrix[i][j] = color.getBlue();
            }
        }

        //initial outputMatrix
        for (int i=0; i< height;i++){
            outputMatrix[i][0] = inputMatrix[i][0];
            outputMatrix[0][i] = inputMatrix[0][i];
            outputMatrix[i][width-1] = inputMatrix[i][width-1];
            outputMatrix[height-1][i] = inputMatrix[height-1][i];
        }

        for (int i=2; i< height-2; i++){
            for (int j=2; j< width-2; j++){
                outputMatrix[i][j] = (
                        inputMatrix[i-2][j-2]*mask[0][0] +inputMatrix[i-2][j-1]*mask[0][1] +inputMatrix[i-2][j]*mask[0][2] +
                        inputMatrix[i-2][j+1]*mask[0][3] +inputMatrix[i-2][j+1]*mask[0][4] +inputMatrix[i-1][j-2]*mask[1][0] +
                        inputMatrix[i-1][j-1]*mask[1][1] +inputMatrix[i-1][j]*mask[1][2] +inputMatrix[i-1][j+1]*mask[1][3] +
                        inputMatrix[i-1][j+2]*mask[1][4] +inputMatrix[i][j-2]*mask[2][0] +inputMatrix[i][j-1]*mask[2][1] +
                        inputMatrix[i][j]*mask[2][2] +inputMatrix[i][j+1]*mask[2][3] +inputMatrix[i][j+2]*mask[2][4] +
                        inputMatrix[i+1][j-2]*mask[3][0] +inputMatrix[i+1][j-1]*mask[3][1] +inputMatrix[i+1][j]*mask[3][2] +
                        inputMatrix[i+1][j+1]*mask[3][3] +inputMatrix[i+1][j+2]*mask[3][4] +inputMatrix[i+2][j-2]*mask[4][0] +
                        inputMatrix[i+2][j-1]*mask[4][1] +inputMatrix[i+2][j]*mask[4][2] +inputMatrix[i+2][j+1]*mask[4][3] +
                        inputMatrix[i+2][j+2]*mask[4][4]) / sum;
            }
        }

        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                color = new Color(outputMatrix[i][j], outputMatrix[i][j], outputMatrix[i][j]);
                imageInput.setRGB(j, i, color.getRGB());
            }
        }

        try {
            ImageIO.write(imageInput, "JPG", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
