package hellothrift;

import hellothrift.conf.ServerConfig;
import hellothrift.impl.HelloWorldServiceImpl;
import hellothrift.service.HelloWorldService;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;


import java.net.ServerSocket;

public class ThreadPoolServer {
    // One Thread Per Connection
    public static void main(String[] args) throws Exception {
        TServerSocket transport = new TServerSocket(new ServerSocket(ServerConfig.SERVER_PORT));

        TCompactProtocol.Factory protocolFactory = new TCompactProtocol.Factory();

        HelloWorldService.Processor<HelloWorldService.Iface> processor =
                new HelloWorldService.Processor<>(new HelloWorldServiceImpl());


        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport);

        tArgs.protocolFactory(protocolFactory);
        tArgs.processor(processor);

        TServer tServer = new TThreadPoolServer(tArgs);
        System.out.println("Running ThreadPool Server...");
        tServer.serve();
    }
}
