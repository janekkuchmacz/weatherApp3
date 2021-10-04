package pl.kuchmaczpogoda.utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import io.github.cdimascio.dotenv.DotenvException;

public class DotenvLoader {
    private final String fileName;
    private Dotenv dotenv;

    public DotenvLoader(String fileName) {
        this.fileName = fileName;
    }

    public void loadEnvFile() throws DotenvException {
        DotenvBuilder dotenvBuilder = new DotenvBuilder();
        dotenvBuilder.filename(fileName);
        dotenv = dotenvBuilder.load();
    }

    public String getEnvVariable(String variableName) {
        return dotenv.get(variableName);
    }
}
