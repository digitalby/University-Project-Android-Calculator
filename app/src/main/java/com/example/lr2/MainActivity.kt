package com.example.lr2

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
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

    var currentNumberString = ""
    var currentOperation: CalculatorOperation = CalculatorOperation.None
    var firstNumber: Double? = null
    var memory: Double = 0.0; private set
    var menu: Menu? = null
    private var preview: Boolean = false
    var lastOperation: CalculatorOperation = CalculatorOperation.None
    var lastOperationNumber: Double = 0.0
    var currentMode = CalculatorMode.PortraitBasic
    lateinit var binding: ActivityMainBinding
    

    fun parseCurrentString(): Double {
        if(currentNumberString.isEmpty() || currentNumberString == "0.") {
            return 0.0
        }
        return currentNumberString.toDouble()
    }

    fun toCurrentString(num: Double) {
        val ret = num.toString()
        if(num.isInfinite() || num.isNaN())
            updateStringAndText("Error")
        else
            updateStringAndText(ret)
        //FIXME long strings and big numbers are not handled!

        //FIXME no trailing zeros allowed!
        //TODO function for making string (both normal and short)
        //TODO handle numbers that are too long (>=1 billion) or with too long decimal parts. don't forget MINUS!
        //TODO handle both portrait and landscape (introduce a new variable?)
    }

    fun updateStringAndText(str: String, textViewText: String = str) {
        currentNumberString = str
        binding.mainTextView.text = textViewText
    }

    fun filterStringAndText() {

    }

    fun doEquality() {
        if(currentNumberString == "Error")
            return
        if(currentOperation != CalculatorOperation.None)
            doCurrentOperation()
        else {
            currentOperation = lastOperation
            firstNumber = parseCurrentString()
            toCurrentString(lastOperationNumber)
            doCurrentOperation()
        }
    }

    fun doPlusMinus() {
        if(currentNumberString.isEmpty() || currentNumberString == "Error")
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

    //TODO Error check should probably be in doInstantOperation? (DRY principle)

    fun doPower(power: Double) {
        if(currentNumberString == "Error")
            return
        currentOperation = CalculatorOperation.Power
        firstNumber = parseCurrentString()
        toCurrentString(power)
        doCurrentOperation()
    }

    fun doLog(base: Double) {
        if(currentNumberString == "Error")
            return
        currentOperation = CalculatorOperation.Log
        firstNumber = parseCurrentString()
        toCurrentString(base)
        doCurrentOperation()
    }

    fun doFactorial() {
        if(currentNumberString == "Error")
            return
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
    }

    fun doSin() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString()
        toCurrentString(sin(num))
    }

    fun doCos() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString()
        toCurrentString(cos(num))
    }

    fun doTan() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString()
        if(num == 90.0 || num == 270.0) {
            updateStringAndText("Error")
        } else {
            toCurrentString(tan(num))
        }
    }

    fun doArcsin() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString()
        toCurrentString(asin(num))
    }

    fun doArccos() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString()
        toCurrentString(acos(num))
    }

    fun doArctan() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString()
        toCurrentString(atan(num))
    }

    fun doPercent() {
        if(currentNumberString == "Error")
            return
        val num = parseCurrentString() / 100.0
        toCurrentString(num)
    }

    fun buttonOperation(operation: CalculatorOperation) {
        if(currentNumberString == "Error")
            return
        if(currentNumberString.isNotEmpty()) {
            doCurrentOperation()
            firstNumber = parseCurrentString()
            preview=true
        }
        currentOperation = operation
    }

    fun doInstantOperation(operation: InstantOperation) {
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
            else -> return
        }
    }

    fun updateMemory(delta: Double = 0.0, newMemory: Double = memory) {
        memory = newMemory
        memory += delta
        val buttonMR: Button? = findViewById(R.id.buttonMR)
        buttonMR?.setTextColor(resources.getColor(R.color.colorPrimary))
        if(memory != 0.0) {
            buttonMR?.setTextColor(resources.getColor(R.color.colorAccent))
        }
    }

    fun doCurrentOperation() {
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
        currentOperation = CalculatorOperation.None
        preview = true
    }

    fun setMode(mode: CalculatorMode) {
        val basicBlock: View = findViewById(R.id.fragmentBasicBlock)
        val scientificBlock: View? = findViewById(R.id.fragmentScientificBlock)
        val menuItem: MenuItem? = menu?.findItem(R.id.buttonSwitchMode)
        currentMode = mode
        menuItem?.setVisible(true)
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
                menuItem?.setVisible(false)
            }
        }
    }

    fun inputNumber(symbol: String) {
        if(symbol == "C") {
            currentOperation = CalculatorOperation.None
            updateStringAndText("", "0")
            preview = false
            return
        }
        if(currentNumberString == "Error")
            return
        if(preview) {
            updateStringAndText("", "0")
            preview = false
        }
        if(currentNumberString.length == 99999) { //FIXME handle landscape and set length (not hardcoded!)
            return
        } else if(currentNumberString.isEmpty()) {
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
                if(currentNumberString.length == 0)
                    updateStringAndText("", "0")
            } else if((symbol == "." && !currentNumberString.contains(symbol)) || symbol.isDigitsOnly()) {
                currentNumberString += symbol
            }
        }
        binding.mainTextView.text = currentNumberString
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
            when(currentMode) {
                CalculatorMode.PortraitBasic -> setMode(CalculatorMode.PortraitScientific)
                CalculatorMode.PortraitScientific -> setMode(CalculatorMode.PortraitBasic)
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
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

    override fun onStart() {
        super.onStart()
    }
}
