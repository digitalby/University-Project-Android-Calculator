package com.example.lr2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.ActivityMainBinding
import kotlin.math.*

enum class CalculatorOperation {
    None,
    Add,
    Subtract,
    Multiply,
    Divide,
    Power,
    Root,
    Log,
    Exponent
}

enum class InstantOperation {
    Percent,
    PlusMinus,
    InsertPi,
    InsertE,
    Backspace,
    Sin,
    Cos,
    Tan,
    Arcsin,
    Arccos,
    Arctan,
    Factorial
}

enum class CalculatorMode {
    PortraitBasic,
    PortraitScientific,
    Landscape
}

class MainActivity : AppCompatActivity() {

    //TODO fragments should communicate using an interface

    private var currentNumberString = ""
    private var currentOperation: CalculatorOperation = CalculatorOperation.None
    private var firstNumber: Double? = null
    private var memory: Double = 0.0
    private var preview: Boolean = false
    private var lastOperation: CalculatorOperation = CalculatorOperation.None
    private var lastOperationNumber: Double = 0.0
    private var currentMode = CalculatorMode.PortraitBasic

    private lateinit var operationToButton: Map<CalculatorOperation, Button>

    private var menu: Menu? = null
    private lateinit var binding: ActivityMainBinding
    

    private fun parseCurrentString(): Double {
        if(currentNumberString.isEmpty() || currentNumberString == "0.") {
            return 0.0
        }
        return currentNumberString.toDouble()
    }

    fun memoryToCurrentString() {
        toCurrentString(memory)
    }

    fun doMemoryPlus(multiplier: Double = 1.0, delta: Double = parseCurrentString()) {
        updateMemory(delta * multiplier)
    }

    private fun toCurrentString(num: Double) {
        val ret = num.toString()
        if(num.isInfinite() || num.isNaN())
            updateStringAndText("Error")
        else if(num % 1 == 0.0) {
            var newValue = num.toString()
            newValue = newValue.replace(Regex("\\.0*$"), "")
            updateStringAndText(newValue)
        }
        else
            updateStringAndText(ret)
    }

    private fun updateStringAndText(str: String, textViewText: String = str) {
        currentNumberString = str
        binding.mainTextView.text = textViewText
    }

    fun doEquality() {
        if(currentNumberString == "Error")
            return
        if(currentOperation != CalculatorOperation.None)
            doCurrentOperation()
        else {
            if(lastOperation != CalculatorOperation.None) {
                setCurrentOperation(lastOperation)
                firstNumber = parseCurrentString()
                toCurrentString(lastOperationNumber)
                doCurrentOperation()
            }
        }
    }

    private fun doPlusMinus() {
        if(currentNumberString.isEmpty())
            return
        if(currentNumberString[0] == '-') {
            currentNumberString = currentNumberString.removeRange(0,1)
        } else {
            if(parseCurrentString() != 0.0) {
                currentNumberString = "-${currentNumberString}"
            }
        }
        binding.mainTextView.text = currentNumberString
    }

    fun doPower(power: Double) {
        setCurrentOperation(CalculatorOperation.Power)
        firstNumber = parseCurrentString()
        toCurrentString(power)
        doCurrentOperation()
    }

    fun doLog(base: Double) {
        setCurrentOperation(CalculatorOperation.Log)
        firstNumber = parseCurrentString()
        toCurrentString(base)
        doCurrentOperation()
    }

    private fun doFactorial() {
        val num = parseCurrentString()
        if(num == 0.0)
            toCurrentString(1.0)
        else if(num % 1 != 0.0) {
            updateStringAndText("Error")
            return
        }
        var ret = 1.0
        for (i: Int in 1..num.absoluteValue.toInt())
            ret *= i
        if(num < 0.0)
            ret = -ret
        toCurrentString(ret)
        preview = true
    }

    private fun doSin() {
        val num = parseCurrentString()
        toCurrentString(sin(num))
        preview = true
    }

    private fun doCos() {
        val num = parseCurrentString()
        toCurrentString(cos(num))
        preview = true
    }

    private fun doTan() {
        val num = parseCurrentString()
        if(num == 90.0 || num == 270.0) {
            updateStringAndText("Error")
        } else {
            toCurrentString(tan(num))
        }
        preview = true
    }

    private fun doArcsin() {
        val num = parseCurrentString()
        toCurrentString(asin(num))
        preview = true
    }

    private fun doArccos() {
        val num = parseCurrentString()
        toCurrentString(acos(num))
        preview = true
    }

    private fun doArctan() {
        val num = parseCurrentString()
        toCurrentString(atan(num))
        preview = true
    }

    private fun doPercent() {
        val num = parseCurrentString() / 100.0
        toCurrentString(num)
        preview = true
    }

    fun buttonOperation(operation: CalculatorOperation) {
        if(currentNumberString == "Error")
            return
        if(currentNumberString.isNotEmpty()) {
            if (!preview || firstNumber == null) {
                doCurrentOperation()
                firstNumber = parseCurrentString()
                preview = true
            }
        }
        setCurrentOperation(operation)
    }

