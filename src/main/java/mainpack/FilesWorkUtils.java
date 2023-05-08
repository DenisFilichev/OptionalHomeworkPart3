package mainpack;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;


public class FilesWorkUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void write(Set<? super Student> students) throws IOException {
        objectMapper.writeValue(Files.newBufferedWriter(Paths.get("Students.sto")), students);
    }

    public List<Student> read() throws IOException {
        List<Student> students;
        try( BufferedReader reader = Files.newBufferedReader(Paths.get("Students.sto"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine())!=null)
                sb.append(line).append("\n");
            students = objectMapper.readValue(sb.toString(), new TypeReference<List<Student>>(){});
        }
        return students;
    }
}
