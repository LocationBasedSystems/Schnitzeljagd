package de.htwberlin.f4.ai.ma.navigation.dijkstra;

import de.htwberlin.f4.ai.ma.node.Node;

/**
 * Created by Johann Winter
 */

class DijkstraNode {

    private final Node node;
    private final String id;


    DijkstraNode(Node node) {
        this.node = node;
        this.id = node.getId();
    }

    /**
     * Getter for the ID
     * @return the ID
     */
    public String getId() {
        return id;
    }


    /**
     * Override hashCode method of the class "Object"
     *
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    /**
     *
     * @param obj the input object
     * @return boolean if the object equals an other object
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DijkstraNode other = (DijkstraNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    /**
     * Overrides standard toString() method
     * @return the id of the DijkstraNode
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * Getter for the node object of the DijkstraNode.
     * @return the node
     */
    public Node getNode() {
        return node;
    }
}