import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdjacentM {
	private ArrayList<ArrayList> lista = new ArrayList();
	private int x;
	private ArrayList<Integer> Vertex = new ArrayList();

	public ArrayList<ArrayList> getLista() {
		return lista;
	}

	public void setLista(ArrayList<ArrayList> lista) {
		this.lista = lista;
	}

	public AdjacentM(int x) {
		this.x = x;
		System.out.println("podaj wierzchoki");
		Scanner scan = new Scanner(System.in);
		for (int i = 0; i < x; i++) {
			Vertex.add(scan.nextInt());
		}
		scan.close();
		for (int i = 0; i < x; i++) {
			ArrayList<Integer> newlist = new ArrayList<>();
			for (int j = 0; j < x; j++) {
				newlist.add(0);
			}
			this.lista.add(newlist);
		}
	}

	public void wypiszKraw() {
		System.out.print(" ");
		for (int z = 0; z < Vertex.size(); z++) {
			System.out.format("%3d", Vertex.get(z));
		}
		System.out.println();
		for (int i = 0; i < lista.size(); i++) {
			System.out.print(Vertex.get(i));
			for (int j = 0; j < lista.size(); j++) {
				System.out.printf("%3d", lista.get(i).get(j));
			}
			System.out.println();
		}
	}

	public void addVertex(Integer Vertex) {
		int pos = this.findPos(Vertex);
		if (pos != -1 || Vertex == null) {
			throw new IllegalArgumentException();
		}
		this.Vertex.add(Vertex);
		ArrayList<Integer> newlist = new ArrayList<>();
		for (int j = 0; j < x + 1; j++) {
			newlist.add(0);
		}
		this.lista.add(newlist);
		for (int j = 0; j < x; j++) {
			this.lista.get(j).add(0);
		}
		this.x = x + 1;

	}

	public int findPos(Integer Vertex) {
		for (int i = 0; i < this.Vertex.size(); i++) {
			if (this.Vertex.get(i) == Vertex) {

				return i;
			}
		}
		return -1;
	}

	public void deleteVertex(Integer Vertex) {
		int pos = this.findPos(Vertex);
		if (pos == -1) {
			throw new NullPointerException();
		} else {
			for (int i = 0; i < this.Vertex.size(); i++) {
				if (this.Vertex.get(i) == Vertex) {
					this.Vertex.remove(i);

				}
			}
			for (int i = 0; i < x; i++) {
				lista.get(i).remove(pos);
			}
			lista.remove(pos);
		}
	}

	public boolean ifExist(Integer Vertex) {
		for (int i = 0; i < this.Vertex.size(); i++) {
			if (this.Vertex.get(i) == Vertex) {

				return true;
			}
		}
		return false;
	}

	public void dodajkraw(int x, int y) {
		dodajkraw(x, y, 1);
	}

	public void dodajkraw(int x, int y, int z) {
		boolean Vertex1 = ifExist(x);
		boolean Vertex2 = ifExist(y);
		if (Vertex1 == false || Vertex2 == false) {
			throw new IllegalArgumentException("brak wierzcholkow");
		}
		int posx = this.findPos(x);
		int posy = findPos(y);
		this.lista.get(posx).set(posy, z);
		this.lista.get(posy).set(posx, z);
	}

	public void usunKraw(int x, int y) {
		boolean Vertex1 = ifExist(x);
		boolean Vertex2 = ifExist(y);
		if (Vertex1 == false || Vertex2 == false) {
			throw new IllegalArgumentException();
		}
		int posx = this.findPos(x);
		int posy = findPos(y);
		this.lista.get(posx).set(posy, 0);
		this.lista.get(posy).set(posx, 0);
	}

	public int mindist(int distance[], boolean sptSet[]) {

		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < this.x; v++)
			if (sptSet[v] == false && distance[v] <= min) {
				min = distance[v];
				min_index = v;
			}

		return min_index;

	}

	void printSolution(int dist[], int n) {
		System.out.println("Vertex   Distance from Source");
		for (int i = 0; i < this.x; i++)
			if (dist[i] == (int) (Integer.MAX_VALUE)) {
				System.out.println(Vertex.get(i) + "          " + "Not reachable");
			} else
				System.out.println(Vertex.get(i) + "          " + dist[i]);
	}

	public void dijkstra(int source) {
		int distance[] = new int[this.x];
		boolean sptSet[] = new boolean[this.x];
		int v = this.findPos(source);
		for (int i = 0; i < this.x; i++) {
			distance[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}
		distance[v] = 0;
		for (int count = 0; count < this.x - 1; count++) {
			int u = mindist(distance, sptSet);

			sptSet[u] = true;
			for (int z = 0; z < this.x; z++)

				if (!sptSet[z] && !this.lista.get(u).get(z).equals(0) && distance[u] != Integer.MAX_VALUE
						&& distance[u] + (int) this.lista.get(u).get(z) < distance[z])
					distance[z] = distance[u] + (int) this.lista.get(u).get(z);
		}
		printSolution(distance, this.x);
	}

	public void prim(int source) {
		int distance[] = new int[this.x];
		boolean sptSet[] = new boolean[this.x];
		int v = this.findPos(source);
		for (int i = 0; i < this.x; i++) {
			distance[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}
		distance[v] = 0;
		for (int count = 0; count < this.x - 1; count++) {
			int u = mindist(distance, sptSet);

			sptSet[u] = true;
			for (int z = 0; z < this.x; z++)

				if (!sptSet[z] && !this.lista.get(u).get(z).equals(0) && distance[u] != Integer.MAX_VALUE
						&& distance[u] + (int) this.lista.get(u).get(z) < distance[z])
					distance[z] = distance[u];
		}
		printSolution(distance, this.x);
	}

	public void zapisdopliku() {
		try {
			FileWriter plik = new FileWriter("plik.txt");
			for (int i = 0; i < Vertex.size(); i++) {
				plik.append(String.valueOf(Vertex.get(i)));
				plik.append(",");
			}
			plik.append("\n");
			for (int i = 0; i < this.x; i++) {
				for (int j = 0; j < this.x; j++) {
					plik.append(String.valueOf(lista.get(i).get(j)));
					plik.append(",");
				}
				plik.append("\n");
			}
			plik.close();
		} catch (Exception e) {
			System.out.println(e.getClass());
		}

	}

	public void odczytzpliku(String plik) {
		int[][] listap;
		try {
			int z = 0;
			ArrayList<Integer> Vertex = new ArrayList();

			BufferedReader zpliku = new BufferedReader(new FileReader(plik));
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
			this.Vertex = Vertex;
			this.lista.clear();
			for (int i = 0; i < Vertex.size(); i++) {
				ArrayList<Integer> newlist = new ArrayList<>();
				for (int j = 0; j < Vertex.size(); j++) {
					newlist.add(listap[i][j]);
				}
				lista.add(newlist);
			}
			this.x = Vertex.size();
		} catch (Exception e) {
			System.out.println(e.getClass());
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

			g2d.setColor(Color.BLUE);
			for (int i = 0; i < lista.size(); i++) {
				ArrayList<Integer> row = lista.get(i);
				for (int j = 0; j < row.size(); j++) {
					Integer weight = row.get(j);
					if (weight != 0) {
						g2d.setColor(Color.BLACK); // tworzenie krawedzi
						g2d.drawLine(points[i].x + oval_radius, points[i].y + oval_radius, points[j].x + oval_radius,
								points[j].y + oval_radius);
						g2d.setColor(Color.BLUE); // wypisywanie wag
						g2d.drawString(weight.toString(), (points[i].x + points[j].x + 2 * oval_radius) / 2 - 10,
								(points[i].y + points[j].y + 2 * oval_radius) / 2 - 20);
					}
				}
			}

			for (int i = 0; i < points.length; i++) {
				drawVertex(g2d, points[i].x, points[i].y, Vertex.get(i).toString());
			}

		}

		private Point[] setPoints() { // tworzenie tablicy X i Y pierwszy na œrodku, reszta symetrycznie gora i dol
			Point[] points = new Point[Vertex.size()];
			if (points.length < 1) {
				return null;
			}
			if (points.length > 0) {
				int center_x = 500, center_y = 500, circle_radius = 300;
				int left = center_x - circle_radius, right = center_x + circle_radius, down = center_y + circle_radius,
						up = center_y - circle_radius;
				points[0] = new Point(left, center_y);
				for (int i = 1; i < (points.length + 1) / 2; i++) {
					int x = left + ((circle_radius * 2 + 20) / (1 + (points.length - 1) / 2)) * i;
					Point y1_y2 = calcY(x, center_x, center_y, circle_radius);
					points[i] = new Point(x, y1_y2.x);
					points[points.length - i] = new Point(x, y1_y2.y);
				}
				if (points.length % 2 == 0) { // sprawdzanie czy trzeba dodac jeszcze jedn na srodku na koncu
					points[points.length / 2] = new Point(right, center_y);
				}
			}
			return points;
		}

		private Point calcY(int x, int a, int b, int r) { // obliczanie polozenia Y dla wierzcholka
			double wsp_a = 1;
			double wsp_b = -2 * b;
			double wsp_c = (x - a) * (x - a) + b * b - r * r;
			double delta = wsp_b * wsp_b - 4 * wsp_a * wsp_c;
			double y1 = (-wsp_b - Math.sqrt(delta)) / (2 * wsp_a);
			double y2 = (-wsp_b + Math.sqrt(delta)) / (2 * wsp_a);
			return new Point((int) y1, (int) y2);
		}

		private void drawVertex(Graphics2D g2d, int oval_x, int oval_y, String string) {
			// tworzenie elips, wypelnianie, pisanie
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

			initUI(); // utworzenie okna
		}

		private void initUI() {

			add(new Surface());
			setBackground(Color.WHITE);
			setTitle("Graf Macierzy");
			setSize(1920, 1080);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}

	}

	public void dijkstra2(int source) {
		int[][] tablica = new int[2][this.x];
		int j, u;
		int d[] = new int[this.x]; // Tablica kosztów dojœcia
		int p[] = new int[this.x]; // Tablica poprzedników
		boolean QS[] = new boolean[this.x];
		int v = this.findPos(source);
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

				if (QS[w] == false && !lista.get(u).get(w).equals(0) && (d[w] > d[u] + (int) lista.get(u).get(w))) {
					d[w] = d[u] + (int) this.lista.get(u).get(w);
					p[w] = u;
				}
			}

		}
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public ArrayList<Integer> getVertex() {
		return Vertex;
	}

	public void setVertex(ArrayList<Integer> vertex) {
		Vertex = vertex;
	}
}
