package com.amin.neuralNetwork.regression.function;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.transforms.Sin;
import org.nd4j.linalg.factory.Nd4j;

/**
 * Calculate function value of sine of x divided by x.
 */
public class SinXDivXMathFunction implements MathFunction {

    @Override
    public INDArray getFunctionValues(final INDArray x) {
        return Nd4j.getExecutioner().execAndReturn(new Sin(x.dup())).div(x).div(0.5);
    }

    @Override
    public String getName() {
        return "SinXDivX";
    }
}
