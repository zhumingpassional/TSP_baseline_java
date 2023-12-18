package org.example.src;

public class Node {
    private int _index;
    private int _x;
    private double _y;

    public Node(int index, int x, double y){
        _index = index;
        _x = x;
        _y = y;
    }

    public int get_index() {
        return _index;
    }

    public void set_index(int _index) {
        this._index = _index;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public double get_y() {
        return _y;
    }

    public void set_y(double _y) {
        this._y = _y;
    }
}
