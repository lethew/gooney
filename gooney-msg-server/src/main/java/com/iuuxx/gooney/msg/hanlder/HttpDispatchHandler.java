package com.iuuxx.gooney.msg.hanlder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

/**
 *
 * @author <a href="mailto:wuzhao-1@thunisoft.com>Zhao.Wu</a>
 * @description com.iuuxx.gooney.msg.hanlder gooney
 * @date 2020/9/18 0018 11:47
 */
public class HttpDispatchHandler extends SimpleChannelInboundHandler<DefaultHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpMethod method = request.method();

        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.ACCEPTED);
        // fix
        ctx.writeAndFlush(response);
    }
}
