package club.itstudio.im.controller

import club.itstudio.im.config.LoginRequiredHandler
import club.itstudio.im.core.annotation.LoginRequired
import club.itstudio.im.core.exception.PermissionError
import club.itstudio.im.core.util.Result
import club.itstudio.im.core.util.ResultUtil
import club.itstudio.im.entity.Message
import club.itstudio.im.entity.UnreadMsgAndCount
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/unread")
class UnreadMsgController {

    /**
     * 获取未读消息列表
     */
    @LoginRequired
    @GetMapping("/list")
    fun getUnreadMsgList(@RequestHeader("Authorization") auth: String): Result<Any> {
        val tokenKey = auth.substring(6)
        val unreadList = UnreadMsgAndCount.getListByToUser(LoginRequiredHandler.LoginToken.tokenMap[tokenKey] ?: 0)
        val msgIDList = mutableListOf<Int>()
        for (unreadMsgAndCount in unreadList) {
            msgIDList.add(unreadMsgAndCount.messageID)
        }
        val msgList = Message.getMsgListByIDList(msgIDList)
        val data = mapOf(
                "msgList" to msgList,
                "unreadList" to unreadList
        )
        return ResultUtil.success(data)
    }

    /**
     * 删除未读消息，等价于标为已读
     */
    @DeleteMapping
    @LoginRequired
    fun deleteUnreadMsg(@RequestHeader("Authorization") auth: String,
                        @RequestParam unreadID: Int): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)]
        val unreadMsg = UnreadMsgAndCount.getByID(unreadID)
        if (unreadMsg.to == userID) {
            UnreadMsgAndCount.deleteRecordByID(unreadID)
            return ResultUtil.success()
        }else {
            throw PermissionError()
        }
    }


}