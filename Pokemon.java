abstract public class Pokemon {
	private String nomePokemon;
	private int hp;
	private boolean acordado = true;
	private AtaquePokemon[] atqs = new AtaquePokemon[4];
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
			acordado = false;
		}
	}
	public boolean getState(){
		return acordado;
	}
	public String getNome(){
		return nomePokemon;
	}
	public void recebeCura(){
		hp += 50; //usei um valor qualquer para a cura
	}
	public class AtaquePokemon extends Event {
		private int dano;
		private String nome;
		public AtaquePokemon(String nome, int dano,long eventTime) {
			super(eventTime);
			this.dano=dano;
			this.nome=nome;
		}
		public void action(Pokemon alvo) {
			alvo.recebeDano(dano);
		}
		public String description() {
			return(nomePokemon + " usou " + nome + " e causou " + dano + " de dano.");
		}
	}
	
	
}
