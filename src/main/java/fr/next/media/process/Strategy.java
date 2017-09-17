package fr.next.media.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;

public class Strategy {
	
	private List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2D;

	public Strategy(History history, List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2D) {
		super();
		this.arrays2D = arrays2D;
	}


	public void strategyExclusionByLine(ResultStrategy rGlobal, List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2DWithColFixed) {
		int index = 0;
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2DWithColFixed : arrays2DWithColFixed) {
			for (int j = index + 1; j < arrays2DWithColFixed.size(); j++) {
				ResultStrategy r = analyseExclusionLine(array2DWithColFixed, arrays2DWithColFixed.get(j));
				rGlobal.setFindNewCase(r.isFindNewCase() || rGlobal.isFindNewCase());
			}
			index++;
		}
	}
	
	
	private ResultStrategy analyseExclusionLine(ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cEC, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cCC) {
		ResultStrategy r = new ResultStrategy();
		int col = cEC.getAxe(1).getElements().size();

		for (int i = 0; i < cEC.getAxe(0).getElements().size(); i++) {
			List<ArrayLogigramValue> c = cEC.getValuesForAnAxe(0, i);
			for (int j = 0; j < cCC.getAxe(0).getElements().size(); j++) {
				boolean match = true;
				List<Case> casesInContext = new ArrayList<>();
				List<ArrayLogigramValue> cC = cCC.getValuesForAnAxe(0, j);
				for (int k = 0; k < col; k++) {
					if ((cC.get(k).equals(ArrayLogigramValue.EMPTY) || cC.get(k).equals(ArrayLogigramValue.OK))
							&& (c.get(k).equals(ArrayLogigramValue.EMPTY) || c.get(k).equals(ArrayLogigramValue.OK))) {
						match = false;
						break;
					}
					casesInContext.add(new Case(cCC, cC.get(k), cCC.getCoordinates().getIndex(0) + j, cCC.getCoordinates().getIndex(1) +k));
					casesInContext.add(new Case(cEC, c.get(k), cEC.getCoordinates().getIndex(0) + i, cEC.getCoordinates().getIndex(1) +k));
				}
				if (match) {
					ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cECl = getArray2D(cEC.getAxe(0), cCC.getAxe(0));
					int indexLine = i;
					int indexCol = j;
					if (cECl == null) {
						cECl = getArray2D(cCC.getAxe(0), cEC.getAxe(0));
						indexLine = j;
						indexCol = i;
					}
					if (cECl.getValueByIndices(indexLine, indexCol).equals(ArrayLogigramValue.EMPTY)) {
						r.setFindNewCase(true);
						cECl.setValueByIndices(ArrayLogigramValue.NEG, indexLine, indexCol);
						addAction(r, "Exclusion de deux colonnes alors la case correspondante des deux éléments est N",
								indexLine, indexCol, cECl, ArrayLogigramValue.NEG, casesInContext);
					} else if (cECl.getValueByIndices(indexLine, indexCol).equals(ArrayLogigramValue.OK)) {
						throw new AssertionError();
					}

				}
			}
		}
		return r;
	}
	
	
	public ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> getArray2D(Axe domainLine, Axe domainCol) {
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c : arrays2D) {
			if ((!domainLine.equals(domainCol)) && (c.getAxe(0).equals(domainLine)
					&& c.getAxe(1).equals(domainCol))) {
				return c;
			}
		}
		return null;
	}
	
	private void addAction(ResultStrategy r, String action, int indexLine, int indexCol, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2D, ArrayLogigramValue value, List<Case> casesInContext) {
		Action e = new Action(action, indexLine, indexCol, array2D, value, casesInContext);
		r.getActions().add(e);
	}
	
	public void strategyExclusionByCol(ResultStrategy rGlobal, List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2DWithLineFixed) {
		int index = 0;
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> array2DWithLineFixed : arrays2DWithLineFixed) {
			for (int j = index + 1; j < arrays2DWithLineFixed.size(); j++) {
				ResultStrategy r = analyseExclusionCol(array2DWithLineFixed, arrays2DWithLineFixed.get(j));
				rGlobal.setFindNewCase(r.isFindNewCase() || rGlobal.isFindNewCase());
			}
			index++;
		}
	}

	private ResultStrategy analyseExclusionCol(ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cEC, ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cCC) {
		ResultStrategy r = new ResultStrategy();
		int line = cEC.getAxe(0).getElements().size();

		for (int i = 0; i < cEC.getAxe(1).getElements().size(); i++) {
			List<ArrayLogigramValue> c = cEC.getValuesForAnAxe(1, i);
			for (int j = 0; j < cCC.getAxe(1).getElements().size(); j++) {
				boolean match = true;
				List<Case> casesInContext = new ArrayList<>();
				List<ArrayLogigramValue> cC = cCC.getValuesForAnAxe(1, j);
				for (int k = 0; k < line; k++) {
					if ((cC.get(k).equals(ArrayLogigramValue.EMPTY) || cC.get(k).equals(ArrayLogigramValue.OK))
							&& (c.get(k).equals(ArrayLogigramValue.EMPTY) || c.get(k).equals(ArrayLogigramValue.OK))) {
						match = false;
						break;
					}
					casesInContext.add(new Case(cCC, cC.get(k), cCC.getCoordinates().getIndex(0) + k, cCC.getCoordinates().getIndex(1) + j));
					casesInContext.add(new Case(cEC, c.get(k), cEC.getCoordinates().getIndex(0) + k, cEC.getCoordinates().getIndex(1) + i));
				}
				if (match) {
					ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cECl = getArray2D(cEC.getAxe(1), cCC.getAxe(1));
					int indexLine = i;
					int indexCol = j;
					if (cECl == null) {
						cECl = getArray2D(cCC.getAxe(1), cEC.getAxe(1));
						indexLine = j;
						indexCol = i;
					}
					if (cECl.getValueByIndices(indexLine, indexCol).equals(ArrayLogigramValue.EMPTY)) {
						r.setFindNewCase(true);
						cECl.setValueByIndices(ArrayLogigramValue.NEG, indexLine, indexCol);
						addAction(r, "Exclusion de deux lignes alors la case correspondante des deux éléments est N",
								indexLine, indexCol, cECl, ArrayLogigramValue.NEG, casesInContext);
					} else if (cECl.getValueByIndices(indexLine, indexCol).equals(ArrayLogigramValue.OK)) {
						throw new AssertionError();
					}
				}
			}
		}
		return r;
	}
	
	
	
	
	public ResultStrategy strategyFill() {
		ResultStrategy r = new ResultStrategy();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c : arrays2D) {
			for (int i = 0; i < c.getAxe(0).getElements().size(); i++) {
				for (int k = 0; k < c.getAxe(1).getElements().size(); k++) {
					if (c.getValueByIndices(i, k).equals(ArrayLogigramValue.OK)) {
						for (int n = 0; n < c.getAxe(1).getElements().size(); n++) {
							if (n != k) {
								if (c.getValueByIndices(i, n).equals(ArrayLogigramValue.EMPTY)) {
									r.setFindNewCase(true);
									addAction(r, "OK trouvé alors toute la colonne N", i, n, c, ArrayLogigramValue.NEG,
											new ArrayList<>());
									c.setValueByIndices(ArrayLogigramValue.NEG, i, n);
								} else if (c.getValueByIndices(i, n).equals(ArrayLogigramValue.OK)) {
									throw new AssertionError();
								}
							}
						}
						for (int m = 0; m < c.getAxe(0).getElements().size(); m++) {
							if (m != i) {
								if (c.getValueByIndices(m, k).equals(ArrayLogigramValue.EMPTY)) {
									r.setFindNewCase(true);
									addAction(r, "OK trouvé alors toute la ligne N", m, k, c, ArrayLogigramValue.NEG, new ArrayList<>());
									c.setValueByIndices(ArrayLogigramValue.NEG, m, k);
								} else if (c.getValueByIndices(m, k).equals(ArrayLogigramValue.OK)) {
									throw new AssertionError();
								}
							}
						}
					}
				}
			}
		}
		return r;
	}
	
	
	public ResultStrategy strategyFillIfAllNByLine() {
		ResultStrategy r = new ResultStrategy();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c : arrays2D) {
			for (int i = 0; i < c.getAxe(1).getElements().size(); i++) {
				int count = 0;
				int indexEmpty = -1;
				for (int k = 0; k < c.getAxe(0).getElements().size(); k++) {
					if (c.getValueByIndices(k, i).equals(ArrayLogigramValue.NEG)) {
						count++;
					} else {
						indexEmpty = k;
					}
				}
				if (count == c.getAxe(0).getElements().size() - 1) {
					if (c.getValueByIndices(indexEmpty, i).equals(ArrayLogigramValue.EMPTY)) {
						r.setFindNewCase(true);
						addAction(r, "Tous N sauf 1 VIDE (en ligne), alors ce dernier est OK", indexEmpty, i, c, ArrayLogigramValue.OK,
								new ArrayList<>());
						c.setValueByIndices(ArrayLogigramValue.OK, indexEmpty, i);
					} else if (c.getValueByIndices(indexEmpty, i).equals(ArrayLogigramValue.NEG)) {
						throw new AssertionError();
					}
				}
			}
		}
		return r;
	}
	
	public ResultStrategy strategyExclusionIfOnlyThreeAndMorePossibilityByLine() {
		ResultStrategy r = new ResultStrategy();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c : arrays2D) {
			for (int i = 0; i < c.getAxe(0).getElements().size(); i++) {
				List<LineMatching> linesMatching = new ArrayList<>();
				for (int j = i + 1; j < c.getAxe(0).getElements().size(); j++) {
					int count = 0;
					List<Integer> emptyCol = new ArrayList<>();
					boolean match = true;
					List<Case> casesInContext = new ArrayList<>();
					for (int k = 0; k < c.getAxe(1).getElements().size(); k++) {
						if (c.getValueByIndices(i, k).equals(ArrayLogigramValue.NEG) && c.getValueByIndices(j, k).equals(ArrayLogigramValue.NEG)) {
							count++;
							casesInContext.add(new Case(c, c.getValuesForAnAxe(0, i).get(k), c.getCoordinates().getIndex(0) + i, c.getCoordinates().getIndex(1) + k));
							casesInContext.add(new Case(c, c.getValuesForAnAxe(0, j).get(k), c.getCoordinates().getIndex(0) + j, c.getCoordinates().getIndex(1) + k));
							
						} else if (c.getValueByIndices(i, k).equals(ArrayLogigramValue.EMPTY) && c.getValueByIndices(j, k).equals(ArrayLogigramValue.EMPTY)) {
							emptyCol.add(k);
						} else {
							match = false;
							break;
						}
					}
					if (match) {
						LineMatching l = new LineMatching();
						l.setCount(count);
						l.setCasesInContext(casesInContext);
						l.setEmptyCol(emptyCol);
						l.setIndex(j);
						linesMatching.add(l);
					}
				}
				if (linesMatching.size() >= 1 && linesMatching.get(0).getCount() >= c.getAxe(1).getElements().size() - (linesMatching.size() + 1)) {
					Integer[] countMatching = new Integer[c.getAxe(1).getElements().size() + 1];
					for (int h = 0; h < countMatching.length; h++) {
						countMatching[h] = 0;
					}
	
					for (LineMatching l : linesMatching) {
						int size = l.getEmptyCol().size();
						countMatching[size] = countMatching[size] + 1;
					}
				

					int maxIndex = findMaxIndex(countMatching);
					int max = countMatching[maxIndex];
					Set<Case> casesInContext = new HashSet<>();
					for (LineMatching l : linesMatching) {
						casesInContext.addAll(l.getCasesInContext());
					}

					if (linesMatching.size() == max) {
						for (Integer col : linesMatching.get(0).getEmptyCol()) {
							for (int f = 0; f < c.getAxe(0).getElements().size(); f++) {
								if (f != i && !isIndexLine(linesMatching, f)) {
									if (c.getValueByIndices(f, col).equals(ArrayLogigramValue.EMPTY)) {
										c.setValueByIndices(ArrayLogigramValue.NEG, f, col);
										r.setFindNewCase(true);
										addAction(r, 
												"Deux possibilités sur deux lignes alors exclusion des autres lignes du même array2D",
												f, col, c, ArrayLogigramValue.NEG,
												Arrays.asList(casesInContext.toArray(new Case[0])));
									} else if (c.getValueByIndices(f, col).equals(ArrayLogigramValue.OK)) {
										throw new AssertionError();
									}
								}
							}
						}
					}
				}
			}
		}
		return r;
	}
	
	
	public ResultStrategy strategyExclusionIfOnlyThreeAndMorePossibilityByCol() {
		ResultStrategy r = new ResultStrategy();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c : arrays2D) {
			for (int i = 0; i < c.getAxe(1).getElements().size(); i++) {
				List<LineMatching> linesMatching = new ArrayList<>();
				for (int j = i + 1; j < c.getAxe(1).getElements().size(); j++) {
					int count = 0;
					List<Integer> emptyCol = new ArrayList<>();
					boolean match = true;
					List<Case> casesInContext = new ArrayList<>();
					for (int k = 0; k < c.getAxe(0).getElements().size(); k++) {
						if (c.getValueByIndices(k, i).equals(ArrayLogigramValue.NEG) && c.getValueByIndices(k, j).equals(ArrayLogigramValue.NEG)) {
							count++;
							casesInContext.add(new Case(c, c.getValuesForAnAxe(1, i).get(k), c.getCoordinates().getIndex(0) + k, c.getCoordinates().getIndex(1) +i));
							casesInContext.add(new Case(c, c.getValuesForAnAxe(1, j).get(k), c.getCoordinates().getIndex(0) + k, c.getCoordinates().getIndex(1) + j));
						} else if (c.getValueByIndices(k, i).equals(ArrayLogigramValue.EMPTY) && c.getValueByIndices(k, j).equals(ArrayLogigramValue.EMPTY)) {
							emptyCol.add(k);
						} else {
							match = false;
							break;
						}
					}
					if (match) {
						LineMatching l = new LineMatching();
						l.setCount(count);
						l.setCasesInContext(casesInContext);
						l.setEmptyCol(emptyCol);
						l.setIndex(j);
						linesMatching.add(l);
					}
				}
				if (linesMatching.size() >= 1 && linesMatching.get(0).getCount() >= c.getAxe(0).getElements().size() - (linesMatching.size() + 1)) {
					Integer[] countMatching = new Integer[c.getAxe(0).getElements().size() + 1];
					for (int h = 0; h < countMatching.length; h++) {
						countMatching[h] = 0;
					}
	
					for (LineMatching l : linesMatching) {
						int size = l.getEmptyCol().size();
						countMatching[size] = countMatching[size] + 1;
					}
				

					int maxIndex = findMaxIndex(countMatching);
					int max = countMatching[maxIndex];
					Set<Case> casesInContext = new HashSet<>();
					for (LineMatching l : linesMatching) {
						casesInContext.addAll(l.getCasesInContext());
					}

					if (linesMatching.size() == max) {
						for (Integer col : linesMatching.get(0).getEmptyCol()) {
							for (int f = 0; f < c.getAxe(1).getElements().size(); f++) {
								if (f != i && !isIndexLine(linesMatching, f)) {
									if (c.getValueByIndices(col, f).equals(ArrayLogigramValue.EMPTY)) {
										c.setValueByIndices(ArrayLogigramValue.NEG, col, f);
										r.setFindNewCase(true);
										addAction(r, 
												"Deux possibilités sur deux colonnes alors exclusion des autres colonnes du même array2D",
												col, f, c, ArrayLogigramValue.NEG,
												Arrays.asList(casesInContext.toArray(new Case[0])));
									} else if (c.getValueByIndices(col, f).equals(ArrayLogigramValue.OK)) {
										throw new AssertionError();
									}
								}
							}
						}
					}
				}
			}
		}
		return r;
	}
	
	
	public ResultStrategy strategyFillIfAllNByCol() {
		ResultStrategy r = new ResultStrategy();
		for (ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c : arrays2D) {
			for (int i = 0; i < c.getAxe(0).getElements().size(); i++) {
				int count = 0;
				int indexEmpty = -1;
				for (int k = 0; k < c.getAxe(1).getElements().size(); k++) {
					if (c.getValueByIndices(i, k).equals(ArrayLogigramValue.NEG)) {
						count++;
					} else {
						indexEmpty = k;
					}
				}
				if (count == c.getAxe(1).getElements().size() - 1) {
					if (c.getValueByIndices(i, indexEmpty).equals(ArrayLogigramValue.EMPTY)) {
						r.setFindNewCase(true);
						addAction(r, "Tous N sauf 1 VIDE (en colonne), alors ce dernier est OK", i, indexEmpty, c, ArrayLogigramValue.OK,
								new ArrayList<>());
						c.setValueByIndices(ArrayLogigramValue.OK, i, indexEmpty);
					} else if (c.getValueByIndices(i, indexEmpty).equals(ArrayLogigramValue.NEG)) {
						throw new AssertionError();
					}
				}
			}
		}
		return r;
	}
	
	private boolean isIndexLine(List<LineMatching> linesMatching, int index) {
		for (LineMatching l : linesMatching) {
			if (l.getIndex() == index) {
				return true;
			}
		}
		return false;
	}

	private int findMaxIndex(Integer[] countMatching) {
		int max = 0;
		int maxIndex = 0;
		int index = 0;
		for (int count : countMatching) {
			if (count >= max) {
				max = count;
				maxIndex = index;
			}
			
			index++;
		}
		return maxIndex;
	}
}
