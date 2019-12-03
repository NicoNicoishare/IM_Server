package club.itstudio.im.entity

import club.itstudio.im.util.Bean
import club.itstudio.itouc.core.util.toStr
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

data class User(val id: Int,
                 val headPicture: String,
                 val userName: String,
                 val nickName: String
                 ){

    companion object {
        fun getUserListByIDList(ids: List<Int>): List<User> {
            if (ids.isEmpty()) return emptyList()
            val sql = "SELECT id, profile_picture, username, nickname FROM users_user WHERE id in (${ids.toStr()})"
            return Bean.jdbcT().query(sql, UserMapper())
        }
    }
}

class UserMapper: RowMapper<User> {
    override fun mapRow(p0: ResultSet, p1: Int): User? {
        val id = p0.getInt("id")
        val headPicture = p0.getString("profile_picture")
        val userName = p0.getString("username")
        val nickName = p0.getString("nickname")
        return User(id, headPicture, userName, nickName)
    }
}