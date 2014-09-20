package org.netkernelroc.esi.parsing;

import org.netkernelroc.esi.rendering.ESIContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.testng.AssertJUnit.*;

/**
 *
 */
@Test(groups = "class_unit")
public class VariableSubstituterTest {

    private static final Map<String, String> valueMap = new HashMap<String, String>(89);
    private static final Map<String, String> queryParamsMap = new HashMap<String, String>(89);
    private static final UUID guid = UUID.randomUUID();

    private VariableSubstituter varSubNoValues;
    private VariableSubstituter varSubValues;

    static {
        valueMap.put("id", "ID_VALUE");
        valueMap.put("pageType", "PAGE_TYPE_VALUE");
        valueMap.put("photoId", "PHOTO_ID_VALUE");
        valueMap.put("mediaType", "MEDIA_TYPE_VALUE");

        queryParamsMap.put("id", guid.toString());
    }
    @BeforeClass
    public void buildSubs() {
        Map<String, String> empty = Collections.emptyMap();
        varSubNoValues = new VariableSubstituter(buildContext(empty));
        varSubValues = new VariableSubstituter(buildContext(valueMap));
    }

    private ESIContext buildContext(final Map<String, String> varMap) {
        return new ESIContext() {
                @Override
                public void assignVariable(String variableName, String value) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public String retrieveVariable(String variableName) {
                    String retVal = varMap.get(variableName);
                    return (retVal == null) ? "" : retVal;
                }

                @Override
                public String resolveInclude(String relativePath) {
                    throw new UnsupportedOperationException();
                }

            @Override
            public String lookupHttpParam(String key) {
                return queryParamsMap.get(key);
            }

            @Override
            public String getPath() {
                return "REQUEST_PATH_VALUE";
            }
        };
    }

    public void testNull() {
        assertEquals("", varSubNoValues.substitute(null));
    }

    public void testEmpty() {
        assertEquals("", varSubNoValues.substitute(""));
    }

    public void testNoSubstitutions() {
        String str = "TheQuickBrownFoxJumpedOverTheLazyDog";
        assertEquals(str, varSubNoValues.substitute(str));
    }

    public void testSubstitutionsNoVals() {
        String src = "/cda-web/head.htm?css=assets/css/gameday-all-min.css&css=" +
                "assets/css/custmodborderwhitebg.css&css=assets/css/gameday-top-module.css&id=" +
                "$(id)&pageType=$(pagetype)&requestPath=$(REQUEST_PATH)&photoId=$(photoId)&mediaType=" +
                "$(mediaType)";
        String expectedResult = "/cda-web/head.htm?css=assets/css/gameday-all-min.css&css=" +
                "assets/css/custmodborderwhitebg.css&css=assets/css/gameday-top-module.css&id=" +
                "&pageType=&requestPath=REQUEST_PATH_VALUE&photoId=&mediaType=";
        assertEquals(expectedResult, varSubNoValues.substitute(src));
    }

    public void testSubstitutions() {
        String src = "/cda-web/head.htm?css=assets/css/gameday-all-min.css&css=" +
                "assets/css/custmodborderwhitebg.css&css=assets/css/gameday-top-module.css&id=" +
                "$(id)&pageType=$(pageType)&requestPath=$(REQUEST_PATH)&photoId=$(photoId)&mediaType=" +
                "$(mediaType)";
        String expectedResult = "/cda-web/head.htm?css=assets/css/gameday-all-min.css&css=" +
                "assets/css/custmodborderwhitebg.css&css=assets/css/gameday-top-module.css&id=" +
                "ID_VALUE&pageType=PAGE_TYPE_VALUE&requestPath=REQUEST_PATH_VALUE&photoId=" +
                "PHOTO_ID_VALUE&mediaType=MEDIA_TYPE_VALUE";

        assertEquals(expectedResult, varSubValues.substitute(src));
    }

    public void testGameSubNoVals() {
        String src = "/cda-web/head.htm?css=assets/css/gameday-all-min.css&css=assets/css/custmodborderwhitebg.css&" +
                "css=assets/css/gameday-top-module.css&id=$(id)&pageType=$(pagetype)&requestPath=$(REQUEST_PATH)&" +
                "photoId=$(photoId)&mediaType=$(mediaType)";

        String expectedResults = "/cda-web/head.htm?css=assets/css/gameday-all-min.css&css=assets/css/custmodborderwhitebg.css&" +
                "css=assets/css/gameday-top-module.css&id=&pageType=&requestPath=REQUEST_PATH_VALUE&" +
                "photoId=&mediaType=";

        assertEquals(expectedResults, varSubNoValues.substitute(src));
    }

    public void testHttpParam() {
        String src = "$(QUERY_STRING{'id'})";
        String expected = guid.toString();
        assertEquals(expected, varSubValues.substitute(src));
    }
}
