package com.example.lr2

import android.os.Bundle
import androidx.core.text.isDigitsOnly
import kotlin.math.*

class CalculatorEngine(private val listener: CalculatorEngineListener?) {

    private var displayedString: String = "0"
    set(value) {
        field = value
        listener?.onDisplayStringChanged(field)
    }
    private var currentNumberString = ""
    private var currentOperation: CalculatorOperation = CalculatorOperation.None
    set(value) {
        field = value
        listener?.onCurrentOperationChanged(field)
    }
    private var firstNumber: Double? = null
    private var memory: Double = 0.0
    set(value) {
        field = value
        listener?.onMemoryChanged(field)
    }
    private var preview: Boolean = false
    private var lastOperation: CalculatorOperation = CalculatorOperation.None
    private var lastOperationNumber: Double = 0.0


    private fun parseCurrentString(): Double {
        if(currentNumberString.isEmpty() || currentNumberString == "0.") {
            return 0.0
        }
        return currentNumberString.toDouble()
    }

    private fun memoryToCurrentString() {
        toCurrentString(memory)
    }

    private fun doMemoryPlus(multiplier: Double = 1.0, delta: Double = parseCurrentString()) {
        updateMemory(delta * multiplier)
    }

    private fun toCurrentString(num: Double) {
        val ret = num.toString()
        if(num.isInfinite() || num.isNaN())
            updateStrings("Error")
        else if(num % 1 == 0.0) {
            var newValue = num.toString()
            newValue = newValue.replace(Regex("\\.0*$"), "")
            updateStrings(newValue)
        }
        else
            updateStrings(ret)
    }

    private fun updateStrings(newCurrentString: String, newDisplayedString: String = newCurrentString) {
        currentNumberString = newCurrentString
        displayedString = newDisplayedString
    }

