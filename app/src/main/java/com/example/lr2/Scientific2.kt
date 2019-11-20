package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentScientific2Binding
import kotlin.math.E

/**
 * A simple [Fragment] subclass.
 */
class Scientific2 : Fragment() {

    lateinit var binding: FragmentScientific2Binding
    lateinit var mainActivity: MainActivity
    fun setButtonActions() {
        binding.buttonFactorial.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Factorial) }
        binding.buttonLn.setOnClickListener{ mainActivity.doLog(E) }
        binding.buttonLog10.setOnClickListener{ mainActivity.doLog(10.0) }
        binding.buttonLog2.setOnClickListener{ mainActivity.doLog(2.0) }
        binding.buttonCubrt.setOnClickListener{ mainActivity.doPower(1.0/3.0) }
        binding.buttonArctan.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Arctan) }
        binding.buttonArccos.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Arccos) }
        binding.buttonArcsin.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Arcsin) }
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
