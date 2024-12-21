package org.example;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileLoader {
    public static List<Game> loadGames(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            CsvToBean<Game> csvToBean = new CsvToBeanBuilder<Game>(reader)
                    .withType(Game.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        }
    }
}