    fun doEquality() {
        if(currentNumberString == "Error")
            return
        if(currentOperation != CalculatorOperation.None)
            doCurrentOperation()
        else {
            if(lastOperation != CalculatorOperation.None) {
                currentOperation = lastOperation
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
        displayedString = currentNumberString
    }

    private fun doPower(power: Double) {
        currentOperation = CalculatorOperation.Power
        firstNumber = parseCurrentString()
        toCurrentString(power)
        doCurrentOperation()
    }

    private fun doLog(base: Double) {
        currentOperation = CalculatorOperation.Log
        firstNumber = parseCurrentString()
        toCurrentString(base)
        doCurrentOperation()
    }

    private fun doFactorial() {
        val num = parseCurrentString()
        if(num == 0.0)
            toCurrentString(1.0)
        else if(num % 1 != 0.0) {
            updateStrings("Error")
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

    private fun toRad(deg: Double): Double {
        return deg * PI / 180.0
    }

    private fun toDeg(rad: Double): Double {
        return rad * 180.0 / PI
    }

    private fun doSin() {
        val num = toRad(parseCurrentString())
        toCurrentString(sin(num))
        preview = true
    }

    private fun doCos() {
        val num = toRad(parseCurrentString())
        toCurrentString(cos(num))
        preview = true
    }

    private fun doTan() {
        val num = parseCurrentString()
        if(num == 90.0 || num == 270.0) {
            updateStrings("Error")
        } else {
            toCurrentString(tan(toRad(num)))
        }
        preview = true
    }

    private fun doArcsin() {
        val num = parseCurrentString()
        toCurrentString(toDeg(asin(num)))
        preview = true
    }

    private fun doArccos() {
        val num = parseCurrentString()
        toCurrentString(toDeg(acos(num)))
        preview = true
    }

    private fun doArctan() {
        val num = parseCurrentString()
        toCurrentString(toDeg(atan(num)))
        preview = true
    }

    private fun doPercent() {
        val num = parseCurrentString() / 100.0
        toCurrentString(num)
        preview = true
    }

    fun doButtonOperation(operation: CalculatorOperation) {
        if(currentNumberString == "Error")
            return
        if(currentNumberString.isNotEmpty()) {
            if (!preview || firstNumber == null) {
                doCurrentOperation()
                firstNumber = parseCurrentString()
                preview = true
            }
        }
        currentOperation = operation
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
            InstantOperation.Backspace -> inputNumber("⌫")
            InstantOperation.Factorial -> doFactorial()
            InstantOperation.Power3 -> doPower(3.0)
            InstantOperation.Power2 -> doPower(2.0)
            InstantOperation.PowerInvert -> doPower(-1.0)
            InstantOperation.Sqrt -> doPower(1.0/2.0)
            InstantOperation.Ln -> doLog(E)
            InstantOperation.Log10 -> doLog(10.0)
            InstantOperation.Log2 -> doLog(2.0)
            InstantOperation.Cubrt -> doPower(1.0/3.0)
            InstantOperation.MR -> memoryToCurrentString()
            InstantOperation.MMinus -> doMemoryPlus(-1.0)
            InstantOperation.MPlus -> doMemoryPlus()
            InstantOperation.MC -> updateMemory(0.0, 0.0)
        }
    }

    private fun updateMemory(delta: Double = 0.0, newMemory: Double = memory) {
        memory = newMemory
        memory += delta
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
                    updateStrings("Error")
                } else {
                    result = firstNumber!! / secondNumber
                }
            }
            CalculatorOperation.Exponent -> {
                result = firstNumber!! * 10.0.pow(secondNumber)
            }
            CalculatorOperation.Log -> {
                if(secondNumber <= 0.0 || secondNumber == 1.0 || firstNumber!! <= 0.0)
                    updateStrings("Error")
                else
                    result = log(firstNumber!!, secondNumber)
            }
            CalculatorOperation.Power -> {
                result = firstNumber!!.pow(secondNumber)
            }
            CalculatorOperation.Root -> {
                if(secondNumber == 0.0)
                    updateStrings("Error")
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

    fun inputNumber(symbol: String) {
        if(symbol == "AC") {
            currentOperation = CalculatorOperation.None
            firstNumber = null
            updateStrings("", "0")
            preview = false
            lastOperation = CalculatorOperation.None
            lastOperationNumber = 0.0
            return
        }
        if(currentNumberString == "Error")
            return
        if(preview) {
            updateStrings("", "0")
            preview = false
        }
        if(currentNumberString.isEmpty()) {
            when (symbol) {
                "." -> currentNumberString = "0."
                "0" -> {
                    displayedString = "0"
                    return
                }
                "⌫" -> return
                else -> currentNumberString += symbol
            }
        } else {
            if(symbol == "⌫") {
                currentNumberString = currentNumberString.removeRange(currentNumberString.length-1,currentNumberString.length)
                if(currentNumberString.isEmpty())
                    updateStrings("", "0")
            } else if((symbol == "." && !currentNumberString.contains(symbol)) || symbol.isDigitsOnly()) {
                currentNumberString += symbol
            }
        }
        displayedString = currentNumberString
    }

    fun putAllToBundle(bundle: Bundle): Bundle {
        bundle.putString("currentNumberString", currentNumberString)
        bundle.putSerializable("currentOperation", currentOperation)
        if(firstNumber != null)
            bundle.putDouble("firstNumber", firstNumber!!)
        bundle.putDouble("memory", memory)
        bundle.putBoolean("preview", preview)
        bundle.putSerializable("lastOperation", lastOperation)
        bundle.putDouble("lastOperationNumber", lastOperationNumber)
        return bundle
    }

    fun getAllFromBundle(bundle: Bundle) {
        doButtonOperation(bundle.getSerializable("currentOperation") as CalculatorOperation)
        updateStrings(bundle.getString("currentNumberString")!!)
        firstNumber = bundle.getDouble("firstNumber")
        updateMemory(0.0, bundle.getDouble("memory"))
        preview = bundle.getBoolean("preview")
        lastOperation = bundle.getSerializable("lastOperation") as CalculatorOperation
        lastOperationNumber = bundle.getDouble("lastOperationNumber")
        if(currentNumberString.isEmpty())
            updateStrings("", "0")
    }
}