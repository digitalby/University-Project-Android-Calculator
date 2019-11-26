package me.digitalby.lr2

interface CalculatorEngineListener {
    fun onDisplayStringChanged(value: String)
    fun onCurrentOperationChanged(operation: CalculatorOperation)
    fun onMemoryChanged(value: Double)
}