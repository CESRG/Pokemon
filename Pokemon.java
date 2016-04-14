
abstract public class Pokemon {
	private String nome;
	private int hp;
	private Ataque[] atqs= new Ataque[4];	
	public class AtaquePokemon extends Event{
		private int dano;
		private String nome;
		public AtaquePokemon(long eventTime) {
			super(eventTime);
		}
		public void action(){
			
		}
		public String description(){
			return("usou "+ nome+ " e causou "+dano+" de dano.");
		}
	}
	
	
}
