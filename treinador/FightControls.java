package treinador;
import controller.Controller;
import controller.Event;
import pokemonTypes.Pokemon;

public class FightControls extends Controller {
	
	
	private class Treinador {
	
		private String nomeTreinador;
		private int ordemAtaque;
		private Pokemon[] pokemons = new Pokemon[6];
		private Pokemon atual;
		private int indiceAtual;
		
		public Treinador(Pokemon[] args, String nome, 
						int ordemAtaque) {
			pokemons = args;
			atual = pokemons[0];
			indiceAtual = 0;
			nomeTreinador = nome;
			this.ordemAtaque = ordemAtaque;
		}
		
		public Pokemon getAtual() {
			return atual;
		}
		public int getIndiceAtual() {
			return indiceAtual;
		}
		public int achaPokemonAcordado() {
			for (int i = 0; i < 6 ; i++) {
				if (pokemons[i].getAcordado()) {
					return i;
				}
			}
			return -1;
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
				return (nomeTreinador + " fugiu da luta.");
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
				if (pokemons[i].getAcordado()) {
					pokemons[indiceAtual] = atual;
					atual = pokemons[i];
					indiceAtual = i;
				}
			}
			public String description() {
				if (pokemons[i].getAcordado()) {
					return (nomeTreinador + " trocou de pokemon.\n" 
							+ atual.getNome() + " vai!");
				}
				else {
					return ("Não é possível usar " +
							"um Pokémon desmaiado!");
				}
			}
		}
		
		
		public UsarItem usaItem(long eventTime, int escolhido) {
			return (new UsarItem(eventTime, escolhido));
		}
		private class UsarItem extends Event {
			
			public static final int prioridade = 3;
			private int i;
			Pokemon p;
			
			public UsarItem(long eventTime, int escolhido) {
				super(eventTime);
				i = escolhido;
				p = pokemons[i];
			}
			
			public void action() {
				if (p.getAcordado()) {
					p.recebeCura(10);
				}
			}
			public String description() {
				if (p.getAcordado()) {
					return (nomeTreinador + " curou " + p.getNome() +
							"!\n" + p.getNome() + " tem " + p.getHP()
							+ " de vida agora!");
				}
				else {
					return ("Não é possível curar um Pokémon desmaiado!");
				}
			}
		}
		
		
		public Ataque fazAtaque(long eventTime, int nAtaque, 
					 			Treinador alvo) {
			return (new Ataque(eventTime, nAtaque, alvo));
		}
		private class Ataque extends Event {
			
			private int n;
			private Treinador alvo;
			private Pokemon pokemonAlvo;
			private String ret;
			
			public Ataque(long eventTime, int nAtaque, 
						  Treinador alvo) {
				super(eventTime);
				n = nAtaque;
				this.alvo = alvo;
				pokemonAlvo = alvo.getAtual();
				ret = atual.EscolheAtaque(n).description();
			}
			
			public void action() {
				atual.EscolheAtaque(n).action(pokemonAlvo);
				if (!pokemonAlvo.getAcordado()) {
					int iAcordado = alvo.achaPokemonAcordado();
					if (iAcordado >= 0) {
						alvo.trocaPokemon(System.currentTimeMillis(),
										  iAcordado);
					}
					else {
						terminate();
					}
				}
			}
			public String description() {
					return ret;
				}
			}
		}
		
	public void terminate(){
		addEvent(null); //Adiciona um evento nulo ao event set,
						//o que causa o término de run().
	}
	
	
	private class Restart extends Event {
		
		public Restart(long eventTime) {
			super(eventTime);
		}
		
		public void action() {
			
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
