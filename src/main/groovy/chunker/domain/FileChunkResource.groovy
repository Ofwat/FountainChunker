package chunker.domain

import org.springframework.core.io.AbstractResource
import org.springframework.util.Assert

/**
 * Created by jtoddington on 05/06/15.
 *
 * Class to convert a Chunk to a Spring Resource
 *
 */
class FileChunkResource extends AbstractResource{

    final FileChunk fileChunk

    public FileChunkResource(FileChunk fileChunk){
        Assert.notNull(fileChunk)
        this.fileChunk = fileChunk
    }

    @Override
    public String getDescription(){
        return null;
    }

    @Override
    public InputStream getInputStream(){
        return this.fileChunk.getInputStream()
    }

    /*
    @Override
    public long getContentLength(){
        return -1
    }
    */

}
