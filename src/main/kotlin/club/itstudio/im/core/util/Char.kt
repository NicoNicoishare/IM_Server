package club.itstudio.im.core.util

enum class CharType {
    Digit,      // 数字
    Capital,    // 大写字母
    Lowercase,  // 小写字母
    Others      // 其他字符
}

fun Char.charType(): CharType {
    return when(this) {
        in '0'..'9' -> CharType.Digit       // 数字
        in 'A'..'Z' -> CharType.Capital     // 大写字母
        in 'a'..'z' -> CharType.Lowercase   // 小写字母
        else -> CharType.Others             // 其他字符
    }
}