    private fun setCurrentOperation(operation: CalculatorOperation) {
        currentOperation = operation
        operationToButton.values.forEach { b -> b.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary)) }
        operationToButton[currentOperation]?.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
    }

    fun doInstantOperation(operation: InstantOperation) {
        if(currentNumberString == "Error")
            return
        when(operation) {
            InstantOperation.Percent -> doPercent()
            InstantOperation.PlusMinus -> doPlusMinus()
            InstantOperation.InsertPi -> {
                preview = false
                toCurrentString(PI)
            }
            InstantOperation.InsertE -> {
                preview = false
                toCurrentString(E)
            }
            InstantOperation.Sin -> doSin()
            InstantOperation.Cos -> doCos()
            InstantOperation.Tan -> doTan()
            InstantOperation.Arcsin -> doArcsin()
            InstantOperation.Arccos -> doArccos()
            InstantOperation.Arctan -> doArctan()
            InstantOperation.Backspace -> inputNumber("Backspace")
            InstantOperation.Factorial -> doFactorial()
        }
    }

    fun updateMemory(delta: Double = 0.0, newMemory: Double = memory) {
        memory = newMemory
        memory += delta
        val buttonMR: Button? = findViewById(R.id.buttonMR)
        if(buttonMR?.isEnabled == true)
            buttonMR.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        else {
            buttonMR?.setTextColor(ContextCompat.getColor(this, R.color.colorDisabled))
            return
        }
        if(memory != 0.0) {
            buttonMR.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
    }

    private fun doCurrentOperation() {
        if(currentNumberString == "Error")
            return
        val secondNumber = parseCurrentString()
        var result = 0.0
        if(firstNumber == null)
            firstNumber = 0.0
        when(currentOperation) {
            CalculatorOperation.None -> return
            CalculatorOperation.Add -> {
                result = firstNumber!! + secondNumber
            }
            CalculatorOperation.Subtract -> {
                result = firstNumber!! - secondNumber
            }
            CalculatorOperation.Multiply -> {
                result = firstNumber!! * secondNumber
            }
            CalculatorOperation.Divide -> {
                if(secondNumber == 0.0) {
                    updateStringAndText("Error")
                } else {
                    result = firstNumber!! / secondNumber
                }
            }
            CalculatorOperation.Exponent -> {
                result = firstNumber!! * 10.0.pow(secondNumber)
            }
            CalculatorOperation.Log -> {
                if(secondNumber <= 0.0 || secondNumber == 1.0 || firstNumber!! <= 0.0)
                    updateStringAndText("Error")
                else
                    result = log(firstNumber!!, secondNumber)
            }
            CalculatorOperation.Power -> {
                result = firstNumber!!.pow(secondNumber)
            }
            CalculatorOperation.Root -> {
                if(secondNumber == 0.0)
                    updateStringAndText("Error")
                else
                    result = firstNumber!!.pow(1.0/secondNumber)
            }
        }
        if(currentNumberString != "Error")
            toCurrentString(result)
        lastOperationNumber = secondNumber
        lastOperation = currentOperation
        firstNumber = null
        setCurrentOperation(CalculatorOperation.None)
        preview = true
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

    fun inputNumber(symbol: String) {
        if(symbol == "C") {
            setCurrentOperation(CalculatorOperation.None)
            firstNumber = null
            updateStringAndText("", "0")
            preview = false
            lastOperation = CalculatorOperation.None
            lastOperationNumber = 0.0
            return
        }
        if(currentNumberString == "Error")
            return
        if(preview) {
            updateStringAndText("", "0")
            preview = false
        }
        if(currentNumberString.isEmpty()) {
            when (symbol) {
                "." -> currentNumberString = "0."
                "0" -> {
                    binding.mainTextView.text = "0"
                    return
                }
                "Backspace" -> return
                else -> currentNumberString += symbol
            }
        } else {
            if(symbol == "Backspace") {
                currentNumberString = currentNumberString.removeRange(currentNumberString.length-1,currentNumberString.length)
                if(currentNumberString.isEmpty())
                    updateStringAndText("", "0")
            } else if((symbol == "." && !currentNumberString.contains(symbol)) || symbol.isDigitsOnly()) {
                currentNumberString += symbol
            }
        }
        binding.mainTextView.text = currentNumberString
    }

    private fun setOperationMap() {
        operationToButton = mapOf(
            CalculatorOperation.Add to findViewById(R.id.buttonPlus) as Button,
            CalculatorOperation.Subtract to findViewById(R.id.buttonMinus) as Button,
            CalculatorOperation.Multiply to findViewById(R.id.buttonMultiply) as Button,
            CalculatorOperation.Divide to findViewById(R.id.buttonDivide) as Button,
            CalculatorOperation.Power to findViewById(R.id.buttonPowerY) as Button,
            CalculatorOperation.Root to findViewById(R.id.buttonRootY) as Button,
            CalculatorOperation.Log to findViewById(R.id.buttonLogY) as Button,
            CalculatorOperation.Exponent to findViewById(R.id.buttonExponent) as Button
        )
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
        savedInstanceState.putString("currentNumberString", currentNumberString)
        savedInstanceState.putSerializable("currentOperation", currentOperation)
        if(firstNumber != null)
            savedInstanceState.putDouble("firstNumber", firstNumber!!)
        savedInstanceState.putDouble("memory", memory)
        savedInstanceState.putBoolean("preview", preview)
        savedInstanceState.putSerializable("lastOperation", lastOperation)
        savedInstanceState.putDouble("lastOperationNumber", lastOperationNumber)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setOperationMap()
        if (savedInstanceState == null) {
            inputNumber("C")
        } else {
            buttonOperation(savedInstanceState.getSerializable("currentOperation") as CalculatorOperation)
            updateStringAndText(savedInstanceState.getString("currentNumberString")!!)
            firstNumber = savedInstanceState.getDouble("firstNumber")
            updateMemory(0.0, savedInstanceState.getDouble("memory"))
            preview = savedInstanceState.getBoolean("preview")
            lastOperation = savedInstanceState.getSerializable("lastOperation") as CalculatorOperation
            lastOperationNumber = savedInstanceState.getDouble("lastOperationNumber")
        }
        if(currentNumberString.isEmpty())
            updateStringAndText("", "0")
    }
}
