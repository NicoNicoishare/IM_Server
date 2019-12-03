package club.itstudio.im.core.util

import club.itstudio.im.core.util.HASH

/**
 * String的扩展函数
 */
fun String.md5(): String =  HASH.md5(this)
fun String.sha1(): String = HASH.sha1(this)
fun String.sha256(): String = HASH.sha256(this)


