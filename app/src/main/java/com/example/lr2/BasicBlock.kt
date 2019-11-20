package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentBasicBlockBinding

/**
 * A simple [Fragment] subclass.
 */
class BasicBlock : Fragment() {

    lateinit var binding: FragmentBasicBlockBinding
    lateinit var mainActivity: MainActivity


    fun setButtonActions() {
        binding.button0.setOnClickListener{ mainActivity.inputNumber("0") }
        binding.button1.setOnClickListener{ mainActivity.inputNumber("1") }
        binding.button2.setOnClickListener{ mainActivity.inputNumber("2") }
        binding.button3.setOnClickListener{ mainActivity.inputNumber("3") }
        binding.button4.setOnClickListener{ mainActivity.inputNumber("4") }
        binding.button5.setOnClickListener{ mainActivity.inputNumber("5") }
        binding.button6.setOnClickListener{ mainActivity.inputNumber("6") }
        binding.button7.setOnClickListener{ mainActivity.inputNumber("7") }
        binding.button8.setOnClickListener{ mainActivity.inputNumber("8") }
        binding.button9.setOnClickListener{ mainActivity.inputNumber("9") }
        binding.buttonDecimal.setOnClickListener{ mainActivity.inputNumber(".") }
        binding.buttonC.setOnClickListener{ mainActivity.inputNumber("C") }
        binding.buttonPlus.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Add) }
        binding.buttonMinus.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Subtract) }
        binding.buttonMultiply.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Multiply) }
        binding.buttonDivide.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Divide) }
        binding.buttonEquals.setOnClickListener{ mainActivity.doEquality() }
        binding.buttonPlusMinus.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.PlusMinus) }
        binding.buttonPercent.setOnClickListener { mainActivity.doInstantOperation(InstantOperation.Percent) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basic_block, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setButtonActions()
    }


}
