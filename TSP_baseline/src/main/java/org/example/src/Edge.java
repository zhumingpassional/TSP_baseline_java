package org.example.src;

public class Edge implements Comparable<Edge> {
    private int _src;
    private int _dest;
    private double _weight;

    // Comparator function used for sorting edges  
    // based on their weight 
    public int compareTo(Edge compareEdge)
    { 
        return (int) (this._weight -compareEdge._weight);
    }

    public int get_src() {
        return _src;
    }

    public void set_src(int _src) {
        this._src = _src;
    }

    public int get_dest() {
        return _dest;
    }

    public void set_dest(int _dest) {
        this._dest = _dest;
    }

    public double get_weight() {
        return _weight;
    }

    public void set_weight(double _weight) {
        this._weight = _weight;
    }
}