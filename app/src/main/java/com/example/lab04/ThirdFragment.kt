package com.example.lab04

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab04.databinding.FragmentThirdBinding
import com.example.lab04.databinding.ListItemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val dataList = DataRepository.getInstance()
    private lateinit var data: MutableList<RepositoryItem>
    private lateinit var _binding: FragmentThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        val recView = _binding.recycleView
        recView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = DataRepository.getInstance().getData()?.let { MyAdapter(it) }
        recView.adapter = adapter
        data = adapter!!.data
        _binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_thirdFragment_to_addDataFragment)
        }

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("addNewItem", viewLifecycleOwner){ string, bundle ->
            run {
                if (bundle.getBoolean("toAdd")){
                    val itemName = bundle.getString("name", "New person")
                    val itemSpec = bundle.getString("spec", "Some spec")
                    val itemStrength = bundle.getFloat("strength", 1.0F)
                    val itemDanger = bundle.getBoolean("danger", false)
                    val itemType = bundle.getString("type", "Lich")
                    val newItem = RepositoryItem(itemName, itemSpec, itemStrength, itemType, itemDanger)
                    dataList.addItem(newItem)
                }
            }
        }

        parentFragmentManager.setFragmentResultListener("editItem", viewLifecycleOwner){ string, bundle ->
            run {
                if (bundle.getBoolean("edit")){
                    val itemName = bundle.getString("name", "New person")
                    val itemSpec = bundle.getString("spec", "Some spec")
                    val itemStrength = bundle.getInt("strength", 1)
                    val itemDanger = bundle.getBoolean("danger", false)
                    val itemType = bundle.getString("type", "Lich")
                    val newItem = RepositoryItem(itemName, itemSpec, itemStrength.toFloat(), itemType, itemDanger)
                    val pos = bundle.getInt("position", -1)
                    if(pos != -1) {
//                        data.removeAt(pos)
                        data[pos] = newItem
                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.listmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.addListItemMenuButton -> {
                deleteSelectedItems()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteSelectedItems() {
        val selectedItems = dataList.getData()?.filter { it.isChecked }
        selectedItems?.forEach {
            dataList.deleteItem(it)
        }
        activity?.recreate()
    }

    inner class MyAdapter(var data: MutableList<RepositoryItem>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        inner class MyViewHolder(viewBinding: ListItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
            val txt1: TextView = viewBinding.itemTitle
            val txt2: TextView = viewBinding.itemSpec1
            val img: ImageView = viewBinding.itemImg
            val checkbox: CheckBox = viewBinding.itemCheckbox
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val viewBinding = ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return MyViewHolder(viewBinding)
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var currData = data[position]
            holder.txt1.text = currData.text_name
            holder.txt2.text = if (currData.text_spec == "Default specification") {
                (currData.item_type + " " + currData.text_spec + " " + currData.item_strength)
            } else {
                currData.text_spec
            }
            holder.checkbox.isChecked = currData.isChecked
            holder.itemView.setOnClickListener {
                parentFragmentManager.setFragmentResult(
                    "msgtochild", bundleOf(
                        "name" to currData.text_name,
                        "spec" to currData.text_spec,
                        "strength" to currData.item_strength,
                        "danger" to currData.dangerous,
                        "type" to currData.item_type,
                        "humanoids" to currData.summons,
                        "position" to position
                    )
                )



                findNavController().navigate(R.id.action_thirdFragment_to_showDataFragment)
            }
            holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
                currData.isChecked = isChecked
            }
            holder.itemView.setOnLongClickListener {
                if (dataList.deleteAt(position))
                    notifyDataSetChanged()
                true
            }
            when(currData.item_type) {
                "Lich" -> holder.img.setImageResource(R.drawable.playericon)
                else -> holder.img.setImageResource(R.drawable.skeletonicon)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThirdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThirdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}