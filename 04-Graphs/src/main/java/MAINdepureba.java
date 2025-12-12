
import graphsDS_ESI_UCLM_v2.Edge;
import graphsDS_ESI_UCLM_v2.Graph;
import graphsDS_ESI_UCLM_v2.Vertex;

import java.util.Iterator;

public class MAINdepureba {

    public static void main(String[] args) {

        String path = "C:\\Users\\pcama\\Documents\\GitHub\\EDPA2-02\\04_Graphs\\assign05\\bikeways.csv";

        try {
            GraphBuilder builder = new GraphBuilder();

            System.out.println("Loading CSV: " + path);
            builder.loadCsv(path);

            Graph graph = builder.getGraph();

            System.out.println("\n========== RESULTS ==========");
            System.out.println("Total lines read:           " + builder.getTotalSegments());
            System.out.println("Valid segments:             " + builder.getValidSegments());
            System.out.println("Total valid length (m):     " + builder.getTotalLength());
            System.out.println("Average segment length (m): " + builder.getAverageSegmentLength());
            System.out.println("Intersections degree > 2:   " + builder.countIntersectionsWithDegreeGreaterThan2());
            // Como no hay numEdges() en Graph, usamos los segmentos válidos:
            System.out.println("Number of edges (valid):    " + builder.getValidSegments());
            System.out.println("================================");

            // EXTRA: mostrar hasta 10 aristas con su BikewaySegment (decorator)
            System.out.println("\nShowing up to 10 example segments (from edge decorators):");
            int shown = 0;

            // Graph.vertices() → Iterator<Vertex>
            Iterator<Vertex> itV = graph.getVertices();
            while (itV.hasNext() && shown < 10) {
                Vertex v = itV.next();

                // Graph.incidentEdges(v) → Iterator<Edge>
                Iterator<Edge> itE = graph.incidentEdges(v);
                while (itE.hasNext() && shown < 10) {
                    Edge e = itE.next();

                    // El decorador del Edge es un BikewayDecorator
                    BikewayDecorator deco = (BikewayDecorator) e.getDecorator();
                    if (deco != null) {
                        System.out.println("Segment: " + deco.getSegment());
                        shown++;
                    }
                }
            }

            System.out.println("\nProcess complete.");

        } catch (IllegalStateException ex) {
            System.err.println("ERROR: Could not load CSV → " + ex.getMessage());
        }
    }
}
