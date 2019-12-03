package club.itstudio.im.core.util

import java.lang.StringBuilder
import kotlin.random.Random

val charSands = charArrayOf( 'w', 'e', 'r', 't', 'y', 'u', 'S', 'A', 'i',  'P', 'o', 'p', 'Z', 'a', 's', 'd', 'J',  'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'N', 'c', 'v', 'b', 'D', 'n', 'm','q', '0', 'G', '1', '2', 'V', '3', '4',  'H', '5', '6', '7', '8', '9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'F', 'K', 'L',  'X', 'C',  'B', 'M')

val intSands = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')

fun getRandomStrBySands(length: Int, sands: CharArray): String{
    val sb = StringBuilder()
    val sandLength = sands.size
    for (i in 0 until length) {
        val index = Random.nextInt(sandLength)
        sb.append(sands[index])
    }
    return sb.toString()
}

/**
 * 获取指定长度的随机字符串
 */
fun Random.nextStr(length: Int): String = getRandomStrBySands(length, charSands)

/**
 * 获取指定长度的随机数字串
 */
fun Random.nextIntStr(length: Int): String = getRandomStrBySands(length, intSands)



