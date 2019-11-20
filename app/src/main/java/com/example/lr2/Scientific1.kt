package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentScientific1Binding

/**
 * A simple [Fragment] subclass.
 */
class Scientific1 : Fragment() {

    lateinit var binding: FragmentScientific1Binding
    lateinit var mainActivity: MainActivity
    fun setButtonActions() {
        binding.buttonExponent.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Exponent) }
        binding.buttonPower3.setOnClickListener{ mainActivity.doPower(3.0) }
        binding.buttonPower2.setOnClickListener{ mainActivity.doPower(2.0) }
        binding.buttonPowerInvert.setOnClickListener{ mainActivity.doPower(-1.0) }
        binding.buttonSqrt.setOnClickListener{ mainActivity.doPower(1.0/2.0) }
        binding.buttonTan.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Tan) }
        binding.buttonCos.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Cos) }
        binding.buttonSin.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Sin) }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scientific1, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setButtonActions()
    }

}
