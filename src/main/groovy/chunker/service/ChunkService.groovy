package chunker.service

import chunker.domain.Chunk
import chunker.domain.ChunkParent
import chunker.domain.ChunkParentRepository
import chunker.domain.FountainChunker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.GridFsTemplate
import org.springframework.stereotype.Service

import java.nio.charset.StandardCharsets

/**
 * Created by jtoddington on 05/06/15.
 */
@Service
class ChunkService {

    @Value('${ofwat.chunkSize}')
    Integer chunkSize

    private final ChunkParentRepository chunkParentRepository

    private final GridFsTemplate fs;

    @Autowired
    public ChunkService(GridFsTemplate fs, ChunkParentRepository chunkParentRepository){
        this.fs = fs
        this.chunkParentRepository = chunkParentRepository
    }

    /**
     * Method to chunk the array and store the chunks in GridFS.
     * Returns ChunkParent which is a ref to ref to the parent -> all the chunks too.
     * @param jsonArray
     */
    public ChunkParent chunkArray(String jsonArray) {
        ChunkParent chunkParent = new ChunkParent()
        chunkParent.setLastAccessed(new Date())
        chunkParent.setId(UUID.randomUUID().toString())
        FountainChunker chunker = new FountainChunker(fs)
        def chunks = chunker.chunk(jsonArray, chunkSize)
        chunkParent.chunks = chunks
        return this.chunkParentRepository.save(chunkParent)
    }

    /**
     * Get the actual chunk of the array.
     * @return
     */
    Chunk getChunk(String chunkId, String chunkParentId){
        //Update the last accessed timestamp on the chunkParent.
        def chunkParent = this.chunkParentRepository.findById(chunkParentId)
        chunkParent.setLastAccessed(new Date())
        this.chunkParentRepository.save(chunkParent)
        //Get the gridFS file.
        Chunk chunk = new Chunk()
        chunk.json = this.fs.getResource(chunkId + ".json").inputStream.getText()
        return chunk
    }

    /**
     *
     */
    public Boolean cleanChunks(Date dateToCleanBefore){
        //Get all the ChunkParents with a last updated older than dateToCLeanBefore.
        def chunkParents = this.chunkParentRepository.findAll()
        List<ChunkParent> toRemove = new ArrayList<ChunkParent>()
        chunkParents.each{
            if(it.lastAccessed < dateToCleanBefore){
                toRemove.add(it)
            }
        }

        toRemove.each{
            removeChunkParent(it)
            println "Removed chunkParent:${it.id} and ${it.chunks.size()} chunks."
        }



        //For each of them delete all the associated file sand then delete the parent record.
        return true
    }

    private def removeChunkParent(ChunkParent chunkParent){
        chunkParent.chunks.each{
            Query query = new Query();
            query.addCriteria(Criteria.where("filename").is(it.getFileRef()));
            this.fs.delete(query);
        }

        chunkParentRepository.delete(chunkParent)

    }
}
