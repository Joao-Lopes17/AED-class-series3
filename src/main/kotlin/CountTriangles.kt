import graphCollections.Graph
import graphCollections.GraphStructure
import java.io.File

fun main(args: Array<String>) {
    val startTime = System.currentTimeMillis()

    if (args.size < 3) {
        println("Usage: inputFile outputFile kCounting")
        return
    }

    val inputFile = args[0]
    val outputFile = args[1]
    val kOutputFile = args[2]

    val directed = false

    val graph = GraphStructure<Int, Int>()
    readFile(inputFile, ",", 0, directed, graph)

    val triangleCounts = if (!directed) {
        countTriangles(graph)
    } else {
        countTrianglesDirected(graph)
    }
    writeTriangleCounts(outputFile, triangleCounts)

    println("Total number of triangles: ${triangleCounts.values.sum() / 3}")

    val endTime = System.currentTimeMillis()
    val elapsedTime = endTime - startTime

    println("Elapsed time: $elapsedTime milliseconds")

    while (true) {
        println("Enter a positive number k (or 0 to exit):")
        val k = readLine()?.toIntOrNull()
        if (k != null && k > 0) {
            val verticesWithMostTriangles = getVerticesWithMostTriangles(triangleCounts, k)
            writeVerticesWithMostTriangles(kOutputFile, verticesWithMostTriangles)
        } else break
    }
}

fun readFile(fileName: String, separator: String, skipLines: Int, directed: Boolean, graph: GraphStructure<Int, Int>) {
    val file = File(fileName)
    var lineCount = 0

    file.forEachLine {
        if (lineCount >= skipLines) {
            val (vertex1, vertex2) = it.split(separator).map { it.trim().toIntOrNull() }
            if (vertex1 != null && vertex2 != null) {
                graph.addVertex(vertex1, 0)
                graph.addVertex(vertex2, 0)
                graph.addEdge(vertex1, vertex2)
                if (!directed) {
                    graph.addEdge(vertex2, vertex1)
                }
            }
        }
        lineCount++
    }
}

fun countTriangles(graph: GraphStructure<Int, Int>): Map<Int, Int> {
    val triangleCounts = mutableMapOf<Int, Int>()

    for (vertex in graph) {
        val adjacencies = vertex.getAdjacencies()
        val vertexId = vertex.id
        var triangle = listOf<Graph.Edge<Int>>()
        for (edge in adjacencies) {
            var cont = true
            for (i in triangle) {
                if (i.adjacent == edge.adjacent) {
                    cont = false
                    break
                }
            }
            if (!cont) continue
            val adjacentVertex = graph.getVertex(edge.adjacent)
            if (adjacentVertex != null) {
                val commonAdjacencies = mutableListOf<Graph.Edge<Int>>()
                for (adjacentEdge in adjacentVertex.getAdjacencies()) {
                    if (adjacentEdge.adjacent != vertexId && adjacencies.any { it.adjacent == adjacentEdge.adjacent }) {
                        commonAdjacencies.add(adjacentEdge)
                    }
                }
                triangle = commonAdjacencies
                val triangleCount = triangleCounts[vertexId] ?: 0
                triangleCounts[vertexId] = triangleCount + triangle.size
            }
        }
    }

    return triangleCounts.toSortedMap()
}

fun countTrianglesDirected(graph: GraphStructure<Int, Int>): Map<Int, Int> {
    val triangleCounts = mutableMapOf<Int, Int>()

    for (vertex in graph) {
        val adjacencies = vertex.getAdjacencies()
        val vertexId = vertex.id
        var triangleCount = 0

        for (edge1 in adjacencies) {
            val adjacentVertexId1 = edge1.adjacent

            val adjacentVertex1 = graph.getVertex(adjacentVertexId1) ?: continue
            val adjacencies1 = adjacentVertex1.getAdjacencies()

            for (edge2 in adjacencies1) {
                val adjacentVertexId2 = edge2.adjacent

                if (adjacentVertexId2 == vertexId) {
                    continue
                }

                val adjacentVertex2 = graph.getVertex(adjacentVertexId2) ?: continue
                val adjacencies2 = adjacentVertex2.getAdjacencies()

                for (edge3 in adjacencies2) {
                    if (edge3.adjacent == vertexId) {
                        triangleCount++
                        break
                    }
                }
            }
        }

        triangleCounts[vertexId] = triangleCount
    }

    return triangleCounts.toSortedMap()
}


fun writeTriangleCounts(fileName: String, triangleCounts: Map<Int, Int>) {
    val file = File(fileName)

    file.bufferedWriter().use { writer ->
        for ((vertexId, count) in triangleCounts) {
            writer.write("$vertexId -> $count")
            writer.newLine()
        }
    }
}

fun getVerticesWithMostTriangles(triangleCounts: Map<Int, Int>, k: Int): List<Map.Entry<Int, Int>> {
    return triangleCounts.entries.sortedByDescending { it.value }.take(k)
}

fun writeVerticesWithMostTriangles(fileName: String?, vertices: List<Map.Entry<Int, Int>>) {
    if (fileName != null) {
        val file = File(fileName)

        file.bufferedWriter().use { writer ->
            for (vertexId in vertices) {
                writer.write("${vertexId.key} -> ${vertexId.value}")
                writer.newLine()
            }
        }
    }
}



