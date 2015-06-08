package chunker.domain

/**
 * Created by jtoddington on 05/06/15.
 */
interface Chunker {
    public List<Chunk> chunk(String jsonArray, Integer chunkSize)
}