import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Graph {
	private Hashtable<Integer, LinkedList<Edge>> list;
	private LinkedList<Integer> vertices;

	public Graph() {
		list = new Hashtable<>();
		vertices = new LinkedList<>();
	}

	class Edge {
		Integer adjacent;
		Integer weight;

		@Override
		public String toString() {
			return "Edge [adjacent=" + adjacent + ", weight=" + weight + "]";
		}

		Edge(Integer adjacent, Integer weight) {
			this.adjacent = adjacent;
			this.weight = weight;
		}
	}

	public void addVertex(Integer vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException();
		}
		if(vertices.contains(vertex)) {
			return;
		}
		list.put(vertex, new LinkedList<>());
		vertices.add(vertex);
	}

	public void deleteVertex(Integer vertex) {
		if (vertex == null) {
			throw new IllegalArgumentException();
		}
		if(!vertices.contains(vertex)) {
			throw new IllegalArgumentException();
		}
		for (Edge edge : list.get(vertex)) {
			LinkedList<Edge> adjacents = list.get(edge.adjacent);
			for (Edge edge2 : adjacents) {
				if (edge2.adjacent.equals(vertex)) {
					adjacents.remove(edge2);
					break;
				}
			}
		}
		list.remove(vertex);
		vertices.remove(vertex);
	}

	public void addEdge(Integer vertex1, Integer vertex2, Integer weight) {
		if(!vertices.contains(vertex2)||!vertices.contains(vertex1)) {
			throw new IllegalArgumentException();
		}
		LinkedList<Edge> tempList = list.get(vertex1);
		for(Edge edge: tempList) {
			if(edge.adjacent.equals(vertex2)) {
				this.deleteEdge(vertex1, vertex2);
				break;
			}
		}
		tempList.add(new Edge(vertex2, weight));
		if (vertex1.equals(vertex2)) {
			return;
		}
		list.get(vertex2).add(new Edge(vertex1, weight));
	}

	public void deleteEdge(Integer vertex1, Integer vertex2) {
		
		if(!vertices.contains(vertex2)||!vertices.contains(vertex1)) {
			throw new IllegalArgumentException();
		}
		LinkedList<Edge> adjacents = list.get(vertex1);
		for (Edge edge : adjacents) {
			if (edge.adjacent.equals(vertex2)) {
				adjacents.remove(edge);
				break;
			}
		}
		if (vertex1.equals(vertex2)) {
			return;
		}
		adjacents = list.get(vertex2);
		for (Edge edge : adjacents) {
			if (edge.adjacent.equals(vertex1)) {
				adjacents.remove(edge);
				return;
			}
		}
	}

	public void deleteEdge(Integer vertex1, Integer vertex2, Integer weight) {
		LinkedList<Edge> adjacents = list.get(vertex1);
		for (Edge edge : adjacents) {
			if (edge.adjacent.equals(vertex2) && edge.weight.equals(weight)) {
				adjacents.remove(edge);
				break;
			}
		}
		if (vertex1.equals(vertex2)) {
			return;
		}
		adjacents = list.get(vertex2);
		for (Edge edge : adjacents) {
			if (edge.adjacent.equals(vertex1) && edge.weight.equals(weight)) {
				adjacents.remove(edge);
				return;
			}
		}
	}

	public String displayEdges() {
		String ret = "";
		for (Integer vertex : vertices) {
			LinkedList<Edge> adjacents = list.get(vertex);
			for (Edge edge : adjacents) {
				ret += vertex + "---(" + edge.weight + ")---" + edge.adjacent + "\n";
			}
		}
		return ret;
	}
	
	public Integer dijkstra(Integer vertex1, Integer vertex2) {
		if(!vertices.contains(vertex2)||!vertices.contains(vertex1)) {
			throw new IllegalArgumentException();
		}
		ArrayList <Integer> calculated = new ArrayList<>();
		ArrayList <Integer> not_calculated = new ArrayList<>();
		for(Integer vertex: vertices) {
			not_calculated.add(vertex);
		}
		Integer [] costs = new Integer[vertices.size()];
		for(int i=0;i<costs.length;i++) {
			costs[i]=new Integer(Integer.MAX_VALUE); // infinity value
		}
		Integer [] descendants = new Integer[vertices.size()];
		for(int i=0;i<costs.length;i++) {
			descendants[i]=new Integer(-1); //no descendants
		}
		costs[vertices.indexOf(vertex1)]=0; //cost of going to himself is 0
		
		for(int i=0;i<vertices.size();i++) { //loop through all vertices
			int active_index=findMin(not_calculated,costs); //move vertex not calculated with the least cost
			calculated.add(vertices.get(active_index));
			not_calculated.remove(vertices.get(active_index));
			for(Edge edge: list.get(vertices.get(active_index))) { //go through all adjacents
				if(!not_calculated.contains(edge.adjacent)) {	//find adjacent present in not calculated
					continue;
				}
				int adj_index = vertices.indexOf(edge.adjacent);
				if(costs[adj_index]>costs[active_index]+edge.weight) {
					costs[adj_index]=costs[active_index]+edge.weight;
					descendants[adj_index]=active_index;
				}
			}
		}
		displayDijkstra(descendants,vertex2);
		return costs[vertices.indexOf(vertex2)];
	}
	private int findMin(ArrayList <Integer> not_calculated, Integer [] costs) {
		Integer min_value=Integer.MAX_VALUE;
		int min_index=vertices.indexOf(not_calculated.get(0));
		for(Integer vertex: not_calculated) {
			int index=vertices.indexOf(vertex);
			if(costs[index]<min_value) {
				min_value=costs[index];
				min_index=index;
			}
		}
		return min_index;
	}
	private void displayDijkstra(Integer [] descendants, Integer goal) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Second ex = new Second(descendants,goal);
				ex.setVisible(true);
			}
		});
	}
	

	@SuppressWarnings("serial")
	class Surface2 extends JPanel {
		static final int oval_radius=25;
		Integer [] descendants;
		Integer goal;
		public Surface2(Integer[] descendants, Integer goal) {
			this.descendants=descendants;
			this.goal=goal;
		}
		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Point [] points = setPoints();
			
			
			
			for (int i = 0; i < vertices.size(); i++) {
				Integer vertex = vertices.get(i);
				LinkedList<Edge> adjacents = list.get(vertex);
				for (Edge edge : adjacents) {
					for(int j=0;j<points.length;j++) {
						if(vertices.get(j).equals(edge.adjacent)) {
							g2d.setColor(Color.BLACK);
							g2d.drawLine(points[i].x+oval_radius, points[i].y+oval_radius, points[j].x+oval_radius, points[j].y+oval_radius);
							g2d.setColor(Color.RED);
							g2d.drawString(edge.weight.toString(), (points[i].x+points[j].x+2*oval_radius)/2-5, (points[i].y+points[j].y+2*oval_radius)/2-5);
							break;
						}
					}
					
				}
			}
			
			for(int i=0;i<points.length;i++) {
				drawVertex(g2d,points[i].x,points[i].y,vertices.get(i).toString());
			}
			Integer next_index=vertices.indexOf(goal);
			g2d.setColor(Color.GREEN);
			while(true) {
				if(descendants[next_index]==-1) {
					break;
				}
				int previous_index=descendants[next_index];
				g2d.drawLine(points[previous_index].x+oval_radius, points[previous_index].y+oval_radius, points[next_index].x+oval_radius, points[next_index].y+oval_radius);
				next_index=descendants[next_index];
			}
		//	g2d.drawOval(500-300,500-300,600,600);
		}
		private Point[] setPoints() {
			Point [] points = new Point [vertices.size()];
			if(points.length<1) {
				return null;
			}
			if(points.length>0) {
				int center_x = 500, center_y=500, circle_radius=300;
				int left=center_x-circle_radius,right=center_x+circle_radius,down=center_y+circle_radius,up=center_y-circle_radius;
				points[0] = new Point(left,center_y);
				for(int i=1;i<(points.length+1)/2;i++) {
					int x=left+((circle_radius*2)/(1+(points.length-1)/2))*i;
					Point y1_y2=calcY(x,center_x,center_y,circle_radius);
					points[i]=new Point(x,y1_y2.x);
					points[points.length-i]=new Point(x,y1_y2.y);
				}
				if(points.length%2==0) {
					points[points.length/2]=new Point(right,center_y);
				}
			}
			return points;
		}
		private Point calcY(int x, int a, int b, int r) {
			double wsp_a=1;
			double wsp_b=-2*b;
			double wsp_c=(x-a)*(x-a)+b*b-r*r;
			double delta = wsp_b*wsp_b-4*wsp_a*wsp_c;
			double y1 =(-wsp_b-Math.sqrt(delta))/(2*wsp_a);
			double y2 =(-wsp_b+Math.sqrt(delta))/(2*wsp_a);
			return new Point((int)y1,(int) y2);
		}
		private void drawVertex(Graphics2D g2d,int oval_x,int oval_y,String string) {
			
			Ellipse2D.Float oval=new Ellipse2D.Float(oval_x, oval_y,oval_radius*2,oval_radius*2);		
			g2d.setColor(Color.GRAY);
			g2d.fill(oval);
			g2d.setColor(Color.BLACK);
			g2d.draw(oval);
			g2d.setColor(Color.RED);
			g2d.drawString(string, oval_x+oval_radius, oval_y+oval_radius);
		}
		
		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			doDrawing(g);
		}
	}

	@SuppressWarnings("serial")
	public class Second extends JFrame {

		public Second(Integer [] descendants, Integer goal) {
			add(new Surface2(descendants,goal));

			setTitle("Dijkstra");
			setSize(2000, 1000);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

	}
	
	
	
	public void display() {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				First ex = new First();
				ex.setVisible(true);
			}
		});
	}

	@SuppressWarnings("serial")
	class Surface extends JPanel {
		static final int oval_radius=25;
		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Point [] points = setPoints();
			
			
			
			for (int i = 0; i < vertices.size(); i++) {
				Integer vertex = vertices.get(i);
				LinkedList<Edge> adjacents = list.get(vertex);
				for (Edge edge : adjacents) {
					for(int j=0;j<points.length;j++) {
						if(vertices.get(j).equals(edge.adjacent)) {
							g2d.setColor(Color.BLACK);
							g2d.drawLine(points[i].x+oval_radius, points[i].y+oval_radius, points[j].x+oval_radius, points[j].y+oval_radius);
							g2d.setColor(Color.RED);
							g2d.drawString(edge.weight.toString(), (points[i].x+points[j].x+2*oval_radius)/2-5, (points[i].y+points[j].y+2*oval_radius)/2-5);
							break;
						}
					}
					
				}
			}
			
			for(int i=0;i<points.length;i++) {
				drawVertex(g2d,points[i].x,points[i].y,vertices.get(i).toString());
			}
		//	g2d.drawOval(500-300,500-300,600,600);
		}
		private Point[] setPoints() {
			Point [] points = new Point [vertices.size()];
			if(points.length<1) {
				return null;
			}
			if(points.length>0) {
				int center_x = 500, center_y=500, circle_radius=300;
				int left=center_x-circle_radius,right=center_x+circle_radius,down=center_y+circle_radius,up=center_y-circle_radius;
				points[0] = new Point(left,center_y);
				for(int i=1;i<(points.length+1)/2;i++) {
					int x=left+((circle_radius*2)/(1+(points.length-1)/2))*i;
					Point y1_y2=calcY(x,center_x,center_y,circle_radius);
					points[i]=new Point(x,y1_y2.x);
					points[points.length-i]=new Point(x,y1_y2.y);
				}
				if(points.length%2==0) {
					points[points.length/2]=new Point(right,center_y);
				}
			}
			return points;
		}
		private Point calcY(int x, int a, int b, int r) {
			double wsp_a=1;
			double wsp_b=-2*b;
			double wsp_c=(x-a)*(x-a)+b*b-r*r;
			double delta = wsp_b*wsp_b-4*wsp_a*wsp_c;
			double y1 =(-wsp_b-Math.sqrt(delta))/(2*wsp_a);
			double y2 =(-wsp_b+Math.sqrt(delta))/(2*wsp_a);
			return new Point((int)y1,(int) y2);
		}
		private void drawVertex(Graphics2D g2d,int oval_x,int oval_y,String string) {
			
			Ellipse2D.Float oval=new Ellipse2D.Float(oval_x, oval_y,oval_radius*2,oval_radius*2);		
			g2d.setColor(Color.GRAY);
			g2d.fill(oval);
			g2d.setColor(Color.BLACK);
			g2d.draw(oval);
			g2d.setColor(Color.RED);
			g2d.drawString(string, oval_x+oval_radius, oval_y+oval_radius);
		}
		
		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			doDrawing(g);
		}
	}

	@SuppressWarnings("serial")
	public class First extends JFrame {

		public First() {

			initUI();
		}

		private void initUI() {

			add(new Surface());

			setTitle("Graph");
			setSize(2000, 1000);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

	}
}
