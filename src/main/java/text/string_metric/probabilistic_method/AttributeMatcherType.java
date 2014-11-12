// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package text.string_metric.probabilistic_method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author scorreia
 * 
 * Enumeration of all available attribute matcher algorithms.
 */
public enum AttributeMatcherType {
    EXACT("Exact"),
    EXACT_IGNORE_CASE("Exact - ignore case"), 
    SOUNDEX("Soundex"), 
    SOUNDEX_BR("Soundex BR"), 
    LEVENSHTEIN("Levenshtein"), 
    JARO("Jaro"), 
    JARO_WINKLER("Jaro-Winkler"), 
    CUSTOM("custom"), 
    DUMMY("Dummy"); 

    private final String componentValue;

    private static final String CLASSNAME_PREFIX = "AttributeMatcherType."; 

    AttributeMatcherType(String componentValue) {
        this.componentValue = componentValue;
    }

    /**
     * Getter for componentValue.
     * 
     * @return the componentValue
     */
    public String getComponentValue() {
        return this.componentValue;
    }

    /**
     * Getter for label.
     * 
     * @return the internationalized label
     */
    public String getLabel() {
        return "" + CLASSNAME_PREFIX + this.name(); // TBD: was Messages#..
    }

    /**
     * @return all Attribute Matcher Types except the internal DUMMY algorithm.
     */
    public static String[] getAllTypes() {
        List<String> types = new ArrayList<String>();
        for (int i = 0; i < AttributeMatcherType.values().length; i++) {
            if (i != DUMMY.ordinal()) {
                types.add(AttributeMatcherType.values()[i].getLabel());
            }
        }
        return types.toArray(new String[types.size() - 1]);
    }

    /**
     * Get AttributeMatcherType by component value, keep the short method name for components
     * 
     * @param componentValue
     * @return the AttributeMatcherType
     */
    public static AttributeMatcherType get(String componentValue) {
        for (AttributeMatcherType type : AttributeMatcherType.values()) {
            if (type.name().equalsIgnoreCase(componentValue) || type.getComponentValue().equalsIgnoreCase(componentValue)) {
                return type;
            }
        }
        return null;
    }
}
