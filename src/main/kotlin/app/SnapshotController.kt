package app

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.io.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@RestController
class SnapshotController {

    @PostMapping("/collections")
    fun collection(dataStream: InputStream,
                   @RequestHeader("filename") fileName: String,
                   @RequestHeader("collection") collection: String): String {

        val parentDirectory = File(outputDirectory, collection)
        makeParent(parentDirectory)
        val outputFile = File(parentDirectory, fileName)

        logger.info("Sending file '$fileName' with collection '$collection' to '$outputFile'")

        BufferedInputStream(dataStream).use { inputStream ->
            BufferedOutputStream(FileOutputStream(outputFile)).use {outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        logger.info("Finished sending '$outputFile'")

        return "$outputFile\n"
    }

    @Synchronized
    fun makeParent(parent: File) = if (!parent.isDirectory) parent.mkdirs() else true

    @Value("\${output.directory:/data/output}")
    private lateinit var outputDirectory: String

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SnapshotController::class.toString())
    }
}
