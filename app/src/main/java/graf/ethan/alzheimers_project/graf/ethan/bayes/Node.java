package graf.ethan.alzheimers_project.graf.ethan.bayes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ethan on 7/22/2015.
 */
public abstract class Node {
    private List<Node> parents;
    private List<Node> children;

    private String[] resultPossibilities;
    private Map<String, Float> result;

    public Node() {
        this.parents = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public  List<Node> getParents() {
        return parents;
    }

    public  List<Node> getChildren() {
        return children;
    }

    public void setParent(Node parent) {
        parents.add(parent);
    }

    public void setChild(Node child) {
        children.add(child);
    }

    public void setResultPossibilities(String[] results) {
        this.resultPossibilities = results;
    }

    public abstract void setTable(Float[][] table);

    //Get the number of possible results.
    public int getTableX() {
        return resultPossibilities.length;
    }

    //Get the number of possible combinations of input.
    public int getTableY() {
        int y = 1;
        for(int i = 0; i < parents.size(); i++) {
            y *= parents.get(i).getTableX();
        }
        return y;
    }

    //Set the result for this node.
    public abstract void setResult();


    public void calculateResult() {
        for(int i = 0; i < parents.size(); i++) {
            if(parents.get(i).result == null) {
                parents.get(i).calculateResult();
            }
        }
    }

    //Get the results for this node.
    public Map<String, Float> getResult() {
        return result;
    }
}
