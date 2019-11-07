/*
Peiyi Guan
215328917
*/



import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;



class Graph<L> implements AbsGraph<L>
{
    /* nested class start */
    /*special object for vertex */
    private class VertexInfo {
        public int level ;
        private VertexInfo(int l){
            level = l;
        }
    }
    /* nested class end */


    private Map<L, VertexInfo> vertex;
    private Map<L, Set<L>> edgesTo;
    private Map<L, Set<L>> edgesFrom;

    protected int numOfVertex = 0;
    protected int numOfEdges = 0;


    public Graph(){
        this.vertex = new TreeMap();
        this.edgesTo = new TreeMap();
        this.edgesFrom = new TreeMap();
    }


    public void addVertex(L key)
    {
        if (!this.vertex.containsKey(key)) {
            this.vertex.put(key, new VertexInfo(-1));
            this.numOfVertex ++;
        }
    }
    private boolean validate(L from, L to){
        return this.vertex.containsKey(from) && this.vertex.containsKey(to);
    }

    public void addEdge(L from, L to)
    {
        if (this.validate(from,to)){ // if both vertex are created

            if (this.edgesFrom.get(from) == null) {
                this.edgesFrom.put(from, new TreeSet());
            }
            if (this.edgesTo.get(to) == null) {
                this.edgesTo.put(to, new TreeSet());
            }

            boolean f = this.edgesFrom.get(from).add(to);
            boolean t = this.edgesTo.get(to).add(from);

            if (f && t){
                this.numOfEdges ++;
            }
        }
    }

    public int numVertices() {
        return this.numOfVertex;
    }

    public int numEdges() {
        return this.numOfEdges;
    }
    private void levelMarkRec(Set<L> edges, L to, int level){
        for (Object e : edges) {
            if (e.equals(to)){
                this.vertex.get(e).level = level;
                return;
            }

            if (this.vertex.get(e).level < 0)
            {
                this.vertex.get(e).level = level;
                Set localSet = this.edgesFrom.get(e);
                levelMarkRec(localSet, to, level + 1); //recursive call
            }
        }
    }


    public Iterator<L> findPath(L from, L to) {

        ArrayList container = new ArrayList();
        TreeSet temp = new TreeSet();

        temp.add(from);
        this.levelMarkRec(temp, to, 0);


        Object des = to;
        int l = this.vertex.get(des).level;

        while (l > 0) {
            container.add(des);
            Iterator edgesIterator= this.edgesTo.get(des).iterator();
            while(edgesIterator.hasNext()) {
                Object edgeHolder = edgesIterator.next();
                 int k = l - this.vertex.get(edgeHolder).level;
                if (k==1) {
                    des = edgeHolder;
                    l--;
                    break;
                }

            }

        }

        container.add(from);
        return container.iterator();
    }



}
