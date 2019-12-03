package club.itstudio.itouc.core.util

/**
 * 正则工具
 */
object RE{
    /**
     * 判断是否含有特殊字符
     */
    fun hasSpecialChar(input: String): Boolean {
        val regex = Regex("""[`~!@#${'$'}^&*()=|{}':;',\[\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？"]""")
        return regex.containsMatchIn(input)
    }

    /**
     * 判断手机号格式
     */
    fun phone(input: String): Boolean {
        val regex = Regex("^[1]([3-9])[0-9]{9}$")
        return regex.matches(input)
    }
}
