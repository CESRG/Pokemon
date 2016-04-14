
public class FightControls extends Controller{
	private class Treinador{
		private Pokemon[] pokemons= new Pokemons[6];
		private Pokemon atual;
		public Treinador(Pokemon[] args){
			this.pokemons=args;
			this.atual=pokemons[0];
		}	
		private class Fugir extends Event{
			public static final int prioridade=1;
			public Fugir(long eventTime) {
				super(eventTime);
			}
			
		}
		private class TrocarPokemon extends Event{
			public static final int prioridade=2;
			public TrocarPokemon(long eventTime) {
				super(eventTime);
			}
		}
		private class UsarItem extends Event{
			public static final int prioridade=3;
			public UsarItem(long eventTime) {
				super(eventTime);
			}
		}
		private class Ataque extends Event{
			private int i=(int)(3*java.lang.Math.random());
			public Ataque(long eventTime) {
				super(eventTime);
			}
			public void action(){
				atual.EscolheAtaque(i).action();
			}
			public String description(){
				return(atual.EscolheAtaque(i).description());
			}
		}
	}
	private class Restart extends Event {
		public Restart(long eventTime) {
			super(eventTime);
		}
		public String description() {
			return("Restarting system")	;
		}
	}
	public static void main(String[] args){
		FightControls fc= new FightControls();
		long tm = System.currentTimeMillis();
		fc.addEvent(fc.new Restart(tm));
		fc.run();
	}

}
