package com.iuuxx.gooney.dns.server;

import com.iuuxx.gooney.dns.server.handler.DnsMappingHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.dns.DatagramDnsQueryDecoder;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import io.netty.handler.codec.dns.DatagramDnsResponseEncoder;
import io.netty.resolver.dns.DnsNameResolver;

/**
 * 自定义DNS服务器
 *
 * @author <a href="mailto:wuzhao-1@thunisoft.com>Zhao.Wu</a>
 * @description com.iuuxx.gooney.dns.server gooney
 * @date 2020/9/18 0018 10:54
 */
public class GooneyDnsApplication {
    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();

        /*
        upd 不可使用ServerBootStrap
        there is no need for NioServerDatagramChannel, because UDP servers open one channel for all clients.
        ServerBootstrap allows many client to connect via its channel.
        Therefore TCP has a dedicated ServerSocketChannel.
        Bootstrap is used to create channels for single connections.
        Because UDP has one channel for all clients it makes sense that only the Bootstrap is requried.
        All clients bind to the same channel
         */
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    /**
                     * This method will be called once the {@link Channel} was registered. After the method returns this instance
                     * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
                     *
                     * @param ch the {@link Channel} which was registered.
                     * @throws Exception is thrown if an error occurs. In that case it will be handled by
                     *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
                     *                   the {@link Channel}.
                     */
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new DatagramDnsQueryDecoder())
                                .addLast(new DatagramDnsResponseEncoder())
                                .addLast(new DnsMappingHandler());

                    }
                })
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_RCVBUF, 1024)
                .option(ChannelOption.SO_SNDBUF, 1024);
    }
}
