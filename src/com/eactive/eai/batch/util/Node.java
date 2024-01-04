package com.eactive.eai.batch.util;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private List<Node> children;
	public Node() {
		children = new ArrayList<>();		
	}
	
	public Node(String name) {
		this.name = name;
		children = new ArrayList<>();		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	public void addChild(Node node) {
		this.children.add(node);
	}
	

}
