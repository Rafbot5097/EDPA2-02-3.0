
import java.io.IOException;

/**************************************************************************************************
 * 
 * Class Name: assignment0
 * Authors name: Patricia Camacho Rivallo, Luis Dueñas Recuero, Rafael Camilo Rubio Quintero
 * Creation date: 28/09/2025
 * Class version: 1.0
 * Class description: Implements 3. of introductory assignment of Data Structures. Creates a new
 * file, and prints the data of the satellites found in the file that orbit more than 10 times per day.
 * 
***************************************************************************************************
*/

public class assignment0 {
    //IMPORTANT: Change the path to where the .csv is
    static final String PATH = "C:\\Users\\pcama\\IdeaProjects\\EDPA-02-2.0\\00-Intro\\src\\main\\resources\\gp.csv";
    public static void main(String[] args){

        try{
            SequentialFile file = new SequentialFile(PATH, ",");
            file.skipLine();

            while (file.checkEOF()) {
                satellite sat = new satellite();
                file.read(sat);

                if (sat.getMeanMotion() > 10) {
                    System.out.println(sat.toString());
			    }
            }
            file.closeFile();
        }
        catch (IOException e){
            System.out.println("Something went wrong, that file does not exist");
        }
    }
}
