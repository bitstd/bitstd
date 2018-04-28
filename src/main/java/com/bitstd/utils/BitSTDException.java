package com.bitstd.utils;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/10/17
 */
public class BitSTDException extends Exception {
	private static final long serialVersionUID = 1L;

	public BitSTDException(String msg) {
		super(msg);
	}

	public BitSTDException(String msg, Throwable e) {
		super(msg, e);
	}
}
