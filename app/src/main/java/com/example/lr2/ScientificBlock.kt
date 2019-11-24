package com.example.lr2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.lr2.databinding.FragmentScientificBlockBinding

/**
 * A simple [Fragment] subclass.
 */
class ScientificBlock : Fragment() {

    private lateinit var binding: FragmentScientificBlockBinding
    private var currentPage = 0
    private lateinit var pageToButtons: Map<Int, List<Button>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scientific_block, container, false)
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.buttonPage.setOnClickListener { setPage(currentPage + 1) }
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
