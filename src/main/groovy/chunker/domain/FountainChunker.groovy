package chunker.domain

import com.google.gson.Gson
import com.mongodb.gridfs.GridFSFile
import org.springframework.boot.json.GsonJsonParser
import org.springframework.data.mongodb.gridfs.GridFsTemplate

import java.nio.charset.StandardCharsets

/**
 * Created by jtoddington on 05/06/15.
 */

/**
 * Class to split a JSON string array reportsentation into an arrray of FileChunks.
 */
class FountainChunker implements Chunker{

    private final GridFsTemplate fs

    public FountainChunker(GridFsTemplate fs){
        this.fs = fs
    }

    /**
     *
     * @param jsonArray
     * @return
     */
    public List<Chunk> chunk(String jsonArray, Integer chunkSize){
        //Turn the String into an iterable array using GSON.
        GsonJsonParser jsonParser = new GsonJsonParser()
        Gson gson = new Gson()
        //TODO add error handling.
        List<Object> objArray = jsonParser.parseList(jsonArray)
        //Iterate over each element and add every X many to a store - Convert back to String.
        List<Chunk> chunks = new ArrayList<Chunk>()
        List<Object> items = new ArrayList<Object>()
        def itemCount = 0
        objArray.eachWithIndex { def entry, int i ->
            //Get CHUNK_SIZE items and turn them into a JSON String array,
            items.add(entry)
            itemCount++
            if((itemCount >= chunkSize) || (i+1 == objArray.size())){
                //turn to a string and store the items.
                String chunkJson = gson.toJson(items)
                InputStream stream = new ByteArrayInputStream(chunkJson.getBytes(StandardCharsets.UTF_8));
                GridFSFile file = this.fs.store(stream, UUID.randomUUID().toString() + ".json")
                def chunk = new Chunk()
                chunk.chunkSize = chunkSize
                //TODO what should these refer to? GridFS id or passed count.
                chunk.id = i
                chunk.fileRef = file.getFilename().toString()
                chunks.add(chunk)
                //reset the count and items.
                itemCount = 0
                items = new ArrayList<Object>()
            }
        }
        return chunks
    }
}
