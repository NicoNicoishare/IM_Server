package club.itstudio.im.core.util

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class Resource {

    /**
     * 获取template文件夹下的模板
     */
    val map: HashMap<String, String> = HashMap()
    fun template(path: String): String {
        if (map.containsKey(path))
            return map[path] ?: ""
        val resource = ClassPathResource("templates/$path")
        val str = resource.inputStream.bufferedReader().use{it.readText()}
        map[path] = str
        return str
    }
}