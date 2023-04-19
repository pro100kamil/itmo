package interlayer;

public class ServerConfiguration {
    private final String host;
    private final int port;

    public ServerConfiguration(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
