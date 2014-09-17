package org.netkernelroc.esi.rendering;

import org.netkernel.layer0.representation.IHDSNode;
import org.netkernelroc.esi.domain.ESILiteral;
import org.netkernelroc.esi.domain.ESIRoot;
import org.netkernelroc.esi.domain.ESITag;
import org.netkernelroc.esi.domain.Except;
import org.netkernelroc.esi.parsing.BaseTag;
import org.netkernelroc.esi.parsing.ESITagFactory;
import org.netkernelroc.esi.parsing.ESITagParser;
import org.netkernelroc.esi.parsing.PageHolder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RenderEngine {

    private ESITagFactory tagFactory = new ESITagFactory();
    private String esiToProcess;

    public RenderEngine(String esiToProcess) {
        this.esiToProcess = esiToProcess;
    }

    public StringBuilder renderedResults(ESIContext context) {
        PageHolder ph = new PageHolder();
        List<BaseTag> parsedTags = new ESITagParser().parse(esiToProcess);
        for (BaseTag parsedTag : parsedTags) {
            processNode(ph, parsedTag);
        }

        ESITag rootTag = new ESIRoot();
        insertChildrenIntoESITags(ph.getRoot(), rootTag);

        return render(context, rootTag);
    }

    private void insertChildrenIntoESITags(IHDSNode root, ESITag rootTag) {
        // No kids to insert
        if (root.getChildren().length == 0)
            return;

        List<ESITag> kids = new ArrayList<ESITag>(root.getChildren().length);
        for (IHDSNode child : root.getChildren()) {
            ESITag childTag = (ESITag) child.getValue();
            insertChildrenIntoESITags(child, childTag);
            kids.add(childTag);
        }
        rootTag.setChildren(kids);
    }

    private StringBuilder render(ESIContext context, ESITag rootTag) {
        StringBuilder result = new StringBuilder(esiToProcess.length());
        rootTag.render(context, result);
        return result;
    }

    private void processNode(PageHolder ph, BaseTag tag) {

        if (tag.isLiteral()) {
            ph.addCompleteEsiTag(new ESILiteral(tag.getTagPayload()));
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
