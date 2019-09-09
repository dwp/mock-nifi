package app

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.io.InputStream
import org.mockito.ArgumentCaptor




@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = [
    "output.directory:/data/output"
])
class MockNifiApplicationUnitTests {

    @Autowired
    private lateinit var controller: SnapshotController

    @Test
    fun controller_will_write_to_file_once_per_file() {
		val mockFileWriter: ISnapshotFileWriter = mock(ISnapshotFileWriter::class.java)
		val mockInputStream: InputStream = mock(InputStream::class.java)

        controller.writer = mockFileWriter
        val result = controller.collection(mockInputStream, "fileName.txt.bz2.enc", "db.aaa.bbbb")
        assertEquals(result, "/data/output/db.aaa.bbbb/fileName.txt.bz2.enc\n")

		val fileArgument = ArgumentCaptor.forClass(File::class.java)

        verify(mockFileWriter, times(1))
                .writeFile(
                        same<InputStream>(mockInputStream),
						fileArgument.capture())

		assertEquals("/data/output/db.aaa.bbbb/fileName.txt.bz2.enc", fileArgument.value.absolutePath)
    }

}
