package com.tonyxlab.lazypizza.utils

fun <T> Iterable<T>.isSelected(
    element: T,
    idSelector: (T) -> Long
): Boolean = any { idSelector(it) == idSelector(element) }

fun <T> Iterable<T>.counterFor(
    element: T,
    idSelector: (T) -> Long,
    counterSelector: (T) -> Int
): Int = firstOrNull { idSelector(it) == idSelector(element) }
        ?.let(counterSelector)
    ?: 0

