package scientifik.kmath.functions

import scientifik.kmath.operations.Ring
import scientifik.kmath.operations.Space
import kotlin.math.max
import kotlin.math.pow

/**
 * Polynomial coefficients without fixation on specific context they are applied to
 * @param coefficients constant is the leftmost coefficient
 */
inline class Polynomial<T : Any>(val coefficients: List<T>) {
    constructor(vararg coefficients: T) : this(coefficients.toList())
}

fun Polynomial<Double>.value() =
    coefficients.reduceIndexed { index: Int, acc: Double, d: Double -> acc + d.pow(index) }


fun <T : Any, C : Ring<T>> Polynomial<T>.value(ring: C, arg: T): T = ring.run {
    if (coefficients.isEmpty()) return@run zero
    var res = coefficients.first()
    var powerArg = arg
    for (index in 1 until coefficients.size) {
        res += coefficients[index] * powerArg
        //recalculating power on each step to avoid power costs on long polynomials
        powerArg *= arg
    }
    return@run res
}

/**
 * Represent a polynomial as a context-dependent function
 */
fun <T : Any, C : Ring<T>> Polynomial<T>.asMathFunction(): MathFunction<T, out C, T> = object :
    MathFunction<T, C, T> {
    override fun C.invoke(arg: T): T = value(this, arg)
}

/**
 * Represent the polynomial as a regular context-less function
 */
fun <T : Any, C : Ring<T>> Polynomial<T>.asFunction(ring: C): (T) -> T = { value(ring, it) }

/**
 * An algebra for polynomials
 */
class PolynomialSpace<T : Any, C : Ring<T>>(val ring: C) : Space<Polynomial<T>> {

    override fun add(a: Polynomial<T>, b: Polynomial<T>): Polynomial<T> {
        val dim = max(a.coefficients.size, b.coefficients.size)
        ring.run {
            return Polynomial(List(dim) { index ->
                a.coefficients.getOrElse(index) { zero } + b.coefficients.getOrElse(index) { zero }
            })
        }
    }

    override fun multiply(a: Polynomial<T>, k: Number): Polynomial<T> {
        ring.run {
            return Polynomial(List(a.coefficients.size) { index -> a.coefficients[index] * k })
        }
    }

    override val zero: Polynomial<T> =
        Polynomial(emptyList())

    operator fun Polynomial<T>.invoke(arg: T): T = value(ring, arg)
}

fun <T : Any, C : Ring<T>, R> C.polynomial(block: PolynomialSpace<T, C>.() -> R): R {
    return PolynomialSpace(this).run(block)
}