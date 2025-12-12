
public class Artist implements iface {

    /*********************************************************************
    *
    * Class Name: Artist
    * Author/s name: PC, RCR
    * Class description: Object Artist where all the information for 
    * each artist is
    *
    **********************************************************************
    */

    String name, genre, stage;
    int day, duration, popularity;

    //Constructors, getters and setters
    public Artist(){
    }
    public Artist(String n, String g, String s, int d, int dur, int p){
        this.name = n;
        this.genre= g;
        this.stage = s;
        this.day = d;
        this.duration = dur;
        this.popularity = p;
    }
    //getters
    public int getPopularity() {
        return popularity;
    } 
    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    } 
    
    @Override
    public void readData(String data[]) {
        /*********************************************************************
        *
        * Method name: readData
        *
        * Description of the Method: Parses data and assigns each attribute to itself
        *
        * Calling arguments: String with the data
        *
        * Return value: void. sets each attribute to the ones in the .csv
        *
        * Required Files: artist.csv must be opened
        *
        *********************************************************************/

        this.name = data[0];
        this.genre = data[1];
        this.day = Integer.parseInt(data[2]);
        this.stage = data[3];
        this.duration = Integer.parseInt(data[4]);
        this.popularity = Integer.parseInt(data[5]);		
    }

  
}