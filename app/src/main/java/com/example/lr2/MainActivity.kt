package com.example.lr2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.ActivityMainBinding
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
    Sin,
    Cos,
    Tan
}

class MainActivity : AppCompatActivity() {

    var currentNumberString = ""
    var currentOperation: CalculatorOperation = CalculatorOperation.None
    var firstNumber: Float? = null
    lateinit var binding: ActivityMainBinding

    fun setButtonActions() {
        binding.button0.setOnClickListener{ inputNumber("0") }
        binding.button1.setOnClickListener{ inputNumber("1") }
        binding.button2.setOnClickListener{ inputNumber("2") }
        binding.button3.setOnClickListener{ inputNumber("3") }
        binding.button4.setOnClickListener{ inputNumber("4") }
        binding.button5.setOnClickListener{ inputNumber("5") }
        binding.button6.setOnClickListener{ inputNumber("6") }
        binding.button7.setOnClickListener{ inputNumber("7") }
        binding.button8.setOnClickListener{ inputNumber("8") }
        binding.button9.setOnClickListener{ inputNumber("9") }
        binding.buttonDecimal.setOnClickListener{ inputNumber(".") }
        binding.buttonC.setOnClickListener{ inputNumber("C") }
        binding.buttonPlus.setOnClickListener{ buttonOperation(CalculatorOperation.Add) }
        binding.buttonMinus.setOnClickListener{ buttonOperation(CalculatorOperation.Subtract) }
        binding.buttonMultiply.setOnClickListener{ buttonOperation(CalculatorOperation.Multiply) }
        binding.buttonDivide.setOnClickListener{ buttonOperation(CalculatorOperation.Divide) }
        binding.buttonEquals.setOnClickListener{ doCurrentOperation() }
        binding.buttonPlusMinus.setOnClickListener{ doInstantOperation(InstantOperation.PlusMinus) }
        binding.buttonPercent.setOnClickListener { doInstantOperation(InstantOperation.Percent) }
    }
    
    //TODO function for making string (both normal and short)

    fun parseCurrentString(): Float {
        if(currentNumberString.isEmpty() || currentNumberString == "0.") {
            return 0f
        }
        return currentNumberString.toFloat()
    }

    fun toCurrentString(num: Float) {
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
            if(parseCurrentString() != 0f) {
                currentNumberString = "-${currentNumberString}"
            }
        }
        binding.mainTextView.text = currentNumberString
    }

    fun doPercent() {
        if(currentNumberString == "Error")
            return
        var num = parseCurrentString()
        num /= 100f
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
            else -> return
        }
    }

    fun doCurrentOperation() {
        if(currentNumberString == "Error")
            return
        val secondNumber = parseCurrentString()
        var result = 0f
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
                if(secondNumber == 0f) {
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
        //FIXME bug: if E is present, disallow entry of numbers, or implement 'preview' mode
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
            if((symbol == "." && !currentNumberString.contains(symbol)) || symbol.isDigitsOnly()) {
                currentNumberString += symbol
            }
        }
        binding.mainTextView.text = currentNumberString
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        inputNumber("C")
        setButtonActions()
    }
}
