package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.FragmentScientificBlockBinding
import kotlin.math.E

/**
 * A simple [Fragment] subclass.
 */
class ScientificBlock : Fragment() {

    private lateinit var binding: FragmentScientificBlockBinding
    private lateinit var mainActivity: MainActivity
    private var currentPage = 0
    private lateinit var pageToButtons: Map<Int, List<Button>>

    private fun setButtonActions() {
        binding.buttonExponent.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Exponent) }
        binding.buttonPower3.setOnClickListener{ mainActivity.doPower(3.0) }
        binding.buttonPower2.setOnClickListener{ mainActivity.doPower(2.0) }
        binding.buttonPowerInvert.setOnClickListener{ mainActivity.doPower(-1.0) }
        binding.buttonSqrt.setOnClickListener{ mainActivity.doPower(1.0/2.0) }
        binding.buttonTan.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Tan) }
        binding.buttonCos.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Cos) }
        binding.buttonSin.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Sin) }
        binding.buttonFactorial.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Factorial) }
        binding.buttonLn.setOnClickListener{ mainActivity.doLog(E) }
        binding.buttonLog10.setOnClickListener{ mainActivity.doLog(10.0) }
        binding.buttonLog2.setOnClickListener{ mainActivity.doLog(2.0) }
        binding.buttonCubrt.setOnClickListener{ mainActivity.doPower(1.0/3.0) }
        binding.buttonArctan.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Arctan) }
        binding.buttonArccos.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Arccos) }
        binding.buttonArcsin.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Arcsin) }
        binding.buttonPage.setOnClickListener { setPage(currentPage + 1) }
        binding.buttonBackspace.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.Backspace) }
        binding.buttonMR.setOnClickListener{ mainActivity.memoryToCurrentString() }
        binding.buttonMMinus.setOnClickListener{ mainActivity.doMemoryPlus(-1.0) }
        binding.buttonMPlus.setOnClickListener{ mainActivity.doMemoryPlus() }
        binding.buttonMC.setOnClickListener{ mainActivity.updateMemory(0.0, 0.0) }
        binding.buttonE.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.InsertE) }
        binding.buttonPi.setOnClickListener{ mainActivity.doInstantOperation(InstantOperation.InsertPi) }
        binding.buttonRootY.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Root) }
        binding.buttonPowerY.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Power) }
        binding.buttonLogY.setOnClickListener{ mainActivity.buttonOperation(CalculatorOperation.Log)
        }
    }

    private fun setPageMap() {
        pageToButtons = mapOf(
            0 to listOf(
                binding.buttonExponent,
                binding.buttonPower3,
                binding.buttonPower2,
                binding.buttonPowerInvert,
                binding.buttonSqrt,
                binding.buttonTan,
                binding.buttonCos,
                binding.buttonSin
            ),
            1 to listOf(
                binding.buttonFactorial,
                binding.buttonLn,
                binding.buttonLog10,
                binding.buttonLog2,
                binding.buttonCubrt,
                binding.buttonArctan,
                binding.buttonArccos,
                binding.buttonArcsin
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scientific_block, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        setButtonActions()
        setPageMap()
    }

    private fun setPage(newPage: Int) {
        currentPage = if(newPage >= pageToButtons.size) {
            0
        } else {
            newPage
        }
        pageToButtons.values.forEach { list -> list.forEach { b -> b.visibility = View.INVISIBLE } }
        pageToButtons[currentPage]?.forEach { b -> b.visibility = View.VISIBLE }
        val button: Button? = view?.findViewById(R.id.buttonPage)
        //button?.text = "${currentPage+1}/${fragments.size}"
        if(newPage == 1)
            button?.setTextColor(ContextCompat.getColor(this.context!!, R.color.colorAccent))
        else {
            if(button?.isEnabled == true)
                button.setTextColor(ContextCompat.getColor(this.context!!, R.color.colorPrimary))
            else
                button?.setTextColor(ContextCompat.getColor(this.context!!, R.color.colorDisabled))
        }

    }


}
