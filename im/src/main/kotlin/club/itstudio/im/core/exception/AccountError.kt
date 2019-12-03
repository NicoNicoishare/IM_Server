package club.itstudio.im.core.exception

/**
 * 账号密码错误
 */
class AccountError (
        override var status: Int = 403,
        override var message: String = "账号或密码错误"
): ITOUCException()