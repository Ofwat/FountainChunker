package chunker.domain

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

/**
 * Created by jtoddington on 05/06/15.
 */
class ChunkParent {

    @Id
    private String id

    private Date lastAccessed = new Date()

    ArrayList<Chunk> chunks

    public String getId(){
        return id
    }

    public void setId(String id){
        this.id = id
    }

    public Date getLastAccessed(){
        return lastAccessed
    }

    public void setLastAccessed(Date date){
        this.lastAccessed = date
    }

}
