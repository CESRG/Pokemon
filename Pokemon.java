
abstract public class Pokemon {
	private String nome;
	private int hp;
	private AtaquePokemon[] atqs= new AtaquePokemon[4];
	public AtaquePokemon EscolheAtaque(int i) {
		return atqs[i];
	}
	public int getHP() {
		return hp;
	}
	public void recebeDano(int dmg) {
		hp -= dmg;
		if (hp <= 0) {
			hp = 0;
			System.out.println(nome + " morreu.");
		}
	}
	public class AtaquePokemon extends Event {
		private int dano;
		private String nome;
		public AtaquePokemon(String nome, int dano,long eventTime) {
			super(eventTime);
			this.dano=dano;
			this.nome=nome;
		}
		public void action() {
			
		}
		public String description() {
			return(Pokemon.nome + " usou " + nome + " e causou " + dano + " de dano.");
		}
	}
	
	
}
