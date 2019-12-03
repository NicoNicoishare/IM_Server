package club.itstudio.im.controller

import club.itstudio.im.config.LoginRequiredHandler
import club.itstudio.im.core.annotation.LoginRequired
import club.itstudio.im.core.exception.ITOUCException
import club.itstudio.im.core.exception.PermissionError
import club.itstudio.im.core.exception.SessionInvalid
import club.itstudio.im.core.util.Result
import club.itstudio.im.core.util.ResultUtil
import club.itstudio.im.entity.GroupMember
import club.itstudio.im.entity.User
import club.itstudio.im.entity.UserGroup
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/group")
class GroupController{

    // 创建群聊
    @PostMapping
    @LoginRequired
    fun createGroup(@RequestHeader("Authorization") auth: String,
                    @RequestParam name: String,
                    @RequestParam picture: String): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()
        val groupID = UserGroup.createGroup(userID, name, picture)
        GroupMember.intoGroup(groupID, userID)
        return ResultUtil.success()
    }

    // 删除群聊
    @DeleteMapping
    @LoginRequired
    fun deleteGroup(@RequestHeader("Authorization") auth: String,
                    @RequestParam groupID: Int): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()
        val group = UserGroup.getByID(groupID)
        if (group.creatorID != userID) throw PermissionError()

        UserGroup.deleteByID(groupID)
        GroupMember.deleteGroup(groupID)
        return ResultUtil.success()
    }

    // 进群
    @PutMapping
    @LoginRequired
    fun intoGroup(@RequestHeader("Authorization") auth: String,
                  @RequestParam groupID: Int): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()

        // 检测群是否存在
        try {
            UserGroup.getByID(groupID)
        }catch (e: Exception) {
            throw ITOUCException("群聊不存在")
        }
        GroupMember.intoGroup(groupID, userID)
        return ResultUtil.success()
    }

    // 获取群成员列表
    @LoginRequired
    @GetMapping("/members")
    fun getGroupMembers(@RequestParam groupID: Int): Result<Any> {
        val memberIDList = GroupMember.getMembersByGroupID(groupID)
        val userIDList = mutableListOf<Int>()
        memberIDList.forEach { userIDList.add(it.userID) }
        val userList = User.getUserListByIDList(userIDList)
        return ResultUtil.success(userList)
    }

    // 退群
    @LoginRequired
    @DeleteMapping("/exit")
    fun exitGroup(@RequestHeader("Authorization") auth: String,
                  @RequestParam groupID: Int): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()
        try {
            val group = UserGroup.getByID(groupID)
            // 创建者退群 解散群
            if (group.creatorID == userID) {
                UserGroup.deleteByID(groupID)
                GroupMember.deleteGroup(groupID)
            }else {// 仅退群
                GroupMember.exitGroup(groupID, userID)
            }
        }catch (e: Exception) {
            throw e
        }
        return ResultUtil.success()
    }

    // 获取群列表
    @LoginRequired
    @GetMapping("/list")
    fun getGroupList(@RequestHeader("Authorization") auth: String): Result<Any> {
        val userID = LoginRequiredHandler.LoginToken.tokenMap[auth.substring(6)] ?: throw SessionInvalid()
        val groupList = GroupMember.getGroupByUserID(userID)
        val idList = mutableListOf<Int>()
        groupList.forEach { idList.add(it.groupID) }
        return ResultUtil.success(UserGroup.getByIDList(idList))
    }


}