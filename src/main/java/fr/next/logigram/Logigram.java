package fr.next.logigram;

import java.util.List;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.AxeOrdNum;
import fr.next.logigram.array.AxeValue;
import fr.next.logigram.array.logigram.Array2DOrdValue;
import fr.next.logigram.draw.Draw;
import fr.next.logigram.process.Action;
import fr.next.logigram.process.Case;
import fr.next.logigram.process.History;
import fr.next.logigram.process.ResultStrategy;
import fr.next.logigram.process.Strategy;

public class Logigram {
	
	private History history;
	private Strategy strategy;
	private Array2DWorld array2DWorld;

	public Logigram(History history, Array2DWorld array2DWorld) {
		this.history = history;
		this.array2DWorld = array2DWorld;
		
		strategy = new Strategy(history, array2DWorld.getArrays2D());
	}

	
	public ResultStrategy strategyExclusionByLine() {
		ResultStrategy rGlobal = new ResultStrategy();
		for(Axe<AxeValue<String>> domain : array2DWorld.getDomains()) {
			List<Array2DOrd<Array2DOrdValue, String>> arraysWithColFixed = array2DWorld.findAllArrays2DWithDomainInCol(domain);
			strategy.strategyExclusionByLine(rGlobal, arraysWithColFixed);
		}
		return rGlobal;
	}

	public ResultStrategy strategyExclusionByCol() {
		ResultStrategy rGlobal = new ResultStrategy();
		for(Axe<AxeValue<String>> domain : array2DWorld.getDomains()) {
			List<Array2DOrd<Array2DOrdValue,String>> arrays2DWithLineFixed = array2DWorld.findAllArrays2DWithDomainInLine(domain);
			strategy.strategyExclusionByCol(rGlobal, arrays2DWithLineFixed);
		}
		return rGlobal;
	}

	public History getHistory() {
		return history;
	}

	public void doResolution() {
		ResultStrategy rGlobal = new ResultStrategy();
		// first loop
		rGlobal.setFindNewCase(true);

		while (rGlobal.isFindNewCase()) {
			rGlobal = new ResultStrategy();
			ResultStrategy sEC = strategyExclusionByLine();
			rGlobal.setFindNewCase(sEC.isFindNewCase() || rGlobal.isFindNewCase());
			display(sEC);

			ResultStrategy sEL = strategyExclusionByCol();
			rGlobal.setFindNewCase(sEL.isFindNewCase() || rGlobal.isFindNewCase());
			display(sEL);

			ResultStrategy sEIOTAMPCol = strategy.strategyExclusionIfOnlyThreeAndMorePossibilityByCol();
			rGlobal.setFindNewCase(sEIOTAMPCol.isFindNewCase() || rGlobal.isFindNewCase());
			display(sEIOTAMPCol);

			ResultStrategy sEIOTAMPL = strategy.strategyExclusionIfOnlyThreeAndMorePossibilityByLine();
			rGlobal.setFindNewCase(sEIOTAMPL.isFindNewCase() || rGlobal.isFindNewCase());
			display(sEIOTAMPL);

			ResultStrategy sFillCol = strategy.strategyFillIfAllNByCol();
			rGlobal.setFindNewCase(sFillCol.isFindNewCase() || rGlobal.isFindNewCase());
			display(sFillCol);

			ResultStrategy sFillLine = strategy.strategyFillIfAllNByLine();
			rGlobal.setFindNewCase(sFillLine.isFindNewCase() || rGlobal.isFindNewCase());
			display(sFillLine);

			ResultStrategy sFill = strategy.strategyFill();
			rGlobal.setFindNewCase(sFill.isFindNewCase() || rGlobal.isFindNewCase());
			display(sFill);
		}

	}

	private void display(ResultStrategy sEC) {
		for (Action a : sEC.getActions()) {
			addAction(a.getAction(), a.getIndexLine(), a.getIndexCol(), a.getArray(), a.getValue(), a.getCasesInContext());
		}
	}

	private void addAction(String action, int i, int j, Array2DOrd<Array2DOrdValue, String> array2D, Array2DOrdValue value, List<Case> casesInContext) {
		StringBuilder strB = new StringBuilder();
		for (Case c : casesInContext) {
			if (strB.length() != 0) {
				strB.append("/");
			}
			strB.append(Draw.getAlpha(c.getIndexLine()) + (c.getIndexCol()+ 1));
		}
		history.addAction(action + " " + array2D.getAxeLine().toString() + ", " + array2D.getAxeCol().toString() + ", "
				+ " col " + j +  "  line " + (i) + ", " + value + "  > "
				+ strB.toString());
	}


	public void draw(int maxLabel) {
		Draw.draw(maxLabel, array2DWorld.getDomains().size(), array2DWorld.getArrays2D());
	}

}
