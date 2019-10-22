import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdjacentL {
	public class Edge {
		Integer weight;
		Integer target;

		public Edge(int x, int y) {
			this.target = x;
			this.weight = y;
		}

		public Edge(int x) {
			this.target = x;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public Integer getTarget() {
			return target;
		}

		public void setTarget(Integer target) {
			this.target = target;
		}

	}

	private Hashtable<Integer, LinkedList> table = new Hashtable();
	private ArrayList<Integer> keys = new ArrayList();
	private int x;

	public AdjacentL(int x) {
		this.x = x;
		System.out.println("podaj wierzcholki");
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < x; i++) {
			int z = scan.nextInt();

			table.put(z, new LinkedList<Edge>());
			System.out.println("dodano klucz" + " " + table.size());
		}
		Enumeration<Integer> klucz = this.table.keys();
		for (int i = 0; i < x; i++) {
			keys.add(klucz.nextElement());
		}

	}

	public void wypiszKraw() {

		for (int i = 0; i < x; i++) {
			System.out.print(keys.get(i));
			Iterator iter = table.get(keys.get(i)).listIterator();
			while (iter.hasNext()) {
				Edge edge = (Edge) iter.next();
				System.out.print(" Cel=" + edge.getTarget() + " Waga=" + edge.getWeight());
			}
			System.out.println();
		}

	}

	@Override
	public String toString() {
		String re = "";
		for (int i = 0; i < x; i++) {
			System.out.print(keys.get(i));
			Iterator iter = table.get(keys.get(i)).listIterator();
			while (iter.hasNext()) {
				Edge edge = (Edge) iter.next();
				re += (" Cel=" + edge.getTarget() + " Waga=" + edge.getWeight());
			}

		}
		return re;
	}

	public boolean czyistnieje(int x, int y) {
		int z = position(x);
		Iterator iter = table.get(keys.get(z)).listIterator();
		while (iter.hasNext()) {
			Edge edge = (Edge) iter.next();
			if (edge.target == y) {
				return true;
			}
		}
		return false;

	}

	public void addEdge(int x, int y) {
		addEdge(x, y, 1);
	}

	public void addEdge(int x, int y, int z) {
		if (table.containsKey(x) == false || table.containsKey(y) == false) {
			throw new IllegalArgumentException();
		}
		if (czyistnieje(x, y)) {
			System.out.println("Krawedz istnieje");
		} else {

			if (x == y) {
				table.get(x).add(new Edge(y, z));
			} else {
				table.get(x).add(new Edge(y, z));
				table.get(y).add(new Edge(x, z));
			}
		}
	}

	public void removeEdge(int x, int y) {
		if (table.containsKey(x) == false || table.containsKey(y) == false) {
			throw new IllegalArgumentException();
		}
		LinkedList<Edge> adjacents = table.get(x);
		for (Edge edge : adjacents) {
			if (edge.getTarget().equals(y)) {
				adjacents.remove(edge);
				break;
			}
		}
		if (x == y) {
			return;
		}

		adjacents = table.get(y);
		for (Edge edge : adjacents) {
			if (edge.getTarget().equals(x)) {
				adjacents.remove(edge);
				break;
			}
		}
	}

	public void ustalklucze() {
		Enumeration<Integer> klucz = this.table.keys();
		for (int i = 0; i < x; i++) {
			keys.add(klucz.nextElement());
		}
	}

	public void removeVertex(Integer Vertex) {
		if (Vertex == null) {
			throw new IllegalArgumentException();
		}
		int pos = position(Vertex);
		if (table.containsKey(Vertex) == false) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < this.x; i++) {
			LinkedList<Edge> adjacents = table.get(keys.get(i));
			for (Edge edge : adjacents) {
				if (edge.getTarget().equals(Vertex)) {
					adjacents.remove(edge);
					break;
				}
			}
		}
		table.remove(Vertex);
		keys.remove(Vertex);
		this.x = this.x - 1;
	}

	public void addVertex(Integer Vertex) {
		if (Vertex == null) {
			throw new IllegalArgumentException();
		}
		if (table.containsKey(Vertex) == true) {
			return;
		}

		table.put(Vertex, new LinkedList<Edge>());
		keys.add(Vertex);
		this.x = this.x + 1;

	}

	public int position(int Vertex) {

		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).equals(Vertex)) {

				return i;
			}
		}
		return -1;
	}

	public ArrayList<Integer> getKeys() {
		return keys;
	}

	public void setKeys(ArrayList<Integer> keys) {
		this.keys = keys;
	}

	public Hashtable<Integer, LinkedList> getTable() {
		return table;
	}

	public void setTable(Hashtable<Integer, LinkedList> table) {
		this.table = table;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void zapisdopliku() {
		try {
			FileWriter plik = new FileWriter("plik2.txt");
			for (int i = 0; i < keys.size(); i++) {
				plik.append(String.valueOf(keys.get(i)));
				plik.append(",");
			}
			plik.append("\n");
			int lista[][] = new int[keys.size()][keys.size()];
			for (int i = 0; i < this.x; i++) {
				for (int j = 0; j < this.x; j++) {
					lista[i][j] = 0;
				}

				for (int k = 0; k < x; k++) {
					Iterator iter = table.get(keys.get(k)).listIterator();
					while (iter.hasNext()) {
						Edge edge = (Edge) iter.next();
						int z = position(edge.getTarget());
						lista[k][z] = edge.getWeight();
					}
					System.out.println();
				}
				for (int z = 0; z < this.x; z++) {
					for (int j = 0; j < this.x; j++) {
						plik.append(String.valueOf(lista[z][j]));
						plik.append(",");
					}
					plik.append("\n");
				}
				plik.close();
			}
		} catch (Exception e) {

		}
	}

	public void odczytzpliku(String plik2) {
		int[][] listap;
		try {
			int z = 0;
			ArrayList<Integer> Vertex = new ArrayList();

			BufferedReader zpliku = new BufferedReader(new FileReader(plik2));
			String line = "";
			line = zpliku.readLine();
			String[] el = line.split(",");
			for (int i = 0; i < el.length; i++) {
				Vertex.add(Integer.parseInt(el[i]));
			}
			listap = new int[Vertex.size()][Vertex.size()];
			while (((line = zpliku.readLine()) != null)) {
				String[] element = line.split(",");
				for (int j = 0; j < element.length; j++) {
					listap[z][j] = Integer.parseInt(element[j]);
				}
				z++;
			}

			this.keys = Vertex;
			this.x = keys.size();
			this.table.clear();
			for (int i = 0; i < Vertex.size(); i++) {
				LinkedList<Edge> newlist = new LinkedList<>();
				for (int j = 0; j < Vertex.size(); j++) {
					if (listap[i][j] != 0) {
						newlist.add(new Edge(Vertex.get(j), listap[i][j]));
					}
				}
				table.put(Vertex.get(i), newlist);
			}
		} catch (Exception e) {
			System.out.println(e.getClass());
		}
	}

	public void dijkstra2(int source, int target) {

		int lista[][] = new int[keys.size()][keys.size()];
		for (int z = 0; z < this.x; z++) {
			for (int j = 0; j < this.x; j++) {
				lista[z][j] = 0;
			}
		}
		for (int k = 0; k < x; k++) {
			Iterator iter = table.get(keys.get(k)).listIterator();
			while (iter.hasNext()) {
				Edge edge = (Edge) iter.next();
				int z = position(edge.getTarget());
				lista[k][z] = edge.getWeight();
			}
		}
		int j, u;
		int d[] = new int[this.x]; // Tablica kosztów dojœcia
		int p[] = new int[this.x]; // Tablica poprzedników
		boolean QS[] = new boolean[this.x];
		int v = this.position(source);
		for (int i = 0; i < this.x; i++) {
			d[i] = Integer.MAX_VALUE;
			p[i] = -1;
			QS[i] = false;

		}
		d[v] = 0;
		for (int i = 0; i < this.x; i++) {

			for (j = 0; QS[j]; j++)
				;
			for (u = j++; j < this.x; j++)
				if (!QS[j] && (d[j] < d[u]))
					u = j;

			QS[u] = true;

			for (int w = 0; w < this.x; w++) {

				if (QS[w] == false && lista[u][w] != 0 && (d[w] > d[u] + lista[u][w])) {

					d[w] = d[u] + lista[u][w];
					p[w] = u;
				}
			}

		}
		Integer[] p2 = new Integer[p.length];
		for (int i = 0; i < p.length; i++) {
			p2[i] = p[i];
		}
		displayDijkstra(p2, target);
		System.out.println();
		for (int i = 0; i < this.x; i++) {
			System.out.print(d[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < this.x; i++) {
			System.out.print(p[i] + " "); // wypisuje index w tablicy, nie sam¹ wartoœæ wierzcho³ka, u¿yæ
											// Vertex.get(p[i]);
		}
		System.out.println();
	}

	public void prim(Integer Vertex) {
		ArrayList<LinkedList<Connection>> edges = new ArrayList<>(this.x);
		LinkedList<Connection> queue = new LinkedList<>();
		ArrayList<Integer> visited = new ArrayList<>();
		ArrayList<Connection> tree = new ArrayList<>();
		for (int i = 0; i < keys.size(); i++) {
			visited.add(i, keys.get(i));
		}
		for (int k = 0; k < x; k++) {
			LinkedList<Connection> connects = new LinkedList<>();
			Iterator iter = table.get(keys.get(k)).listIterator();
			while (iter.hasNext()) {
				Edge edge = (Edge) iter.next();
				Connection connect = new Connection(keys.get(k), edge.getTarget(), edge.getWeight());
				connects.add(connect);
			}
			edges.add(connects);
		}
		int position = this.position(Vertex);
		queue.addAll(edges.get(position));

		visited.remove(Vertex);
		System.out.println(edges);
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).size() == 0) {
				visited.remove(keys.get(i));
				System.out.println(visited);
			}
		}

		while (!visited.isEmpty()) {

			Collections.sort(queue);
			System.out.println("przed: " + queue);
			System.out.println(tree);
			Connection connect = queue.removeFirst();
			if (connect.getTarget() == connect.getSource()) {
				connect = queue.removeFirst();
			}
			int vertex_next = position(connect.getTarget());
			System.out.println(connect.getTarget());

			tree.add(connect);
			visited.remove(connect.getTarget());
			queue.addAll(edges.get(vertex_next));
			maintain_queue(queue, visited);
			maintain_queue(queue, visited);
			System.out.println("po: " + queue);
			System.out.println(tree);

		}
		Iterator iter = tree.listIterator();
		while (iter.hasNext()) {
			Connection edge = (Connection) iter.next();
			System.out.println(edge.toString());
			System.out.println();

		}
		this.displayprim(tree, Vertex);
	}

	public void maintain_queue(LinkedList<Connection> queue, ArrayList<Integer> visited) {

		for (int j = 0; j < queue.size(); j++) {
			Connection connect1 = queue.get(j);
			if (visited.contains(connect1.getTarget()) == false) {

				queue.remove(j);
			}

		}

	}

	public void displayprim(ArrayList<Connection> tree, Integer Vertex) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Okno3 ex = new Okno3(tree, Vertex);
				ex.setVisible(true);
			}
		});
	}

	@SuppressWarnings("serial")
	class Surface3 extends JPanel {

		static final int oval_radius = 28;
		ArrayList<Connection> tree;
		Integer Vertex;
		int z = 0;

		public Surface3(ArrayList<Connection> tree, Integer Vertex) {
			this.tree = tree;
			this.Vertex = Vertex;
			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (z == tree.size()) {

					} else {
						z++;
						repaint();
					}
				}
			});
		}

		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Point[] points = setPoints();

			for (int i = 0; i < keys.size(); i++) {
				Integer vertex = keys.get(i);
				LinkedList<Edge> adjacents = table.get(vertex);
				for (Edge edge : adjacents) {
					for (int j = 0; j < points.length; j++) {
						if (keys.get(j).equals(edge.target)) {
							g2d.setColor(Color.BLACK);
							g2d.setStroke(new BasicStroke(3));
							g2d.drawLine(points[i].x + oval_radius, points[i].y + oval_radius,
									points[j].x + oval_radius, points[j].y + oval_radius);
							g2d.setColor(Color.BLUE);
							g2d.setFont(new Font("TimeRoman", 1, 15));
							g2d.drawString(edge.weight.toString(),
									(points[i].x + points[j].x + 2 * oval_radius) / 2 - 10,
									(points[i].y + points[j].y + 2 * oval_radius) / 2 - 30);
							break;
						}
					}

				}
			}

			for (int j = 0; j < z; j++) {
				g2d.setColor(Color.BLUE);
				g2d.drawLine(points[position(tree.get(j).source)].x + oval_radius,
						points[position(tree.get(j).source)].y + oval_radius,
						points[position(tree.get(j).target)].x + oval_radius,
						points[position(tree.get(j).target)].y + oval_radius);

			}
			for (int i = 0; i < points.length; i++) {
				drawVertex(g2d, points[i].x, points[i].y, keys.get(i).toString());
			}
		}

		private Point[] setPoints() {
			Point[] points = new Point[keys.size()];
			if (points.length < 1) {
				return null;
			}
			if (points.length > 0) {
				int center_x = 500, center_y = 500, circle_radius = 300;
				int left = center_x - circle_radius, right = center_x + circle_radius, down = center_y + circle_radius,
						up = center_y - circle_radius;
				points[0] = new Point(left, center_y);
				for (int i = 1; i < (points.length + 1) / 2; i++) {
					int x = left + ((circle_radius * 2) / (1 + (points.length - 1) / 2)) * i;
					Point y1_y2 = calcY(x, center_x, center_y, circle_radius);
					points[i] = new Point(x, y1_y2.x + 43);
					points[points.length - i] = new Point(x, y1_y2.y);
				}
				if (points.length % 2 == 0) {
					points[points.length / 2] = new Point(right, center_y);
				}
			}
			return points;
		}

		private Point calcY(int x, int a, int b, int r) {
			double wsp_a = 1;
			double wsp_b = -2 * b;
			double wsp_c = (x - a) * (x - a) + b * b - r * r;
			double delta = wsp_b * wsp_b - 4 * wsp_a * wsp_c;
			double y1 = (-wsp_b - Math.sqrt(delta)) / (2 * wsp_a);
			double y2 = (-wsp_b + Math.sqrt(delta)) / (2 * wsp_a);
			return new Point((int) y1, (int) y2);
		}

		private void drawVertex(Graphics2D g2d, int oval_x, int oval_y, String string) {

			Ellipse2D.Float oval = new Ellipse2D.Float(oval_x, oval_y, oval_radius * 3, oval_radius * 2);
			g2d.setColor(Color.BLACK);
			g2d.fill(oval);
			g2d.setColor(Color.BLACK);
			g2d.draw(oval);
			g2d.setColor(Color.WHITE);
			g2d.drawString(string, oval_x + oval_radius, oval_y + oval_radius);
		}

		Point[] points = setPoints();

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			doDrawing(g);
		}
	}

	@SuppressWarnings("serial")
	public class Okno3 extends JFrame {

		public Okno3(ArrayList<Connection> tree, Integer Vertex) {
			add(new Surface3(tree, Vertex));
			setBackground(Color.WHITE);
			setTitle("Minimal Tree");
			setSize(1920, 1080);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

	}

	public class Connection implements Comparable<Connection> {
		public Integer source;
		public Integer target;
		public Integer weight;

		@Override
		public String toString() {
			return "Connection [source=" + source + ", target=" + target + ", weight=" + weight + "]";
		}

		public Integer getSource() {
			return source;
		}

		public void setSource(Integer source) {
			this.source = source;
		}

		public Integer getTarget() {
			return target;
		}

		public void setTarget(Integer target) {
			this.target = target;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public Connection(Integer x, Integer y, Integer z) {
			this.source = x;
			this.target = y;
			this.weight = z;
		}

		public int compareTo(Connection con) {
			if (this.weight == con.weight) {
				return 0;
			} else {
				if (this.weight > con.weight) {
					return 1;
				} else
					return -1;
			}

		}

	}

	public void display() {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Okno ex = new Okno();
				ex.setVisible(true);
			}
		});
	}

	@SuppressWarnings("serial")
	class Surface extends JPanel {
		static final int oval_radius = 18;

		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Point[] points = setPoints();

			for (int i = 0; i < keys.size(); i++) {
				Integer vertex = keys.get(i);
				LinkedList<Edge> adjacents = table.get(vertex);
				for (Edge edge : adjacents) {
					for (int j = 0; j < points.length; j++) {
						if (keys.get(j).equals(edge.target)) {
							g2d.setColor(Color.BLACK);
							g2d.drawLine(points[i].x + oval_radius, points[i].y + oval_radius,
									points[j].x + oval_radius, points[j].y + oval_radius);
							g2d.setColor(Color.BLUE);
							g2d.drawString(edge.weight.toString(),
									(points[i].x + points[j].x + 2 * oval_radius) / 2 - 10,
									(points[i].y + points[j].y + 2 * oval_radius) / 2 - 20);
							break;
						}
					}

				}
			}

			for (int i = 0; i < points.length; i++) {
				drawVertex(g2d, points[i].x, points[i].y, keys.get(i).toString());
			}

		}

		private Point[] setPoints() {
			Point[] points = new Point[keys.size()];
			if (points.length < 1) {
				return null;
			}
			if (points.length > 0) {
				int center_x = 500, center_y = 500, circle_radius = 300;
				int left = center_x - circle_radius, right = center_x + circle_radius, down = center_y + circle_radius,
						up = center_y - circle_radius;
				points[0] = new Point(left, center_y);
				for (int i = 1; i < (points.length + 1) / 2; i++) {
					int x = left + ((circle_radius * 2) / (1 + (points.length - 1) / 2)) * i;
					Point y1_y2 = calcY(x, center_x, center_y, circle_radius);
					points[i] = new Point(x, y1_y2.x);
					points[points.length - i] = new Point(x, y1_y2.y);
				}
				if (points.length % 2 == 0) {
					points[points.length / 2] = new Point(right, center_y);
				}
			}
			return points;
		}

		private Point calcY(int x, int a, int b, int r) {
			double wsp_a = 1;
			double wsp_b = -2 * b;
			double wsp_c = (x - a) * (x - a) + b * b - r * r;
			double delta = wsp_b * wsp_b - 4 * wsp_a * wsp_c;
			double y1 = (-wsp_b - Math.sqrt(delta)) / (2 * wsp_a);
			double y2 = (-wsp_b + Math.sqrt(delta)) / (2 * wsp_a);
			return new Point((int) y1, (int) y2);
		}

		private void drawVertex(Graphics2D g2d, int oval_x, int oval_y, String string) {

			Ellipse2D.Float oval = new Ellipse2D.Float(oval_x, oval_y, oval_radius * 3, oval_radius * 2);
			g2d.setColor(Color.BLACK);
			g2d.fill(oval);
			g2d.setColor(Color.BLACK);
			g2d.draw(oval);
			g2d.setColor(Color.WHITE);
			g2d.drawString(string, oval_x + oval_radius, oval_y + oval_radius);
		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			doDrawing(g);
		}
	}

	@SuppressWarnings("serial")
	public class Okno extends JFrame {

		public Okno() {

			initUI();
		}

		private void initUI() {

			add(new Surface());
			setBackground(Color.WHITE);
			setTitle("Graf Listowy");
			setSize(1920, 1080);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

	}

	private void displayDijkstra(Integer[] descendants, Integer goal) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Okno2 ex = new Okno2(descendants, goal);
				ex.setVisible(true);
			}
		});
	}

	@SuppressWarnings("serial")
	class Surface2 extends JPanel {
		static final int oval_radius = 18;
		Integer[] descendants;
		Integer goal;

		public Surface2(Integer[] descendants, Integer goal) {
			this.descendants = descendants;
			this.goal = goal;
		}

		private void doDrawing(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			Point[] points = setPoints();

			for (int i = 0; i < keys.size(); i++) {
				Integer vertex = keys.get(i);
				LinkedList<Edge> adjacents = table.get(vertex);
				for (Edge edge : adjacents) {
					for (int j = 0; j < points.length; j++) {
						if (keys.get(j).equals(edge.target)) {
							g2d.setColor(Color.BLACK);
							g2d.drawLine(points[i].x + oval_radius, points[i].y + oval_radius,
									points[j].x + oval_radius, points[j].y + oval_radius);
							g2d.setColor(Color.BLUE);
							g2d.drawString(edge.weight.toString(),
									(points[i].x + points[j].x + 2 * oval_radius) / 2 - 10,
									(points[i].y + points[j].y + 2 * oval_radius) / 2 - 20);
							break;
						}
					}

				}
			}
			Integer next_index = keys.indexOf(goal);
			g2d.setColor(Color.RED);
			while (true) {
				if (descendants[next_index] == -1) {
					break;
				}
				int previous_index = descendants[next_index];
				g2d.drawLine(points[previous_index].x + oval_radius, points[previous_index].y + oval_radius,
						points[next_index].x + oval_radius, points[next_index].y + oval_radius);
				next_index = descendants[next_index];
			}

			for (int i = 0; i < points.length; i++) {
				drawVertex(g2d, points[i].x, points[i].y, keys.get(i).toString());

			}

		}

		private Point[] setPoints() {
			Point[] points = new Point[keys.size()];
			if (points.length < 1) {
				return null;
			}
			if (points.length > 0) {
				int center_x = 500, center_y = 500, circle_radius = 300;
				int left = center_x - circle_radius, right = center_x + circle_radius, down = center_y + circle_radius,
						up = center_y - circle_radius;
				points[0] = new Point(left, center_y);
				for (int i = 1; i < (points.length + 1) / 2; i++) {
					int x = left + ((circle_radius * 2) / (1 + (points.length - 1) / 2)) * i;
					Point y1_y2 = calcY(x, center_x, center_y, circle_radius);
					points[i] = new Point(x, y1_y2.x + 43);
					points[points.length - i] = new Point(x, y1_y2.y);
				}
				if (points.length % 2 == 0) {
					points[points.length / 2] = new Point(right, center_y);
				}
			}
			return points;
		}

		private Point calcY(int x, int a, int b, int r) {
			double wsp_a = 1;
			double wsp_b = -2 * b;
			double wsp_c = (x - a) * (x - a) + b * b - r * r;
			double delta = wsp_b * wsp_b - 4 * wsp_a * wsp_c;
			double y1 = (-wsp_b - Math.sqrt(delta)) / (2 * wsp_a);
			double y2 = (-wsp_b + Math.sqrt(delta)) / (2 * wsp_a);
			return new Point((int) y1, (int) y2);
		}

		private void drawVertex(Graphics2D g2d, int oval_x, int oval_y, String string) {

			Ellipse2D.Float oval = new Ellipse2D.Float(oval_x, oval_y, oval_radius * 3, oval_radius * 2);
			g2d.setColor(Color.BLACK);
			g2d.fill(oval);
			g2d.setColor(Color.BLACK);
			g2d.draw(oval);
			g2d.setColor(Color.WHITE);
			g2d.drawString(string, oval_x + oval_radius, oval_y + oval_radius);
		}

		@Override
		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			doDrawing(g);
		}
	}

	@SuppressWarnings("serial")
	public class Okno2 extends JFrame {

		public Okno2(Integer[] descendants, Integer goal) {
			add(new Surface2(descendants, goal));
			setBackground(Color.WHITE);
			setTitle("Droga Dijkstry");
			setSize(1920, 1080);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}

	}

}