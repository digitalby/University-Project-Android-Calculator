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
        binding.buttonExponent.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonPower3.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonPower2.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonPowerInvert.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonSqrt.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonTan.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonCos.setOnClickListener{ throw NotImplementedError("not implemented") }
        binding.buttonSin.setOnClickListener{ throw NotImplementedError("not implemented") }
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
