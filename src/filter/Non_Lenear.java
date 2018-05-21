package filter;

import java.util.ArrayList;
import java.util.Collections;

import static filter.Main.*;

/**
 * Created by kaaveh on 4/22/16.
 */
public class Non_Lenear {

    public static void median(int dim, int height, int width){
        ArrayList data = new ArrayList();

        for (int i=dim/2; i< height-dim/2; i++){
            for (int j=dim/2; j< width-dim/2; j++){

                for (int ii=0; ii<dim; ii++){
                    for (int jj=0; jj<dim; jj++){
                        data.add(inputMatrix[i-(dim/2)+ii][j-(dim/2)+jj]);
                    }
                }
                Collections.sort(data);
                outputMatrix[i][j] = (int) data.get((dim*dim)/2+1);
                data.clear();
            }
        }
    }
}
