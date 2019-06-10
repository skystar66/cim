package com.crossoverjie.cim.server.ssl;

import com.crossoverjie.cim.common.constant.Constants;
import com.crossoverjie.cim.common.protocol.CIMRequestProto;
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

public class NettySocketSSLHandler extends SimpleChannelInboundHandler<CIMRequestProto.CIMReqProtocol> {

    private static Logger logger = LoggerFactory.getLogger(NettySocketSSLHandler.class);




    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CIMRequestProto.CIMReqProtocol msg) throws Exception {

    }
}
