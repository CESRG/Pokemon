package pokemonTypes;

import controller.Event;

abstract public class Pokemon {

	private String nomePokemon;
	private int hp;
	private String tipo;
	private int tipo;
	private boolean acordado = true;
	private AtaquePokemon[] atqs = new AtaquePokemon[4];
	private double[][] fraquezas = {
			{ 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 0.0, 0.5, 1.0, 1.0, 1.0, 1.0,
					1.0, 1.0, 1.0, 1.0, 1.0 },
			{ 2.0, 1.0, 0.5, 0.5, 1.0, 2.0, 0.5, 0.0, 2.0, 1.0, 1.0, 1.0, 1.0,
					0.5, 2.0, 1.0, 2.0, 0.5 },
			{ 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 1.0, 2.0, 0.5,
					1.0, 1.0, 1.0, 1.0, 1.0 },
			{ 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5, 0.0, 1.0, 1.0, 2.0, 1.0,
					1.0, 1.0, 1.0, 1.0, 2.0 },
			{ 1.0, 1.0, 0.0, 2.0, 1.0, 2.0, 0.5, 1.0, 2.0, 2.0, 1.0, 0.5, 2.0,
					1.0, 1.0, 1.0, 1.0, 1.0 },
			{ 1.0, 0.5, 2.0, 1.0, 0.5, 1.0, 2.0, 1.0, 0.5, 2.0, 1.0, 1.0, 1.0,
					1.0, 2.0, 1.0, 1.0, 1.0 },
			{ 1.0, 0.5, 0.5, 0.5, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 2.0, 1.0,
					2.0, 1.0, 1.0, 2.0, 0.5 },
			{ 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0,
					2.0, 1.0, 1.0, 0.5, 1.0 },
			{ 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 0.5, 0.5, 0.5, 1.0, 0.5,
					1.0, 2.0, 1.0, 1.0, 2.0 },
			{ 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 2.0, 1.0, 2.0, 0.5, 0.5, 2.0, 1.0,
					1.0, 2.0, 0.5, 1.0, 1.0 },
			{ 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5, 1.0,
					1.0, 1.0, 0.5, 1.0, 1.0 },
			{ 1.0, 1.0, 0.5, 0.5, 2.0, 2.0, 0.5, 1.0, 0.5, 0.5, 2.0, 0.5, 1.0,
					1.0, 1.0, 0.5, 1.0, 1.0 },
			{ 1.0, 1.0, 2.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 0.5, 0.5,
					1.0, 1.0, 0.5, 1.0, 1.0 },
			{ 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0,
					0.5, 1.0, 1.0, 0.0, 1.0 },
			{ 1.0, 1.0, 2.0, 1.0, 2.0, 1.0, 1.0, 1.0, 0.5, 0.5, 0.5, 2.0, 1.0,
					1.0, 0.5, 2.0, 1.0, 1.0 },
			{ 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0,
					1.0, 1.0, 2.0, 1.0, 0.0 },
			{ 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 1.0, 2.0, 1.0, 1.0, 1.0, 1.0, 1.0,
					2.0, 1.0, 1.0, 0.5, 0.5 },
			{ 1.0, 2.0, 1.0, 0.5, 1.0, 1.0, 1.0, 1.0, 0.5, 0.5, 1.0, 1.0, 1.0,
					1.0, 1.0, 2.0, 2.0, 1.0 } };

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
