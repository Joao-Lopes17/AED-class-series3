package graphCollections

operator fun  <I,D> MutableSet<Graph.Edge<I>>.contains(vertex: Graph.Vertex<I, D>):Boolean{
   for(i in this){
      if(vertex.id == i.adjacent) return true
   }
   return false

}
