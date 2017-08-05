package fr.next.logigram.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.next.logigram.array.Array2DOrd;
import fr.next.logigram.array.Axe;
import fr.next.logigram.array.logigram.Array2DOrdValue;

public class Strategy {
	
	private List<Array2DOrd<Array2DOrdValue, String>> arrays2D;

	public Strategy(History history, List<Array2DOrd<Array2DOrdValue, String>> arrays2D) {
		super();
		this.arrays2D = arrays2D;
	}


	public void strategyExclusionByLine(ResultStrategy rGlobal, List<Array2DOrd<Array2DOrdValue, String>> arrays2DWithColFixed) {
		int index = 0;
		for (Array2DOrd<Array2DOrdValue, String> array2DWithColFixed : arrays2DWithColFixed) {
			for (int j = index + 1; j < arrays2DWithColFixed.size(); j++) {
				ResultStrategy r = analyseExclusionLine(array2DWithColFixed, arrays2DWithColFixed.get(j));
				rGlobal.setFindNewCase(r.isFindNewCase() || rGlobal.isFindNewCase());
			}
			index++;
		}
	}
	
	
	private ResultStrategy analyseExclusionLine(Array2DOrd<Array2DOrdValue, String> cEC, Array2DOrd<Array2DOrdValue, String> cCC) {
		ResultStrategy r = new ResultStrategy();
		int col = cEC.getAxeCol().getElements().size();

		for (int i = 0; i < cEC.getAxeLine().getElements().size(); i++) {
			Array2DOrdValue[] c = cEC.getLine(i);
			for (int j = 0; j < cCC.getAxeLine().getElements().size(); j++) {
				boolean match = true;
				List<Case> casesInContext = new ArrayList<>();
				Array2DOrdValue[] cC = cCC.getLine(j);
				for (int k = 0; k < col; k++) {
					if ((cC[k].equals(Array2DOrdValue.EMPTY) || cC[k].equals(Array2DOrdValue.OK))
							&& (c[k].equals(Array2DOrdValue.EMPTY) || c[k].equals(Array2DOrdValue.OK))) {
						match = false;
						break;
					}
					casesInContext.add(new Case(cCC, cC[k], cCC.getCoordinates().getIndexLine() + j, cCC.getCoordinates().getIndexCol() +k));
					casesInContext.add(new Case(cEC, c[k], cEC.getCoordinates().getIndexLine() + i, cEC.getCoordinates().getIndexCol() +k));
				}
				if (match) {
					Array2DOrd<Array2DOrdValue, String> cECl = getArray2D(cEC.getAxeLine(), cCC.getAxeLine());
					int indexLine = i;
					int indexCol = j;
					if (cECl == null) {
						cECl = getArray2D(cCC.getAxeLine(), cEC.getAxeLine());
						indexLine = j;
						indexCol = i;
					}
					if (cECl.getValue(indexLine, indexCol).equals(Array2DOrdValue.EMPTY)) {
						r.setFindNewCase(true);
						cECl.setValue(indexLine, indexCol, Array2DOrdValue.NEG);
						addAction(r, "Exclusion de deux colonnes alors la case correspondante des deux éléments est N",
								indexLine, indexCol, cECl, Array2DOrdValue.NEG, casesInContext);
					} else if (cECl.getValue(indexLine, indexCol).equals(Array2DOrdValue.OK)) {
						throw new AssertionError();
					}

				}
			}
		}
		return r;
	}
	
	
	public Array2DOrd<Array2DOrdValue, String> getArray2D(Axe domainLine, Axe domainCol) {
		for (Array2DOrd<Array2DOrdValue, String> c : arrays2D) {
			if ((!domainLine.equals(domainCol)) && (c.getAxeLine().equals(domainLine)
					&& c.getAxeCol().equals(domainCol))) {
				return c;
			}
		}
		return null;
	}
	
	private void addAction(ResultStrategy r, String action, int indexLine, int indexCol, Array2DOrd<Array2DOrdValue, String> array2D, Array2DOrdValue value, List<Case> casesInContext) {
		Action e = new Action(action, indexLine, indexCol, array2D, value, casesInContext);
		r.getActions().add(e);
	}
	
	public void strategyExclusionByCol(ResultStrategy rGlobal, List<Array2DOrd<Array2DOrdValue, String>> arrays2DWithLineFixed) {
		int index = 0;
		for (Array2DOrd<Array2DOrdValue, String> array2DWithLineFixed : arrays2DWithLineFixed) {
			for (int j = index + 1; j < arrays2DWithLineFixed.size(); j++) {
				ResultStrategy r = analyseExclusionCol(array2DWithLineFixed, arrays2DWithLineFixed.get(j));
				rGlobal.setFindNewCase(r.isFindNewCase() || rGlobal.isFindNewCase());
			}
			index++;
		}
	}

