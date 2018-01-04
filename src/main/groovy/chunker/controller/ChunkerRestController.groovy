package chunker.controller

import chunker.domain.Chunk
import chunker.domain.ChunkParent
import chunker.domain.ChunkParentRepository
import chunker.service.ChunkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

/**
 * Created by jtoddington on 05/06/15.
 */
@RestController
@RequestMapping("/chunks")
class ChunkerRestController {

    private final ChunkParentRepository chunkParentRepository

    private final ChunkService chunkService

    @Autowired
    public ChunkerRestController(ChunkParentRepository chunkParentRepository, ChunkService chunkService){
        this.chunkService = chunkService
        this.chunkParentRepository = chunkParentRepository
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postJson(UriComponentsBuilder uriBuilder, @RequestBody final String jsonString){
        HttpHeaders headers = new HttpHeaders();
        URI uri = uriBuilder.path("/users")
                .buildAndExpand().toUri();
        headers.setLocation(uri);

        //Turn the JSON into an object and split into batches
        ChunkParent chunkParent = chunkService.chunkArray(jsonString)

        return new ResponseEntity<Void>(chunkParent, headers, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET, value = "{chunkParentId}/{chunkId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChunks(UriComponentsBuilder uriBuilder, @PathVariable String chunkParentId, @PathVariable String chunkId){
        def chunk = chunkService.getChunk(chunkId, chunkParentId)
        HttpHeaders headers = new HttpHeaders();
        URI uri = uriBuilder.path("/users")
                .buildAndExpand().toUri();
        headers.setLocation(uri);
        return new ResponseEntity<Void>(chunk.json, headers, HttpStatus.OK);
    }

}
