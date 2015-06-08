package chunker.domain

import chunker.service.ChunkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import groovy.time.*
import groovy.time.TimeCategory

/**
 * Created by jtoddington on 08/06/15.
 */
@Component
class ScheduledTasks {



    private final ChunkService chunkService


    @Value('${ofwat.chunkTimeout}')
    Integer reportAge

    @Autowired
    public ScheduledTasks(ChunkService chunkService){
        this.chunkService = chunkService
    }

    @Scheduled(cron = '${ofwat.cronExpression}')
    public void cleanChunks(){
        Date now = new Date()
        Date cleanDate
        use( groovy.time.TimeCategory ) {
            cleanDate = now-(((reportAge/1000)/60).toInteger()).minutes
        }
        println "*** Running clean chunks for chunks before: " + cleanDate.toGMTString() +  "***"
        chunkService.cleanChunks(cleanDate)
    }

}
