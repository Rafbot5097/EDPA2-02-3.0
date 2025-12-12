import graphsDS_ESI_UCLM_v2.*;
import java.io.*;
import java.math.*;
import java.util.*;

/*********************************************************************
 *
 * Class Name: GraphBuilder
 * Author/s name: PC, RCR, LD - Group 02
 * Release/Creation date: 13/11/2025
 * Class description: Class responsible for building the graph from the CSV and applying the filtering conditions specified
 *
 *********************************************************************/

public class GraphBuilder {

    
    private Graph graph;
    private int totalSegments = 0;      // number of lines (segments) read from CSV
    private int validSegments = 0;      // number of segments that pass the filters
    private double totalLength = 0.0;   // sum of the lengths of valid segments

    // Map to track the degree of each intersection (vertex)
    private Map<String, Integer> vertexDegrees = new HashMap<>();

    
    public GraphBuilder() {
        this.graph = new TreeMapGraph();
    }

    /*****************************************************************
     * Reads the CSV file, creates BikewaySegment objects, applies the
     * filters and fills the graph with valid edges decorated with
     * BikewayDecorator.*/

    public void loadCsv(String filePath) {
        File csvFile = new File(filePath);
        System.out.println("Loading CSV: " + filePath);
        try (Scanner scanner = new Scanner(csvFile)) {
            if (!scanner.hasNextLine()) {
                System.err.println("ERROR: CSV file is empty: " + csvFile.getAbsolutePath());
                return;
            }
            // Skip header
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                // every non-header line as one segment read
                totalSegments++;
                String[] fields = line.split(";");
                try {
                    // Adjust indexes if necessary according to your CSV:
                    int id = Integer.parseInt(fields[0].trim());   // Segment ID
                    String speedStr = fields[10].trim();         // Speed limit
                    String lengthStr = fields[17].trim();         // Length (m)
                    String yearStr= fields[18].trim();         // Year of construction
                    String geom = fields[22].trim();         // Geometry [lon,lat] [lon,lat]...

                    String snowStr = fields[21].trim();         // Snow removal service
                    boolean snowRemoval = snowStr.equalsIgnoreCase("Yes")|| snowStr.equalsIgnoreCase("Y")|| snowStr.equalsIgnoreCase("True")|| snowStr.equalsIgnoreCase("T");
                    // any essential field is missing,discard the segment
                    if (speedStr.isEmpty() || lengthStr.isEmpty() || yearStr.isEmpty() || geom.isEmpty()) {
                        continue; // go to next line
                    }
                    int speed = Integer.parseInt(speedStr);
                    double length = Double.parseDouble(lengthStr);
                    int year = Integer.parseInt(yearStr);
                    // Apply filters from the assignment:
                    // - Minimum speed: 30 km/h
                    // - Minimum length: 10 m
                    // - Year >= 1990
                    if (speed < 30 || length <= 10.0 || year < 1990) {
                        continue;
                    }
               
                    String[] pointStrings = geom.split("\\]\\s*\\[");
                    String firstPoint = pointStrings[0].replace("[", "").replace("]", "").trim();
                    String lastPoint = pointStrings[pointStrings.length - 1].replace("[", "").replace("]", "").trim();
                    String[] startParts = firstPoint.split(",");
                    String[] endParts = lastPoint.split(",");

                    String startLon = startParts[0].trim();
                    String startLat = startParts[1].trim();
                    String endLon= endParts[0].trim();
                    String endLat = endParts[1].trim();

                    String startCoord = roundTo5(startLon) + "," + roundTo5(startLat);
                    String endCoord = roundTo5(endLon)+ "," + roundTo5(endLat);

                    // Avoid degenerate edges (same origin and destination)
                    if (startCoord.equals(endCoord)) {
                        continue;
                    }

                    // Create segment object
                    BikewaySegment segment = new BikewaySegment(id,speed,length,year,snowRemoval,startCoord,endCoord);

                    // Insert vertices into the graph 
                    Vertex vStart = graph.insertVertex(startCoord);
                    Vertex vEnd = graph.insertVertex(endCoord);
                    // Insert edge and decorate it with BikewayDecorator
                    Edge edge = graph.insertEdge(vStart, vEnd,null);
                    edge.setDecorator(new BikewayDecorator(segment));

                    // Update statistics
                    validSegments++;
                    totalLength +=segment.getLength();
                    // Update degree counts
                    incrementDegree(startCoord);
                    incrementDegree(endCoord);
                } catch (Exception parseEx) {
                 
                }
            }

        } catch (FileNotFoundException e) {
            String absPath = csvFile.getAbsolutePath();
            System.err.println("ERROR: CSV file not found: " + absPath);
            // We stop execution with a clear runtime error
            throw new IllegalStateException("CSV file not found: " + absPath, e);
        }
    }

   
     //  method to round a numeric string to 5 decimal places
    
    private String roundTo5(String value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(5, RoundingMode.HALF_UP);
        return String.valueOf(bd.doubleValue());
    }

    
     // Helper to increase the degree of a vertex identified by its
     // coordinate string.
     
    private void incrementDegree(String coord) {
        Integer deg = vertexDegrees.get(coord);
        if (deg == null) {
            vertexDegrees.put(coord, 1);
        } else {
            vertexDegrees.put(coord, deg + 1);
        }
    }

    
    public Graph getGraph() {
        return graph;
    }

    public int getTotalSegments() {
        return totalSegments;
    }

    public int getValidSegments() {
        return validSegments;
    }

    public double getTotalLength() {
        return totalLength;
    }

    public double getAverageSegmentLength() {
        if (validSegments == 0) {
            return 0.0;
        }
        return totalLength / validSegments;
    }

     // Counts how many intersections (vertices) have degree > 2.
     
    public int countIntersectionsWithDegreeGreaterThan2() {
        int count = 0;
        for (int degree : vertexDegrees.values()) {
            if (degree > 2) {
                count++;
            }
        }
        return count;
    }
}
