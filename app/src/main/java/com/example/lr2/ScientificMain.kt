package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentScientificMainBinding

/**
 * A simple [Fragment] subclass.
 */
class ScientificMain : Fragment() {

    lateinit var binding: FragmentScientificMainBinding
    lateinit var mainActivity: MainActivity

    fun setButtonActions() {
        binding.buttonBackspace.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Backspace) }
        binding.buttonMR.setOnClickListener{ mainActivity.toCurrentString(mainActivity.memory) }
        binding.buttonMMinus.setOnClickListener{ mainActivity.updateMemory(-mainActivity.parseCurrentString()) }
        binding.buttonMPlus.setOnClickListener{ mainActivity.updateMemory(mainActivity.parseCurrentString()) }
        binding.buttonMC.setOnClickListener{ mainActivity.updateMemory(0.0, 0.0) }
        binding.buttonE.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.InsertE) }
        binding.buttonPi.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.InsertPi) }
        binding.buttonRootY.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Root) }
        binding.buttonPowerY.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Power) }
        binding.buttonLogY.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Log) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scientific_main, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setButtonActions()
    }


}
