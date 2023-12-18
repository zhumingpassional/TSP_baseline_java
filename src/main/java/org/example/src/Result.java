package org.example.src;

import java.util.ArrayList;

public class Result {
    private ArrayList<Integer> _nodeIds;
    private double _totalDist;

    private ArrayList<Node> _nodes;

//    public Result(ArrayList<Integer> nodeIds, Data data){
//        _nodeIds = nodeIds;
//        _totalDist = Util.calcTotalDistForNodeIds(nodeIds, data);
//        ArrayList<Node> nodes = new ArrayList<>();
//        for (Integer nodeId : nodeIds) {
//            Node node = data.getNodes().get(nodeId);
//            nodes.add(node);
//        }
//        _nodes = nodes;
//    }

    public Result(ArrayList<Node> nodes, Data data){
        _nodes = nodes;
        ArrayList<Integer> nodeIds = new ArrayList<>();
        for (Node node : nodes) {
            nodeIds.add(node.get_id());
        }
        _nodeIds = nodeIds;
        _totalDist = Util.calcTotalDistForNodeIds(nodeIds, data);
    }

    public ArrayList<Integer> get_nodeIds() {
        return _nodeIds;
    }

    public void set_nodeIds(ArrayList<Integer> _nodeIds) {
        this._nodeIds = _nodeIds;
    }

    public double get_totalDist() {
        return _totalDist;
    }

    public void set_totalDist(double _totalDist) {
        this._totalDist = _totalDist;
    }

    public ArrayList<Node> get_nodes() {
        return _nodes;
    }

    public void set_nodes(ArrayList<Node> _nodes) {
        this._nodes = _nodes;
    }
}
