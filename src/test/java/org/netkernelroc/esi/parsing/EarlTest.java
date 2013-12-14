package org.netkernelroc.esi.parsing;

import org.netkernel.layer0.representation.IHDSNode;
import org.netkernelroc.esi.domain.ESITag;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 *
 */
@Test(groups = "manual")
public class EarlTest {

    private ESITagFactory tagFactory = new ESITagFactory();

    String simplePayload;
    String simpleResults;

    String gameDayPayload;
    String gameDayResults;

    @BeforeClass
    public void initPayload() throws IOException {
        File payloadFile = new ClassPathResource("dev/TEST/game2.html").getFile();
        simplePayload = readFileToString(payloadFile, "UTF-8");

        File resultsFile = new ClassPathResource("dev/TEST/game2_rendered.html").getFile();
        simpleResults = readFileToString(resultsFile, "UTF-8");

        payloadFile = new ClassPathResource("dev/TEST/pre.html").getFile();
        gameDayPayload = readFileToString(payloadFile, "UTF-8");

        resultsFile = new ClassPathResource("dev/TEST/pre_rendered.html").getFile();
        gameDayResults = readFileToString(resultsFile, "UTF-8");
    }

    public void simple() {
        xmlLoad(simplePayload, simpleResults);
    }


    public void preGame() {
        xmlLoad(gameDayPayload, gameDayResults);
    }

    private void xmlLoad(String payload, String expected)  {
        PageHolder ph = new PageHolder();

        List<Tag> parsedTags = new ESITagParser().parse(payload);
        for (Tag parsedTag : parsedTags) {
            processNode(ph, parsedTag);
        }


        String renderResult = render(ph.getRoot());
        assertEquals(expected, renderResult);
    }

    private String render(IHDSNode root) {
        StringBuilder result = new StringBuilder();
        for (IHDSNode child : root.getChildren()) {
            if ("HTML".equals(child.getName()))
                result.append(child.getValue().toString());
            else if ("ESI".equals(child.getName())) {
                result.append(((ESITag) child.getValue()).render());
            }
            else {
                result.append("ZZZZZZ");  // purposeful noise for the moment.
            }
        }
        return result.toString();
    }

    private void processNode(PageHolder ph, Tag tag) {

        if (tag.isLiteral()) {
            ph.addLiteral(tag.getTagPayload());
            return;
        }

        if (tag.isComplete()) {
            ph.addCompleteEsiTag(tagFactory.createTag(tag));
            return;
        }

        if (tag.isEndTag()) {
            ph.esiTagEnd();
            return;
        }

        ph.addEsiTagStart(tagFactory.createTag(tag));
    }

}
