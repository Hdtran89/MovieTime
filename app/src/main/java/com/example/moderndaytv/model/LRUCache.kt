package com.example.moderndaytv.model

internal class Node {
    var value: MovieCard ?= null
    var key: String ?= null
    var left: Node ?= null
    var right: Node ?= null
}

class LRUCache {
    private var mapCache : HashMap<String, Node>
    private val LRU_SIZE = 5
    private var start = Node()
    private var end = Node()
    init {
        mapCache = HashMap(LRU_SIZE)
    }

    fun putItem(key: String, value: MovieCard){
        if(mapCache.containsKey(key)){
            var movieCard = mapCache.get(key)
            movieCard?.value = value
            removeNode(movieCard!!)
            addToTop(movieCard)
        } else {
            val newNode = Node()
            newNode.left = null
            newNode.right = null
            newNode.value = value
            newNode.key = key
            if(mapCache.size > LRU_SIZE){
                mapCache.remove(end.key)
                removeNode(end)
                addToTop(newNode)
            } else {
                addToTop(newNode)
            }
            mapCache.put(key,newNode)
        }
    }

    fun getItem(movieId: String) : MovieCard?{
        if(mapCache.containsKey(movieId)){
            val movieCard = mapCache.get(movieId)
            removeNode(movieCard!!)
            addToTop(movieCard)
            return movieCard.value
        }
        return null
    }

    private fun removeNode(node: Node){
        if (node.left != null) {
            node.left!!.right = node.right
        } else {
            start = node.right!!
        }

        if (node.right != null) {
            node.right!!.left = node.left
        } else {
            end = node.left!!
        }
    }

    private fun addToTop(node: Node){
        node.right = start
        node.left = null
        if (start != null)
            start.left = node
        start = node
        if (end == null)
            end = start
    }
}