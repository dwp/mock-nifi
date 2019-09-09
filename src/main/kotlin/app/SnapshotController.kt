package app

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.io.*

@RestController
class SnapshotController {

    @PostMapping("/collections")
    fun collection(dataStream: InputStream,
                   @RequestHeader("filename") fileName: String,
                   @RequestHeader("collection") collection: String): String {

        val parentDirectory = File(outputDirectory, collection)
        makeParent(parentDirectory)
        val outputFile = File(parentDirectory, fileName)

        BufferedInputStream(dataStream).use { inputStream ->
            BufferedOutputStream(FileOutputStream(outputFile)).use {outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return "$outputFile\n"
    }

    @Synchronized
    fun makeParent(parent: File) = if (!parent.isDirectory) parent.mkdirs() else true

    @Value("\${output.directory:/data/output}")
    private lateinit var outputDirectory: String
}
