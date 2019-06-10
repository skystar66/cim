package com.crossoverjie.cim.server.init;

import com.crossoverjie.cim.common.protocol.CIMRequestProto;
import com.crossoverjie.cim.common.ssl.SecureChatSslContextFactory;
import com.crossoverjie.cim.server.handle.CIMServerHandle;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

import javax.net.ssl.SSLEngine;

/**
 * Function:
 *
 * @author xuliang
 * Date: 17/05/2018 18:51
 * @since JDK 1.8
 */
public class CIMServerInitializer extends ChannelInitializer<Channel> {

    private final CIMServerHandle cimServerHandle = new CIMServerHandle();

    //SSL
    private static SslHandler sslHandler = null;




    @Override
    protected void initChannel(Channel ch) throws Exception {

        ch.pipeline()
                .addLast("ssl", getSslHandler())//使用ssl认证
                //11 秒没有向客户端发送消息就发生心跳
                .addLast(new IdleStateHandler(11, 0, 0))
                // google Protobuf 编解码
                .addLast(new ProtobufVarint32FrameDecoder()) //解码器
                .addLast(new ProtobufDecoder(CIMRequestProto.CIMReqProtocol.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender()) //编码器
                .addLast(new ProtobufEncoder()) //编码器
//                .addLast(new NettySocketSSLHandler())
                .addLast(cimServerHandle);

    }


    private SslHandler getSslHandler() {
        SslHandler sslHandler = null;
        String sChatPath = ("/Users/xuliang/Documents/chat/cim/certfile/sChat.jks");
        SSLEngine engine = SecureChatSslContextFactory.getServerContext(sChatPath, sChatPath).createSSLEngine();
        engine.setUseClientMode(false);//设置服务端模式
        engine.setNeedClientAuth(true);//需要客户端验证
        sslHandler = new SslHandler(engine);

        return sslHandler;
    }

}
