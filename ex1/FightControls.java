package ex1;

public class FightControls extends Controller {

    private class Treinador {

        private String nomeTreinador;
        private Pokemon[] pokemons = new Pokemon[6];
        private Pokemon atual;
        private int indiceAtual;
        private int ordemAtaque;

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
            for (int i = 0; i < 6; i++) {
                if (pokemons[i].getAcordado()) {
                    return i;
                }
            }
            return -1;
        }

        public Pokemon getPokemon(int i) {
            return pokemons[i];
        }

        private void terminate() {
            terminateController();
        }

        public Fugir fuga(long eventTime) {
            return new Fugir(eventTime);
        }

        private class Fugir extends Event {

            public Fugir(long eventTime) {
                super(eventTime);
                priority = 1 + ordemAtaque * 10;
            }

            public void action() {
                terminate();
            }

            public String description() {
                return (nomeTreinador + " fugiu da luta.");
            }
        }

        public TrocarPokemon trocaPokemon(long tm, int escolhido) {
            return new TrocarPokemon(tm, escolhido);
        }

        private class TrocarPokemon extends Event {

            private int i;

            public TrocarPokemon(long eventTime, int escolhido) {
                super(eventTime);
                priority = 2 + ordemAtaque * 10;
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
                } else {
                    return ("Não é possível usar "
                            + "um Pokémon desmaiado!");
                }
            }
        }

        public UsarItem usaItem(long eventTime, int escolhido) {
            return new UsarItem(eventTime, escolhido);
        }

        private class UsarItem extends Event {

            private int i;
            Pokemon p;

            public UsarItem(long eventTime, int escolhido) {
                super(eventTime);
                i = escolhido;
                p = pokemons[i];
                priority = 3 + ordemAtaque * 10;
            }

            public void action() {
                if (p.getAcordado()) {
                    p.recebeCura(10);
                }
            }

            public String description() {
                if (p.getAcordado()) {
                    return (nomeTreinador + " curou " + p.getNome()
                            + "!\n" + p.getNome() + " tem "
                            + p.getHP() + " de vida agora!");
                } else {
                    return ("Não é possível curar um Pokémon desmaiado!");
                }
            }
        }

        public Ataque fazAtaque(long eventTime, int nAtaque,
                Treinador alvo) {
            return new Ataque(eventTime, nAtaque, alvo);
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
            }

            public void action() {
                atual.EscolheAtaque(n).action(pokemonAlvo);
                if (!pokemonAlvo.getAcordado()) {
                    int iAcordado = alvo.achaPokemonAcordado();
                    if (iAcordado >= 0) {
                        alvo.trocaPokemon(System.currentTimeMillis(),
                                iAcordado).action();
                    } else {
                        terminate();
                    }
                }
            }

            public String description() {
                return ret;
            }
        }
    }

    private class Restart extends Event {

        public Restart(long eventTime) {
            super(eventTime);
        }

        public void action() {
            Pokemon[] pkmn1 = { new Pokemon("Pikachu", 35, 13),
                    new Pokemon("Charmander", 39, 10),
                    new Pokemon("Squirtle", 44, 11),
                    new Pokemon("Magikarp", 20, 11),
                    new Pokemon("Abra", 25, 14),
                    new Pokemon("Paras", 35, 7) };
            Pokemon[] pkmn2 = { new Pokemon("Zubat", 40, 3),
                    new Pokemon("Diglet", 10, 5),
                    new Pokemon("Bulbasaur", 45, 12),
                    new Pokemon("Shellder", 30, 1),
                    new Pokemon("Ditto", 48, 1),
                    new Pokemon("Caterpie", 45, 7),
                    new Pokemon("Pidgey", 40, 3) };
            long tm = System.currentTimeMillis();

            Treinador ash = new Treinador(pkmn1, "Ash", 1);
            Treinador trash = new Treinador(pkmn2, "Trash", 2);
            addEvent(ash.fazAtaque(tm, 1, trash));
            addEvent(ash.usaItem(tm, 0));
            addEvent(trash.trocaPokemon(tm, 3));
            addEvent(trash.fazAtaque(tm, 4, ash));
            addEvent(trash.usaItem(tm, 2));
            addEvent(ash.fazAtaque(tm, 3, trash));
            organizaEventSet();
        }

        public String description() {
            return ("Restarting system");
        }
    }

    // public void organizaEventSet() {
    // Event e;
    // Event aux;
    // boolean trocou = true;
    // int jogadorAtivo;
    // aux = getNext();
    // while (trocou) {
    // trocou = false;
    // while ((e = getNext()) != null) {
    // jogadorAtivo = aux.getPrio() / 10;
    // if (jogadorAtivo == e.getPrio() / 10) {
    // if (aux.getPrio() > e.getPrio())
    // trocaEvent();
    // trocou = true;
    // }
    // aux = e;
    // }
    // if (trocou) {
    // resetEvent();
    // }
    // }
    // resetEvent();
    // }

    public static void main(String[] args) {

        FightControls fc = new FightControls();
        long tm = System.currentTimeMillis();
        fc.addEvent(fc.new Restart(tm));
        fc.run();
    }
}