	private ResultStrategy analyseExclusionCol(Array2DOrd<Array2DOrdValue, String> cEC, Array2DOrd<Array2DOrdValue, String> cCC) {
		ResultStrategy r = new ResultStrategy();
		int line = cEC.getAxeLine().getElements().size();

		for (int i = 0; i < cEC.getAxeCol().getElements().size(); i++) {
			Array2DOrdValue[] c = cEC.getCol(i);
			for (int j = 0; j < cCC.getAxeCol().getElements().size(); j++) {
				boolean match = true;
				List<Case> casesInContext = new ArrayList<>();
				Array2DOrdValue[] cC = cCC.getCol(j);
				for (int k = 0; k < line; k++) {
					if ((cC[k].equals(Array2DOrdValue.EMPTY) || cC[k].equals(Array2DOrdValue.OK))
							&& (c[k].equals(Array2DOrdValue.EMPTY) || c[k].equals(Array2DOrdValue.OK))) {
						match = false;
						break;
					}
					casesInContext.add(new Case(cCC, cC[k], cCC.getCoordinates().getIndexLine() + k, cCC.getCoordinates().getIndexCol() + j));
					casesInContext.add(new Case(cEC, c[k], cEC.getCoordinates().getIndexLine() + k, cEC.getCoordinates().getIndexCol() + i));
				}
				if (match) {
					Array2DOrd<Array2DOrdValue, String> cECl = getArray2D(cEC.getAxeCol(), cCC.getAxeCol());
					int indexLine = i;
					int indexCol = j;
					if (cECl == null) {
						cECl = getArray2D(cCC.getAxeCol(), cEC.getAxeCol());
						indexLine = j;
						indexCol = i;
					}
					if (cECl.getValue(indexLine, indexCol).equals(Array2DOrdValue.EMPTY)) {
						r.setFindNewCase(true);
						cECl.setValue(indexLine, indexCol, Array2DOrdValue.NEG);
						addAction(r, "Exclusion de deux lignes alors la case correspondante des deux éléments est N",
								indexLine, indexCol, cECl, Array2DOrdValue.NEG, casesInContext);
					} else if (cECl.getValue(indexLine, indexCol).equals(Array2DOrdValue.OK)) {
						throw new AssertionError();
					}
				}
			}
		}
		return r;
	}
	
	
	
	
	public ResultStrategy strategyFill() {
		ResultStrategy r = new ResultStrategy();
		for (Array2DOrd<Array2DOrdValue, String> c : arrays2D) {
			for (int i = 0; i < c.getAxeLine().getElements().size(); i++) {
				for (int k = 0; k < c.getAxeCol().getElements().size(); k++) {
					if (c.getValue(i, k).equals(Array2DOrdValue.OK)) {
						for (int n = 0; n < c.getAxeCol().getElements().size(); n++) {
							if (n != k) {
								if (c.getValue(i, n).equals(Array2DOrdValue.EMPTY)) {
									r.setFindNewCase(true);
									addAction(r, "OK trouvé alors toute la colonne N", i, n, c, Array2DOrdValue.NEG,
											new ArrayList<>());
									c.setValue(i, n, Array2DOrdValue.NEG);
								} else if (c.getValue(i, n).equals(Array2DOrdValue.OK)) {
									throw new AssertionError();
								}
							}
						}
						for (int m = 0; m < c.getAxeLine().getElements().size(); m++) {
							if (m != i) {
								if (c.getValue(m, k).equals(Array2DOrdValue.EMPTY)) {
									r.setFindNewCase(true);
									addAction(r, "OK trouvé alors toute la ligne N", m, k, c, Array2DOrdValue.NEG, new ArrayList<>());
									c.setValue(m, k, Array2DOrdValue.NEG);
								} else if (c.getValue(m, k).equals(Array2DOrdValue.OK)) {
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
		for (Array2DOrd<Array2DOrdValue, String> c : arrays2D) {
			for (int i = 0; i < c.getAxeCol().getElements().size(); i++) {
				int count = 0;
				int indexEmpty = -1;
				for (int k = 0; k < c.getAxeLine().getElements().size(); k++) {
					if (c.getValue(k, i).equals(Array2DOrdValue.NEG)) {
						count++;
					} else {
						indexEmpty = k;
					}
				}
				if (count == c.getAxeLine().getElements().size() - 1) {
					if (c.getValue(indexEmpty, i).equals(Array2DOrdValue.EMPTY)) {
						r.setFindNewCase(true);
						addAction(r, "Tous N sauf 1 VIDE (en ligne), alors ce dernier est OK", indexEmpty, i, c, Array2DOrdValue.OK,
								new ArrayList<>());
						c.setValue(indexEmpty, i, Array2DOrdValue.OK);
					} else if (c.getValue(indexEmpty, i).equals(Array2DOrdValue.NEG)) {
						throw new AssertionError();
					}
				}
			}
		}
		return r;
	}
	
	public ResultStrategy strategyExclusionIfOnlyThreeAndMorePossibilityByLine() {
		ResultStrategy r = new ResultStrategy();
		for (Array2DOrd<Array2DOrdValue, String> c : arrays2D) {
			for (int i = 0; i < c.getAxeLine().getElements().size(); i++) {
				List<LineMatching> linesMatching = new ArrayList<>();
				for (int j = i + 1; j < c.getAxeLine().getElements().size(); j++) {
					int count = 0;
					List<Integer> emptyCol = new ArrayList<>();
					boolean match = true;
					List<Case> casesInContext = new ArrayList<>();
					for (int k = 0; k < c.getAxeCol().getElements().size(); k++) {
						if (c.getValue(i, k).equals(Array2DOrdValue.NEG) && c.getValue(j, k).equals(Array2DOrdValue.NEG)) {
							count++;
							casesInContext.add(new Case(c, c.getLine(i)[k], c.getCoordinates().getIndexLine() + i, c.getCoordinates().getIndexCol() + k));
							casesInContext.add(new Case(c, c.getLine(j)[k], c.getCoordinates().getIndexLine() + j, c.getCoordinates().getIndexCol() + k));
							
						} else if (c.getValue(i, k).equals(Array2DOrdValue.EMPTY) && c.getValue(j, k).equals(Array2DOrdValue.EMPTY)) {
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
				if (linesMatching.size() >= 1 && linesMatching.get(0).getCount() >= c.getAxeCol().getElements().size() - (linesMatching.size() + 1)) {
					Integer[] countMatching = new Integer[c.getAxeCol().getElements().size() + 1];
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
							for (int f = 0; f < c.getAxeLine().getElements().size(); f++) {
								if (f != i && !isIndexLine(linesMatching, f)) {
									if (c.getValue(f, col).equals(Array2DOrdValue.EMPTY)) {
										c.setValue(f, col, Array2DOrdValue.NEG);
										r.setFindNewCase(true);
										addAction(r, 
												"Deux possibilités sur deux lignes alors exclusion des autres lignes du même array2D",
												f, col, c, Array2DOrdValue.NEG,
												Arrays.asList(casesInContext.toArray(new Case[0])));
									} else if (c.getValue(f, col).equals(Array2DOrdValue.OK)) {
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
		for (Array2DOrd<Array2DOrdValue, String> c : arrays2D) {
			for (int i = 0; i < c.getAxeCol().getElements().size(); i++) {
				List<LineMatching> linesMatching = new ArrayList<>();
				for (int j = i + 1; j < c.getAxeCol().getElements().size(); j++) {
					int count = 0;
					List<Integer> emptyCol = new ArrayList<>();
					boolean match = true;
					List<Case> casesInContext = new ArrayList<>();
					for (int k = 0; k < c.getAxeLine().getElements().size(); k++) {
						if (c.getValue(k, i).equals(Array2DOrdValue.NEG) && c.getValue(k, j).equals(Array2DOrdValue.NEG)) {
							count++;
							casesInContext.add(new Case(c, c.getCol(i)[k], c.getCoordinates().getIndexLine() + k, c.getCoordinates().getIndexCol() +i));
							casesInContext.add(new Case(c, c.getCol(j)[k], c.getCoordinates().getIndexLine() + k, c.getCoordinates().getIndexCol() + j));
						} else if (c.getValue(k, i).equals(Array2DOrdValue.EMPTY) && c.getValue(k, j).equals(Array2DOrdValue.EMPTY)) {
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
				if (linesMatching.size() >= 1 && linesMatching.get(0).getCount() >= c.getAxeLine().getElements().size() - (linesMatching.size() + 1)) {
					Integer[] countMatching = new Integer[c.getAxeLine().getElements().size() + 1];
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
							for (int f = 0; f < c.getAxeCol().getElements().size(); f++) {
								if (f != i && !isIndexLine(linesMatching, f)) {
									if (c.getValue(col, f).equals(Array2DOrdValue.EMPTY)) {
										c.setValue(col, f, Array2DOrdValue.NEG);
										r.setFindNewCase(true);
										addAction(r, 
												"Deux possibilités sur deux colonnes alors exclusion des autres colonnes du même array2D",
												col, f, c, Array2DOrdValue.NEG,
												Arrays.asList(casesInContext.toArray(new Case[0])));
									} else if (c.getValue(col, f).equals(Array2DOrdValue.OK)) {
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
		for (Array2DOrd<Array2DOrdValue, String> c : arrays2D) {
			for (int i = 0; i < c.getAxeLine().getElements().size(); i++) {
				int count = 0;
				int indexEmpty = -1;
				for (int k = 0; k < c.getAxeCol().getElements().size(); k++) {
					if (c.getValue(i, k).equals(Array2DOrdValue.NEG)) {
						count++;
					} else {
						indexEmpty = k;
					}
				}
				if (count == c.getAxeCol().getElements().size() - 1) {
					if (c.getValue(i, indexEmpty).equals(Array2DOrdValue.EMPTY)) {
						r.setFindNewCase(true);
						addAction(r, "Tous N sauf 1 VIDE (en colonne), alors ce dernier est OK", i, indexEmpty, c, Array2DOrdValue.OK,
								new ArrayList<>());
						c.setValue(i, indexEmpty, Array2DOrdValue.OK);
					} else if (c.getValue(i, indexEmpty).equals(Array2DOrdValue.NEG)) {
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
