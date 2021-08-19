package assignment3;

public class Edge {
	private String name;
	private Integer weight;
	
	public Edge(String name, Integer weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getWeight() {
		return this.weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
