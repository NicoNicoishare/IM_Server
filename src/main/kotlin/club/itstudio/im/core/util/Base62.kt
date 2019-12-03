package club.itstudio.im.core.util

import java.util.Stack

/**
 * 62进制与10进制转换
 */

class Base62 {
    companion object {

        fun from10To62(number: Long): String {
            var rest: Long? = number
            val stack = Stack<Char>()
            val result = StringBuilder(0)
            while (rest != 0L) {
                stack.add(charSands[(rest!! - rest / 62 * 62).toInt()])
                rest /= 62
            }
            while (!stack.isEmpty()) {
                result.append(stack.pop())
            }
            return result.toString()

        }

        fun from62To10(sixty_str: String): Long {
            var multiple = 1L
            var result: Long = 0L
            var c: Char?
            for (i in 0 until sixty_str.length) {
                c = sixty_str[sixty_str.length - i - 1]
                result += (valueOf62(c) * multiple)
                multiple *= 62L
            }
            return result
        }

        private fun valueOf62(c: Char?): Int {
            for (i in charSands.indices) {
                if (c == charSands[i]) {
                    return i
                }
            }
            return -1
        }
    }
}


