package app

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.same
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.io.InputStream
import org.mockito.ArgumentCaptor


@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = [
    "output.directory:/my/folder"
])
class MockNifiApplicationUnitTests {

    @Autowired
    private lateinit var controller: SnapshotController

    val folder = "/my/folder"
    val collection = "db.aaa.bbbb"
    val filename = "filename.txt.zipped.enc"
    val expectedFinalDestination = "$folder/$collection/$filename"
    val expectedFinalDestinationWithNewline = "$expectedFinalDestination\n"

    @Test
    fun controller_will_write_to_file_once_per_file_with_predictable_name() {
        val mockFileWriter: ISnapshotFileWriter = mock(ISnapshotFileWriter::class.java)
        val mockInputStream: InputStream = mock(InputStream::class.java)

        controller.writer = mockFileWriter
        val result = controller.collection(mockInputStream, filename, collection)
        assertEquals(result, expectedFinalDestinationWithNewline)

        val fileArgument = ArgumentCaptor.forClass(File::class.java)

        verify(mockFileWriter, times(1))
                .writeFile(
                        same(mockInputStream),
                        fileArgument.capture())

        assertEquals(expectedFinalDestination, fileArgument.value.absolutePath)
    }

}
