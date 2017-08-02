package de.htwberlin.f4.ai.ma.persistence;

import java.util.ArrayList;

import de.htwberlin.f4.ai.ma.fingerprint_generator.node.Node;
import de.htwberlin.f4.ai.ma.indoor_graph.Edge;
import de.htwberlin.f4.ai.ma.prototype_temp.location_result.LocationResult;
import de.htwberlin.f4.ai.ma.prototype_temp.location_result.LocationResultImpl;

/**
 * Created by Johann Winter
 */

public interface DatabaseHandler {

    void insertNode(Node node);
    void updateNode(Node node, String oldNodeId);
    ArrayList<Node> getAllNodes();
    Node getNode(String nodeName);
    void deleteNode(Node node);

    void insertLocationResult(LocationResult locationResult);
    ArrayList<LocationResultImpl> getAllLocationResults();
    void deleteLocationResult(LocationResult locationResult);

    void insertEdge(Edge edge);
    ArrayList<Edge> getAllEdges();
    void deleteEdge(Edge edge);

}
