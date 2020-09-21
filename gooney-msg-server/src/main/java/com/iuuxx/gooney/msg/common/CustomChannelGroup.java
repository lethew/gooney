package com.iuuxx.gooney.msg.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 *
 * @author <a href="mailto:wuzhao-1@thunisoft.com>Zhao.Wu</a>
 * @description com.iuuxx.gooney.msg.common gooney
 * @date 2020/9/18 0018 16:44
 */
public class CustomChannelGroup extends DefaultChannelGroup {

    private final ConcurrentMap<String, ChannelId> shortIds = PlatformDependent.newConcurrentHashMap();

    private final ConcurrentMap<String, ChannelId> longIds = PlatformDependent.newConcurrentHashMap();

    public CustomChannelGroup(EventExecutor executor) {
        super(executor);
    }

    public CustomChannelGroup(String name, EventExecutor executor) {
        super(name, executor);
    }

    public CustomChannelGroup(EventExecutor executor, boolean stayClosed) {
        super(executor, stayClosed);
    }

    public CustomChannelGroup(String name, EventExecutor executor, boolean stayClosed) {
        super(name, executor, stayClosed);
    }


    @Override
    public boolean add(Channel channel) {
        ChannelId id = channel.id();
        return shortIds.putIfAbsent(id.asShortText(), id) == null
                && longIds.putIfAbsent(id.asLongText(), id)  == null
                && super.add(channel);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof ChannelId) {
            shortIds.remove(((ChannelId) o).asShortText());
            longIds.remove(((ChannelId) o).asLongText());
        } else if (o instanceof Channel) {
            ChannelId id = ((Channel) o).id();
            shortIds.remove(id.asShortText());
            longIds.remove(id.asLongText());
        }
        return super.remove(o);
    }

    public Channel get(String id) {
        ChannelId ch = shortIds.getOrDefault(id, longIds.get(id));
        return Optional.ofNullable(ch)
                .map(super::find)
                .orElse(null);
    }

}
