package com.example.tarif_defteri

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import kotlinx.android.synthetic.main.fragment_list.*
import java.lang.Exception


class ListFragment : Fragment() {
    val yemekIsmiList = ArrayList<String>()
    val yemekIDList = ArrayList<Int>()
    private lateinit var listeAdapter : ListeRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeAdapter = ListeRecyclerAdapter( yemekIsmiList,yemekIDList)
        recyclerView.layoutManager =  LinearLayoutManager(context)
        recyclerView.adapter = listeAdapter
        sqlVeriAlma()
    }
    fun sqlVeriAlma(){
        try {
         activity?.let {
             val database = it.openOrCreateDatabase("Yemekler", Context.MODE_PRIVATE,null)
             val cursor = database.rawQuery("SELECT * FROM yemekeler",null )
             val yemekIsmiIndex = cursor.getColumnIndex("yemekismi")
             val yemekID = cursor.getColumnIndex("id")

             yemekIsmiList.clear()
             yemekIDList.clear()

             while (cursor.moveToNext()){
                 yemekIsmiList.add(cursor.getString(yemekIsmiIndex))
                 yemekIDList.add(cursor.getInt(yemekID))
             }
             listeAdapter.notifyDataSetChanged()
             cursor.close()


         }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}