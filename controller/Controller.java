package controller;

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

	public void trocaEvent() {
		es.troca();
	}

	public void resetEvent() {
		es.reset();
	}
}
