package com.example.listeddashboard.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.listeddashboard.Model.ListCustom
import com.example.listeddashboard.R
import com.example.listeddashboard.ViewModel.Vm
import com.example.listeddashboard.utils.Adapter

class RecentsLinks : Fragment() {

    private lateinit var list: ListView
    private lateinit var vm: Vm
    private lateinit var myadapter: Adapter
    private lateinit var arrayList: ArrayList<ListCustom>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflating our fragment
        return inflater.inflate(R.layout.fragment_recent_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = view.findViewById(R.id.lstView)
        // taking instance of viewmodel class
        vm = ViewModelProvider(requireActivity())[Vm::class.java]

        arrayList = ArrayList()
        myadapter = Adapter(requireContext(), arrayList)
        list.adapter = myadapter
        //setting up observer for our live data
        //used viewLifecycleOwner because activity lifecycle of activity and fragment differs
        vm.res.observe(viewLifecycleOwner, Observer { res ->
            val topList = res.data.recent_links.map {
                ListCustom(
                    title = it.title,
                    total_clicks = it.total_clicks,
                    original_image = it.original_image,
                    smart_link = it.smart_link,
                    created_at = it.created_at,
                )
            }
            arrayList.clear()
            arrayList.addAll(topList)
            myadapter.notifyDataSetChanged()
        })

        vm.err.observe(viewLifecycleOwner, Observer { err ->
            Log.e("Error in TopLinks fragment", err)
        })
    }
    companion object {
        fun newInstance(topLinks: List<ListCustom>): TopLinks {
            val fragment = TopLinks()
            return fragment
        }
    }
}

