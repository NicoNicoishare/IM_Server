package club.itstudio.im.core.exception

import club.itstudio.im.core.exception.ITOUCException

class PermissionError (
        override var status: Int = 405,
        override var message: String = "权限错误"
): ITOUCException()