package me.digitalby.lr2


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import me.digitalby.lr2.R
import me.digitalby.lr2.databinding.FragmentBasicBlockBinding

class BasicBlock : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentBasicBlockBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.fragment_basic_block, container, false)
        return binding.root
    }
}
