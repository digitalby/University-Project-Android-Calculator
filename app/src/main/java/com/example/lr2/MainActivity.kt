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
        binding.buttonPlusMinus.setOnClickListener{ doPlusMinus() }
        binding.buttonPercent.setOnClickListener { doPercent() }
    }
    
    //TODO instant operations
    //TODO function for making string (both normal and short) with and without outputting

    fun parseCurrentString(): Float {
        if(currentNumberString.isNullOrEmpty() || currentNumberString == "0.") {
            return 0f
        }
        return currentNumberString.toFloat()
    }

    fun toCurrentString(num: Float) {
        var ret = num.toString()
        //FIXME long strings and big numbers are not handled!

        //TODO handle numbers that are too long (>=1 billion) or with too long decimal parts. don't forget MINUS!
        //TODO handle both portrait and landscape (introduce a new variable?)
        currentNumberString = ret
        binding.mainTextView.text = currentNumberString
    }

    fun doPlusMinus() {
        if(currentNumberString.isNullOrEmpty() || currentNumberString == "Error")
            return
        if(currentNumberString.contains("-")) {
            currentNumberString = currentNumberString.replace("-","")
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
        if(!currentNumberString.isNullOrEmpty()) {
            doCurrentOperation()
            firstNumber = parseCurrentString()
            currentNumberString = ""
            binding.mainTextView.text = "0"
        }
        currentOperation = operation
    }

    fun doCurrentOperation() {
        if(currentNumberString == "Error")
            return
        val secondNumber = parseCurrentString()
        var result: Float = 0f
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
                    currentNumberString = "Error"
                    binding.mainTextView.text = currentNumberString
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
        if(symbol == "C") {
            currentOperation = CalculatorOperation.None
            currentNumberString = ""
            binding.mainTextView.text = "0"
            return
        }
        if(currentNumberString == "Error")
            return
        if(currentNumberString.length == 8) { //TODO handle landscape and don't hardcode length
            return
        } else if(currentNumberString.isNullOrEmpty()) {
            if(symbol == ".") {
                currentNumberString = "0."
            } else if(symbol == "0") {
                binding.mainTextView.text = "0"
                return
            } else {
                currentNumberString += symbol
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
