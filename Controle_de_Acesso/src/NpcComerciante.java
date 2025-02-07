import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

public class NpcComerciante {
    
    PriorityBlockingQueue<Jogador> queue = new PriorityBlockingQueue<>(100, Comparator.comparingLong(Jogador::GetTimeToTrade));
    Semaphore simultaneousPlayer;
    
    public NpcComerciante(int maxNumberOfPlayerToTrade) {
        simultaneousPlayer = new Semaphore(maxNumberOfPlayerToTrade);
    }
    
    public void AtenderJogador(Jogador j) {
        System.out.println(j.Name + " entrou na fila.");
        
        queue.offer(j);
        
        try {
            simultaneousPlayer.acquire();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        Jogador jFromQueue = queue.poll();
        
        System.out.println("Está sendo atendido " + jFromQueue.Name);
        
        jFromQueue.IniciarAtendimento();
        
        System.out.println(jFromQueue.Name + " começou o atendimento.");
    }
    
    public void SairDoAtendimento(Jogador j) {
    	
        System.out.println("Terminou a troca " + j.Name);
        j.SairDoAtendimento();
        
        
        System.out.println(j.Name + " saiu do atendimento.");
        
        simultaneousPlayer.release();
    }
}