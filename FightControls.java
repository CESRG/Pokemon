
public class FightControls extends Controller {
	private class Treinador {
		private String nome;
		private int ordemAtaque;
		private Pokemon[] pokemons = new Pokemon[6];
		private Pokemon atual;
		private int indiceAtual;
		public Treinador(Pokemon[] args, String nome, int ordemAtaque) {
			pokemons = args;
			atual = pokemons[0];
			indiceAtual = 0;
			this.nome = nome;
			this.ordemAtaque = ordemAtaque;
		}
		public Pokemon getAtual() {
			return atual;
		}
		public int getIndiceAtual() {
			return indiceAtual;
		}
		public Pokemon getPokemon(int i) {
			return pokemons[i];
		}
		private class Fugir extends Event {
			public static final int prioridade = 1;
			public Fugir(long eventTime) {
				super(eventTime);
			}
			public void action() {
				terminate();	
			}
			public String description() {
				return (nome + " fugiu da luta.");
			}
			
		}
		public TrocarPokemon trocaPokemon(long tm, int escolhido) {
			return (new TrocarPokemon(tm, escolhido));
		}
		private class TrocarPokemon extends Event {
			public static final int prioridade = 2;
			private int i;
			public TrocarPokemon(long eventTime, int escolhido) {
				super(eventTime);
				i = escolhido;
			}
			public void action() {
				if(pokemons[i].getState()) {
					pokemons[indiceAtual] = atual;
					atual = pokemons[i];
					indiceAtual = i;
				}
			}
			public String description() {
				if(pokemons[i].getState()) {
					return (nome + " trocou de pokemon, " + atual.getNome() + " está lutando agora.");
				}
				else{
					return (nome + " tentou trocar o pokemon ativo, mas isso não foi possível pois o pokemon escolhido está nocauteado");
				}
			}
		}
		public UsarItem usaItem(long eventTime, int escolhido) {
			return (new UsarItem(long eventTime, int escolhido));
		}
		private class UsarItem extends Event {
			public static final int prioridade = 3;
			private int i;
			public UsarItem(long eventTime, int escolhido) {
				super(eventTime);
				i = escolhido;
			}
			public void action() {
				pokemons[i].recebeCura();
				if (i == 0) {
					atual.recebeCura();
				}
			}
			public String description() {
				return (pokemons[i].getNome() + " recebeu uma cura e está com " + pokemons[i].getHP() + " de vida.")
			}
		}
		public Ataque fazAtaque(long eventTime, int n, Treinador alvo) {
			return (new Ataque(long eventTime, int n, Treinador alvo));
		}
		private class Ataque extends Event {
			private int n;
			private Treinador alvo;
			public Ataque(long eventTime, int n, Treinador alvo) {
				super(eventTime);
				this.n = n;
				this.alvo = alvo;
			}
			public void action() {
				atual.EscolheAtaque(n).action(alvo.getAtual());
				if (!alvo.getAtual().getState()) {
					(alvo.trocaPokemon(tm, (alvo.getIndiceAtual()+1))).action();
				}
			}
			public String description() {
				return (atual.EscolheAtaque(n).description());
				if (!alvo.getState()) {
					return (atual.EscolheAtaque(n).description() + "ln" + alvo.getPokemon(alvo.getIndiceAtual() - 1).getNome()+" foi nocauteado e " + alvo.getAtual().getNome() + " o substituiu.");
				}
			}
		}
		
	}
	public void terminate(){
		while ((e = es.getNext()) != null) {
			es.removeCurrent();
		}
	}
	private class Restart extends Event {
		public Restart(long eventTime) {
			super(eventTime);
		}
		public void action() {
			long tm = System.currentTimeMillis();
			Treinador ash = new Treinador({new Pikachu(), new Charizard(), new Squirtle(), new Magikarp()}, "Ash",1);
			Treinador trash = new Treinador({new Wombat(), new Diglet(), new Bulbasaur(), new Snorlax()}, "Trash",2);
			addEvent(ash.fazAtaque(tm, 1, trash);
			addEvent();
			organizaEventSet(tm)
		}
		public String description() {
			return("Restarting system");
		}
	}
	public static void main(String[] args) {
		FightControls fc = new FightControls();
		long tm = System.currentTimeMillis();
		fc.addEvent(fc.new Restart(tm));
		fc.run();
	}

}
