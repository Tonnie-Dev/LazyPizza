package com.tonyxlab.lazypizza.utils

fun Double.toPrice():String {

    return if (this%1.0 == 0.0)
        "$${this.toInt()}"
    else
        "$${"%.2f".format(this)}"

}
