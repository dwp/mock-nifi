package app

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import java.io.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@RestController
class SnapshotController(var writer: ISnapshotFileWriter) {

    @PostMapping("/collections")
    fun collection(dataStream: InputStream,
                   @RequestHeader("filename") fileName: String,
                   @RequestHeader("topic") topic: String): String {

        val parentDirectory = File(outputDirectory, topic)
        makeParent(parentDirectory)
        val outputFile = File(parentDirectory, fileName)
        logger.info("Sending file '$fileName' with collection '$topic' to '$outputFile'")

        writer.writeFile(dataStream, outputFile)
        logger.info("Finished sending '$outputFile'")

        return "$outputFile\n"
    }

    @Synchronized
    fun makeParent(parent: File) = if (!parent.isDirectory) parent.mkdirs() else true

    @Value("\${output.directory:/data/output}")
    lateinit var outputDirectory: String

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SnapshotController::class.toString())
    }

}
