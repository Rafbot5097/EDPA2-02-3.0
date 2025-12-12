/*********************************************************************
*
* Class Name: MatchRequest
* Author/s name: PC, RCR, LD - Group 02
* Release/Creation date: 9/10/2025
* Class version: Final version
* Class description: Assignment 2 completed with all the implementation
*
**********************************************************************
*/
public class MatchRequest implements Comparable<MatchRequest>, iface{

    private int requestId; //Identifier for the request
    private String playerId; //Identifier for the player
    private boolean premiumSubscription; //Indicates if the player is premium or not
    private int skillLevel; //Skill level of the player
    private char matchType; //Type of match, S for short and L for long

    /*********************************************************************
    *
    * Method name: MatchRequest
    *
    * Description of the Method: Constructor for MatchRequest
    *
    * Calling arguments: int requestId (identifier for the request), String playerId (identifier for the player (user)), 
    * boolean premiumSubscription (boolean value indicating whether or not the player has a premium subscription), 
    * int skillLevel (skill level of the player), char matchType (type of match the player wants to play. It takes the value L (long) or S (short))
    *
    * Return value: void
    *
    *********************************************************************/
    public MatchRequest(int requestId, String playerId, boolean premiumSubscription, int skillLevel, char matchType) {
        this.requestId = requestId;
        this.playerId = playerId;
        this.premiumSubscription = premiumSubscription;
        this.skillLevel = skillLevel;
        this.matchType = matchType;
    }

    public MatchRequest(){ //Used to create empty matchRequests
    }


    /*********************************************************************
    *
    * Method name: compareTo
    *
    * Description of the Method: Compares skill level between 2 players
    *
    * Calling arguments: MatchRequest other (Another player with all their parameters)
    *
    * Return value: int, returns -1, 0 or 1 depending of the skill level of both parties
    *
    *********************************************************************/

    @Override
    public int compareTo(MatchRequest other) {
        return Integer.compare(other.skillLevel, this.skillLevel);
    }

    /*********************************************************************
    *
    * Method name: readData
    *
    * Description of the Method: parses data of a player and assigns it to itself
    *
    * Calling arguments: String[] where all info is
    *
    * Return value: void, assigns each parsed data to its corresponding attribute
    *
    * Required Files: player_request.csv
    *
    *********************************************************************/

    @Override
    public void readData(String[] data) {

        this.requestId = Integer.parseInt(data[0].substring(1));
        this.playerId = data[1];
        this.premiumSubscription = Boolean.parseBoolean(data[2]);
        this.skillLevel = Integer.parseInt(data[3]);
        this.matchType = data[4].charAt(0);
						
    }

    /*********************************************************************
    *
    * Method name: queueType
    *
    * Description of the Method: Determines the type of match
    *
    * Calling arguments: MatchRequest mr (The match to be evaluated)
    *
    * Return value: String, gets the match type (Premium Long, Premium Short, Regular Long and Regular Short)
    *
    *********************************************************************/
    public String queueType(MatchRequest mr){ 
        if (mr.premiumSubscription){
            if (mr.matchType == 'L'){
                return "PL";
            } else{
                return "PS";
            }
            
        } else{
            if (mr.matchType == 'L'){
                return "RL";
            }else{
                return "RS"; 
            }

        }
     }
    

    public String playerInfo(){
        return " Player: " + playerId + " SkillLevel: " + skillLevel;
    }

    @Override
    public String toString() {
        return playerId + " (" + (premiumSubscription ? "Premium: " : "Regular: ") +
               "Skill: " + skillLevel + " MatchType: " + matchType + ")";
    }

}   



