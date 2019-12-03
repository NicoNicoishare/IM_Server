package club.itstudio.itouc.core.util

// 去除List的toString的[]
fun List<Any>.toStr(): String{
    if (this.isEmpty()) return ""
    val s = "$this"
    return s.substring(1, s.length - 1)
}


