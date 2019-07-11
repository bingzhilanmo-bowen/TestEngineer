package com.lanmo.nserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioTestServer {


	public static void main(String[] args) throws IOException {
		Selector boss = Selector.open();

		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(1024));
		ssc.register(boss, SelectionKey.OP_ACCEPT);

		int coreNum = Runtime.getRuntime().availableProcessors();
		System.out.println("coreNum = [" + coreNum + "]");
		ChildSelector[] childSelectors = new ChildSelector[coreNum];
		for(int i=0; i<coreNum; i++) {
			childSelectors[i] = new ChildSelector();
		}

		int index = 0;
		while(boss.select()>0) {
			Set<SelectionKey> keys = boss.selectedKeys();
			for(SelectionKey key:keys) {
				keys.remove(key);
				if(key.isValid()&&key.isAcceptable()) {
					ServerSocketChannel serverAcceptChannel = (ServerSocketChannel) key.channel();
					SocketChannel acceptedChannel = serverAcceptChannel.accept();
					acceptedChannel.configureBlocking(false);
					int childSec =  ++index%coreNum;
					System.out.println("Accept request:" + acceptedChannel.socket().getInetAddress()
							+ ", child sec : " +childSec);
					ChildSelector follower = childSelectors[childSec];
					follower.register(acceptedChannel);
					//follower.wakeUp();
				}
			}
		}


	}


	public static class ChildSelector {

		private Selector child;

		private ExecutorService service = Executors.newFixedThreadPool(
				2*Runtime.getRuntime().availableProcessors());


		public ChildSelector() throws IOException {
			child = Selector.open();
		}


		public void register(SocketChannel socketChannel) throws ClosedChannelException {
			socketChannel.register(child, SelectionKey.OP_READ);
		}


		public void wakeup() {
			this.child.wakeup();
		}
		public void select() {
			service.submit(() -> {
				while(true) {
					if(child.select(500)<=0) {
						continue;
					}
					Set<SelectionKey> keys = child.selectedKeys();
					Iterator<SelectionKey> iterator = keys.iterator();
					while(iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();
						if(key.isReadable()) {
							ByteBuffer buffer = ByteBuffer.allocate(1024);
							SocketChannel channel = (SocketChannel) key.channel();
							int count = channel.read(buffer);
							if(count<0) {
								channel.close();
								key.cancel();
								System.out.println(channel+"->red end !");
								continue;
							}else if(count==0) {
								System.out.println(channel+",size is 0 !");
								continue;
							}else{
								System.out.println(channel+",message is :"+new String(buffer.array()));
							}
						}
					}
				}
			});
		}

	}


}
