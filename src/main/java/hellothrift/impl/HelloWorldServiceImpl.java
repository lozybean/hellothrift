package hellothrift.impl;

import hellothrift.service.HelloWorldService;
import org.apache.thrift.TException;

public class HelloWorldServiceImpl implements HelloWorldService.Iface {
    @Override
    public String say(String name) throws TException {
        return "Hello" + name;
    }
}
