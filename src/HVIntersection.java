/******************************************************************************
 *  Compilation:  javac HVIntersection.java
 *  Execution:    java HVIntersection N
 *  
 *  Generate N random h-v line segments and print out all
 *  pairwise intersections between them.
 * 
 *  % java HVIntersection 1000
 *
 ******************************************************************************/
import java.util.PriorityQueue;
import java.util.Comparator;

public class HVIntersection {

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
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int INFINITY = Integer.MAX_VALUE;   // -INFINITY is second smallest integer

        // generate N random h-v segments
        SegmentHV[] segments = new SegmentHV[N];
        for (int i = 0; i < N; i++) {
            int x1  = (int) (Math.random() * 10);
            int x2  = x1 + (int) (Math.random() * 3);
            int y1  = (int) (Math.random() * 10);
            int y2  = y1 + (int) (Math.random() * 5);
            if (Math.random() < 0.5) segments[i] = new SegmentHV(x1, y1, x1, y2);
            else                     segments[i] = new SegmentHV(x1, y1, x2, y1);
            System.out.println(segments[i]);
        }
        System.out.println();

        // create events
        //MinPQ<Event> pq = new MinPQ<Event>();
        //PriorityQueue<Event> pq = new PriorityQueue<Event>(N, idComparator);
        PriorityQueue<Event> pq = new PriorityQueue<Event>(N);
        for (int i = 0; i < N; i++) {
            if (segments[i].isVertical()) {
                Event e = new Event(segments[i].x1, segments[i]);
                //pq.insert(e);
                pq.add(e);
            }
            else if (segments[i].isHorizontal()) {
                Event e1 = new Event(segments[i].x1, segments[i]);
                Event e2 = new Event(segments[i].x2, segments[i]);
                //pq.insert(e1);
                //pq.insert(e2);
                pq.add(e1);
                pq.add(e2);
            }
        }

        // run sweep-line algorithm
        RangeSearch<SegmentHV, Integer> st = new RangeSearch<SegmentHV, Integer>();

        //while (!pq.isEmpty()) {
        while (0 != pq.size()) {
            //Event e = pq.delMin();
        	Event e = pq.poll();
            int sweep = e.time;
            System.out.print(sweep + " - ");
            SegmentHV segment = e.segment;
           
            // vertical segment
            if (segment.isVertical()) {
                // a bit of a hack here - use infinity to handle degenerate intersections
                SegmentHV seg1 = new SegmentHV(-INFINITY, segment.y1, -INFINITY, segment.y1);
                SegmentHV seg2 = new SegmentHV(+INFINITY, segment.y2, +INFINITY, segment.y2);
                Iterable<SegmentHV> list = st.range(seg1, seg2);
                for (SegmentHV seg : list) {
                    System.out.println("Intersection:  " + segment);
                    System.out.println("               " + seg);
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