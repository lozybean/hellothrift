package hellothrift;

import hellothrift.conf.ServerConfig;
import hellothrift.impl.HelloWorldServiceImpl;
import hellothrift.service.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class HsHaServer {
    public static void main(String[] argv) throws Exception{
        TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(ServerConfig.SERVER_PORT);
        TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

        THsHaServer.Args tArgs = new THsHaServer.Args(tnbSocketTransport);
        tArgs.processor(tprocessor);
        tArgs.transportFactory(new TFramedTransport.Factory());
        tArgs.protocolFactory(new TCompactProtocol.Factory());
        // 相比NonBlockingServer, 将业务处理交给线程池, 主线程只负责监听和数据读写;
        TServer server = new THsHaServer(tArgs);
        System.out.println("Running HsHa Server");
        server.serve();
    }
}
