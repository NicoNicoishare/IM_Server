package club.itstudio.im.core.exception

import club.itstudio.im.core.exception.ITOUCException

class UploadFailure (override var status: Int = 405,
                     override var message: String = "上传失败"): ITOUCException()