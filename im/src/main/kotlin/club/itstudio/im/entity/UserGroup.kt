package club.itstudio.im.entity

import club.itstudio.im.core.exception.ITOUCException
import club.itstudio.im.util.Bean
import club.itstudio.itouc.core.util.toStr
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.lang.Exception
import java.sql.ResultSet
import java.sql.Statement

/**
 * 用户创建群
 */
data class UserGroup (
        val id: Int,
        val creatorID: Int,
        val name: String,
        val time: Long,
        val picture: String
){

   companion object {
       // 插入组
       fun createGroup(createID: Int, name: String, picture: String): Int{
           val sql = "INSERT INTO ins_api_usergroup(creater, name, time, picture) VALUES(?, ?, ?, ?)"
           val keyHolder = GeneratedKeyHolder()
           Bean.jdbcT().update({
               val ps = it.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
               ps.setInt(1, createID)
               ps.setString(2, name)
               ps.setLong(3, System.currentTimeMillis())
               ps.setString(4, picture)
               ps
           }, keyHolder)
           return keyHolder.key!!.toInt()
       }

       // 根据创建者获取组
       fun getListByCreater(createID: Int): List<UserGroup> {
           val sql = "SELECT * FROM ins_api_usergroup WHERE creater=?"
           return Bean.jdbcT().query(sql, UserGroupMapper(), createID)
       }

       // 根据ID获取组
       fun getByID(id: Int): UserGroup {
           val sql = "SELECT * FROM ins_api_usergroup WHERE id=?"
           val userGroup = Bean.jdbcT().queryForObject(sql, UserGroupMapper(), id)
           return userGroup ?: throw ITOUCException("不存在ID=$id 的群聊")
       }

       // 根据ID列表获取群聊列表
       fun getByIDList(ids: List<Int>): List<UserGroup> {
           if (ids.isEmpty()) return emptyList()
           val sql = "SELECT * FROM ins_api_usergroup WHERE id in (${ids.toStr()})"
           return Bean.jdbcT().query(sql, UserGroupMapper())
       }

       // 根据ID删除群
       fun deleteByID(id: Int) {
           val sql = "DELETE FROM ins_api_usergroup WHERE id=?"
           val userGroup = Bean.jdbcT().update(sql, id)
       }
   }
}

class UserGroupMapper: RowMapper<UserGroup> {
    override fun mapRow(p0: ResultSet, p1: Int): UserGroup? {
        val id = p0.getInt("id")
        val createID = p0.getInt("creater")
        val name = p0.getString("name")
        val time = p0.getLong("time")
        val picture = p0.getString("picture")
        return UserGroup(id, createID, name, time, picture)
    }
}