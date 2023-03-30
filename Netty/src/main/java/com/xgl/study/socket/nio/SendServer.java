package com.xgl.study.socket.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class SendServer {

	private static Charset charset = Charset.forName("UTF-8");
	private static ByteBuffer buffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws IOException {
		int stage = 0;
		String fileName = "";
		FileChannel fileChannel = null;
		int num;

		Selector selector = Selector.open();
		Selector selector1 = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress(9000));
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		
	}
	class MutiReact implements Runnable{
		private final Selector selector;
		String name;
		
		MutiReact(Selector selector,String name){
			this.selector = selector;
			this.name = name;
			
		}
		@Override
		public void run() {
			try {
				while (selector.select() > 0) {
					Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove();
						if (key.isValid()) {
							if (key.isConnectable()) {
								System.out.println("isConnectable"+"   "+name);
							}
							if (key.isAcceptable()) {
								System.out.println("isAcceptable"+"   "+name);
							}
							if (key.isReadable()) {
								System.out.println("isReadable"+"   "+name);
							}
							if (key.isWritable()) {
								System.out.println("isWritable"+"   "+name);
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
