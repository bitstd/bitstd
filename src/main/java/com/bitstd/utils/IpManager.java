package com.bitstd.utils;

import java.util.LinkedList;
import com.bitstd.model.IpElement;

public class IpManager {

	private LinkedList<IpElement> freeIpQueue = new LinkedList<IpElement>();
	private Object lock = new Object();

	public IpManager() {

	}

	public void set(String ip, int port) {
		IpElement ele = new IpElement();
		ele.setIp(ip);
		ele.setPort(port);
		addFreeIP(ele);
	}

	public IpElement get() {
		return getIP();
	}

	public int getQueueSize() {
		return freeIpQueue.size();
	}

	public void addFreeIP(IpElement ele) {
		synchronized (lock) {
			freeIpQueue.addLast(ele);
		}
	}

	public IpElement getIP() {
		synchronized (lock) {
			IpElement ele = null;
			while (true) {
				if (freeIpQueue.size() > 0) {
					ele = freeIpQueue.removeFirst();
					return ele;
				} else {
					continue;
				}
			}
		}
	}

	public void start(int threadnum) {
		String[] ip = { "192.168.0.1", "192.168.0.2", "192.168.0.3", "192.168.0.4" };
		int[] port = { 22226, 22227, 22228, 22229 };
		for (int i = 0; i < ip.length; i++) {
			set(ip[i], port[i]);
		}
		class DoRunnable implements Runnable {

			public void run() {
				while (true) {
					IpElement ele = get();
					System.out.println(Thread.currentThread().getName() + " " + ele.getIp() + "  " + ele.getPort() + "  "
							+ getQueueSize());
					addFreeIP(ele);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}

		for (int i = 0; i < threadnum; i++) {
			DoRunnable dorun = new DoRunnable();
			Thread th = new Thread(dorun);
			th.start();
		}

	}

	public static void main(String[] args) throws Exception {
		IpManager ip = new IpManager();
		ip.start(2);
	}
}
