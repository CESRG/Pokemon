package ex1;

abstract class Pokemon {

	private String nomePokemon;
	private boolean acordado = true;
	private int hp;
	private int numTipo;
	private String tipo;
	private AtaquePokemon[] atqs = new AtaquePokemon[4];
	private int[][] fraquezas = {
			{ 2, 2, 2, 2, 2, 1, 2, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
			{ 4, 2, 1, 1, 2, 4, 1, 0, 4, 2, 2, 2, 2, 1, 4, 2, 4, 1 },
			{ 2, 4, 2, 2, 2, 1, 4, 2, 1, 2, 2, 4, 1, 2, 2, 2, 2, 2 },
			{ 2, 2, 2, 1, 1, 1, 2, 1, 0, 2, 2, 4, 2, 2, 2, 2, 2, 4 },
			{ 2, 2, 0, 4, 2, 4, 1, 2, 4, 4, 2, 1, 4, 2, 2, 2, 2, 2 },
			{ 2, 1, 4, 2, 1, 2, 4, 2, 1, 4, 2, 2, 2, 2, 4, 2, 2, 2 },
			{ 2, 1, 1, 1, 2, 2, 2, 1, 1, 1, 2, 4, 2, 4, 2, 2, 4, 1 },
			{ 0, 2, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 4, 2, 2, 1, 2 },
			{ 2, 2, 2, 2, 2, 4, 2, 2, 1, 1, 1, 2, 1, 2, 4, 2, 2, 4 },
			{ 2, 2, 2, 2, 2, 1, 4, 2, 4, 1, 1, 4, 2, 2, 4, 1, 2, 2 },
			{ 2, 2, 2, 2, 4, 4, 2, 2, 2, 4, 1, 1, 2, 2, 2, 1, 2, 2 },
			{ 2, 2, 1, 1, 4, 4, 1, 2, 1, 1, 4, 1, 2, 2, 2, 1, 2, 2 },
			{ 2, 2, 4, 2, 0, 2, 2, 2, 2, 2, 4, 1, 1, 2, 2, 1, 2, 2 },
			{ 2, 4, 2, 4, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 0, 2 },
			{ 2, 2, 4, 2, 4, 2, 2, 2, 1, 1, 1, 4, 2, 2, 1, 4, 2, 2 },
			{ 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 4, 2, 0 },
			{ 2, 1, 2, 2, 2, 2, 2, 4, 2, 2, 2, 2, 2, 4, 2, 2, 1, 1 },
			{ 2, 4, 2, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 4, 4, 2 } };
	protected static AtaquePokemon[] atqsPossiveis;

	public AtaquePokemon EscolheAtaque(int i) {
		return atqs[i];
	}

	public int getHP() {
		return hp;
	}

	public void recebeDano(int dmg, String tipoOponente) {
		int multiplicador = 1;
		int dano;

		dano = dmg * multiplicador;
		hp -= dano;
		System.out.println(nomePokemon + " perdeu " + dano + " de vida!");

		if (hp <= 0) {
			hp = 0;
			acordado = false;
			System.out.println(nomePokemon + " desmaiou!");
		}
	}

	public boolean getAcordado() {
		return acordado;
	}

	public String getNome() {
		return nomePokemon;
	}

	public void recebeCura(int heal) {
		hp += heal;
	}

	public class AtaquePokemon extends Event {

		private int dano;
		private String nomeAtaque;

		public AtaquePokemon(String nome, int dano, long eventTime) {
			super(eventTime);
			this.dano = dano;
			nomeAtaque = nome;
		}

		public void action() {
		}

		public void action(Pokemon alvo) {
			alvo.recebeDano(dano, tipo);
		}

		public String description() {
			return (nomePokemon + " usou " + nomeAtaque + "!");
		}
	}
}
// tipos: 1-normal;
//2-luta
//3-voador
//4-veneno
//5-terra
//6-pedra
//7-inseto
//8-fantasma
//9-metal
//10-fogo
//11-água
//12-planta
//13-elétrico
//14-psíquico
//15-gelo
//16-dragão
//17-obscuro
//18-fada

