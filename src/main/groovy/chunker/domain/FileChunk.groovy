package chunker.domain

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jtoddington on 05/06/15.
 */
interface FileChunk {
    public InputStream getInputStream() throws IOException;
}
