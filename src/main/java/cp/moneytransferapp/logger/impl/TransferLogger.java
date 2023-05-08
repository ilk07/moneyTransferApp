package cp.moneytransferapp.logger.impl;

import cp.moneytransferapp.config.AppProperties;
import cp.moneytransferapp.logger.TransferJournal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Component
public class TransferLogger implements TransferJournal {

    @Autowired
    AppProperties appProperties;

    private static TransferLogger INSTANCE = null;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private TransferLogger() {}

    public static TransferLogger getInstance() {
        if (INSTANCE == null) {
            synchronized (TransferLogger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransferLogger();
                }
            }
        }
        return INSTANCE;
    }

    private String createLogMessage(String msg) {
        return LocalDateTime.now().format(format) + "[Transfer->" + msg + "]";
    }

    public void writeToFile(String msg) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(appProperties.getLogFilename(), true))) {
            bw.write(createLogMessage(msg));
            bw.write('\n');
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean logToJournal(String msg) {
        writeToFile(createLogMessage(msg));
        return true;
    }
}

