package club.itstudio.im

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImApplication

fun main(args: Array<String>) {
    runApplication<ImApplication>(*args)
}
