package club.itstudio.im.core.exception

/**
 * 连接异常，一般出现在与教务系统的连接处
 */
class ConnectFailure(
        override var status: Int = 402,
        override var message: String = "连接失败"
): ITOUCException()