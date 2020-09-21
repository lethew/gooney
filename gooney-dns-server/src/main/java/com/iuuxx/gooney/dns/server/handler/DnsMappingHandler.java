package com.iuuxx.gooney.dns.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.dns.DefaultDnsResponse;
import io.netty.handler.codec.dns.DnsQuery;
import io.netty.handler.codec.dns.DnsResponse;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * TODO:
 *
 * @author <a href="mailto:wuzhao-1@thunisoft.com>Zhao.Wu</a>
 * @description com.iuuxx.gooney.dns.server.handler gooney
 * @date 2020/9/18 0018 11:13
 */
public class DnsMappingHandler extends SimpleChannelInboundHandler<DnsQuery> {
    /**
     * Is called for each message of type {@link I}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DnsQuery msg) throws Exception {

        DnsResponse response = new DefaultDnsResponse(12);
        ctx.writeAndFlush(response);
    }
}
