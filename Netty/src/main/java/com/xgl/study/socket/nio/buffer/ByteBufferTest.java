package com.xgl.study.socket.nio.buffer;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * 
 * Buffer内部本质为数组
 * Buffer类型：ByteBuffer、ShortBuffer、IntBuffer、LongBuffer、FloatBuffer、DoubleBuffer、CharBuffer、MapedByteBuffer（专门用于内存映射）
 * Buffer属性：capacity(内置数据的长度)、 position(读写位置)、limit(读写限制，及实际数据长度)、mark(可以记录当前位置，需要时候可恢复到此位置)
 * Buffer基本使用步骤
 *   1.使用allocate创建一个Buffer实例对象
 *   2.调用put方法将数据写入到缓冲区
 *   3.写入完成后调用flip方法转换为读模式
 *   4.使用get读取数据
 *   5.读取完成后电泳clear方法将缓冲区转换为写模式
 *   
 */
public class ByteBufferTest {
	
	private static Logger logger = LoggerFactory.getLogger(ByteBufferTest.class);
	
	public static void main(String[] args) {
		
		//Buffer内部本质为数组，capacity为数据的长度
		IntBuffer buffer  = IntBuffer.allocate(20);
		
		//put 方法，将数据写入到Buffer中，超过capacity时则会抛出异常
		//写入数据时 limit = capacity,position+1
		for(int i = 1 ; i<= 15 ;i++) {
			buffer.put(i);
		}
		logger.info("写模式 {}", buffer.array());
		logger.info("写模式 {}",buffer);
		
		//flip 将写模式切换成读模式
		//将此时的position赋值给limit，position重置为0
		buffer.flip();
		logger.info("反转后 {}", buffer.array());
		logger.info("反转后 {}",buffer);
		
		//get 获取数据当前position位置的数据，然后position+1
		buffer.get();
		logger.info("读模式 {}", buffer.array());
		logger.info("读模式 {}",buffer);
		
		//clear读模式转换成写模式
		//limit = capacity,position=0
		buffer.clear();
		logger.info("clear后 {}", buffer.array());
		logger.info("clear后 {}",buffer);
		
		//compact读模式转换成写模式(还未读取数据仍会保留)
		//position = limit-当前position，然后capacity赋值给limit
		buffer.compact();
		logger.info("compact后 {}", buffer.array());
		logger.info("compact后 {}",buffer);
		
		//rewind 倒带，清楚mark标志，从开头开始读取
		buffer.rewind();
		logger.info("rewind后 {}", buffer.array());
		logger.info("rewind后 {}",buffer);
		
		//mark 标记当前position位置
		buffer.mark();
		logger.info("mark后 {}", buffer.array());
		logger.info("mark后 {}",buffer);
		
		//reset 将position复位到mark标记位置
		buffer.reset();
		logger.info("reset后 {}", buffer.array());
		logger.info("reset后 {}",buffer);
		
		//返回当前剩余未读数字长度 limit-position
		buffer.remaining();
		
	}

}
