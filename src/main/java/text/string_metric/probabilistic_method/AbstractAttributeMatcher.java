package text.string_metric.probabilistic_method;


public abstract class AbstractAttributeMatcher implements IAttributeMatcher {

	private NullOption nullOption = NullOption.nullMatchNull;

    private String attributeName = null;
    
    private final double threshold;
    
    private final double weight;
    
    public AbstractAttributeMatcher(double threshold, double weight) {
    	this.threshold = threshold;
    	this.weight = weight;
    }

    public float getThreshold() {
        return (float) this.threshold;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getMatchingWeight(String str1, String str2) {
        switch (nullOption) {
        case nullMatchAll:
            if (isNullOrEmpty(str1) || isNullOrEmpty(str2)) {
                return 1.0;
            }
            break;
        case nullMatchNone:
            if (isNullOrEmpty(str1) || isNullOrEmpty(str2)) {
                return 0.0;
            }
            break;
        case nullMatchNull:
            boolean str1IsNull = isNullOrEmpty(str1);
            boolean str2IsNull = isNullOrEmpty(str2);
            if (str1IsNull && str2IsNull) { // both null => match
                return 1.0;
            } else if (str1IsNull || str2IsNull) { // only one null => non-match
                return 0.0;
            }
            break;
        default:
            break;
        }

        assert !isNullOrEmpty(str1) : "string should not be null or empty here"; //$NON-NLS-1$
        assert !isNullOrEmpty(str2) : "string should not be null or empty here"; //$NON-NLS-1$
        return getWeight(str1, str2);
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str); //$NON-NLS-1$ 
    }

    /**
     * Calculate matching weight using specified matcher.
     * 
     * @param record1 the first string
     * @param record2 the secord string
     * @return result between 0 and 1
     */
    protected abstract double getWeight(String record1, String record2);

    public void setNullOption(NullOption option) {
        this.nullOption = option;
    }

    public NullOption getNullOption() {
        return nullOption;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String name) {
        this.attributeName = name;
    }

    public void setNullOption(String option) {
        if (IAttributeMatcher.NullOption.nullMatchAll.name().equalsIgnoreCase(option)) {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchAll;
        } else if (IAttributeMatcher.NullOption.nullMatchNone.name().equalsIgnoreCase(option)) {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchNone;
        } else if (IAttributeMatcher.NullOption.nullMatchNull.name().equalsIgnoreCase(option)) {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchNull;
        } else {
            this.nullOption = IAttributeMatcher.NullOption.nullMatchNull;
        }
    }

    public boolean isDummyMatcher() {
        return false;
    }

}
