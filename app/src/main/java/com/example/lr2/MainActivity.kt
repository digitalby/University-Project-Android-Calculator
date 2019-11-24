package com.example.lr2

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), CalculatorEngineListener {
    private var currentMode = CalculatorMode.PortraitBasic

    private val buttonIDtoOperation: Map<Int, CalculatorOperation> = mapOf(
        R.id.buttonPlus to CalculatorOperation.Add,
        R.id.buttonMinus to CalculatorOperation.Subtract,
        R.id.buttonMultiply to CalculatorOperation.Multiply,
        R.id.buttonDivide to CalculatorOperation.Divide,
        R.id.buttonPowerY to CalculatorOperation.Power,
        R.id.buttonRootY to CalculatorOperation.Root,
        R.id.buttonLogY to CalculatorOperation.Log,
        R.id.buttonExponent to CalculatorOperation.Exponent
    )

    private val buttonIDToInstant: Map<Int, InstantOperation> = mapOf(
        R.id.buttonPercent to InstantOperation.Percent,
        R.id.buttonPlusMinus to InstantOperation.PlusMinus,
        R.id.buttonPi to InstantOperation.InsertPi,
        R.id.buttonE to InstantOperation.InsertE,
        R.id.buttonBackspace to InstantOperation.Backspace,
        R.id.buttonSin to InstantOperation.Sin,
        R.id.buttonCos to InstantOperation.Cos,
        R.id.buttonTan to InstantOperation.Tan,
        R.id.buttonArcsin to InstantOperation.Arcsin,
        R.id.buttonArccos to InstantOperation.Arccos,
        R.id.buttonArctan to InstantOperation.Arctan,
        R.id.buttonFactorial to InstantOperation.Factorial,
        R.id.buttonPower3 to InstantOperation.Power3,
        R.id.buttonPower2 to InstantOperation.Power2,
        R.id.buttonPowerInvert to InstantOperation.PowerInvert,
        R.id.buttonSqrt to InstantOperation.Sqrt,
        R.id.buttonLn to InstantOperation.Ln,
        R.id.buttonLog10 to InstantOperation.Log10,
        R.id.buttonLog2 to InstantOperation.Log2,
        R.id.buttonCubrt to InstantOperation.Cubrt,
        R.id.buttonMR to InstantOperation.MR,
        R.id.buttonMMinus to InstantOperation.MMinus,
        R.id.buttonMPlus to InstantOperation.MPlus,
        R.id.buttonMC to InstantOperation.MC
    )

    private var menu: Menu? = null
    private lateinit var binding: ActivityMainBinding
    private val engine = CalculatorEngine(this)

    override fun onMemoryChanged(value: Double) {
        val buttonMR: Button? = findViewById(R.id.buttonMR)
        if(buttonMR?.isEnabled == true)
            buttonMR.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        else {
            buttonMR?.setTextColor(ContextCompat.getColor(this, R.color.colorDisabled))
            return
        }
        if(value != 0.0) {
            buttonMR.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
    }

    override fun onDisplayStringChanged(value: String) {
        binding.mainTextView.text = value
    }

    override fun onCurrentOperationChanged(operation: CalculatorOperation) {
        if(BuildConfig.FLAVOR == "demo")
            return
        buttonIDtoOperation.keys.forEach { id ->
            findViewById<Button>(id).setTextColor(ContextCompat.getColor(this, R.color.colorPrimary)) }
        for(id in buttonIDtoOperation.keys) {
            if(buttonIDtoOperation[id] == operation) {
                findViewById<Button?>(id)?.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                break
            }
        }
    }

    fun onClick(view: View) {
        when {
            view.id in buttonIDtoOperation.keys -> engine.doButtonOperation(buttonIDtoOperation[view.id]!!)
            view.id in buttonIDToInstant.keys -> engine.doInstantOperation(buttonIDToInstant[view.id]!!)
            view.id == R.id.buttonEquals -> engine.doEquality()
            else -> {
                val button = view as Button
                engine.inputNumber(button.text.toString())
            }
        }
    }

    private fun setMode(mode: CalculatorMode) {
        val basicBlock: View = findViewById(R.id.fragmentBasicBlock)
        val scientificBlock: View? = findViewById(R.id.fragmentScientificBlock)
        val menuItem: MenuItem? = menu?.findItem(R.id.buttonSwitchMode)
        currentMode = mode
        menuItem?.isVisible = true
        when(mode) {
            CalculatorMode.PortraitBasic -> {
                scientificBlock?.visibility = View.GONE
                basicBlock.visibility = View.VISIBLE
                menuItem?.icon =  ContextCompat.getDrawable(this, R.drawable.scientific)
            }
            CalculatorMode.PortraitScientific -> {
                basicBlock.visibility = View.GONE
                scientificBlock?.visibility = View.VISIBLE
                menuItem?.icon =  ContextCompat.getDrawable(this, R.drawable.basic)
            }
            CalculatorMode.Landscape -> {
                basicBlock.visibility = View.VISIBLE
                menuItem?.isVisible = false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val superRet = super.onCreateOptionsMenu(menu)
        this.menu = menu
        val orientation = resources.configuration.orientation
        if(orientation == Configuration.ORIENTATION_PORTRAIT)
            setMode(CalculatorMode.PortraitBasic)
        else if(orientation == Configuration.ORIENTATION_LANDSCAPE)
            setMode(CalculatorMode.Landscape)
        return superRet
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.buttonSwitchMode) {
            if(currentMode == CalculatorMode.PortraitBasic) {
                setMode(CalculatorMode.PortraitScientific)
            } else if (currentMode == CalculatorMode.PortraitScientific) {
                setMode(CalculatorMode.PortraitBasic)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(engine.putAllToBundle(savedInstanceState))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            engine.inputNumber("AC")
        } else {
            engine.getAllFromBundle(savedInstanceState)
        }
    }
}
