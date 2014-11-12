package text.string_metric.probabilistic_method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import text.string_metric.ProbabilisticMetric;

// Equivalent to MFBRecordMatcher
public class ProbabilisticMatcher implements ProbabilisticMetric<MatchResult, Record> {

	private static final Logger LOGGER = Logger.getLogger(ProbabilisticMatcher.class.getName());
	
	private static final double MAX_SCORE = 1;

    private final double minConfidenceValue;
    
    private static final Logger log = Logger.getLogger(ProbabilisticMatcher.class.getName()	);

    protected int recordSize = 0;

    protected int[][] attributeGroups;

    /**
     * The importance weights of each attribute.
     */
    protected double[] attributeWeights;

    /**
     * Indices of records to be compared.
     */
    protected int[] usedIndices;

    protected IAttributeMatcher[] attributeMatchers;

    /**
     * Indices of the Matchers to be used as blocking variables.
     */
    protected int[] blockedIndices;

    /**
     * Indices of records to be compared (without the blocked indices).
     */
    protected int[] usedIndicesNotblocked = null; // TODO to be reset

    /**
     * The attribute matching weigths computed by each attribute matcher.
     */
    protected double[] attributeMatchingWeights;

    /**
     * Tells whether attribute group are used.
     */
    protected boolean useGroups = false;

    /**
     * Threshold below which variables are blocked. Default value is 1.
     */
    protected double blockingThreshold = 1;

    /**
     * Threshold below which a record will not match
     */
    protected Double recordMatchThreshold = Double.POSITIVE_INFINITY;

    /**
     * hide the label when there is only one matcher.
     */
    protected boolean displayLabels = false;
    
	public ProbabilisticMatcher(double minConfidenceValue) {
		this.minConfidenceValue = minConfidenceValue;
	}
	
    public IAttributeMatcher[] getAttributeMatchers() {
        return attributeMatchers;
    }
    
    public boolean setAttributeMatchers(IAttributeMatcher[] attrMatchers) {
        if (attrMatchers == null || attrMatchers.length != recordSize) {
            return false;
        }
        // else
        this.attributeMatchers = attrMatchers;
        return true;
    }
    
    public boolean setAttributeWeights(double[] weights) {
        if (weights == null || recordSize != weights.length) {
            return false;
        }
        attributeWeights = normalize(weights);
        return attributeWeights != null;
    }
    
    private double[] normalize(double[] weights) {
        List<Integer> indices = new ArrayList<Integer>();
        double total = 0;
        for (int i = 0; i < recordSize; i++) {
            final double w = weights[i];
            if (w < 0) {
                throw new IllegalArgumentException(String.format("Invalid attribute weight %d", w));
            }
            total += w;
            if (w != 0) {
                indices.add(i);
            }
        }
        // at least one weight must be non zero
        if (total == 0.0d) {
            throw new IllegalArgumentException(String.format("At least one weight must be non zero")); 
        }

        this.usedIndices = new int[indices.size()];
        int j = 0;
        for (Integer idx : indices) {
            usedIndices[j++] = idx;
        }

        double[] normalized = new double[recordSize];
        for (int i = 0; i < recordSize; i++) {
            final double w = weights[i];
            // total = 0 already handled before
            normalized[i] = w / total;
        }
        return normalized;
    }
    
    public void setRecordSize(int numberOfAttributes) {
        this.recordSize = numberOfAttributes;
        // initialize weights with 1 for every attribute
        double[] weights = new double[recordSize];
        Arrays.fill(weights, 1.0d);
        this.attributeWeights = normalize(weights);
        // initialize array of attribute MATCHING weights
        this.attributeMatchingWeights = new double[recordSize];
    }
    
    public boolean setBlockingAttributeMatchers(int[] attrMatcherIndices) {
        for (int idx : attrMatcherIndices) {
            if (idx < 0 || idx >= recordSize) {
                log.warning("the index must be between 0 and the size of the records");
                return false;
            }
        }
        this.blockedIndices = attrMatcherIndices;
        return true;
    }
    
    protected int[] getUsedIndicesNotblocked() {
        if (usedIndicesNotblocked == null && usedIndices != null) {
            List<Integer> indices = new ArrayList<Integer>();
            for (int usedIdx : usedIndices) {
                boolean isBlocked = false;
                if (blockedIndices != null) {
                    for (int blockedIdx : blockedIndices) {
                        if (blockedIdx == usedIdx) {
                            isBlocked = true;
                            break;
                        }
                    }
                }
                if (!isBlocked) {
                    indices.add(usedIdx);
                }
            }
            usedIndicesNotblocked = new int[indices.size()];
            for (int i = 0; i < indices.size(); i++) {
                usedIndicesNotblocked[i] = indices.get(i);
            }
        }
        return usedIndicesNotblocked;
    }
    
    public boolean setblockingThreshold(double threshold) {
        this.blockingThreshold = threshold;
        return true;
    }
    
    public double[] getCurrentAttributeMatchingWeights() {
        return this.attributeMatchingWeights;
    }
    
