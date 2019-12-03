package club.itstudio.im.core.exception

import club.itstudio.im.core.exception.ITOUCException

/**
 * 教务处jsession失效后抛出异常
 */
class SessionInvalid (
        override var status:Int = 401,
        override var message: String = "会话失效"
): ITOUCException()