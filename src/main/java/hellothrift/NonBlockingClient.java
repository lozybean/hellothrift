package hellothrift;

import hellothrift.service.HelloWorldService;
import hellothrift.conf.ServerConfig;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class NonBlockingClient {
    public static void main(String[] argv) throws Exception {
        for (int  i = 0; i < 10; i++) {
            new Thread("Thread" + i) {
                @Override
                public void run() {
                    try (
                            TTransport transport = new TFramedTransport(
                                    new TSocket(ServerConfig.SERVER_IP, ServerConfig.SERVER_PORT, ServerConfig.TIMEOUT))) {
                        HelloWorldService.Client client = new HelloWorldService.Client(new TCompactProtocol(transport));
                        transport.open();

                        String result = client.say("Lyon" + this.getName());
                        System.out.println(result);
                    } catch (
                            TException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}

