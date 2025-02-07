public class Jogador extends Thread{
	
	private boolean traded = false, aguardadoAtendimento = false, atendimentoInicializado =false;
	long timestamp;
	long timeNeededToTrade;
	long timeWhenStartWaiting;
	NpcComerciante npcComerciante;
	
	 public int Name;
	
	public Jogador(NpcComerciante npc, int name, long _MS_timeToLookForTrade, long _MS_timeNeededToTrade) {
		
		
		npcComerciante = npc;
		timestamp =  _MS_timeToLookForTrade*1000000;
		timeNeededToTrade = _MS_timeNeededToTrade*1000000;
		
		Name = name;
	}
	
	public long GetTimeToTrade() {
		return timeWhenStartWaiting;
	}
	
	public void run() {
		while(!traded) {
			if(atendimentoInicializado){
				ReceberAtendimento();
			}else if(!aguardadoAtendimento) {
				Idle();
			}
		}
		
	}
	public void Idle() {
		
		if(Wait())
			return;
		
		BuscarAtendimento();
	}
	
	
	public void BuscarAtendimento() {
		System.out.println("Aguardado Atendimento " + Name);
		aguardadoAtendimento = true;
		timeWhenStartWaiting = System.nanoTime();
		npcComerciante.AtenderJogador(this);
	}
	
	public void IniciarAtendimento() {	
		atendimentoInicializado = true;
		timestamp = timeNeededToTrade;	
	}
	
	public void ReceberAtendimento() {
		
		if(Wait())
			return;
		
		npcComerciante.SairDoAtendimento(this);
		
	}
	
	public void SairDoAtendimento() {
		traded = true;
	}
	
	public boolean Wait() {
		timestamp--;
		
		return timestamp >= 0;
	}
}