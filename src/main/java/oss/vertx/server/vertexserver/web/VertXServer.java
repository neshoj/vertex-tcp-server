package oss.vertx.server.vertexserver.web;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetServerOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class VertXServer {

  static final String HOST = System.getProperty("host", "localhost");
  static final int PORT = Integer.parseInt(System.getProperty("port", "5000"));

  @PostConstruct
  public void createServer() {

    Vertx vertx = Vertx.vertx();

    var options = new NetServerOptions().setPort(PORT).setHost(HOST);

    var server = vertx.createNetServer(options);
    server.connectHandler(
        socket -> {
          socket.handler(
              buffer -> {
                String input = buffer.getString(0, buffer.length());
                System.err.println(input);
                  socket.write(input+"\r\n");
              });
        });
    server.listen();
  }
}
