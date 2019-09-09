package app

import org.springframework.stereotype.Component
import java.io.*

interface ISnapshotFileWriter {
    fun writeFile(dataStream: InputStream?, outputFile: File?)
}

@Component
 class SnapshotFileWriter : ISnapshotFileWriter {
    override fun writeFile(dataStream: InputStream?, outputFile: File?) {
        BufferedInputStream(dataStream).use { inputStream ->
            BufferedOutputStream(FileOutputStream(outputFile)).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }
}