    public String getLabeledAttributeMatchWeights() {
        final String separator = " | "; //$NON-NLS-1$
        StringBuffer buffer = new StringBuffer();
        double[] currentAttributeMatchingWeights = this.getCurrentAttributeMatchingWeights();
        for (int i = 0; i < currentAttributeMatchingWeights.length; i++) {
            IAttributeMatcher attributeMatcher = this.attributeMatchers[i];
            if (attributeMatcher.isDummyMatcher()) {
                continue; // Don't take dummy matcher into account.
            }
            if (buffer.length() > 0) {
                buffer.append(separator);
            }
            if (displayLabels) {
                String attributeName = attributeMatcher.getAttributeName();
                if (attributeName != null) {
                    buffer.append(attributeName).append(": "); //$NON-NLS-1$
                }
            }
            buffer.append(currentAttributeMatchingWeights[i]);
        }

        return buffer.toString();
    }
    
    public double getRecordMatchThreshold() {
        return this.recordMatchThreshold;
    }
    
    public void setRecordMatchThreshold(double recordMatchThreshold) {
        this.recordMatchThreshold = recordMatchThreshold;
    }
    
    public int getRecordSize() {
        return recordSize;
    }
    
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.getClass().getSimpleName()).append(" Record size:"); //$NON-NLS-1$
        buf.append(this.recordSize).append("\n"); //$NON-NLS-1$
        for (int usedIndice : usedIndices) {
            buf.append(this.attributeMatchers[usedIndice].getMatchType()).append("/"); //$NON-NLS-1$
        }
        return buf.toString();
    }
	
	public MatchResult measure(Record record1, Record record2) {
		Iterator<Attribute> mergedRecordAttributes = record1.getAttributes().iterator();
        Iterator<Attribute> currentRecordAttributes = record2.getAttributes().iterator();
        double confidence = 0;
        int matchIndex = 0;
        MatchResult result = new MatchResult(record1.getAttributes().size());
        int maxWeight = 0;
        while (mergedRecordAttributes.hasNext()) {
            Attribute left = mergedRecordAttributes.next();
            Attribute right = currentRecordAttributes.next();
            IAttributeMatcher matcher = attributeMatchers[matchIndex];
            // Find the first score to exceed threshold (if any).
            double score = matchScore(left, right, matcher);
            attributeMatchingWeights[matchIndex] = score;
            result.setScore(matchIndex, matcher, score, record1.getId(), left.getValue(), record2.getId(),
                    right.getValue());
            result.setThreshold(matchIndex, matcher.getThreshold());
            confidence += score * matcher.getWeight();
            maxWeight += matcher.getWeight();
            matchIndex++;
        }
        double normalizedConfidence = confidence > 0 ? confidence / maxWeight : confidence; // Normalize to 0..1 value
        result.setConfidence(normalizedConfidence);
        if (normalizedConfidence < minConfidenceValue) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.fine("Cannot match record: merged record has a too low confidence value (" + normalizedConfidence + " < "
                        + minConfidenceValue + ")");
            }
            return ProbabilisticMatcher.NonMatchResult.wrap(result);
        }
        record2.setConfidence(normalizedConfidence);
        return result;
	}
	
	private static double matchScore(Attribute leftAttribute, Attribute rightAttribute, IAttributeMatcher matcher) {
        // Find the best score in values
        // 1- Try first values
        String left = leftAttribute.getValue();
        String right = rightAttribute.getValue();
        double score = matcher.getMatchingWeight(left, right);
        if (score >= matcher.getThreshold()) {
            return score;
        }
        // 2- Compare using values that build attribute value (if any)
        Iterator<String> leftValues = leftAttribute.getValues().iterator();
        Iterator<String> rightValues = rightAttribute.getValues().iterator();
        double maxScore = 0;
        String leftValue = left;
        while (leftValues.hasNext()) {
            leftValue = leftValues.next();
            while (rightValues.hasNext()) {
                String rightValue = rightValues.next();
                score = matcher.getMatchingWeight(leftValue, rightValue);
                if (score > maxScore) {
                    maxScore = score;
                }
                if (maxScore == MAX_SCORE) {
                    // Can't go higher, no need to perform other checks.
                    return maxScore;
                }
            }
        }
        // Process remaining values in right (if any).
        while (rightValues.hasNext()) {
            String rightValue = rightValues.next();
            score = matcher.getMatchingWeight(leftValue, rightValue);
            if (score > maxScore) {
                maxScore = score;
            }
            if (maxScore == MAX_SCORE) {
                // Can't go higher, no need to perform other checks.
                return maxScore;
            }
        }
        return maxScore;
    }
	
	public static class NonMatchResult extends MatchResult {

        public static final MatchResult INSTANCE = wrap(new MatchResult(0));

        private final MatchResult result;

        private NonMatchResult(MatchResult result) {
            super(result.getScores().size());
            this.result = result;
        }

        public static MatchResult wrap(MatchResult result) {
            return new NonMatchResult(result);
        }

        @Override
        public List<Score> getScores() {
            return result.getScores();
        }

        @Override
        public List<Float> getThresholds() {
            return result.getThresholds();
        }

        @Override
        public void setScore(int index, IAttributeMatcher algorithm, double score, String recordId1, String value1,
                String recordId2, String value2) {
            result.setScore(index, algorithm, score, recordId1, value1, recordId2, value2);
        }

        @Override
        public void setThreshold(int index, float threshold) {
            result.setThreshold(index, threshold);
        }

        @Override
        public boolean isMatch() {
            return false;
        }
    }
	
}
