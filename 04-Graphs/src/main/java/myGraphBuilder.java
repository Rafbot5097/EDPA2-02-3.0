/*
import java.io.IOException;

public class myGraphBuilder {
    static final String PATH = "C:\\Users\\pcama\\Desktop\\Curso25-26\\Primer Cuatrimestre\\EDA\\Assignment05\\assign05\\bikeways.csv"; //Path to .csv

    public  static void main(String[] args) throws IOException {
        try {
            SequentialFile<BikewaySegment> file = new SequentialFile<>(PATH, ";");
            BikewaySegment debug = new BikewaySegment();
            file.skipLine(); //Header won't be read
            while (file.checkEOF()) {
                file.read(debug);

                debug = new BikewaySegment();

                file.read(debug);
                if(debug.checkValid()) {
                    debug.processCoordinates();
                    System.out.println(debug.toString());
                }
                debug = new BikewaySegment(); //Needed so that the reader keeps reading something other than the same bikeway
            }

            } catch(IOException e){
            System.out.println("Something went wrong. That file does not exist");
        }

    }

}
*/