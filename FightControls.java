
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
		public int achaPokemonAcordado() {
			for (int i = 0; i < 6 ; i++) {
				if (pokemons[i].acordado()) {
					return i;
				}
			}
			return(-1);
		}
		public Pokemon getPokemon(int i) {
			return pokemons[i];
		}
		public Fugir fuga(long eventTime){
			return( new Fugir(eventTime));
		}
		private class Fugir extends Event {
			public Fugir(long eventTime) {
				super(eventTime);
				priority=1+(ordemAtaque*10);
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
			private int i;
			public TrocarPokemon(long eventTime, int escolhido) {
				super(eventTime);
				priority=2+(ordemAtaque*10);
				i = escolhido;
			}
			public void action() {
				if(pokemons[i].acordado()) {
					pokemons[indiceAtual] = atual;
					atual = pokemons[i];
					indiceAtual = i;
				}
				else{return;}
			}
			public String description() {
				if(pokemons[i].acordado()) {
					return (nome + " trocou de pokemon, " + atual.getNome()
					+ " está lutando agora.");
				}
				else{
					return (nome + " tentou trocar o pokemon ativo, "
							+ "mas isso não foi possível pois o "
							+ "pokemon escolhido está nocauteado.");
				}
			}
		}
		public UsarItem usaItem(long eventTime, int escolhido) {
			return (new UsarItem(eventTime, escolhido));
		}
		private class UsarItem extends Event {
			private int i;
			public UsarItem(long eventTime, int escolhido) {
				super(eventTime);
				priority=3+(ordemAtaque*10);
				i = escolhido;
			}
			public void action() {
				pokemons[i].recebeCura();
				if (i == 0) {
					atual.recebeCura();
				}
			}
			public String description() {
				return (pokemons[i].getNome() + 
						" recebeu uma cura e está com " 
						+ pokemons[i].getHP() + " de vida.");
			}
		}
		public Ataque fazAtaque(long eventTime, int n, Treinador alvo) {
			return (new Ataque(eventTime, n, alvo));
		}
		private class Ataque extends Event {
			private int n;
			private Treinador alvo;
			private Pokemon pokemonAlvo;
			String ret;
			public Ataque(long eventTime, int n, Treinador alvo) {
				super(eventTime);
				priority=4+(ordemAtaque*10);
				this.n = n;
				this.alvo = alvo;
				pokemonAlvo = alvo.getAtual();
			}
			public void action() {
				atual.EscolheAtaque(n).action(pokemonAlvo);
				if (!pokemonAlvo.acordado()) {
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
				ret = (atual.EscolheAtaque(n).description());
				if (pokemonAlvo.acordado()) {
					return ret;
				}
				else {
					if (alvo.achaPokemonAcordado() >= 0) {
						return (ret + "\n" + pokemonAlvo.getNome() + 
								" foi nocauteado e " +
								alvo.getAtual().getNome() + " o substituiu.");
					}
					else {
						return (ret + "\n" + "Todos os pokemons de " + nome + 
							" foram nocauteados e a luta acabou.");
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
			addEvent(ash.fazAtaque(tm, 1, trash));
			addEvent(ash.usaItem(tm, 0));
			addEvent(trash.trocaPokemon(tm, 3));
			addEvent(trash.fazAtaque(tm, 4, ash));
			addEvent(trash.usaItem(tm, 2));
			addEvent(ash.fazAtaque(tm, 3, trash));
			organizaEventSet();
		}
		public String description() {
			return("Restarting system");
		}
	}
	public void organizaEventSet(){
		Event e;
		Event aux;
		boolean trocou = true;
		int jogadorAtivo;
		aux = es.getNext();
		while(trocou){	
			trocou = false;
			while ((e = es.getNext()) != null){
				jogadorAtivo = (aux.getPrio())/10;
				if((jogadorAtivo == ((e.getPrio())/10))){
					if(aux.getPrio() > e.getPrio())
						es.exchange()
						trocou = true;
				}
				aux = e;
			}
			if(trocou){
				es.reset();
			}
		}
		es.reset();
	}
	public static void main(String[] args) {
		FightControls fc = new FightControls();
		long tm = System.currentTimeMillis();
		fc.addEvent(fc.new Restart(tm));
		fc.run();
	}

}
