package club.itstudio.im.controller

import club.itstudio.im.config.LoginRequiredHandler
import club.itstudio.im.core.annotation.LoginRequired
import club.itstudio.im.core.exception.PermissionError
import club.itstudio.im.core.exception.SessionInvalid
import club.itstudio.im.core.util.Result
import club.itstudio.im.core.util.ResultUtil
import club.itstudio.im.entity.GroupMember
import club.itstudio.im.entity.Message
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/msg")
class MessageController {

    // 获取个人消息历史列表
    @LoginRequired
    @GetMapping("/single/list")
    fun getMsgList(@RequestParam userID: Int,
                   @RequestParam time: Long,
                   @RequestHeader("Authorization") auth: String): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()
        return ResultUtil.success(Message.getSingleMsgListByTime(time, userID, userID))
    }

    // 获取群消息历史列表
    @LoginRequired
    @GetMapping("/group/list")
    fun getGroupMsgList(@RequestParam groupID: Int,
                   @RequestParam time: Long,
                   @RequestHeader("Authorization") auth: String): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()
        if (!GroupMember.checkInGroup(groupID, userID)) throw PermissionError()
        return ResultUtil.success(Message.getGroupMsgListByTime(time, groupID))
    }

}