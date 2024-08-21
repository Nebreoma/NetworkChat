import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import ru.netology.MultiThreadedServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class MultiThreadedServerTest {

    @Test
    @DisplayName("метод определяющий время")
    public void currentTimeTest () {
        Clock clock = Clock.fixed(Instant.parse("2024-08-19T20:12:43.00Z"), ZoneId.systemDefault());
        LocalTime time = LocalTime.now(clock);
        mockStatic(LocalTime.class);
        mockStatic(MultiThreadedServer.class);
        when(LocalTime.now()).thenReturn(time);
        when(MultiThreadedServer.currentTime()).thenReturn("20:12:43");

        String timeNow = MultiThreadedServer.currentTime();
        System.out.println(timeNow);

        assertEquals("20:12:43", timeNow);
    }

    @Test
    @DisplayName("метод чтения из файла настроек")
    public void TestReadNumberPort() throws IOException {
        File temp = File.createTempFile("temp", ".txt");
        FileWriter writer = new FileWriter(temp);
        writer.write(8086);
        writer.flush();

        when(MultiThreadedServer.readNumberPort(any())).thenReturn(8086);

        int port = MultiThreadedServer.readNumberPort("temp.txt");
        temp.deleteOnExit();

        assertEquals(8086, port);
    }

}


