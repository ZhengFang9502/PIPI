package proteomics.Types;


import java.util.*;

public class FinalResultEntry {

    private static final double histogramBinSize = 0.05f;
    private static final double histogramBinOffset = 0.5;
    private static final double maxScore = 10;
    private static final int scoreNum = 5;

    private int scanNum;
    private List<Double> scoreList = new LinkedList<>();
    private Peptide peptide;
    private double normalizedCrossXcorr;
    private int globalSearchRank;
    private double ionFrac;
    private double matchedHighestIntensityFrac;
    private Set<String> scoredPeptide = new HashSet<>();

    private int candidateNum;
    private final int[] scoreHistogram = new int[(int) (maxScore / histogramBinSize) + 1]; // start from zero score.
    private double negativeLog10EValue = -9999;
    private float qValue = -1;

    private double[] log10SurvivalArray;
    private int startIdx = -1;
    private double rSquare = -1;

    public FinalResultEntry(int scanNum) {
        this.scanNum = scanNum;
    }

    public double getScore() {
        return scoreList.get(0);
    }

    public boolean noScore() {
        return scoreList.isEmpty();
    }

    public Peptide getPeptide() {
        return peptide;
    }
    
    public boolean isDecoy() {
        return peptide.isDecoy();
    }

    public double getDeltaC() {
        if (scoreList.size() < 2) {
            return 1;
        } else {
            return (scoreList.get(0) - scoreList.get(1)) / scoreList.get(0);
        }
    }

    public double getDeltaLC() {
        if (scoreList.size() < 3) {
            return 1;
        } else {
            return (scoreList.get(0) - scoreList.get(scoreList.size() - 1)) / scoreList.get(0);
        }
    }

    public int getScanNum() {
        return scanNum;
    }

    public boolean scored(String peptide) {
        return scoredPeptide.contains(peptide);
    }

    public void addScore(double score) {
        if (scoreList.size() < scoreNum) {
            scoreList.add(score);
            Collections.sort(scoreList, Collections.reverseOrder());
        } else if (score > scoreList.get(scoreNum - 1)) {
            scoreList.remove(scoreNum - 1);
            scoreList.add(score);
            Collections.sort(scoreList, Collections.reverseOrder());
        }
    }

    public void addScoredPeptide(String peptide) {
        scoredPeptide.add(peptide);
    }

    public void setPeptide(Peptide peptide) {
        this.peptide = peptide;
    }

    public void setScanNum(int scanNum) {
        this.scanNum = scanNum;
    }

    public int getCandidateNum() {
        return candidateNum;
    }

    public void addToScoreHistogram(double score) {
        if (score >= 0) {
            ++candidateNum;
            ++scoreHistogram[(int) (score / histogramBinSize + histogramBinOffset)];
        } else {
            ++candidateNum;
            ++scoreHistogram[0];
        }
    }

    public void setQValue(float qValue) {
        this.qValue = qValue;
    }

    public float getQValue() {
        return qValue;
    }

    public int[] getScoreHistogram() {
        return scoreHistogram;
    }

    public void setNegativeLog10EValue(double negativeLog10EValue) {
        this.negativeLog10EValue = negativeLog10EValue;
    }

    public double getNegativeLog10EValue() {
        return negativeLog10EValue;
    }

    public double getHistogramBinSize() {
        return histogramBinSize;
    }

    public double getHistogramBinOffset() {
        return histogramBinOffset;
    }

    public void setLog10SurvivalArray(double[] log10SurvivalArray) {
        this.log10SurvivalArray = log10SurvivalArray;
    }

    public double[] getLog10SurvivalArray() {
        return log10SurvivalArray;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }

    public int getStartIdx() {
        return startIdx;
    }

    public void setRSquare(double rSquare) {
        this.rSquare = rSquare;
    }

    public double getRSquare() {
        return rSquare;
    }

    public void setNormalizedCrossXcorr(double normalizedCrossXcorr) {
        this.normalizedCrossXcorr = normalizedCrossXcorr;
    }

    public void setGlobalSearchRank(int rank) {
        globalSearchRank = rank;
    }

    public double getNormalizedCrossXcorr() {
        return normalizedCrossXcorr;
    }

    public int getGlobalSearchRank() {
        return globalSearchRank;
    }

    public void setIonFrac(double ionFrac) {
        this.ionFrac = ionFrac;
    }

    public void setMatchedHighestIntensityFrac(double matchedHighestIntensityFrac) {
        this.matchedHighestIntensityFrac = matchedHighestIntensityFrac;
    }

    public double getIonFrac() {
        return ionFrac;
    }

    public double getMatchedHighestIntensityFrac() {
        return matchedHighestIntensityFrac;
    }
}
