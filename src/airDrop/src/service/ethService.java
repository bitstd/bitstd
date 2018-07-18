package service;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public interface ethService {
    public BigInteger Price() throws ExecutionException, InterruptedException;
}
