package chunker.domain

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

/**
 * Created by jtoddington on 05/06/15.
 */
class Chunk {

    //TODO we need a way to reference the block of 'chunks' i.e. if we require a particular data key then how do we find it?

    @Id
    private String id
    private int chunkSize
    private String fileRef
    private String json

    public Chunk(String fileRef){
        this.fileRef = fileRef
    }

    public String getFileRef(){
        return fileRef
    }

    public void setFileRef(String fileRef){
        this.fileRef = fileRef
    }

    public String getId(){
        return this.id
    }

    public void setId(String id){
        this.id = id
    }

    public void setJson(String json){
        this.json = json
    }

    public String getJson(){
        return this.json
    }
}
