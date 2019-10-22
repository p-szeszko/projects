import java.util.Random;
import java.util.concurrent.Semaphore;

public class lista8 {
	private static final int liczbaFilozofow = 5;
	private static final Random random = new Random();
	private static final Semaphore odzwierny = new Semaphore(4, true);
	private static final Paleczka[] paleczki = new Paleczka[5];

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++)
			paleczki[i] = new Paleczka();
		Filozof[] filozofowie = new Filozof[liczbaFilozofow];
		for (int i = 0; i < 5; i++) {
			filozofowie[i] = new Filozof(i);
			filozofowie[i].start();
		}
	}

	static class Filozof extends Thread {
		private final Paleczka lewa, prawa;

		Filozof(int numer) {
			super("Filozof" + numer);
			prawa = paleczki[numer];
			lewa = paleczki[(numer + 1) % liczbaFilozofow];
		}

		void medytuj() throws InterruptedException {
			System.out.println(getName() + " medytuje");
			sleep(random.nextInt(10000));
			System.out.println(getName() + " konczy medytacje");
			jedz();
		}

		void jedz() throws InterruptedException {
			odzwierny.acquire();
			lewa.wez();
			prawa.wez();
			System.out.println(getName() + " je posilek");
			sleep(random.nextInt(10000));
			lewa.odloz();
			prawa.odloz();
			odzwierny.release();
			System.out.println(getName() + " konczy posilek");
			medytuj();
		}

		@Override
		public void run() {
			try {
				medytuj();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Paleczka {
	private final Semaphore dostepna = new Semaphore(1);

	void wez() throws InterruptedException {
		dostepna.acquire();
	}

	void odloz() {
		dostepna.release();
	}
}