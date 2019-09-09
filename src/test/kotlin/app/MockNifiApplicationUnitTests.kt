package app

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


@RunWith(SpringRunner::class)
@SpringBootTest
@TestPropertySource(properties = [
    "output.directory:/data/output"
])
class MockNifiApplicationUnitTests {

    @Autowired
    private lateinit var controller: SnapshotController
	private val mockFileWriter: SnapshotFileWriter = mock(SnapshotFileWriter::class.java)
	private val mockInputStream: InputStream = mock(InputStream::class.java)

    @Test
    fun controller_will_write_to_file_once_per_file() {

        controller.writer = mockFileWriter

        controller.collection(mockInputStream, "yourFileName.txt.bz2.enc", "yourCollection.is.this")

        verify(mockFileWriter, times(1))
                .writeFile(same(mockInputStream), isA(File::class.java))
    }

}
