package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentScientific3Binding

/**
 * A simple [Fragment] subclass.
 */
class Scientific3 : Fragment() {

    lateinit var binding: FragmentScientific3Binding
    lateinit var mainActivity: MainActivity
    fun setButtonActions() {
        binding.buttonFactorial.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonArctanh.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonArccosh.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonArcsinh.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonRandom.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonTanh.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonCosh.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonSinh.setOnClickListener{ throw NotImplementedError("not implemented") }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scientific3, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setButtonActions()
    }

}
