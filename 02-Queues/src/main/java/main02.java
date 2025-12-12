import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class main02 {
    //IMPORTANT: Change the path to where the .csv is
    static final String PATH = "C:\\Users\\pcama\\IdeaProjects\\EDPA-02-2.0\\02-Queues\\src\\main\\resources\\player_requests.csv"; //Path to .csv
    private static final int MAX_NON_PREMIUM_MATCHES = 1; //Given by statement. Refers to max amount of non premium matches that can be created at the same time
    private static final int MAX_PREMIUM_MATCHES = 2; //Given by statement. Refers to max amount of premium matches that can be created at the same time

    /*********************************************************************
    *
    * Method name: createMatch
    *
    * Description of the Method: creates a match for 2 players to play
    *
    * Calling arguments: Queue<MatchRequest> queue (the match to be played), String matchType (type of match), int maxMatches (limit of matches that can be played)
    *
    * Return value: void, returns the match to be played
    *
    *********************************************************************/

    public static void createMatch(Queue<MatchRequest> queue, String matchType, int maxMatches){
        int counter = 0;
        while(counter < maxMatches && queue.size() > 1){
            System.out.println("Match generated! this will be a " + matchType + " match.");
            System.out.println(queue.remove().playerInfo() + " VS " + queue.remove().playerInfo());
            System.out.println("Good luck to both players!!");
            System.out.println(" ");
            counter ++;
        }
    }

    /*********************************************************************
    *
    * Method name: showMatches
    *
    * Description of the Method: Prints all the info related to the created matches
    *
    * Calling arguments: The 4 queues we are working with (premium long, premium short, no-premium short and no-premium long)
    *
    * Return value: void, prints information
    *
    *********************************************************************/
    public static void showMatches(Queue<MatchRequest> pL, Queue<MatchRequest> pS, Queue<MatchRequest> nS, Queue<MatchRequest> nL){
        System.out.println("Starting the matchmaking process!!");

        while(pL.size() > 1 || pS.size() > 1 || nL.size() > 1 || nS.size() > 1){
            createMatch(pL, "Long, premium", MAX_PREMIUM_MATCHES);
            createMatch(pS, "Short, premium", MAX_PREMIUM_MATCHES);
            createMatch(nS, "Short, non premium", MAX_NON_PREMIUM_MATCHES);
            createMatch(nL, "Long, non premium", MAX_NON_PREMIUM_MATCHES);
        }
    }
    public static void main(String[] args) {
        try {
            MatchRequest mr = new MatchRequest();

            SequentialFile<MatchRequest> file = new SequentialFile<>(PATH, ";");
            //IMPORTANT!! Change the path so that its where player_requests.csv is!!
            Queue <MatchRequest> nL = new LinkedBlockingQueue<MatchRequest>(); //nopremium long matches
            Queue <MatchRequest> nS = new LinkedBlockingQueue<MatchRequest>(); //nopremium short matches

            PriorityQueue <MatchRequest> pL = new PriorityQueue<MatchRequest>(); //premium long matches
            PriorityQueue <MatchRequest> pS = new PriorityQueue<MatchRequest>(); //premium short matches

            file.skipLine(); //Header won't be read
            while (file.checkEOF()) { 
                file.read(mr);
                System.out.println(mr);
                switch (mr.queueType(mr)) {
                    case "PL":
                        pL.add(mr);
                        break;
                    case "PS":
                        pS.add(mr);
                        break;                        
                    case "RL":
                        nL.add(mr);
                        break;
                    case "RS":
                        nS.add(mr);
                        break;
                }
                mr = new MatchRequest(); //Needed so that the reader keeps reading something other than the same player
            } 

            //Up until here, we read from the .csv, and fill the queues.

            showMatches(pL, pS, nS, nL);

        } catch(IOException e){
            System.out.println("Something went wrong. That file does not exist");
        }
      
    }
}
