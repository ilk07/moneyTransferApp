package cp.moneytransferapp.logger.impl;

import cp.moneytransferapp.logger.TransferJournal;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Setter
@ConfigurationProperties("transfer.log")
public class TransferLogger implements TransferJournal {

    private static String filename;
    private static TransferLogger INSTANCE = null;

    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    private TransferLogger() {
    }

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

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    private String createLogMessage(String msg) {
        return LocalDateTime.now().format(format) + "[Transfer->" + msg + "]";
    }
    private void writeToFile(String msg){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
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

