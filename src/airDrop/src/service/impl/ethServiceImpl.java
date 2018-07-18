package service.impl;


import service.ethService;
import util.readFile;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

//@Service("ethService")
public class ethServiceImpl implements ethService {

   // @Override
    public BigInteger Price() throws ExecutionException, InterruptedException {
        return readFile.web3j.ethGasPrice().sendAsync().get().getGasPrice();
    }
}
