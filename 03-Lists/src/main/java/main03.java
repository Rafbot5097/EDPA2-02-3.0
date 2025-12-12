import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;

public class main03 {
    /*********************************************************************
    *
    * Class Name: main03
    * Author/s name: PC, RCR, LD - Group 02
    * Release/Creation date: 03/11/2025
    * Class description: main class for assignment 03
    *
    **********************************************************************/

    //IMPORTANT: Change the path to where the .csv is
    static final String PATH = "C:\\Users\\pcama\\IdeaProjects\\EDPA-02-2.0\\03-Lists\\src\\main\\resources\\artists.csv"; //Path to .csv

    static final int RIVER_START_HOUR = 13; //Hour of start for river stage
    static final int RIVER_START_MIN = 30;  //Minute of start for river stage

    static final int MAIN_START_HOUR = 14; //Hour of start for main stage
    static final int MAIN_START_MIN = 0;   //Minute of start for main stage

    public static LinkedList<Artist>[] subdivideLists(LinkedList<Artist> day)  {
        /*********************************************************************
        *
        * Method Name: subdivideLists
        * Name of the original author: PC
        * Description of the Method: Subdivides original list into two, depending on which day 
        * and stage the artists play in
        * Calling arguments: LinkedList<Artist> that will be subdivided
        * Return value: Returns two lists, one for main stage artists and the other for river stage ones
        * Required files: artist.csv must be opened
        *
        ***********************************************************************/

        Artist current;
        LinkedList<Artist> mainStage = new LinkedList<Artist>();
        LinkedList<Artist> riverStage = new LinkedList<Artist>();

        for (int i = 0; i < day.size(); i++) {
            current = day.get(i);
            if(current.stage.equals("Main")){
                mainStage.add(current);
            } else {
                riverStage.add(current);
            }
        }

        LinkedList<Artist> toReturn[] = new LinkedList[] {mainStage, riverStage};

        return toReturn;
    }   

    public static LinkedList<Artist> comparePopDurNam(LinkedList<Artist> list1){

        /*********************************************************************
        *
        * Method Name: comparePopDurName
        * Name of the original author: RCR
        * Description of the Method: Orders a list following the criteria given in
        * assignment03 (ascending popularity, then ascending duration and then alphabetical)
        * Calling arguments: LinkedList<Artist> that will be ordered according to the criteria
        * Return value: Returns the ordered list
        * Required files: artist.csv must be opened
        *
        ***********************************************************************/

        Comparator<Artist> compare = Comparator.comparing(Artist::getPopularity)
                                    .thenComparing(Artist::getDuration)
                                    .thenComparing(Artist::getName);
        
        list1.sort(compare);

        return list1;    
    }

    public static void printSchedule(LinkedList<Artist> list, int startHour, int startMinute){

        /*********************************************************************
        *
        * Method name: printSchedule
        *
        * Name of the original author: LD
        *
        * Description of the Method: prints the schedule for each stage, keeping in mind that the times
        * must be updated
        *
        * Calling arguments: LinedList<Artist> where the ordered artist for each day and stage are,
        * and start time (depends on what stage we are working with)
        *
        * Return value: void, only prints
        *
        * Required Files: artist.csv must be opened
        *
        *********************************************************************/

        int totalDuration = 0; 
        int popularitySum = 0;
        int recess = 30;
        int hour = startHour;
        int minute = startMinute;

        System.out.println(list.peek().stage  +" Stage schedule: ");
        System.out.println("--------------------------------------");


        for (Artist artist : list){
            // Format time as HH:MM
            String time = String.format("%02d:%02d", hour, minute);
            
            //Print schedule and artist info
            
            System.out.println(time + " _ "+ artist.getName() +" Genre: "+ artist.genre + " Duration: "+ artist.getDuration()+ " mins" + " Popularity: "+ artist.getPopularity());
            totalDuration += artist.getDuration();
            popularitySum += artist.getPopularity();

            // Update time and minute keeping in mind the recess
            int totalMinutes = artist.getDuration() + recess;
            hour += totalMinutes / 60;
            minute += totalMinutes % 60;
            if ( minute >= 60){
                hour += 1;
                minute -= 60;
            }
            if (hour>=24) {
            	hour -= 24;
            }
        }

        // Accumulate data for final statistics
        double avgPopularity = list.isEmpty() ? 0 : (double) popularitySum / list.size();
        String headliner = list.isEmpty() ? "None" : list.getLast().getName();

        //Show statistics
        System.out.println("Total music time: "+ totalDuration + " min");
        System.out.printf("Average popularity: %.2f\n", avgPopularity);
        System.out.println("Headliner: "+ headliner);
        System.out.println();
    }


	public static void main(String[] args) {
		
        LinkedList<Artist>[] result;
        // Lists for each day and stage
        LinkedList<Artist> day1 = new LinkedList<Artist>();
        LinkedList<Artist> day2 = new LinkedList<Artist>();
        LinkedList<Artist> day3 = new LinkedList<Artist>();

        LinkedList<Artist> day1Main;
        LinkedList<Artist> day2Main;
        LinkedList<Artist> day3Main;

        LinkedList<Artist> day1River;
        LinkedList<Artist> day2River;
        LinkedList<Artist> day3River;

        try {
            SequentialFile<Artist> file = new SequentialFile<>(PATH, ";");
            file.skipLine(); //Skip the header

            //We add all artists on their corresponding list depending on the day they play
            while(file.checkEOF()){
            	Artist art = new Artist();
                file.read(art);
                switch (art.day) {
                    case 1:
                        day1.add(art);
                        break;
                    case 2:
                        day2.add(art);
                        break;
                    case 3:
                        day3.add(art);
                        break;
                }
                
            }

        result = subdivideLists(day1);

        day1Main = result[0];
        day1River = result[1];

        result = subdivideLists(day2);
        day2Main = result[0];
        day2River = result[1];

        result = subdivideLists(day3);
        day3Main = result[0];
        day3River = result[1];

        //Orders all lists depending on their popularity, duration and name
        day1Main = comparePopDurNam(day1Main);
        day2Main = comparePopDurNam(day2Main);
        day3Main = comparePopDurNam(day3Main);

        day1River = comparePopDurNam(day1River);
        day2River = comparePopDurNam(day2River);
        day3River = comparePopDurNam(day3River);

        //Prints all days and stages schedules
        System.out.println("Day 1: ");
        printSchedule(day1Main, MAIN_START_HOUR, MAIN_START_MIN);
        printSchedule(day1River, RIVER_START_HOUR, RIVER_START_MIN);

        System.out.println("Day 2: ");
        printSchedule(day2Main, MAIN_START_HOUR, MAIN_START_MIN);
        printSchedule(day2River, RIVER_START_HOUR, RIVER_START_MIN);

        System.out.println("Day 3: ");
        printSchedule(day3Main, MAIN_START_HOUR, MAIN_START_MIN);
        printSchedule(day3River, RIVER_START_HOUR, RIVER_START_MIN);


        } catch (IOException e) {
            System.out.println("Something went wrong. That file does not exist");
        }    
    }
}
