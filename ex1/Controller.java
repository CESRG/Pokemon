package ex1;

public class Controller {

	private EventSet es = new EventSet();

	public Event getNext() {
		return es.getNext();
	}

	public void addEvent(Event c) {
		es.add(c);
	}

	public void run() {
		Event e;
		while ((e = es.getNext()) != null) {
			if (e.ready()) {
				e.action();
				System.out.println(e.description());
				es.removeCurrent();
			}
		}
	}

	public void terminateController() {
		Event e = es.getCurrent();
		while (es.getNext() != null) {
			es.removeCurrent();
		}
		addEvent(e);
	}

	private void resetEvent() {
		es.reset();
	}

	private void trocaEvent() {
		es.troca();
	}

	protected void organizaEventSet() {
		Event e;
		Event aux;
		boolean trocou = true;
		int jogadorAtivo;
		aux = getNext();
		while (trocou) {
			trocou = false;
			while ((e = getNext()) != null) {
				jogadorAtivo = aux.getPrio() / 10;
				if (jogadorAtivo == e.getPrio() / 10) {
					if (aux.getPrio() > e.getPrio())
						trocaEvent();
					trocou = true;
				}
				aux = e;
			}
			if (trocou) {
				resetEvent();
			}
		}
		resetEvent();
	}
}
