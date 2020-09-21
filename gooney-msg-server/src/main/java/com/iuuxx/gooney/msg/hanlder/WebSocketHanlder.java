package com.iuuxx.gooney.msg.hanlder;

import com.iuuxx.gooney.msg.common.CustomChannelGroup;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *
 * @author <a href="mailto:wuzhao-1@thunisoft.com>Zhao.Wu</a>
 * @description com.iuuxx.gooney.msg.hanlder gooney
 * @date 2020/9/18 0018 14:01
 */
public class WebSocketHanlder extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 存储客户端信息
     */
    private static final CustomChannelGroup CLIENTS = new CustomChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        CLIENTS.add(channel);
        TextWebSocketFrame socketFrame = new TextWebSocketFrame(channel.id().asLongText());
        ctx.writeAndFlush(socketFrame);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
