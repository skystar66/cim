package com.crossoverjie.cim.client.ssl;

import com.crossoverjie.cim.common.constant.Constants;
import com.crossoverjie.cim.common.protocol.CIMRequestProto;
import com.crossoverjie.cim.common.protocol.CIMResponseProto;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.nio.ByteBuffer;

public class NettySocketClientSSLHandler extends SimpleChannelInboundHandler<CIMResponseProto.CIMResProtocol> {

    private static Logger logger = LoggerFactory.getLogger(NettySocketClientSSLHandler.class);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {

        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if (future.isSuccess()) {
                            logger.info(">>>>>>>>>> ssl握手成功");
                            byte[] array = new byte[]{(byte) 7d, 04};

                            ByteBuffer bu = ByteBuffer.wrap(array);
                            ctx.channel().writeAndFlush(bu);
                        } else {
                            logger.error(">>>>>>>>>> ssl握手失败");
                        }



                        ctx.writeAndFlush(
                                "Welcome to " + InetAddress.getLocalHost().getHostName() +
                                        " secure chat service!\n");
                        ctx.writeAndFlush(
                                "Your session is protected by " +
                                        ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                                        " cipher suite.\n");

                    }
                });
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CIMResponseProto.CIMResProtocol msg) throws Exception {

    }
}
