package club.itstudio.im.core.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * json 序列化和反序列化工具
 */

val mapper = jacksonObjectMapper()

fun toJson(`object`: Any): String = mapper.writeValueAsString(`object`)



