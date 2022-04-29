package AStar;

public class Node {
    public int x, y, g, h, f;
    public Node parent;
    public boolean moveable;

    public Node(int x, int y, boolean moveable) {
        this.x = x;
        this.y = y;
        this.moveable = moveable;
        parent = null;
    }
    public int getF() {
        return f;
    }

    public void setF() {
         f = g+h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getG() {
        return g;
    }
    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean equals(Object obj) {
        Node node = (Node) obj;

        return getX() == node.getX()&& getY() == node.getY();
    }

    @Override
    public String toString() {
        return "["+ getX() + ", " + getY()+ "]";
    }
}
