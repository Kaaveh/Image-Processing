package filter;

import static filter.Main.*;

/**
 * Created by kaaveh on 4/22/16.
 */
public class Linear {
    public static void linearOpt(int dim, int height, int width){

        for (int i=0; i< mask.length; i++){
            for (int j=0; j< mask.length; j++){
                sum += mask[i][j];
            }
        }

        for (int i=dim/2; i< height-dim/2; i++){
            for (int j=dim/2; j< width-dim/2; j++){

                for (int ii=0; ii<dim; ii++){
                    for (int jj=0; jj<dim; jj++){
                        outputMatrix[i][j] += inputMatrix[i-(dim/2)+ii][j-(dim/2)+jj]*mask[ii][jj];
                    }
                }
                if (choice ==1 || choice==2)
                    outputMatrix[i][j] /= sum;
                else {
                    outputMatrix[i][j] += inputMatrix[i][j];

                    if (outputMatrix[i][j] >= 255)
                        outputMatrix[i][j]= 255;
                    else if (outputMatrix[i][j] <= 0)
                        outputMatrix[i][j] = 0;
                }
            }
        }
    }
}
