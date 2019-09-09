package app

import org.springframework.stereotype.Component
import java.io.*

@Component
class SnapshotFileWriter {
    fun writeFile(dataStream: InputStream, outputFile: File) {
        BufferedInputStream(dataStream).use { inputStream ->
            BufferedOutputStream(FileOutputStream(outputFile)).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}