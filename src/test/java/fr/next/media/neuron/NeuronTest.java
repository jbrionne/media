package fr.next.media.neuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeInt;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDSpaceByIndices;
import fr.next.media.array.CoordinatesXDSpaceByIndices;
import fr.next.media.array.impl.Array2DMatrix3fImpl;
import fr.next.media.array.impl.ArrayFactory;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;
import junit.framework.TestCase;

public class NeuronTest extends TestCase {

	public void test3DMap() {
		
		NeuronExecutor<Integer> nexec = new NeuronExecutor<>();
		
		Axe<AxeValue<Integer>> domNeurone = new AxeOrd<AxeValue<Integer>>("x");
		for (int i = 0; i < 10; i++) {
			domNeurone.add(new AxeValue<Integer>(i));
		}

		float pulseIntensity = 1f;
		float noPulseIntensity = 0f;
		Agregation agre = (a, b, c) -> (a * b) + c;
		Activation activ = a -> a >= pulseIntensity;
		Activation activNot = a -> a >= -(pulseIntensity / 2f);

		{
			System.out.println("Basic");

			ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
					.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);

			// 0 --- 1 --- 3 --- 5
			// \-- 2 --- 4 --/

			array.setValue(1f, 0, 1);
			array.setValue(1f, 0, 2);
			array.setValue(1f, 1, 3);
			array.setValue(1f, 2, 4);
			array.setValue(0.5f, 3, 5);
			array.setValue(0.5f, 4, 5);

			Map<Integer, Float> step0 = new HashMap<>();
			step0.put(0, pulseIntensity);

			List<Map<Integer, Float>> steps = new ArrayList<>();
			nexec.simul(steps, array, step0, agre, activ, false);

			Map<Integer, Float> lastStep = steps.get(steps.size() - 1);

			assertActivated(5, lastStep, activ);
		}

		ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> andBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
		{
			System.out.println("Boolean And");
			// learning

			// 0 --- 2 --
			// 1 --/

			// true && true -> true
			// true && false -> false
			// false && true -> false
			// false && false -> false

			andBooleanArray.setValue(0.6f, 0, 2);
			andBooleanArray.setValue(0.6f, 1, 2);

			{
				System.out.println("Boolean And true && true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);
				step0.put(1, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, andBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(2, lastStep, activ);
			}

			{
				System.out.println("Boolean And false && true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, andBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(2, lastStep, activ);

			}

			{
				System.out.println("Boolean And true && false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);
				step0.put(1, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, andBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(2, lastStep, activ);
			}

			{
				System.out.println("Boolean And false && false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, andBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(2, lastStep, activ);
			}

		}

		ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> orBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
				.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
		{
			System.out.println("Boolean Or");
			// learning
			// 0 --- 2 --
			// 1 --/

			// true || true -> true
			// true || false -> true
			// false || true -> true
			// false || false -> false

			orBooleanArray.setValue(1.1f, 0, 2);
			orBooleanArray.setValue(1.1f, 1, 2);

			{
				System.out.println("Boolean Or true && true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);
				step0.put(1, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, orBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(2, lastStep, activ);
			}

			{
				System.out.println("Boolean Or false && true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, orBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(2, lastStep, activ);
			}

			{
				System.out.println("Boolean Or true && false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);
				step0.put(1, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, orBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(2, lastStep, activ);
			}

			{
				System.out.println("Boolean Or false && false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, orBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(2, lastStep, activ);
			}

		}

		{
			System.out.println("Boolean Not");
			// learning
			ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> notBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
					.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
			// 0 --- 1

			// NOT true -> false
			// Not false -> true

			notBooleanArray.setValue(-1f, 0, 1);

			{
				System.out.println("Boolean Not true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, notBooleanArray, step0, agre, activNot, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(1, lastStep, activNot);
			}

			// it should not existed at low level
			// to make it work, don't take into account the threshold and keep
			// excitation value of the first neurons
			{
				System.out.println("Boolean Not false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, notBooleanArray, step0, agre, activNot, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(1, lastStep, activNot);
			}

		}

		{
			System.out.println("Boolean Xor");
			// learning
			ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> xorBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
					.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
			// 0 --- 2 --- 4
			// 1 --- 3 --/

			// true xor true -> false
			// true xor false -> true
			// false xor true -> true
			// false xor false -> false

			xorBooleanArray.setValue(0.6f, 0, 2);
			xorBooleanArray.setValue(1.1f, 0, 3);
			xorBooleanArray.setValue(0.6f, 1, 2);
			xorBooleanArray.setValue(1.1f, 1, 3);
			xorBooleanArray.setValue(-2f, 2, 4);
			xorBooleanArray.setValue(1.1f, 3, 4);
			{
				System.out.println("Boolean Xor true && true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);
				step0.put(1, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, xorBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(4, lastStep, activ);
			}

			{
				System.out.println("Boolean Xor false && true");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, pulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, xorBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(4, lastStep, activ);
			}

			{
				System.out.println("Boolean Xor true && false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, pulseIntensity);
				step0.put(1, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, xorBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(4, lastStep, activ);
			}

			{
				System.out.println("Boolean Xor false && false");
				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, noPulseIntensity);

				List<Map<Integer, Float>> steps = new ArrayList<>();
				nexec.simul(steps, xorBooleanArray, step0, agre, activ, false);

				Map<Integer, Float> lastStep = steps.get(steps.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(4, lastStep, activ);
			}

			{

				System.out.println("Boolean Xor false && false, compute or && and");

				ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> xorExclusionBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
						.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
				xorExclusionBooleanArray.setValue(-2f, 0, 2);
				xorExclusionBooleanArray.setValue(1.1f, 1, 2);

				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, noPulseIntensity);

				// change reference
				Map<Integer, Float> newStepOr0 = new HashMap<>();
				newStepOr0.put(0, step0.get(0));
				newStepOr0.put(1, step0.get(1));

				List<Map<Integer, Float>> stepsOr = new ArrayList<>();
				nexec.simul(stepsOr, orBooleanArray, newStepOr0, agre, activ, false);
				Map<Integer, Float> lastStepOr = stepsOr.get(stepsOr.size() - 1);

				// change reference
				Map<Integer, Float> newStepAnd0 = new HashMap<>();
				newStepAnd0.put(0, step0.get(0));
				newStepAnd0.put(1, step0.get(1));

				List<Map<Integer, Float>> stepsAnd = new ArrayList<>();
				nexec.simul(stepsAnd, andBooleanArray, newStepAnd0, agre, activ, false);
				Map<Integer, Float> lastStepAnd = stepsAnd.get(stepsAnd.size() - 1);

				// change reference
				Map<Integer, Float> newLastSteps = new HashMap<>();
				newLastSteps.put(0, lastStepAnd.get(2) != null ? lastStepAnd.get(2) : 0);
				newLastSteps.put(1, lastStepOr.get(2) != null ? lastStepOr.get(2) : 0);

				List<Map<Integer, Float>> stepsFinal = new ArrayList<>();
				nexec.simul(stepsFinal, xorExclusionBooleanArray, newLastSteps, agre, activ, false);

				Map<Integer, Float> lastStep = stepsFinal.get(stepsFinal.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertNotActivated(2, lastStep, activ);

				//

			}

			{

				System.out.println("Boolean Xor false && true, compute or && and");

				ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> xorExclusionBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
						.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
				xorExclusionBooleanArray.setValue(-2f, 0, 2);
				xorExclusionBooleanArray.setValue(1.1f, 1, 2);

				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, pulseIntensity);

				// change reference
				Map<Integer, Float> newStepOr0 = new HashMap<>();
				newStepOr0.put(0, step0.get(0));
				newStepOr0.put(1, step0.get(1));

				List<Map<Integer, Float>> stepsOr = new ArrayList<>();
				nexec.simul(stepsOr, orBooleanArray, newStepOr0, agre, activ, false);
				Map<Integer, Float> lastStepOr = stepsOr.get(stepsOr.size() - 1);

				// change reference
				Map<Integer, Float> newStepAnd0 = new HashMap<>();
				newStepAnd0.put(0, step0.get(0));
				newStepAnd0.put(1, step0.get(1));

				List<Map<Integer, Float>> stepsAnd = new ArrayList<>();
				nexec.simul(stepsAnd, andBooleanArray, newStepAnd0, agre, activ, false);
				Map<Integer, Float> lastStepAnd = stepsAnd.get(stepsAnd.size() - 1);

				// change reference
				System.out.println("lastStepAnd " + lastStepAnd.get(2));
				System.out.println("lastStepOr " + lastStepOr.get(2));
				Map<Integer, Float> newLastSteps = new HashMap<>();
				newLastSteps.put(0, lastStepAnd.get(2) != null ? lastStepAnd.get(2) : 0);
				newLastSteps.put(1, lastStepOr.get(2) != null ? lastStepOr.get(2) : 0);

				List<Map<Integer, Float>> stepsFinal = new ArrayList<>();
				nexec.simul(stepsFinal, xorExclusionBooleanArray, newLastSteps, agre, activ, false);

				Map<Integer, Float> lastStep = stepsFinal.get(stepsFinal.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(2, lastStep, activ);

				//

			}

			{

				ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> globalBooleanArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
						.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeurone, domNeurone);
				// or
				globalBooleanArray.setValue(1.1f, 0, 2);
				globalBooleanArray.setValue(1.1f, 1, 2);

				// and
				globalBooleanArray.setValue(0.6f, 3, 5);
				globalBooleanArray.setValue(0.6f, 4, 5);

				// xor
				globalBooleanArray.setValue(-2f, 6, 8);
				globalBooleanArray.setValue(1.1f, 7, 8);

				Map<Integer, Float> step0 = new HashMap<>();
				step0.put(0, noPulseIntensity);
				step0.put(1, pulseIntensity);

				// change reference
				Map<Integer, Float> newStepOr0 = new HashMap<>();
				newStepOr0.put(0, step0.get(0));
				newStepOr0.put(1, step0.get(1));

				List<Map<Integer, Float>> stepsOr = new ArrayList<>();
				nexec.simul(stepsOr, globalBooleanArray, newStepOr0, agre, activ, false);
				Map<Integer, Float> lastStepOr = stepsOr.get(stepsOr.size() - 1);

				// change reference
				Map<Integer, Float> newStepAnd0 = new HashMap<>();
				newStepAnd0.put(3, step0.get(0));
				newStepAnd0.put(4, step0.get(1));

				List<Map<Integer, Float>> stepsAnd = new ArrayList<>();
				nexec.simul(stepsAnd, globalBooleanArray, newStepAnd0, agre, activ, false);
				Map<Integer, Float> lastStepAnd = stepsAnd.get(stepsAnd.size() - 1);

				// change reference
				Map<Integer, Float> newLastSteps = new HashMap<>();
				newLastSteps.put(6, lastStepAnd.get(5) != null ? lastStepAnd.get(5) : 0);
				newLastSteps.put(7, lastStepOr.get(2) != null ? lastStepOr.get(2) : 0);
				
				List<Map<Integer, Float>> stepsFinal = new ArrayList<>();
				nexec.simul(stepsFinal, globalBooleanArray, newLastSteps, agre, activ, false);

				Map<Integer, Float> lastStep = stepsFinal.get(stepsFinal.size() - 1);
				System.out.println("activate " + lastStep.toString());

				assertActivated(8, lastStep, activ);
			}

		}

	}

	private void assertNotActivated(int id, Map<Integer, Float> lastStep, Activation activ) {
		assertTrue(!lastStep.containsKey(id)
				|| (lastStep.containsKey(id) && !activ.threshold(lastStep.get(id).floatValue())));
	}

	private void assertActivated(int id, Map<Integer, Float> lastStep, Activation activ) {
		assertTrue(lastStep.containsKey(id) && activ.threshold(lastStep.get(id).floatValue()));
	}
	
	private void assertActivated(String id, Map<String, Float> lastStep, Activation activ) {
		assertTrue(lastStep.containsKey(id) && activ.threshold(lastStep.get(id).floatValue()));
	}
	
	public void testPatternRecognition() {

		NeuronExecutor<Integer> nexec = new NeuronExecutor<>();
		
		int nbLine = 4;
		int nbCol = 4;
		int nbCase = nbLine * nbCol;

		Axe<AxeValue<Integer>> domNeuroneLayer1 = new AxeOrd<AxeValue<Integer>>("layer1");
		for (int i = 0; i < nbCase * 2; i++) {
			domNeuroneLayer1.add(new AxeValue<Integer>(i));
		}
		Axe<AxeValue<Integer>> domNeuroneLayer2 = new AxeOrd<AxeValue<Integer>>("layer2");
		for (int i = nbCase; i < (nbCase * 2) + 9; i++) {
			domNeuroneLayer2.add(new AxeValue<Integer>(i));
		}
		Axe<AxeValue<Integer>> domNeuroneLayer4 = new AxeOrd<AxeValue<Integer>>("layer4");
		for (int i = nbCase * 2; i < (nbCase * 2) + 9 + 4; i++) {
			domNeuroneLayer4.add(new AxeValue<Integer>(i));
		}

		AxeInt axeLine = new AxeInt<>("worldLine", 4);
		AxeInt axeCol = new AxeInt<>("worldCol", 1);
		List<Axe<AxeValue>> axes = new ArrayList<>();
		axes.add(axeLine);
		axes.add(axeCol);
		int[] indicesLayer1 = new int[] { 0, 0 };
		int[] indicesLayer2 = new int[] { 1, 0 };
		int[] indicesLayer4 = new int[] { 2, 0 };
		Map<Integer, Float> lastStepLayer1 = null;
		{
			System.out.println("Layer1");
			Agregation agre = (a, b, c) -> (a * b) + c;
			Activation activ = a -> a >= 3.5f;

			ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
					.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeuroneLayer1, domNeuroneLayer1);

			// 0 1 2 3
			// 4 5 6 7
			// 8 9 10 11
			// 12 13 14 15

			Map<Integer, Float> step0 = new HashMap<>();
			step0.put(0, -1f);
			step0.put(1, -1f);
			step0.put(2, 1f);
			step0.put(3, -1f);
			
			step0.put(4, -1f);
			step0.put(5, 1f);
			step0.put(6, 1f);
			step0.put(7, -1f);
			
			step0.put(8, 1f);
			step0.put(9, 1f);
			step0.put(10, -1f);
			step0.put(11, -1f);
			
			step0.put(12, -1f);
			step0.put(13, -1f);
			step0.put(14, -1f);
			step0.put(15, -1f);

			int index = nbCase;
			for (int j = 0; j < nbCol; j++) {
				for (int i = 0; i < nbLine; i++) {

					int lim = nbCol * (j + 1);
					int lim2 = nbCol * (j + 1 + 1);

					int a = i + nbCol * j;
					array.setValue(-1f, a, index);
					int b = i + 1 + nbCol * j;
					if (b < lim) {
						array.setValue(1f, b, index);
					}
					int x = i + nbCol + nbCol * j;
					if (x < lim2 && x < nbCase) {
						array.setValue(1f, x, index);
					}
					int y = i + nbCol + 1 + nbCol * j;
					if (y < lim2 && y < nbCase) {
						array.setValue(1f, y, index);
					}
					index++;
				}
			}

			List<Map<Integer, Float>> steps = new ArrayList<>();
			nexec.simul(steps, array, step0, agre, activ, true);

			lastStepLayer1 = steps.get(steps.size() - 1);
			System.out.println("activate " + lastStepLayer1.toString());

			
			for(int i = 16; i < 32; i++) {
				if(i == 17 || i == 20) {
					assertActivated(i, lastStepLayer1, a -> a >=1f);
				} else {
					assertNotActivated(i, lastStepLayer1, a -> a >=1f);
				}
			}
		}
		Map<Integer, Float> lastStepLayer2 = null;
		{
			System.out.println("Layer2");
			Agregation agre = (a, b, c) -> (a * b) + c;
			Activation activ = a -> a >= 3.5f;

			ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
					.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeuroneLayer2, domNeuroneLayer2);
			// 16 17 18 19
			// 20 21 22 23
			// 24 25 26 27
			// 28 29 30 31

			int index = nbCase * 2;
			for (int j = 0; j < nbCol - 1; j++) {
				for (int i = nbCase; i < nbCase + nbLine - 1; i++) {
					int a = i + nbCol * j;
					int b = i + 1 + nbCol * j;
					int x = i + nbCol + nbCol * j;
					int y = i + nbCol + 1 + nbCol * j;
					array.setValue(-1f, a, index);
					array.setValue(1f, b, index);
					array.setValue(1f, x, index);
					array.setValue(-1f, y, index);
					index++;
				}
			}

			// layer 2
			List<Map<Integer, Float>> steps = new ArrayList<>();
			nexec.simul(steps, array, lastStepLayer1, agre, activ, true);

			lastStepLayer2 = steps.get(steps.size() - 1);
			System.out.println("activate " + lastStepLayer2.toString());

			assertActivated(32, lastStepLayer2, a -> a >=1f);
			for(int i = 33; i < 41; i++) {
				assertNotActivated(i, lastStepLayer2, a -> a >=1f);
			}
		}
		Map<Integer, Float> lastStepLayer4 = null;
		{
			System.out.println("Layer4");
			Agregation agre = (a, b, c) -> (a * b) + c;
			Activation activ = a -> a >= -2.5f;

			ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> array = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
					.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeuroneLayer4, domNeuroneLayer4);
			// 32 33 34
			// 35 36 37
			// 38 39 40
			nbCol = 3;
			nbLine = 3;	
			int index = nbCase * 2 + 9;
			for (int j = 0; j < nbCol - 1; j++) {
				for (int i = nbCase * 2; i < nbCase * 2 + nbLine - 1; i++) {
					int a = i + nbCol * j;
					int b = i + 1 + nbCol * j;
					int x = i + nbCol + nbCol * j;
					int y = i + nbCol + 1 + nbCol * j;
					array.setValue(1f, a, index);
					array.setValue(1f, b, index);
					array.setValue(1f, x, index);
					array.setValue(1f, y, index);
					index++;
				}
			}
			
			// 41 42
			// 43 44
			
			// layer 4
			List<Map<Integer, Float>> steps = new ArrayList<>();
			nexec.simul(steps, array, lastStepLayer2, agre, activ, true);

			lastStepLayer4 = steps.get(steps.size() - 1);
			System.out.println("activate " + lastStepLayer4.toString());

			assertActivated(41, lastStepLayer4, a -> a >=1f);
			assertNotActivated(42, lastStepLayer4, a -> a >=1f);
			assertNotActivated(43, lastStepLayer4, a -> a >=1f);
			assertNotActivated(44, lastStepLayer4, a -> a >=1f);
		}

		//Multilayer perceptron
		//Math.tanh(vi)
		// 1 / (1 + Math.exp(vi))
	}
	
	public void testMedFunction() {
		
		Axe<AxeValue<Integer>> domNeuroneX = new AxeOrd<AxeValue<Integer>>("x");
		for (int i = 0; i < 10; i++) {
			domNeuroneX.add(new AxeValue<Integer>(i));
		}
		Axe<AxeValue<Integer>> domNeuroneY = new AxeOrd<AxeValue<Integer>>("y");
		for (int i = 0; i < 10; i++) {
			domNeuroneY.add(new AxeValue<Integer>(i));
		}
		ArrayXDOrd axes = new MapXDWithEmptyValueGenericImpl<>(Integer.class, 0, domNeuroneX, domNeuroneY);
		NeuronExecutor<Integer> nexec = new NeuronExecutor<>();
		float noPulseIntensity = 0f;
		float pulseIntensity = 1f;
		Agregation agre = (a, b, c) -> (a * b) + c;
		Activation activ = a -> a >= pulseIntensity;
		
		AxeInt xLoc = new AxeInt("x", 3);
		AxeInt yLoc = new AxeInt("y", 3);
		ArrayXDOrd indices1 = new Array2DMatrix3fImpl<>(xLoc, yLoc);
		indices1.setValue(0f, 0, 0);
		indices1.setValue(0f, 0, 1);
		ArrayXDOrd indices2 = new Array2DMatrix3fImpl<>(xLoc, yLoc);
		indices2.setValue(3f, 0, 0);
		indices2.setValue(3f, 0, 1);
		ArrayXDOrd indices3 = new Array2DMatrix3fImpl<>(xLoc, yLoc);
		indices3.setValue(6f, 0, 0);
		indices3.setValue(6f, 0, 1);
		MedAndBoolean and = new MedAndBoolean(Float.class,"", domNeuroneX, domNeuroneY);
		and.addCoordinate(new CoordinatesXDSpaceByIndices(axes, indices1));
		MedOrBoolean or = new MedOrBoolean(Float.class,"", domNeuroneX, domNeuroneY);
		or.addCoordinate(new CoordinatesXDSpaceByIndices(axes, indices2));
		MedXorExcludeBoolean xorEx = new MedXorExcludeBoolean(Float.class,"", domNeuroneX, domNeuroneY);
		xorEx.addCoordinate(new CoordinatesXDSpaceByIndices(axes, indices3));
		
		
		
		System.out.println(and.getValueFromUpperAxeCoordByIndices(axes, 0f, 2f));
		System.out.println(and.getValueFromUpperAxeCoordByIndices(axes, 1f, 2f));
		
		System.out.println(or.getValueFromUpperAxeCoordByIndices(axes, 3f, 5f));
		System.out.println(or.getValueFromUpperAxeCoordByIndices(axes, 4f, 5f));
		
		System.out.println(xorEx.getValueFromUpperAxeCoordByIndices(axes, 6f, 8f));
		System.out.println(xorEx.getValueFromUpperAxeCoordByIndices(axes, 7f, 8f));
		
		Map<Integer, Float> step0 = new HashMap<>();
		step0.put(0, noPulseIntensity);
		step0.put(1, pulseIntensity);

		// change reference
		Map<Integer, Float> newStepOr0 = new HashMap<>();
		newStepOr0.put(0, step0.get(0));
		newStepOr0.put(1, step0.get(1));

		List<Map<Integer, Float>> stepsOr = new ArrayList<>();
		nexec.simul(stepsOr, or, newStepOr0, agre, activ, false);
		Map<Integer, Float> lastStepOr = stepsOr.get(stepsOr.size() - 1);

		// change reference
		Map<Integer, Float> newStepAnd0 = new HashMap<>();
		newStepAnd0.put(0, step0.get(0));
		newStepAnd0.put(1, step0.get(1));

		List<Map<Integer, Float>> stepsAnd = new ArrayList<>();
		nexec.simul(stepsAnd, and, newStepAnd0, agre, activ, false);
		Map<Integer, Float> lastStepAnd = stepsAnd.get(stepsAnd.size() - 1);

		// change reference
		Map<Integer, Float> newLastSteps = new HashMap<>();
		newLastSteps.put(0, lastStepAnd.get(2) != null ? lastStepAnd.get(2) : 0);
		newLastSteps.put(1, lastStepOr.get(2) != null ? lastStepOr.get(2) : 0);
		
		List<Map<Integer, Float>> stepsFinal = new ArrayList<>();
		nexec.simul(stepsFinal, xorEx, newLastSteps, agre, activ, false);

		Map<Integer, Float> lastStep = stepsFinal.get(stepsFinal.size() - 1);
		System.out.println("activate " + lastStep.toString());

		assertActivated(2, lastStep, activ);
		
	}
	
	public void testMedFunctionWithMerge() {
		
		Axe<AxeValue<Integer>> domNeuroneX = new AxeOrd<AxeValue<Integer>>("x");
		for (int i = 0; i < 10; i++) {
			domNeuroneX.add(new AxeValue<Integer>(i));
		}
		Axe<AxeValue<Integer>> domNeuroneY = new AxeOrd<AxeValue<Integer>>("y");
		for (int i = 0; i < 10; i++) {
			domNeuroneY.add(new AxeValue<Integer>(i));
		}
		ArrayXDOrd axes = new MapXDWithEmptyValueGenericImpl<>(Float.class, 0f, domNeuroneX, domNeuroneY);
		NeuronExecutor<CoordinatesXDSpaceByIndices> nexec = new NeuronExecutor<>();
		float noPulseIntensity = 0f;
		float pulseIntensity = 1f;
		Agregation agre = (a, b, c) -> (a * b) + c;
		Activation activ = a -> a >= pulseIntensity;
		
		MedAndBoolean and = new MedAndBoolean(Float.class,"", domNeuroneX, domNeuroneY);
		MedOrBoolean or = new MedOrBoolean(Float.class,"", domNeuroneX, domNeuroneY);
		MedXorExcludeBoolean xorEx = new MedXorExcludeBoolean(Float.class,"", domNeuroneX, domNeuroneY);
		
//		Axe<AxeValue<Integer>> andEntries = new AxeOrd<AxeValue<Integer>>("a");
//		andEntries.add(new AxeValue<Integer>(0));
//		andEntries.add(new AxeValue<Integer>(1));
//		Axe<AxeValue<Integer>> andOutputs = new AxeOrd<AxeValue<Integer>>("b");
//		andOutputs.add(new AxeValue<Integer>(2));
//		MedNode andNode = new MedNode("andNode", andEntries, andOutputs, and);
//		
//		Axe<AxeValue<Integer>> orEntries = new AxeOrd<AxeValue<Integer>>("c");
//		orEntries.add(new AxeValue<Integer>(0));
//		orEntries.add(new AxeValue<Integer>(1));
//		Axe<AxeValue<Integer>> orOutputs = new AxeOrd<AxeValue<Integer>>("d");
//		orOutputs.add(new AxeValue<Integer>(2));
//		MedNode orNode = new MedNode("orNode", orEntries, orOutputs, or);
//	
//		Axe<AxeValue<Integer>> xorExEntries = new AxeOrd<AxeValue<Integer>>("e");
//		xorExEntries.add(new AxeValue<Integer>(0));
//		xorExEntries.add(new AxeValue<Integer>(1));
//		Axe<AxeValue<Integer>> xorExOutputs = new AxeOrd<AxeValue<Integer>>("f");
//		xorExOutputs.add(new AxeValue<Integer>(2));
//		MedNode xorExNode = new MedNode("xorExNode", xorExEntries, xorExOutputs, xorEx);
	
		
//		ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>> glueArray = (ArrayXDOrd<Float, Integer, Axe<AxeValue<Integer>>>) ArrayFactory
//				.newInstanceArrayLogigramValueMapX2IntegerWithFloat(domNeuroneX, domNeuroneY);
//		glueArray.setValue(1f, andNode.getOutputs().getElements().get(0).getValue(),  xorExNode.getEntries().getElements().get(0).getValue());
//		glueArray.setValue(1f, andNode.getOutputs().getElements().get(0).getValue(),  xorExNode.getEntries().getElements().get(1).getValue());
		
		ArrayXDOrd indicesAnd0 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesAnd0.setValue(0f, 0, 0);
		CoordinatesXDSpaceByIndices coordAnd0 = new CoordinatesXDSpaceByIndices(and, indicesAnd0);
	
		ArrayXDOrd indicesAnd1 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesAnd1.setValue(0f, 1, 1);
		CoordinatesXDSpaceByIndices coordAnd1 = new CoordinatesXDSpaceByIndices(and, indicesAnd1);
	
		ArrayXDOrd indicesAnd2 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesAnd2.setValue(0f, 2, 2);
		CoordinatesXDSpaceByIndices coordAnd2 = new CoordinatesXDSpaceByIndices(and, indicesAnd2);
	
		//idem Or
		
		ArrayXDOrd indicesOr0 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesOr0.setValue(0f, 0, 0);
		CoordinatesXDSpaceByIndices coordOr0 = new CoordinatesXDSpaceByIndices(or, indicesOr0);
	
		ArrayXDOrd indicesOr1 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesOr1.setValue(0f, 1, 1);
		CoordinatesXDSpaceByIndices coordOr1 = new CoordinatesXDSpaceByIndices(or, indicesOr1);
	
		ArrayXDOrd indicesOr2 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesOr2.setValue(0f, 2, 2);
		CoordinatesXDSpaceByIndices coordOr2 = new CoordinatesXDSpaceByIndices(or, indicesOr2);
		
		//idem Ex
		
		ArrayXDOrd indicesXorEx0 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesXorEx0.setValue(0f, 0, 0);
		CoordinatesXDSpaceByIndices coordXorEx0 = new CoordinatesXDSpaceByIndices(xorEx, indicesXorEx0);
	
		ArrayXDOrd indicesXorEx1 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesXorEx1.setValue(0f, 1, 1);
		CoordinatesXDSpaceByIndices coordXorEx1 = new CoordinatesXDSpaceByIndices(xorEx, indicesXorEx1);
	
		ArrayXDOrd indicesXorEx2 = new Array2DMatrix3fImpl<>(domNeuroneX, domNeuroneY);
		indicesXorEx2.setValue(0f, 2, 2);
		CoordinatesXDSpaceByIndices coordXorEx2 = new CoordinatesXDSpaceByIndices(xorEx, indicesXorEx2);
	
		
		Axe<AxeValue<CoordinatesXDSpaceByIndices>> coordX = new AxeOrd<AxeValue<CoordinatesXDSpaceByIndices>>("coordX");
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordAnd0));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordAnd1));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordAnd2));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordOr0));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordOr1));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordOr2));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordXorEx0));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordXorEx1));
		coordX.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordXorEx2));
		Axe<AxeValue<CoordinatesXDSpaceByIndices>> coordY = new AxeOrd<AxeValue<CoordinatesXDSpaceByIndices>>("coordY");
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordAnd0));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordAnd1));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordAnd2));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordOr0));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordOr1));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordOr2));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordXorEx0));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordXorEx1));
		coordY.add(new AxeValue<CoordinatesXDSpaceByIndices>(coordXorEx2));
		
		ArrayXDOrd<Float, CoordinatesXDSpaceByIndices, Axe<AxeValue<CoordinatesXDSpaceByIndices>>> glueArray = (ArrayXDOrd<Float, CoordinatesXDSpaceByIndices, Axe<AxeValue<CoordinatesXDSpaceByIndices>>>) ArrayFactory
				.newInstanceArrayLogigramValueMapX2IntegerWithFloat(coordX, coordY);
		glueArray.setValue(1f, coordAnd2,  coordXorEx0);
		glueArray.setValue(1f, coordOr2,  coordXorEx1);
		
//		Axe<AxeValue<Integer>> allEntries = new AxeOrd<AxeValue<Integer>>("e");
//		allEntries.add(new AxeValue<Integer>(0));
//		allEntries.add(new AxeValue<Integer>(1));
//		Axe<AxeValue<Integer>> allOutputs = new AxeOrd<AxeValue<Integer>>("f");
//		allOutputs.add(new AxeValue<Integer>(2));
//		MedNode allNode = new MedNode("allNode", allEntries, allOutputs, all);
//	
		
		Map<CoordinatesXDSpaceByIndices, Float> step0 = new HashMap<>();
		step0.put(coordAnd0, noPulseIntensity);
		step0.put(coordAnd1, pulseIntensity);
		step0.put(coordOr0, noPulseIntensity);
		step0.put(coordOr1, pulseIntensity);

		List<Map<CoordinatesXDSpaceByIndices, Float>> stepsFinal = new ArrayList<>();
		nexec.simul(stepsFinal, glueArray, step0, agre, activ, false);
		Map<CoordinatesXDSpaceByIndices, Float> lastStep = stepsFinal.get(stepsFinal.size() - 1);
		System.out.println("activate " + lastStep.toString());
	
	}

}
