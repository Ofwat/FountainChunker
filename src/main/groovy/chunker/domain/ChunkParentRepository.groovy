package chunker.domain

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Created by jtoddington on 05/06/15.
 */
interface ChunkParentRepository extends MongoRepository<ChunkParent, String>{
    ChunkParent findById(String id);
}
