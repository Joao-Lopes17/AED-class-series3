package graphCollections

class GraphStructure<I, D> : Graph<I, D> {
    private val graph: MutableMap<I, Graph.Vertex<I, D>> = mutableMapOf()
    override val size: Int
        get() = graph.size


    override fun getEdge(id: I, idAdj: I): Graph.Edge<I>? {
        //return if (getVertex(id)?.getAdjacencies()!!.contains(EdgeStructure(id, idAdj))) EdgeStructure(id, idAdj)
        //else null
        val edges = getVertex(id)?.getAdjacencies()
        for(i in edges!!){
            if(idAdj == i.adjacent) return i
        }
        return null
    }

    override fun getVertex(id: I): Graph.Vertex<I, D>? {
        for (i in graph) {
            if (id == i.key) return i.value
        }
        return null
    }

    override fun addEdge(id: I, idAdj: I): I? {
        if (getVertex(id) == null) return null
        getVertex(id)?.getAdjacencies()!!.add(EdgeStructure(id, idAdj))
        return idAdj
    }

    override fun addVertex(id: I, d: D): D? {
        if (getVertex(id) != null) return null
        //graph.put(id, VertexStructure(id, d))
        graph[id] = VertexStructure(id, d)
        return d
    }

    override fun iterator(): Iterator<Graph.Vertex<I, D>> {
        return graph.values.iterator()
    }

    inner class VertexStructure(override val id: I, override var data: D) : Graph.Vertex<I, D> {
        val set: MutableSet<Graph.Edge<I>> = mutableSetOf()
        override fun setData(newData: D): D {
            val oldData = data
            data = newData
            return oldData
        }

        override fun getAdjacencies(): MutableSet<Graph.Edge<I>> {
            return set
        }

    }

    inner class EdgeStructure(override val id: I, override val adjacent: I) : Graph.Edge<I>
}

