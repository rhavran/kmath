package scientifik.kmath.operations

/**
 * A container for trigonometric operations for specific type. Trigonometric operations are limited to semifields.
 *
 * The operations are not exposed to class directly to avoid method bloat but instead are declared in the field.
 * It also allows to override behavior for optional operations.
 */
interface TrigonometricOperations<T> : FieldOperations<T> {
    /**
     * Computes the sine of [arg] .
     */
    fun sin(arg: T): T

    /**
     * Computes the cosine of [arg].
     */
    fun cos(arg: T): T

    /**
     * Computes the tangent of [arg].
     */
    fun tan(arg: T): T

    /**
     * Computes the inverse sine of [arg].
     */
    fun asin(arg: T): T

    /**
     * Computes the inverse cosine of [arg].
     */
    fun acos(arg: T): T

    /**
     * Computes the inverse tangent of [arg].
     */
    fun atan(arg: T): T

    companion object {
        const val SIN_OPERATION = "sin"
        const val COS_OPERATION = "cos"
        const val TAN_OPERATION = "tan"
        const val ASIN_OPERATION = "asin"
        const val ACOS_OPERATION = "acos"
        const val ATAN_OPERATION = "atan"
    }
}

/**
 * Computes the sine of [arg] .
 */
fun <T : MathElement<out TrigonometricOperations<T>>> sin(arg: T): T = arg.context.sin(arg)

/**
 * Computes the cosine of [arg].
 */
fun <T : MathElement<out TrigonometricOperations<T>>> cos(arg: T): T = arg.context.cos(arg)

/**
 * Computes the tangent of [arg].
 */
fun <T : MathElement<out TrigonometricOperations<T>>> tan(arg: T): T = arg.context.tan(arg)

/**
 * Computes the inverse sine of [arg].
 */
fun <T : MathElement<out TrigonometricOperations<T>>> asin(arg: T): T = arg.context.asin(arg)

/**
 * Computes the inverse cosine of [arg].
 */
fun <T : MathElement<out TrigonometricOperations<T>>> acos(arg: T): T = arg.context.acos(arg)

/**
 * Computes the inverse tangent of [arg].
 */
fun <T : MathElement<out TrigonometricOperations<T>>> atan(arg: T): T = arg.context.atan(arg)

/**
 * A container for power operations like square roots and exponentiation.
 */
interface PowerOperations<T> : Algebra<T> {
    /**
     * Raises [arg] to the power [pow].
     */
    fun power(arg: T, pow: Number): T

    /**
     * Computes the positive square root of the value [arg].
     */
    fun sqrt(arg: T): T = power(arg, 0.5)

    /**
     * Raises this value to the power [pow].
     */
    infix fun T.pow(pow: Number) = power(this, pow)

    companion object {
        const val POW_OPERATION = "pow"
        const val SQRT_OPERATION = "sqrt"
    }
}

/**
 * Raises this value to the power [pow].
 */
infix fun <T : MathElement<out PowerOperations<T>>> T.pow(power: Double): T = context.power(this, power)

/**
 * Computes the positive square root of the value [arg].
 */
fun <T : MathElement<out PowerOperations<T>>> sqrt(arg: T): T = arg pow 0.5

/**
 * Raises this value to the power 2.0.
 */
fun <T : MathElement<out PowerOperations<T>>> sqr(arg: T): T = arg pow 2.0

/**
 * A container for operations related to Euler's `e` number.
 */
interface ExponentialOperations<T> : FieldOperations<T>, Ring<T>, PowerOperations<T> {
    /**
     * Returns Euler's number `e` in this algebra.
     */
    val e: T
        get() = exp(one)

    /**
     * Computes Euler's number `e` raised to the power of the value [arg].
     */
    fun exp(arg: T): T

    /**
     * Computes the natural logarithm (base `e`) of the value [arg].
     */
    fun ln(arg: T): T

    /**
     * Computes the hyperbolic sine of [arg].
     */
    fun sinh(arg: T): T = (exp(arg) - exp(-arg)) / 2

    /**
     * Computes the hyperbolic cosine of [arg].
     */
    fun cosh(arg: T): T = (exp(arg) + exp(-arg)) / 2

    /**
     * Computes the hyperbolic tangent of [arg].
     */
    fun tanh(arg: T): T = (exp(arg) - exp(-arg)) / (exp(-arg) + exp(arg))

    /**
     * Computes the inverse hyperbolic sine of [arg].
     */
    fun asinh(arg: T): T = ln(sqrt(arg * arg + one) + arg)

    /**
     * Computes the inverse hyperbolic cosine of [arg].
     */
    fun acosh(arg: T): T = ln(arg + sqrt((arg - one) * (arg + one)))

    /**
     * Computes the inverse hyperbolic tangent of [arg].
     */
    fun atanh(arg: T): T = (ln(arg + one) - ln(one - arg)) / 2

    companion object {
        const val EXP_OPERATION = "exp"
        const val LN_OPERATION = "ln"
        const val SINH_OPERATION = "sinh"
        const val COSH_OPERATION = "cosh"
        const val TANH_OPERATION = "tanh"
        const val ASINH_OPERATION = "asinh"
        const val ACOSH_OPERATION = "acosh"
        const val ATANH_OPERATION = "atanh"
    }
}

/**
 * Computes Euler's number `e` raised to the power of the value [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> exp(arg: T): T = arg.context.exp(arg)

/**
 * Computes the natural logarithm (base `e`) of the value [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> ln(arg: T): T = arg.context.ln(arg)

/**
 * Computes the hyperbolic sine of [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> sinh(arg: T): T = arg.context.sinh(arg)

/**
 * Computes the hyperbolic cosine of [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> cosh(arg: T): T = arg.context.cosh(arg)

/**
 * Computes the hyperbolic tangent of [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> tanh(arg: T): T = arg.context.tanh(arg)

/**
 * Computes the inverse hyperbolic sine of [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> asinh(arg: T): T = arg.context.asinh(arg)

/**
 * Computes the inverse hyperbolic cosine of [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> acosh(arg: T): T = arg.context.acosh(arg)

/**
 * Computes the inverse hyperbolic tangent of [arg].
 */
fun <T : MathElement<out ExponentialOperations<T>>> atanh(arg: T): T = arg.context.atanh(arg)

/**
 * A container for norm functional on element.
 */
interface Norm<in T : Any, out R> {
    /**
     * Computes the norm of [arg] (i.e. absolute value or vector length).
     */
    fun norm(arg: T): R
}

/**
 * Computes the norm of [arg] (i.e. absolute value or vector length).
 */
fun <T : MathElement<out Norm<T, R>>, R> norm(arg: T): R = arg.context.norm(arg)
