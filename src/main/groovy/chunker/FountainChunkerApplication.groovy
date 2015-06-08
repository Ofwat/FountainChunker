package chunker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class FountainChunkerApplication {

    static void main(String[] args) {
        SpringApplication.run FountainChunkerApplication, args
    }
}
