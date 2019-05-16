package hellothrift;

import hellothrift.conf.ServerConfig;
import hellothrift.impl.HelloWorldServiceImpl;
import hellothrift.service.HelloWorldService;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;


public class NonBlockingServer {

    public static void main(String[] argv) throws TException {
        TProcessor processor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());
        TNonblockingServerSocket socket = new TNonblockingServerSocket(ServerConfig.SERVER_PORT);

        TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(socket);
        tnbArgs.processor(processor);
        tnbArgs.transportFactory(new TFramedTransport.Factory());
        tnbArgs.protocolFactory(new TCompactProtocol.Factory());

        // 使用非阻塞式IO服务端和客户端需要指定TFramedTransport数据传输的方式
        TServer server = new TNonblockingServer(tnbArgs);
        System.out.println("Running Non-blocking Server...");
        server.serve();
    }
}
