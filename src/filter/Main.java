package filter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static filter.Linear.linearOpt;
import static filter.Non_Lenear.median;

/**
 * Created by kaaveh on 4/20/16.
 */
public class Main {

    public static int[][] mask;
    public static int [][] inputMatrix;
    public static int [][] outputMatrix;
    public static int sum=0;
    public static int choice;

    public static void main(String[] args) {
        File input = new File("/home/kaaveh/Projects/Java/IDEA/Image Processing/resources/a.jpg");
        File output = new File("/home/kaaveh/Projects/Java/IDEA/Image Processing/resources/b.jpg");

        BufferedImage imageInput = null;
        Color color;

        try {
            imageInput = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = imageInput.getWidth();
        int height = imageInput.getHeight();
        inputMatrix = new int[height][width];
        outputMatrix = new int[height][width];
        int dim;

        Scanner inputOrder = new Scanner(System.in);

        System.out.println("What's dimension of your favorite filter?");
        System.out.println("1. 3*3");
        System.out.println("2. 5*5");
        choice = inputOrder.nextInt();

        if (choice == 1)
            dim= 3;
        else
            dim= 5;
        mask = new int[dim][dim];

        //customize your mask
        System.out.println("Please choose a Filter:");
        System.out.println("1. Smooth(gussi)");
        System.out.println("2. Average");
        System.out.println("3. Sharp(+)");
        System.out.println("4. Sharp(-)");
        System.out.println("5. Median");

        choice = inputOrder.nextInt();

        if (choice ==1)
            smooth();
        else if (choice ==2)
            average(dim);
        else if (choice ==3)
            plusSharp();
        else if (choice ==4)
            minusSharp();
        else if (choice ==5)
            median(dim, height, width);

        //initial inputMatrix
        for (int i=0; i< height; i++){
            for (int j=0; j< width; j++){
                color = new Color(imageInput.getRGB(j, i));
                inputMatrix[i][j] = color.getBlue();
            }
        }

        //initial outputMatrix
        for (int i=0; i< height;i++){
            for (int ii=0; ii< dim/2; ii++) {

                outputMatrix[i][ii] = inputMatrix[i][ii];
                outputMatrix[ii][i] = inputMatrix[ii][i];
                outputMatrix[i][width - 1-ii] = inputMatrix[i][width - 1-ii];
                outputMatrix[height - 1-ii][i] = inputMatrix[height - 1-ii][i];
            }
        }

        if (choice == 5){
            median(dim, height, width);
        }else {
            linearOpt(dim, height, width);
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


    private static void average(int dim){

        for (int i=0; i< dim; i++){
            for (int j=0; j< dim; j++){
                mask[i][j] = 1;
            }
        }
    }

    private static void smooth(){
        mask[0][0] = 1;
        mask[0][1] = 2;
        mask[0][2] = 1;
        mask[1][0] = 2;
        mask[1][1] = 4;
        mask[1][2] = 2;
        mask[2][0] = 1;
        mask[2][1] = 2;
        mask[2][2] = 1;
    }

    private static void plusSharp(){
        mask[0][0] = 0;
        mask[0][1] = 1;
        mask[0][2] = 0;
        mask[1][0] = 1;
        mask[1][1] = -4;
        mask[1][2] = 1;
        mask[2][0] = 0;
        mask[2][1] = 1;
        mask[2][2] = 0;
    }

    private static void minusSharp(){
        mask[0][0] = 0;
        mask[0][1] = -1;
        mask[0][2] = 0;
        mask[1][0] = -1;
        mask[1][1] = 4;
        mask[1][2] = -1;
        mask[2][0] = 0;
        mask[2][1] = -1;
        mask[2][2] = 0;
    }
}
