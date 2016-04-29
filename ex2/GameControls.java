
public class GameControls extends FightControls {
	private int[][] mapa = new int[100][100]; //0 será chão normal, 1 será grama
	private int[] pos = new int[2];
	public void inicializaMapa(){
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 100; j++){
				if(java.lang.Math.random() > 0.5){
					mapa[i][j]=1;
				}
				else{
					mapa[i][j]=0;
				}
			}
		}
		pos[1]=0;
		pos[2]=0;
	}
	private class Mover extends Event{
		private int i;
		private int destino;
		private Treinador jogador;
		
		public Treinador geraAnimalSelvagem(){
			return (new Treinador({new Pikachu()}, "pikachu", 2 ))
		}
		public Mover(long eventTime, int dir, Treinador jogador) { //1 = esquerda; 2 = direita; 3 = para cima; 4 = para baixo
			super(eventTime);
			i = dir;
			this.jogador = jogador;
		}
		public void action(){
			if(i == 1 && pos[1]!=0){
				pos[1]--; 
				destino = mapa[pos[1]][pos[2]];
				if(destino == 1 && ((int)4*java.lang.Math.random()) >= 3){ 
					double tm =  System.currentTimeMillis();
					addEvent(new Luta(tm, jogador, geraAnimalSelvagem()));
				}
			}
			if(i == 2 && pos[1]!=99){
				pos[1]++; 
				destino = mapa[pos[1]][pos[2]];
				if(destino == 1 && ((int)4*java.lang.Math.random()) > 3){
					double tm =  System.currentTimeMillis();
					addEvent(new Luta(tm, jogador, geraAnimalSelvagem()));
				}
			}
			if(i == 3 && pos[1]!=0){
				pos[2]--; 
				destino = mapa[pos[1]][pos[2]];
				if(destino == 1 && ((int)4*java.lang.Math.random()) > 3){
					double tm =  System.currentTimeMillis();
					addEvent(new Luta(tm, jogador, geraAnimalSelvagem()));
				}
			}
			if(i == 4 && pos[1]!=99){
				pos[2]++; 
				destino = mapa[pos[1]][pos[2]];
				if(destino == 1 && ((int)4*java.lang.Math.random()) > 3){
					double tm =  System.currentTimeMillis();
					addEvent(new Luta(tm, jogador, geraAnimalSelvagem()));
				}
			}
		}
		public String description(){
			if(i == 1 && pos[1]!=0){
				return("ele se moveu para a esquerda");
			}
			if(i == 2 && pos[1]!=99){
				return("ele se moveu para a direita");
			}
			if(i == 3 && pos[1]!=0){
				return("ele se moveu para cima");
			}
			if(i == 4 && pos[1]!=99){
				return("ele se moveu para baixo");
			}
			else{
				return("ele não pode se mover nessa direção");
			}
		}
	}
	private class Luta extends Event{
		private Treinador t;
		private int i = 0;
		private int r;
		public Luta(long eventTime, Treinador jogador, Treinador selvagem) {
			super(eventTime);
		}
		public void action(){
			while(selvagem.getAtual().getAcordado() && (jogador.achaPokemonAcordado() >= 0)){
				r= (int) (4*java.lang.Math.random());
				addEvent(jogador.fazAtaque(tm, i%4, selvagem));
				addEvent(selvagem.fazAtaque(tm, r%4, jogador));
				i++;
				run();
			}
		}
		public String description(){
			return("fim de batalha";
		}
	}
	public static void main(String[] args) {

		GameControls gc = new GameControls();
		long tm = System.currentTimeMillis();
		Treinador ash = new Treinador({new Pikachu(), new Charizard(),
				new Squirtle(), new Magikarp()}, "Ash", 1);
		int r= (int) (4*java.lang.Math.random());
		
		for(int i = 0; i < 20; i++){
			gc.addEvent(new Mover(tm, r, ash));
		}
		fc.run();
	}
	
}
