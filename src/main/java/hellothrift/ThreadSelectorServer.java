package hellothrift;

import hellothrift.conf.ServerConfig;
import hellothrift.impl.HelloWorldServiceImpl;
import hellothrift.service.HelloWorldService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class ThreadSelectorServer {
    public static void main(String[] argv) throws Exception{
        TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(ServerConfig.SERVER_PORT);
        TProcessor processor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverSocket);
        tArgs.processor(processor);
        tArgs.protocolFactory(new TCompactProtocol.Factory());
        tArgs.transportFactory(new TFramedTransport.Factory());
        // 类似多线程的HsHaServer
        // AcceptThread: 专门处理新的连接请求 -> 多个SelectorThread
        // SelectorThread: 处理网络IO -> ExecutorService
        // ExecutorService: 业务处理线程池;
        TThreadedSelectorServer server = new TThreadedSelectorServer(tArgs);
        System.out.println("Running ThreadedSelector Server");
        server.serve();
    }
}
