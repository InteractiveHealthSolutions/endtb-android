/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihsinformatics.endtb.location_provider;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Admin
 */
public class Node {

    @Override
    public String toString() {
        return name;
    }

    String name;
    int id;
    Node parent;
    private boolean isParent = false;
    private ArrayList<Node> children;

    public Node(int id, String name, String parent) {
        children = new ArrayList<>();
        this.name = name;
        this.id = id;
        if (parent != null) { // Because root has no parent
            this.parent = Location.locationTree.get(parent);
            if (this.parent!=null)
                this.parent.addChild(this);
        }
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public int addChild(Node childNode) {
        children.add(childNode);
        // return index of last added child
        return children.size() - 1;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.name);
        hash = 31 * hash + this.id;
        hash = 31 * hash + Objects.hashCode(this.parent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        return true;
    }


}
