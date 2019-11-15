package com.example.lr2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import com.example.lr2.databinding.ActivityMainBinding

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
    }

    fun doOperation() {

    }

    fun inputNumber(symbol: String) {
        if(symbol == "C") {
            currentNumberString = ""
            binding.mainTextView.text = "0"
            return
        }
        if(currentNumberString.length == 8) {
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
