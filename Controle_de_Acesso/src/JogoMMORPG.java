import java.util.Random;

public class JogoMMORPG {
	Random r = new Random();
	
	public JogoMMORPG(int numberOfPlayers) {
		NpcComerciante npc = new NpcComerciante(3);
		Jogador[] jogador = new Jogador[numberOfPlayers];
				
		for(int i = 0; i< numberOfPlayers; i++) {
			jogador[i] = new Jogador(npc,i, r.nextLong(100,500),r.nextLong(500,1000));
			jogador[i].start();
		}
	}
}