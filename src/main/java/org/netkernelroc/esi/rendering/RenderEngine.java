package org.netkernelroc.esi.rendering;

import org.netkernel.layer0.representation.IHDSNode;
import org.netkernelroc.esi.domain.ESILiteral;
import org.netkernelroc.esi.domain.ESITag;
import org.netkernelroc.esi.domain.Except;
import org.netkernelroc.esi.parsing.BaseTag;
import org.netkernelroc.esi.parsing.ESITagFactory;
import org.netkernelroc.esi.parsing.ESITagParser;
import org.netkernelroc.esi.parsing.PageHolder;

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

        StringBuilder results = new StringBuilder(esiToProcess.length());
        render(context, ph.getRoot(), results);
        return results;
    }

    private void render(ESIContext context, IHDSNode root, StringBuilder result) {
        for (IHDSNode child : root.getChildren()) {
            if ("HTML".equals(child.getName()))
                result.append(child.getValue().toString());
            else if ("ESI".equals(child.getName())) {
                final ESITag childTag = (ESITag) child.getValue();
                result.append(childTag.render(context));
                if (child.getChildren().length > 0) {
                    // TODO - hack for now to skip exception handling.  Needs
                    // to be implemented as part of the esi:try
                    if (childTag instanceof Except)
                        continue;
                    render(context, child, result);
                }
            }
            else {
                // Should only have "raw html" and ESI tags.
                throw new IllegalStateException();
            }
        }
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
