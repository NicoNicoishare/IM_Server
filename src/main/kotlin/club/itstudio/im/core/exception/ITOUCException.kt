package club.itstudio.im.core.exception

import java.lang.RuntimeException

/**
 * 异常基类
 */
open class ITOUCException (
        override var message: String = "操作失败",
        open var status: Int = 400
): RuntimeException()