
import java.time.LocalDateTime;


/**************************************************************************************************
 * 
 * Class Name: satellite
 * Authors name: Patricia Camacho Rivallo, Luis Dueñas Recuero, Rafael Camilo Rubio Quintero
 * Creation date: 28/09/2025
 * Class version: 1.0
 * Class description: Implements 3. of introductory assignment of Data Structures.
 * 
***************************************************************************************************
*/

public class satellite implements iface{
    private String objectName;
    private String intDesignator;
    private LocalDateTime epoch;
    private double meanMotion;
    private double eccentricity;
    private double inclination;
    private double raan;
    private double pericenter;
    
    public void readData(String[] data) {
        this.objectName = data[0];
        this.intDesignator = data[1];
        this.epoch = LocalDateTime.parse(data[2]); //Hope it's correct
        this.meanMotion = Double.parseDouble(data[3]);
        this.eccentricity = Double.parseDouble(data[4]);
        this.inclination = Double.parseDouble(data[5]);
        this.raan = Double.parseDouble(data[6]);
        this.pericenter = Double.parseDouble(data[7]);							
    }

    public double getMeanMotion(){
        return meanMotion;
    }

    @Override
    public String toString(){
        String str = "Object Name: " + objectName + " International Designator: " + intDesignator + 
        " Epoch: " + epoch + " Mean Motion: " + meanMotion + " Eccentricity: " + eccentricity + 
        " Inclination: " + inclination + " RAAN: " + raan + " Pericenter: " + pericenter;
        return str;

    }
}

