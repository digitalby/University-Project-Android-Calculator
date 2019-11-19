package com.example.lr2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
/**
 * A simple [Fragment] subclass.
 */
class ScientificBlock : Fragment() {

    lateinit var mainActivity: MainActivity
    private lateinit var fragments: Array<View>
    private var currentPage = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scientific_block, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity = activity as MainActivity
        fragments = arrayOf(view?.findViewById(R.id.fragmentScientific1)!!,
                view?.findViewById(R.id.fragmentScientific2)!!)
        val button = view?.findViewById(R.id.buttonPage) as Button
        button.setOnClickListener {
            setPage(currentPage + 1)
        }
        setPage(0)
    }

    fun setPage(newPage: Int) {
        if(newPage >= fragments.size) {
            currentPage = 0
        } else {
            currentPage = newPage
        }
        fragments.forEach { x -> x.visibility = View.GONE }
        fragments[currentPage].visibility = View.VISIBLE
        val button = view?.findViewById(R.id.buttonPage) as Button
        //TODO lightup 2ndf on 2nd page
        //button.text = "${currentPage+1}/${fragments.size}"
    }

}
