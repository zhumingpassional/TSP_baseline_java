package org.example.src;

public class Node {
    private static int _countId = 0;
    private int _id;
    private double _x;
    private double _y;

    public Node(int index, double x, double y){
        _id = index;
        _x = x;
        _y = y;
    }

    public Node(double x, double y){
        _id = _countId++;
        _x = x;
        _y = y;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double get_x() {
        return _x;
    }

    public void set_x(double _x) {
        this._x = _x;
    }

    public double get_y() {
        return _y;
    }

    public void set_y(double _y) {
        this._y = _y;
    }
}
