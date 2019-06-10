package com.crossoverjie.cim.server.util;

import com.crossoverjie.cim.common.pojo.CIMUserInfo;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Function:
 *
 * @author xuliang
 * Date: 22/05/2018 18:33
 * @since JDK 1.8
 */
public class SessionSocketHolder {
    private static final Map<Long, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(16);
    private static final Map<Long, String> SESSION_MAP = new ConcurrentHashMap<>(16);


    public static void saveSession(Long userId, String userName) {
        SESSION_MAP.put(userId, userName);
    }

    public static void removeSession(Long userId) {
        SESSION_MAP.remove(userId);
    }

    /**
     * 设置用户通道channel信息
     *
     * @param
     * @return
     */
    public static void put(Long id, NioSocketChannel socketChannel) {
        CHANNEL_MAP.put(id, socketChannel);
    }

    public static NioSocketChannel get(Long id) {
        return CHANNEL_MAP.get(id);
    }


    /**
     * 获取所有在线channel信息
     *
     * @param
     * @return
     */

    public static Map<Long, NioSocketChannel> getMAP() {
        return CHANNEL_MAP;
    }

    /**
     * 清除用户通道channel信息
     *
     * @param nioSocketChannel
     * @return
     */
    public static void remove(NioSocketChannel nioSocketChannel) {

        //TODO : CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel) 过滤出
        //TODO 连接中存在的 nioSocketChannel 数据，在用 CHANNEL_MAP （原有数据） 移除 该map中的数据

        CHANNEL_MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
    }

    /**
     * 获取注册用户信息
     *
     * @param nioSocketChannel
     * @return
     */
    public static CIMUserInfo getUserId(NioSocketChannel nioSocketChannel) {
        for (Map.Entry<Long, NioSocketChannel> entry : CHANNEL_MAP.entrySet()) {
            NioSocketChannel value = entry.getValue();
            if (nioSocketChannel == value) {
                Long key = entry.getKey();
                String userName = SESSION_MAP.get(key);
                CIMUserInfo info = new CIMUserInfo(key, userName);
                return info;
            }
        }

        return null;
    }


}
