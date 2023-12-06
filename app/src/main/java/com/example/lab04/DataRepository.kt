package com.example.lab04

class DataRepository {
    private val LIST_SIZE = 2
    private lateinit var dataList: MutableList<RepositoryItem>

    companion object{
        private var INSTANCE: DataRepository? = null
        fun getInstance(): DataRepository{
            if(INSTANCE == null){
                INSTANCE = DataRepository()
            }

            return INSTANCE!!
        }
    }

    fun getData() : MutableList<RepositoryItem> {
        return dataList
    }

    fun deleteItem(position: Int): Boolean {
        dataList.removeAt(position)
        return true
    }

    fun addItem(item: RepositoryItem): Boolean {
        return dataList.add(item)
    }

    init {
        dataList = MutableList(LIST_SIZE) { i -> RepositoryItem(i) }
    }

}