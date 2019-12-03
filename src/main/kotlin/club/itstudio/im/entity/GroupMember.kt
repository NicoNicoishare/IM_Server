package club.itstudio.im.entity

import club.itstudio.im.util.Bean
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

/**
 * 群成员
 */
data class GroupMember (
        val groupID: Int,
        val userID: Int
){
    companion object {
        // 根据群ID获取群成员
        fun getMembersByGroupID(groupID: Int): List<GroupMember>{
            val sql = "SELECT * FROM ins_api_groupmember WHERE groupID=?"
            return Bean.jdbcT().query(sql, GroupMemberMapper(), groupID)
        }

        // 检查用户是否在群中
        fun checkInGroup(groupID: Int, userID: Int): Boolean {
            val sql = "SELECT * FROM ins_api_groupmember WHERE groupID=? AND userID=?"
            return Bean.jdbcT().query(sql, GroupMemberMapper(), groupID, userID).size == 1
        }

        // 进群
        fun intoGroup(groupID: Int, userID: Int): Boolean {
            val sql = "REPLACE INTO ins_api_groupmember(groupID, userID) VALUES(?, ?)"
            return Bean.jdbcT().update(sql, groupID, userID) == 1
        }

        // 退群
        fun exitGroup(groupID: Int, userID: Int): Boolean {
            val sql = "DELETE FROM ins_api_groupmember WHERE groupID=? AND userID=?"
            return Bean.jdbcT().update(sql, groupID, userID) == 1
        }

        // 解散群
        fun deleteGroup(groupID: Int): Boolean {
            val sql = "DELETE FROM ins_api_groupmember WHERE groupID=?"
            return Bean.jdbcT().update(sql, groupID) != 0
        }

        // 根据userID获取群
        fun getGroupByUserID(userID: Int): List<GroupMember> {
            val sql = "SELECT * FROM ins_api_groupmember WHERE userID=?"
            return Bean.jdbcT().query(sql, GroupMemberMapper(), userID)
        }
    }
}

class GroupMemberMapper: RowMapper<GroupMember> {
    override fun mapRow(p0: ResultSet, p1: Int): GroupMember? {
        val groupID = p0.getInt("groupID")
        val userID = p0.getInt("userID")
        return GroupMember(groupID, userID)
    }
}