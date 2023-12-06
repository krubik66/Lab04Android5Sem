package com.example.lab04

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [secondTab.newInstance] factory method to
 * create an instance of this fragment.
 */
class secondTab : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var saveButton: Button
    lateinit var radioGroup: RadioGroup

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
        val view = inflater.inflate(R.layout.fragment_second_tab, container, false)

        radioGroup = view.findViewById(R.id.radioGroupSecondTab)
        saveButton = view.findViewById(R.id.secondTabSaveButton)

        saveButton.setOnClickListener {_->
            val data = requireActivity().getSharedPreferences("additional", Context.MODE_PRIVATE)
            val editor = data.edit()
            when(radioGroup.checkedRadioButtonId){
                R.id.radioButton -> editor.putInt("imageNumber", 1)
                R.id.radioButton2 -> editor.putInt("imageNumber", 2)
                R.id.radioButton3 -> editor.putInt("imageNumber", 3)
            }
            editor.apply()
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment secondTab.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            secondTab().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}