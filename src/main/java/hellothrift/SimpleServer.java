package hellothrift;


import hellothrift.conf.ServerConfig;
import hellothrift.impl.HelloWorldServiceImpl;
import hellothrift.service.HelloWorldService;

import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;


public class SimpleServer {
    private static Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();

        // 1. 传输层定义
        ServerSocket serverSocket = new ServerSocket(ServerConfig.SERVER_PORT);
        TServerSocket socket = new TServerSocket(serverSocket);

        // 2. 协议层定义
        // 常用协议:
        // TBinaryProtocol: 使用二进制编码格式进行数据传输
        // TCompactProtocol: 高效率/密集的二进制编码格式进行数据传输
        // TJSONProtocol: 使用JSON文本的数据编码协议进行数据传输
        // TSimpleJSONProtocol: 只提供JSON只写的协议, 适用于通过脚本语言解析
        TCompactProtocol.Factory protocolFactory = new TCompactProtocol.Factory();


        // 3. 处理层定义
        HelloWorldService.Processor processor =
                new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

        // 4. 初始化服务器参数
        TSimpleServer.Args tArgs = new TSimpleServer.Args(socket);
        tArgs.protocolFactory(protocolFactory);
        tArgs.processor(processor);

        // 5. 定义服务
        // 常用服务类型:
        // TSimpleServer: 单线程服务器端, 使用标准的阻塞式I/O
        // TThreadPoolServer: 多线程服务器端, 使用标准的阻塞式I/O
        // TNonblockingServer: 单线程服务器端, 使用非阻塞式I/O
        // THsHaServer: 半同步半异步服务器端, 基于非阻塞式I/O读写和多线程工作任务处理
        // TThreadedSelectorServer: 多线程选择器服务器端, 对THsHaServer在异步I/O模型上进行增强
        TServer tServer = new TSimpleServer(tArgs);
        logger.info("Running Simple Server...");
        tServer.serve();
    }
}
