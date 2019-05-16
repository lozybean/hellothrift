package hellothrift;

import hellothrift.conf.ServerConfig;
import hellothrift.service.HelloWorldService;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleClient {
    private static Logger logger = LoggerFactory.getLogger(SimpleClient.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try (TTransport transport = new TSocket(ServerConfig.SERVER_IP, ServerConfig.SERVER_PORT, ServerConfig.TIMEOUT)) {
            TProtocol protocol = new TCompactProtocol(transport);
            HelloWorldService.Client client = new HelloWorldService.Client(protocol);
            transport.open();

            String result = client.say("Lyon");
            logger.info(result);
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
// python version:
// from thrift.transport import TSocket
// from thrift.protocol import TCompactProtocol
// from hello.HelloWorldService import Client
// if __name__ == '__main__':
//     t_socket = TSocket.TSocket('127.0.0.1', 9090)
//     protocol = TCompactProtocol.TCompactProtocol(t_socket)
//     client = Client(protocol)
//     t_socket.open()
//     msg = client.say('Lyon')
//     print(msg)
