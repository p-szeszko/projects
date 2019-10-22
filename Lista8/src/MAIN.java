public class MAIN {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdjacentL macierz = new AdjacentL(4);

		macierz.odczytzpliku("plik2.txt");
		macierz.prim(0);
	}
}
