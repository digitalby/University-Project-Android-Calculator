package com.example.lr2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.ActivityMainBinding
import kotlin.math.E
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.roundToLong

enum class CalculatorOperation {
    None,
    Add,
    Subtract,
    Multiply,
    Divide
}

enum class InstantOperation {
    Percent,
    PlusMinus,
    InsertPi,
    InsertE,
    Backspace,
    Sin,
    Cos,
    Tan
}

class MainActivity : AppCompatActivity() {

    var currentNumberString = ""
    var currentOperation: CalculatorOperation = CalculatorOperation.None
    var firstNumber: Double? = null
    var memory: Double = 0.0; private set
    lateinit var binding: ActivityMainBinding
    
    //TODO function for making string (both normal and short)

    fun parseCurrentString(): Double {
        if(currentNumberString.isEmpty() || currentNumberString == "0.") {
            return 0.0
        }
        return currentNumberString.toDouble()
    }

    fun toCurrentString(num: Double) {
        val ret = num.toString()
        //FIXME long strings and big numbers are not handled!

        //FIXME no trailing zeros allowed!

        //TODO handle numbers that are too long (>=1 billion) or with too long decimal parts. don't forget MINUS!
        //TODO handle both portrait and landscape (introduce a new variable?)
        updateStringAndText(ret)
    }

    fun updateStringAndText(str: String, textViewText: String = str) {
        currentNumberString = str
        binding.mainTextView.text = textViewText
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

    fun doPercent() {
        if(currentNumberString == "Error")
            return
        var num = parseCurrentString()
        num /= 100.0
        toCurrentString(num)
    }

    fun buttonOperation(operation: CalculatorOperation) {
        if(currentNumberString == "Error")
            return
        if(!currentNumberString.isEmpty()) {
            doCurrentOperation()
            firstNumber = parseCurrentString()
            updateStringAndText("", "0")
        }
        currentOperation = operation
    }

    fun doInstantOperation(operation: InstantOperation) {
        when(operation) {
            InstantOperation.Percent -> doPercent()
            InstantOperation.PlusMinus -> doPlusMinus()
            InstantOperation.InsertPi -> toCurrentString(PI)
            InstantOperation.InsertE -> toCurrentString(E)
            InstantOperation.Backspace -> inputNumber("Backspace")
            else -> return
        }
    }

    fun updateMemory(delta: Double = 0.0, newMemory: Double = memory) {
        memory = newMemory
        memory += delta
    }

    fun doCurrentOperation() {
        if(currentNumberString == "Error")
            return
        val secondNumber = parseCurrentString()
        var result = 0.0
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
        }
        if(currentNumberString != "Error")
            toCurrentString(result)
        firstNumber = null
        currentOperation = CalculatorOperation.None
    }

    fun inputNumber(symbol: String) {
        //FIXME bug: if EXPONENT is present, disallow entry of numbers, or implement 'preview' mode
        if(symbol == "C") {
            currentOperation = CalculatorOperation.None
            updateStringAndText("", "0")
            return
        }
        if(currentNumberString == "Error")
            return
        if(currentNumberString.length == 8) { //TODO handle landscape and don't hardcode length
            return
        } else if(currentNumberString.isEmpty()) {
            when (symbol) {
                "." -> currentNumberString = "0."
                "0" -> {
                    binding.mainTextView.text = "0"
                    return
                }
                else -> currentNumberString += symbol
            }
        } else {
            if(symbol == "Backspace") {
                currentNumberString = currentNumberString.removeRange(currentNumberString.length-1,currentNumberString.length)
            } else if((symbol == "." && !currentNumberString.contains(symbol)) || symbol.isDigitsOnly()) {
                currentNumberString += symbol
            }
        }
        binding.mainTextView.text = currentNumberString
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        inputNumber("C")
    }
}
