package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentScientific2Binding

/**
 * A simple [Fragment] subclass.
 */
class Scientific2 : Fragment() {

    lateinit var binding: FragmentScientific2Binding
    lateinit var mainActivity: MainActivity
    fun setButtonActions() {
        binding.buttonFactorial.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonLn.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonLog10.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonLog2.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonCubrt.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonArctan.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonArccos.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonArcsin.setOnClickListener{ throw NotImplementedError("not implemented") }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scientific2, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setButtonActions()
    }

}
