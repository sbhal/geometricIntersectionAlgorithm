package project2_latest;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;

public class HVIntersection {
	RandomLineGenerator randLineSet;

	public HVIntersection(RandomLineGenerator randLineSet) {
		super();
		this.randLineSet = randLineSet;
	}

	// helper class for events in sweep line algorithm
	public static class Event implements Comparable<Event> {
		//public static class Event {
		int time;
		SegmentHV segment;

		public Event(int time, SegmentHV segment) {
			this.time    = time;
			this.segment = segment;
		}

		public int compareTo(Event that) {
			if      (this.time < that.time) return -1;
			else if (this.time > that.time) return +1;
			else                            return  0; 
		}
	}
	//Comparator anonymous class implementation
	public static Comparator<Event> idComparator = new Comparator<Event>(){

		@Override
		public int compare(Event c1, Event c2) {
			return (int) (c1.time - c2.time);
		}
	};

	// the sweep-line algorithm
	public void printIntersection() {
		System.out.println("Intersection found with Sweep Line Algorithm are:");
		int INFINITY = Integer.MAX_VALUE;   // -INFINITY is second smallest integer

		//PriorityQueue<Event> pq = new PriorityQueue<Event>(N, idComparator);
		PriorityQueue<Event> pq = new PriorityQueue<Event>();

		//reading Horizontal Lines
		ArrayList<HorizontalLines> hline = randLineSet.getHorizontalLine();
		for (int i = 0; i < hline.size(); i++) {
			HorizontalLines line = hline.get(i);
			SegmentHV segment = new SegmentHV(line.x, line.y, line.x+25, line.y);
			Event e1 = new Event(segment.x1, segment);
			Event e2 = new Event(segment.x2, segment);
			pq.add(e1);
			pq.add(e2);
		}
		//reading Vertical Lines
		ArrayList<VerticalLines> vline = randLineSet.getVerticalLine();
		for (int i = 0; i < vline.size(); i++) {
			VerticalLines line = vline.get(i);
			SegmentHV segment = new SegmentHV(line.getX(), line.getY(), line.getX(), line.getY()+25);
			Event e = new Event(segment.x1, segment);
			pq.add(e);
		}

		// run sweep-line algorithm
		RangeSearch<SegmentHV, Integer> st = new RangeSearch<SegmentHV, Integer>();

		while (0 != pq.size()) {
			Event e = pq.poll();
			int sweep = e.time;
			//System.out.print(sweep + " - ");
			SegmentHV segment = e.segment;

			// vertical segment
			if (segment.isVertical()) {
				// a bit of a hack here - use infinity to handle degenerate intersections
				SegmentHV seg1 = new SegmentHV(-INFINITY, segment.y1, -INFINITY, segment.y1);
				SegmentHV seg2 = new SegmentHV(+INFINITY, segment.y2, +INFINITY, segment.y2);
				Iterable<SegmentHV> list = st.range(seg1, seg2);
				for (SegmentHV seg : list) {
					System.out.println("Intersection:  " + segment + " with " + seg);
				}
			}

			// next event is left endpoint of horizontal h-v segment
			else if (sweep == segment.x1) {
				//st.add(segment);
				st.put(segment, 0);
			}

			// next event is right endpoint of horizontal h-v segment
			else if (sweep == segment.x2) {
				st.remove(segment);
			}
		}
	}
}