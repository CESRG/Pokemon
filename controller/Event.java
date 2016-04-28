package controller;

abstract public class Event {
	
	private long evtTime;
	private static int priority;
	
	public Event(long eventTime) {
		evtTime = eventTime;
	}
	public boolean ready() {
		return System.currentTimeMillis() >= evtTime;
	}
	public int getPrio() {
		return priority;
	}
	abstract public void action();
	abstract public String description();
}

class EventSet {
	
	private Event[] events = new Event[100];
	private	int	index = 0;
	private	int	next = 0;
	private boolean	looped = false;
	
	public void add(Event e) {
		if (index >= events.length)
			return;
		events[index++] = e;
	}
	
	public Event getNext() {
		int start = next;
		do {
			next = (next + 1) % events.length;
			if (start == next)
				looped = true;
			if ((next == (start + 1) % events.length) && looped)
				return null;
		} while(events[next] == null);
		return events[next];
	}
	public void removeCurrent() {
		events[next] = null;
	}
	public Event getCurrent() {
		return events[next];
	}

 	 public void exchange(){
 		Event aux;
 		aux = events[(next-1)];
 		events[(next-1)]= events[next];
 		events[next]=aux;
 	}
 	public void reset(){
 		next = 0;
 		looped = false;
 	}
 
}
