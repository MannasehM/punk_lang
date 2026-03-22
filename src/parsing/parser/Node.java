import java.util.*;

public class Node {
    private String type;
    private List<Node> children;

    public Node(String type) {
        this.type = type;
        this.children = new ArrayList<Node>();
    }

    public String getType() {
        return this.type;
    }

    public List<Node> getChildren() {
        return this.children;
    }
}